package com.example.myplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myplayer.R;
import com.example.myplayer.activity.VideoPlayerActivity;
import com.example.myplayer.activity.VitamioPlayActivity;
import com.example.myplayer.base.BaseActivity;
import com.example.myplayer.base.BaseFragment;
import com.example.myplayer.bean.VideoItem;
import com.example.myplayer.util.DateUtil;
import com.example.myplayer.util.ViewUtils;

import java.util.ArrayList;

/*
         佛祖保佑       永无BUG
---------------------------------------------
          视频列表适配器
          别人做的支持Cursor的适配器
          @author ZDS
          create on 2016/4/2  18:32 */

public class VideoListAdapter extends BasePlayAdapter<VideoListAdapter.MyViewHolder, VideoItem> {

    public VideoListAdapter(Context context, Cursor c, BaseFragment baseFragment) {
        super(context, c, baseFragment);
    }

    //RecyclerView 的特殊方法直接绑定holder
    @Override
    public void onBindViewHolder(MyViewHolder holder, final Cursor cursor, final int position) {
        VideoItem videoItem = VideoItem.fromCursor(cursor);
        holder.tvTitle.setText(videoItem.getTitle());
        holder.tvDuration.setText(DateUtil.formatVideoDuration(videoItem.getDuration()));
        holder.tvSize.setText(Formatter.formatFileSize(mContext, videoItem.getSize()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.getActivity(), VitamioPlayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(VideoPlayerActivity.POSITION, position);
                bundle.putSerializable(VideoPlayerActivity.VIDEOLIST, cursorToList(getCursor()));
                intent.putExtras(bundle);
                mBaseFragment.startActivity(intent);
            }
        });
    }

    /**
     * 将cursor中的数据取出来放入集合当中
     *
     * @param cursor
     * @return
     */
    @Override
    protected ArrayList<VideoItem> cursorToList(Cursor cursor) {
        ArrayList<VideoItem> list = new ArrayList<VideoItem>();
        cursor.moveToPosition(-1);
        while (cursor.moveToNext()) {
            list.add(VideoItem.fromCursor(cursor));
        }
        return list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = ViewUtils.inflateView(R.layout.adapter_void_item);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvDuration;
        private TextView tvSize;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
        }
    }
}
