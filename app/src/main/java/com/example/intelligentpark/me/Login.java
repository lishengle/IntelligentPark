package com.example.intelligentpark.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.intelligentpark.MeActivity;
import com.example.intelligentpark.R;
import com.example.intelligentpark.constants.Constants;
import com.example.intelligentpark.pojo.Account;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 李盛乐 on 2017.10.3.
 */

public class Login extends Activity{
    private Button back;
    private Button register;
    private EditText phone;
    private EditText password;
    private Button eye;
    private Button login;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        back = (Button) findViewById(R.id.back);
        register = (Button) findViewById(R.id.register);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        eye = (Button) findViewById(R.id.eye);
        login = (Button) findViewById(R.id.login);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login.this.finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        bingdingLogin();
    }

    private void bingdingLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneString = phone.getText().toString();
                String pwString = password.getText().toString();
                if (StringUtils.isEmpty(phoneString)) {
                    Toast.makeText(Login.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                Pattern pattern = Pattern.compile("^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\\d{8})?$");
                Matcher matcher = pattern.matcher(phoneString);
                if (!matcher.matches()) {
                    Toast.makeText(Login.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                } else {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("phone", phoneString);
                    jsonObject.put("password", pwString);
                    try {
                        String result = Request.Post(Constants.HOST + "/login").bodyString(jsonObject.toJSONString(), ContentType.APPLICATION_JSON).execute().returnContent().asString();
                        JSONObject response = JSON.parseObject(result);
                        if (response.getIntValue("status") == 0) {
                            Account user =(Account) JSON.parse(response.getString("account"));
                            editor.putString("user_name", user.getName());
                            editor.putString("user_phone", user.getPhone());
                            editor.commit();
                            Intent intent = new Intent(Login.this, MeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "登录失败" + response.getString("msg"), Toast.LENGTH_SHORT);
                        }
                    } catch (Exception e) {
                        Toast.makeText(Login.this, "登录失败" + e.getMessage(), Toast.LENGTH_SHORT);
                    }

                }
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
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
}
