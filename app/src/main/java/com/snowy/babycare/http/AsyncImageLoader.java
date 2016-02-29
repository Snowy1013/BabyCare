package com.snowy.babycare.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by snowy on 16/2/28.
 */
public class AsyncImageLoader {
    //软引用，使用内存做临时缓存，当程序退出或内存不足时清楚软引用
    private HashMap<String, SoftReference<Drawable>> imageCache;

    public AsyncImageLoader() {
        imageCache = new HashMap<String, SoftReference<Drawable>>();
    }

    /**
     * 定义回调接口
     */
    public interface ImageCallBack{
        public void imageLoaded(Drawable imageDrawable, String urlString);
    }

    /**
     * 创建子线程加载图片
     * 子线程加载完图片交给handler处理（子线程不能更新ui，而handler处在主线程，可以更新ui）
     * handler又交给imageCallback，imageCallback须要自己来实现，在这里可以对回调参数进行处理
     *
     * @param imageUrl
     * @param imageCallBack
     * @return
     */
    public Drawable loadDrawable(final String imageUrl, final ImageCallBack imageCallBack){
        //如果缓存中存在图片,则首先使用缓存中的图片
        if(imageCache.containsKey(imageUrl)){
            SoftReference<Drawable> softReference = imageCache.get(imageUrl);
            Drawable drawable = softReference.get();
            if(drawable != null){
                imageCallBack.imageLoaded(drawable, imageUrl);//执行回调
                return drawable;
            }
        }

        //在主线程中执行回调，更新UI
        final android.os.Handler handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                imageCallBack.imageLoaded((Drawable) msg.obj, imageUrl);
            }
        };

        //如果缓存中没有图片，则创建子线程访问网络并加载图片，把结果交给handler处理
        new Thread(){
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(imageUrl);
                //下载完图片放在缓存中
                imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
                //结果交给handler处理
                Message msg = handler.obtainMessage(0, drawable);
                handler.sendMessage(msg);
            }
        }.start();

        return null;
    }

    //下载图片
    public Drawable loadImageFromUrl(String urlString){
        Drawable drawable = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            drawable = new BitmapDrawable(bitmap);
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return drawable;
    }

    //清除缓存
    public void clearCache(){
        if(imageCache.size() > 0){
            imageCache.clear();
        }
    }
}
