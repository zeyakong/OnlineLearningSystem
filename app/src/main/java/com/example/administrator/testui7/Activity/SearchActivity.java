package com.example.administrator.testui7.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.administrator.testui7.R;

public class SearchActivity extends AppCompatActivity {

    private SearchView sv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sv = (SearchView) this.findViewById(R.id.sv);

        sv.setIconifiedByDefault(false);

        sv.setSubmitButtonEnabled(true);

    }
}
