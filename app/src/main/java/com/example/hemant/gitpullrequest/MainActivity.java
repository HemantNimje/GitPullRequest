package com.example.hemant.gitpullrequest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<PullRequest>> {

    private static final String PULL_REQUEST_URL = "https://api.github.com/repos/mit-cml/appinventor-sources/pulls";

    private static final int PULL_REQUEST_LOADER_ID = 0;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PrListAdapter mAdapter;
    private ArrayList<PullRequest> mPullRequests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the recyclerView
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PrListAdapter(getApplicationContext(), mPullRequests);
        mRecyclerView.setAdapter(mAdapter);

        // Load the information when connected to internet else notify user about network loss.
        if (isConnectedToNetwork()) {
            getSupportLoaderManager().initLoader(PULL_REQUEST_LOADER_ID, null, this);
        } else {
            //TODO Show no internet connection
        }
    }

    /**
     * Called when the loader is created.
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
     * Called when loader finish fetching data
     */
    @Override
    public void onLoadFinished(@NonNull Loader<List<PullRequest>> loader, List<PullRequest> requests) {
        // update the list of pull request and notify adapter
        mPullRequests.clear();
        if (requests != null && !requests.isEmpty()) {
            mPullRequests.addAll(requests);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Called when the loader is reset
     */
    @Override
    public void onLoaderReset(@NonNull Loader<List<PullRequest>> loader) {
        mAdapter.clear();
    }

    /**
     * Checks if connected to the network
     *
     * @return boolean which indicated if the device is connected to the internet
     */
    public boolean isConnectedToNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
