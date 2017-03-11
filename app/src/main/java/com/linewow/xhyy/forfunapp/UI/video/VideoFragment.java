package com.linewow.xhyy.forfunapp.UI.video;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.adapter.VideoListAdapter;
import com.linewow.xhyy.forfunapp.app.AppConstant;
import com.linewow.xhyy.funlibrary.base.BaseFragment;
import com.linewow.xhyy.forfunapp.entity.VideoInfo;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by LXR on 2017/1/19.
 */

public class VideoFragment extends BaseFragment<VideoPresenter, VideoModel> implements VideoContract.View {
    @Bind(R.id.video_recycler)
    RecyclerView videoRecycler;
    @Bind(R.id.video_swipe)
    SwipeRefreshLayout videoSwipe;
    @Bind(R.id.channel_float_button)
    FloatingActionButton channelFloatButton;
    private VideoListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        videoRecycler.setLayoutManager(manager);
        videoRecycler.setItemAnimator(new DefaultItemAnimator());
        videoRecycler.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (JCVideoPlayerManager.getCurrentJcvdOnFirtFloor() != null) {
                    JCVideoPlayer player = (JCVideoPlayer) JCVideoPlayerManager.getCurrentJcvdOnFirtFloor();
                    if ((((ViewGroup) view).indexOfChild(player) != -1) && player.currentState == JCVideoPlayer.CURRENT_STATE_PLAYING) {
                        JCVideoPlayer.releaseAllVideos();
                    }
                }
            }
        });


    }

    @Override
    protected void initPresenter() {

        presenter.setVM(this, model);
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayerStandard.releaseAllVideos();
    }


    @Override
    public void refresh(List<VideoInfo> list) {
        videoSwipe.setRefreshing(false);
        adapter.setNewData(list);
    }

    @Override
    public void load(List<VideoInfo> list) {
        videoSwipe.setRefreshing(false);
        adapter.addData(list);
    }

    @Override
    public void err(String err) {

    }

    @Override
    public void start(List<VideoInfo> list) {

        adapter = new VideoListAdapter(list);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.load();
            }
        });
        videoRecycler.setAdapter(adapter);

        videoSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    @Override
    public void loadComplete() {
        adapter.loadComplete();
    }

    @Override
    public void scrollToTop() {
        videoRecycler.smoothScrollToPosition(0);
    }



    @OnClick(R.id.channel_float_button)
    public void onClick() {
        rxManager.post(AppConstant.NEWS_LIST_TO_TOP,"");
    }
}
