package com.example.administrator.testui7.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.administrator.testui7.Fragment.CommunityFragment;
import com.example.administrator.testui7.Fragment.DownloadFragment;
import com.example.administrator.testui7.Fragment.DownloadingFragment;
import com.example.administrator.testui7.Fragment.RecommendFragment;
import com.example.administrator.testui7.Fragment.TypeFragment;
import com.example.administrator.testui7.R;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {

    private FragmentPagerAdapter mSectionsPagerAdapter;
    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_download);
        //设置顶部返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("缓存视频");

        mViewPager = (ViewPager) findViewById(R.id.viewpager_download);
        mTabLayout = (TabLayout) findViewById(R.id.download_tabs);

        mFragments = new ArrayList<Fragment>();
        Fragment f1 = new DownloadFragment();
        Fragment f2 = new CommunityFragment();
        mFragments.add(f1);
        mFragments.add(f2);

        mSectionsPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "已缓存";
                    case 1:
                        return "正在缓存";
                }
                return null;
            }
        };
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
