package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Justin Cho
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;
        String tempsplit = _cycles.replace(")", " ");
        String tempsplit2 = tempsplit.replace("(", "");
        _splitcycle = tempsplit2.split(" ", _cycles.length());
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        _cycles += cycle;
        String tempsplit = _cycles.replace(")", "(");
        String tempsplit2 = tempsplit.replace("(", "");
        _splitcycle = tempsplit2.split(" ", _cycles.length());
    }

    /** Return the value of P modulo the size of this permutation. */
    /** @param size is changed to be inclusive of other usages
     * @param p is the original integer we are modding
     * @return */
    final int wrap(int p, int size) {
        int r = p % size;
        if (r < 0) {
            r += size;
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char ch = _alphabet.toChar(wrap(p, size()));
        for (int j = 0; j < _splitcycle.length; j++) {
            if (_splitcycle[j].contains(String.valueOf(ch))) {
                for (int i = 0; i < _splitcycle[j].length(); i++) {
                    if (_splitcycle[j].charAt(i) == ch) {
                        int wrap = wrap(i + 1, _splitcycle[j].length());
                        char updatedChar = _splitcycle[j].charAt(wrap);
                        return _alphabet.toInt(updatedChar);
                    }
                }
            }
        }
        return p;

    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char ch = _alphabet.toChar(wrap(c, size()));
        for (int j = 0; j < _splitcycle.length; j++) {
            if (_splitcycle[j].contains(String.valueOf(ch))) {
                for (int i = 0; i < _splitcycle[j].length(); i++) {
                    if (_splitcycle[j].charAt(i) == ch) {
                        int wrap = wrap(i - 1, _splitcycle[j].length());
                        char updatedChar = _splitcycle[j].charAt(wrap);
                        return _alphabet.toInt(updatedChar);
                    }
                }
            }
        }
        return c;
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        return _alphabet.toChar(permute(_alphabet.toInt(p)));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    int invert(char c) {
        return _alphabet.toChar(invert(_alphabet.toInt(c)));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        for (int i = 0; i < _splitcycle.length; i++) {
            if (_splitcycle[i].length() <= 1) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Cycles. */
    private String _cycles;

    /** Cycles after modification. */
    private String[] _splitcycle;
}
