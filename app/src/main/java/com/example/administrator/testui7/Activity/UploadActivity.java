package com.example.administrator.testui7.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.testui7.R;
import com.example.administrator.testui7.utils.CallbackBundle;
import com.example.administrator.testui7.utils.OpenFileDialog;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

public class UploadActivity extends AppCompatActivity {
    private Button bUpload,bChooesFile;
    private BmobUser bu;
    private TextView tUsername,tPath;
    private EditText ePrice;
    private  String filepath;
    private  BmobFile bmobFile;
    static private int openfileDialogId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        initView();

        initData();
    }

    private void initData() {
        bu = BmobUser.getCurrentUser();
        tUsername.setText(bu.getUsername());

        bChooesFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(openfileDialogId);
            }
        });

        bUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UploadActivity.this, "check!", Toast.LENGTH_SHORT).show();
                if(filepath!=null){
                    bmobFile = new BmobFile(new File(filepath));
                    final ProgressDialog progress = new ProgressDialog(UploadActivity.this);
                    progress.setMessage("正在上传...请稍等");
                    progress.setCanceledOnTouchOutside(false);
                    progress.show();
                    bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                progress.dismiss();
                                //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                Toast.makeText(UploadActivity.this,"上传文件成功:" + bmobFile.getFileUrl(), Toast.LENGTH_SHORT).show();
                            }else{
                                progress.dismiss();
                                Toast.makeText(UploadActivity.this, "上传文件失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onProgress(Integer value) {
                            // 返回的上传进度（百分比）
                        }
                    });
                }else {
                    Toast.makeText(UploadActivity.this, "error file path null!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_upload);
        //设置顶部返回按钮
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("上传视频");

        bUpload = (Button) findViewById(R.id.btn_upload);
        bChooesFile = (Button) findViewById(R.id.btn_choosefile);
        tUsername = (TextView) findViewById(R.id.tv_uploadname);
        ePrice = (EditText) findViewById(R.id.et_price);
        tPath = (TextView) findViewById(R.id.tv_path);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==openfileDialogId){
            Map<String, Integer> images = new HashMap<String, Integer>();
            // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
            images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);   // 根目录图标
            images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);    //返回上一层的图标
            images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);   //文件夹图标
            images.put("mp4", R.drawable.filedialog_wavfile);   //avi文件图标
            images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            filepath = bundle.getString("path");
                            tPath.setText(filepath);
                        }
                    },
                    ".mp4;",
                    images);
            return dialog;
        }
        return null;
    }
}
