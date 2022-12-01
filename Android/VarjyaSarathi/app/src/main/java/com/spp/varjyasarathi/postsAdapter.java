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

public class postsAdapter extends RecyclerView.Adapter<postsAdapter.ViewHolder> {

    private ArrayList<Dustbins> localDataSet;
    private OnPostListener monPostListener;
    String musername;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView title;
        private final TextView des;
        private final TextView confidence;
        private final TextView location;
        private final ConstraintLayout cl;
        OnPostListener onPostListener;
        String username;

        public ViewHolder(View view , OnPostListener onPostListener, String username) {
            super(view);
            // Define click listener for the ViewHolder's View
            this.onPostListener = onPostListener;
            this.username = username;

            title = (TextView) view.findViewById(R.id.tv_title_post_ma);
            des = (TextView) view.findViewById(R.id.tv_des_ma);
            confidence = (TextView) view.findViewById(R.id.tv_rv_confidence);
            location = (TextView) view.findViewById(R.id.tv_rv_location);
            cl = (ConstraintLayout) view.findViewById(R.id.rv_main);

            view.setOnClickListener(this);


        }

        public TextView getTitle() {
            return title;
        }
        public TextView getDes() {
            return des;
        }
        public TextView getConfidence() {
            return confidence;
        }
        public TextView getLocation() {
            return location;
        }
        public ConstraintLayout getLayout() {
            return cl;
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
    public postsAdapter(ArrayList<Dustbins> dataSet, OnPostListener onPostListener , String username) {
        localDataSet = dataSet;
        this.monPostListener = onPostListener;
        this.musername = username;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);



        return new ViewHolder(view , monPostListener, musername );
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTitle().setText(localDataSet.get(position).DustbinID+"...");
        viewHolder.getDes().setText(String.valueOf(localDataSet.get(position).R_RODs));
        viewHolder.getConfidence().setText( localDataSet.get(position).Type);
        viewHolder.getLocation().setText(localDataSet.get(position).Lat + " , " + localDataSet.get(position).Long );

        if(localDataSet.get(position).R_RODs > 8 ){
            viewHolder.getDes().setBackgroundResource(R.drawable.gradiant_shadow_border);
        }
        else if(localDataSet.get(position).R_RODs > 4){
            viewHolder.getDes().setBackgroundResource(R.drawable.gradiant_shadow_border_p);
        }
        else{
            viewHolder.getDes().setBackgroundResource(R.drawable.gradiant_shadow_border_s);
        }

        Log.d("yolopel" , localDataSet.get(position).Type);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}
