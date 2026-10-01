package com.jofano.jmmotoshop;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setStatusBarColor(Color.rgb(17, 23, 17));
        window.setNavigationBarColor(Color.rgb(17, 23, 17));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(0); // icon status bar putih
        }

        web = new WebView(this);
        web.setBackgroundColor(Color.rgb(238, 242, 239));
        setContentView(web);

        // Android 15 (target SDK 35) menerapkan edge-to-edge. Padding ini memastikan
        // header aplikasi dimulai tepat DI BAWAH status bar/notifikasi HP.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            web.setOnApplyWindowInsetsListener((v, insets) -> {
                android.graphics.Insets bars = insets.getInsets(WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
                v.setPadding(0, bars.top, 0, bars.bottom);
                return WindowInsets.CONSUMED;
            });
        }

        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setTextZoom(100);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(web, true);

        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });

        Uri.Builder url = Uri.parse(BuildConfig.APP_URL).buildUpon();
        String token = BuildConfig.APP_TOKEN == null ? "" : BuildConfig.APP_TOKEN.trim();
        if (!token.isEmpty()) url.appendQueryParameter("token", token);
        web.loadUrl(url.build().toString());
    }

    @Override
    public void onBackPressed() {
        if (web != null && web.canGoBack()) web.goBack();
        else super.onBackPressed();
    }
}
