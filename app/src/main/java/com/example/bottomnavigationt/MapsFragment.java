package com.example.bottomnavigationt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener {
    private static final String url = "http://192.168.0.79:8080/";

    private MainActivity activity;
    private ArrayList<VandalismInfo> vandalismList = new ArrayList<>();


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            googleMap.setOnMarkerClickListener(MapsFragment.this);

            for (int i = 0; i < vandalismList.size(); i++) {

                VandalismInfo vandalismInfo = vandalismList.get(i);
                LatLng latLng = new LatLng(vandalismInfo.getLat(), vandalismInfo.getLon());

                Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                marker.setTag(Integer.valueOf(i));

                if (vandalismInfo.getCleaned()){
                    marker.setVisible(false);
                }
            }


        }

    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        activity = (MainActivity) getActivity();

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ClientService clientService = retrofit.create(ClientService.class);

        Call<ArrayList<VandalismInfo>> call = clientService.getVandalism();
        call.enqueue(new VandalismCallBack());
         */
        DataLoader.getData();

        return view;

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        activity.behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        VandalismInfo vandalismInfo = vandalismList.get((Integer) marker.getTag());
        activity.addressTv.setText(vandalismInfo.getAddress());
        activity.objectTv.setText(vandalismInfo.getObject());
        activity.typeTv.setText(vandalismInfo.getType());
        activity.votesTv.setText(vandalismInfo.getVotes().toString());
        activity.behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        return false;
    }
    private void moveAndZoom(GoogleMap map,LatLng latLng,float zoom){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
    /*
    class VandalismCallBack implements Callback<ArrayList<VandalismInfo>> {
        @Override
        public void onResponse(Call<ArrayList<VandalismInfo>> call, Response<ArrayList<VandalismInfo>> response) {
            if (response.isSuccessful()) {
                vandalismList = response.body();
            }

        }
        @Override
        public void onFailure(Call<ArrayList<VandalismInfo>> call, Throwable t) {
            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    */

}