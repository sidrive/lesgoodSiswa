package com.lesgood.app.ui.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;


import com.lesgood.app.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Agus on 5/13/17.
 */

public class WebViewActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.webView)
    WebView webView;

    @Bind(R.id.view_progress)
    LinearLayout viewProgress;

    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewProgress.setVisibility(View.VISIBLE);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            url = extras.getString("url");
        }else{
            return;
        }

        /* An instance of this class will be registered as a JavaScript interface */
        class MyJavaScriptInterface
        {


            public MyJavaScriptInterface()
            {

            }

            @SuppressWarnings("unused")

            public void processContent(String aContent)
            {
                final String content = aContent;
                Log.d("GETHTMLVALUE", aContent);
            }
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "INTERFACE");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                viewProgress.setVisibility(View.GONE);
            }
        });

        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
