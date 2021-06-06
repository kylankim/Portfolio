package gitlet;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

import static gitlet.Utils.*;

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
//        if (_commands.contains(_input[0].toLowerCase())) {
//            this._command = _input[0];
//            this._operand = Arrays.copyOfRange(_input,1,_input.length);
//        }

        this._command = _input[0];
        this._operand = Arrays.copyOfRange(_input,1,_input.length);

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
        File repo = new File(".gitlet");

        if (!repo.exists()) {
            repo.mkdir();
            new File(".gitlet/objects").mkdir();
            new File(".gitlet/refs").mkdir();
            new File(".gitlet/refs/heads").mkdir();

            Commit initial = new Commit("initial commit", "null");

            File path = Utils.join(".gitlet/objects/", initial.get_name());
            writeObject(path, initial);
            File path2 = Paths.get(".gitlet/refs/heads/master").toFile();
            writeObject(path2, initial);

            writeContents(Paths.get(".gitlet/HEAD").toFile(), "master");
            writeObject(join(".gitlet/index"), new Index());

        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }


    /** Adds a copy of the file as it currently exists to the staging area
     * (see the description of the commit command). For this reason, adding a file
     * is also called staging the file for addition. Staging an already-staged file
     * overwrites the previous entry in the staging area with the new contents. The
     * staging area should be somewhere in .gitlet. If the current working version of
     * the file is identical to the version in the current commit, do not stage it to
     * be added, and remove it from the staging area if it is already there (as can
     * happen when a file is changed, added, and then changed back). The file will
     * no longer be staged for removal (see gitlet rm), if it was at the time of the
     * command. */
    public void add() {
        Index index = readObject(join(".gitlet/index"), Index.class);
        HashMap contentsToAdd = index.get_contentsToAdd();
        File file = new File(_operand[0]);

        String head = readContentsAsString(join(".gitlet/HEAD"));
        Commit headCommit = readObject(join(".gitlet/refs/heads", head), Commit.class);

        String newBlobID = "b" + sha1(readContentsAsString(join(_operand[0])));

        boolean stagedButChanged = contentsToAdd.containsKey(_operand[0])
                && !contentsToAdd.get(_operand[0]).equals(newBlobID);
        boolean trackedButChanged = headCommit.get_contents().containsKey(_operand[0])
                && !headCommit.get_contents().get(_operand[0]).equals(newBlobID);
        boolean tracked = headCommit.get_contents().containsKey(_operand[0]);

        if (file.exists()) {
            if (stagedButChanged || trackedButChanged || !tracked) {
                Blob blob = new Blob(file);
                writeObject(join(".gitlet/", blob.get_name()), blob);
                index.putAddStage(_operand[0], blob.get_name());
            }
        } else {
            System.out.println("File does not exist.");
        }

        if (tracked && index.get_contentsToRemove().containsKey(_operand[0])) {
            index.removeRemoveStage(_operand[0]);
            index.removeAddStage(_operand[0]);
        }

        writeObject(join(".gitlet/index"), index);

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

        if (_operand == null || _operand[0].equals("")) {
            System.out.println("Please enter a commit message.");
        }

        boolean tracker = false;

        String head = readContentsAsString(join(".gitlet/HEAD"));
        Commit parent = readObject(join(".gitlet/refs/heads", head), Commit.class);
        Commit current = new Commit(_operand[0], parent.get_name());

//        Commit parent2 = null;
//        if (_operand[0].split(" ")[0].equals("Merged")) {
//            parent2 = readObject(join(".gitlet/refs/heads", _operand[0].split(" ")[1]), Commit.class);
//            current = new Commit(_operand[0], parent.get_name(), parent2.get_name());
//        }

        for (String fileName : parent.get_contents().keySet()) {
            current.add(fileName, parent.get_contents().get(fileName));
        }

        Index index = readObject(join(".gitlet/index"), Index.class);

        for (String fileNameToAdd : index.get_contentsToAdd().keySet()) {
            tracker = true;
            String blobId = index.get_contentsToAdd().get(fileNameToAdd);
            Blob b = readObject(join(".gitlet/", blobId), Blob.class);
            current.add(fileNameToAdd, blobId);
            writeObject(join(".gitlet/objects", blobId), b);
        }

        for (String fileNameToDelete : index.get_contentsToRemove().keySet()) {
            tracker = true;
            if (current.get_contents().containsKey(fileNameToDelete)) {
                current.remove(fileNameToDelete);
            }
        }

        if (!tracker) {
            System.out.println("No changes added to the commit.");
            return;
        }

        current.refreshName();

        writeObject(join(".gitlet/objects", current.get_name()), current);
        writeObject(join(".gitlet/refs/heads", head), current);

        writeObject(join(".gitlet/index"), new Index());

    }

    /** Starting at the current head commit, display information about each
     *  commit backwards along the commit tree until the initial commit,
     *  following the first parent commit links, ignoring any second parents
     *  found in merge commits. (In regular Git, this is what you get with git
     *  log --first-parent). This set of commit nodes is called the commit's
     *  history. For every node in this history, the information it should display
     *  is the commit id, the time the commit was made, and the commit message.
     *  Here is an example of the exact format it should follow */
    public void log() {
        String head = readContentsAsString(join(".gitlet/HEAD"));
        Commit current = readObject(join(".gitlet/refs/heads", head), Commit.class);
        String name = current.get_name();
        while (join(".gitlet/objects", name).exists()) {
            Commit commit = readObject(join(".gitlet/objects", name), Commit.class);
            commit.print();
            name = commit.get_parent();
        }
    }


    /** Like log, except displays information about all commits ever made.
     * The order of the commits does not matter */
    public void globalLog() {
        for (String tmp : plainFilenamesIn(".gitlet/objects")) {
            if (tmp.charAt(0) == 'c') {
                Commit commit = readObject(join(".gitlet/objects", tmp), Commit.class);
                commit.print();
            }
        }
    }


    /** Takes the version of the file as it exists in the head commit, the front of the
     * current branch, and puts it in the working directory, overwriting the version
     * of the file that's already there if there is one. The new version of the file
     * is not staged.
     *
     * Takes the version of the file as it exists in the commit with the given id,
     * and puts it in the working directory, overwriting the version of the file
     * that's already there if there is one. The new version of the file is not staged.
     *
     * Takes all files in the commit at the head of the given branch, and puts them
     * in the working directory, overwriting the versions of the files that are already
     * there if they exist. Also, at the end of this command, the given branch will now
     * be considered the current branch (HEAD). Any files that are tracked in the current
     * branch but are not present in the checked-out branch are deleted. The staging area
     * is cleared, unless the checked-out branch is the current branch. */
    public void checkout() {
        if (_operand[0].equals("--")) {
            String head = readContentsAsString(join(".gitlet/HEAD"));
            Commit headCommit = readObject(join(".gitlet/refs/heads", head), Commit.class);
            String blobID = headCommit.get_contents().get(_operand[1]);

            if (blobID != null) {
                Blob blob = readObject(join(".gitlet/objects", blobID), Blob.class);
                writeContents(join(_operand[1]), blob.get_contents());
                return;
            } else {
                System.out.println("File does not exist in that commit.");
            }

        } else if (_operand.length == 3) {
            if (_operand[1].equals("--")) {
                Boolean tracker = false;

                for (String fileName : plainFilenamesIn(".gitlet/objects")) {

                    if (fileName.charAt(0) == 'c') {
                        Commit commit = readObject(join(".gitlet/objects", fileName), Commit.class);

                        if (_operand[0].regionMatches(true, 0, fileName, 0, _operand[0].length())) {
                            tracker = true;

                            if (commit.get_contents().containsKey(_operand[2])) {

                                String blobID = commit.get_contents().get(_operand[2]);
                                Blob blob = readObject(join(".gitlet/objects", blobID), Blob.class);

                                writeContents(join(_operand[2]), blob.get_contents());
                                return;
                            }
                        }
                    }
                }
                if (tracker) {
                    System.out.println("File does not exist in that commit.");
                } else {
                    System.out.println("No commit with that id exists.");
                }
            } else {
                System.out.println("Incorrect operands.");
            }
        } else {
            String[] tmp = new String[1];
            tmp[0] = _operand[0];
            _operand = tmp;
            checkout();
        }
    }

    public void remove() {
        String head = readContentsAsString(join(".gitlet/HEAD"));
        Commit headCommit = readObject(join(".gitlet/refs/heads", head), Commit.class);

        Index index = readObject(join(".gitlet/index"), Index.class);
        HashMap removeStage = index.get_contentsToRemove();

        boolean tracker = false;

        for (String key : _operand) {
            if (headCommit.get_contents().containsKey(key)) {
                removeStage.put(key, headCommit.get_contents().get(key));
                if (join(key).exists()) {
                    join(key).delete();
                }
                tracker = true;
            }

            if (join(".gitlet/index").exists()) {
                index = readObject(join(".gitlet/index"), Index.class);

                if (index.get_contentsToAdd().containsKey(key)) {
                    index.get_contentsToAdd().remove(key);
                    writeObject(join(".gitlet/index"), index);
                    tracker = true;
                    break;
                }
            }
        }

        writeObject(join(".gitlet/index"), Index.class);

        if (!tracker) {
            System.out.println("No reason to remove the file.");
        }

    }

    public void find() {

    }

    public void status() {
        String head = readContentsAsString(join(".gitlet/HEAD"));
        System.out.println("=== Branches ===");
        List<String> branches = plainFilenamesIn(".gitlet/refs/heads");
        Collections.sort(branches);
        for (String branch : branches) {
            if (head.equals(branch)) {
                System.out.format("*%s\n", branch);
            } else {
                System.out.println(branch);
            }
        }
        System.out.println();

        Index index = readObject(join(".gitlet/index"), Index.class);
        HashMap contentsToAdd = index.get_contentsToAdd();
        HashMap contentsTORemove = index.get_contentsToRemove();

        ArrayList<String> fileNames = new ArrayList<>(contentsToAdd.keySet());
        Collections.sort(fileNames);
        System.out.println("=== Staged Files ===");
        for (String fileName : fileNames) {
            System.out.println(fileName);
        }
        System.out.println();

        System.out.println("=== Removed Files ===");
        ArrayList<String> fileNames2 = new ArrayList<>(contentsTORemove.keySet());
        for (String fileName2 : fileNames2) {
            System.out.println(fileName2);
        }
        System.out.println();

        System.out.println("=== Modifications Not Staged For Commit ===");
        Commit headCommit = readObject(join(".gitlet/refs/heads", head), Commit.class);

        for (String tmp : plainFilenamesIn(System.getProperty("user.dir"))) {

            String newBlobID = "b" + sha1(readContentsAsString(join(tmp)));

            boolean trackedButChanged = headCommit.get_contents().containsKey(tmp)
                    && !headCommit.get_contents().get(tmp).equals(newBlobID);
            boolean stagedButChanged = contentsToAdd.containsKey(_operand[0])
                    && !contentsToAdd.get(_operand[0]).equals(newBlobID);

            if (trackedButChanged && contentsToAdd.get(tmp) == null) {
                System.out.format("%s (modified)\n", tmp);
            } else if (stagedButChanged) {
                System.out.format("%s (modified)\n", tmp);
            }
        }

        for (String tmp : headCommit.get_contents().keySet()) {
            if (contentsToAdd.containsKey(tmp) && !join(tmp).exists()) {
                System.out.format("%s (deleted)\n", tmp);
            } else if (!join(tmp).exists() && !contentsTORemove.containsKey(tmp)) {
                if (headCommit.get_contents().containsKey(tmp)
                        && !plainFilenamesIn(System.getProperty("user.dir"))
                        .contains(tmp)) {
                    System.out.format("%s (deleted)\n", tmp);
                }
            }
        }
        System.out.println();

        System.out.println("=== Untracked Files ===");
        for (String tmp : plainFilenamesIn(System.getProperty("user.dir"))) {
            if (!headCommit.get_contents().containsKey(tmp) && !contentsToAdd.containsKey(tmp)) {
                System.out.format("%s\n", tmp);
            }
        }
        System.out.println();
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
