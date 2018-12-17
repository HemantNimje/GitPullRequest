package com.example.hemant.gitpullrequest;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<PullRequest>>, OnItemClickListener {

    private static final String PULL_REQUEST_URL = "https://api.github.com/repos/mit-cml/appinventor-sources/pulls?state=open";

    private static final int PULL_REQUEST_LOADER_ID = 0;

    private ProgressBar mProgressbar;
    private PrListAdapter mAdapter;
    private ArrayList<PullRequest> mPullRequests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressbar = findViewById(R.id.progress_bar_main_screen);
        TextView mNoInternetConnectionTextView = findViewById(R.id.no_internet_connection_text_view);

        // Bind the recyclerView
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view_main_screen);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PrListAdapter(getApplicationContext(), mPullRequests);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setClickListener(this);

        // Load the information if connected to internet else notify user about network loss.
        if (NetworkUtils.isConnectedToNetwork(getApplicationContext())) {
            getSupportLoaderManager().initLoader(PULL_REQUEST_LOADER_ID, null, this);
        } else {
            mProgressbar.setVisibility(View.GONE);
            mNoInternetConnectionTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Create new instance of loader to fetch the data
     */
    @NonNull
    @Override
    public Loader<List<PullRequest>> onCreateLoader(int i, @Nullable Bundle bundle) {
        Uri baseUri = Uri.parse(PULL_REQUEST_URL);
        Uri.Builder builder = baseUri.buildUpon();

        // Append the query parameters here

        return new PrLoader(this, builder.toString());
    }

    /**
     * Update the pullRequestList when loader is finished fetching data and notify adapter to
     * reflect UI
     */
    @Override
    public void onLoadFinished(@NonNull Loader<List<PullRequest>> loader, List<PullRequest> requests) {

        mProgressbar.setVisibility(View.GONE);

        // update the list of pull request and notify adapter
        mPullRequests.clear();
        if (requests != null && !requests.isEmpty()) {
            mPullRequests.addAll(requests);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Clear the pullRequestList on loader reset
     */
    @Override
    public void onLoaderReset(@NonNull Loader<List<PullRequest>> loader) {
        mPullRequests.clear();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Handle list item click
     */
    @Override
    public void onClick(View view, int position) {
        PullRequest currentPullRequest = mPullRequests.get(position);
        Intent detailsActivityIntent = new Intent(this, DetailActivity.class);
        detailsActivityIntent.putExtra("PR", currentPullRequest.getDiffUrl());
        startActivity(detailsActivityIntent);
    }
}
