package com.global.imd.psiupdates.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.global.imd.psiupdates.R;
import com.global.imd.psiupdates.model.PSIInfo;
import com.global.imd.psiupdates.network.AsyncTaskCompleteListener;
import com.global.imd.psiupdates.network.CallWebService;
import com.global.imd.psiupdates.util.Constant;
import com.global.imd.psiupdates.util.DateUtil;
import com.global.imd.psiupdates.util.Utility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            try {
                JSONObject jsonObject = new JSONObject(result);
                List<PSIInfo> psiInfoList = new ArrayList<>();

                JSONArray regionMetaDataArray = jsonObject.getJSONArray(Constant.REGION_METADATA_FIELD);

                for (int i = 0; i < regionMetaDataArray.length(); i++) {
                    JSONObject object = regionMetaDataArray.getJSONObject(i);

                    psiInfoList.add(new PSIInfo(object.getString(Constant.NAME_FIELD), object.getJSONObject(Constant.LABEL_LOCATION_FIELD).getDouble(Constant.LONGITUDE_FIELD),
                            object.getJSONObject(Constant.LABEL_LOCATION_FIELD).getDouble(Constant.LATITUDE_FIELD)));
                }

                JSONArray itemsArray = jsonObject.getJSONArray(Constant.ITEMS_FIELD);
                JSONObject jsonObjectPSI = itemsArray.getJSONObject(0).getJSONObject(Constant.READINGS_FIELD).getJSONObject(Constant.PM25_ONE_HOURLY_FIELD);

                int minValue = 0;
                int maxValue = 0;

                for (PSIInfo psiInfo : psiInfoList) {
                    psiInfo.setValue(jsonObjectPSI.getInt(psiInfo.getName()));

                    LatLng latLng = new LatLng(psiInfo.getLatitude(), psiInfo.getLongitude());


                    View markerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

                    setValue(markerView, R.id.psi_value, String.valueOf(psiInfo.getValue()));
                    setValue(markerView, R.id.name_value, psiInfo.getName().substring(0, 1).toUpperCase() + psiInfo.getName().substring(1));

                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(BitmapDescriptorFactory.fromBitmap(Utility.createDrawableFromView(context, markerView))));

                    if (psiInfo.getName().equalsIgnoreCase(Constant.FOCUSED_CAMERA_POSITION))
                        mapFocusPosition = latLng;

                    // get min and max value
                    if (minValue == 0) {
                        minValue = psiInfo.getValue();
                        maxValue = psiInfo.getValue();
                    } else {
                        if (psiInfo.getValue() < minValue) minValue = psiInfo.getValue();
                        if (psiInfo.getValue() > maxValue) maxValue = psiInfo.getValue();
                    }
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapFocusPosition, Constant.ZOOM_VALUE));

                Date lastUpdateDate = DateUtil.parseDate(Constant.DATE_FORMAT_RESPONSE, itemsArray.getJSONObject(0).getString(Constant.TIMESTAMP_FIELD));
                lastUpdatedText.setText(String.format(getString(R.string.label_last_updated), DateUtil.formatDate(Constant.DATE_FORMAT_UPDATE, lastUpdateDate)));
                psiInfoText.setText(String.format(getString(R.string.label_pm25hourly), String.valueOf(minValue), String.valueOf(maxValue)));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, getString(R.string.message_unable_to_show_psi), Toast.LENGTH_LONG).show();
            }
        }
    }
}
