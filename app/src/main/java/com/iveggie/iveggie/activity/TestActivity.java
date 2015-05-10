package com.iveggie.iveggie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iveggie.framework.activity.BaseActivity;
import com.iveggie.framework.activity.LaunchActivity;
import com.iveggie.iveggie.R;

/**
 * TestActivity
 * Desc:
 * Date: 2015/5/10
 * Time: 19:22
 * Created by: Wooxxx
 */
public class TestActivity extends BaseActivity {
    private Button logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        logOut = (Button) findViewById(R.id.logout);
        setListeners();
    }

    private void setListeners(){
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestActivity.this.getmCenter().cLogout();
                Intent intent = new Intent();
                intent.setClass(TestActivity.this, LaunchActivity.class);
                startActivity(intent);
            }
        });
    }
}
