package com.spp.varjyasarathi;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class socialAdapter extends RecyclerView.Adapter<socialAdapter.ViewHolder> {

    private ArrayList<Users> localDataSet;
    private OnPostListener monPostListener;
    String musername;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView title;
        private final TextView confidence;
        private final TextView rank;
        OnPostListener onPostListener;
        String username;

        public ViewHolder(View view , OnPostListener onPostListener, String username) {
            super(view);
            // Define click listener for the ViewHolder's View
            this.onPostListener = onPostListener;
            this.username = username;

            title = (TextView) view.findViewById(R.id.tv_title_post_ma);
            confidence = (TextView) view.findViewById(R.id.tv_rv_confidence);
            rank = (TextView) view.findViewById(R.id.textView6);


            view.setOnClickListener(this);


        }

        public TextView getTitle() {
            return title;
        }
        public TextView getConfidence() {
            return confidence;
        }
        public TextView getRank() {
            return rank;
        }

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }

    }

    public interface OnPostListener{
        void onPostClick(int position);

        void onLikeCLick(int position , ImageView iv);
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public socialAdapter(ArrayList<Users> dataSet, OnPostListener onPostListener , String username) {
        localDataSet = dataSet;
        this.monPostListener = onPostListener;
        this.musername = username;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item_social, viewGroup, false);



        return new ViewHolder(view , monPostListener, musername );
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getRank().setText( String.valueOf(position+1));
        viewHolder.getTitle().setText(localDataSet.get(position).personName);
        viewHolder.getConfidence().setText( localDataSet.get(position).vsp + " VSP");
        if(musername.equals(localDataSet.get(position).personId)){
            viewHolder.getTitle().setBackgroundResource(R.drawable.gradiant_shadow_border_p);
        }
        else{
            viewHolder.getTitle().setBackgroundResource(R.drawable.round_corner_black_border_white_bg);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
