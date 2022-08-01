package com.example.bottomnavigationt;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ClientService {
    @GET("api/v1/vandalism")
    Call<ArrayList<VandalismInfo>> getVandalism();
    @POST("api/v1/vandalism")
    Call<ArrayList<VandalismInfo>> postVandalism(@Body VandalismInfo vandalismInfo);
    @PUT("api/v1/vandalism/{id}")
    Call<ArrayList<VandalismInfo>> putVandalism(@Path("id") Long id, @QueryMap HashMap<String,Object> params);
   @DELETE("api/v1/vandalism/{id}")
    Call<ArrayList<VandalismInfo>> deleteVandalism(@Path("id") Long id);



}
