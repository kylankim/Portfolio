import java.io.Reader;
import java.io.IOException;
import java.util.Objects;


/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author Kidong Kim
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */

    private Reader _str;
    private String _from;
    private String _to;


    public TrReader(Reader str, String from, String to) {
        _str = str;
        if (from.length() == to.length()) {
            _from = from;
            _to = to;
        } else {
            System.out.println("String size does not match!");
        }
    }


    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
<<<<<<< HEAD
        Objects.checkFromIndexSize(off,len,cbuf.length);
=======
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
        int count = 0;
        for (int i = off; i < off + len; i++) {
            try {
                int tmp = _str.read();
                if (tmp == -1) {
<<<<<<< HEAD
=======
                    if (count != 0) {
                        return count;
                    }
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
                    return tmp;
                }
                count++;
                if (_from != null&& _from.indexOf(tmp) != -1) {
                    cbuf[i] = _to.charAt(_from.indexOf(tmp));
                } else{
                    cbuf[i] = (char)tmp;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } return count;
<<<<<<< HEAD
    }

    public int read() throws IOException{
        char cbuf[] = new char[1];
        if (read(cbuf, 0, 1) == -1)
            return -1;
        else
            return cbuf[0];
=======
>>>>>>> 3840a9c9ffcf1597c864be835f1da34a5e0f8fe8
    }

    @Override
    public void close() throws IOException {

    }
}
