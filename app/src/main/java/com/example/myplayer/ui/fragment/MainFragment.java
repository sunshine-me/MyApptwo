package com.example.myplayer.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.myplayer.R;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.ui.view.LazyViewPager;
import com.example.myplayer.ui.view.NoScrollViewPager;
import com.example.myplayer.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangdongsheng on 16/8/11.
 */
public class MainFragment extends BaseFragment {


    private RadioGroup rgGroup;


    private NoScrollViewPager mViewPager;

    int mCurrent;
    private final Fragment[] Factory = {new HomeFragment(), new NewsFragment(),
            new GoyFragment(), new OtherFragment(),
           new SetingFragment()};

    @Override
    protected View initView() {
        View view = ViewUtils.inflateView(R.layout.fragment_content);
        rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
        mViewPager = (NoScrollViewPager) view.findViewById(R.id.vp_content);
        return view;
    }

    @Override
    protected void initListener() {


        // 监听RadioGroup的选择事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        // mViewPager.setCurrentItem(0);// 设置当前页面
                        mViewPager.setCurrentItem(0, false);// 去掉切换页面的动画
                        break;
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(1, false);// 设置当前页面
                        break;
                    case R.id.rb_smart:
                        mViewPager.setCurrentItem(2, false);// 设置当前页面
                        break;
                    case R.id.rb_gov:
                        mViewPager.setCurrentItem(3, false);// 设置当前页面
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4, false);// 设置当前页面
                        break;

                    default:
                        break;
                }
            }
        });


        FragmentManager fm = getChildFragmentManager();
        MyFragmentPagerAdapter mAdapter = new MyFragmentPagerAdapter(fm);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new MyHomePageChangeListener());
        mViewPager.setCurrentItem(mCurrent);
        mViewPager.setOnRestoreListener(new NoScrollViewPager.RestoreListener() {
            @Override
            public void onResotreFinish() {
                mViewPager.setCurrentItem(mCurrent);
                setBottomMark(mCurrent);
            }
        });
    }

    @Override
    protected void initData() {
        rgGroup.check(R.id.rb_home);// 默认勾选首页
    }

    private void setBottomMark(int i){
        switch (i) {
            case 0:
                rgGroup.check(R.id.rb_home);
                break;
            case 1:
                rgGroup.check(R.id.rb_news);
                break;
            case 2:
                rgGroup.check(R.id.rb_smart);
                break;
            case 3:
                rgGroup.check(R.id.rb_gov);
                break;
            case 4:
                rgGroup.check(R.id.rb_setting);
                break;
        }

    }

    @Override
    protected void processClick(View view) {

    }
    public class MyHomePageChangeListener implements LazyViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int position) {
            mCurrent = position;
        }
    }
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return Factory.length;
        }

        @Override
        public Fragment getItem(int position) {
            return Factory[position];
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }
}
