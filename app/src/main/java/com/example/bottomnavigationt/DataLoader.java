package com.example.bottomnavigationt;


import android.util.Log;

import android.widget.Toast;




import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataLoader {
    private MapsFragment fragment;
    private GoogleMap googleMap;
    private static DataLoader instance = null;

    //private final String url = "http://192.168.0.79:8080/";
    private final String url = "http://192.168.0.116:8080/";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private ClientService clientService = retrofit.create(ClientService.class);

    private List<VandalismInfo> vandalismList;

    private DataLoader(GoogleMap googleMap,MapsFragment fragment){
        this.googleMap = googleMap;
        this.fragment = fragment;
    }

    public static DataLoader getInstance(GoogleMap googleMap, MapsFragment fragment){
        if (instance == null) {
            instance = new DataLoader(googleMap,fragment);
        }
        return instance;
    }

    public static DataLoader getInstance() {
        if (instance != null) {
            return instance;
        }
        else {
            throw new IllegalStateException("одиночка ещё не был создан, " +
                    "воспользуйтесь перегруженной функцией с аргументами");
        }
    }

    public void getData(){
        Call<List<VandalismInfo>> call = clientService.getVandalism();
        call.enqueue(new VandalismCallBack());
    }

    public void postData(VandalismInfo vandalismInfo){
        Call<List<VandalismInfo>> call = clientService.postVandalism(vandalismInfo);
        call.enqueue(new VandalismCallBack());
    }

    public  void deleteData(Long id){
        Call<List<VandalismInfo>> call =clientService.deleteVandalism(id);
        call.enqueue(new VandalismCallBack());
    }

    public void putData(Long id,HashMap<String,Object> paramsMap){
        Call<List<VandalismInfo>> call = clientService.putVandalism(id,paramsMap);
        call.enqueue(new VandalismCallBack());
    }



    private class VandalismCallBack implements Callback<List<VandalismInfo>> {
        @Override
        public void onResponse(Call<List<VandalismInfo>> call, Response<List<VandalismInfo>> response) {
            if (response.isSuccessful()) {

                vandalismList = response.body();

                for (VandalismInfo vandalismInfo:vandalismList) {

                    LatLng latLng = new LatLng(vandalismInfo.getLat(), vandalismInfo.getLon());

                    Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                    marker.setTag(vandalismInfo);

                    if (vandalismInfo.getCleaned()){
                        marker.setVisible(false);
                    }
                }

                Log.d("onResponse","good answer" );
                Toast.makeText(fragment.getContext(), "хороший ответ", Toast.LENGTH_LONG).show();

            }
            else {

                Log.d("onResponse","bad answer" );
                Toast.makeText(fragment.getContext(), response.message(), Toast.LENGTH_LONG).show();
            }

        }
        @Override
        public void onFailure(Call<List<VandalismInfo>> call, Throwable t) {
            Log.d("onFailure","failed to get response");
            Toast.makeText(fragment.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
