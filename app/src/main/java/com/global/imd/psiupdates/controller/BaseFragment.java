package com.global.imd.psiupdates.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.global.imd.psiupdates.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;

/**
 * Created by Caca Rusmana on 27/09/2017.
 */

public class BaseFragment extends Fragment implements OnMapReadyCallback {

    protected Context context;
    protected GoogleMap mMap;
    protected LatLng mapFocusPosition;
    @BindView(R.id.last_updated_text)
    TextView lastUpdatedText;
    @BindView(R.id.psi_info_text)
    TextView psiInfoText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    protected void initComponent(View view) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    protected void setMarkerValue(View markerView, int id, String value) {
        TextView textView = (TextView) markerView.findViewById(id);
        textView.setText(value);
    }
}
