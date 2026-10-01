package com.jofano.jmmotoshop.admin;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private FrameLayout root;
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureWindow();

        try {
            createLayout();
            configureWebView();

            if (savedInstanceState == null) {
                loadAdmin();
            } else {
                webView.restoreState(savedInstanceState);
            }
        } catch (Throwable error) {
            showStartupError(error);
        }
    }

    private void configureWindow() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setStatusBarColor(Color.rgb(15, 26, 19));
        window.setNavigationBarColor(Color.rgb(15, 26, 19));
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(true);
            WindowInsetsController controller = window.getInsetsController();
            if (controller != null) {
                controller.show(
                        WindowInsets.Type.statusBars()
                                | WindowInsets.Type.navigationBars()
                );
                controller.setSystemBarsAppearance(0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                                | WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
            }
        } else {
            window.getDecorView().setSystemUiVisibility(0);
        }
    }

    private void createLayout() {
        root = new FrameLayout(this);
        root.setFitsSystemWindows(true);
        root.setBackgroundColor(Color.rgb(238, 242, 239));

        webView = new WebView(this);
        webView.setBackgroundColor(Color.rgb(238, 242, 239));
        root.addView(webView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        progressBar = new ProgressBar(
                this,
                null,
                android.R.attr.progressBarStyleHorizontal
        );
        progressBar.setMax(100);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);

        FrameLayout.LayoutParams progressParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                dp(3)
        );
        progressParams.gravity = android.view.Gravity.TOP;
        root.addView(progressBar, progressParams);

        setContentView(root);
    }

    private void configureWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(false);
        settings.setTextZoom(100);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setLoadWithOverviewMode(false);
        settings.setUseWideViewPort(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        String userAgent = settings.getUserAgentString();
        settings.setUserAgentString(userAgent + " JM-Motoshop-Admin/1.0.1");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            settings.setAlgorithmicDarkeningAllowed(false);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            settings.setForceDark(WebSettings.FORCE_DARK_OFF);
        }

        CookieManager cookies = CookieManager.getInstance();
        cookies.setAcceptCookie(true);
        cookies.setAcceptThirdPartyCookies(webView, true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (progressBar == null) {
                    return;
                }

                progressBar.setProgress(newProgress);
                progressBar.setVisibility(
                        newProgress >= 100 ? View.GONE : View.VISIBLE
                );
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view,
                    WebResourceRequest request
            ) {
                Uri uri = request.getUrl();
                if (uri == null) {
                    return false;
                }

                String scheme = uri.getScheme();
                return !("http".equalsIgnoreCase(scheme)
                        || "https".equalsIgnoreCase(scheme));
            }

            @Override
            public void onReceivedError(
                    WebView view,
                    WebResourceRequest request,
                    WebResourceError error
            ) {
                super.onReceivedError(view, request, error);

                if (request.isForMainFrame()) {
                    String description = error == null
                            ? "Koneksi gagal"
                            : String.valueOf(error.getDescription());
                    showWebError(description);
                }
            }
        });
    }

    private void loadAdmin() {
        if (webView != null) {
            webView.loadUrl(BuildConfig.ADMIN_URL);
        }
    }

    private void showWebError(String detail) {
        Toast.makeText(
                this,
                "Tidak dapat membuka JM Motoshop Admin: " + detail,
                Toast.LENGTH_LONG
        ).show();

        if (webView == null) {
            return;
        }

        String html = "<!doctype html>"
                + "<html><head><meta name='viewport' content='width=device-width,initial-scale=1'>"
                + "<style>body{font-family:sans-serif;background:#eef2ef;padding:28px;color:#142018}"
                + "button{background:#a8ff1e;border:0;border-radius:12px;padding:14px 18px;font-weight:700}"
                + "</style></head><body>"
                + "<h2>JM Motoshop Admin</h2>"
                + "<p>Koneksi ke server gagal.</p>"
                + "<p>Periksa koneksi internet lalu tekan Coba Lagi.</p>"
                + "<button onclick=\"location.href='" + BuildConfig.ADMIN_URL + "'\">Coba Lagi</button>"
                + "</body></html>";

        webView.loadDataWithBaseURL(
                BuildConfig.ADMIN_URL,
                html,
                "text/html",
                "UTF-8",
                null
        );
    }

    private void showStartupError(Throwable error) {
        TextView message = new TextView(this);
        message.setBackgroundColor(Color.rgb(238, 242, 239));
        message.setTextColor(Color.rgb(30, 35, 30));
        message.setTextSize(16);
        message.setPadding(dp(24), dp(32), dp(24), dp(24));
        message.setText(
                "JM Motoshop Admin tidak dapat memulai WebView.\n\n"
                        + "Pastikan Android System WebView / Google Chrome aktif dan terbaru.\n\n"
                        + "Detail: " + error.getClass().getSimpleName()
        );
        setContentView(message);
    }

    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (webView != null) {
            webView.saveState(outState);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        CookieManager.getInstance().flush();
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }
}
