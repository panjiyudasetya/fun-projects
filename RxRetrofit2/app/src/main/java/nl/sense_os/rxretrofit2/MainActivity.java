package nl.sense_os.rxretrofit2;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import io.reactivex.disposables.CompositeDisposable;
import nl.sense_os.rxretrofit2.api.APIClient;
import nl.sense_os.rxretrofit2.api.APIDisposableObserver;
import nl.sense_os.rxretrofit2.api.response.Quote;

public class MainActivity extends BaseActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private TextView tvQuote;
    private TextView tvAuthor;
    private APIClient apiClient;

    @Override
    public int initWithLayout() {
        return R.layout.activity_main;
    }

    @Override
    public String initWithTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public View.OnClickListener initWithFabListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadQuote();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiClient = new APIClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadQuote();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    protected void initView() {
        super.initView();

        tvAuthor = (TextView) findViewById(R.id.tvAuthor);
        tvQuote  = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void onPreExecuteAPI() {
        setABProgressVisibility(View.VISIBLE);
        enableFab(false);
        setContentText(null);
    }

    @Override
    public void onPostExecuteAPI() {
        setABProgressVisibility(View.GONE);
        enableFab(true);
    }

    private void setContentText(@Nullable Quote quote) {
        if (quote == null) {
            tvQuote.setText(getString(R.string.please_wait));
            tvAuthor.setVisibility(View.GONE);
        } else {
            tvQuote.setText(quote.getQuote());
            tvAuthor.setText(String.format("― %s", quote.getAuthor()));
            tvAuthor.setVisibility(View.VISIBLE);
        }
    }

    private void loadQuote() {
        onPreExecuteAPI();
        compositeDisposable.add(apiClient.getQuote(new APIDisposableObserver<Quote>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showSnackbar(e.getLocalizedMessage());
            }

            @Override
            public void onAPIError(@NonNull Exception e) {
                showSnackbar(e.getMessage());
            }

            @Override
            public void onNext(Quote value) {
                setContentText(value);
            }

            @Override
            public void onComplete() {
                onPostExecuteAPI();
            }
        }));
    }
}
