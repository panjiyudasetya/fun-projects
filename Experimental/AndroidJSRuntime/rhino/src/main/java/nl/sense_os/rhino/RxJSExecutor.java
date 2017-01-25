package nl.sense_os.rhino;

import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by panjiyudasetya on 1/13/17.
 */

public class RxJSExecutor extends JSExecutor {

    /**
     * Create reactive {@link Disposable} action while execute {@link JSParam} as requirements of executing
     * java script on Android Runtime.
     *
     * @param jsParam Java script parameters. It contain some attributes and JS code.
     * @param observer Observer action
     * @return {@link Disposable}
     */
    public static Disposable execute(@NonNull JSParam jsParam,
                                     @NonNull DisposableObserver<JSONObject> observer) {
        return observableExecutor(jsParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }

    /**
     * Creating Observable method to execute {@link JSParam}
     *
     * @param jsParam Java script parameters. It contain some attributes and JS code.
     * @return {@link Observable}
     */
    private static Observable<JSONObject> observableExecutor(@NonNull final JSParam jsParam) {
        return Observable.defer(new Callable<ObservableSource<? extends JSONObject>>() {
            @Override
            public ObservableSource<? extends JSONObject> call() throws Exception {
                return Observable.just(execute(jsParam));
            }
        });
    }
}
