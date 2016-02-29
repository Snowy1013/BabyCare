package com.snowy.babycare.fragment;

import android.graphics.Bitmap;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.snowy.babycare.R;
import com.snowy.babycare.adapter.InfoListViewAdapter2;
import com.snowy.babycare.adapter.InfoViewPagerAdapter2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snowy on 16/1/20.
 * ViewPager，ListView异步加载网络图片，循环滑动
 * 使用开源框架ImageLoader,jar包已导入
 */
public class InfoContentFragment2 extends Fragment implements View.OnClickListener {

    private LinearLayout tipsBox_info;//存放提示点的容器
    private ImageView[] tips;//提示性点点数组
    private int currentPage = 0;//当前展示的页码
    private PullToRefreshListView lv_info;
    private List<Map<String, Object>> infoList;//ListView中展示的数据集合
    private View viewpager_info;
    private ViewPager vp_img_info;
    private InfoListViewAdapter2 infoListViewAdapter;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    private String[] imgUrls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //创建一个view，并返回
        View view = inflater.inflate(R.layout.frag_content_info, container, false);

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(true)
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565) //设置图片的解码类型
                .displayer(new FadeInBitmapDisplayer(300))
                .build(); // 创建配置过的DisplayImageOption对象

        //初始化ImagView的网址数组
        imgUrls = new String[]{
                "http://img01.taopic.com/150308/240486-15030P95K189.jpg",
                "http://img.taopic.com/uploads/allimg/140201/234793-14020109154626.jpg",
                "http://img.taopic.com/uploads/allimg/140806/235021-140P60IP178.jpg",
                "http://img.taopic.com/uploads/allimg/140902/235106-140Z20R03673.jpg",
                "http://img.taopic.com/uploads/allimg/140817/235034-140QFSK550.jpg",
                "http://img.taopic.com/uploads/allimg/140201/234793-14020109422645.jpg",
                "http://img1.juimg.com/141011/330784-14101121520646.jpg",
                "http://img.taopic.com/uploads/allimg/140201/234793-14020111022647.jpg",
                "http://pic3.nipic.com/20090602/1101704_100027084_2.jpg",
                "http://img.juimg.com/tuku/yulantu/131226/328166-13122615544912.jpg"
        };

        initViewPager(); // 初始化ViewPager
        initListView(view);//初始化ListView,将ViewPager添加到ListView的addHeaderView()

        return view;
    }

    public void initListView(View view) {
        //查找控件
        lv_info = (PullToRefreshListView) view.findViewById(R.id.lv_info);

        //初始化ListView数据
        infoList = new ArrayList();
        initListViewData();

        infoListViewAdapter = new InfoListViewAdapter2(getActivity(), infoList, imageLoader, options);
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
                initListViewData();
                new FinishRefresh().execute();
                infoListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉时添加数据
                Map<String, Object> map = new HashMap();
                map.put("imgUrl", "http://img01.taopic.com/150314/240379-1503141Q42788.jpg");
                map.put("title", "我是刷新出来的......");
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

    //初始化ListView数据
    public void initListViewData(){

        for (int i = 0; i < imgUrls.length; i++) {
            Map<String, Object> map = new HashMap();
            map.put("imgUrl", imgUrls[i]);
            map.put("title", "我是标题党");
            Date currentDate = new Date();
            SimpleDateFormat formater = new SimpleDateFormat("MM月dd日");
            map.put("date", formater.format(currentDate));
            map.put("talknum", i + "");
            infoList.add(map);
        }
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

        //初始化提示点
        initTips();


        vp_img_info.setAdapter(new InfoViewPagerAdapter2(getActivity(), imgUrls, imageLoader, options));

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
        int n = Integer.MAX_VALUE / 2 % imgUrls.length;
        int initPosition = Integer.MAX_VALUE / 2 - n;
        vp_img_info.setCurrentItem(initPosition);

    }

    //初始化viewpager的指示点
    public void initTips() {
        tips = new ImageView[imgUrls.length];
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

    @Override
    public void onDestroy() {
        imageLoader.stop();//停止加载图片
        super.onDestroy();
    }
}
