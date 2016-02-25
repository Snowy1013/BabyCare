package com.snowy.babycare.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by snowy on 16/2/25.
 */
public class InfoViewPagerAdapter extends PagerAdapter {
    private int[] images;
    private Context context;
    public InfoViewPagerAdapter(Context context, int[] images) {
        this.images = images;
        this.context = context;
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
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView img = new ImageView(context);
        img.setImageResource(images[position%images.length]);
        container.addView(img);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
