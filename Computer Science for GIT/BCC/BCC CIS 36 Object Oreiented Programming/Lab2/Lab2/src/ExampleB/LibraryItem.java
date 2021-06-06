package ExampleB;

import java.util.UUID;

public class LibraryItem {

    //Setting the Id of the object randomly
    String ID = UUID.randomUUID().toString();

    //Description of the obejct
    private String description;

    void setDescription(String s) {
        this.description = s;
    }

    String getDescription() {
        return this.description;
    }
}
