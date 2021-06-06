package gitlet;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;

public class Command {

    /** Command String. */
    private String _command;

    /** Array of operand Strings . */
    private String[] _operand;

    /** Hashset of commands.
     * All commands are stroed in lowercase*/
    private HashSet<String> _commands = new HashSet<>();

    {
        _commands.add("add");
        _commands.add("commit");
        _commands.add("push");
        _commands.add("pull");
        _commands.add("fetch");
        _commands.add("merge");
        _commands.add("init");
        _commands.add("status");
        _commands.add("remove");

    }



    /** Default constructor.
     * Only when the command exist in the HashSet of commands */
    public Command(String[] _input) {
        if (_commands.contains(_input[0].toLowerCase())) {
            this._command = _input[0];
            this._operand = Arrays.copyOfRange(_input,1,_input.length);
        }
    }

    public void process() {
        switch (_command) {
            case "init":
                init();
                break;
            case "add":
                add();
                break;
            case "commit":
                commit();
                break;
            case "rm":
                remove();
                break;
            case "log":
                log();
                break;
            case "global-log":
                globalLog();
                break;
            case "find":
                find();
                break;
            case "status":
                status();
                break;
            case "checkout":
                checkout();
                break;
            case "branch":
                branch();
                break;
            case "rm-branch":
                removeBranch();
                break;
            case "reset":
                reset();
                break;
            case "merge":
                merge();
                break;
//            case "push":
//                break;
//            case "pull":
//                break;
            default: // May be useless
                System.out.println("No command found.");
        }
    }

    /** Creates a new Gitlet version-control system in the current directory.
     * This system will automatically start with one commit: a commit that contains
     * no files and has the commit message initial commit (just like that, with
     * no punctuation). It will have a single branch: master, which initially points
     * to this initial commit, and master will be the current branch. The timestamp
     * for this initial commit will be 00:00:00 UTC, Thursday, 1 January 1970 in
     * whatever format you choose for dates (this is called "The (Unix) Epoch",
     * represented internally by the time 0.) Since the initial commit in all
     * repositories created by Gitlet will have exactly the same content, it follows
     * that all repositories will automatically share this commit (they will all have
     * the same UID) and all commits in all repositories will trace back to it. */

    public void init() {
        File cwd = new File(System.getProperty("user.dir")); // Returns absolute path to CWD
        Commit initial = new Commit("Initial commit", null);
        // Branches? Here we need to initialize a master branch and have it point to initial commit
        // What is UID?

    }

    public void add() {

    }


    /** Saves a snapshot of certain files in the current commit and staging area
     *  so they can be restored at a later time, creating a new commit. The commit
     *  is said to be tracking the saved files. By default, each commit's snapshot
     *  of files will be exactly the same as its parent commit's snapshot of files;
     *  it will keep versions of files exactly as they are, and not update them.
     *  A commit will only update the contents of files it is tracking that have been
     *  staged for addition at the time of commit, in which case the commit will now
     *  include the version of the file that was staged instead of the version it got
     *  from its parent. A commit will save and start tracking any files that were
     *  staged for addition but weren't tracked by its parent. Finally, files tracked
     *  in the current commit may be untracked in the new commit as a result being
     *  staged for removal by the rm command. */

    public void commit() {

        // Read from local computer the HEAD commit object and the staging area

        // Clone the HEAD commit
        // Modify its message and timestamp according to user input
        // Use the staging area in order to modify the files tracked by the new commit

        // Write back any new objects made or any modified objects read earlier
    }

    public void remove() {

    }

    public void log() {

    }

    public void globalLog() {

    }

    public void find() {

    }

    public void status() {

    }

    public void checkout() {

    }

    public void branch() {

    }

    public void removeBranch() {

    }

    public void reset() {

    }

    public void merge() {

    }






    public String get_command() {
        return _command;
    }

    public String[] get_operand() {
        return _operand;
    }

    public HashSet<String> get_commands() {
        return _commands;
    }
}
