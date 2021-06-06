package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Justin Cho
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

    /**PlugBoard setup.
     * @param M is machine
     * @param settings is list of rotors*/
    private void plugboardMe(Machine M, String[] settings) {
        String plugboardSet = "";
        if (settings.length > M.numRotors() + 1) {
            for (int i = M.numRotors() + 1; i < settings.length; i++) {
                plugboardSet += settings[i] + " ";
            }
        }

        M.setPlugboard(new Permutation(plugboardSet.trim(), _alphabet));
        M.setRotors(settings[M.numRotors()]);

    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine m = readConfig();
        String next = _input.nextLine().trim();
        if (!next.startsWith("*")) {
            throw new EnigmaException("incorrect format: missing *");
        }

        next = next.replace("*", "").trim();
        String[] nextList = next.split(" ");

        if (nextList[nextList.length - 1].length() >= nextList.length - 1) {
            throw new EnigmaException("Bad rotor name");
        }

        int check = 0;
        for (int i = 0; i < nextList.length; i++) {
            if (!nextList[i].startsWith("(")) {
                check += 1;
            }
        }
        String[] errorcheck = new String[check];

        for (int j = 0; j < check; j++) {
            errorcheck[j] = nextList[j];
        }

        if (errorcheck[errorcheck.length - 1].length() < errorcheck.length - 2) {
            throw new EnigmaException("Wheel settings too short");
        }
        setUp(m, nextList);


        while (_input.hasNextLine()) {
            next = _input.nextLine().trim();
            if (!next.isEmpty() && !next.startsWith("*")) {
                String convert = m.convert(next.replaceAll(" ", ""));
                printMessageLine(convert);
            } else if (next.startsWith("*")) {
                setUp(m, nextList);
            } else {
                _output.println();
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alp = _config.nextLine().trim();

            if (alp.contains("(") || alp.contains(")") || alp.contains("*")) {
                throw new EnigmaException("Wrong formatting of config");
            }
            _alphabet = new Alphabet(alp);

            if (!_config.hasNextInt()) {
                throw new EnigmaException("Missing number of Rotors");
            }
            _numRotors = _config.nextInt();

            if (!_config.hasNextInt()) {
                throw new EnigmaException("Missing number of Pawls");
            }
            _pawls = _config.nextInt();

            _config.nextLine();

            String maybeRotor = "";

            while (_config.hasNextLine()) {
                String rotor = _config.nextLine().trim();
                if (_config.hasNextLine()) {
                    maybeRotor = _config.nextLine().trim();
                }
                if (maybeRotor.startsWith("(")) {
                    rotor += " " + maybeRotor;
                    _rotorsList.add(readRotor(rotor));
                } else {
                    _rotorsList.add(readRotor(rotor));
                    _rotorsList.add(readRotor(maybeRotor));
                }

            }
            return new Machine(_alphabet, _numRotors, _pawls, _rotorsList);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config.
     * @param rotor is used with scanner.*/
    private Rotor readRotor(String rotor) {
        try {

            Scanner scan = new Scanner(rotor);
            String name = scan.next();
            String type = scan.next();
            String listofRotors = "";

            while (scan.hasNext()) {
                String temp = scan.next();
                if (temp.startsWith("(")) {
                    listofRotors += temp;
                }
            }

            Permutation permute = new Permutation(listofRotors, _alphabet);

            if (type.startsWith("M")) {
                return new MovingRotor(name, permute, type.substring(1));
            } else if (type.startsWith("N")) {
                return new FixedRotor(name, permute);
            } else {
                return new Reflector(name, permute);
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String[] settings) {
        String[] currentRotors = new String[M.numRotors()];

        for (int i = 0; i < M.numRotors(); i++) {
            currentRotors[i] = settings[i];
        }
        M.insertRotors(currentRotors);

        plugboardMe(M, settings);
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        for (int i = 0; i < msg.length(); i++) {
            if ((i + 1) % 5 == 0 && i != msg.length() - 1) {
                _output.print(msg.substring(i - 4, i + 1).trim() + " ");
            } else if (i == msg.length() - 1) {
                _output.println(msg.substring(i - (i % 5), i + 1).trim());
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

    /** Number of rotors. */
    private int _numRotors;

    /** Number of pawls. */
    private int _pawls;

    /** List of rotors. */
    private ArrayList<Rotor> _rotorsList = new ArrayList<>();
}
