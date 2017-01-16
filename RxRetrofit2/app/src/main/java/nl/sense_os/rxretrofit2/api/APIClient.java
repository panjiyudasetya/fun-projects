package nl.sense_os.rxretrofit2.api;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by panjiyudasetya on 1/16/17.
 */

public class APIClient extends APIConfigurations {
    public APIClient() {
        super();
    }

    /**
     * Get random Quote from API Client
     *
     * @param observer observer
     * @return Disposable
     */
    public Disposable getQuote(APIDisposableObserver observer) {
        return APIService.getQuote()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
    }
}