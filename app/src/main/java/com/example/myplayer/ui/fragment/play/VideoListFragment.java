package com.example.myplayer.ui.fragment.play;

import android.view.View;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.util.ViewUtils;

/**
 * Created by Administrator on 2016/3/31.
 */
public class VideoListFragment extends BaseFragment{

    @Override
    protected View initView() {
        return ViewUtils.inflateView(R.layout.fragment_video);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
//        RecyclerView listView = mRootView.findViewById()
    }

    @Override
    protected void processClick(View view) {

    }
}
