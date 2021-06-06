/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.regex.Pattern;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 *  @author Kidong Kim
 */
class Board {

    /** Default number of moves for each side that results in a draw. */
    static final int DEFAULT_MOVE_LIMIT = 10000;

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
//        this(BoardTest.BOARD4, BP); //TODO
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move.
     *
     * For copyFrom to copy the region data ~ must not be null */
    void initialize(Piece[][] contents, Piece side) {
        _moves.clear();
        _boards.clear();
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();

        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;
        _winner = null;
        _winnerKnown = false;

        for (int c = 0; c < contents.length; c++) {
            for (int r = 0; r < contents[0].length; r++) {
                int index = r * 8 + c;
                _board[index] = contents[r][c];
            }
        }

        computeRegions();
    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /** Set my state to a copy of BOARD.
     *
     * Clear all the board to set the data
     * Copying history of boards. needs to reverse the order as Stack is FILO
     * Copying contents of boards.*/
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }

        clear();
        _moves.clear();
        _boards.clear();
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();

        _winner = board._winner;
        _winnerKnown = board._winnerKnown;
        _turn = board._turn;
        _moveLimit = board._moveLimit;
        _blackRegionSizes.addAll(board._blackRegionSizes);
        _whiteRegionSizes.addAll(board._whiteRegionSizes);
        _moves.addAll(board._moves);


        Stack<Board> tmpMove = new Stack<Board>();
        for (Board tmp : board._boards) {
            tmpMove.push(tmp);
        }
        for (Board tmp : tmpMove) {
            _boards.push(tmp);
        }


