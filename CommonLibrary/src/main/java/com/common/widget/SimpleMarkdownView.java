package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.mukesh.MarkdownView;

/**
 * by y on 2017/5/17
 */

public class SimpleMarkdownView extends MarkdownView {

    private ProgressBar progressbar;
    private boolean isLoading = false;

    public SimpleMarkdownView(Context context) {
        super(context);
        init();
    }

    public SimpleMarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleMarkdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        progressbar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new WebView.LayoutParams(WebView.LayoutParams.MATCH_PARENT, 26, 0, 0));
        addView(progressbar);
        setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        super.onProgressChanged(view, newProgress);
                        newProgressBar(newProgress);
                    }
                });
    }

    public boolean isLoading() {
        return isLoading;
    }


    private void newProgressBar(int newProgress) {
        if (newProgress == 100) {
            progressbar.setVisibility(GONE);
            isLoading = true;
        } else {
            if (progressbar.getVisibility() == GONE) {
                progressbar.setVisibility(VISIBLE);
            }
            isLoading = false;
            progressbar.setProgress(newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (progressbar != null) {
            WebView.LayoutParams lp = (WebView.LayoutParams) progressbar.getLayoutParams();
            lp.x = l;
            lp.y = t;
            progressbar.setLayoutParams(lp);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
