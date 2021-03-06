package com.example.myplayer.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.activity.second.DateRiJiMemoEditActivity;
import com.example.myplayer.db.Memo;
import com.example.myplayer.db.MemoDao;
import com.example.myplayer.util.CommonUtil;
import com.example.myplayer.util.DateUtils;
import com.example.myplayer.util.FormatHelper;
import com.example.myplayer.util.RxBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import cn.aigestudio.datepicker.views.MonthView;
import rx.Observable;
import rx.functions.Action1;

import static com.example.myplayer.util.APPUtil.getDaoSession;
import static com.example.myplayer.util.FileUtil.backDB;
import static com.example.myplayer.util.FileUtil.copyDB;

/**
 * Created by Administrator on 2016/3/31.
 * Three
 */
public class MeditationFragment extends BaseFragment {

    private DatePicker picker;
    private MonthView monthView;
    private TextView tvContent;
    private Memo currentMemo;
    private HashMap<String, Memo> daoHashMap = new HashMap<>();
    private MemoDao userDao = getDaoSession().getMemoDao();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meditation;
    }

    @Override
    protected void initWidget() {
        picker = (DatePicker) mRootView.findViewById(R.id.main_dp);
        monthView = CommonUtil.getField(picker, "monthView", MonthView.class);
        tvContent = (TextView) mRootView.findViewById(R.id.tv_content);
        tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    protected void initData() {
        initPicker();
        registEditMemo();
        //点击还原备份
        mRootView.findViewById(R.id.btn_copt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyDB();
            }
        });
        //点击回到今日
        mRootView.findViewById(R.id.btn_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.setDate(DateUtils.getNowYear(), DateUtils.getNowMonth());
            }
        });
        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DateRiJiMemoEditActivity.class);
                intent.putExtra(DateRiJiMemoEditActivity.MODEL, currentMemo);
                startActivity(intent);
            }
        });
    }

    private void registEditMemo() {
        RxBus.getInstance().toObservable(Memo.class, "Memo_Save").subscribe(new Action1<Memo>() {
            @Override
            public void call(Memo memo) {
                String day = memo.addyear + "-" + memo.addmonth + "-" + memo.addday;
                daoHashMap.put(day, memo);
                DPCManager.getInstance().setDecorTL(new ArrayList<String>(daoHashMap.keySet()));
                setContentText(day);
                backDB();
            }
        });
    }

    private void initPicker() {
        picker.setDate(DateUtils.getNowYear(), DateUtils.getNowMonth());
        //获取当前时间的数据
        queryCurrentData(DateUtils.getNowYear(), DateUtils.getNowMonth());
        currentMemo = daoHashMap.get(DateUtils.formatSystemDate());
        //去掉一些不必要的显示内容
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);
        //单选模式和标识绘制
        picker.setMode(DPMode.SINGLE);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTL(canvas, rect, paint, data);
                paint.setColor(Color.RED);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 4, paint);
            }
        });

        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                setContentText(date);
            }
        });
        picker.setOnDateScrollChangeListener(new MonthView.OnDateScrollChangeListener() {
            @Override
            public void onDataCurrent(int year, int month) {
                queryCurrentData(year, month);
                monthView.postInvalidate();
            }
        });
        setContentText(DateUtils.getNowYear() + "-" + DateUtils.getNowMonth() + "-" + DateUtils.getNowDay());
    }

    private void setContentText(String date) {
        currentMemo = daoHashMap.get(date);
        if (currentMemo == null) {
            currentMemo = new Memo();
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtils.transform(date, DateUtils.yyyyMMDD));
            currentMemo.addmonth = c.get(Calendar.MONTH) + 1;
            currentMemo.addday = c.get(Calendar.DAY_OF_MONTH);
            currentMemo.addyear = c.get(Calendar.YEAR);
            currentMemo.adddate = currentMemo.addyear + "-" + FormatHelper.formatIntTwo(currentMemo.addmonth) + "-" + FormatHelper.formatIntTwo(currentMemo.addday);
        }

        tvContent.setText(TextUtils.isEmpty(currentMemo.content) ? "点击填写" : currentMemo.content);
    }

    //数据库查询数据
    private void queryCurrentData(int year, int month) {
        daoHashMap.clear();
        String likeStr = year + "-" + FormatHelper.formatIntTwo(month) + "%";
        List<Memo> list = userDao.queryBuilder().where(MemoDao.Properties.Adddate.like(likeStr)).list();
        Observable.from(list)
                .subscribe(new Action1<Memo>() {
                    @Override
                    public void call(Memo memo) {
                        daoHashMap.put(memo.addyear + "-" + memo.addmonth + "-" + memo.addday, memo);
                    }
                });

        DPCManager.getInstance().setDecorTL(new ArrayList<String>(daoHashMap.keySet()));
    }
}
