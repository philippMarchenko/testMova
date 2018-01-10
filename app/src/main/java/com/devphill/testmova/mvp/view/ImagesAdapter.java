package com.devphill.testmova.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devphill.testmova.R;
import com.devphill.testmova.model_data.HistoryItem;
import com.devphill.testmova.model_data.Image;

import java.util.List;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder>  {

    private static final String LOG_TAG = "DecrlarationsAdapterTag";

    private static Context mContext;
    private Activity myActivity;
    private List<HistoryItem> historyItems;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView phrase;
        private ImageView imageView;

        public MyViewHolder(View v) {
            super(v);
            this.phrase = (TextView) v.findViewById(R.id.phrase);
            this.imageView = (ImageView) v.findViewById(R.id.image);

        }
    }

    public ImagesAdapter(Context context, Activity activity, List<HistoryItem> list) {

        mContext = context;
        myActivity = activity;
        historyItems = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.images_card_view, viewGroup, false);
        return new MyViewHolder(v);

    }
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position) {

        final HistoryItem historyItem = historyItems.get(position);

       // viewHolder.imageView.setImageBitmap(historyItem.getBitmap());

        Glide.with(mContext)
             .load(historyItem.getImage_url())
             .centerCrop()
             .into(viewHolder.imageView);

        viewHolder.phrase.setText(historyItem.getPhrase());

    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

}
