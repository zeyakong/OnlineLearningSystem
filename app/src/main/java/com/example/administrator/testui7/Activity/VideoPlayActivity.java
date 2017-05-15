package com.example.administrator.testui7.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.dl7.player.utils.SoftInputUtils;
import com.example.administrator.testui7.Adapter.MyRecyclerViewAdapter;
import com.example.administrator.testui7.Adapter.MyRecyclerViewAdapterForComment;
import com.example.administrator.testui7.Bean.Comment;
import com.example.administrator.testui7.Bean.Video;
import com.example.administrator.testui7.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;


public class VideoPlayActivity extends AppCompatActivity {

    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/SD/movie_index.m3u8";
    private static final String VIDEO_HD_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/HD/movie_index.m3u8";
    private static final String IMAGE_URL = "http://vimg2.ws.126.net/image/snapshot/2016/11/I/M/VC62HMUIM.jpg";
    List<Comment> datas = new ArrayList<>();
    Toolbar mToolbar;
    private IjkPlayerView mPlayerView;
    private View mEtLayout;
    private EditText mEditText;
    private Button mIvSend;
    private boolean mIsFocus;
    String Url=null;
    MyRecyclerViewAdapterForComment mAdapter;
    RecyclerView mRecyclerView;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mPlayerView = (IjkPlayerView) findViewById(R.id.player_view);
        mEtLayout = findViewById(R.id.ll_layout);
        mEditText = (EditText) findViewById(R.id.et_content);    //发表评论
        mIvSend = (Button) findViewById(R.id.btn_send);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Video Player");
        Intent intent = getIntent();
        //从Intent当中根据key取得value
         value = intent.getStringExtra("vedioObjectId");
        System.out.println("ObjectId............................."+value);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BmobQuery<Comment> query1 = new BmobQuery<Comment>();
        query1.addWhereEqualTo("videoid",value);
        query1.setLimit(50);
        //执行查询方法

        query1.findObjects(new FindListener<Comment>(){

            @Override
            public void done(List<Comment> list, BmobException e) {
                if(e==null){
                    System.out.println("daxiao............................."+list.size());
                    datas=list;
                    mAdapter=new MyRecyclerViewAdapterForComment(VideoPlayActivity.this, datas);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new MyRecyclerViewAdapterForComment.OnRecyclerViewItemClickListener(){


                        @Override
                        public void onItemClick(View view, String data) {

                        }
                    });
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });





      BmobQuery<Video> query = new BmobQuery<Video>();
        query.getObject(value, new QueryListener<Video>() {

            @Override
            public void done(Video video, BmobException e) {
                if(e==null){
                  Url=video.getVideoFile().getFileUrl();
                    Glide.with(VideoPlayActivity.this).load(IMAGE_URL).fitCenter().into(mPlayerView.mPlayerThumb);
                    mPlayerView.init()
                            .setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
                            .setSkipTip(1000*60*1)
                            //.enableDanmaku()
                            //.setDanmakuSource(getResources().openRawResource(R.raw.bili))
                            .setVideoSource(null, Url,  Url, null, null)
                            .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
                    mIvSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //mPlayerView.sendDanmaku(mEditText.getText().toString(), false);
                            Comment ct= new Comment();
                            ct.setVideoid(value);
                            ct.setCommentInfer(mEditText.getText().toString());
                            ct.setUserid(BmobUser.getCurrentUser().getObjectId());
                            ct.setLikeNum(5);

                            ct.save(new SaveListener<String>() {

                                @Override
                                public void done(String objectId, BmobException e) {
                                    if(e==null){
                                        //toast("创建数据成功：" + objectId);
                                    }else{
                                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    }
                                }
                            });
                            mEditText.setText("");
                            _closeSoftInput();
                        }
                    });
                    mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean isFocus) {
                            if (isFocus) {
                                mPlayerView.editVideo();
                            }
                            mIsFocus = isFocus;
                        }
                    });



                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }



        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPlayerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (_isHideSoftInput(view, (int) ev.getX(), (int) ev.getY())) {
            _closeSoftInput();
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void _closeSoftInput() {
        mEditText.clearFocus();
        SoftInputUtils.closeSoftInput(this);
        mPlayerView.recoverFromEditVideo();
    }

    private boolean _isHideSoftInput(View view, int x, int y) {
        if (view == null || !(view instanceof EditText) || !mIsFocus) {
            return false;
        }
        return x < mEtLayout.getLeft() ||
                x > mEtLayout.getRight() ||
                y < mEtLayout.getTop();
    }


}

