package gitlet;

import org.junit.Test;

public class CommandTest {

    @Test
    public void testConstructor() {
        Command c1 = new Command(new String[]{"add", "tmp1.txt", "tmp2.txt"});
        for (String s : c1.get_operand()) {
            System.out.println(s);
        }
    }
}
