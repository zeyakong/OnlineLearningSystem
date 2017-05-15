package com.example.administrator.testui7.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.testui7.R;
import com.example.administrator.testui7.Bean.UserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LoginActivity extends AppCompatActivity implements TextWatcher{

    private TextView tlogin;
    private EditText etusername, etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //初始化Bmob
        Bmob.initialize(this, "376f1cc3355ed774826bf66035e39999");
        BmobUser user = BmobUser.getCurrentUser();
        //判断是否有用户缓存
        if(user!=null){
            //用户已经登录了！
            Intent i =new Intent(LoginActivity.this,MainActivity.class);
            //这个flag使得跳转到mainActivity中，按返回键不会再返回到这个页面 直接退出整个程序
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        //没有登录则进行下一步
        //登录
        tlogin = (TextView) findViewById(R.id.tv_login);
        etusername = (EditText)findViewById(R.id.et_username);
        etpassword = (EditText)findViewById(R.id.et_password);

        etusername.addTextChangedListener(this);
        etpassword.addTextChangedListener(this);

        //注册界面
        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        Login ();
    }


    //一下三个方法实现没有输入时，登录按钮是灰色的类似b站。
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        if (!TextUtils.isEmpty(etusername.getText().toString())
                && !TextUtils.isEmpty(etpassword.getText().toString()))
        {
            tlogin.setEnabled(true);
        }
        else
        {
            tlogin.setEnabled(false);
        }
    }

    //以下方法实现判断用户输入类型
    /**
     * 验证邮箱输入是否合法
     *
     * @param strEmail
     * @return
     */
    public static boolean isEmail(String strEmail) {
        // String strPattern =
        // "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        String strPattern = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * 验证是否是手机号码
     *
     * @param str
     * @return
     */
    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    //登录主体的逻辑实现
    private void Login (){
        //其他方法已经得到了不为空的用户名和密码，这里不用判断。

        findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
                progress.setMessage("正在登陆...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                String account=etusername.getText().toString();
                String password = etpassword.getText().toString();
                Log.i("smile","account="+account+"/password="+password);
                BmobUser.loginByAccount(account,password, new LogInListener<UserInfo>() {
                    @Override
                    public void done(UserInfo user, BmobException e) {
                        if(user!=null){
                            progress.dismiss();
                            Intent i =new Intent(LoginActivity.this,MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }else{
                            progress.dismiss();
                            Toast.makeText(LoginActivity.this, "登陆失败"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
