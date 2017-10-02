package com.global.imd.psiupdates.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.global.imd.psiupdates.R;
import com.global.imd.psiupdates.adapter.HomeViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caca Rusmana on 27/09/2017.
 */

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        initComponent();
    }

    private void initComponent() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_psiupdates));

        setupViewPager();
    }

    private void setupViewPager() {
        mFragmentList = new ArrayList<>();
        List<String> mFragmentTitleList = new ArrayList<>();

        mFragmentList.add(new PSI24MapFragment());
        mFragmentTitleList.add(getString(R.string.label_psi24_title));
        mFragmentList.add(new PM25MapFragment());
        mFragmentTitleList.add(getString(R.string.label_pm25_title));

        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mFragmentTitleList);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_refresh:
                int selectedTabPosition = tabLayout.getSelectedTabPosition();
                if (selectedTabPosition == 0) {
                    ((PSI24MapFragment) mFragmentList.get(selectedTabPosition)).reloadData(true);
                } else {
                    ((PM25MapFragment) mFragmentList.get(selectedTabPosition)).reloadData(true);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
