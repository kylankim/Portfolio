package gitlet;

import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.*;

public class Blob implements Serializable {

    /** Name of the blob. (SHA1 ID ) */
    private String _name;

    /** Contents of the blob in a stream of bytes. */
    private byte[] _contents;

    public Blob(File file) {
        this._contents = readContents(file);
        this._name = "b" + sha1(_contents);
    }

    public String get_name() {
        return _name;
    }

    public byte[] get_contents() {
        return _contents;
    }
}
