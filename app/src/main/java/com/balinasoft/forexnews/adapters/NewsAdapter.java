package com.balinasoft.forexnews.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balinasoft.forexnews.R;
import com.balinasoft.forexnews.models.News;
import com.balinasoft.forexnews.utils.PicassoImageGetter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

/**
 * Created by Serega on 06.08.2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> news;
    private Context context;

    private Html.ImageGetter picassoImageGetter;

    public NewsAdapter(final Context context, List<News> news) {
        this.context = context;
        this.news = news;
        picassoImageGetter = new PicassoImageGetter(context);
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_news, parent, false);
        return new NewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        holder.cardView.setSelected(false);
        holder.tvDescription.setMaxLines(4);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if (view.isSelected()) {
                    holder.tvDescription.setMaxLines(Integer.MAX_VALUE);
                } else {
                    holder.tvDescription.setMaxLines(4);
                }
            }
        });


        holder.tvTitle.setText(news.get(position).getTitle());
        holder.tvDescription.setText(Html.fromHtml(news.get(position).getDescription(), picassoImageGetter, null));
        holder.tvPubDate.setText(news.get(position).getPubDate());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView tvTitle, tvDescription, tvPubDate;

        public NewsViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvPubDate = (TextView) itemView.findViewById(R.id.tvPubDate);
        }
    }
}

