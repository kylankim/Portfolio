package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Kidong Kim
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
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        _cycles += cycle;
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** split the cycle into cycle sets in array type. */
    /** @param cycle @return cycle2 **/
    String[] split(String cycle) {
        cycle = cycle.replaceAll(" ", "")
                .replaceAll("\\(", "").replaceAll("\\)", "/");
        String[] cycle2 = cycle.split("/");
        return cycle2;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        p = wrap(p);
        return _alphabet.toInt(permute(_alphabet.toChar(p)));
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        c = wrap(c);
        return _alphabet.toInt(invert(_alphabet.toChar(c)));
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        if (!_cycles.isEmpty()) {
            String[] list = split(_cycles);
            for (int i = 0; i < list.length; i++) {
                if (list[i].contains(String.valueOf(p))) {
                    if (list[i].indexOf(String.valueOf(p))
                            == list[i].length() - 1) {
                        return list[i].charAt(0);
                    } else {
                        return list[i].charAt(list[i]
                                .indexOf(String.valueOf(p)) + 1);
                    }
                }
            }
        }
        return p;
    }



    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        if (!_cycles.isEmpty()) {
            String[] list = split(_cycles);
            for (int i = 0; i < list.length; i++) {
                if (list[i].contains(String.valueOf(c))) {
                    if (list[i].indexOf(String.valueOf(c)) == 0) {
                        return list[i].charAt(list[i].length() - 1);
                    } else {
                        return list[i].charAt(list[i]
                                .indexOf(String.valueOf(c)) - 1);
                    }
                }
            }
        }
        return c;
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        String[] list = split(_cycles);
        for (int i = 0; i < list.length; i++) {
            if (list[i].length() != 1) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Cycles of this rotor. */
    private String _cycles;

}
