package nl.sense_os.realmwithrx.realm;

/**
 * Created by panjiyudasetya on 2/22/17.
 */

public class Input {
    private String name;
    private int version;

    public Input(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
