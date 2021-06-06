package lists;

/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @author Kidong Kim
 */
class Lists {
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list
     *            ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntListList naturalRuns(IntList L) {

        int count = 0;
        IntList M = new IntList;
        for (M = L; M.tail != null ; M = M.tail) {
            if (M.head > M.tail.head) {
                count++;
            }
        }

        if (L == null) {
            return null;
        }

        if (count == 0) {
            return IntListList.list(L , null);
        }

        else {
            M = L;
            M.head = new
        }






        /* *Replace this body with the solution. */
        while(L.tail != null && L.head < L.tail.head) {

        }
        return null;
    }
}
