package com.custom.day3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhangli on 2019/2/28.
 */

public class PagerFragment extends Fragment {

    private View view;
    public static PagerFragment getInstance(String item) {
        PagerFragment pagerFragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("item",item);
        pagerFragment.setArguments(bundle);
        return pagerFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item,null);

        Bundle bundle = getArguments();
        TextView textView = (TextView) view.findViewById(R.id.fragment_tv);
        textView.setText(bundle.getString("item"));
        return view;
    }
}
