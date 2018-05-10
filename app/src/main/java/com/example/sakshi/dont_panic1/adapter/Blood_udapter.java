/*
 * %W% %E% Zain-Ul-Abedin
 *
 * Copyright (c) 2017-2018. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of ZainMustafaaa.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * for learning purposes.
 *
 */
package com.example.sakshi.dont_panic1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sakshi.dont_panic1.BloodBank.BloodDetail;
import com.example.sakshi.dont_panic1.Hospital.NearbyHospitalsDetail;
import com.example.sakshi.dont_panic1.R;

import java.util.ArrayList;
import java.util.List;


public class Blood_udapter extends BaseAdapter {
    private Context context;
    private ArrayList<BloodDetail> bloodList;


    public Blood_udapter(Context context, ArrayList<BloodDetail> hospitalList){
        this.context = context;
        this.bloodList = hospitalList;
    }


    @Override
    public int getCount() {
        return bloodList.size();
    }


    @Override
    public Object getItem(int i) {
        return bloodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 3232;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) view = view.inflate(context, R.layout.blood_list_view, null);

        TextView placeTextView =  view.findViewById(R.id.placeNameTextView);
        TextView ratingTextView =  view.findViewById(R.id.ratingTextView);
        TextView openNowTextView =  view.findViewById(R.id.openingTime);

        placeTextView.setText(bloodList.get(i).getHospitalName());
        ratingTextView.setText(bloodList.get(i).getRating());
        openNowTextView.setText("Open now: " + bloodList.get(i).getOpeningHours());

        return view;
    }
}
