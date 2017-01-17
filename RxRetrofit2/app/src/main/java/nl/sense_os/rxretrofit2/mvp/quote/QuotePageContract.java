package nl.sense_os.rxretrofit2.mvp.quote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import nl.sense_os.rxretrofit2.api.data.Quote;
import nl.sense_os.rxretrofit2.mvp.BasePresenter;

/**
 * Created by panjiyudasetya on 1/17/17.
 */

public interface QuotePageContract {
    interface View {
        void onPreExecuteAPI();
        void onPostExecuteAPI();
        void updateTextWithQuote(@Nullable Quote quote);
        void showMessageWithSnackbar(@NonNull String message);
    }

    interface Presenter extends BasePresenter {
        void obtainDataFromAPI();
    }
}
