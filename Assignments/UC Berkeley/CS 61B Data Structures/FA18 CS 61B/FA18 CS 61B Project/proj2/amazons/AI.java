package amazons;


import java.util.Iterator;

import static amazons.Piece.*;

/** A Player that automatically generates moves.
 *  @author Kidong Kim
 */
class AI extends Player {

    /** A position magnitude indicating a win (for white if positive, black
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new AI with no piece or controller (intended to produce
     *  a template). */
    AI() {
        this(null, null);
    }

    /** A new AI playing PIECE under control of CONTROLLER. */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        return new AI(piece, controller);
    }

    @Override
    String myMove() {
        Move move = findMove();
        _controller.reportMove(move);
        return move.toString();
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(board());
        if (_myPiece == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _lastFoundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */
    private int findMove(Board board, int depth, boolean saveMove, int sense,
                         int alpha, int beta) {
        if (depth == 0 || board.winner() != null) {
            return staticScore(board);
        }
        if (sense == 1) {
            int bestVal = Integer.MIN_VALUE;
            Move[] children = new Move[_magic];
            Iterator<Move> l = board.legalMoves(board.turn());
            for (int i = 0; l.hasNext(); i++) {
                children[i] = l.next();
            }
            for (Move k : children) {
                Board abc = board;
                abc.makeMove(k);
                int a =  Math.max(bestVal, findMove(abc, depth - 1,
                        false, -1, alpha, beta));
                if (bestVal > beta && saveMove) {
                    _lastFoundMove = k;
                    return bestVal;
                }
                alpha = Math.max(alpha, bestVal);
            }
            return bestVal;
        } else {
            int bestVal = Integer.MAX_VALUE;
            Move[] children = new Move[_magic];
            Iterator<Move> l = board.legalMoves(board.turn());
            for (int i = 0; l.hasNext(); i++) {
                children[i] = l.next();
            }
            for (Move k : children) {
                Board abc = board;
                abc.makeMove(k);
                int a =  Math.max(bestVal, findMove(abc, depth - 1,
                        false, -1, alpha, beta));
                if (bestVal < alpha  && saveMove) {
                    _lastFoundMove = k;
                    return bestVal;
                }
                beta = Math.min(beta, bestVal);
            }
            return bestVal;
        }
    }
    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private int maxDepth(Board board) {
        int N = board.numMoves();
        return 2;
    }


    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {
        Piece winner = board.winner();
        if (winner == BLACK) {
            return -WINNING_VALUE;
        } else if (winner == WHITE) {
            return WINNING_VALUE;
        }

        int count1 = 0;
        int count2 = 0;

        Iterator<Move> l = board.legalMoves(board.turn());
        while (l.hasNext()) {
            count1++;
        }

        Iterator<Move> l2 = board.legalMoves(board.turn().opponent());
        while (l2.hasNext()) {
            count1++;
        }

        return count1 - count2;
    }
    /** private static int _MagicNumber = 97. */
    private final int _magic = 2200;
}
