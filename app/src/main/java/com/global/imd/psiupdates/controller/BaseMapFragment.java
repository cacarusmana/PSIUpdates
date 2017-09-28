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

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.global.imd.psiupdates.R;
import com.global.imd.psiupdates.util.Constant;
import com.global.imd.psiupdates.util.Utility;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;

/**
 * Created by Caca Rusmana on 27/09/2017.
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // disable all gesture on map
        googleMap.getUiSettings().setAllGesturesEnabled(false);
        mMap = googleMap;
    }

    protected void setValue(View markerView, int id, String value) {
        TextView textView = (TextView) markerView.findViewById(id);
        textView.setText(value);
    }

    protected void initSummaryLevel(String[] titles, String[] descriptions) {

        for (int i = 0; i < titles.length; i++) {
            View summaryView = LayoutInflater.from(context).inflate(R.layout.summary_level_row, null);

            setValue(summaryView, R.id.title_value, titles[i]);
            setValue(summaryView, R.id.description_value, descriptions[i]);

            ImageView cloudIcon = (ImageView) summaryView.findViewById(R.id.cloud_icon);
            cloudIcon.setImageResource(context.getResources().getIdentifier(Utility.getImageFileName(titles[i]), Constant.DRAWABLE, context.getPackageName()));

            final ExpandableRelativeLayout descriptionLayout = (ExpandableRelativeLayout) summaryView.findViewById(R.id.description_layout);
            descriptionLayout.collapse();

            summaryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    descriptionLayout.toggle();
                }
            });

            contentLayout.addView(summaryView);
        }

    }


}
