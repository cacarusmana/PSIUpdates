package com.global.imd.psiupdates.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.global.imd.psiupdates.R;
import com.global.imd.psiupdates.model.PSIInfo;
import com.global.imd.psiupdates.util.Constant;
import com.global.imd.psiupdates.util.DateUtil;
import com.global.imd.psiupdates.util.Utility;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Caca Rusmana on 27/09/2017.
 */

/*
Base Fragment for Map Fragment, so another child can able to use some function or variable of the BaseMapFragment,
it will reduce the rewrite code or function
 */
public class BaseMapFragment extends Fragment implements OnMapReadyCallback {

    protected Context context;
    protected GoogleMap mMap;
    protected LatLng mapFocusPosition;
    protected View rootView;

    @BindView(R.id.last_updated_text)
    TextView lastUpdatedText;
    @BindView(R.id.psi_info_text)
    TextView psiInfoText;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    protected void initComponent() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    // it will notify google map if map already to be used
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // disable all gesture on map
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        mMap = googleMap;
    }

    // set value for TextView component
    protected TextView setValue(View view, int id, String value) {
        TextView textView = (TextView) view.findViewById(id);
        textView.setText(value);
        return textView;
    }

    /*
    initialize the summary level description of psi or pm2.5
    it created dynamically as the array given to this method
     */
    protected void initSummaryLevel(String[] titles, String[] descriptions) {

        for (int i = 0; i < titles.length; i++) {
            View summaryView = LayoutInflater.from(context).inflate(R.layout.summary_level_row, null);

            setValue(summaryView, R.id.title_value, titles[i]);
            setValue(summaryView, R.id.description_value, descriptions[i]);

            ImageView cloudIcon = (ImageView) summaryView.findViewById(R.id.cloud_icon);
            cloudIcon.setImageResource(context.getResources().getIdentifier(Utility.getImageFileName(titles[i]), Constant.DRAWABLE, context.getPackageName()));

            final ImageView expandIcon = (ImageView) summaryView.findViewById(R.id.expand_icon);


            final ExpandableRelativeLayout descriptionLayout = (ExpandableRelativeLayout) summaryView.findViewById(R.id.description_layout);
            descriptionLayout.collapse();

            summaryView.findViewById(R.id.good_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    descriptionLayout.toggle();

                    if (descriptionLayout.isExpanded()) {
                        expandIcon.startAnimation(Utility.expandAnimation());
                        expandIcon.setImageResource(R.drawable.expand);

                    } else {
                        expandIcon.startAnimation(Utility.collapseAnimation());
                        expandIcon.setImageResource(R.drawable.collapse);
                    }

                }
            });

            contentLayout.addView(summaryView);
        }
    }

    /*
    initialize the google map with psi or pm2.5 information for each region
     */
    protected void initGoogleMap(String result, String readingsKey) {
        mMap.clear();

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
            JSONObject jsonObjectPSI = itemsArray.getJSONObject(0).getJSONObject(Constant.READINGS_FIELD).getJSONObject(readingsKey);

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
            Toast.makeText(context, readingsKey.equals(Constant.PSI24_HOURLY_FIELD) ? getString(R.string.message_unable_to_show_psi) : getString(R.string.message_unable_to_show_pm25), Toast.LENGTH_LONG).show();
        }
    }


}
