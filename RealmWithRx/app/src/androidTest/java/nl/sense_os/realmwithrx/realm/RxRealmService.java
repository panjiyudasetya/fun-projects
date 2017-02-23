package nl.sense_os.realmwithrx.realm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import nl.sense_os.realmwithrx.realm.objects.RealmModule;

/**
 * Created by panjiyudasetya on 2/23/17.
 */

public class RxRealmService extends RealmService {

    public RxRealmService(Context context) {
        super(context);
        Realm.init(context);
    }

    @VisibleForTesting
    public RxRealmService(Context context, String name) {
        Realm.init(context);
        defaultConfiguration = new RealmConfiguration.Builder()
                .modules(new RealmModule())
                .name(name)
                .inMemory()
                .build();
        Realm.setDefaultConfiguration(defaultConfiguration);
    }

    public Observable<Boolean> rxCreateOrUpdateInput(@NonNull final Input input) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Realm realm = Realm.getInstance(defaultConfiguration);
                try {
                    createOrUpdateInput(input);
                    return Boolean.TRUE;
                } finally {
                    realm.close();
                }
            }
        });
    }

    public Observable<List<Input>> rxGetInputs() {
        return Observable.fromCallable(new Callable() {
            @Override
            public List<Input> call() throws Exception {
                Realm realm = Realm.getInstance(defaultConfiguration);
                try {
                    return getInputs();
                } finally {
                    realm.close();
                }
            }
        });
    }

    public Observable<List<Input>> rxCreateAndGetInputs(@NonNull final Input input) {
        return Observable.fromCallable(new Callable() {
            @Override
            public List<Input> call() throws Exception {
                Realm realm = Realm.getInstance(defaultConfiguration);
                try {
                    createOrUpdateInput(input);
                    return getInputs();
                } finally {
                    realm.close();
                }
            }
        });
    }
}
