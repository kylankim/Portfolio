package amazons;




import java.util.Collections;
import java.util.Iterator;

import static amazons.Piece.*;
import static amazons.Move.mv;


/** The state of an Amazons Game.
 *  @author Kidong Kim
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 10;

    /** Initializes a game board with SIZE squares on a side in the
     *  initial position. */
    Board() {
        init();
    }

    /** Initializes a copy of MODEL. */
    Board(Board model) {
        copy(model);
    }

    /** Copies MODEL into me. */
    void copy(Board model) {
        this.board = model.board;
        this.nummove = model.nummove;
        this._turn = model._turn;
        this._winner = model._winner;
    }
    /** saves the status onto past. */
    void save() {
        past.board = this.board;
        past.nummove = this.nummove;
        past._turn = this._turn;
        past._winner = this._winner;
    }

    /** Clears the board to the initial position. */
    void init() {
        _turn = WHITE;
        _winner = EMPTY;
        board = new Object[Board.SIZE][Board.SIZE];
        this.put(WHITE, Square.sq(0, 3));
        this.put(BLACK, Square.sq(0, 6));
        this.put(WHITE, Square.sq(9, 3));
        this.put(BLACK, Square.sq(9, 6));
        this.put(WHITE, Square.sq(3, 0));
        this.put(BLACK, Square.sq(3, 9));
        this.put(WHITE, Square.sq(6, 0));
        this.put(BLACK, Square.sq(6, 9));
        nummove = 0;
    }

    /** Return the Piece whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Return the number of moves (that have not been undone) for this
     *  board. */
    int numMoves() {
        return nummove;
    }

    /** Return the winner in the current position, or null if the game is
     *  not yet finished. */
    Piece winner() {
        if (!legalMoves().hasNext()) {
            if (_turn == BLACK) {
                return WHITE;
            }
            return BLACK;
        }
        return null;
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {
        return (Piece) board[s.col()][s.row()];
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW <= 9. */
    final Piece get(int col, int row) {
        return (Piece) board[col][row];
    }
    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        board[s.col()][s.row()] = p;
    }

    /** Set square (COL, ROW) to P. */
    final void put(Piece p, int col, int row) {
        board[col][row] = p;
        _winner = EMPTY;
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /** Array of directions. */
    private static final int[][] DIR = {
            { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
            { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };

    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        return false;
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        for (int i = 0; i < 8; i++) {
            if (from.col() + DIR[i][0] > 0 && from.row() + DIR[i][1] > 0) {
                if (get(from.col() + DIR[i][0],
                        from.row() + DIR[i][1]) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        Square point = from;
        if (from == to) {
            return false;
        }
        while (point != to) {
            if (to == null) {
                return false;
            }
            if (from.direction(to) < 0 || from.direction(to) > 7) {
                return false;
            }
            point = point.queenMove(from.direction(to), 1);
            if (get(point.col(), point.row()) != null) {
                return false;
            }
        }
        return true;
    }

    /** Return true iff FROM-TO(SPEAR) is a legal move in the current
     *  position. */
    boolean isLegal(Square from, Square to, Square spear) {
        if (from != spear) {
            return isLegal(from, to) && isLegal(to, spear);
        }
        return isLegal(from, to);

    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        return isLegal(move.from(), move.to(), move.spear());
    }

    /** Move FROM-TO(SPEAR), assuming this is a legal move. */
    void makeMove(Square from, Square to, Square spear) {
        if (isLegal(from, to, spear)) {
            this.put(this.get(from), to);
            this.put(EMPTY, from);
            this.put(SPEAR, spear);
            nummove++;
            if (_turn == WHITE) {
                _turn = BLACK;
            } else {
                _turn = WHITE;
            }
        }
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        makeMove(move.from(), move.to(), move.spear());
    }

    /** Undo one move.  Has no effect on the initial board. */
    void undo() {
        copy(past);
    }

    /** Return an Iterator over the Squares that are reachable by an
     *  unblocked queen move from FROM. Does not pay attention to what
     *  piece (if any) is on FROM, nor to whether the game is finished.
     *  Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     *  feature is useful when looking for Moves, because after moving a
     *  piece, one wants to treat the Square it came from as empty for
     *  purposes of spear throwing.) */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /** Return an Iterator over all legal moves on the current board. */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }

    /** Return an Iterator over all legal moves on the current board for
     *  SIDE (regardless of whose turn it is). */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /** An iterator used by reachableFrom. */
    private class ReachableFromIterator implements Iterator<Square> {

        /** Iterator of all squares reachable by queen move from FROM,
         *  treating ASEMPTY as empty. */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = -1;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            Square a = _from.queenMove(_dir, _steps);
            toNext();
            return a;
        }

        /** Advance _dir and _steps, so that the next valid Square is
         *  _steps steps in direction _dir from _from. */
        private void toNext() {
            while (_dir < 8) {
                if (_dir < 0) {
                    _dir++;
                    _steps++;
                    return;
                }
                while (isLegal(_from, _from.queenMove(_dir, _steps + 1))
                        && _from.queenMove(_dir, _steps + 1).col() >= 0
                        && _from.queenMove(_dir, _steps + 1).row() >= 0
                        && _dir != 8) {
                    _steps++;
                    return;
                }
                _steps = 0;
                _dir++;
            }
        }

        /** Starting square. */
        private Square _from;
        /** Current direction. */
        private int _dir;
        /** Current distance. */
        private int _steps;
        /** Square treated as empty. */
        private Square _asEmpty;
    }

    /** An iterator used by legalMoves. */
    private class LegalMoveIterator implements Iterator<Move> {

        /** All legal moves for SIDE (WHITE or BLACK). */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            _pcount = 0;
            tmp = 0;
            count = 0;
            helper();
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _pcount < 4;
        }

        @Override
        public Move next() {
            Move a = mv(_start, _nextSquare, spear);
            if (!_pieceMoves.hasNext()) {
                while (_spearThrows.hasNext()) {
                    spear = _spearThrows.next();
                    return a;
                }
                if (count == 0) {
                    spear = _start;
                    count++;
                    return a;
                }
                count = 0;
                helper();
                _pcount++;
            }
            if (hasNext()) {
                toNext();
            }
            return a;
        }

        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void toNext() {
            if (_nextSquare == null) {
                _nextSquare = _pieceMoves.next();
                _spearThrows = reachableFrom(_nextSquare, null);
            } else if (!_spearThrows.hasNext()) {
                if (count == 0) {
                    spear = _start;
                    count++;
                    return;
                }
                count = 0;
                _nextSquare = _pieceMoves.next();
                _spearThrows = reachableFrom(_nextSquare, null);
            }
            spear = _spearThrows.next();
        }
        /** Checks the index of the board. */
        private void helper() {
            for (int i = tmp; i < 100; i++) {
                if (get(Square.sq(i)) == _fromPiece) {
                    _start = Square.sq(i);
                    _pieceMoves = reachableFrom(_start, null);
                    tmp = i + 1;
                    break;
                }
            }
        }



        /** Color of side whose moves we are iterating. */
        private Piece _fromPiece;
        /** Current starting square. */
        private Square _start;
        /** Remaining starting squares to consider. */
        private Iterator<Square> _startingSquares;
        /** Current piece's new position. */
        private Square _nextSquare;
        /** Remaining moves from _start to consider. */
        private Iterator<Square> _pieceMoves;
        /** Remaining spear throws from _piece to consider. */
        private Iterator<Square> _spearThrows;
        /** Count of queen. */
        private int _pcount;
        /** Square that saves sqear tmply. */
        private Square spear;
        /** Count of index. */
        private int tmp;
        /** Count of 1 differ. */
        private int count;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 9; i >= 0; i--) {
            str += "  ";
            for (int k = 0; k < 10; k++) {
                if (board[k][i] == null) {
                    str += " -";
                } else {
                    str += " ";
                    str += board[k][i];
                }
            }
            str += "\n";
        }
        return str;
    }

    /** An empty iterator for initialization. */
    private static final Iterator<Square> NO_SQUARES =
        Collections.emptyIterator();

    /** Piece whose turn it is (BLACK or WHITE). */
    private Piece _turn;
    /** Cached value of winner on this board, or EMPTY if it has not been
     *  computed. */
    private Piece _winner;
    /** 2D array board. */
    private Object[][] board;
    /** Count numbers of moves. */
    private int nummove;
    /** Save the past information. */
    private Board past;
}
