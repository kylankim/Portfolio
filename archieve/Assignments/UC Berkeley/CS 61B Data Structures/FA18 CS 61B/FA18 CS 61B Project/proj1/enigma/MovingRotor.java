package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Kidong Kim
 */
class MovingRotor extends Rotor {

    /**
     * A rotor named NAME whose permutation in its default setting is
     * PERM, and whose notches are at the positions indicated in NOTCHES.
     * The Rotor is initally in its 0 setting (first character of its
     * alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }


    @Override
    boolean atNotch() {
        char[] list2 = _notches.toCharArray();
        int[] flist = new int[_notches.length()];
        for (int i = 0; i < flist.length; i++) {
            flist[i] = super.permutation().alphabet().toInt(list2[i]);
            if (super.setting() == super.permutation().wrap(flist[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    void advance() {
        super.set(super.permutation().wrap(super.setting() + 1));
    }

    @Override
    boolean rotates() {
        return true;
    }


    /** notches of the rotor. */
    private String _notches;

}
