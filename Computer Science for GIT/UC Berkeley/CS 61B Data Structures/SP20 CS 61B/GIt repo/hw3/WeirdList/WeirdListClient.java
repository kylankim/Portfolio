import net.sf.saxon.functions.Empty;

/** Functions to increment and sum the elements of a WeirdList. */
class WeirdListClient {

    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
<<<<<<< HEAD
        return L.map(new IntUnaryFunction() {
            @Override
            public int apply(int x) {
                return x+n;
            }
        });
=======
        return L.map(x -> x + n);
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
    }

    /** Return the sum of all the elements in L. */
    static int sum(WeirdList L) {
<<<<<<< HEAD
        return L.getHelper().sum;
=======
        final int[] sum = {0};
        L.map(new IntUnaryFunction() {
            @Override
            public int apply(int x) {
                sum[0] += x;
                return x;
            }
        });
        return sum[0];
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
    }

    /* IMPORTANT: YOU ARE NOT ALLOWED TO USE RECURSION IN ADD AND SUM
     *
     * As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     *
     * HINT: Try checking out the IntUnaryFunction interface.
     *       Can we use it somehow?
     */
}
