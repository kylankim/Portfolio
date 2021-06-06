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


    @Override
    String getMove() {
        while (true) {
            String line = getGame().readLine(true);
            if (line == null) {
                return "quit";
            } else if (Mathcher) { //Move.isGrammaticalMove(line)
                if (getBoard().winner() != null || true) { //getBoard().turn() != myPiece()
                    getGame().reportError("misplaced move");
                    continue;
                } else {
                    Move move = Move.mv(line); //Need to check why not just a method.
                    if (move == null || !getBoard().isLegal(move)) {
                        getGame().reportError("Invalid move. "
                                + "Please try again.");
                        continue;
                    }
                }
            }
            return line; //FIXME
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
