import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author Kidong Kim
 */
public class BSTStringSetTest  {


    @Test
    public void test() {
        BSTStringSet tmp = new BSTStringSet();
        assertEquals(0, tmp.asList().size());

        tmp.put("Hi");
        assertTrue(tmp.contains("Hi"));
        assertEquals(1, tmp.asList().size());
    }
}
