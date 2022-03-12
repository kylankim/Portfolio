package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Kidong Kim
 */
class MovingRotor extends Rotor {

    private char[] _notches;

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        set(0);
        _notches = notches.toCharArray();
    }

    @Override
    void advance() {
        set(setting()+1);
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        for (char tmp : _notches){
            if (alphabet().toChar(setting()) == tmp) {
                return true;
            }
        }
        return false;
    }

    /** return char list of notches */
    public char[] get_notches() {
        return _notches;
    }
}
