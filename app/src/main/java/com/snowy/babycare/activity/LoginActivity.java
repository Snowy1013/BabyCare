package com.snowy.babycare.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.snowy.babycare.R;
import com.snowy.babycare.bean.Member;
import com.snowy.babycare.bean.Result;
import com.snowy.babycare.http.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

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
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
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
                final String phone = et_phone_login.getText().toString();
                final String pwd = et_pwd_login.getText().toString();
                if(phone.equals("")){
                    Toast.makeText(this, "请输入您的账号", Toast.LENGTH_SHORT).show();
                }else if(pwd.equals("")){
                    Toast.makeText(this, "请输入您的密码", Toast.LENGTH_SHORT).show();
                }else {
                    //TODO
                    //首先使用 Volley.newRequestQueue获取 RequestQueue对象
                    RequestQueue requestQueue = VolleySingleton.getVolleySingleton(this).getRequestQueue();
                    //发送StringRequest请求
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            "http://cowthan-hanhan.aliapp.com/www/test-api/post1.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //解析返回的Json字符串
                            Gson gson = new Gson();
                            Result result = gson.fromJson(s, Result.class);
                            int code = result.getCode();
                            Member member = result.getResult();

                            //登录成功将数据保存到SharedPreferences，下次自动登录，并带回主界面
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putInt("id", member.getId());
                            editor.putString("nickname", member.getNickname());
                            editor.putInt("sex", member.getSex());
                            editor.putString("member_icon", member.getMember_icon());
                            editor.putInt("city_id", member.getCity_id());
                            editor.putInt("province_id", member.getProvince_id());
                            editor.putInt("points_nums", member.getPoints_nums());
                            editor.putInt("msg_unread_nums", member.getMsg_unread_nums());
                            editor.putString("sid", member.getSid());
                            editor.commit();
                            //登录成功关闭该Activity，带着数据回到主界面
                            LoginActivity.this.finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Toast.makeText(LoginActivity.this, volleyError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("username", phone);
                            map.put("pwd", pwd);
                            return map;
                        }
                    };
                    //将StringRequest对象添加进RequestQueue请求队列中
                    VolleySingleton.getVolleySingleton(this).addToRequestQueue(stringRequest);
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
