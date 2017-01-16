package nl.sense_os.rxretrofit2.api;

import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by panjiyudasetya on 1/16/17.
 */

public abstract class APIDisposableObserver<T> extends DisposableObserver<T> {
    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            onAPIError(new Exception(getMessage(exception.code(), "Unknown error.")));
        } else if (e instanceof UnknownHostException) {
            onAPIError(new Exception("Server not available."));
        }
    }

    /**
     * Exception while API Error happened.
     *
     * @param e Exception
     */
    public abstract void onAPIError(@NonNull Exception e);

    /**
     * Generate human readable error based on error network type.
     *
     * @param errorCode http error code
     * @param defaultMessage default error message
     * @return Error message
     */
    @NonNull
    private String getMessage(int errorCode, @NonNull String defaultMessage) {
        switch (errorCode) {
            case 400 : return "Bad Request.";
            case 401 : return "Unauthorized";
            case 403 : return "Forbidden";
            case 404 : return "Not Found";
            case 408 : return "Request Time Out";
            default  : return defaultMessage;
        }
    }
}
