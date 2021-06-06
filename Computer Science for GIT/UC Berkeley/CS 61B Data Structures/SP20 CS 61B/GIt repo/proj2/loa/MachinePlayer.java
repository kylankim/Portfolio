/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
//            System.out.println("value : " + value);
        } else {
            value = findMove(work, chooseDepth(), true, -1, -INFTY, INFTY);
//            System.out.println("value : " + value);
        }
        printed.clear();
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
            return readBoard(board);
        }

        int bestVal = 0;
        Board tmpBoard = new Board();
        List<Move> prevMv = new ArrayList<>();
        if (sense == 1) {
            bestVal = -INFTY;
            for (Move tmp : board.legalMoves()) {
                if (board.movesMade() > 16) {
                    prevMv = board.getMoves().subList(board.movesMade() - 16, board.movesMade() - 1);
                }
                Move oppositeMv = Move.mv(tmp.getTo(), tmp.getFrom());
                if (!prevMv.contains(tmp) && !prevMv.contains(oppositeMv) && board.get(tmp.getFrom()) == WP) {
                    tmpBoard.copyFrom(board);
                    tmpBoard.makeMove(tmp);
                    int response = findMove(tmpBoard, depth - 1, false, -1, alpha, beta); // Response of opponent
                    if (response > bestVal) {
                        if (saveMove) {
                            _foundMove = tmp;
                        }
                        bestVal = response;
//                        System.out.println("WP's new best value = " + bestVal + " & Save move = " + saveMove + " & Move = " + tmp.toString());
                        alpha = max(alpha, response);
                    }
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        if (sense == -1) {
            bestVal = INFTY;
            for (Move tmp : board.legalMoves()) {
                if (board.movesMade() > 16) {
                    prevMv = board.getMoves().subList(board.movesMade() - 16, board.movesMade() - 1);
                }
                Move oppositeMv = Move.mv(tmp.getTo(), tmp.getFrom());
                if (!prevMv.contains(tmp) && !prevMv.contains(oppositeMv) && board.get(tmp.getFrom()) == BP) {
                    tmpBoard.copyFrom(board);
                    tmpBoard.makeMove(tmp);
                    int response = findMove(tmpBoard, depth - 1, false, 1, alpha, beta);
                    if (response < bestVal) {
                        if (saveMove) {
                            _foundMove = tmp;
                        }
                        bestVal = response;
//                        System.out.println("BP's new best value = " + bestVal + " & Save move = " + saveMove + " & Move = " + tmp.toString());
                    }
                    beta = min(beta, response);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

//        if (_foundMove != null) {
//            if (!printed.contains(_foundMove)) {
//                printed.add(_foundMove);
//                System.out.println(bestVal + " : " + _foundMove.toString());
//            }
//        }
        return bestVal;
    }

    ArrayList<Move> printed = new ArrayList<>(); //TODO

    //TODO
    /**
     * Need modification of calculation for Black piece.
     * Add additional method for removing the piece that is related to the highest value move of opponent piece
     */

    /**
     * Points for separating opponents group
     *
     *
     * @param board current board
     * @return int current score of the side
     */

    private int readBoard(Board board) {

        if (board.winner() != null) {
            if (board.winner() == board.turn()) {
                return -100000;
            } else if (board.winner() == board.turn().opposite()) {
                return 100000;
            }
        }

        int val = 1000;
        Board current = new Board(), previous = new Board();
        current.copyFrom(board);
        previous.copyFrom(board.get_boards().peek());
        Piece my = board.turn();
        Piece opponent = my.opposite();
        List<Integer> pmyregionsizes = previous.getRegionSizes(my);
        List<Integer> cmyregionsizes = current.getRegionSizes(my);
        List<Integer> popponentregionsizes = previous.getRegionSizes(opponent);
        List<Integer> copponentregionsizes  = current.getRegionSizes(opponent);
        double pmyVariance = calcVariance(previous, my);
        double cmyVariance = calcVariance(current, my);
        double poppVariance = calcVariance(previous, opponent);
        double coppVariance = calcVariance(current, opponent);


        // # of group change
        if (pmyregionsizes.size() > cmyregionsizes.size()) {
            if (copponentregionsizes.size() > popponentregionsizes.size()) {
                val += 5000;
            } else if (copponentregionsizes.size() < popponentregionsizes.size()) {
                val += 3000;
            } else {
                val += 4000;
            }
        } else if (pmyregionsizes.size() < cmyregionsizes.size()) {
            if (copponentregionsizes.size() > popponentregionsizes.size()) {
                val += 4500;
            } else if (copponentregionsizes.size() < popponentregionsizes.size()) {
                val -= 500;
            }
        } else {
            if (copponentregionsizes.size() > popponentregionsizes.size()) {
                val += 3500;
            } else if (copponentregionsizes.size() < popponentregionsizes.size()) {
                val += 500;
            }
        }

        //Comparison btw 2 boards
        val += ((pmyregionsizes.size() - cmyregionsizes.size()) * 999);
        val += ((copponentregionsizes.size() - popponentregionsizes.size()) * 499);
        val += ((copponentregionsizes.size() - cmyregionsizes.size()) * 333);
        val += ((pmyregionsizes.size() - popponentregionsizes.size()) * 333);


        //Compare the variance
        val += ((pmyVariance - cmyVariance) * 350);
        val += ((coppVariance - poppVariance) * 350);

        int pmyAverage = 0;
        int cmyAverage = 0;
        int poppAverage = 0;
        int coppAverage = 0;

        for (int tmp : pmyregionsizes) {
            pmyAverage += tmp;
        }
        for (int tmp : cmyregionsizes) {
            cmyAverage += tmp;
        }
        for (int tmp : popponentregionsizes) {
            poppAverage += tmp;
        }
        for (int tmp : copponentregionsizes) {
            coppAverage += tmp;
        }

        val += ((coppAverage - cmyAverage) * 500); //Current # of pieces
        val += ((pmyAverage - poppAverage) * 150); //Current # of pieces

        if (val < 0) {
            val = 100;
        } else {
            val += 100;
        }

        if ((popponentregionsizes.size() < pmyregionsizes.size()) && (copponentregionsizes.size() > cmyregionsizes.size())) {
            val *= 1.43; // Extra 43% if the move has made reverse winner
        }

        // Value variation by the # of piece and # of group
        if ((cmyAverage > coppAverage) && (cmyregionsizes.size() <= copponentregionsizes.size())) {
            val *= 1.18;
        } else if ((cmyAverage < coppAverage) && (cmyregionsizes.size() >= copponentregionsizes.size())) {
            val *= 0.89;
        } else if ((cmyAverage == coppAverage) && (cmyregionsizes.size() > copponentregionsizes.size())) {
            val *= 1.08;
        } else if ((cmyAverage == coppAverage) && (cmyregionsizes.size() < copponentregionsizes.size())) {
            val *= 0.97;
        }

        //Better to capture when it separates the group but bad when their are limited # of pieces
        if (board.getMoves().get(board.movesMade() - 1).isCapture() && (copponentregionsizes.size() - popponentregionsizes.size()) > 0) { // If the move captures and raised # of groups
            val *= 1.57;
        } else if (board.getMoves().get(board.movesMade() - 1).isCapture() && (copponentregionsizes.size() - popponentregionsizes.size()) < 0) { // If the move captures and reduced # of groups
            val *= 0.86;
        }

        pmyAverage /= pmyregionsizes.size();
        cmyAverage /= cmyregionsizes.size();
        poppAverage /= popponentregionsizes.size();
        coppAverage /= copponentregionsizes.size();

        val += ((cmyAverage - pmyAverage) * 1000);
        val -= ((coppAverage - poppAverage) * 1000);

        if (cmyAverage > pmyAverage) {
            if (poppAverage > coppAverage) {
                val *= 1.27;
            }
            val *= 1.13;
        } else if (cmyAverage < pmyAverage) {
            if (poppAverage > coppAverage) {
                val *= 1.17;
            }
            val *= 0.83;
        }
        return val;
    }

    private double calcVariance(Board board, Piece turn) {

        int meanCol = 0;
        int meanRow = 0;
        int squareCol = 0;
        int squareRow = 0;
        int num = 0;

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                if (board.get(Square.sq(col, row)) == turn) {
                    meanCol += col;
                    meanRow += row;
                    squareCol += (col * col);
                    squareRow += (row * row);
                    num++;
                }
            }
        }
        meanCol /= num;
        meanRow /= num;
        squareCol /= num;
        squareRow /= num;

        int resultCol = squareCol - (meanCol * meanCol);
        int resultRow = squareRow - (meanRow * meanRow);

        return resultCol + resultRow;
    }

    /** Return a search depth for the current position. */
    private int chooseDepth() {
        return 2;
    }

    /** Used to convey moves discovered by findMove. */
    private Move _foundMove;

}
