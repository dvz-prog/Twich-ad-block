package com.twitchadb.app;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;

public class TwitchWebViewClient extends WebViewClient {

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();

        if (AdBlocker.isAdUrl(url)) {
            return new WebResourceResponse(
                "text/plain",
                "utf-8",
                new ByteArrayInputStream("".getBytes())
            );
        }
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        view.evaluateJavascript(AdBlocker.getAdBlockScript(), null);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        view.evaluateJavascript(AdBlocker.getAdBlockScript(), null);
    }
}
