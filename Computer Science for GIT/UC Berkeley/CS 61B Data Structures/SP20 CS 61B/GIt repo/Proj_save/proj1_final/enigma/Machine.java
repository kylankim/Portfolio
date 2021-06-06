package enigma;

import java.util.Collection;
import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Kidong Kim
 */
class Machine {

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of Rotors. */
    private int _numRotors;

    /** Number of Moving Rotors. */
    private int _pawls;

    /** Collection of all Rotors from config file. */
    private Collection<Rotor> _allRotors;

    /** List of Rotors used in the machine. */
    private Rotor[] _rotors;

    /** Plugboard of the machine. */
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
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        for (int i = 0; i < rotors.length; i++) {
            for (Rotor tmp : _allRotors) {
                if (tmp.name().equals(rotors[i])) {
                    if (i == 0 && !tmp.reflecting()) {
                        throw new EnigmaException("Reflector must be inserted");
                    } else if (i > 0 && i < (numRotors() - numPawls())
                            && (tmp.rotates() || tmp.reflecting())) {
                        throw new EnigmaException("Fixed Rotor "
                                + "must be inserted");
                    } else if (i > (numRotors() - numPawls())
                            && !tmp.rotates()) {
                        throw new EnigmaException("Moving Rotor "
                                + "must be inserted");
                    }
                    for (int j = 0; i > j; j++) {
                        if (_rotors[j].equals(tmp)) {
                            throw new EnigmaException("Duplicate Rotor.");
                        }
                    }
                    _rotors[i] = tmp;
                }
            }
            if (_rotors[i] == null) {
                throw new EnigmaException("No Rotor exists with the name");
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        char[] settings = setting.toCharArray();
        if (settings.length != _rotors.length - 1) {
            throw new EnigmaException("Wrong setting length.");
        }
        for (int i = 0; i < _rotors.length - 1; i++) {
            if (_alphabet.getCharList().contains(settings[i])) {
                _rotors[i + 1].set(settings[i]);
            } else {
                throw new EnigmaException("Wrong Char in setting.");
            }
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
        checker[numRotors() - 1] = true;
        for (int i = numRotors() - 2; i >= numRotors() - numPawls(); i--) {
            if (_rotors[i].rotates() && _rotors[i + 1].atNotch()) {
                checker[i] = true;
                checker[i + 1] = true;
            }
        }

        for (int i = numRotors() - numPawls(); i < numRotors(); i++) {
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
            if (!_alphabet.getCharList().contains(letters[i])) {
                throw new EnigmaException("Char does not exist in Alphabet");
            }
            int tmp2 = convert(_alphabet.toInt(letters[i]));
            String tmp3 = Character.toString(_alphabet.toChar(tmp2));
            result = result.concat(tmp3);
        }
        return result;
    }

    /** Getter method of rotors.
     * @return Rotor[] */
    public Rotor[] getRotors() {
        return _rotors;
    }
}
