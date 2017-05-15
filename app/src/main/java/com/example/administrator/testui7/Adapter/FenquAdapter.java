package com.example.administrator.testui7.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.testui7.Bean.FenquBean;
import com.example.administrator.testui7.R;


import java.util.List;


/**
 * Created by Administrator on 2016/1/14 10:15.
 */
public class FenquAdapter extends RecyclerView.Adapter<MyViewHolder1> implements View.OnClickListener{

    List<FenquBean> mDatas;
    private Context context;
    private LayoutInflater inflater;
    private FenquAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }
    public void setOnItemClickListener(FenquAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public FenquAdapter(Context context,List<FenquBean> datas) {
        mDatas = datas;
        this.context = context;
        inflater=LayoutInflater. from(context);
    }


    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fenqu, parent, false);
        MyViewHolder1 viewHolder = new MyViewHolder1(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder1 holder, int position) {
        FenquBean vi = mDatas.get(position);
        holder.iv_image.setImageResource(vi.icon);
        holder.tv_title.setText(vi.title);
    }





    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());

        }
    }
}

class MyViewHolder1 extends RecyclerView.ViewHolder {
    ImageView iv_image;
    TextView tv_title;


    public MyViewHolder1(View itemView) {
        super(itemView);
        iv_image= (ImageView) itemView.findViewById(R.id.item_fenqu_icon);
        tv_title = (TextView) itemView.findViewById(R.id.item_fenqu_tv);

    }
}

//class FenquViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
//    @Bind(R.id.item_fenqu_icon)
//    ImageView mIcon;
//    @Bind(R.id.item_fenqu_tv)
//    TextView mTv;
//
//
//    public FenquViewHolder(View itemView) {
//        super(itemView);
//        ButterKnife.bind(this,itemView);
//
//    }
//
//    public void loadData(FenquBean fenquBean) {
//        mIcon.setImageResource(fenquBean.icon);
//        mTv.setText(fenquBean.title);
//    }
//
//
//    @Override
//    public void onClick(View v) {
//                        Snackbar.make(itemView, "item监听成功"+data, Snackbar.LENGTH_SHORT)
//                        .setAction("Undo", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                // Perform anything for the action selected
//                            }
//                        })
//                        .show();
//    }
//}