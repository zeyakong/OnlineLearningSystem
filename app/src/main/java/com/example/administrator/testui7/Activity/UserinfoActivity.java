package com.example.administrator.testui7.Activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.testui7.Fragment.CommunityFragment;
import com.example.administrator.testui7.Fragment.DownloadFragment;
import com.example.administrator.testui7.Fragment.DownloadingFragment;
import com.example.administrator.testui7.Fragment.TypeFragment;
import com.example.administrator.testui7.Fragment.RecommendFragment;
import com.example.administrator.testui7.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;


public class UserinfoActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private ImageView mIvUser;
    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvRating;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private FragmentPagerAdapter mSectionsPagerAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        initView();

        initDatas();
    }

    private void initView() {
        //设置Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mIvUser = (ImageView) findViewById(R.id.iv_user);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mTvRating = (TextView) findViewById(R.id.tv_rating);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头

        mFragments = new ArrayList<Fragment>();
        Fragment f1 = new RecommendFragment();
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
                        return "推荐";
                    case 1:
                        return "已购买";
                }
                return null;
            }
        };
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initDatas() {
        mTvMsg.setText("积分:0");
        mTvRating.setText("余额:0");
        mTvTitle.setText(BmobUser.getCurrentUser().getUsername());

        mIvUser.setImageResource(R.drawable.user1);
        mCollapsingToolbarLayout.setTitleEnabled(false);

        findViewById(R.id.btn_changeinfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserinfoActivity.this,ChangeInfoActivity.class));
            }
        });
    }
}
