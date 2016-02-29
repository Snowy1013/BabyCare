package com.snowy.babycare.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.snowy.babycare.http.AsyncImageLoader;

/**
 * Created by snowy on 16/2/25.
 * ViewPager异步加载网络图片
 */
public class InfoViewPagerAdapter1 extends PagerAdapter {
    private String[] urlStrings;
    private Context context;
    private AsyncImageLoader asyncImageLoader;

    public InfoViewPagerAdapter1(Context context, String[] urlStrings) {
        this.urlStrings = urlStrings;
        this.context = context;
        asyncImageLoader = new AsyncImageLoader();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        Drawable drawable = asyncImageLoader.loadDrawable(urlStrings[position % urlStrings.length], new AsyncImageLoader.ImageCallBack() {
            @Override
            public void imageLoaded(Drawable imageDrawable, String urlString) {
                ImageView imageView = new ImageView(context);
                imageView.setBackground(imageDrawable);
                container.addView(imageView);
            }
        });

        ImageView imageView = new ImageView(context);
        imageView.setBackground(drawable);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
