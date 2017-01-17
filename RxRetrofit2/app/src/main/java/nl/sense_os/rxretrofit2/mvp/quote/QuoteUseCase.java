package nl.sense_os.rxretrofit2.mvp.quote;

import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import nl.sense_os.rxretrofit2.api.APIClient;
import nl.sense_os.rxretrofit2.api.APIDisposableObserver;

/**
 * Created by panjiyudasetya on 1/17/17.
 */

public class QuoteUseCase {
    private APIClient apiClient;

    public QuoteUseCase() {
        this.apiClient = new APIClient();
    }

    public Disposable getQuoteOfTheDay(@NonNull APIDisposableObserver observer) {
        return apiClient.getQuote(observer);
    }
}
