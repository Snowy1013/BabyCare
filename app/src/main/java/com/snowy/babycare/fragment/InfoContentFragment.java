package com.snowy.babycare.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.snowy.babycare.R;
import com.snowy.babycare.adapter.InfoListViewAdapter;
import com.snowy.babycare.adapter.InfoViewPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snowy on 16/1/20.
 * ViewPager加载本地图片，循环滑动
 */
public class InfoContentFragment extends Fragment implements View.OnClickListener {

    private LinearLayout tipsBox_info;//存放提示点的容器
    private ImageView[] tips;//提示性点点数组
    private int[] images;//图片ID数组
    private int currentPage = 0;//当前展示的页码
    private PullToRefreshListView lv_info;
    private List<Map<String, Object>> infoList;//ListView中展示的数据集合
    private View viewpager_info;
    private ViewPager vp_img_info;
    private InfoListViewAdapter infoListViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //创建一个view，并返回
        View view = inflater.inflate(R.layout.frag_content_info, container, false);
        initViewPager(); // 初始化ViewPager
        initListView(view);//初始化ListView,将ViewPager添加到ListView的addHeaderView()

        return view;
    }

    public void initListView(View view) {
        //查找控件
        lv_info = (PullToRefreshListView) view.findViewById(R.id.lv_info);

        //初始化ListView
        infoList = new ArrayList();
        for (int i = 0; i < 15; i++) {
            Map<String, Object> map = new HashMap();
            map.put("imgId", R.drawable.img4);
            map.put("title", "我是标题党");
            Date currentDate = new Date();
            SimpleDateFormat formater = new SimpleDateFormat("MM月dd日");
            map.put("date", formater.format(currentDate));
            map.put("talknum", i + "");
            infoList.add(map);
        }

        infoListViewAdapter = new InfoListViewAdapter(getActivity(), infoList);
        lv_info.setAdapter(null);
        lv_info.getRefreshableView().addHeaderView(viewpager_info);
        lv_info.setAdapter(infoListViewAdapter);

        //设置上拉还是下拉刷新
        /**
         *如果Mode设置成Mode.BOTH，需要设置刷新Listener为OnRefreshListener2，
         * 并实现onPullDownToRefresh()、onPullUpToRefresh()两个方法。
         *如果Mode设置成Mode.PULL_FROM_START或Mode.PULL_FROM_END，
         * 需要设置刷新Listener为OnRefreshListener，同时实现onRefresh()方法。
         *当然也可以设置为OnRefreshListener2，
         * 但是Mode.PULL_FROM_START的时候只调用onPullDownToRefresh()方法，
         * Mode.PULL_FROM_END的时候只调用onPullUpToRefresh()方法.
         */
        lv_info.setMode(PullToRefreshBase.Mode.BOTH);
        //设置上拉下拉时的显示
        initPull();
        //设置监听事件
        lv_info.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉时重置数据
                infoList.clear();
                for (int i = 0; i < 16; i++) {
                    Map<String, Object> map = new HashMap();
                    map.put("imgId", R.drawable.img2);
                    map.put("title", "我是标题党");
                    Date currentDate = new Date();
                    SimpleDateFormat formater = new SimpleDateFormat("MM月dd日");
                    map.put("date", formater.format(currentDate));
                    map.put("talknum", i + 100 + "");
                    infoList.add(map);
                }
                new FinishRefresh().execute();
                infoListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉时添加数据
                Map<String, Object> map = new HashMap();
                map.put("imgId", R.drawable.img3);
                map.put("title", "我是标题党");
                Date currentDate = new Date();
                SimpleDateFormat formater = new SimpleDateFormat("MM月dd日");
                map.put("date", formater.format(currentDate));
                map.put("talknum", 100 + "");
                infoList.add(map);
                new FinishRefresh().execute();
                infoListViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initPull() {
        ILoadingLayout startLables = lv_info.getLoadingLayoutProxy(true, false);//前者为true表示下拉
        startLables.setPullLabel("下拉刷新......");//刚下拉是显示
        startLables.setRefreshingLabel("正在载入.....");//刷新时显示
        startLables.setReleaseLabel("放开刷新......");//下来一定距离时显示

        ILoadingLayout endLables = lv_info.getLoadingLayoutProxy(false, true);//后者为true表示上拉
        endLables.setPullLabel("上拉刷新......");
        endLables.setRefreshingLabel("正在载入.....");
        endLables.setReleaseLabel("放开刷新......");

        String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
        lv_info.getLoadingLayoutProxy().setLastUpdatedLabel(label);
    }

    /**
     * 初始化ViewPager，以便于添加到listview的header
     */
    public void initViewPager() {
        viewpager_info = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_info, null);
        //查找控件
        tipsBox_info = (LinearLayout) viewpager_info.findViewById(R.id.tipsBox_info);
        vp_img_info = (ViewPager) viewpager_info.findViewById(R.id.vp_img_info);

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
        int initPosition = Integer.MAX_VALUE / 2 - n;
        vp_img_info.setCurrentItem(initPosition);

    }

    //初始化viewpager的指示点
    public void initTips() {
        tips = new ImageView[5];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.page_now_tip);//默认显示第一张图片，第一个点为选中状态
            } else {
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

    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            lv_info.onRefreshComplete(); // 设置刷新完成
        }
    }
}
