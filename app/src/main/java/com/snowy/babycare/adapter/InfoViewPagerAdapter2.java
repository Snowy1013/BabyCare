package com.snowy.babycare.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.snowy.babycare.R;

/**
 * Created by snowy on 16/2/25.
 * ViewPager异步加载网络图片，使用ImageLoader开源框架
 */
public class InfoViewPagerAdapter2 extends PagerAdapter {
    private String[] imageUrls;
    private Context context;
    private ImageLoader imageLoader;
    private DisplayImageOptions options; //DisplayImageOptions是用于设置图片显示的类

    public InfoViewPagerAdapter2(Context context, String[] imageUrls, ImageLoader imageLoader, DisplayImageOptions options) {
        this.imageUrls = imageUrls;
        this.context = context;
        this.imageLoader = imageLoader;
        this.options = options;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager_info, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_item_vp_info);
        final ProgressBar loading_info = (ProgressBar) view.findViewById(R.id.loading_info);

        /**
         * 显示图片
         * 参数1：图片url
         * 参数2：显示图片的控件
         * 参数3：显示图片的设置
         * 参数4：监听器
         */
        imageLoader.displayImage(imageUrls[position % imageUrls.length], imageView, options, new SimpleImageLoadingListener(){
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                loading_info.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) { //获取图片失败的原因
                    case IO_ERROR:              // 文件I/O错误
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:        // 解码错误
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:        // 网络延迟
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:         // 内存不足
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:               // 原因不明
                        message = "Unknown error";
                        break;
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                loading_info.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                loading_info.setVisibility(View.GONE);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
