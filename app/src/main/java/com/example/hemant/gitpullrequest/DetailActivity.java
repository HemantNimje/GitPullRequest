package com.example.hemant.gitpullrequest;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<String>> {

    private ProgressBar mProgressBar;
    private DiffAdapter mAdapter;
    private ArrayList<String> mDiffLines = new ArrayList<>();
    private static final int DIFF_REQUEST_LOADER_ID = 0;
    private String diffUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Set navigation up button
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        diffUrl = getIntent().getStringExtra("PR");

        mProgressBar = findViewById(R.id.progress_bar_detail_screen);

        // Bind the recyclerView
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view_detail_screen);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DiffAdapter(getApplicationContext(), mDiffLines);
        mRecyclerView.setAdapter(mAdapter);

        // Load the information if connected to internet.
        if (NetworkUtils.isConnectedToNetwork(getApplicationContext())) {
            getSupportLoaderManager().initLoader(DIFF_REQUEST_LOADER_ID, null, this);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    /**
     * Create new instance of loader to fetch the data
     */
    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(diffUrl);
        Uri.Builder builder = baseUri.buildUpon();
        return new DiffLoader(this, builder.toString());
    }

    /**
     * Update the pullRequestList when loader is finished fetching data and notify adapter to
     * reflect UI
     */
    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> data) {

        mProgressBar.setVisibility(View.GONE);

        // Update the list of difference and notify the DiffAdapter
        mDiffLines.clear();
        if (data != null && !data.isEmpty()) {
            mDiffLines.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Clear the DiffList on loaderReset
     */
    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {
        mDiffLines.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
