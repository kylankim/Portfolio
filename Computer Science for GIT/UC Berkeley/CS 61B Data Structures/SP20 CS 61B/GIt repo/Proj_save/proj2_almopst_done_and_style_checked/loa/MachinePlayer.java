/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static loa.Piece.*;

/** An automated Player.
 *  @author Kidong Kim
 */
class MachinePlayer extends Player {

    /** A position-score magnitude indicating a win (for white if positive,
     *  black if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    /** A new MachinePlayer with no piece or controller (intended to produce
     *  a template). */
    MachinePlayer() {
        this(null, null);
    }

    /** A MachinePlayer that plays the SIDE pieces in GAME. */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
    }

    @Override
    String getMove() {
        Move choice;

        assert side() == getGame().getBoard().turn();
        int depth;
        choice = searchForMove();
        getGame().reportMove(choice);
        return choice.toString();
    }

    @Override
    Player create(Piece piece, Game game) {
        return new MachinePlayer(piece, game);
    }

    @Override
    boolean isManual() {
        return false;
    }

    /** Return a move after searching the game tree to DEPTH>0 moves
     *  from the current position. Assumes the game is not over. */
    private Move searchForMove() {
        Board work = new Board(getBoard());
        int value;
        assert side() == work.turn();
        _foundMove = null;
        if (side() == WP) {
            value = findMove(work, chooseDepth(), true, 1, -INFTY, INFTY);
        } else {
            value = findMove(work, chooseDepth(), true, -1, -INFTY, INFTY);
        }
        return _foundMove;
    }

    /** Find a move from position BOARD and return its value, recording
     *  the move found in _foundMove iff SAVEMOVE. The move
     *  should have maximal value or have value > BETA if SENSE==1,
     *  and minimal value or value < ALPHA if SENSE==-1. Searches up to
     *  DEPTH levels.  Searching at level 0 simply returns a static estimate
     *  of the board value and does not set _foundMove. If the game is over
     *  on BOARD, does not set _foundMove.
     *
     *  No repeated move and reverse of the past move
     *  */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {

        if (depth == 0 || board.winner() != null) {
            return calculate(board);
        }

        int bestVal = 0;

        for (Move tmp: board.legalMoves()) {
            if (!board.getMoves().contains(Move.mv(tmp.getTo(),
                    tmp.getFrom())) && !board.getMoves().contains(tmp)) {
                if (sense == 1) {
                    bestVal = -INFTY;
                    board.makeMove(tmp);

                    int response = findMove(board, depth - 1,
                            true, -1, alpha, beta);
                    board.retract();

                    if (response > bestVal) {
                        if (saveMove) {
                            _foundMove = tmp;
                        }
                        bestVal = response;
                        alpha = max(alpha, response);
                    }

                    if (beta <= alpha) {
                        break;
                    }
                }

                if (sense == -1) {
                    bestVal = INFTY;
                    board.makeMove(tmp);

                    int response = findMove(board, depth - 1,
                            true, 1, alpha, beta);
                    board.retract();

                    if (response < bestVal) {
                        if (saveMove) {
                            _foundMove = tmp;
                        }
                        bestVal = response;
                    }
                    beta = min(beta, response);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return bestVal;
    }

    /** Comparison btw average size of groups */
    /** Comparison btw # of groups */
    /** Comparison btw # of small groups */
    /** Comparison btw # of pieces to connect more */

    /** Higher value for better condition to win.
     * @param board current board
     * @return int heuristic value
     */
    private int calculate(Board board) {
        if (board.winner() != null) {
            if (board.winner() == board.turn()) {
                return 10;
            } else if (board.winner() == board.turn().opposite()) {
                return -10;
            }
        }
        int myScore = 0;
        int oppScore = 0;
        int myCount = 0;
        int oppCount = 0;
        int myToConnect = 0;
        int oppToConnect = 0;
        List<Integer> mySizes = board.getRegionSizes(board.turn());
        List<Integer> oppSizes = board.getRegionSizes(board.turn().opposite());
        for (int tmp : mySizes) {
            myScore += tmp;
            if (myToConnect < tmp) {
                myToConnect = tmp;
            }
            if (tmp == 1) {
                myCount++;
            }
        }
        myScore /= mySizes.size();
        myToConnect = myScore - myToConnect;
        for (int tmp : oppSizes) {
            oppScore += tmp;
            if (tmp > oppToConnect) {
                oppToConnect = tmp;
            }
            if (tmp == 1) {
                oppCount++;
            }
        }
        oppScore /= oppSizes.size();
        oppToConnect = oppScore - oppToConnect;
        if (myScore > oppScore) {
            myScore++;
        } else if (oppScore > myScore) {
            oppScore++;
        }
        if (mySizes.size() > oppSizes.size()) {
            myScore++;
        } else if (mySizes.size() < oppSizes.size()) {
            oppScore++;
        }
        if (myCount > oppCount) {
            myScore++;
        } else if (myCount < oppCount) {
            oppScore++;
        }
        if (myToConnect < oppToConnect) {
            myCount++;
        } else if (myToConnect > oppToConnect) {
            oppCount++;
        }
        return myCount - oppCount;
    }

    /** Return a search depth for the current position. */
    private int chooseDepth() {
        return 5;
    }

    /** Used to convey moves discovered by findMove. */
    private Move _foundMove;

}
