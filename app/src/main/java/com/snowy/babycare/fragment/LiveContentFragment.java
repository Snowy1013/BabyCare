package com.snowy.babycare.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.snowy.babycare.R;
import com.snowy.babycare.http.MyAsynctTask;

/**
 * Created by Snowy on 16/1/20.
 */
public class LiveContentFragment extends Fragment {

    private ImageView img_live;
    private EditText et_live;
    private ProgressBar progressbar_live;
    private Button btn_live;
    private String urlStr = "http://img3.dns4.cn/pic/101274/cuirushi/20150715161331_5752_zs_sy.jpg";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_content_live, container, false);

        img_live = (ImageView) view.findViewById(R.id.img_live);
        et_live = (EditText) view.findViewById(R.id.et_live);
        progressbar_live = (ProgressBar) view.findViewById(R.id.progressbar_live);
        btn_live = (Button) view.findViewById(R.id.btn_live);
        et_live.setText(urlStr);

        btn_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = et_live.getText().toString();
                MyAsynctTask asynctTask = new MyAsynctTask(getActivity(), img_live, progressbar_live);
                asynctTask.execute(url);
            }
        });

        return view;
    }
}