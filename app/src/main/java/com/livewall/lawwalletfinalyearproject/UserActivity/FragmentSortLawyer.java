package com.livewall.lawwalletfinalyearproject.UserActivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livewall.lawwalletfinalyearproject.R;


public class FragmentSortLawyer extends Fragment {



    public FragmentSortLawyer() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sort_lawyer, container, false);
      return  view;
    }
}