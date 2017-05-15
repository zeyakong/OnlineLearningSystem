package com.example.administrator.testui7.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.testui7.R;
import com.example.administrator.testui7.Bean.UserInfo;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FinalRegisterActivity extends AppCompatActivity implements TextWatcher{
    private String phone2;
    private EditText etfinalusername,etfinalpassword,etfinalconform;
    private Button btnfinal;
    private BmobUser bu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_final_register);
        //设置顶部返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("用户信息");

        Intent i =getIntent();
        phone2 = i.getStringExtra("phone");
        EditText etfinalphone = (EditText) findViewById(R.id.et_final_phone);
        etfinalphone.setText("手机号："+phone2);

        etfinalusername = (EditText) findViewById(R.id.et_final_username);
        etfinalpassword = (EditText)findViewById(R.id.et_final_password);
        etfinalconform =(EditText)findViewById(R.id.et_final_conform);

        btnfinal =(Button)findViewById(R.id.btn_final) ;

        etfinalusername.addTextChangedListener(this);
        etfinalconform.addTextChangedListener(this);
        etfinalusername.addTextChangedListener(this);
        finalRegister();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(etfinalusername.getText().toString())
                && !TextUtils.isEmpty(etfinalpassword.getText().toString())
                &&!TextUtils.isEmpty(etfinalconform.getText().toString()))
        {
            btnfinal.setEnabled(true);
        }
        else
        {
            btnfinal.setEnabled(false);
        }
    }

    private void finalRegister(){

        findViewById(R.id.btn_final).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(FinalRegisterActivity.this);
                progress.setMessage("请稍等...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                if(!etfinalpassword.getText().toString().equals(etfinalconform.getText().toString())){
                    progress.dismiss();
                    Toast.makeText(FinalRegisterActivity.this, "两次密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                }else{
                    bu = new BmobUser();
                    bu.setUsername(etfinalusername.getText().toString());
                    bu.setPassword(etfinalpassword.getText().toString());
                    bu.setMobilePhoneNumber(phone2);
                    bu.setMobilePhoneNumberVerified(true);
                    //注意：不能用save方法进行注册
                    bu.signUp(new SaveListener<UserInfo>() {
                        @Override
                        public void done(UserInfo s, BmobException e) {
                            if(e==null){
                                Toast.makeText(FinalRegisterActivity.this,"注册成功:" +s.toString(), Toast.LENGTH_SHORT).show();
                                progress.setMessage("正在登录");
                                progress.show();
                                bu.login(new SaveListener<BmobUser>() {
                                    @Override
                                    public void done(BmobUser bmobUser, BmobException e) {
                                        if(e==null){
                                            progress.dismiss();
                                            Toast.makeText(FinalRegisterActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                            //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                                            //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(UserInfo.class)获取自定义用户信息
                                            Intent i = new Intent(FinalRegisterActivity.this,MainActivity.class);
                                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(i);
                                        }else{
                                            progress.dismiss();
                                            Toast.makeText(FinalRegisterActivity.this, "登录失败"+e, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                progress.dismiss();
                                Toast.makeText(FinalRegisterActivity.this, "注册失败"+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}
