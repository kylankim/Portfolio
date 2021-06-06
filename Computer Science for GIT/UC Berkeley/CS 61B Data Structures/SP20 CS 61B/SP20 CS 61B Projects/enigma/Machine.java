package enigma;

import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Justin Cho
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */

    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors;
        _rotorsList = new Rotor[numRotors];
        if (_numRotors <= 1) {
            throw new EnigmaException("rotor number must be greater than 1");
        }
        if (0 > _pawls && _pawls >= _numRotors) {
            throw new EnigmaException("pawl number wrong");
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Used to access _rotorsList in Main.
     * @return _rotorsList */
    Rotor[] rotors() {
        return _rotorsList;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        if (rotors.length != _rotorsList.length) {
            throw new EnigmaException("Length must match");
        }

        for (int i = 0; i < rotors.length; i++) {
            for (Rotor r: _allRotors) {
                if (rotors[i].equals(r.name())) {
                    _rotorsList[i] = r;
                    _rotorsList[i].set(0);
                    break;
                }
            }
        }
        if (!_rotorsList[0].reflecting()) {
            throw new EnigmaException("First rotor must be reflector");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() != _numRotors - 1) {
            throw new EnigmaException("Wrong length of setting");
        }

        for (int i = 1; i < _rotorsList.length; i++) {
            char set = setting.charAt(0);
            if (!_alphabet.contains(set)) {
                throw new EnigmaException("Character not in alphabet");
            }

            _rotorsList[i].set(set);
            setting = setting.substring(1);
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

        boolean[] check = new boolean[_rotorsList.length];
        for (int i = _numRotors - _pawls; i < _numRotors; i++) {
            if (i == _numRotors - 1) {
                _rotorsList[i].advance();
            }
            if (i != _numRotors - 1) {
                if (_rotorsList[i + 1].atNotch()) {
                    int temp = _rotorsList[i].setting();
                    _rotorsList[i].advance();
                    int temp2 = _rotorsList[i].setting();
                    if (temp != temp2) {
                        check[i] = true;
                    }
                }
                boolean notch = _rotorsList[i].atNotch();
                if (notch && check[i - 1] && _rotorsList[i].rotates()) {
                    _rotorsList[i].advance();
                }
            }
        }

        int p = _plugboard.permute(c);

        for (int i = _numRotors - 1; i >= 0; i--) {
            p = _rotorsList[i].convertForward(p);
        }

        for (int i = 1; i < _numRotors; i++) {
            p = _rotorsList[i].convertBackward(p);
        }

        return _plugboard.permute(p);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */

    String convert(String msg) {
        int temp;
        String updated = "";

        for (int i = 0; i < msg.length(); i++) {
            temp = convert(_alphabet.toInt(msg.charAt(i)));
            updated = updated + _alphabet.toChar(temp);
        }
        return updated;
    }


    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Total number of rotors within the machine. */
    private int _numRotors;

    /** Number of connected rotors with pawls. */
    private int _pawls;

    /** Collection of all rotors. */
    private Collection<Rotor> _allRotors;

    /** Rotors used. */
    private Rotor[] _rotorsList;

    /** Initial Plugboard Setting. */
    private Permutation _plugboard;
}
