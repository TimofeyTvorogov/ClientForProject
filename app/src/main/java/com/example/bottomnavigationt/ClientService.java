package com.example.bottomnavigationt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ClientService {

    @GET("api/v1/vandalism")
    Call<List<VandalismInfo>> getVandalism();

    @POST("api/v1/vandalism")
    Call<Void> postVandalism(@Body VandalismInfo vandalismInfo);

    @POST("api/v1/vandalism/postImage")
    @Multipart
    Call<Void> postImage(@Part MultipartBody.Part file);

    @PUT("api/v1/vandalism/{id}")
    Call<Void> putVandalism(@Path("id") String id, @QueryMap HashMap<String,Object> params);

   @DELETE("api/v1/vandalism/{id}")
   Call<Void> deleteVandalism(@Path("id") String id);




}
