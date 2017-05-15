package com.example.administrator.testui7.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.testui7.R;
import com.example.administrator.testui7.Bean.UserInfo;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class MoneyActivity extends AppCompatActivity {
    Double money=0.0;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        // 初始化BmobPay对象,可以在支付时再初始化

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_money);
        //设置顶部返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("钱包");

        final ProgressDialog progress = new ProgressDialog(MoneyActivity.this);
        progress.setMessage("请稍等...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        tv = (TextView)findViewById(R.id.tv_money);
        BmobQuery<UserInfo> bmobQuery = new BmobQuery<UserInfo>();
        bmobQuery.addWhereEqualTo("userid",BmobUser.getCurrentUser().getObjectId());
        bmobQuery.setLimit(2);
        //执行查询方法
        bmobQuery.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if(e==null){
                    progress.dismiss();
                    Toast.makeText(MoneyActivity.this,"查询成功：共"+object.size()+"条数据。", Toast.LENGTH_SHORT).show();
                    for (UserInfo user : object) {
                        //获得playerName的信息
                        money =user.getMoney()+money;
                    }
                    tv.setText("余额："+money);
                    money = 0.0;
                }else{
                    progress.dismiss();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });

        findViewById(R.id.btn_kejin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MoneyActivity.this,AddActivity.class));
            }
        });
    }
}
