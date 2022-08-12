package com.example.bottomnavigationt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class AddFragment extends Fragment implements View.OnClickListener {
    ImageButton backIb;
    EditText addObjectEt, addTypeEt;
    Button uploadVandalismB;
    ImageView vandalismIv;
    MainActivity activity;
    DataLoader dataLoader;
    protected String typeToAdd, objectToAdd;
    protected VandalismInfo vandalismToPost;
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
        uploadVandalismB.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_ib:
                activity.returnUI();
                break;
            case R.id.upload_vandalism_b:

                if ((addTypeEt.getText() == null && addObjectEt.getText() == null)||
                        (addTypeEt.getText().equals("") && addObjectEt.getText().equals(""))) {

                    typeToAdd = "default type";
                    objectToAdd = "default object";
                }
                else {
                    typeToAdd = addTypeEt.getText().toString();
                    objectToAdd = addObjectEt.getText().toString();
                }

                vandalismToPost = new VandalismInfo(null,null,null,typeToAdd,objectToAdd);

                dataLoader = DataLoader.getInstance();
                dataLoader.initLoc(true);



                activity.returnUI();
                Toast.makeText(getContext(), "случай вандализма добавлен", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}