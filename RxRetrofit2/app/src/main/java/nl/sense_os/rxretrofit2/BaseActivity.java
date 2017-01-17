package nl.sense_os.rxretrofit2;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by panjiyudasetya on 1/16/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity context;
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private View container;
    private FloatingActionButton fab;

    /**
     * Initialize activity with layout ID.
     *
     * @return Used layout ID
     */
    protected abstract int initWithLayout();
    protected View.OnClickListener initWithFabListener() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(initWithLayout());
        initToolbar();
        initView();
    }

    /** Initialize view environment. */
    protected void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progress);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        container = findViewById(R.id.container);
        if (fab != null && initWithFabListener() != null) {
            fab.setOnClickListener(initWithFabListener());
        }
    }

    /**
     * Enable disable floating action button.
     *
     * @param enable True if enable, False otherwise
     */
    protected void enableFab(boolean enable) {
        if (fab != null) {
            fab.setEnabled(enable);
            if (enable) {
                fab.setOnClickListener(initWithFabListener());
            } else {
                fab.setOnClickListener(null);
            }
        }
    }

    /**
     * Set action bar progress visibility
     *
     * @param visibility view visibility
     */
    protected void setABProgressVisibility(int visibility) {
        if (progressBar != null) {
            progressBar.setVisibility(visibility);
        }
    }

    /**
     * Show message into snackbar content
     *
     * @param message displayed message
     */
    protected void showMessage(@NonNull String message) {
        if (container != null) {
            Snackbar snackbar = Snackbar.make(container, message, Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    /** Initialize toolbar if possible. */
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(ContextCompat.getColor(context, android.R.color.white));
        }
    }
}
