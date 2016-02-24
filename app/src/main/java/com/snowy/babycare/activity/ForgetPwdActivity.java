package com.snowy.babycare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.snowy.babycare.R;

/**
 * Created by snowy on 16/2/24.
 */
public class ForgetPwdActivity extends Activity implements View.OnClickListener{
    private EditText et_phone_forgetpwd;
    private EditText et_code_forgetpwd;
    private EditText et_pwd_forgetpwd;
    private Button btn_getcode_forgetpwd;
    private Button btn_submit_forgetpwd;
    private ImageView btn_visible_forgetpwd;
    private ImageView back_forgetpwd;
    private boolean isPwdVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);

        initView();
    }

    private void initView(){
        et_phone_forgetpwd = (EditText) findViewById(R.id.et_phone_forgetpwd);
        et_code_forgetpwd = (EditText) findViewById(R.id.et_code_forgetpwd);
        et_pwd_forgetpwd = (EditText) findViewById(R.id.et_pwd_forgetpwd);
        btn_getcode_forgetpwd = (Button) findViewById(R.id.btn_getcode_forgetpwd);
        btn_submit_forgetpwd = (Button) findViewById(R.id.btn_submit_forgetpwd);
        btn_visible_forgetpwd = (ImageView) findViewById(R.id.btn_visible_forgetpwd);
        back_forgetpwd = (ImageView) findViewById(R.id.back_forgetpwd);

        btn_getcode_forgetpwd.setOnClickListener(this);
        btn_submit_forgetpwd.setOnClickListener(this);
        btn_visible_forgetpwd.setOnClickListener(this);
        back_forgetpwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String phone = null;
        switch (v.getId()){
            case R.id.back_forgetpwd:
                //返回
                finish();
                break;
            case R.id.btn_getcode_forgetpwd:
                phone = et_phone_forgetpwd.getText().toString();
                //获取验证码
                if(phone.equals("")) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                //TODO
                break;
            case R.id.btn_submit_forgetpwd:
                //提交
                phone = et_phone_forgetpwd.getText().toString();
                String code = et_code_forgetpwd.getText().toString();
                String pwd = et_pwd_forgetpwd.getText().toString();

                if(phone.equals("")){
                    Toast.makeText(this, "请输入11位手机号", Toast.LENGTH_SHORT).show();
                }else if(code.equals("")){
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }else if(pwd.equals("")){
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                }else{
                    //TODO
                    Toast.makeText(this, "phone=" + phone + "; pwd= " + pwd + "; code=" + code, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_visible_forgetpwd:
                //密码隐藏显示
                if(!isPwdVisible){
                    btn_visible_forgetpwd.setImageResource(R.drawable.eye_blue);
                    //显示密码,如：123445
                    et_pwd_forgetpwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPwdVisible = true;
                }else {
                    btn_visible_forgetpwd.setImageResource(R.drawable.eye_black);
                    //隐藏密码，如：......
                    et_pwd_forgetpwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPwdVisible = false;
                }
                break;
        }
    }
}
