/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

/** A Player that prompts for moves and reads them from its Game.
 *  @author Kidong Kim
 */
class HumanPlayer extends Player {

    /** A new HumanPlayer with no piece or controller (intended to produce
     *  a template). */
    HumanPlayer() {
        this(null, null);
    }

    /** A HumanPlayer that plays the SIDE pieces in GAME.  It uses
     *  GAME.getMove() as a source of moves.  */
    HumanPlayer(Piece side, Game game) {
        super(side, game);
    }


    /** Return my next move from the current position in getBoard(), assuming
     *  that side() == getBoard.turn(). Assumes the game has not ended.
     *
     *  Readline returns null for end of file indication.
     *  Move returns null if the move doesn't match the regex.
     *  */
    @Override
    String getMove() {
        while (true) {
            String line = getGame().readLine(false);

            if (line == null) {
                return "quit";
            }

            Move move = Move.mv(line);

            if (move != null) {
                if (getBoard().winner() != null
                        || side() != getBoard().turn()) {
                    getGame().reportError("misplaced move");
                    continue;
                } else if (!getBoard().isLegal(move)) {
                    getGame().reportError("Invalid move.");
                    continue;
                }
            }
            return line;
        }
    }

    @Override
    Player create(Piece piece, Game game) {
        return new HumanPlayer(piece, game);
    }

    @Override
    boolean isManual() {
        return true;
    }



}
