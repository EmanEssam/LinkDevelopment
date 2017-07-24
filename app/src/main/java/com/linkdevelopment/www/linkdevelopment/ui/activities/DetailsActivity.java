package com.linkdevelopment.www.linkdevelopment.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkdevelopment.www.linkdevelopment.R;
import com.linkdevelopment.www.linkdevelopment.models.Article;
import com.linkdevelopment.www.linkdevelopment.network.Constants;

import java.text.DateFormat;
import java.util.Date;

public class DetailsActivity extends AppCompatActivity {
    private TextView mArticleTitle, mArticleDescription, mArticleAuthor, mArticleDate;
    private ImageView mArticleImageView;
    private Article article;
    private Button mOpenWebsiteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.activity_title).toUpperCase());
        article = getIntent().getExtras().getParcelable(Constants.MOVIE);
        intiViews();
        mArticleTitle.setText(article.getTitle());
        mArticleAuthor.setText(getString(R.string.by)+" "+ article.getAuthor());
        mArticleDescription.setText(article.getDescription());
        Glide.with(this).load(article.getUrlToImage()).placeholder(R.drawable.placeholder).into(mArticleImageView);
        mOpenWebsiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });
        Date date = new Date();
        String stringDate = DateFormat.getDateInstance().format(date);
        mArticleDate.setText(stringDate);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void intiViews() {
        mArticleTitle = (TextView) findViewById(R.id.article_details_title_tv);
        mArticleDescription = (TextView) findViewById(R.id.article_details_desc_tv);
        mArticleAuthor = (TextView) findViewById(R.id.author_name_details_tv);
        mArticleImageView = (ImageView) findViewById(R.id.article_image_view_details);
        mOpenWebsiteBtn = (Button) findViewById(R.id.open_website_btn);
        mArticleDate = (TextView) findViewById(R.id.article_date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
