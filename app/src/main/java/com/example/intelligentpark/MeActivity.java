package com.example.intelligentpark;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.intelligentpark.me.Login;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by 李盛乐 on 2017.10.2.
 */

public class MeActivity extends Activity{
    private Button myIcon;
    private Button order;
    private Button money;
    private Button voucher;
    private Button invitation;
    private Button feedback;
    private Button quit;
    private Button setting;
    private Button back;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        myIcon = (Button) findViewById(R.id.myIcon);
        order = (Button) findViewById(R.id.order);
        money = (Button) findViewById(R.id.money);
        voucher = (Button) findViewById(R.id.voucher);
        invitation = (Button) findViewById(R.id.invitation);
        feedback = (Button) findViewById(R.id.feedback);
        quit = (Button) findViewById(R.id.quit);
        setting = (Button) findViewById(R.id.setting);
        back = (Button) findViewById(R.id.back);
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("user_name", null);
        if (!StringUtils.isEmpty(name)) {
            myIcon.setText("      " + name);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeActivity.this.finish();
            }
        });
        myIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
