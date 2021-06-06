package enigma;

import java.util.HashMap;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Kidong Kim
 */
class Machine {

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    private int _numRotors;

    private  int _pawls;

    private Collection<Rotor> _allRotors;

    private Rotor[] _rotors;

    private Permutation _plugboard;

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors;
        _rotors = new Rotor[_numRotors];
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls(){
            return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        for (int i = 0; i < rotors.length; i++) {
            for (Rotor tmp : _allRotors) {
                if (tmp.name().equals(rotors[i])) {
                    _rotors[i] = tmp;
                }
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        char[] settings = setting.toCharArray();
        for (int i = 0; i < _rotors.length - 1; i++) {
            _rotors[i+1].set(settings[i]);
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        boolean[] checker = new boolean[numRotors()];
        for (int i = 0; i < numRotors() - numPawls(); i++) {
            checker[i] = false; //from left side. Reflector and fixed rotor cannot rotate
        }
        checker[numRotors() - 1] = true; // The most right one
        for (int i = numRotors() - 2; i >= numRotors() - numPawls(); i--) { //start from the right side
            if (_rotors[i].rotates() && _rotors[i + 1].atNotch()) {
                checker[i] = true;
                checker[i+1] = true;
            }
            //checker[i] = false;
        }

        for (int i = 0; i < _rotors.length; i++) {
            if (checker[i]) {
                _rotors[i].advance();
            }
        }

        int result = _plugboard.permute(c);
        for (int i = numRotors() - 1; i >= 0; i--) {
            result = _rotors[i].convertForward(result);
        }
        for (int i = 1; i < numRotors(); i++) {
            result = _rotors[i].convertBackward(result);
        }

        return _plugboard.permute(result);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        char[] letters = msg.toCharArray();
        String result = "";
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] != ' ') {
                String tmp2 = Character.toString(_alphabet.toChar(convert(_alphabet.toInt(letters[i]))));
                result = result.concat(tmp2);
            }
        }
        return result;
    }
}
