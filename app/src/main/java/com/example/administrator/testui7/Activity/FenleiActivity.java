package com.example.administrator.testui7.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.administrator.testui7.Adapter.MyRecyclerViewAdapter;
import com.example.administrator.testui7.Bean.Video;
import com.example.administrator.testui7.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FenleiActivity extends AppCompatActivity {

    List<Video> datas = new ArrayList<>();
    MyRecyclerViewAdapter mAdapter;
    RecyclerView mRecyclerView;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenlei);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        mToolbar.setNavigationIcon(R.drawable.back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        setActionBar();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        BmobQuery<Video> query = new BmobQuery<Video>();

        query.addWhereEqualTo("videoType", "计算机");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法

        query.findObjects(new FindListener<Video>() {

            @Override
            public void done(List<Video> list, BmobException e) {
                if(e==null){
                    System.out.println("作者是"+list.get(0).getAuther()+"........................");

                    datas=list;
                    mAdapter=new MyRecyclerViewAdapter(FenleiActivity.this, datas);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnRecyclerViewItemClickListener(){
                        @Override
                        public void onItemClick(View view , String data){

                            Intent intent = new Intent();
                            //在Intent对象当中添加一个键值对
                            intent.putExtra("vedioObjectId", data);
                            intent.setClass(FenleiActivity.this,VideoPlayActivity.class);
                            startActivity(intent);
                            //startActivity(new Intent(getActivity(),VideoPlayActivity.class));
                        }
                    });
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });


    }

    private void setActionBar() {
        android.support.v7.app.ActionBar actionbar = getSupportActionBar(); //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true); //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        //actionbar.setDisplayShowHomeEnabled(true);
        //actionbar.setDisplayUseLogoEnabled(true); //显示标题
        //actionbar.setDisplayShowTitleEnabled(true);
    }
}
