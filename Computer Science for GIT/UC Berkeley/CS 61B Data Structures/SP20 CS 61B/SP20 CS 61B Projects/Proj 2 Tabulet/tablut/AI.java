package tablut;

import static java.lang.Math.*;

import static tablut.Piece.*;


/** A Player that automatically generates moves.
 *  @author Justin Cho
 */
class AI extends Player {

    /** A position-score magnitude indicating a win (for white if positive,
     *  black if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A position-score magnitude indicating a forced win in a subsequent
     *  move.  This differs from WINNING_VALUE to avoid putting off wins. */
    private static final int WILL_WIN_VALUE = Integer.MAX_VALUE - 40;
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
        Move myMove = findMove();
        _controller.reportMove(myMove);
        return myMove.toString();
    }

    @Override
    boolean isManual() {
        return false;
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. (minimax) */
    private Move findMove() {
        Board b = new Board(board());
        if (b.turn() == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else if (b.turn() == BLACK) {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /** The move found by the last call to one of the ...FindMove methods
     *  below. */
    private Move _lastFoundMove;

    /** Find a move from position BOARD and return its value, recording
     *  the move found into _lastFoundMove iff SAVEMOVE.
     *
     *  The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.
     *
     *  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _lastMoveFound. */

    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {
        if (depth == 0 || board.winner() != null) {
            return staticScore(board);
        }
        int bestSoFar = 0;
        for (Move test: board.legalMoves(board.turn())) {
            if (sense == 1) {
                bestSoFar = -INFTY;
                board.makeMove(test);

                int response = findMove(board, depth - 1,
                        false, -1, alpha, beta);
                board.undo();
                if (response >= bestSoFar) {
                    if (saveMove) {
                        _lastFoundMove = test;
                    }
                    bestSoFar = response;
                    alpha = max(alpha, response);
                }
                if (beta <= alpha) {
                    break;
                }
            }

            if (sense == -1) {
                bestSoFar = INFTY;
                board.makeMove(test);

                int response = findMove(board, depth - 1,
                        false, 1, alpha, beta);
                board.undo();

                if (response <= bestSoFar) {
                    if (saveMove) {
                        _lastFoundMove = test;
                    }
                    bestSoFar = response;
                }
                beta = min(beta, response);

                if (beta <= alpha) {
                    break;
                }
            }
        }

        return bestSoFar;

    }

    /** Return a heuristically determined maximum search depth
     *  based on characteristics of BOARD. */
    private static int maxDepth(Board board) {
        return 1;
    }

    /** Return a heuristic value for BOARD. */
    private int staticScore(Board board) {

        if (board.kingPosition() == null) {
            return -INFTY;
        }

        if (board.kingPosition().isEdge()) {
            return WINNING_VALUE;
        }

        return 0;
    }
}
