package com.example.hemant.gitpullrequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiffAdapter extends RecyclerView.Adapter<DiffAdapter.DiffAdapterViewHolder> {

    private Context mContext;
    private List<String> mDiffLines;

    public DiffAdapter(Context context, ArrayList<String> diffLines) {
        mContext = context;
        mDiffLines = diffLines;
    }

    @NonNull
    @Override
    public DiffAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_commit_line, parent, false);
        view.setFocusable(true);
        return new DiffAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiffAdapterViewHolder holder, int position) {
        String diffLine = mDiffLines.get(position);

        //TODO use better regex in future to optimize the binding of views
        if (diffLine.startsWith("---") || diffLine.startsWith("+++")) {
            holder.fromTextView.setText(diffLine);
            holder.toTextView.setText("");
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else if (diffLine.startsWith("-")) {
            holder.fromTextView.setText(diffLine);
            holder.toTextView.setText("");
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.red_200));
        } else if (diffLine.startsWith("+")) {
            holder.fromTextView.setText("");
            holder.toTextView.setText(diffLine);
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.green_200));
        } else if (diffLine.startsWith(" ") || diffLine.startsWith("\t")) {
            holder.fromTextView.setText(diffLine);
            holder.toTextView.setText(diffLine);
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        if (mDiffLines == null) return 0;
        return mDiffLines.size();
    }

    public class DiffAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView fromTextView, toTextView;

        public DiffAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            fromTextView = itemView.findViewById(R.id.from_text_view);
            toTextView = itemView.findViewById(R.id.to_text_view);
        }
    }
}
