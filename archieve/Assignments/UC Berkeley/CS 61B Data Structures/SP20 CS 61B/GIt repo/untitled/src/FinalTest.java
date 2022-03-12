import ucb.junit.textui;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;


public class FinalTest {

    /** A dummy test to avoid complaint. */
    @Test
    public void triangleTest() {
        Final tmp = new Final();
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            lst.add(i);
        }
        System.out.println(lst.toString());


        LinkedList<LinkedList<Integer>> result = tmp.triangle(lst);

        System.out.println("Result : " + result.size());

        System.out.println(result.toString());

        while (result.size() > 0) {
            LinkedList<Integer> t = result.remove();
            System.out.println(t.toString());
        }





        System.out.println(result.toString());


    }

}
