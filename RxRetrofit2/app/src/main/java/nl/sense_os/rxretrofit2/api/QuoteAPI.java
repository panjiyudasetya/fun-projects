package nl.sense_os.rxretrofit2.api;

import io.reactivex.Observable;
import nl.sense_os.rxretrofit2.api.response.Quote;
import retrofit2.http.GET;

/**
 * Created by panjiyudasetya on 1/16/17.
 */

public interface QuoteAPI {
    @GET("?cat=movies")
    Observable<Quote> getQuote();
}
