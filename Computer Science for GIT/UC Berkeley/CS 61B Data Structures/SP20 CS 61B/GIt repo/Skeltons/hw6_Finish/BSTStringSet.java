import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 * @author Kidong Kim
 */
public class BSTStringSet implements SortedStringSet, Iterable<String> {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }

    @Override
    public void put(String s) {
        if (_root == null) {
            _root = new Node(s);
        } else {
            Node tmp = _root;
            int control;
            while (true) {
                control = s.compareTo(tmp.s);
                if (control < 0) {
                    if (tmp.left == null) {
                        tmp.left = new Node(s);
                        break;
                    }
                    tmp = tmp.left;
                } else if (control > 0) {
                    if (tmp.right == null) {
                        tmp.right = new Node(s);
                        break;
                    }
                    tmp = tmp.right;
                } else {
                    break;
                }
            }
        }
    }

    private Node insert(Node T, String s) {
        if (s == null) {
            return T;
        }
        if (T == null) {
            return new Node(s);
        }
        if (s.compareTo(T.s) < 0) {
            T.left = insert(T.left, s);
        }
        if (s.compareTo(T.s) > 0) {
            T.right = insert(T.right, s);
        }
        return T;
    }

    @Override
    public boolean contains(String s) {
        Node tmp = _root;
        while (tmp != null) {
            int c = s.compareTo(tmp.s);
            if (c < 0) {
                tmp = tmp.left;
            } else if (c > 0) {
                tmp = tmp.right;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> asList() {
        ArrayList<String> result = new ArrayList<>();
        for (String label : this) {
            result.add(label);
        }
        return result;
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }


    @Override
    public Iterator<String> iterator(String low, String high) {
        return new in_order_iterator(_root, low, high);
    }

    private static class in_order_iterator implements Iterator<String> {

        private String _low;
        private String _high;
        private Stack<Node> _toDo = new Stack<>();


        in_order_iterator(Node node, String _low, String _high) {
            this._low = _low;
            this._high = _high;
            addTree(node);
        }

        private void addTree(Node node) {
            if (node != null) {
                if (node.s.compareTo(_high) >= 0) {
                    addTree(node.left);
                    return;
                } if (node.s.compareTo(_low) < 0) {
                    addTree(node.right);
                    return;
                }
                _toDo.push(node);
                addTree(node.left);
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public String next() {
            if (hasNext()) {
                Node result = _toDo.pop();
                addTree(result.right);
                return result.s;
            }
            throw new NoSuchElementException();
        }
    }


    /** Root node of the tree. */
    private Node _root;
}
