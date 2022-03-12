import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author Kidong Kim
 */
public class ECHashStringSetTest  {
    // FIXME: Add your own tests for your ECHashStringSetTest

    @Test
    public void test() {
        ECHashStringSet tmp = new ECHashStringSet();
        String tmp2 = StringUtils.randomString(5);
        for (int i = 0; i < 1000; i++) {
            tmp.put(tmp2);
            tmp2 = StringUtils.nextString(tmp2);
        }

        assertEquals(1000, tmp.size());

        List<String> total = tmp.asList();
        for (String t : total) {
            assertTrue(tmp.contains(t));
        }
        assertEquals(total.size(), tmp.size());

    }

    @Test
    public void test2() {
        ECHashStringSet tmp = new ECHashStringSet();
        assertEquals(0, tmp.size());

        tmp.put("Hi");
        assertEquals(1, tmp.size());

        tmp.put("Hi");
        assertEquals(1, tmp.size());

        tmp.put("Bye");
        assertEquals(2, tmp.size());
    }
}
