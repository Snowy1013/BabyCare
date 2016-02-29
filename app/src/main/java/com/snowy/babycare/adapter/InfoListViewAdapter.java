package com.snowy.babycare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.snowy.babycare.R;

import java.util.List;
import java.util.Map;

/**
 * Created by snowy on 16/2/26.
 * ListView加载本地图片
 */
public class InfoListViewAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, Object>> list;
    public InfoListViewAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
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
        holder.img.setImageResource((Integer) list.get(position).get("imgId"));
        holder.title.setText((String)list.get(position).get("title"));
        holder.date.setText((String)list.get(position).get("date"));
        holder.talknum.setText((String)list.get(position).get("talknum"));
        holder.talk.setTag(position);

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
