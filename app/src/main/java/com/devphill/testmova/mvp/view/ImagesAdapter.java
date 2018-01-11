package com.devphill.testmova.mvp.view;

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

import java.text.SimpleDateFormat;
import java.util.List;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder>  {

    private static final String LOG_TAG = "ImagesAdapterTag";

    private static Context mContext;
    private List<HistoryItem> historyItems;

    public interface ImagesListener{

        void onItemClick(int position);
    }

    ImagesListener imagesListener;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView phrase,detail,date;
        private ImageView imageView,delete;

        public MyViewHolder(View v) {
            super(v);
            this.phrase =  v.findViewById(R.id.phrase);
            this.detail = v.findViewById(R.id.detail);
            this.date =  v.findViewById(R.id.date);
            this.imageView =  v.findViewById(R.id.image);
            this.delete =  v.findViewById(R.id.delete);

        }
    }

    public ImagesAdapter(Context context, List<HistoryItem> list,ImagesListener imagesListener) {

        mContext = context;
        historyItems = list;
        this.imagesListener = imagesListener;
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

        Glide.with(mContext)
             .load(historyItem.getImage_url())
             .centerCrop()
             .into(viewHolder.imageView);                                       //загружаем картинку

        viewHolder.phrase.setText("Запрос: " + historyItem.getPhrase());        //выводим фразу запрса

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String strTime = simpleDateFormat.format(historyItem.getCurent_ms());   //когда был запрос

        viewHolder.date.setText(strTime);
        viewHolder.detail.setText("Детали: " + historyItem.getCaption());       //детали запроса

        //слушатель клика по корзинке
        viewHolder.delete.setOnClickListener(view -> imagesListener.onItemClick(position));

    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

}
