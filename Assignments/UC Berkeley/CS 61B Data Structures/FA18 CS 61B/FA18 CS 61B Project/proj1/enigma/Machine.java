package enigma;
import java.util.Collection;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author Kidong Kim
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        numRotor = numRotors;
        numPawls = pawls;
        _allRotors = allRotors;
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return numRotor;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return numPawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {

        Object[] rotorset = _allRotors.toArray();
        rotorSet = new Rotor[numRotor];
        for (int i = 0; i < rotors.length; i++) {
            for (int j = 0; j < rotorset.length; j++) {
                if (rotors[i].equals(((Rotor) rotorset[j]).name())) {
                    rotorSet[i] = (Rotor) rotorset[j];
                }
            }
        }
        for (int i = 0; i < numRotor; i++) {
            if (rotorSet[i] == null) {
                throw new EnigmaException("empty rotor spot exist");
            }
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        if (setting.length() > rotorSet.length) {
            throw new EnigmaException("cannot match up");
        }
        for (int i = 1; i < rotorSet.length; i += 1) {
            if (!_alphabet.contains(setting.charAt(i - 1))) {
                throw new EnigmaException("out of boundary");
            }
            rotorSet[i].set(setting.charAt(i - 1));
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
        if (rotor(numRotor - 1).atNotch()) {
            rotor(numRotor - 2).advance();

        }
        if (rotor(numRotor - 2).atNotch()) {
            rotor(numRotor - 3).advance();
            rotor(numRotor - 2).advance();
        }
        if (rotor(numRotor - 3).atNotch()) {
            rotor(numRotor - 4).advance();
            rotor(numRotor - 3).advance();
        }
        rotor(numRotor - 1).advance();

        int result = _plugboard.permute(c);
        for (int j = rotorSet.length - 1; j > -1; j--) {
            result = rotorSet[j].convertForward(result);
        }
        for (int k = 1; k < rotorSet.length; k++) {
            result = rotorSet[k].convertBackward(result);
        }
        return _plugboard.permute(result);
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        String result = "";
        for (int i = 0; i < msg.length(); i++) {
            String tmpmsg = Character.toString(_alphabet.toChar
                    (convert(_alphabet.toInt(msg.charAt(i)))));
            result = result.concat(tmpmsg);
        }
        return result;
    }


    /** Return the rotor of x from the rotor set. */
    /** @param x @return rotorSet[x] **/
    Rotor rotor(int x) {
        return rotorSet[x];
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of rotors. */
    private int numRotor;

    /** Number of pawls. */
    private int numPawls;

    /** Coolection of all rotors. */
    private Collection<Rotor> _allRotors;

    /** Data of plugboard. */
    private Permutation _plugboard;

    /** Array list of rotors. */
    private Rotor[] rotorSet;

}
