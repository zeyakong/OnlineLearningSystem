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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.testui7.Bean.Video;
import com.example.administrator.testui7.Fragment.DataFragment;
import com.example.administrator.testui7.Fragment.RecommendFragment;
import com.example.administrator.testui7.Fragment.TypeFragment;
import com.example.administrator.testui7.R;
import com.example.administrator.testui7.base.OkHttpClientManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class VideoInfoActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private ImageView mIvVideo;
    private TextView mTvTitle;
    private TextView mTvMsg;
    private TextView mTvRating;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Button bPlay;
    private Video video;

    private FragmentPagerAdapter mSectionsPagerAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_info);

        initView();

        initDatas();
    }

    private void initView() {
        //设置Toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mIvVideo = (ImageView) findViewById(R.id.iv_video);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mTvRating = (TextView) findViewById(R.id.tv_rating);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        bPlay = (Button) findViewById(R.id.btn_playvideo);



        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头

        mFragments = new ArrayList<Fragment>();
        Fragment f1 = new DataFragment();

        Fragment f2 = new RecommendFragment();

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
                        return "介绍";
                    case 1:
                        return "相关推荐";
                }
                return null;
            }
        };
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initDatas() {
        mTvMsg.setText("热度:0");
        mTvRating.setText("价格:10");
        mTvTitle.setText(BmobUser.getCurrentUser().getUsername());
        video =(Video)getIntent().getSerializableExtra("video");
        mTvTitle.setText(video.getVideoName());
        OkHttpClientManager.displayImage(mIvVideo,video.getVideoImg().getFileUrl());
        mCollapsingToolbarLayout.setTitleEnabled(false);

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //在Intent对象当中添加一个键值对
                intent.putExtra("vedioObjectId", video.getObjectId());
                intent.setClass(VideoInfoActivity.this,VideoInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}
