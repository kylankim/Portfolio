package enigma;

import java.io.StringReader;
import java.util.ArrayList;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Kidong Kim
 */
class Permutation {



    /** All Cycles stored int the String array as (abcd) */
    private ArrayList<String> _cycleList = new ArrayList<>();

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Size of Permutation. */
    private int _size;

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _size = alphabet.size();
        if (cycles.length() != 0) {
            addCycle(cycles); //add cycles to String array
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        String[] cycleList = cycle.split(" ");
        String p_str = "";
        for (int i = 0; i < cycleList.length; i++) {
            String tmp = cycleList[i].trim();
            _cycleList.add(tmp.substring(1, tmp.length()-1));
            p_str = p_str.concat(_cycleList.get(i));
        }
        if (_size != p_str.length()) {
            for (char tmp : _alphabet.get_charList()) {
                if (p_str.indexOf(tmp) == -1) {
                    _cycleList.add(Character.toString(tmp));
                }
            }
        }
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _size;
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size.
     *  Return -1 if char p does not exist in the _cycleList.
     *  @input int index of the char in the Alphabet List
     *  @return int index of the char
     *  */
    int permute(int p) {
        int tmp = wrap(p);
        for (String p_cycle : _cycleList) {
            if (p_cycle.indexOf(_alphabet.toChar(tmp)) != -1) {
                int target_index = (p_cycle.indexOf(_alphabet.toChar(tmp)) + 1) % p_cycle.length();
                int result_index = _alphabet.toInt(p_cycle.charAt(target_index));
                return result_index;
            }
        }
        return tmp;
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size.
     *  @input int index of the char in the Alphabet List
     *  @return int index of the char
     *  */
    int invert(int c) {
        int tmp = wrap(c);
        for (String p_cycle : _cycleList) {
            if (p_cycle.indexOf(_alphabet.toChar(tmp)) != -1) {
                int target_index = ((p_cycle.indexOf(_alphabet.toChar(tmp)) - 1)
                        + p_cycle.length()) % p_cycle.length();
                int result_index = _alphabet.toInt(p_cycle.charAt(target_index));
                return result_index;
            }
        }
        return tmp;
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        return _alphabet.toChar(permute(_alphabet.toInt(p)));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        return _alphabet.toChar(invert(_alphabet.toInt(c)));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (String tmp : _cycleList) {
            if (tmp.length() == 1) {
                return false;
            }
        }
        return true;
    }

    /** Getter Method of _cycleList */
    public ArrayList<String> get_cycleList() {
        return _cycleList;
    }

}
