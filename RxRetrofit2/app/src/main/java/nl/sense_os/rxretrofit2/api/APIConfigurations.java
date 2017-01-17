package nl.sense_os.rxretrofit2.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import io.reactivex.schedulers.Schedulers;
import nl.sense_os.rxretrofit2.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API Configurations class
 *
 * Created by panjiyudasetya on 1/16/17.
 */

public class APIConfigurations {
    private static final String BASE_URL = "https://andruxnet-random-famous-quotes.p.mashape.com/";
    protected static RestAPI APIService;

    public APIConfigurations() {
        init();
    }

    public void init() {
        if (APIService == null) {
            // Initialize Http Logging
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel( BuildConfig.DEBUG ? Level.BODY : Level.NONE );

            // Initialize http client
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("X-Mashape-Key", "R7gYJVnJcpmshFcA48ATd3p9YVW1p13ktiDjsne43u46VF883t")
                                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                    .addHeader("Accept", "application/json")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging)
                    .build();

            // Initialize retrofit builder
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(httpClient)
                    .build();

            // Create API Service
            APIService = retrofit.create(RestAPI.class);
        }
    }
}
