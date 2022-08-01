package com.example.bottomnavigationt;

import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataLoader {

    private static final String url = "http://192.168.0.79:8080/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static final ClientService clientService = retrofit.create(ClientService.class);

    public static ArrayList<VandalismInfo> vandalismList = new ArrayList<>();

    public static void getData(){
        Call<ArrayList<VandalismInfo>> call = clientService.getVandalism();
        call.enqueue(new VandalismCallBack());
    }

    public static void postData(VandalismInfo vandalismInfo){
        Call<ArrayList<VandalismInfo>> call = clientService.postVandalism(vandalismInfo);
        call.enqueue(new VandalismCallBack());
    }

    public static void deleteData(Long id){
        Call<ArrayList<VandalismInfo>> call =clientService.deleteVandalism(id);
        call.enqueue(new VandalismCallBack());
    }

    public static void putData(Long id,HashMap<String,Object> paramsMap){
        Call<ArrayList<VandalismInfo>> call = clientService.putVandalism(id,paramsMap);
                call.enqueue(new VandalismCallBack());
    }

    private static class VandalismCallBack implements Callback<ArrayList<VandalismInfo>> {
        @Override
        public void onResponse(Call<ArrayList<VandalismInfo>> call, Response<ArrayList<VandalismInfo>> response) {
            if (response.isSuccessful()) {
                vandalismList = response.body();
            }
            else {
                Log.d("onResponse","bad answer" );
            }

        }
        @Override
        public void onFailure(Call<ArrayList<VandalismInfo>> call, Throwable t) {
            Log.d("onFailure","failed to get response");
        }
    }


}
