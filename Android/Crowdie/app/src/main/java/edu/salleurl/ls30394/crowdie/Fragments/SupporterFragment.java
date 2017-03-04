package edu.salleurl.ls30394.crowdie.Fragments;

/**
 * Created by avoge on 04/03/2017.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import edu.salleurl.ls30394.crowdie.R;

public class SupporterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_supporter, container, false);
        return rootView;
    }

}
