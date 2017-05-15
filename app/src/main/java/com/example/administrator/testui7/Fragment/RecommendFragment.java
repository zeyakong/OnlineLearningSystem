package com.example.administrator.testui7.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.testui7.Activity.VideoInfoActivity;
import com.example.administrator.testui7.Adapter.VideoAdapter;
import com.example.administrator.testui7.Bean.Video;
import com.example.administrator.testui7.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.media.CamcorderProfile.get;

/**
 * Created by Administrator on 2017/3/18.
 */

public class RecommendFragment extends Fragment{
    private RecyclerView mRecyclerView;

    private List<Video> mDatas;

    private VideoAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recommend,container,false);

        initDatas();

        initViews(root);


        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("正在加载...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        BmobQuery<Video> query = new BmobQuery<Video>();
        query.addWhereGreaterThan("price",0);

        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<Video>() {

            @Override
            public void done(List<Video> list, BmobException e) {
                if(e==null){
                    progress.dismiss();
                    mDatas=list;
                    mAdapter = new VideoAdapter(getActivity(),mDatas);
                    mRecyclerView.setAdapter(mAdapter);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    mRecyclerView.setLayoutManager(linearLayoutManager);
                    mAdapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClik(View view, int position) {
                            Toast.makeText(getActivity(), "check"+position, Toast.LENGTH_SHORT).show();
                            Video temp= mDatas.get(position);
                            Intent intent = new Intent();
                            //在Intent对象当中添加一个键值对
                            intent.putExtra("video", temp);
                            intent.setClass(getActivity(),VideoInfoActivity.class);
                            startActivity(intent);
                        }
                    });
                }else{
                    progress.dismiss();
                    Toast.makeText(getActivity(),"失败："+e.getMessage()+","+e.getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private void initViews(View root) {
        mRecyclerView = (RecyclerView)root.findViewById(R.id.id_recyclerview);
    }

    private void initDatas() {
        mDatas = new ArrayList<Video>();
    }
}
