package com.example.hemant.gitpullrequest;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class DiffLoader extends AsyncTaskLoader<List<String>> {

    private String mUrl;

    DiffLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<String> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        return QueryUtils.fetchDiffData(mUrl);
    }
}
