import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TranslateTest {

    @Test
    public void translate() {
        String sample = "qwerasdfqwerasdfzxcv";
        String sample2 = "1234567890";
        String sample3 = "abcdefghijklmnopqrstuvwxyz";

        assertEquals("qweruiopqweruiopzxcv",Translate.translate(sample, "asdf", "uiop"));
        assertEquals("1234567890",Translate.translate(sample2, "asdf", "uiop"));

        assertEquals("0bc0e0ghijklmnopqr0tuvwxyz",Translate.translate(sample3, "asdf", "0000"));
        assertEquals(sample3,Translate.translate(sample3, "asd", "0000"));


    }
}