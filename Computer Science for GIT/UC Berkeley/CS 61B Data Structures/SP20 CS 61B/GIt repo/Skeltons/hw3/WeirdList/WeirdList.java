<<<<<<< HEAD
import net.sf.saxon.functions.Empty;
import net.sf.saxon.functions.PositionAndLast;

import java.awt.*;
=======
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8

/** A WeirdList holds a sequence of integers.
 * @author Kidong Kim
 */
public class WeirdList {
<<<<<<< HEAD
    /** The empty sequence of integers. */
    public static final WeirdList EMPTY = null;
    private int _head;
    private WeirdList _tail;
    static WeirdListHelperOriginal fixed = new WeirdListHelperOriginal();
    private WeirdListHelper helper;

    /** A new WeirdList whose head is HEAD and tail is TAIL. */
    public WeirdList(int head, WeirdList tail) {
        _head = head;
        _tail = tail;
        fixed.help(head);
        helper = new WeirdListHelper(fixed);
=======
    /**
     * The empty sequence of integers.
     */

    private int _head;
    private WeirdList _tail;
    private int sum = 0;

    public static final WeirdList EMPTY = new helper(); // how can I set it as a base case.

    /**
     * A new WeirdList whose head is HEAD and tail is TAIL.
     */
    public WeirdList(int head, WeirdList tail) {
        this._head = head;
        this._tail = tail;
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
    }

    /**
     * Returns the number of elements in the sequence that
     * starts with THIS.
     */
    public int length() {
<<<<<<< HEAD
        return helper.length;
=======
        return 1 + _tail.length();
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
    }

    /**
     * Return a string containing my contents as a sequence of numerals
     * each preceded by a blank.  Thus, if my list contains
     * 5, 4, and 2, this returns " 5 4 2".
     */
    @Override
    public String toString() {
<<<<<<< HEAD
        return helper.toString;
=======
        return " " + _head + _tail.toString(); // TODO: REPLACE THIS LINE
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
    }

    /**
     * Part 3b: Apply FUNC.apply to every element of THIS WeirdList in
     * sequence, and return a WeirdList of the resulting values.
     */
    public WeirdList map(IntUnaryFunction func) {
<<<<<<< HEAD
        WeirdList pointer = this;
        while(pointer._tail != null) {
            int tmp = pointer._head;
            pointer._head = func.apply(pointer._head);
            tmp = (pointer._head - tmp) * pointer.getHelper().length;
            pointer.getHelper().sum += tmp;
            pointer = pointer._tail;
        }
        pointer._head = func.apply(pointer._head);
        return this;  // TODO: REPLACE THIS LINE
    }

    public WeirdListHelper getHelper() {
        return helper;
    }

    public class WeirdListHelper{

        int length;
        String toString;
        int sum;

        public WeirdListHelper(WeirdListHelperOriginal a) {
            this.length = a.length;
            this.toString = a.toString;
            this.sum = a.sum;
        }
    }

    static class WeirdListHelperOriginal{

        private int length = 0;
        private String toString = "";
        private int sum = 0;

        public void help(int head){
            length++;
            toString = " " + head + toString;
            sum += head;
        }
=======
        WeirdList tmp = new WeirdList(func.apply(_head), _tail.map(func));
        return tmp;  // TODO: REPLACE THIS LINE
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
    }
    //_head = func.apply(_head);
    //_tail.map(func);

    /*
     * You should not add any methods to WeirdList, but you will need
     * to add private fields (e.g. head).

     * But that's not all!

     * You will need to create at least one additional class for WeirdList
     * to work. This is because you are forbidden to use any of the
     * following in ANY of the code for HW3:
     *       if, switch, while, for, do, try, or the ?: operator.

     * If you'd like an obtuse hint, scroll to the very bottom of this
     * file.

     * You can create this hypothetical class (or classes) in separate
     * files like you usually do, or if you're feeling bold you can
     * actually stick them INSIDE of this class. Yes, nested classes
     * are a thing in Java.

     * As an example:
     * class Garden {
     *     private static class Potato {
     *        int n;
     *        public Potato(int nval) {
     *           n = nval;
     *        }
     *     }
     * }
     * You are NOT required to do this, just an extra thing you can
     * do if you want to avoid making a separate .java file. */

    private static class helper extends WeirdList {

        public helper() {
            super(99, null);
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        public WeirdList map(IntUnaryFunction func) {
            return this;
        }

    }
}

/*
 * Hint: The first non-trivial thing you'll probably do to WeirdList
 * is to fix the EMPTY static variable so that it points at something
 * useful. */
