package com.rokuta96.simpletodo.Model;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface SimpleToDoApi {

    String API_ENDPOINT = "https://5f9ymkv3c9.execute-api.ap-northeast-1.amazonaws.com";

    @GET("api?")
    Observable<Repositories> listToDo();

    @POST("api?")
    Observable<String> addToDo(@Query("priority") int priority, @Query("title") String title);

    @PUT("api?")
    Observable<String> updateToDo(@Query("id") int id, @Query("priority") int priority, @Query("title") String title);

    @DELETE("api?")
    Observable<String> deleteToDo(@Query("id") int id);

    class Repositories implements Serializable {

        public final List<EntityToDo> Items;

        public Repositories(List<EntityToDo> items) {
            this.Items = items;
        }
    }
}
