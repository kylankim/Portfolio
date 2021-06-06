/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.util.ArrayList;
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
     *  What is savemove?
     *  heuristic value?
     *  sense == 1 for maximal value
     *  sense == -1 for minimal value
     *
     *  */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {

        if (depth == 0 || board.winner() != null) {
            return calculate(board);
        }

        int bestVal = 0;

        for (Move tmp: board.legalMoves()) {

            /** No repeated move and reverse of the past move */
            if (!board.get_moves().contains(Move.mv(tmp.getTo(), tmp.getFrom())) && !board.get_moves().contains(tmp)) {
                if (sense == 1) {
                    bestVal = -INFTY;
                    board.makeMove(tmp);

                    int response = findMove(board, depth - 1, true, -1, alpha, beta);
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

                    int response = findMove(board, depth - 1, true, 1, alpha, beta);
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

        return bestVal; //TODO
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

        int my_score = 0;
        int opp_score = 0;
        int my_count = 0;
        int opp_count = 0;
        int my_toConnect = 0;
        int opp_toConnect = 0;

        List<Integer> my_sizes = board.getRegionSizes(board.turn());
        List<Integer> opp_sizes = board.getRegionSizes(board.turn().opposite());

        for (int tmp : my_sizes) {
            my_score += tmp;
            if (my_toConnect < tmp) {
                my_toConnect = tmp;
            }
            if (tmp == 1) {
                my_count++;
            }
        }
        my_score /= my_sizes.size();
        my_toConnect = my_score - my_toConnect;

        for (int tmp : opp_sizes) {
            opp_score += tmp;
            if (tmp > opp_toConnect) {
                opp_toConnect = tmp;
            }
            if (tmp == 1) {
                opp_count++;
            }
        }
        opp_score /= opp_sizes.size();
        opp_toConnect = opp_score - opp_toConnect;

        if (my_score > opp_score) {
            my_score++;
        } else if (opp_score > my_score) {
            opp_score++;
        }

        if (my_sizes.size() > opp_sizes.size()) {
            my_score++;
        } else if (my_sizes.size() < opp_sizes.size()) {
            opp_score++;
        }

        if (my_count > opp_count) {
            my_score++;
        } else if (my_count < opp_count) {
            opp_score++;
        }

        if (my_toConnect < opp_toConnect) {
            my_count++;
        } else if (my_toConnect > opp_toConnect) {
            opp_count++;
        }

        return my_count - opp_count;
    }

    /** Return a search depth for the current position. */
    private int chooseDepth() {
        return 5;  //TODO
    }

    // FIXME: Other methods, variables here.

    /** Used to convey moves discovered by findMove. */
    private Move _foundMove;

}
