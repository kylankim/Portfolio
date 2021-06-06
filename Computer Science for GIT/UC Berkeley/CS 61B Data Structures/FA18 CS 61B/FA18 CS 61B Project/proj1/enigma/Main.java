package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Kidong Kim
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine result = readConfig();
        String tmp = _input.nextLine();
        if (tmp.charAt(0) != '*') {
            throw new EnigmaException("sometihng is missing from the input");
        }
        while (_input.hasNext()) {
            setUp(result, tmp);
            tmp = (_input.nextLine()).toUpperCase();
            while (tmp.isEmpty()) {
                _output.println();
                tmp = (_input.nextLine()).toUpperCase();
            }
            while (_input.hasNext() && !tmp.contains("*")) {
                String tmp2 = result.convert(tmp.replaceAll(" ", ""));
                printMessageLine(tmp2);
                tmp = (_input.nextLine()).toUpperCase();
            }
            if (!_input.hasNext() && tmp.contains("*")) {
                break;
            } else if (!_input.hasNext()) {
                String tmp2 = result.convert(tmp.replaceAll(" ", ""));
                printMessageLine(tmp2);
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alphabet = _config.next();
            _alphabet = new CharacterRange(alphabet.charAt(0), alphabet
                    .charAt(alphabet.length() - 1));
            if (!_config.hasNextInt()) {
                throw new EnigmaException("No numrotors");
            }
            int numRotors = _config.nextInt();
            if (!_config.hasNextInt()) {
                throw new EnigmaException("No pawls");
            }
            int pawls = _config.nextInt();
            if (!_config.hasNext()) {
                throw new EnigmaException("no name");
            }
            _tmpstack = (_config.next()).toUpperCase();
            while (_config.hasNext()) {
                names = _tmpstack;
                notches = _config.next().toUpperCase();
                _allRotors.add(readRotor());

            }
            return new Machine(_alphabet, numRotors, pawls, _allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }



    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            perm = "";
            _tmpstack = _config.next().toUpperCase();
            while (_config.hasNext() && _tmpstack.contains("(")) {
                perm += _tmpstack;
                perm += " ";
                _tmpstack = _config.next().toUpperCase();
            }
            perm.concat(_tmpstack + " ");
            if (notches.isEmpty() || names.isEmpty()) {
                throw new EnigmaException("wrong settings");
            }
            if (notches.charAt(0) == 'M') {
                return new MovingRotor(names,
                        new Permutation(perm, _alphabet), notches.substring(1));
            }
            if (notches.charAt(0) == 'N') {
                return new FixedRotor(names, new Permutation(perm, _alphabet));
            }
            return new Reflector(names, new Permutation(perm, _alphabet));

        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        String[] set = settings.split(" ");
        if (set.length < M.numRotors() + 1) {
            throw new EnigmaException("empty spot or plugs are missing");
        }
        String[] rotors = new String[M.numRotors()];
        for (int i = 1; i < M.numRotors() + 1; i++) {
            rotors[i - 1] = set[i];
        }
        String plugs = "";
        for (int i = M.numRotors() + 2; i < set.length; i++) {
            plugs += set[i];
            plugs += " ";
        }
        M.insertRotors(rotors);

        for (int j = 0; j < M.numRotors(); j++) {
            if (j == 0) {
                if (M.rotor(j).rotates()) {
                    throw new EnigmaException("no reflector");
                }
            } else {
                if (M.rotor(j).reflecting()) {
                    throw new EnigmaException("should not be reflector");
                }
            }

        }


        for (int i = 0; i < M.numRotors(); i++) {
            for (int k = i; k < M.numRotors() - 1; k++) {
                if (rotors[i] == rotors[k + 1]) {
                    throw new EnigmaException("cannot use the rotor twice");
                }
            }
        }

        M.setRotors(set[M.numRotors() + 1]);
        M.setPlugboard(new Permutation(plugs, _alphabet));
    }


    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        if (msg.isEmpty()) {
            _output.println();
            return;
        }
        int count = 0;
        for (int i = 0; i < msg.length() / 5; i++) {
            _output.print(msg.substring(count, count + 5) + " ");
            count += 5;
        }
        _output.println(msg.substring(count, msg.length()));
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** String of parameter. */
    private String perm;

    /** String of notches. */
    private String notches;

    /** temp. information stocks here. */
    private String _tmpstack;

    /** String of names. */
    private String names;

    /** Array list of all rotors. */
    private ArrayList<Rotor> _allRotors = new ArrayList<>();

}
