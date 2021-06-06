package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;

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
        Machine machine = readConfig();
        String tmp = _input.nextLine().trim();
        if (!tmp.startsWith("*")) {
            throw new EnigmaException("Missing setting statement.");
        }
        tmp = tmp.replace("*", "").trim();
        setUp(machine, tmp);
        while (_input.hasNextLine()) {
            tmp = _input.nextLine().trim();
            if (tmp.startsWith("*")) {
                tmp = tmp.replace("*", "").trim();
                setUp(machine, tmp);
            } else if (tmp.equals("")) {
                _output.println();
            } else {
                tmp = tmp.replace(" ", "");
                tmp = machine.convert(tmp);
                printMessageLine(tmp);
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alpha = _config.nextLine().trim();
            _alphabet = new Alphabet(alpha);

            if (!_config.hasNextInt()) {
                throw new EnigmaException("Missing number of Rotors");
            }
            int numRotor = _config.nextInt();

            if (!_config.hasNextInt()) {
                throw new EnigmaException("Missing number of Pawls");
            }
            int numPawl = _config.nextInt();
            _config.nextLine();
            ArrayList<Rotor> rotors = new ArrayList<>();
            while (_config.hasNextLine()) {
                String tmp = _config.nextLine().trim();
                if (_config.hasNextLine()) {
                    String tmp2 = _config.nextLine().trim();
                    if (tmp2.startsWith("(")) {
                        _rotorinfo = tmp.concat(" " + tmp2);
                        rotors.add(readRotor());
                    } else {
                        _rotorinfo = tmp;
                        rotors.add(readRotor());
                        if (!tmp2.equals("")) {
                            _rotorinfo = tmp2;
                            rotors.add(readRotor());
                        }
                    }
                } else if (!tmp.equals("")) {
                    _rotorinfo = tmp;
                    rotors.add(readRotor());
                }
            }
            return new Machine(_alphabet, numRotor, numPawl, rotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String[] tmp = _rotorinfo.split(" ");
            String name = tmp[0].trim();
            String perm = "";
            for (int i = 2; i < tmp.length; i++) {
                if (tmp[i].startsWith("(")) {
                    tmp[i] = tmp[i].replace(")(", ") (");
                    perm = perm.concat(tmp[i] + " ");
                }
            }
            if (tmp[1].length() != 1) {
                String not = tmp[1].substring(1);
                return new MovingRotor(name,
                        new Permutation(perm, _alphabet), not);
            } else if (tmp[1].equals("N")) {
                return new FixedRotor(name, new Permutation(perm, _alphabet));
            }
            return new Reflector(name, new Permutation(perm, _alphabet));
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        String[] tmp = settings.split(" ");
        String[] rotors = new String[M.numRotors()];
        System.arraycopy(tmp, 0, rotors, 0, rotors.length);
        M.insertRotors(rotors);
        M.setRotors(tmp[M.numRotors()]);
        String perm = "";
        for (int i = M.numRotors() + 1; i < tmp.length; i++) {
            if (tmp[i].startsWith("(")) {
                perm = perm.concat(tmp[i] + " ");
            } else if (!tmp[i].startsWith("(")
                    && (tmp[i].trim().length() == M.numRotors() - 1)) {
                _secondSetting = tmp[i];
            }
        }
        M.setPlugboard(new Permutation(perm, _alphabet));
    }


    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        for (int i = 0; i < msg.length(); i++) {
            if ((i + 1) % 5 == 0 && i != msg.length() - 1) {
                _output.print(msg.substring(i - 4, i + 1) + " ");
            } else if (i == msg.length() - 1) {
                _output.println(msg.substring(i - (i % 5), i + 1));
            }
        }
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** String of each Rotor's information. */
    private String _rotorinfo;

    /** String of the EC setting. */
    private String _secondSetting;

}
