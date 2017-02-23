package nl.sense_os.realmwithrx.realm;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import nl.sense_os.realmwithrx.BuildConfig;
import nl.sense_os.realmwithrx.realm.objects.RealmInput;
import nl.sense_os.realmwithrx.realm.objects.RealmModule;

/**
 * Created by panjiyudasetya on 2/23/17.
 */

public class RealmService {
    private static final String CONFIG_NAME = "nl.sense_os.realmwithrx";
    protected RealmConfiguration defaultConfiguration;

    protected RealmService() {}

    public RealmService(Context context) {
        Realm.init(context);
        defaultConfiguration = new RealmConfiguration.Builder()
                .modules(new RealmModule())
                .name(CONFIG_NAME)
                .inMemory()
                .build();
        Realm.setDefaultConfiguration(defaultConfiguration);
    }

    public void createOrUpdateInput(@NonNull Input input) {
        Realm realm = getRealmInstance();
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(InputMapper.toRealm(input));
            realm.commitTransaction();
        } finally {
            realm.close();
        }
    }

    public List<Input> getInputs() {
        Realm realm = getRealmInstance();
        try {
            RealmResults<RealmInput> realmInputs = realm.where(RealmInput.class).findAll();
            List<Input> inputs = new ArrayList<>();
            if (realmInputs != null) inputs = InputMapper.toList(realmInputs);
            return inputs;
        } finally {
            realm.close();
        }
    }

    public void deleteRealm() {
        try {
            Realm.getInstance(defaultConfiguration).close();
            Realm.deleteRealm(defaultConfiguration);
        } catch (Exception ex) {
            if (BuildConfig.DEBUG) {
                ex.printStackTrace();
            }
        }
    }

    private Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }
}
