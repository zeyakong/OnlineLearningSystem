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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.testui7.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ValidateActivity extends AppCompatActivity implements TextWatcher{
    private EditText etvalidate;
    private Button btnnext;
    private TextView tvphone;
    private String phone, validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_validate);
        //设置顶部返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("验证手机号");

        etvalidate = (EditText) findViewById(R.id.et_validate);
        btnnext = (Button)findViewById(R.id.btn_next);
        etvalidate.addTextChangedListener(this);

        Intent i =getIntent();
        tvphone = (TextView)findViewById(R.id.tv_phone);
        phone = i.getStringExtra("phone");

        tvphone.setText(phone);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(ValidateActivity.this);
                progress.setMessage("正在验证中...");
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                BmobSMS.verifySmsCode(phone,etvalidate.getText().toString(), new UpdateListener() {
                    @Override
                    public void done(BmobException ex) {
                        if(ex==null){//短信验证码已验证成功
                            progress.dismiss();
                            Log.i("smile", "验证通过");
                            Toast.makeText(ValidateActivity.this,"验证通过", Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(ValidateActivity.this,FinalRegisterActivity.class);
                            i.putExtra("phone",phone);
                            startActivity(i);
                        }else{
                            progress.dismiss();
                            Log.i("smile", "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                            Toast.makeText(ValidateActivity.this, "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
        if (!TextUtils.isEmpty(etvalidate.getText().toString()))
        {
            btnnext.setEnabled(true);
        }
        else
        {
            btnnext.setEnabled(false);
        }
    }
}
