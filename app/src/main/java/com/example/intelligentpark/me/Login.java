package com.example.intelligentpark.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 李盛乐 on 2017.10.3.
 */

public class Login extends Activity{
    private static final String STATUS = "status";
    private static final String MSG = "msg";
    private static final int LOGIN_INT = 0x123;
    private static final String LOGIN_SUCCESS_STRING = "success";
    private static final String LOGIN_FAIL_STRING = "fail";

    private Button back;
    private Button register;
    private EditText phone;
    private EditText password;
    private Button eye;
    private Button login;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == LOGIN_INT) {
                if (msg.getData().getString(STATUS).equals(LOGIN_SUCCESS_STRING)) {
                    Intent intent = new Intent(Login.this, MeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, msg.getData().getString(MSG), Toast.LENGTH_SHORT);
                }
            }
        }
    };

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

                new Thread(){
                    @Override
                    public void run() {
                        String phoneString = phone.getText().toString();
                        String pwString = password.getText().toString();
                        if (StringUtils.isEmpty(phoneString)) {
                            Toast.makeText(Login.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                        }
                        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
                        Matcher matcher = pattern.matcher(phoneString);
                        if (!matcher.matches()) {
                            Toast.makeText(Login.this, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                        } else {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("phone", phoneString);
                            jsonObject.put("password", pwString);
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            try {
                                OkHttpClient okHttpClient = new OkHttpClient();
                                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), jsonObject.toJSONString());
                                Request request = new Request.Builder().url(Constants.HOST + "/login").post(requestBody).build();
                                Response response = okHttpClient.newCall(request).execute();
                                JSONObject resJson = JSON.parseObject(response.body().toString());
                                if (resJson.getIntValue("status") == 0) {
                                    Account user =(Account) JSON.parse(resJson.getString("account"));
                                    editor.putString("user_name", user.getName());
                                    editor.putString("user_phone", user.getPhone());
                                    editor.commit();
                                    message.what = LOGIN_INT;
                                    bundle.putString(STATUS, LOGIN_SUCCESS_STRING);
                                    message.setData(bundle);
                                    handler.sendMessage(message);
                                } else {
                                    //Toast.makeText(Login.this, "登录失败" + resJson.getString("msg"), Toast.LENGTH_SHORT);
                                    message.what = LOGIN_INT;
                                    bundle.putString(STATUS, LOGIN_FAIL_STRING);
                                    bundle.putString(STATUS, "登录失败：" + jsonObject.getString("msg"));
                                    message.setData(bundle);
                                }
                            } catch (Exception e) {
                                System.out.print(e);
                                //Toast.makeText(Login.this, "登录失败" + e.getMessage(), Toast.LENGTH_SHORT);
                                message.what = LOGIN_INT;
                                bundle.putString(STATUS, LOGIN_FAIL_STRING);
                                bundle.putString(STATUS, "登录失败：" + e.getMessage());
                                message.setData(bundle);
                            }
                        }
                    }
                }.start();

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
