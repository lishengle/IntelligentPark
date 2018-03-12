package com.example.intelligentpark.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.intelligentpark.R;
import com.example.intelligentpark.constants.Constants;
import com.example.intelligentpark.pojo.Account;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by 李盛乐 on 2017.10.3.
 */

public class Register extends Activity{

    private EditText name;
    private EditText password;
    private EditText password2;
    private EditText phone;
    private Button register;
    private Button login;
    private TextView nameWarn;
    private TextView phoneWarn;
    private TextView passwordWarn;
    private TextView password2Warn;

    private static Logger logger = Logger.getLogger("register");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);
        phone = (EditText) findViewById(R.id.phone);
        register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        nameWarn = (TextView) findViewById(R.id.nameWarn);
        phoneWarn = (TextView) findViewById(R.id.phoneWarn);
        passwordWarn = (TextView) findViewById(R.id.passwordWarn);
        password2Warn = (TextView) findViewById(R.id.password2Warn);

        bingdingRegister();
        bingdingLogin();
    }

    private void bingdingRegister() {
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String nameString = name.getText().toString();
                String passwordString = password.getText().toString();
                String phoneString = phone.getText().toString();
                String password2String = password2.getText().toString();
                boolean allright = true;
                if (StringUtils.isEmpty(nameString)) {
                    allright = false;
                    nameWarn.setTextColor(Constants.RED);
                }
                if (StringUtils.isEmpty(phoneString)) {
                    allright = false;
                    phoneWarn.setTextColor(Constants.RED);
                }
                if (StringUtils.isEmpty(passwordString)) {
                    allright = false;
                    passwordWarn.setTextColor(Constants.RED);
                }
                if (StringUtils.isEmpty(password2String)) {
                    allright = false;
                    password2Warn.setTextColor(Constants.RED);
                }
                if (!StringUtils.equals(passwordString, password2String)) {
                    allright = false;
                    password2Warn.setText("两次密码不一致");
                    password2Warn.setTextColor(Constants.RED);
                }
                if (allright) {
                    Account user = new Account(nameString, passwordString, phoneString);
                    try {
                        String userJson = JSON.toJSONString(user);
                        String responseString = Request.Post(Constants.HOST + "/me/register").bodyString(userJson, ContentType.APPLICATION_JSON).execute().returnContent().asString();
                        JSONObject response = JSON.parseObject(responseString);
                        if (response.getIntValue("status") == 0) {
                            Intent intent = new Intent(Register.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Register.this, "注册失败：" + response.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        logger.log(Level.INFO, "注册异常:" + e.getMessage());
                        Toast.makeText(Register.this, "注册失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void bingdingLogin(){

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
