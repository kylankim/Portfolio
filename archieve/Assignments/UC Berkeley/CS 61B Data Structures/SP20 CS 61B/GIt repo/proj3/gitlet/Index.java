package gitlet;

import java.io.Serializable;
import java.util.HashMap;

public class Index implements Serializable {

    /** Contents of the index for addition.
     * Name of files associated with their blobs */
    private HashMap<String, String> _contentsToAdd = new HashMap<>();

    /** Contents of the index for removal.
     * Name of files associated with their blobs */
    private HashMap<String, String> _contentsToRemove = new HashMap<>();


    /** Constructor of the Index. */
    public Index() { }

    public void putAddStage (String fileName, String blobName) {
        _contentsToAdd.put(fileName, blobName);
    }

    public void removeAddStage (String fileName) {
        _contentsToAdd.remove(fileName);
    }

    public void putRemoveStage (String fileName, String blobName) {
        _contentsToRemove.put(fileName, blobName);
    }

    public void removeRemoveStage (String fileName) {
        _contentsToRemove.remove(fileName);
    }


    public HashMap<String, String> get_contentsToAdd() {
        return _contentsToAdd;
    }

    public HashMap<String, String> get_contentsToRemove() {
        return _contentsToRemove;
    }
}
