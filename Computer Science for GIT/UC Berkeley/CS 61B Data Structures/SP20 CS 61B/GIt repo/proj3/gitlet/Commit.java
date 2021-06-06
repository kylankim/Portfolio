package gitlet;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

public class Commit implements Serializable {

    /** Commit message. */
    private String _message;

    /** Timestamp. */
    private Timestamp _timeStamp;

    /** Pointer to its first parent commit.
     * The file name where we can find the commit object. */
    private String _parent;

    /** Pointer to its second parent commit. */
    private String _parent2;

    /** Name of the commit. (SHA1 ID ) */
    private String _name;

    /** Current working branch of the commit.
     * Pointers represented in String */
    private String _branch;

    /** Hashmap of contents of the commit w/ the name of the file with its blobs. */
    private HashMap<String, String> _contents = new HashMap<>();

    /** Default constructor of the commit object. */
    public Commit(String _message, String _parent, String _branch) {
        this._message = _message;
        this._branch = _branch;
        this._parent = _parent;

        //Timestamp for initial commit
        if (_parent.equals("null")) {
            this._timeStamp = new Timestamp(new Date(Instant.EPOCH.getEpochSecond()).getTime());
        } else {
            this._timeStamp = new Timestamp(new Date(Instant.now().getEpochSecond()).getTime());
        }

        this._name = "c" + Utils.sha1(_message, _timeStamp.toString(), this._parent , _contents.toString());
    }

    /** Constructor of the commit object with message and a single parent. */
    public Commit(String _message, String _parent) {
        this(_message, _parent, null);
    }

    /** Add file and associated blob into the _contents of commit. */
    public void add (String fileName, String blobName) {
        _contents.put(fileName, blobName);
    }

    /** Remove file from the _contents of commit. */
    public void remove (String fileName) {
        _contents.remove(fileName);
    }

    /** Rehash the SHA value after Modification to the contents of the object. */
    public String refreshName() {
        this._name = "c" + Utils.sha1(_message, _timeStamp.toString(), _parent, _contents.toString());
        return _name;
    }

    public String get_parent() {
        return _parent;
    }

    public String get_message() {
        return _message;
    }

    public Date get_timeStamp() {
        return _timeStamp;
    }

    public String get_name() {
        return _name;
    }

    public HashMap<String, String> get_contents() {
        return _contents;
    }

    public void set_contents(HashMap<String, String> _contents) {
        this._contents = _contents;
    }

    /**
     * Print the commit.
     */
    public void print() {
        System.out.println("===");
        System.out.format("commit %s\n", _name);
        if (_parent2 != null) {
            System.out.format("Merge: %s %s\n", _parent.substring(0, 7), _parent2.substring(0, 7));
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
        System.out.format("Date: %s\n", sdf.format(_timeStamp));
        System.out.println(_message);
        System.out.println();
    }
}
