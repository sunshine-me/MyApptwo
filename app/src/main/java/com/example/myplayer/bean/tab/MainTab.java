package com.example.myplayer.bean.tab;

import com.example.myplayer.R;
import com.example.myplayer.fragment.BodhiFragment;
import com.example.myplayer.fragment.MeditationFragment;
import com.example.myplayer.fragment.NirvanaFragment;
import com.example.myplayer.fragment.SixVirtuesFragment;


public enum MainTab {

	/*
    NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
			NewsViewPagerFragment.class),
			*/

    NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
            BodhiFragment.class),

    TWEET(1, R.string.main_tab_name_tweet, R.drawable.tab_icon_tweet,
            NirvanaFragment.class),

    QUICK(2, R.string.main_tab_name_quick, R.drawable.tab_icon_new,
            null),

    EXPLORE(3, R.string.main_tab_name_explore, R.drawable.tab_icon_explore,
            MeditationFragment.class),

    ME(4, R.string.main_tab_name_my, R.drawable.tab_icon_me,
            SixVirtuesFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
