package com.example.myplayer.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.myplayer.R;
import com.example.myplayer.util.ViewUtils;

public class PlayService extends Service {

    private WindowManager wm;
    private View mView;

    private int mScreenWidth;
    private int mScreenHeight;

    private int startX;
    private int startY;
    private WindowManager.LayoutParams mParams;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        mView = ViewUtils.inflateView(R.layout.service_video_play);
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        // 修改完左上角对其
        mParams.gravity = Gravity.LEFT + Gravity.TOP;
        mParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mParams.format = PixelFormat.TRANSPARENT;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("asldfjalsdjfl");
            }
        });


        wm.addView(mView, mParams);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mParams.y = msg.arg1;
            wm.updateViewLayout(mView, mParams);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wm != null && mView != null) {
            wm.removeView(mView);// 从窗口移除布局
        }
    }
}