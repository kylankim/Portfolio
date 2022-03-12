import java.util.*;



public class Final {

    public LinkedList<LinkedList<Integer>> triangle (List<Integer> A) {

        LinkedList<LinkedList<Integer>> result = new LinkedList<>();

        Iterator<Integer> iter = A.iterator();

        for (int k = 1; iter.hasNext(); k++) {

            LinkedList tmp = new LinkedList<Integer>();
            while (iter.hasNext() && k > tmp.size()) {
                tmp.add(iter.next());
            } if (!tmp.isEmpty()) {
                result.add(tmp);
            }
        }

        return result;
    }
}
