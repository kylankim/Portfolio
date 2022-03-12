package enigma;

import com.sun.xml.internal.xsom.impl.scd.Iterators;

import java.io.StringReader;
import java.util.ArrayList;

/** An alphabet of encodable characters.  Provides a mapping from characters
 *  to and from indices into the alphabet.
 *  @author Kidong Kim
 */

class Alphabet {

    /** Number of chars in Alphabet class. */
    private int _size = 0;

    /** String of list of chars in Alphabet class. */
    private String _chars = "";

    /** Array list of chars in Alphabet class. */
    private ArrayList<Character> _charList = new ArrayList<>();

    /** A new alphabet containing CHARS.  Character number #k has index
     *  K (numbering from 0). No character may be duplicated. */
    Alphabet(String chars) {
        for (char tmp : chars.toCharArray()){
            if (!_charList.contains(tmp)) {
                _chars = _chars.concat(Character.toString(tmp));
                _charList.add(tmp);
                _size++;
            }
        }
    }

    /** A default alphabet of all upper-case characters. */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /** Returns the size of the alphabet. */
    int size() {
        return _size;
    }

    /** Returns true if CH is in this alphabet.
     * if ch doesn't exist, result of indexOf() will be -1, and return false  */
    boolean contains(char ch) {
        return _charList.contains(ch);
    }

    /** Returns character number INDEX in the alphabet, where
     *  0 <= INDEX < size(). */
    char toChar(int index) {
        return _charList.get(index);
    }

    /** Returns the index of character CH which must be in
     *  the alphabet. This is the inverse of toChar(). */
    int toInt(char ch) {
        return _charList.indexOf(ch);
    }

    /** Getter method for the Array list of chars in Alphabet class */
    public ArrayList<Character> get_charList() {
        return _charList;
    }
}
