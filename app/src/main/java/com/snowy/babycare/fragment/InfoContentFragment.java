package com.snowy.babycare.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snowy.babycare.R;

/**
 * Created by Snowy on 16/1/20.
 */
public class InfoContentFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_content_info, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
