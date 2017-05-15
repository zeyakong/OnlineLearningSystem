package com.example.administrator.testui7.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.testui7.Activity.FenleiActivity;
import com.example.administrator.testui7.Adapter.FenquAdapter;
import com.example.administrator.testui7.Bean.FenquBean;
import com.example.administrator.testui7.R;
import com.example.administrator.testui7.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */

public class TypeFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private String[]        mFenquArray;
    private int[]           mIconArray;
    private List<FenquBean> mDatas;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fengqu, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fenqu_recyclerView);
        initData();

        return view;
    }
    public void initData() {
        //获取数据
        //mFenquArray = UIUtils.getResources().getStringArray(R.array.fenqu);  //strings文件
        mFenquArray =new String[]{"计算机","数 学","语 文","物 理","英 语","马克思","dota2","娱 乐","科 学","化 学","生 物","文学鉴赏"};
        mIconArray = new int[]{R.mipmap.ic_category_01, R.mipmap.ic_category_02, R.mipmap.ic_category_03,
                R.mipmap.ic_category_04, R.mipmap.ic_category_05, R.mipmap.ic_category_06,
                R.mipmap.ic_category_07, R.mipmap.ic_category_08, R.mipmap.ic_category_09, R.mipmap.ic_category_10,
                R.mipmap.ic_category_11,R.mipmap.ic_category_13};
        mDatas = new ArrayList<>();
        for(int i = 0;i<mFenquArray.length;i++){
            FenquBean bean = new FenquBean();
            bean.icon = mIconArray[i];
            bean.title = mFenquArray[i];
            mDatas.add(bean);
        }

        mRecyclerView.setLayoutManager(new GridLayoutManager(UIUtils.getContext(), 3));
        FenquAdapter mAdapter=new FenquAdapter(this.getActivity(), mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new FenquAdapter.OnRecyclerViewItemClickListener(){


                                            @Override
                                            public void onItemClick(View view, String data) {
                                                Intent intent = new Intent();
                                                //在Intent对象当中添加一个键值对

                                                intent.setClass(getActivity(),FenleiActivity.class);
                                                startActivity(intent);
                                            }
                                        }
        );
    }

}
