package com.example.hemant.gitpullrequest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class PrLoader extends AsyncTaskLoader<List<PullRequest>> {

    private String mUrl;

    PrLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<PullRequest> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchPrData(mUrl);
    }
}
