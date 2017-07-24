package com.linkdevelopment.www.linkdevelopment.network;

import com.linkdevelopment.www.linkdevelopment.models.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Eman on 19/07/2017.
 */

public interface ApiInterface {
    @GET("articles?source=the-next-web")
    Call<ArticleResponse> getArticles(@Query("apiKey") String api_key);

}
