package com.livewall.lawwalletfinalyearproject.AdminActivity.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.livewall.lawwalletfinalyearproject.R;

public class ShowUserDetailToAdmin extends Fragment {


    public ShowUserDetailToAdmin() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_user_detail_to_admin, container, false);
    }
}