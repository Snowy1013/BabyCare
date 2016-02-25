package com.snowy.babycare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.snowy.babycare.R;
import com.snowy.babycare.adapter.InfoViewPagerAdapter;

/**
 * Created by Snowy on 16/1/20.
 */
public class InfoContentFragment extends Fragment implements View.OnClickListener{

    private ViewPager vp_img_info;
    private LinearLayout tipsBox_info;//存放提示点的容器
    private ImageView[] tips;//提示性点点数组
    private int[] images;//图片ID数组
    private int currentPage=0;//当前展示的页码
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //创建一个view，并返回
        View view = inflater.inflate(R.layout.frag_content_info, container, false);
        initView(view);//初始化界面
        initData(); // 初始化数据

        return view;
    }

    public void initView(View view){
        //查找控件
        vp_img_info = (ViewPager) view.findViewById(R.id.vp_img_info);
        tipsBox_info = (LinearLayout) view.findViewById(R.id.tipsBox_info);

    }

    public void initData() {
        //初始化ImagView的id数组
        images = new int[]{R.drawable.img1, R.drawable.img2,
                R.drawable.img3, R.drawable.img4, R.drawable.img5};
        //初始化提示点
        initTips();

        vp_img_info.setAdapter(new InfoViewPagerAdapter(getActivity(), images));

        //更改当前指示点
        vp_img_info.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tips[currentPage].setBackgroundResource(R.drawable.page_tip);
                currentPage = position % tips.length;
                tips[position % tips.length].setBackgroundResource(R.drawable.page_now_tip);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 一开始设置当前页面为一个很大的数，以至于一开始就能向前滑动
         *
         */
        int n = Integer.MAX_VALUE / 2 % images.length;
        int initPosition = Integer.MAX_VALUE / 2 - n ;
        vp_img_info.setCurrentItem(initPosition);
    }

    public void initTips() {
        tips = new ImageView[5];
        for(int i=0; i<tips.length; i++){
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10,10));
            tips[i] = imageView;
            if(i==0){
                imageView.setBackgroundResource(R.drawable.page_now_tip);//默认显示第一张图片，第一个点为选中状态
            }else {
                imageView.setBackgroundResource(R.drawable.page_tip);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 5;
            params.rightMargin = 5;
            tipsBox_info.addView(imageView, params);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
