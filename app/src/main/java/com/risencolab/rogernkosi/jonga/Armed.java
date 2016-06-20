package com.risencolab.rogernkosi.jonga;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by empirestate on 6/15/16.
 */
public class Armed extends Fragment implements View.OnClickListener{

    Button disarm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_armed, container, false);
        disarm = (Button)view.findViewById(R.id.disarm);
        disarm.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        

        if (v.getId() == R.id.disarm){
            FragmentManager manager = getFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new Ready());
            fragmentTransaction.commit();
        }

    }

}
