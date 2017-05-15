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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.testui7.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class RegisterActivity extends AppCompatActivity implements TextWatcher{
    private EditText etphone;
    private Button btnvalidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_register);
        //设置顶部返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");

        etphone = (EditText) findViewById(R.id.et_phone);
        btnvalidate = (Button)findViewById(R.id.btn_validate);
        etphone.addTextChangedListener(this);

        btnvalidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
                progress.setMessage("请稍等...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                if(isMobile(etphone.getText().toString())){
                    BmobSMS.requestSMSCode(etphone.getText().toString(),"标准模板", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId,BmobException ex) {
                            if(ex==null){//验证码发送成功
                                progress.dismiss();
                                Toast.makeText(RegisterActivity.this, "我们已经发送了一条短信，请填写验证码！", Toast.LENGTH_LONG).show();
                                Log.i("smile", "短信id："+smsId);//用于查询本次短信发送详情
                            }
                        }
                    });
                    Intent i =new Intent(RegisterActivity.this,ValidateActivity.class);
                    i.putExtra("phone",etphone.getText().toString());
                    startActivity(i);
                }else{
                    progress.dismiss();
                    Toast.makeText(RegisterActivity.this, "请输入正确的手机号！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!TextUtils.isEmpty(etphone.getText().toString()))
        {
            btnvalidate.setEnabled(true);
        }
        else
        {
            btnvalidate.setEnabled(false);
        }
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
}
