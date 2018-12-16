package com.example.hemant.gitpullrequest;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    private TextView screenRotationRequestTextView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private DiffAdapter mAdapter;
    private ArrayList<String> mDiffLines = new ArrayList<>();
    private static final int DIFF_REQUEST_LOADER_ID = 0;
    private String diffUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        diffUrl = getIntent().getStringExtra("PR");


        screenRotationRequestTextView = findViewById(R.id.screen_rotation_request_text);
        screenRotationRequestTextView.setVisibility(View.GONE);

        mRecyclerView = findViewById(R.id.recycler_view_detail_screen);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DiffAdapter(getApplicationContext(), mDiffLines);
        mRecyclerView.setAdapter(mAdapter);

        if (NetworkUtils.isConnectedToNetwork(getApplicationContext())) {
            getSupportLoaderManager().initLoader(DIFF_REQUEST_LOADER_ID, null, this);
        }


        TextView textView = findViewById(R.id.diff_url);
        textView.setText(diffUrl);

    }

    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(diffUrl);
        Uri.Builder builder = baseUri.buildUpon();
        return new DiffLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> data) {
        mDiffLines.clear();
        if (data != null && !data.isEmpty()){
            mDiffLines.addAll(data);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {
        mDiffLines.clear();
        mAdapter.notifyDataSetChanged();
    }
}
