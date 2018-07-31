package com.deuro.android.Fragments;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.deuro.android.R;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class Webview_Fragment extends Fragment implements ScreenShotable {
    private WebView webview;
    private ProgressDialog pd;
    public Webview_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview_, container, false);
        final String url = getArguments().getString("url");
        webview = view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        pd = ProgressDialog.show(getActivity(), "", "Please wait...", true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        if (!url.equalsIgnoreCase("")) {
            webview.loadUrl(url);
        }
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (pd != null) {
                    pd.dismiss();
                }
            }
        });
        return view;
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
