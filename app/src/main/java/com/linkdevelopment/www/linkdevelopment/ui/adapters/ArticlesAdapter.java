package com.linkdevelopment.www.linkdevelopment.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkdevelopment.www.linkdevelopment.R;
import com.linkdevelopment.www.linkdevelopment.models.Article;

import java.text.DateFormat;;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Eman on 01/07/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {
    private ArrayList<Article> mFilteredList;
    private Context mContext;
    private List<Article> mArticleList = new ArrayList<>();
    private OnListClickListener onListClickListener;

    public ArticlesAdapter() {
    }

    public ArticlesAdapter(Context mContext, List<Article> mArticleList, OnListClickListener onListClickListener) {
        this.mContext = mContext;
        this.mArticleList = mArticleList;
        this.onListClickListener = onListClickListener;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_row, parent, false);
        ArticleViewHolder vh = new ArticleViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Glide.with(mContext).load(mArticleList.get(position).getUrlToImage()).placeholder(R.drawable.placeholder).dontAnimate().into(holder.article_image_view);
        holder.mArticleTitle.setText(mArticleList.get(position).getTitle());
        holder.mAuthorName.setText(mContext.getString(R.string.by) + " " + mArticleList.get(position).getAuthor());
        Date date = new Date();
        String stringDate = DateFormat.getDateInstance().format(date);
        holder.mArticleDate.setText(stringDate);


    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        public ImageView article_image_view;
        public TextView mArticleDate, mAuthorName, mArticleTitle;


        public ArticleViewHolder(final View itemView) {
            super(itemView);
            article_image_view = (ImageView) itemView.findViewById(R.id.article_image_view);
            mArticleDate = (TextView) itemView.findViewById(R.id.article_date_tv);
            mArticleTitle = (TextView) itemView.findViewById(R.id.article_title_tv);
            mAuthorName = (TextView) itemView.findViewById(R.id.author_name_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onListClickListener.onListItemClicked(getAdapterPosition());
                }
            });
        }
    }

    public interface OnListClickListener {
        void onListItemClicked(int ClickedItemPosition);
    }

    public void setFilter(List<Article> countryModels) {
        mFilteredList = new ArrayList<>();
        mFilteredList.addAll(countryModels);
        notifyDataSetChanged();
    }

}
