package com.snowy.babycare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.snowy.babycare.R;

/**
 * Created by snowy on 16/2/23.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    private ImageView back_login;
    private EditText et_phone_login;
    private EditText et_pwd_login;
    private Button btn_login2;
    private Button btn_forget_pwd;
    private Button btn_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        back_login = (ImageView) findViewById(R.id.back_login);
        et_phone_login = (EditText) findViewById(R.id.et_phone_logiin);
        et_pwd_login = (EditText) findViewById(R.id.et_pwd_login);
        btn_forget_pwd = (Button) findViewById(R.id.btn_forget_pwd);
        btn_sign = (Button) findViewById(R.id.btn_sign_login);
        btn_login2 = (Button) findViewById(R.id.btn_denglu_login);

        back_login.setOnClickListener(this);
        btn_forget_pwd.setOnClickListener(this);
        btn_sign.setOnClickListener(this);
        btn_login2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent();
        switch (id){
            case R.id.back_login:
                finish();
                break;
            case R.id.btn_denglu_login:
                //登录
                String phone = et_phone_login.getText().toString();
                String pwd = et_pwd_login.getText().toString();
                if(phone.equals("")){
                    Toast.makeText(this, "请输入您的账号", Toast.LENGTH_SHORT).show();
                }else if(pwd.equals("")){
                    Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
                }else {
                    //TODO
                    Toast.makeText(this, "phone=" + phone + "; " +
                            "pwd=" + pwd, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_forget_pwd:
                //忘记密码
                intent.setClass(this, ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sign_login:
                //注册
                intent.setClass(this, SignupActivity.class);
                startActivity(intent);
                break;
        }
    }
}
