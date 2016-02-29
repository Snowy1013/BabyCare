package com.snowy.babycare.http;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by snowy on 16/2/28.
 */
public class MyAsynctTask extends AsyncTask<String, Integer, Bitmap> {

    private ImageView imageView;
    private ProgressBar progressBar;
    private Context context;

    public MyAsynctTask( Context context, ImageView imageView,
                        ProgressBar progressBar) {
        super();
        this.context = context;
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    /**
     * 改方法在执行实际的后台操作时被UI线程调用，可以在该方法中做一些准备工作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 当publichProcess被调用以后，ＵＩ线程将调用这个有方法在界面上展示任务的情况，
     * 比如一个额进度条。这里是更新进度条
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        progressBar.setProgress(value);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
        super.onPostExecute(result);
    }


    /**
     * 该方法在OnpreExecute执行以后马上执行，改方法执行在后台线程当中，负责耗时的计算，
     * 可以调用publishProcess方法来实时更新任务进度
     * @param params
     * @return
     */
    @Override
    protected Bitmap doInBackground(String... params) { //输入可变长度的可变参数 和ＵＩ线程中的Asyna.execute()对应

        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            final int lenth = connection.getContentLength();
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setMax(lenth);
                }
            });

            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

            inputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }
}

