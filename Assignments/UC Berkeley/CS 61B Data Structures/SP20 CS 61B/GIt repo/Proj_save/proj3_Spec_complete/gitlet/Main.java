package gitlet;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Kidong Kim
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args[0] == null) {
            System.out.println("No command found.");
        }

        Command command = new Command(args);
        // Process the command

        if (command.get_command() == null) { // If the command does not exist

        }

        // FILL THIS IN
    }

}
