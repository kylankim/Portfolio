package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Justin Cho
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
        _perm = perm;
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    void advance() {
        super.set(super.setting() + 1);
    }

    @Override
    boolean atNotch() {
        for (int i = 0; i < _notches.length(); i++) {
            int modSetting = _perm.wrap(super.setting(), size());
            if (alphabet().toInt(_notches.charAt(i)) == modSetting) {
                return true;
            }
        }
        return false;
    }
    /** Notches. */
    private String _notches;

    /** Permutation. */
    private Permutation _perm;
}
