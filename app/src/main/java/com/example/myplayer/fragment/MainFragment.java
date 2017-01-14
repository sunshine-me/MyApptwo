package com.example.myplayer.fragment;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.myplayer.R;
import com.example.myplayer.adapter.baseadapter.MyMainFragmentPagerAdapter;
import com.example.myplayer.widget.LazyViewPager;
import com.example.myplayer.widget.NoScrollViewPager;

/**
 * Created by zhangdongsheng on 16/8/11.
 * RadioGroup 低栏的主页
 */
public class MainFragment extends BaseFragment {


    int mCurrent;
    Fragment[] fragments = new BaseFragment[]{new BodhiFragment(), new NirvanaFragment(), new MeditationFragment(), new SixVirtuesFragment()};
    private RadioGroup rgGroup;
    private NoScrollViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    private void setMargin(View view) {
        initButton((RadioButton) view);
        //Button 压边是 放大的效果
//        ViewHelper.setScaleX(view, 1.2f);
//        ViewHelper.setScaleY(view, 1.2f);
    }

    public void initButton(RadioButton radioBtns) {
        Drawable[] drawables = radioBtns.getCompoundDrawables();//通过RadioButton的getCompoundDrawables()方法，拿到图片的drawables,分别是左上右下的图片
        drawables[1].setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen._16dp),
                getResources().getDimensionPixelSize(R.dimen._16dp));

        radioBtns.setCompoundDrawables(drawables[0], drawables[1], drawables[2],
                drawables[3]);//将改变了属性的drawable再重新设置回去
    }

    @Override
    protected void initWidget() {
        rgGroup = (RadioGroup) mRootView.findViewById(R.id.rg_group);
        mViewPager = (NoScrollViewPager) mRootView.findViewById(R.id.vp_content);
        setMargin(rgGroup.findViewById(R.id.rb_home));
        setMargin(rgGroup.findViewById(R.id.rb_news));
        setMargin(rgGroup.findViewById(R.id.rb_smart));
        setMargin(rgGroup.findViewById(R.id.rb_gov));
        setMargin(rgGroup.findViewById(R.id.rb_setting));
        // 监听RadioGroup的选择事件
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        // mViewPager.setCurrentItem(0);// 设置当前页面
                        mViewPager.setCurrentItem(0, true);// false 去掉切换页面的动画
                        break;
                    case R.id.rb_news:
                        mViewPager.setCurrentItem(1, true);// 设置当前页面
                        break;
                    case R.id.rb_smart:
                        mViewPager.setCurrentItem(2, true);// 设置当前页面
                        break;
                    case R.id.rb_gov:
                        mViewPager.setCurrentItem(3, true);// 设置当前页面
                        break;
                    case R.id.rb_setting:
                        mViewPager.setCurrentItem(4, true);// 设置当前页面
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

    private void setBottomMark(int i) {
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

    private class MyFragmentPagerAdapter extends MyMainFragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
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
