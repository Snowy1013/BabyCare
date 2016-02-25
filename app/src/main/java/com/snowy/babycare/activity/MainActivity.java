package com.snowy.babycare.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.snowy.babycare.R;
import com.snowy.babycare.fragment.FindContentFragment;
import com.snowy.babycare.fragment.InfoContentFragment;
import com.snowy.babycare.fragment.LiveContentFragment;
import com.snowy.babycare.fragment.QaContentFragment;
import com.snowy.babycare.fragment.ShopContentFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button btn_bottombar_main_info;
    private Button btn_bottombar_main_qa;
    private Button btn_bottombar_main_live;
    private Button btn_bottombar_main_shop;
    private Button btn_bottombar_main_find;
    private Button btn_login;

    private InfoContentFragment infoContentFragment;
    private QaContentFragment qaContentFragment;
    private LiveContentFragment liveContentFragment;
    private ShopContentFragment shopContentFragment;
    private FindContentFragment findContentFragment;

    private long exitTime = 0;//返回键两次按下的时间差

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //设置默认的Fragment界面
        setDefaultFragment();
    }

    private void initView(){
        btn_bottombar_main_info = (Button) findViewById(R.id.btn_bottombar_main_info);
        btn_bottombar_main_qa = (Button) findViewById(R.id.btn_bottombar_main_qa);
        btn_bottombar_main_live = (Button) findViewById(R.id.btn_bottombar_main_live);
        btn_bottombar_main_shop = (Button) findViewById(R.id.btn_bottombar_main_shop);
        btn_bottombar_main_find = (Button) findViewById(R.id.btn_bottombar_main_find);
        btn_login = (Button) findViewById(R.id.btn_login);//登录按钮

        //设置点击事件
        btn_bottombar_main_info.setOnClickListener(this);
        btn_bottombar_main_qa.setOnClickListener(this);
        btn_bottombar_main_live.setOnClickListener(this);
        btn_bottombar_main_shop.setOnClickListener(this);
        btn_bottombar_main_find.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    /**
     * 设置默认的Fragment界面为资讯
     */
    public void setDefaultFragment(){
        //把当前选择的资讯按钮设为选中状态，其他按钮为非选中状态
        btn_bottombar_main_info.setSelected(true);
        if(btn_bottombar_main_qa.isSelected())
            btn_bottombar_main_qa.setSelected(false);
        if(btn_bottombar_main_live.isSelected())
            btn_bottombar_main_live.setSelected(false);
        if(btn_bottombar_main_shop.isSelected())
            btn_bottombar_main_shop.setSelected(false);
        if(btn_bottombar_main_find.isSelected())
            btn_bottombar_main_find.setSelected(false);
        //开启Fragment事务
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        infoContentFragment = new InfoContentFragment();
        //使用资讯Fragment替换content
        transaction.replace(R.id.content_main, infoContentFragment);
        //提交事务
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //开启Fragment事务
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (id) {
            case R.id.btn_bottombar_main_info:
                //资讯
                if(infoContentFragment == null)
                    infoContentFragment = new InfoContentFragment();
                fragmentTransaction.replace(R.id.content_main, infoContentFragment);

                //把当前选择的按钮设为选中状态，其他按钮为非选中状态
                btn_bottombar_main_info.setSelected(true);
                if(btn_bottombar_main_qa.isSelected())
                    btn_bottombar_main_qa.setSelected(false);
                if(btn_bottombar_main_live.isSelected())
                    btn_bottombar_main_live.setSelected(false);
                if(btn_bottombar_main_shop.isSelected())
                    btn_bottombar_main_shop.setSelected(false);
                if(btn_bottombar_main_find.isSelected())
                    btn_bottombar_main_find.setSelected(false);
                break;
            case R.id.btn_bottombar_main_qa:
                //问答
                if(qaContentFragment == null)
                    qaContentFragment = new QaContentFragment();
                fragmentTransaction.replace(R.id.content_main, qaContentFragment);

                //把当前选择的按钮设为选中状态，其他按钮为非选中状态
                btn_bottombar_main_qa.setSelected(true);
                if(btn_bottombar_main_info.isSelected())
                    btn_bottombar_main_info.setSelected(false);
                if(btn_bottombar_main_live.isSelected())
                    btn_bottombar_main_live.setSelected(false);
                if(btn_bottombar_main_shop.isSelected())
                    btn_bottombar_main_shop.setSelected(false);
                if(btn_bottombar_main_find.isSelected())
                    btn_bottombar_main_find.setSelected(false);
                break;
            case R.id.btn_bottombar_main_live:
                //直播
                if(liveContentFragment == null)
                    liveContentFragment = new LiveContentFragment();
                fragmentTransaction.replace(R.id.content_main, liveContentFragment);

                //把当前选择的按钮设为选中状态，其他按钮为非选中状态
                btn_bottombar_main_live.setSelected(true);
                if(btn_bottombar_main_qa.isSelected())
                    btn_bottombar_main_qa.setSelected(false);
                if(btn_bottombar_main_info.isSelected())
                    btn_bottombar_main_info.setSelected(false);
                if(btn_bottombar_main_shop.isSelected())
                    btn_bottombar_main_shop.setSelected(false);
                if(btn_bottombar_main_find.isSelected())
                    btn_bottombar_main_find.setSelected(false);
                break;
            case R.id.btn_bottombar_main_shop:
                //商城
                if (shopContentFragment == null)
                    shopContentFragment = new ShopContentFragment();
                fragmentTransaction.replace(R.id.content_main, shopContentFragment);

                //把当前选择的按钮设为选中状态，其他按钮为非选中状态
                btn_bottombar_main_shop.setSelected(true);
                if(btn_bottombar_main_qa.isSelected())
                    btn_bottombar_main_qa.setSelected(false);
                if(btn_bottombar_main_live.isSelected())
                    btn_bottombar_main_live.setSelected(false);
                if(btn_bottombar_main_info.isSelected())
                    btn_bottombar_main_info.setSelected(false);
                if(btn_bottombar_main_find.isSelected())
                    btn_bottombar_main_find.setSelected(false);
                break;
            case R.id.btn_bottombar_main_find:
                //发现
                if (findContentFragment == null)
                    findContentFragment = new FindContentFragment();
                fragmentTransaction.replace(R.id.content_main, findContentFragment);

                //把当前选择的按钮设为选中状态，其他按钮为非选中状态
                btn_bottombar_main_find.setSelected(true);
                if(btn_bottombar_main_qa.isSelected())
                    btn_bottombar_main_qa.setSelected(false);
                if(btn_bottombar_main_live.isSelected())
                    btn_bottombar_main_live.setSelected(false);
                if(btn_bottombar_main_shop.isSelected())
                    btn_bottombar_main_shop.setSelected(false);
                if(btn_bottombar_main_info.isSelected())
                    btn_bottombar_main_info.setSelected(false);
                break;
            case R.id.btn_login:
                //登录
                Intent intent = new Intent();
                intent.setClass(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 捕捉按键的操作
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //两次按下返回键退出程序
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){ //getRepeatCount什么意思？
            //判断时间间隔，小于2秒则退出程序
            long curTime = System.currentTimeMillis();
            if(curTime - exitTime > 2000){
                String msg = "再按一次退出程序";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                exitTime = curTime;
            }else {
                //小于2秒退出程序
                finish();
                //返回桌面操作
                /*Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);*/
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
