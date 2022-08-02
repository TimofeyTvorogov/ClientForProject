package com.example.bottomnavigationt;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddFragment extends Fragment implements View.OnClickListener {
    ImageButton backIb;
    EditText addObjectEt, addTypeEt;
    Button uploadVandalismB;
    ImageView vandalismIv;
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
activity = (MainActivity) getActivity();
        backIb = view.findViewById(R.id.back_ib);

        addObjectEt = view.findViewById(R.id.add_object_et);
        addTypeEt = view.findViewById(R.id.add_type_et);

        uploadVandalismB = view.findViewById(R.id.upload_vandalism_b);

        vandalismIv = view.findViewById(R.id.vandalism_iv);
backIb.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_ib:
                activity.fab.show();
                activity.bottomBar.setVisibility(View.VISIBLE);
                getParentFragmentManager().beginTransaction().hide(this).show(activity.mapsFragment).commit();
            case R.id.upload_vandalism_b:
                //todo implement geocoder and geolocation
                DataLoader.postData(new VandalismInfo(123D,
                        321D,
                        "default address",
                        addTypeEt.getText().toString(),
                        addObjectEt.getText().toString()));
                break;


        }
    }


}