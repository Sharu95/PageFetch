package me.kulam.pagefetch;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

public class WebActivity extends AppCompatActivity {

    private WebView webView;
    private TextView tViewMain;
    private TextView tViewURL;
    private static SimpleArcDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle getExtras = getIntent().getExtras();
        String url = getExtras.getString("url");
        String title = getExtras.getString("title");
        getSupportActionBar().setTitle(title.toUpperCase());

        //TODO: Toolbar settings, icons, modifications
        //TODO: Hide toolbar on scroll

        /*See https://android-arsenal.com/details/1/3066*/
        mDialog = new SimpleArcDialog(this);
        //set configs here
        ArcConfiguration ac = new ArcConfiguration(this);
        mDialog.setConfiguration(ac);
        mDialog.show();
        
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        WebActivityClient viewClient = new WebActivityClient();
        viewClient.shouldOverrideUrlLoading(webView, url);
        webView.setWebViewClient(viewClient);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Want to go home? */

        //TODO: Return home to homepage in webview
        if (item.getItemId() == R.id.home_btn){
            finish();
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }

    private static class WebActivityClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if(mDialog.isShowing()){
                mDialog.dismiss();
            }
            super.onPageFinished(view, url);
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
