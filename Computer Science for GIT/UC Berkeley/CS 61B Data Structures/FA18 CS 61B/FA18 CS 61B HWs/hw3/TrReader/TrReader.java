import java.io.Reader;
import java.io.IOException;

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

    Reader str;
    String from;
    String to;

    public TrReader(Reader str, String from, String to) {

        this.str = str;
        this.from = from;
        this.to = to;

    }

    @Override
    public void close() {
        try {
            str.close();
        } catch (IOException e) {
            return;
        }
    }


    public int read(char[] strs, int off, int len) throws IOException {

        int c = str.read(strs, off, len);

        int i = off;

        while (i < len) {
            if(from.indexOf(strs[i]) != -1) {
                strs[i] = to.charAt(from.indexOf(strs[i]));
            }
            i++;
        }

        return c;

    }
    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!
}


