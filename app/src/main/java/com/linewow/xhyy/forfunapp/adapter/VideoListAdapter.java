package com.linewow.xhyy.forfunapp.adapter;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.entity.VideoInfo;
import com.squareup.picasso.Picasso;
import java.util.List;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by LXR on 2017/1/25.
 */

public class VideoListAdapter extends BaseQuickAdapter<VideoInfo>{
    private Context context;

    public VideoListAdapter(List<VideoInfo> data) {
        super(R.layout.item_video,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, VideoInfo videoInfo) {
        if(context==null){
            context=baseViewHolder.getConvertView().getContext();
        }
        JCVideoPlayerStandard jc=baseViewHolder.getView(R.id.video_jc);
        jc.setUp(videoInfo.videourl, JCVideoPlayer.SCREEN_LAYOUT_LIST,videoInfo.videoname);
        Picasso.with(context).load(videoInfo.videopic).fit().centerCrop().into(jc.thumbImageView);
    }
}
