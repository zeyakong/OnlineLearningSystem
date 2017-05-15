package com.example.administrator.testui7.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.testui7.Bean.Video;
import com.example.administrator.testui7.R;


import java.util.List;

/**
 * Created by 晓勇 on 2015/7/12 0012.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder>implements View.OnClickListener  {
    private List<Video> datas;
    private Context context;
    private List<Integer> lists;
    private LayoutInflater inflater;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public MyRecyclerViewAdapter(Context context, List<Video> datas) {
        this.datas = datas;
        this.context = context;
        inflater=LayoutInflater. from(context);
    }

//    private void getRandomHeights(List<video> datas) {
//        lists = new ArrayList<>();
//        for (int i = 0; i < datas.size(); i++) {
//            lists.add((int) (200 + Math.random() * 400));
//        }
//    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       // ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        // params.height = lists.get(position);//把随机的高度赋予item布局
        //holder.itemView.setLayoutParams(params);
        Video vi = datas.get(position);
        holder.iv_image.setImageResource(R.drawable.book);
        holder.tv_title.setText( vi.getVideoName());
        holder.tv_price.setText( "￥" + vi.getPrice());
        holder.tv_author.setText( "作者:" + vi.getAuther());

        holder.tv_date.setText("上传时间："+vi.getCreatedAt());
        //holder.tv_num_rating.setText("评分:"+vi.getRating());
        holder.tv_num_rating.setText("评分:"+vi.getRating());
       holder.itemView.setTag(vi.getObjectId());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());

        }
    }
}


