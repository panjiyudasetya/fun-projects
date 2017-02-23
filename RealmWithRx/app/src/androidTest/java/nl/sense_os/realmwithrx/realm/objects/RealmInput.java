package nl.sense_os.realmwithrx.realm.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by panjiyudasetya on 2/22/17.
 */

public class RealmInput extends RealmObject {
    @PrimaryKey
    private String name;
    private int version;

    public RealmInput() {
    }

    public RealmInput(String name, int version) {
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
