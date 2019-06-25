package com.example.projektma;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreadListAdapter extends RecyclerView.Adapter<ThreadListAdapter.ThreadListViewHolder> {

    private ArrayList<String> datasetTitle = new ArrayList<String>();
    private ArrayList<Integer> datasetTID = new ArrayList<Integer>();

    /*
    Is created for each Item in the list, more attributes may be added depending on how many
    need to be displayed per item
     */
    public static class ThreadListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout itemLayout;
        public ThreadListViewHolder(LinearLayout v) {
            super(v);
            itemLayout = v;
        }
    }

    public ThreadListAdapter(ArrayList<String> datasetTitle, ArrayList<Integer> datasetTID) {
        this.datasetTitle = datasetTitle;
        this.datasetTID = datasetTID;
    }

    // Create new views (invoked by the post_layout manager)
    @Override
    public ThreadListAdapter.ThreadListViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.thread_layout, parent, false);

        ThreadListViewHolder vh = new ThreadListViewHolder(v);
        return vh;
    }

    class OnClickListenerThreadBtn implements View.OnClickListener {
        private int tid;
        private String threadTitle;

        public OnClickListenerThreadBtn(int tid, String threadTitle){
            this.tid = tid;
            this.threadTitle = threadTitle;
        }

        public void onClick(View v){
            Intent intent = new Intent(GroupActivity.groupActivity, ThreadActivity.class);
            intent.putExtra("tid", tid);
            intent.putExtra("threadTitle", threadTitle);
            GroupActivity.groupActivity.startActivity(intent);
        }
    }


    // Replace the contents of a view (invoked by the post_layout manager)
    @Override
    public void onBindViewHolder(ThreadListAdapter.ThreadListViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((TextView) holder.itemLayout.findViewById(R.id.textViewTitle)).setText(datasetTitle.get(position));
        (holder.itemLayout.findViewById(R.id.textViewTitle))
                .setOnClickListener(new OnClickListenerThreadBtn(datasetTID.get(position), datasetTitle.get(position)));
    }

    // Return the size of your dataset (invoked by the thread_layout manager)
    @Override
    public int getItemCount() {
        return datasetTitle.size();
    }
}
