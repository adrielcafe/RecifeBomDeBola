package com.adrielcafe.recifebomdebola.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.adrielcafe.recifebomdebola.App;
import com.adrielcafe.recifebomdebola.R;
import com.adrielcafe.recifebomdebola.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

public class JudgmentsFragment extends Fragment {
    private MainActivity activity;

    @Bind(R.id.web)
    WebView webView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_judgments, container, false);
        ButterKnife.bind(this, root);

        String url = Util.getPdfViewerUrl(App.JUDGMENTS_PDF_URL);
        if(Util.isConnected(activity)){
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    activity.setLoading(false);
                }
            });

            activity.setLoading(true);
            webView.loadUrl(url);
        } else {
            Toast.makeText(activity, R.string.connect_to_internet, Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}