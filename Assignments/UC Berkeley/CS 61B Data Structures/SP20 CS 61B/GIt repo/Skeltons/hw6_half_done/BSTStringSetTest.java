import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author Kidong Kim
 */
public class BSTStringSetTest  {


    @Test
    public void testNothing() {
        BSTStringSet tmp = new BSTStringSet();
        assertTrue(tmp.asList().size() == 0);
    }
}
