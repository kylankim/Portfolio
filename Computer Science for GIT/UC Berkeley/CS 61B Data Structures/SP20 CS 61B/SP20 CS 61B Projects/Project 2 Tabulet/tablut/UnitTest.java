package tablut;

import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

import java.util.ArrayList;
import java.util.List;

/** The suite of all JUnit tests for the enigma package.
 *  @author Justin Cho
 */
public class UnitTest {

    /** Run the JUnit tests in this package. Add xxxTest.class entries to
     *  the arguments of runClasses to run other JUnit tests. */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class);
    }

    @Test
    public void printTest() {
        Board b = new Board();
        System.out.println(b.encodedBoard());
    }
    @Test
    public void checkRepeatedTest() {
        Board b = new Board();
        ArrayList<String> positions = new ArrayList<String>();
        positions.add(b.encodedBoard());
        b.makeMove(Move.mv("i6-7"));

        positions.add(b.encodedBoard());
        b.makeMove(Move.mv("f5-6"));

        positions.add(b.encodedBoard());
        b.makeMove(Move.mv("i7-6"));

        positions.add(b.encodedBoard());
        b.makeMove(Move.mv("f6-5"));

        positions.add(b.encodedBoard());


        for (String bap: positions) {
            if (bap.equals(b.encodedBoard())) {
                System.out.println(bap);
            } else {
                System.out.println(bap);
            }
        }

    }
    @Test
    public void undoBoardTest() {
        Board b = new Board();
        b.makeMove(Move.mv("i6-7"));
        b.makeMove(Move.mv("g5-6"));
        b.makeMove(Move.mv("h5-9"));
        b.makeMove(Move.mv("g6-7"));
        b.makeMove(Move.mv("i7-8"));
        System.out.println(b);
        b.undo();
        System.out.println(b);

        b.undo();
        System.out.println(b);

        b.undo();
        System.out.println(b);

        b.undo();
        System.out.println(b);

        b.undo();
        System.out.println(b);
    }

    @Test
    public void copyTest() {
        Board b = new Board();
        Board c = new Board();
        Piece[][] practice = {
                {E, E, E, B, B, B, E, E, E},
                {E, E, E, E, B, E, E, E, E},
                {E, E, E, E, W, E, E, E, E},
                {B, E, E, E, W, E, E, E, B},
                {B, B, W, W, K, W, W, B, B},
                {B, E, E, E, W, E, E, E, B},
                {E, E, E, E, W, E, E, E, E},
                {E, E, E, E, B, E, E, E, E},
                {E, E, E, B, B, B, E, E, E},
        };
        buildBoard(c, practice);
        b.copy(c);
        System.out.println(b.encodedBoard());
        System.out.println(c);
    }
    @Test
    public void testLegalWhiteMoves() {
        Board b = new Board();
        List<Move> movesList = b.legalMoves(Piece.WHITE);

        assertEquals(56, movesList.size());

        assertFalse(movesList.contains(Move.mv("e7-8")));
        assertFalse(movesList.contains(Move.mv("e8-f")));

        assertTrue(movesList.contains(Move.mv("e6-f")));
        assertTrue(movesList.contains(Move.mv("f5-8")));
    }

    /**
     * Tests legalMoves for black pieces to make sure it returns allLegal Moves.
     * This method needs to be finished and may need to be changed
     * based on your implementation.
     */
    @Test
    public void testLegalBlackMoves() {
        Board b = new Board();
        List<Move> movesList = b.legalMoves(Piece.BLACK);

        assertEquals(80, movesList.size());

        assertFalse(movesList.contains(Move.mv("e8-7")));
        assertFalse(movesList.contains(Move.mv("e7-8")));

        assertTrue(movesList.contains(Move.mv("f9-i")));
        assertTrue(movesList.contains(Move.mv("h5-1")));
    }

    private void buildBoard(Board b, Piece[][] target) {
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = Board.SIZE - 1; row >= 0; row--) {
                Piece piece = target[Board.SIZE - 1 - row][col];
                b.put(piece, Square.sq(col, row));
            }
        }
        System.out.println(b);
    }

    static final Piece E = Piece.EMPTY;
    static final Piece W = Piece.WHITE;
    static final Piece B = Piece.BLACK;
    static final Piece K = Piece.KING;

    static final Piece[][] INITIALBOARDSTATE = {
            {E, E, E, B, B, B, E, E, E},
            {E, E, E, E, B, E, E, E, E},
            {E, E, E, E, W, E, E, E, E},
            {B, E, E, E, W, E, E, E, B},
            {B, B, W, W, K, W, W, B, B},
            {B, E, E, E, W, E, E, E, B},
            {E, E, E, E, W, E, E, E, E},
            {E, E, E, E, B, E, E, E, E},
            {E, E, E, B, B, B, E, E, E},
    };
}