        for (int i = 0; i < board._board.length; i++) {
            _board[i] = board._board[i];
        }
    }

    /** Return the contents of the square at SQ. */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /** Set the square at SQ to V and set the side that is to move next
     *  to NEXT, if NEXT is not null. */
    void set(Square sq, Piece v, Piece next) {
        _board[sq.index()] = v;
        if (next != null) {
            _turn = next;
        }
    }

    /** Set the square at SQ to V, without modifying the side that
     *  moves next. */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /** Set limit on number of moves (before tie results) to LIMIT.
     * When checking the limit it should be limit * 2 is checked by winner*/
    void setMoveLimit(int limit) {
        _moveLimit = limit;
    }

    /** Assuming isLegal(MOVE), make MOVE. Assumes MOVE.isCapture()
     *  is false.
     *
     *  Get piece from Square f and assign it to square t
     *  while setting empty for square t.
     *  If isCapture() is false, to was originally empty.
     *
     *  make a deep copy of current board and store it to
     *  a stack of boards before making a move.
     *  Move the piece from the Square f to Square t.
     *  Set the content of Square t as Empty as the piece has been removed.
     *  */
    void makeMove(Move move) {
        assert isLegal(move);

        Board tmp = new Board();
        tmp.copyFrom(this);
        _boards.push(tmp);

        Square f = move.getFrom();
        Square t = move.getTo();

        if (get(f).opposite() == get(t)) {
            move = move.captureMove();
        }
        set(t, get(f));
        set(f, EMP);

        _moves.add(move);

        _subsetsInitialized = false;
        winner();

        _turn = _turn.opposite();
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0.
     *
     *  Remove the previous move from moves ~ automatically # of moves - 1.
     *  Get the previous copy of _board from the Stack of boards. */
    void retract() {
        assert movesMade() > 0;
        _moves.remove(movesMade() - 1);
        copyFrom(_boards.pop());
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff FROM - TO is a legal move for the player currently on
     *  move.
     *
     *  1. piece in the from square is the _turn
     *  2. from-to is not blocked by other piece
     *      and to doesn't have opposing square
     *  3. distance btw from and to square == # of pieces on the line
     *
     *  */
    boolean isLegal(Square from, Square to) {
        if (_turn == get(from) && !blocked(from, to)) {
            int fT = from.direction(to);
            int tF = to.direction(from);

            int count = 1;
            int step = 1;
            while (true) {
                if (from.moveDest(fT, step) == null
                        && from.moveDest(tF, step) == null) {
                    break;
                } else if (from.moveDest(fT, step) == null) {
                    if (get(from.moveDest(tF, step)) != EMP) {
                        count++;
                    }
                    step++;
                } else if (from.moveDest(tF, step) == null) {
                    if (get(from.moveDest(fT, step)) != EMP) {
                        count++;
                    }
                    step++;
                } else {
                    if (get(from.moveDest(fT, step)) != EMP) {
                        count++;
                    }
                    if (get(from.moveDest(tF, step)) != EMP) {
                        count++;
                    }
                    step++;
                }
            }
            int distance = from.distance(to);
            return distance == count;
        }
        return false;
    }

    /** Return true iff MOVE is legal for the player currently on move.
     *  The isCapture() property is ignored. */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo());
    }

    /** Return a sequence of all legal moves from this position.
     *
     *  For each square to all other squares isLegal()
     *  to check legal move and add it to the list.
     *
     *  Returns all legal moves for current player by isLegal()
     *  _turn == get(from) */
    List<Move> legalMoves() {
        ArrayList<Move> legal = new ArrayList<>();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Square from = sq(col, row);
                if (get(from) != EMP) {
                    for (int row2 = 0; row2 < BOARD_SIZE; row2++) {
                        for (int col2 = 0; col2 < BOARD_SIZE; col2++) {
                            Square to = sq(col2, row2);
                            if (isLegal(from, to)) {
                                legal.add(Move.mv(from, to));
                            }
                        }
                    }
                }
            }
        }

        return legal;
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
     *  null. If the game has ended in a tie, returns EMP.
     *  1. I am the only*/
    Piece winner() {
        if (!_winnerKnown) {
            if (piecesContiguous(_turn)) {
                _winner = _turn;
                _winnerKnown = true;
            } else if (!piecesContiguous(_turn)
                    && piecesContiguous(_turn.opposite())) {
                _winner = _turn.opposite();
                _winnerKnown = true;
            } else if (_moveLimit * 2 <= movesMade()) {
                _winner = EMP;
                _winnerKnown = true;
            }
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
     *  piece or by a friendly piece on the target square.
     *
     *  If the move is in the line || friendly piece on the destination
     *
     *  */
    private boolean blocked(Square from, Square to) {
        if (!from.isValidMove(to) || get(from) == get(to)) {
            return true;
        }
        int dir = from.direction(to);
        int distance = from.distance(to);
        for (int i = 1; i < distance; i++) {
            if (get(from).opposite() == get(from.moveDest(dir, i))) {
                return true;
            }
        }
        return false;
    }

    /** Return the size of the as-yet unvisited cluster of squares
     *  containing P at and adjacent to SQ.  VISITED indicates squares that
     *  have already been processed or are in different clusters.  Update
     *  VISITED to reflect squares counted. */
    private int numContig(Square sq, boolean[][] visited, Piece p) {
        if (p == EMP || get(sq) != p || visited[sq.col()][sq.row()]) {
            return 0;
        }
        int count = 1;
        visited[sq.col()][sq.row()] = true;
        for (int direction = 0; direction < 8; direction++) {
            Square tmp = sq.moveDest(direction, 1);
            if (tmp != null) {
                count += numContig(tmp, visited, p);
            }
        }
        return count;
    }

    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();

        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Square sq = sq(col, row);
                if (get(sq) == BP) {
                    int size = numContig(sq, visited, BP);
                    if (size != 0) {
                        _blackRegionSizes.add(size);
                    }
                } else if (get(sq) == WP) {
                    int size = numContig(sq, visited, WP);
                    if (size != 0) {
                        _whiteRegionSizes.add(size);
                    }
                }
            }
        }

        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = true;
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

    public Stack<Board> get_boards() {
        return _boards;
    }

    /** Getter method of _moves.
     *  @return ArrayList<> _moves list of the board. **/
    public ArrayList<Move> getMoves() {
        return _moves;
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

    /** Stack of all board status according to each move. */
    private Stack<Board> _boards = new Stack<Board>();

    /** Current contents of the board.  Square S is at _board[S.index()]. */
    private final Piece[] _board = new Piece[BOARD_SIZE * BOARD_SIZE];

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
