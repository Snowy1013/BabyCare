package com.snowy.babycare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.snowy.babycare.R;

import java.util.List;
import java.util.Map;

/**
 * Created by snowy on 16/2/26.
 * ListView异步加载网络图片
 */
public class InfoListViewAdapter2 extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> list;
    private ImageLoader imageLoader;
    private DisplayImageOptions options; //DisplayImageOptions是用于设置图片显示的类

    public InfoListViewAdapter2(Context context, List<Map<String, Object>> list, ImageLoader imageLoader, DisplayImageOptions options) {
        this.list = list;
        this.context = context;
        this.imageLoader = imageLoader;
        this.options = options;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_info, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img_item_lv_info);
            holder.title = (TextView) convertView.findViewById(R.id.title_item_info);
            holder.date = (TextView) convertView.findViewById(R.id.date_item_info);
            holder.talk = (ImageView) convertView.findViewById(R.id.btn_talk_item_info);
            holder.talknum = (TextView) convertView.findViewById(R.id.talk_num_info);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String imgUrl = (String) list.get(position).get("imgUrl");

        holder.title.setText((String)list.get(position).get("title"));
        holder.date.setText((String)list.get(position).get("date"));
        holder.talknum.setText((String)list.get(position).get("talknum"));
        holder.talk.setTag(position);

        /**
         * 显示图片
         * 参数1：图片url
         * 参数2：显示图片的控件
         * 参数3：显示图片的设置
         * 参数4：监听器
         */
        imageLoader.displayImage(imgUrl, holder.img, options, new SimpleImageLoadingListener(){
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
            }

        });
//        holder.talk.setTag(position);

        return convertView;
    }

    class ViewHolder  {
        private ImageView img;
        private TextView title;
        private TextView date;
        private TextView talknum;
        private ImageView talk;
    }
}
