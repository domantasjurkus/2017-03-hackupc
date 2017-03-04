package edu.salleurl.ls30394.crowdie.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.salleurl.ls30394.crowdie.R;

/**
 * Created by avoge on 04/03/2017.
 */

public class VictimFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_victim, container, false);
        return rootView;
    }

}
