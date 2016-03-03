package com.snowy.babycare.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.snowy.babycare.R;
import com.snowy.babycare.fragment.FindContentFragment;
import com.snowy.babycare.fragment.InfoContentFragment2;
import com.snowy.babycare.fragment.LiveContentFragment;
import com.snowy.babycare.fragment.QaContentFragment;
import com.snowy.babycare.fragment.ShopContentFragment;
import com.snowy.babycare.http.VolleySingleton;
import com.snowy.babycare.view.CircleImageView;

import java.security.PrivateKey;
import java.util.zip.Inflater;

/**
 *
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button btn_bottombar_main_info;
    private Button btn_bottombar_main_qa;
    private Button btn_bottombar_main_live;
    private Button btn_bottombar_main_shop;
    private Button btn_bottombar_main_find;

    //DrawerLayout中的控件
    private DrawerLayout main_drawlayout;
    private RelativeLayout main_draw_lefts;
    private RelativeLayout memberinfo_zone;
    private CircleImageView touxiang_zone;
    private TextView nickname_zone;
    private TextView points_nums_zone;
    private TextView msg_unread_nums_zone;
    private Button logout_zone;

    private InfoContentFragment2 infoContentFragment;
    private QaContentFragment qaContentFragment;
    private LiveContentFragment liveContentFragment;
    private ShopContentFragment shopContentFragment;
    private FindContentFragment findContentFragment;

    private long exitTime = 0;//返回键两次按下的时间差
    private SharedPreferences sp;
    private String sid;//判断用户是否登录的字符串

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

        main_drawlayout = (DrawerLayout) findViewById(R.id.main_drawlayout);
        main_draw_lefts = (RelativeLayout) findViewById(R.id.main_draw_lefts);
        memberinfo_zone = (RelativeLayout) findViewById(R.id.memberinfo_zone);
        touxiang_zone = (CircleImageView) findViewById(R.id.touxiang_zone);
        nickname_zone = (TextView) findViewById(R.id.nickname_zone);
        points_nums_zone= (TextView) findViewById(R.id.points_nums_zone);
        msg_unread_nums_zone = (TextView) findViewById(R.id.msg_unread_nums_zone);
        logout_zone = (Button) findViewById(R.id.logout_zone);

        //设置点击事件
        btn_bottombar_main_info.setOnClickListener(this);
        btn_bottombar_main_qa.setOnClickListener(this);
        btn_bottombar_main_live.setOnClickListener(this);
        btn_bottombar_main_shop.setOnClickListener(this);
        btn_bottombar_main_find.setOnClickListener(this);
        //给头像所在的RelativeLayout添加点击事件
        memberinfo_zone.setOnClickListener(this);
        logout_zone.setOnClickListener(this);
        touxiang_zone.setClickable(false);
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
        infoContentFragment = new InfoContentFragment2();
        //使用资讯Fragment替换content
        transaction.replace(R.id.content_main, infoContentFragment);
        //提交事务
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        sid = sp.getString("sid", null);
        if (sid != null){ //如果sid不为null，则表示用户已登录，否则未登录
            //如果已经登录显示 退出登录的按钮
            //从bundle获取数据，并设置个人中心数据
            String nickname = sp.getString("nickname", null);
            nickname_zone.setText(nickname);
            //获得的头像是网络图片，所以需要加载，此处使用Volley开源框架
            String member_icon = sp.getString("member_icon", null);
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                    touxiang_zone, R.drawable.touxiang, R.drawable.touxiang);
            ImageLoader imageLoader = VolleySingleton.getVolleySingleton(MainActivity.this).getImageLoader();
            imageLoader.get(member_icon, listener);

            int points_nums = sp.getInt("points_nums", 0);
            points_nums_zone.setText(points_nums+"");
            int msg_unread_nums = sp.getInt("msg_unread_nums", 0);
            msg_unread_nums_zone.setText(msg_unread_nums + "");
            logout_zone.setVisibility(View.VISIBLE);
        } else {
            //如果未登录显示
            touxiang_zone.setImageResource(R.drawable.touxiang);
            nickname_zone.setText("还没有昵称");
            points_nums_zone.setText("0");
            msg_unread_nums_zone.setText("0");
            logout_zone.setVisibility(View.GONE);
        }
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
                    infoContentFragment = new InfoContentFragment2();
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
            case R.id.memberinfo_zone:
                //头像所在的RelativeLayout添加点击事件,如果未登录则到LoginActivity，如果已登录，到编辑个人资料界面
                if(sid == null){
                    toLogin();
                }else {
                    //TODO
                }

                break;
            case R.id.logout_zone:
                //退出登录，将SharedPreferences中的sid设为null，跳转到登录页面
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("sid", null);
                editor.commit();
                toLogin();
                break;
        }
        fragmentTransaction.commit();
    }

    //跳转到登录页面的方法
    public void toLogin(){
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
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
