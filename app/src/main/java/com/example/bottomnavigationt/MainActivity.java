package com.example.bottomnavigationt;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;



import java.util.HashMap;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener, View.OnClickListener {

    private static final int REQUEST_CODE = 1001;
    private static final String FINE_LOC = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String CRS_LOC = Manifest.permission.ACCESS_COARSE_LOCATION;

    private boolean granted = false;

    private final FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction ft;

    private LinearLayout bottomSheet;
    private TextView addressTv, objectTv, typeTv, votesTv;
    private Button voteButton, deleteButton;

    private AboutFragment aboutFragment = new AboutFragment();
    private DataLoader dataLoader;

    protected AddFragment addFragment = new AddFragment();
    private SmoothBottomBar bottomBar;
    protected MapsFragment mapsFragment = new MapsFragment();
    protected FloatingActionButton fab;
    protected BottomSheetBehavior behavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomSheet = findViewById(R.id.bottom_sheet);
        addressTv = findViewById(R.id.address_tv);
        objectTv = findViewById(R.id.object_tv);
        typeTv = findViewById(R.id.type_tv);
        votesTv = findViewById(R.id.votes_tv);
        voteButton = findViewById(R.id.vote_b);
        deleteButton = findViewById(R.id.delete_b);
        fab = findViewById(R.id.open_addFragment_fab);
        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setItemActiveIndex(0);

        deleteButton.setOnClickListener(this);
        voteButton.setOnClickListener(this);
        fab.setOnClickListener(this);
        bottomBar.setOnItemSelectedListener(this);

        behavior = from(bottomSheet);
        behavior.setHideable(true);
        behavior.setState(STATE_HIDDEN);
        behavior.setPeekHeight(280);
        behavior.setBottomSheetCallback(new BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case STATE_DRAGGING:
                    case STATE_EXPANDED:
                        fab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                        //fm.beginTransaction().hide(mapsFragment).commit();
                        break;
                    case STATE_COLLAPSED:
                    case STATE_HIDDEN:
                        fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                        //fm.beginTransaction().show(mapsFragment).commit();
                        break;


                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        ft = fm.beginTransaction()
                .add(R.id.fragment_container,mapsFragment)
                .add(R.id.fragment_container,addFragment).hide(addFragment)
                .add(R.id.fragment_container, aboutFragment).hide(aboutFragment);
        ft.commit();



    }

    @Override
    public void onClick(View view) {

        dataLoader = DataLoader.getInstance();
        //todo на нажатие кнопки забирать локацию
        switch (view.getId()){
            case R.id.open_addFragment_fab:
                fab.hide();
                ft = fm.beginTransaction()
                        .show(addFragment)
                        .hide(mapsFragment)
                        .setReorderingAllowed(true)
                        .addToBackStack(null);
                ft.commit();
                bottomBar.setVisibility(View.GONE);
                break;

            case R.id.vote_b:
                VandalismInfo vandalismInfo = (VandalismInfo) mapsFragment.getCurrentMarker().getTag();
                Long votes = vandalismInfo.getVotes();
                HashMap<String,Object> queryMap = new HashMap<>();
                queryMap.put("votes",++votes);
                dataLoader.putVandalism(vandalismInfo.getId(),queryMap);
                break;

            case R.id.delete_b:
                vandalismInfo = (VandalismInfo) mapsFragment.getCurrentMarker().getTag();
                dataLoader.deleteVandalism(vandalismInfo.getId());
                break;

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
        mapsFragment.currentMarker = null;
        switch (bottomBar.getVisibility()){

            case View.VISIBLE:
                if(behavior.getState()== STATE_HIDDEN){
                    finish();
                }
                else{
                    behavior.setState(STATE_HIDDEN);

                }
                break;

            case View.GONE:
                returnUI();
                break;

        }

    }
    public void returnUI(){
        bottomBar.setVisibility(View.VISIBLE);
        fm.popBackStack();
        fab.show();
    }
    public void setInfoOnBottomSheet(VandalismInfo vandalismInfo) {

        /*if (behavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }*/

        addressTv.setText(vandalismInfo.getAddress());
        objectTv.setText(vandalismInfo.getObject());
        typeTv.setText(vandalismInfo.getType());
        votesTv.setText(vandalismInfo.getVotes().toString());

        behavior.setState(STATE_EXPANDED);
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





    public boolean isGranted() {
        return granted;
    }
}
