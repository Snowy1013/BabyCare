package com.snowy.babycare.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snowy.babycare.R;

/**
 * Created by snowy on 16/2/24.
 */
public class SignupActivity extends Activity implements View.OnClickListener{
    private EditText et_phone_signup;
    private EditText et_code_signup;
    private EditText et_pwd_signup;
    private Button btn_getcode_signup;
    private Button btn_zhuce_signup;
    private ImageView btn_visible_signup;
    private CheckBox cb_agree_signup;
    private TextView tv_agree_signup;
    private ImageView back_signup;
    private boolean isPwdVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();
    }

    private void initView(){
        et_phone_signup = (EditText) findViewById(R.id.et_phone_signup);
        et_code_signup = (EditText) findViewById(R.id.et_code_signup);
        et_pwd_signup = (EditText) findViewById(R.id.et_pwd_signup);
        btn_getcode_signup = (Button) findViewById(R.id.btn_getcode_signup);
        btn_zhuce_signup = (Button) findViewById(R.id.btn_zhuce_signup);
        btn_visible_signup = (ImageView) findViewById(R.id.btn_visible_signup);
        cb_agree_signup = (CheckBox) findViewById(R.id.cb_agree_signup);
        tv_agree_signup = (TextView) findViewById(R.id.tv_agree_signup);
        back_signup = (ImageView) findViewById(R.id.back_signup);

        btn_getcode_signup.setOnClickListener(this);
        btn_zhuce_signup.setOnClickListener(this);
        btn_visible_signup.setOnClickListener(this);
        back_signup.setOnClickListener(this);
        cb_agree_signup.setChecked(true);
        tv_agree_signup.setOnClickListener(this);
        cb_agree_signup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //TODO

            }
        });

    }

    @Override
    public void onClick(View v) {
        String phone = null;
        switch (v.getId()){
            case R.id.back_signup:
                //返回
                finish();
                break;
            case R.id.btn_getcode_signup:
                phone = et_phone_signup.getText().toString();
                //获取验证码
                if(phone.equals("")) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else {
                    //TODO
                }

                break;
            case R.id.btn_zhuce_signup:
                //注册
                phone = et_phone_signup.getText().toString();
                String code = et_code_signup.getText().toString();
                String pwd = et_pwd_signup.getText().toString();

                if(cb_agree_signup.isChecked()){
                    if(phone.equals("")){
                        Toast.makeText(this, "请输入11位手机号", Toast.LENGTH_SHORT).show();;
                    }else if(code.equals("")){
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    }else if(pwd.equals("")){
                        Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    }else{
                        //TODO
                        Toast.makeText(this, "phone=" + phone + "; pwd= " + pwd + "; code=" + code, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_visible_signup:
                //密码隐藏显示
                if(!isPwdVisible){
                    btn_visible_signup.setImageResource(R.drawable.eye_blue);
                    //显示密码,如：123445
                    et_pwd_signup.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPwdVisible = true;
                }else {
                    btn_visible_signup.setImageResource(R.drawable.eye_black);
                    //隐藏密码，如：......
                    et_pwd_signup.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPwdVisible = false;
                }
                break;
            case R.id.tv_agree_signup:
                //用户协议
//                Intent intent = new Intent();
                Toast.makeText(this, "《用户协议》", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
