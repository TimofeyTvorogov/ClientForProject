package com.example.bottomnavigationt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;



public class MainActivity extends AppCompatActivity implements OnItemSelectedListener, View.OnClickListener {
    public SmoothBottomBar bottomBar;
    public AboutFragment aboutFragment = new AboutFragment();
    public MapsFragment mapsFragment = new MapsFragment();
    public AddFragment addFragment = new AddFragment();
    private final FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft;
    public FloatingActionButton fab;




    private static final int REQUEST_CODE = 1001;
    private static final String FINE_LOC = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String CRS_LOC = Manifest.permission.ACCESS_COARSE_LOCATION;
    public boolean granted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomBar = findViewById(R.id.bottomBar);

        fab = findViewById(R.id.open_addFragment_fab);


        fab.setOnClickListener(this);


        bottomBar.setOnItemSelectedListener(this);
        bottomBar.setItemActiveIndex(0);

        ft = fm.beginTransaction()
                .add(R.id.fragment_container,mapsFragment)
                .add(R.id.fragment_container,addFragment).hide(addFragment)
                .add(R.id.fragment_container, aboutFragment).hide(aboutFragment);
        ft.commit();




    }
    @Override
    public void onClick(View view) {
        fab.hide();
        ft = fm.beginTransaction().show(addFragment).hide(mapsFragment).setReorderingAllowed(true).addToBackStack(null);
        ft.commit();
        bottomBar.setVisibility(view.GONE);
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
                        finish();
                        break;

                    }
                }
            }
            else {
                granted = false;
                finish();
            }
        }
    }

    @Override
    public boolean onItemSelect(int i) {
        if (i==0 && aboutFragment.adminSwitch.isChecked()){
            fab.show();
        }
        else{
            fab.hide();
        }
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

    @Override
    public void onBackPressed() {
        if(bottomBar.getVisibility()==View.VISIBLE){
            finish();
        }
        else{
            bottomBar.setVisibility(View.VISIBLE);
            fm.popBackStack();
            fab.show();
        }
    }
}