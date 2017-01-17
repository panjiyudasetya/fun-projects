package nl.sense_os.rxretrofit2.mvp.quote;

import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import nl.sense_os.rxretrofit2.api.APIDisposableObserver;
import nl.sense_os.rxretrofit2.api.data.Quote;

/**
 * Created by panjiyudasetya on 1/17/17.
 */

public class QuotePresenter implements QuotePageContract.Presenter {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private QuoteUseCase useCase;
    private QuotePageContract.View view;

    public QuotePresenter(@NonNull QuotePageContract.View view,
                          @NonNull QuoteUseCase useCase) {
        this.view = view;
        this.useCase = useCase;
    }

    @Override
    public void obtainDataFromAPI() {
        view.onPreExecuteAPI();
        compositeDisposable.add(useCase.getQuoteOfTheDay(new APIDisposableObserver<Quote>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.showMessageWithSnackbar(e.getLocalizedMessage());
                view.onPostExecuteAPI();
            }

            @Override
            public void onAPIError(@NonNull Exception e) {
                view.showMessageWithSnackbar(e.getLocalizedMessage());
                view.onPostExecuteAPI();
            }

            @Override
            public void onNext(Quote value) {
                view.updateTextWithQuote(value);
            }

            @Override
            public void onComplete() {
                view.onPostExecuteAPI();
            }
        }));
    }

    @Override
    public void init() {
    }

    @Override
    public void release() {
        compositeDisposable.clear();
        useCase = null;
    }
}
