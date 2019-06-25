package com.example.projektma;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> {


    public static final int TYPE_FIRST_ITEM = 0;
    public static final int TYPE_ITEM = 1;

    private List<String> datasetText;
    //List<String> myDataset;
    private List<String> datasetPoster;
    private List<String> datasetTimestamp;

    /*
    Is created for each Item in the list, more attributes may be added depending on how many
    need to be displayed per item
     */
    public static class PostListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout itemLayout;
        public PostListViewHolder(LinearLayout v) {
            super(v);
            itemLayout = v;
        }
    }

    public PostListAdapter(List<String> datasetPoster, List<String> datasetTimestamp, List<String> datasetText) {

        this.datasetPoster = datasetPoster;
        this.datasetTimestamp = datasetTimestamp;
        this.datasetText = datasetText;
    }

    // Create new views (invoked by the post_layout manager)
    @Override
    public PostListAdapter.PostListViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        LinearLayout v = null;
        switch (viewType) {
            case TYPE_FIRST_ITEM:
                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.first_post_layout, parent, false);
                break;

            case TYPE_ITEM:
                v = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.post_layout, parent, false);
                break;
        }

        PostListViewHolder vh = new PostListViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the post_layout manager)
    @Override
    public void onBindViewHolder(PostListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        switch (holder.getItemViewType()) {
            case TYPE_FIRST_ITEM:
                ((TextView) holder.itemLayout.findViewById(R.id.textViewUnameFirst)).setText(datasetPoster.get(position));
                ((TextView) holder.itemLayout.findViewById(R.id.textViewTimestampFirst)).setText(datasetTimestamp.get(position));
                ((TextView) holder.itemLayout.findViewById(R.id.textViewTextFirst)).setText(datasetText.get(position));
                break;

            case TYPE_ITEM:
                ((TextView) holder.itemLayout.findViewById(R.id.textViewUname)).setText(datasetPoster.get(position));
                ((TextView) holder.itemLayout.findViewById(R.id.textViewTimestamp)).setText(datasetTimestamp.get(position));
                ((TextView) holder.itemLayout.findViewById(R.id.textViewText)).setText(datasetText.get(position));
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRST_ITEM;
        } else return TYPE_ITEM;
    }

    // Return the size of your dataset (invoked by the post_layout manager)
    @Override
    public int getItemCount() {
        return datasetPoster.size();
    }

}
