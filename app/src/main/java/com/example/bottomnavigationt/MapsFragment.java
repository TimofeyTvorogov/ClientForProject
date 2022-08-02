package com.example.bottomnavigationt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.HashMap;



// TODO: 02.08.2022 implement countdownTimer
public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener, View.OnClickListener, GoogleMap.OnMapClickListener {
    private LinearLayout bottomSheet;
    private BottomSheetBehavior behavior;
    private TextView addressTv, objectTv, typeTv, votesTv;
    private Button voteButton, deleteButton;

    private ArrayList<VandalismInfo> vandalismList = new ArrayList<>();
    private Long currentMarkerId = null;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            googleMap.setOnMarkerClickListener(MapsFragment.this);

            for (int i = 0; i < vandalismList.size(); i++) {

                VandalismInfo vandalismInfo = vandalismList.get(i);
                LatLng latLng = new LatLng(vandalismInfo.getLat(), vandalismInfo.getLon());

                Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng));
                marker.setTag(vandalismInfo.getId());

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


        bottomSheet = view.findViewById(R.id.bottom_sheet);
        addressTv = view.findViewById(R.id.address_tv);
        objectTv = view.findViewById(R.id.object_tv);
        typeTv = view.findViewById(R.id.type_tv);
        votesTv = view.findViewById(R.id.votes_tv);
        voteButton = view.findViewById(R.id.vote_b);
        deleteButton = view.findViewById(R.id.delete_b);
        behavior = BottomSheetBehavior.from(bottomSheet);

        deleteButton.setOnClickListener(this);
        voteButton.setOnClickListener(this);

        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setPeekHeight(280);

        DataLoader.getData();
        vandalismList = DataLoader.vandalismList;
        return view;

    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        VandalismInfo vandalismInfo = findVandalismInfoById((Long) marker.getTag());
        addressTv.setText(vandalismInfo.getAddress());
        objectTv.setText(vandalismInfo.getObject());
        typeTv.setText(vandalismInfo.getType());
        votesTv.setText(vandalismInfo.getVotes().toString());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        currentMarkerId = vandalismInfo.getId();
        return false;
    }

    private VandalismInfo findVandalismInfoById(Long id) {
        VandalismInfo returnInfo = null;
        for (VandalismInfo vandalismInfo:vandalismList) {
            if (vandalismInfo.getId().equals(id)){
                returnInfo = vandalismInfo;
            }
        }
        return returnInfo;
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

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.delete_b:
                DataLoader.deleteData(currentMarkerId);

                break;

            case R.id.vote_b:
                VandalismInfo vandalismInfo = findVandalismInfoById(currentMarkerId);
                Long votes = vandalismInfo.getVotes();
                HashMap<String,Object> queryMap = new HashMap<>();
                queryMap.put("votes",votes++);
                DataLoader.putData(currentMarkerId,queryMap);
                break;
        }

    }


    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if (behavior.getState()!=BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        currentMarkerId = null;
    }
}