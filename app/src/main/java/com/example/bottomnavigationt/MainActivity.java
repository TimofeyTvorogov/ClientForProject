package com.example.bottomnavigationt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
SmoothBottomBar bottomBar;
LinearLayout bottomSheet;

    AboutFragment aboutFragment = new AboutFragment();
    MapsFragment mapsFragment = new MapsFragment();
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft;
    BottomSheetBehavior behavior;
    TextView addressTv, objectTv, typeTv, votesTv;



    private static final int REQUEST_CODE = 1001;
    private static final String FINE_LOC = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String CRS_LOC = Manifest.permission.ACCESS_COARSE_LOCATION;
    public boolean granted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.bottomBar);
        bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        addressTv = findViewById(R.id.address_tv);
        objectTv = findViewById(R.id.object_tv);
        typeTv = findViewById(R.id.type_tv);
        votesTv = findViewById(R.id.votes_tv);

        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setPeekHeight(280);

        bottomBar.setOnItemSelectedListener(this);
        bottomBar.setItemActiveIndex(0);

        ft = fm.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container,mapsFragment)
                .add(R.id.fragment_container, aboutFragment).hide(aboutFragment);
        ft.commit();




    }

    public boolean checkPermission(){

        if (ActivityCompat.checkSelfPermission(this, FINE_LOC) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, CRS_LOC) != PackageManager.PERMISSION_GRANTED) {
            //что делать, если разрешение не дано: попробовать запросить
            ActivityCompat.requestPermissions(this, new String[]{FINE_LOC, CRS_LOC}, REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            granted = true;
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        granted = false;
                        break;
                    }
                }
            }
            else {
                granted = false;
            }
        }
    }

    @Override
    public boolean onItemSelect(int i) {
        ft = fm.beginTransaction();
        switch (i){
            case 0:
            ft.hide(aboutFragment);
            ft.show(mapsFragment);
            break;
            case 1:
                ft.hide(mapsFragment);
                ft.show(aboutFragment);
            break;
        }
        ft.commit();
        return true;
    }
}