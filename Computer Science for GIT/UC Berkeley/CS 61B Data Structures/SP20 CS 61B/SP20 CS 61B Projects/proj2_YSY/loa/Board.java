/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

import java.util.regex.Pattern;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 *  @author Seongyun Yang
 */
class Board {

    /** Default number of moves for each side that results in a draw. */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row][col]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        // FIXME
        _moves.clear();

        for (int c = 0; c < contents.length; c++) {
            for (int r = 0; r < contents[c].length; r++) {
                int id = sq(r, c).index();
                _board[id] = contents[c][r];
            }
        }
        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;
    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }
        _moves.clear();
        _moves.addAll(board._moves);
        _turn = board.turn();
        for (int i = 0; i < board._board.length; i++) {
            Piece newP = board._board[i];
            _board[i] = newP;
        }
        // FIXME
    }

    /** Return the contents of the square at SQ. */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /** Set the square at SQ to V and set the side that is to move next
     *  to NEXT, if NEXT is not null. */
    void set(Square sq, Piece v, Piece next) {
        // FIXME
        int id = sq.index();
        _board[id] = v;

        if (next != null) {
            _turn = next;
        }
    }

    /** Set the square at SQ to V, without modifying the side that
     *  moves next. */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /** Set limit on number of moves by each side that results in a tie to
     *  LIMIT, where 2 * LIMIT > movesMade(). */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /** Assuming isLegal(MOVE), make MOVE. Assumes MOVE.isCapture()
     *  is false. */
    void makeMove(Move move) {
        assert isLegal(move);
        assert !move.isCapture();
        // FIXME assert !move.isCapture 이거 어디서 튀어나온거야?

        Square fromSq = move.getFrom();
        Square toSq = move.getTo();
        boolean catched = _turn.opposite() == get(toSq);

        set(fromSq, EMP);
        set(toSq, _turn);
        move = catched ? move.captureMove() : move;
        _moves.add(move);
        _turn = _turn.opposite();
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        assert movesMade() > 0;
        // FIXME
        int preMoveId = _moves.size() - 1;
        Move preMove = _moves.get(preMoveId);

        _moves.remove(preMoveId);
        _turn = _turn.opposite();

        if (preMove.isCapture()) {
            set(preMove.getTo(), _turn.opposite());
        } else {
            set(preMove.getTo(), EMP);
        }
        set(preMove.getFrom(), _turn);
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff FROM - TO is a legal move for the player currently on
     *  move. */
    boolean isLegal(Square from, Square to) {
        if (to == null) {
            return false;
        } else if (!from.isValidMove(to)) {
            return false;
        } else if (from.distance(to) != stepsCanMove(from, to)) {
            return false;
        } else if (blocked(from, to)) {
            return false;
        }
        return true;   // FIXME
    }

    /** Return true iff MOVE is legal for the player currently on move.
     *  The isCapture() property is ignored. */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo());
    }

    /** Return a sequence of all legal moves from this position. */
    List<Move> legalMoves() {
        ArrayList<Move> allPossibleMoves = new ArrayList<>();

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                Square sq = sq(c, r);
                if (get(sq) == _turn) {
                    List<Move> allMoves = legalMoves(sq);
                    allPossibleMoves.addAll(allMoves);
                }
            }
        }
        return allPossibleMoves;  // FIXME
    }



    /** Return true iff the game is over (either player has all his
     *  pieces continguous or there is a tie). */
    boolean gameOver() {
        return winner() != null;
    }

    /** Return true iff SIDE's pieces are continguous. */
    boolean piecesContiguous(Piece side) {
        return getRegionSizes(side).size() == 1;
    }

    /** Return the winning side, if any.  If the game is not over, result is
     *  null.  If the game has ended in a tie, returns EMP. */
    Piece winner() {
        if (!_winnerKnown) {
            // FIXME
            if (piecesContiguous(_turn)) {
                _winner = _turn;
            } else if (!piecesContiguous(_turn) && piecesContiguous(_turn.opposite())) {
                _winner = _turn.opposite();
            } else if (_turn == BP && _moveLimit * 2 <= movesMade()) {
                _winner = EMP;
            }
            _winnerKnown = true;
        }
        return _winner;
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                out.format("%s ", get(sq(c, r)).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return true if a move from FROM to TO is blocked by an opposing
     *  piece or by a friendly piece on the target square. */
    private boolean blocked(Square from, Square to) {
        if (get(to) == _turn || get(from) == _turn.opposite()) {
            return true;
        }

        int direction = from.direction(to);
        int totalSteps = stepsCanMove(from, to);

        for (int i = 1; i < totalSteps; i++) {
            Square sq = from.moveDest(direction, i);
            if (get(sq) == _turn.opposite()) {
                return true;
            }
        }
        return false;
        // FIXME
    }

    /** Return the size of the as-yet unvisited cluster of squares
     *  containing P at and adjacent to SQ.  VISITED indicates squares that
     *  have already been processed or are in different clusters.  Update
     *  VISITED to reflect squares counted. */
    private int numContig(Square sq, boolean[][] visited, Piece p) {
        int counter = 0;

        if (p == EMP || p != get(sq) ||
                visited[sq.col()][sq.row()] == true) {
            return 0;
        }

        if (get(sq) == p) {
            counter++;
        }

        visited[sq.col()][sq.row()] = true;

        for (int direction = 0; direction < 8; direction++) {
            Square newSq = sq.moveDest(direction, 1);
            if (newSq != null) {
                counter = counter + numContig(newSq, visited, p);
            }
        }
        return counter;
        // FIXME
    }

    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();
        // FIXME
        boolean[][] visitedBP = new boolean[BOARD_SIZE][BOARD_SIZE];
        boolean[][] visitedWP = new boolean[BOARD_SIZE][BOARD_SIZE];
        int lenVisited = visitedBP.length;

        for (int i = 0; i < lenVisited; i++) {
            Arrays.fill(visitedBP[i], false);
            Arrays.fill(visitedWP[i], false);
        }

        for (int r = 0; r < BOARD_SIZE; r++) {
            for (int c = 0; c < BOARD_SIZE; c++) {
                Square sq = sq(c, r);
                if (get(sq) == BP) {
                    int size = numContig(sq, visitedBP, BP);
                    if (size != 0) {
                        _blackRegionSizes.add(size);
                    }
                } else if (get(sq) == WP) {
                    int size = numContig(sq, visitedWP, WP);
                    if (size != 0) {
                        _whiteRegionSizes.add(size);
                    }
                }
            }
        }
        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = false;
    }

    /** Return the sizes of all the regions in the current union-find
     *  structure for side S. */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }

    // FIXME: Other methods, variables?

    /** Return the number of the steps that the piece can move
     *  based on the direction.
     *  Make sure that count the steps based on horizontal, vertical,
     *  negative diagonal, or positive diagonal.
     */
    private int stepsCanMove(Square from, Square to) {
        int directionA = from.direction(to);
        int directionB = (directionA + 4) % BOARD_SIZE;
        int total = 0;

        for (Square sq = from; sq != null;
             sq = sq.moveDest(directionA, 1)) {
            if (get(sq) != EMP) {
                total++;
            }
        }

        for (Square sq = from; sq != null;
             sq = sq.moveDest(directionB, 1)){
            if (get(sq) != EMP) {
                total++;
            }
        }
        return total - 1;
    }

    /** call the method stepsCanMove with fromSq and direction */
    private int stepsCanMove(Square from, int direction) {
        Square toSq = from.moveDest(direction, 1);
        if (toSq == null) {
            int newDirection = (direction + 4) % BOARD_SIZE;
            Square newToSq = from.moveDest(newDirection, 1);
            return stepsCanMove(from, newToSq);
        }
        return stepsCanMove(from, toSq);
    }

    /** All legal moves that one square can move */
    private ArrayList<Move> legalMoves(Square fromSq) {
        ArrayList<Move> allMoves = new ArrayList<>();
        ArrayList<Square> allToSq = new ArrayList<>();
        int verticalSteps = stepsCanMove(fromSq, 0);
        int horizontalSteps = stepsCanMove(fromSq, 2);
        int posDiagSteps = stepsCanMove(fromSq, 1);
        int negDiagSteps = stepsCanMove(fromSq, 3);

        for (int i = 0; i < 8; i++) {
            Square toSq = fromSq;
            if (i == 0 || i == 4) {
                toSq = fromSq.moveDest(i, verticalSteps);
            } else if (i == 2 || i == 6) {
                toSq = fromSq.moveDest(i, horizontalSteps);
            } else if (i == 1 || i == 5) {
                toSq = fromSq.moveDest(i, posDiagSteps);
            } else if (i == 3 || i == 7) {
                toSq = fromSq.moveDest(i, negDiagSteps);
            }
            allToSq.add(toSq);
        }

        for (int i = 0; i < allToSq.size(); i++) {
            Square toSq = allToSq.get(i);
            if (isLegal(fromSq, toSq)) {
                Move move = Move.mv(fromSq, toSq);
                allMoves.add(move);
            }
        }
        return allMoves;
    }

    /** Find the heuristic value for the board. */
    public int findHeuristic() {
        if (_winnerKnown) {
            if (_winner == _turn) {
                return 5;
            } else if (_winner != _turn) {
                return -5;
            } else {
                return 2;
            }
        }
        if (!_winnerKnown) {
            int numBP = 0;
            int numWP = 0;
            int heuristicVal = 0;

            for (int size : _whiteRegionSizes) {
                numWP = numWP + size;
            }

            for (int size : _blackRegionSizes) {
                numBP = numBP + size;
            }

            int avgSizeBP = Math.round(numBP / _blackRegionSizes.size());
            int avgSizeWP = Math.round(numWP / _whiteRegionSizes.size());

        }
        return 0;
    }

    /** The standard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** Current contents of the board.  Square S is at _board[S.index()]. */
    private final Piece[] _board = new Piece[BOARD_SIZE  * BOARD_SIZE];

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** Limit on number of moves before tie is declared.  */
    private int _moveLimit;
    /** True iff the value of _winner is known to be valid. */
    private boolean _winnerKnown;
    /** Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     *  in progress).  Use only if _winnerKnown. */
    private Piece _winner;

    /** True iff subsets computation is up-to-date. */
    private boolean _subsetsInitialized;

    /** List of the sizes of continguous clusters of pieces, by color. */
    private final ArrayList<Integer>
        _whiteRegionSizes = new ArrayList<>(),
        _blackRegionSizes = new ArrayList<>();
}
