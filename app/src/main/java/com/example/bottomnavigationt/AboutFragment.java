package com.example.bottomnavigationt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class AboutFragment extends Fragment {
    private MainActivity activity;
    protected Switch adminSwitch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        activity = (MainActivity) getActivity();
        adminSwitch = view.findViewById(R.id.admin_switch);


        return view;
    }
}