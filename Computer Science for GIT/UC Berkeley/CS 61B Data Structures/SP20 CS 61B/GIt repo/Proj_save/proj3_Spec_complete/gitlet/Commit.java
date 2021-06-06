package gitlet;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

public class Commit implements Serializable {

    /** Commit message. */
    private String _message;

    /** Timestamp. */
    private Timestamp _timeStamp;

    /** Pointer to its first parent commit.
     * The file name where we can find the commit object. */
    private String _parent1;

    /** Pointer to its second parent commit. */
    private String _parent2;

    /** String of the commit log. */
    private String _log;

    /** Name of the commit. (SHA1 ID ) */
    private String _name;

    /** Current working branch of the commit.
     * Pointers represented in String */
    private String _branch;

    /** Default constructor of the commit object. */
    public Commit(String _message, String _parent1, String _parent2, String _log, String _name, String _branch) {
        this._message = _message;
        this._parent1 = _parent1;
        this._parent2 = _parent2;
        this._log = _log;
        this._name = _name;
        this._branch = _branch;

        //Timestamp for initial commit
        if (_parent1 == null && _parent2 == null) {
            this._timeStamp = new Timestamp(new Date(Instant.EPOCH.getEpochSecond()).getTime());
        } else {
            this._timeStamp = new Timestamp(new Date().getTime());
        }
    }

    /** Constructor of the commit object with message and a single parent. */
    public Commit(String _message, String _parent) {
        this(_message, _parent, null, null, null, null);
    }

    public String get_parent1() {
        return _parent1;
    }

    public String get_parent2() {
        return _parent2;
    }

    public String get_message() {
        return _message;
    }

    public Date get_timeStamp() {
        return _timeStamp;
    }

    public void set_timeStamp(Timestamp _timeStamp) {
        this._timeStamp = _timeStamp;
    }
}
