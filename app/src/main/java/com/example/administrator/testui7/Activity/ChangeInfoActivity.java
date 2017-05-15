package com.example.administrator.testui7.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.testui7.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangeInfoActivity extends AppCompatActivity {

    private TextView tUsername;
    private TextView tPhone;
    private TextView tMoney;
    private TextView tPoint;
    private TextView tEmail;
    private EditText ePw;
    private EditText eNewPw;
    private BmobUser bu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        initView();

        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_changeinfo);
        //设置顶部返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("详细信息");

        tUsername = (TextView) findViewById(R.id.tv_userinfoname);
        tPhone = (TextView) findViewById(R.id.tv_userinfophnoe);
        tMoney = (TextView) findViewById(R.id.tv_userinfomoney);
        tPoint = (TextView) findViewById(R.id.tv_userinfopoint);
        tEmail = (TextView) findViewById(R.id.tv_userinfoemail);
        eNewPw = (EditText) findViewById(R.id.et_userinfonewpw);
        ePw = (EditText) findViewById(R.id.et_userinfopw);
    }

    private void initData() {
        bu = BmobUser.getCurrentUser();
        tUsername.setText(bu.getUsername());
        tPhone.setText(bu.getMobilePhoneNumber());
        if (bu.getEmailVerified() != null) {
            tEmail.setText(bu.getEmail());
        }else{
            //点击可绑定邮箱
            tEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void ChangePw(View view) {
            /* 这是兼容的 AlertDialog
            这里使用了 android.support.v7.app.AlertDialog.Builder
            可以直接在头部写 import android.support.v7.app.AlertDialog
            那么下面就可以写成 AlertDialog.Builder
            */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("修改密码");
        builder.setNegativeButton("取消", null);
        LayoutInflater inflater = getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog,(ViewGroup) findViewById(R.id.dialog));
        eNewPw = (EditText) dialog.findViewById(R.id.et_userinfonewpw);
        ePw = (EditText) dialog.findViewById(R.id.et_userinfopw);
        builder.setView(dialog);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    BmobUser.updateCurrentUserPassword(ePw.getText().toString(), eNewPw.getText().toString(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ChangeInfoActivity.this, "修改成功，请重新登录！", Toast.LENGTH_SHORT).show();
                                bu.logOut();
                                Intent i = new Intent(ChangeInfoActivity.this, LoginActivity.class);
                                //这个flag使得跳转到mainActivity中，按返回键不会再返回到这个页面 直接退出整个程序
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                            }else{
                                Toast.makeText(ChangeInfoActivity.this, "旧密码不匹配呀！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
        });
        builder.show();
    }

    public void Logout(View view) {
            /* 这是兼容的 AlertDialog
            这里使用了 android.support.v7.app.AlertDialog.Builder
            可以直接在头部写 import android.support.v7.app.AlertDialog
            那么下面就可以写成 AlertDialog.Builder
            */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("你确定不是手滑？");
        builder.setNegativeButton("手滑", null);

        builder.setPositiveButton("注销", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BmobUser bu = BmobUser.getCurrentUser();
                bu.logOut();
                Intent i = new Intent(ChangeInfoActivity.this, LoginActivity.class);
                //这个flag使得跳转到mainActivity中，按返回键不会再返回到这个页面 直接退出整个程序
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        builder.show();
    }
}
