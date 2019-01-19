package com.example.hemant.gitpullrequest;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PrListAdapter extends RecyclerView.Adapter<PrListAdapter.PrListAdapterViewHolder> {

    private Context mContext;
    private List<PullRequest> mPullRequests;
    private OnItemClickListener mClickListener;

    /**
     * Constructor
     */
    PrListAdapter(Context context, ArrayList<PullRequest> pullRequests) {
        mContext = context;
        mPullRequests = pullRequests;
    }

    @NonNull
    @Override
    public PrListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewTypw) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        view.setFocusable(true);
        return new PrListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrListAdapterViewHolder holder, int position) {
        PullRequest currentPullRequest = mPullRequests.get(position);

        String title = currentPullRequest.getTitle();
        holder.titleView.setText(title);

        String avatarUrl = currentPullRequest.getAvatarUrl();
        Picasso.with(mContext).load(Uri.parse(avatarUrl)).into(holder.avatarView);

        int issueNumber = currentPullRequest.getIssueNumber();
        holder.issueNumberView.setText("#".concat(String.valueOf(issueNumber)));
    }

    @Override
    public int getItemCount() {
        if (mPullRequests == null) return 0;
        return mPullRequests.size();
    }

    public class PrListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleView;
        CircleImageView avatarView;
        TextView issueNumberView;

        PrListAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.list_item_title);
            avatarView = itemView.findViewById(R.id.list_item_avatar);
            issueNumberView = itemView.findViewById(R.id.list_item_issue_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.onClick(v, getAdapterPosition());
            }
        }
    }

    void setClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }
}
