package com.global.imd.psiupdates.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.global.imd.psiupdates.R;
import com.global.imd.psiupdates.network.AsyncTaskCompleteListener;
import com.global.imd.psiupdates.network.CallWebService;
import com.global.imd.psiupdates.util.Constant;
import com.global.imd.psiupdates.util.DateUtil;
import com.global.imd.psiupdates.util.Utility;
import com.google.android.gms.maps.GoogleMap;

import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by Caca Rusmana on 27/09/2017.
 */

public class PM25MapFragment extends BaseMapFragment implements AsyncTaskCompleteListener<Object> {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pm25_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initComponent();
    }

    @Override
    protected void initComponent() {
        super.initComponent();

        initSummaryLevel(getResources().getStringArray(R.array.pm25_safety_level_title_array), getResources().getStringArray(R.array.safety_level_desc_array));
    }

    // request data to server
    public void reloadData(boolean showDialog) {
        String url = Constant.PM25_URL + Constant.DATE_TIME_FIELD + DateUtil.formatDate(Constant.DATE_FORMAT, new Date());
        CallWebService task = new CallWebService(context, this, showDialog);
        task.execute(url);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        reloadData(false);
    }

    @Override
    public void onTaskComplete(Object... params) {
        String result = (String) params[0];

        if (Utility.checkValidResult(context, result)) {
            initGoogleMap(result, Constant.PM25_ONE_HOURLY_FIELD);
        }
    }
}
