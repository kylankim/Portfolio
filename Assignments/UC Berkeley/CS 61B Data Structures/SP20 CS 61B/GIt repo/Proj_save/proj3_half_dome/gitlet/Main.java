package gitlet;

import static gitlet.Utils.join;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Kidong Kim
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            System.out.println("No command found.");
            return;
        }

        if (!join(System.getProperty("user.dir"), ".gitlet").exists() && !args[0].equals("init")) {
            System.out.println("Not in an initialized Gitlet directory.");
            return;
        }

        Command command = new Command(args);
        command.process();

        // FILL THIS IN
    }

}
