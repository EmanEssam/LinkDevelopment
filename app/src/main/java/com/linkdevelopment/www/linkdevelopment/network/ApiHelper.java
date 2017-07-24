package com.linkdevelopment.www.linkdevelopment.network;

import android.content.Context;
import android.widget.Toast;

import com.linkdevelopment.www.linkdevelopment.models.Article;
import com.linkdevelopment.www.linkdevelopment.models.ArticleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Eman on 19/07/2017.
 */
public class ApiHelper {
    public static GetArticles getArticles;

    public static void getArticles(final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface service = retrofit.create(ApiInterface.class);
        Call<ArticleResponse> articles = service.getArticles(Constants.API_KEY);

        articles.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                getArticles.onSuccess(response.body().getArticles(), context);
            }


            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                getArticles.onFailed(t.getMessage());
            }
        });
    }

    public interface GetArticles {
        void onSuccess(List<Article> articles, Context context);

        void onFailed(String error);

    }

    public static void setArticleInterface(GetArticles articles) {
        getArticles = articles;
    }

}
