package nl.sense_os.rxretrofit2;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import nl.sense_os.rxretrofit2.api.data.Quote;
import nl.sense_os.rxretrofit2.mvp.quote.QuotePageContract;
import nl.sense_os.rxretrofit2.mvp.quote.QuotePresenter;
import nl.sense_os.rxretrofit2.mvp.quote.QuoteUseCase;

public class MainActivity extends BaseActivity implements QuotePageContract.View {
    private TextView tvQuote;
    private TextView tvAuthor;

    private QuotePresenter presenter;

    @Override
    public int initWithLayout() {
        return R.layout.activity_main;
    }

    @Override
    public View.OnClickListener initWithFabListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.obtainDataFromAPI();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new QuotePresenter(this, new QuoteUseCase());
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.obtainDataFromAPI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.release();
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
        updateTextWithQuote(null);
    }

    @Override
    public void onPostExecuteAPI() {
        setABProgressVisibility(View.GONE);
        enableFab(true);
    }

    @Override
    public void updateTextWithQuote(@Nullable Quote quote) {
        if (quote == null) {
            tvQuote.setText(getString(R.string.please_wait));
            tvAuthor.setVisibility(View.GONE);
        } else {
            tvQuote.setText(quote.getQuote());
            tvAuthor.setText(String.format("― %s", quote.getAuthor()));
            tvAuthor.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMessageWithSnackbar(@NonNull String message) {
        showMessage(message);
    }
}
