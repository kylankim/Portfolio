/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import org.junit.Test;

import static loa.Square.BOARD_SIZE;
import static org.junit.Assert.*;

import static loa.Piece.*;
import static loa.Square.sq;
import static loa.Move.mv;

/** Tests of the Board class API.
 *  @author Kidong Kim
 */
public class BoardTest {

    /** A "general" position. */
    static final Piece[][] BOARD1 = {
        { EMP, BP,  EMP,  BP,  BP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP,  BP,  BP, EMP, WP  },
        { WP,  EMP,  BP, EMP, EMP,  WP, EMP, EMP  },
        { WP,  EMP,  WP,  WP, EMP,  WP, EMP, EMP  },
        { WP,  EMP, EMP, EMP,  BP, EMP, EMP, WP  },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP  },
        { EMP, BP,  BP,  BP,  EMP,  BP,  BP, EMP }
    };

    /** A position in which black, but not white, pieces are contiguous. */
    static final Piece[][] BOARD2 = {
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  BP,  BP, EMP, EMP },
        { EMP,  WP,  BP,  WP,  WP, EMP, EMP, EMP },
        { EMP, EMP,  BP,  BP,  WP,  WP, EMP,  WP },
        { EMP,  WP,  WP,  BP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP,  BP, EMP, EMP, EMP, EMP },
    };

    /** A position in which black, but not white, pieces are contiguous. */
    static final Piece[][] BOARD3 = {
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  WP, EMP, EMP, EMP },
        { EMP,  WP,  BP,  WP,  WP, EMP, EMP, EMP },
        { EMP, EMP,  BP,  BP,  WP,  WP,  WP, EMP },
        { EMP,  WP,  WP,  WP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
    };


    static final String BOARD1_STRING =
        "===\n"
        + "    - b b b - b b - \n"
        + "    - - - - - - - - \n"
        + "    w - - - b - - w \n"
        + "    w - w w - w - - \n"
        + "    w - b - - w - - \n"
        + "    w - - - b b - w \n"
        + "    w - - - - - - w \n"
        + "    - b - b b - - - \n"
        + "Next move: black\n"
        + "===";

    /** Test display */
    @Test
    public void toStringTest() {
        Board tmp = new Board(BOARD1, BP);
        System.out.println(tmp.toString());
        System.out.println(BOARD1_STRING);
        assertEquals(BOARD1_STRING, tmp.toString());
        tmp.clear();
        System.out.println(tmp.toString());
        assertEquals(tmp.toString(), new Board().toString());
    }

    @Test
    public void testInitializeCopyFrom() {
        Board e1 = new Board(BOARD2, WP);
        Board e2 = new Board();
        e2.initialize(BOARD3, WP);

        Board e3  = new Board(BOARD3, BP);
        assertNotEquals(e2.toString(), e3.toString());
        e2.copyFrom(e3);
        assertEquals(e3.toString(), e2.toString());

        e2.clear();
        assertEquals(e2.toString(), new Board().toString());

        e2.copyFrom(e1);
        assertEquals(e2.toString(), e1.toString());
    }

    @Test
    public void testlegalMoves() {
        Board tmp = new Board(BOARD1, BP);
        System.out.println(tmp.toString());
        for (Move i : tmp.legalMoves()) {
            System.out.println(i.toString());
        }
        System.out.println();
        tmp = new Board(BOARD1, WP);
        for (Move i : tmp.legalMoves()) {
            System.out.println(i.toString());
        }
    }

    /** Test legal moves. */
    @Test
    public void testLegality1() {
        Board b = new Board(BOARD1, BP);
        assertTrue("f3-d5", b.isLegal(mv("f3-d5")));
        assertTrue("f3-h5", b.isLegal(mv("f3-h5")));
        assertTrue("f3-h1", b.isLegal(mv("f3-h1")));
        assertTrue("f3-b3", b.isLegal(mv("f3-b3")));
        assertFalse("f3-d1", b.isLegal(mv("f3-d1")));
        assertFalse("f3-h3", b.isLegal(mv("f3-h3")));
        assertFalse("f3-e4", b.isLegal(mv("f3-e4")));
        assertFalse("c4-c7", b.isLegal(mv("c4-c7")));
        assertFalse("b1-b4", b.isLegal(mv("b1-b4")));
    }

    /** Test contiguity. */
    @Test
    public void testContiguous1() {
        Board b1 = new Board(BOARD1, BP);
        assertFalse("Board 1 black contiguous?", b1.piecesContiguous(BP));
        assertFalse("Board 1 white contiguous?", b1.piecesContiguous(WP));
        assertFalse("Board 1 game over?", b1.gameOver());
        Board b2 = new Board(BOARD2, BP);
        assertTrue("Board 2 black contiguous?", b2.piecesContiguous(BP));
        assertFalse("Board 2 white contiguous?", b2.piecesContiguous(WP));
        assertTrue("Board 2 game over", b2.gameOver());
        Board b3 = new Board(BOARD3, BP);
        assertTrue("Board 3 white contiguous?", b3.piecesContiguous(BP));
        assertTrue("Board 3 black contiguous?", b3.piecesContiguous(WP));
        assertTrue("Board 3 game over", b3.gameOver());
    }

    @Test
    public void testEquals1() {
        Board b1 = new Board(BOARD1, BP);
        Board b2 = new Board(BOARD1, BP);

        assertEquals("Board 1 equals Board 1", b1, b2);
    }

    @Test
    public void testMove1() {
        Board b0 = new Board(BOARD1, BP);
        Board b1 = new Board(BOARD1, BP);

        b1.makeMove(mv("f3-d5"));
        assertEquals("square d5 after f3-d5", BP, b1.get(sq(4, 5)));
        assertEquals("square f3 after f3-d5", EMP, b1.get(sq(6, 3)));
        assertEquals("Check move count for board 1 after one move",
                     1, b1.movesMade());
        b1.retract();
        assertEquals("Check for board 1 restored after retraction", b0, b1);
        assertEquals("Check move count for board 1 after move + retraction",
                     0, b1.movesMade());
    }

    @Test
    public void testLimit() {
        Board b0 = new Board();
        b0.setMoveLimit(1);
        b0.makeMove(mv("b1-h1"));
        b0.makeMove(mv("a2-b1"));
        assertEquals(EMP, b0.winner());
    }

    @Test
    public void testillegal() {
        Board b0 = new Board();
        assertNull("Wrong regex off the board", mv("b1-g3"));
        assertNull("No piece in the square", mv("b2-h1"));

        // Move called after the limit of moves

    }

    @Test
    public void testcopyForm() {
        Board b0 = new Board(BOARD1, BP);
        Board b1 = new Board(BOARD1, BP);
        Board b2 = new Board(BOARD3, BP);
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            assertEquals(b1.get(sq(i % 8 , i / 8)), b0.get(sq(i % 8 , i / 8)));
        }
        assertEquals(b0.legalMoves().size(), b1.legalMoves().size());
        assertEquals(b0.turn(), b1.turn());
        assertEquals(b0.movesMade(), b1.movesMade());
        assertTrue(b0.getRegionSizes(BP).equals(b1.getRegionSizes(BP)));
        assertTrue(b0.getRegionSizes(WP).equals(b1.getRegionSizes(WP)));
        assertEquals(b0.winner(), b1.winner());

        b0.makeMove(mv("f3-d5"));
        b1.copyFrom(b0);
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            assertEquals(b1.get(sq(i % 8 , i / 8)), b0.get(sq(i % 8 , i / 8)));
        }
        assertEquals(b0.legalMoves().size(), b1.legalMoves().size());
        assertEquals(b0.turn(), b1.turn());
        assertEquals(b0.movesMade(), b1.movesMade());
        assertEquals(b0.winner(), b1.winner());
        assertTrue(b0.getRegionSizes(BP).equals(b1.getRegionSizes(BP)));
        assertTrue(b0.getRegionSizes(WP).equals(b1.getRegionSizes(WP)));

        System.out.println(b2.toString());
        b1.copyFrom(b2);
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            assertEquals("Contents of two boards are different",b1.get(sq(i % 8 , i / 8)), b2.get(sq(i % 8 , i / 8)));
        }
        assertEquals(b2.legalMoves().size(), b1.legalMoves().size());
        assertEquals(b2.turn(), b1.turn());
        assertEquals(b2.movesMade(), b1.movesMade());
        assertEquals(b2.winner(), b1.winner());
        assertTrue(b2.getRegionSizes(BP).equals(b1.getRegionSizes(BP)));
        assertTrue(b2.getRegionSizes(WP).equals(b1.getRegionSizes(WP)));
    }

    @Test
    public void testWinner() {
        Board b0 = new Board(BOARD3, BP);
        Board b2 = new Board(BOARD3, WP);
        assertEquals("Piece of the turn win.", BP, b0.winner());
        assertEquals("Piece of the turn win.", WP, b2.winner());
    }

}
