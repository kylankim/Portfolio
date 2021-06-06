package tablut;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Stack;


import static tablut.Piece.*;
import static tablut.Square.*;
import static tablut.Move.mv;

import java.util.HashSet;


/** The state of a Tablut Game.
 *  @author Justin Cho
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 9;

    /** The throne (or castle) square and its four surrounding squares.. */
    static final Square THRONE = sq(4, 4),
        NTHRONE = sq(4, 5),
        STHRONE = sq(4, 3),
        WTHRONE = sq(3, 4),
        ETHRONE = sq(5, 4);

    /** Initial positions of attackers. */
    static final Square[] INITIAL_ATTACKERS = {
        sq(0, 3), sq(0, 4), sq(0, 5), sq(1, 4),
        sq(8, 3), sq(8, 4), sq(8, 5), sq(7, 4),
        sq(3, 0), sq(4, 0), sq(5, 0), sq(4, 1),
        sq(3, 8), sq(4, 8), sq(5, 8), sq(4, 7)
    };

    /** Initial positions of defenders of the king. */
    static final Square[] INITIAL_DEFENDERS = {
        NTHRONE, ETHRONE, STHRONE, WTHRONE,
        sq(4, 6), sq(4, 2), sq(2, 4), sq(6, 4)
    };

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
        if (model == this) {
            return;
        }
        init();
        this._winner = model._winner;
        this._turn = model._turn;
        this._moveCount = model._moveCount;
        this._repeated = model._repeated;
        this._revPutpos = model._revPutpos;
        this._lim = model._lim;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                this._board[row][col] = model.get(col, row);
            }
        }

        for (String bap: model.checkrepeat) {
            this.checkrepeat.add(bap);
        }

        Stack<Board> temp = new Stack<Board>();

        while (!model._positions.isEmpty()) {
            temp.push(model._positions.pop());
        }

        while (!temp.isEmpty()) {
            _positions.push(temp.pop());
        }
    }

    /** Clears the board to the initial position. */
    void init() {
        _board = new Piece[SIZE][SIZE];
        _turn = BLACK;
        _winner = null;
        _moveCount = 0;
        _lim = -1;
        _repeated = false;
        clearUndo();
        checkrepeat.clear();


        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                for (Square def: INITIAL_DEFENDERS) {
                    if (def.row() == row && def.col() == col) {
                        _board[row][col] = WHITE;
                    }
                }
                for (Square atk: INITIAL_ATTACKERS) {
                    if (atk.row() == row && atk.col() == col) {
                        _board[row][col] = BLACK;
                    }
                }
                if (THRONE.row() == row && THRONE.col() == col) {
                    _board[row][col] = KING;
                }
                if (_board[row][col] == null) {
                    _board[row][col] = EMPTY;
                }
            }
        }
        _positions.push(this);
    }

    /** Set the move limit to LIM.  It is an error if 2*LIM <= moveCount().
     * @param n is the _limit*/
    void setMoveLimit(int n) {
        _lim = n;

        if (2 * _lim <= moveCount()) {
            throw new Error("You passed your limit");
        }
    }

    /** Return a Piece representing whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Return the winner in the current position, or null if there is no winner
     *  yet. */
    Piece winner() {
        return _winner;
    }

    /** Returns true iff this is a win due to a repeated position. */
    boolean repeatedPosition() {
        return _repeated;
    }

    /** Record current position and set winner() next mover if the current
     *  position is a repeat. */
    private void checkRepeated() {
        boolean contains = checkrepeat.contains(encodedBoardRepeated());
        if (contains) {
            _repeated = true;
        }

        if (_repeated) {
            _winner = turn().opponent();
        }
    }

    /** Return the number of moves since the initial position that have not been
     *  undone. */
    int moveCount() {
        return _moveCount;
    }

    /** Return location of the king. */
    Square kingPosition() {
        Square kingsPosition = null;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (_board[row][col] == KING) {
                    kingsPosition = sq(col, row);
                }
            }
        }
        return kingsPosition;
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {
        return get(s.col(), s.row());
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW < 9. */
    final Piece get(int col, int row) {
        return _board[row][col];
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set p to Square s.
     * @param p is the piece
     * @param s is the square*/
    final void put(Piece p, Square s) {
        _board[s.row()][s.col()] = p;
    }

    /** Set square S to P and record for undoing. Undoing in this case
     * means that we are saving a previous position in case we want to
     * undo a position. Put will not have a record only when the game
     * is first initialized*/
    final void revPut(Piece p, Square s) {

        put(p, s);
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, sq(col - 'a', row - '1'));
    }

    /** Can king win the game for WHITE?
     * @return returns boolean of whether white can win */
    boolean kingNextWin() {
        ArrayList<Square> edges = new ArrayList<Square>();
        for (int row = 0; row < 9; row++) {
            edges.add(sq(0, row));
            edges.add(sq(8, row));
        }
        for (int col = 0; col < 9; col++) {
            edges.add(sq(col, 0));
            edges.add(sq(col, 8));
        }
        for (Square compare: edges) {
            if (isUnblockedMove(kingPosition(), compare)) {
                return true;
            }
        }
        return false;
    }
    /** Return true iff FROM - TO is an unblocked rook move on the current
     *  board.  For this to be true, FROM-TO must be a rook move and the
     *  squares along it, other than FROM, must be empty. */
    boolean isUnblockedMove(Square from, Square to) {
        int checkCol = Math.abs(from.col() - to.col());
        int checkRow = Math.abs(from.row() - to.row());
        if (from != to) {
            if (checkCol == 0 && from.row() < to.row()) {
                for (int i = from.row() + 1; i <= to.row(); i++) {
                    if (!(_board[i][to.col()] == EMPTY)) {
                        return false;
                    }
                }
                return true;
            } else if (checkCol == 0 && from.row() > to.row()) {
                for (int j = from.row() - 1; j >= to.row(); j--) {
                    if (!(_board[j][to.col()] == EMPTY)) {
                        return false;
                    }
                }
                return true;
            } else if (checkRow == 0 && from.col() < to.col()) {
                for (int a = from.col() + 1; a <= to.col(); a++) {
                    if (!(_board[to.row()][a] == EMPTY)) {
                        return false;
                    }
                }
                return true;
            } else if (checkRow == 0 && from.col() > to.col()) {
                for (int b = from.col() - 1; b >= to.col(); b--) {
                    if (!(_board[to.row()][b] == EMPTY)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        return get(from).side() == _turn;
    }

    /** Return true iff FROM-TO is a valid move. */
    boolean isLegal(Square from, Square to) {
        if (isLegal(from) && isUnblockedMove(from, to)) {
            return true;
        }
        return false;
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        return isLegal(move.from(), move.to());
    }

    /** Move FROM-TO, assuming this is a legal move. */
    void makeMove(Square from, Square to) {
        if (isLegal(from, to)) {
            Board newBoard = new Board();
            newBoard.copy(this);
            _positions.push(newBoard);

            checkrepeat.add(encodedBoardRepeated());

            revPut(_board[from.row()][from.col()], to);
            put(EMPTY, from);

            if (kingPosition() == null) {
                _winner = BLACK;
            } else if (kingPosition().isEdge()) {
                _winner = WHITE;
            }

            if (to.col() + 2 < 9) {
                Square east = sq(to.col() + 2, to.row());
                capture(to, east);
            }

            if (to.col() - 2 >= 0) {
                Square west = sq(to.col() - 2, to.row());
                capture(to, west);
            }

            if (to.row() - 2 >= 0) {
                Square south = sq(to.col(), to.row() - 2);
                capture(to, south);
            }

            if (to.row() + 2 < 9) {
                Square north = sq(to.col(), to.row() + 2);
                capture(to, north);
            }

            checkRepeated();

            if (_turn == WHITE) {
                _turn = BLACK;
            } else {
                _turn = WHITE;
            }
            _moveCount += 1;

            if (_lim != -1) {
                if (_lim * 2 <= _moveCount) {
                    _winner = turn().opponent();
                }
            }
        }
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        makeMove(move.from(), move.to());
    }

    /** Function that makes the King hostile to both white and black.
     * @param sq0 is first square
     * @param sq2 is second square*/
    private void captureHostileKing(Square sq0, Square sq2) {
        if (sq0 == THRONE || sq2 == THRONE) {
            Square middle = sq0.between(sq2);
            if (get(middle) != KING) {
                if (get(THRONE.col(), THRONE.row()) == EMPTY
                        && (get(middle) != get(sq0)
                        && get(middle) != get(sq2))) {
                    put(EMPTY, middle);
                }
            }
        }
    }
    /** Function that makes the Throne hostile.
     * @param sq0 is first square
     * @param sq2 is second square*/
    private void captureHostileWhite(Square sq0, Square sq2) {
        Piece northT = _board[NTHRONE.row()][NTHRONE.col()];
        Piece southT = _board[STHRONE.row()][STHRONE.col()];
        Piece westT = _board[WTHRONE.row()][WTHRONE.col()];
        Piece eastT = _board[ETHRONE.row()][ETHRONE.col()];
        ArrayList<Piece> border = new ArrayList<>();

        int count = 0;

        border.add(northT);
        border.add(southT);
        border.add(westT);
        border.add(eastT);

        for (Piece surround: border) {
            if (surround == BLACK) {
                count += 1;
            }
        }
        if (count >= 3 && kingPosition() == THRONE) {
            Square middle = sq0.between(sq2);
            if (sq0 == THRONE) {
                if (get(middle.col(), middle.row()) == WHITE
                        && get(sq2.col(), sq2.row()) == BLACK) {
                    put(EMPTY, middle);
                }
            } else if (sq2 == THRONE) {
                if (get(middle.col(), middle.row()) == WHITE
                        && get(sq0.col(), sq0.row()) == BLACK) {
                    put(EMPTY, middle);
                }
            }
        }
    }

    /** Function that captures king in throne.
     * @param sq0 is first square
     * @param sq2 is second square*/
    public void captureKingThrone(Square sq0, Square sq2) {
        Piece north = get(NTHRONE.col(), NTHRONE.row());
        Piece south = get(STHRONE.col(), STHRONE.row());
        Piece west = get(WTHRONE.col(), WTHRONE.row());
        Piece east = get(ETHRONE.col(), ETHRONE.row());

        if (north == BLACK
                && south == BLACK
                && west == BLACK
                && east == BLACK) {
            put(EMPTY, THRONE);
        }
    }

    /** Function that captures king adjacent to throne.
     * @param sq0 is first square
     * @param sq2 is second square*/
    public void captureKingThroneAdj(Square sq0, Square sq2) {
        Square kingPos = sq0.between(sq2);
        Piece east = get(kingPos.col() + 1, kingPos.row());
        Piece west = get(kingPos.col() - 1, kingPos.row());
        Piece south = get(kingPos.col(), kingPos.row() - 1);
        Piece north = get(kingPos.col(), kingPos.row() + 1);

        Square eastSq = sq(kingPos.col() + 1, kingPos.row());
        Square westSq = sq(kingPos.col() - 1, kingPos.row());
        Square southSq = sq(kingPos.col(), kingPos.row() - 1);
        Square northSq = sq(kingPos.col(), kingPos.row() + 1);


        if ((north == BLACK || northSq == THRONE)
                && (south == BLACK || southSq == THRONE)
                && (west == BLACK || westSq == THRONE)
                && (east == BLACK || eastSq == THRONE)) {
            put(EMPTY, kingPos);
        }
    }

    /** Capture the piece between SQ0 and SQ2, assuming a piece just moved to
     *  SQ0 and the necessary conditions are satisfied. */
    private void capture(Square sq0, Square sq2) {
        captureHostileKing(sq0, sq2);
        if (sq0 == THRONE || sq2 == THRONE) {
            captureHostileWhite(sq0, sq2);
        }
        if (sq0.between(sq2) == kingPosition() && kingPosition() == THRONE) {
            captureKingThrone(sq0, sq2);
        }
        if (sq0.between(sq2) == kingPosition() && (kingPosition() == NTHRONE
                || kingPosition() == WTHRONE || kingPosition() == STHRONE
                || kingPosition() == ETHRONE)) {
            captureKingThroneAdj(sq0, sq2);
        }
        Piece first = _board[sq0.row()][sq0.col()];
        Piece second = _board[sq2.row()][sq2.col()];

        Square middle = sq0.between(sq2);
        if (middle != THRONE
                && (get(NTHRONE.col(), NTHRONE.row()) != KING
                || middle != kingPosition())
                && (get(STHRONE.col(), STHRONE.row()) != KING
                || middle != kingPosition())
                && (get(WTHRONE.col(), WTHRONE.row()) != KING
                || middle != kingPosition())
                && (get(ETHRONE.col(), ETHRONE.row()) != KING
                || middle != kingPosition())) {
            if (first == second || (first == WHITE
                    && second == KING && get(middle) != WHITE)
                    || (second == WHITE && first == KING
                    && get(middle) != WHITE)) {
                if (sq0.col() == sq2.col()) {
                    if (sq0.row() > sq2.row() && sq0.row() - 2 == sq2.row()) {
                        if (first != _board[sq0.row() - 1][sq0.col()]) {
                            _board[sq0.row() - 1][sq0.col()] = EMPTY;
                        }
                    } else if (sq0.row() < sq2.row()
                            && sq2.row() - 2 == sq0.row()) {
                        if (first != _board[sq2.row() - 1][sq0.col()]) {
                            _board[sq2.row() - 1][sq0.col()] = EMPTY;
                        }
                    }
                } else if (sq0.row() == sq2.row()) {
                    if (sq0.col() > sq2.col() && sq0.col() - 2 == sq2.col()) {
                        if (first != _board[sq0.row()][sq0.col() - 1]) {
                            _board[sq0.row()][sq0.col() - 1] = EMPTY;
                        }
                    } else if (sq2.col() > sq0.col()
                            && sq2.col() - 2 == sq0.col()) {
                        if (first != _board[sq0.row()][sq2.col() - 1]) {
                            _board[sq0.row()][sq2.col() - 1] = EMPTY;
                        }
                    }
                }
            }
        }

        if (kingPosition() == null) {
            _winner = BLACK;
        }
    }

    /** Undo one move.  Has no effect on the initial board. */
    void undo() {
        if (true) {
            undoPosition();
        }
    }

    /** Remove record of current position in the set of positions encountered,
     *  unless it is a repeated position or we are at the first move.
     *  A stack would be helpful.*/
    private void undoPosition() {
        checkRepeated();
        if (!repeatedPosition()) {
            copy(_positions.pop());
        }
        _moveCount -= 1;
    }

    /** Clear the undo stack and board-position counts. Does not modify the
     *  current position or win status. Board-position counts mean in this case
     *  the previous positions (could be in a set).*/
    void clearUndo() {
        _positions.clear();

    }

    /** Return a new mutable list of all legal moves on the current board for
     *  SIDE (ignoring whose turn it is at the moment). */
    List<Move> legalMoves(Piece side) {
        List<Move> allLegalMoves = new ArrayList<Move>();
        Piece temp = _turn;
        _turn = side;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (_board[row][col] == side) {
                    Square sqFrom = sq(col, row);
                    for (int i = 1; i < 9; i++) {
                        if (row + i < 9 && isLegal(sqFrom, sq(col, row + i))) {
                            allLegalMoves.add(mv(sqFrom, sq(col, row + i)));
                        }
                        if (row - i >= 0 && isLegal(sqFrom, sq(col, row - i))) {
                            allLegalMoves.add(mv(sqFrom, sq(col, row - i)));
                        }
                        if (col + i < 9 && isLegal(sqFrom, sq(col + i, row))) {
                            allLegalMoves.add(mv(sqFrom, sq(col + i, row)));
                        }
                        if (col - i >= 0 && isLegal(sqFrom, sq(col - i, row))) {
                            allLegalMoves.add(mv(sqFrom, sq(col - i, row)));
                        }
                    }
                }
            }
        }
        if (side == WHITE) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (_board[row][col] == KING) {
                        Square sqFrom = sq(col, row);
                        for (int i = 1; i < 9; i++) {
                            if (row + i < 9 && isLegal(sqFrom,
                                    sq(col, row + i))) {
                                allLegalMoves.add(mv(sqFrom, sq(col, row + i)));
                            }
                            if (row - i >= 0 && isLegal(sqFrom,
                                    sq(col, row - i))) {
                                allLegalMoves.add(mv(sqFrom, sq(col, row - i)));
                            }
                            if (col + i < 9 && isLegal(sqFrom,
                                    sq(col + i, row))) {
                                allLegalMoves.add(mv(sqFrom, sq(col + i, row)));
                            }
                            if (col - i >= 0 && isLegal(sqFrom,
                                    sq(col - i, row))) {
                                allLegalMoves.add(mv(sqFrom, sq(col - i, row)));
                            }
                        }
                    }
                }
            }
        }
        _turn = temp;

        if (allLegalMoves.isEmpty()) {
            _winner = _turn.opponent();
        }
        return allLegalMoves;
    }

    /** Return true iff SIDE has a legal move. */
    boolean hasMove(Piece side) {
        return false;
    }

    @Override
    public String toString() {
        return toString(true);
    }

    /** Return a text representation of this Board.  If COORDINATES, then row
     *  and column designations are included along the left and bottom sides.
     */
    String toString(boolean coordinates) {
        Formatter out = new Formatter();
        for (int r = SIZE - 1; r >= 0; r -= 1) {
            if (coordinates) {
                out.format("%2d", r + 1);
            } else {
                out.format("  ");
            }
            for (int c = 0; c < SIZE; c += 1) {
                out.format(" %s", get(c, r));
            }
            out.format("%n");
        }
        if (coordinates) {
            out.format("  ");
            for (char c = 'a'; c <= 'i'; c += 1) {
                out.format(" %c", c);
            }
            out.format("%n");
        }
        return out.toString();
    }

    /** Return the locations of all pieces on SIDE. */
    public HashSet<Square> pieceLocations(Piece side) {
        assert side != EMPTY;
        HashSet<Square> locations = new HashSet<Square>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (_board[row][col] == side) {
                    locations.add(sq(col, row));
                }
                if (side == WHITE && _board[row][col] == KING) {
                    locations.add(sq(col, row));
                }
            }
        }
        return locations;
    }

    /** Return the contents of _board in the order of SQUARE_LIST as a sequence
     *  of characters: the toString values of the current turn and Pieces. */
    String encodedBoard() {
        char[] result = new char[Square.SQUARE_LIST.size() + 1];
        result[0] = turn().toString().charAt(0);
        for (Square sq : SQUARE_LIST) {
            result[sq.index() + 1] = get(sq).toString().charAt(0);
        }
        return new String(result);
    }

    /** Function specifically coded for checkrepeated.
     * @return encodedboard without the turn*/
    String encodedBoardRepeated() {
        char[] result = new char[Square.SQUARE_LIST.size()];
        for (Square sq : SQUARE_LIST) {
            result[sq.index()] = get(sq).toString().charAt(0);
        }
        return new String(result);
    }

    /** returns ratio of black pieces to white. */
    public int ratio() {
        int numBlack = pieceLocations(BLACK).size();
        int numWhite = 2 * pieceLocations(WHITE).size();
        return numWhite - numBlack;
    }

    /** Piece whose turn it is (WHITE or BLACK). */
    private Piece _turn;
    /** Cached value of winner on this board, or null if it has not been
     *  computed. */
    private Piece _winner;
    /** Number of (still undone) moves since initial position. */
    private int _moveCount;
    /** True when current board is a repeated position (ending the game). */
    private boolean _repeated;

    /** The board. */
    private Piece[][] _board = new Piece[SIZE][SIZE];
    /** A stack of all positions after each turn. */
    private Stack<Board> _positions = new Stack<Board>();
    /** Place to store for _revPut. */
    private Piece[][] _revPutpos;

    /** Limit. */
    private int _lim = -1;

    /** Extra checkrepeat ArrayList. */
    private ArrayList<String> checkrepeat = new ArrayList<String>();
}
