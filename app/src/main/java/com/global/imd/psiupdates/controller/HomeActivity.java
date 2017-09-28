package com.global.imd.psiupdates.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.global.imd.psiupdates.R;
import com.global.imd.psiupdates.network.AsyncTaskCompleteListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Caca Rusmana on 27/09/2017.
 */

public class HomeActivity extends AppCompatActivity implements AsyncTaskCompleteListener<Object> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        initComponent();
    }

    private void initComponent() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_activity_psiupdates));

        setupViewPager();
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PSI24Fragment(), getString(R.string.label_psi24_title));
        adapter.addFragment(new ParticulateMatterFragment(), getString(R.string.label_pm25_title));
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onTaskComplete(Object... params) {
        setupViewPager();
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        private ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        private void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
