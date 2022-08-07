package com.example.bottomnavigationt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.google.android.material.bottomsheet.BottomSheetBehavior;





// TODO: 02.08.2022 implement countdownTimer
public class MapsFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        OnMapReadyCallback {

    private MainActivity activity;
    private Marker currentMarker = null;
    private DataLoader dataLoader;


        @Override
        public void onMapReady(GoogleMap googleMap) {
            googleMap.setOnMapClickListener(this);
            googleMap.setOnMarkerClickListener(MapsFragment.this);
            dataLoader = DataLoader.getInstance(googleMap, this);
            dataLoader.getData();
            //поставить камеру на метку локации
            //googleMap.setMyLocationEnabled(true);
            //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(1,2),10));

        }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        activity = (MainActivity) getActivity();
        return view;

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        currentMarker = marker;
        VandalismInfo vandalismInfo = (VandalismInfo) marker.getTag();
        activity.setInfoOnBottomSheet(vandalismInfo);
        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {

            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
       if (activity.behavior.getState()!=BottomSheetBehavior.STATE_HIDDEN) {
            activity.behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        Toast.makeText(getContext(), "map click", Toast.LENGTH_SHORT).show();
        currentMarker = null;
    }

    public Marker getCurrentMarker() {
        return currentMarker;
    }

}