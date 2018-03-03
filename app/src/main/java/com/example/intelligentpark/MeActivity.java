package com.example.intelligentpark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.intelligentpark.me.Login;

/**
 * Created by 李盛乐 on 2017.10.2.
 */

public class MeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        Button myIcon = (Button) findViewById(R.id.myIcon);
        Button order = (Button) findViewById(R.id.order);
        Button money = (Button) findViewById(R.id.money);
        Button voucher = (Button) findViewById(R.id.voucher);
        Button invitation = (Button) findViewById(R.id.invitation);
        Button feedback = (Button) findViewById(R.id.feedback);
        Button quit = (Button) findViewById(R.id.quit);
        Button setting = (Button) findViewById(R.id.setting);
        Button back = (Button) findViewById(R.id.back);

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
