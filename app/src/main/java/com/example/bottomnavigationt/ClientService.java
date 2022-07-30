package com.example.bottomnavigationt;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ClientService {
    @GET("api/v1/vandalism")
    Call<ArrayList<VandalismInfo>> getVandalism();
    @POST("api/v1/vandalism")
    Call<ArrayList<VandalismInfo>> uploadVandalism(@Body VandalismInfo vandalismInfo);



}
