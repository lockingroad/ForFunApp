package com.linewow.xhyy.forfunapp;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.linewow.xhyy.forfunapp.UI.beauty.BeautyFragment;
import com.linewow.xhyy.forfunapp.UI.care.CareFragment;
import com.linewow.xhyy.forfunapp.UI.channel.ChannelFragment;
import com.linewow.xhyy.forfunapp.UI.video.VideoFragment;
import com.linewow.xhyy.forfunapp.app.AppConstant;
import com.linewow.xhyy.funlibrary.base.BaseCompatActivity;
import com.linewow.xhyy.forfunapp.entity.TabEntity;
import com.linewow.xhyy.forfunapp.view.CommonTabLayout;
import com.linewow.xhyy.forfunapp.view.CustomTabEntity;
import com.linewow.xhyy.forfunapp.view.OnTabSelectListener;
import com.linewow.xhyy.funlibrary.util.CommonUtil;
import com.linewow.xhyy.funlibrary.util.StatusBarUtil;

import java.util.ArrayList;

import butterknife.Bind;
import etong.bottomnavigation.lib.BottomNavigationBar;
import rx.functions.Action1;

public class FrameActivity extends BaseCompatActivity {

    @Bind(R.id.frame_main)
    FrameLayout frameMain;
    @Bind(R.id.frame_navigation)
    BottomNavigationBar frameNavigation;
    private int hei;

    private String TAG="FrameActivity";
    private ChannelFragment channelFragment;
    private BeautyFragment beautyFragment;
    private VideoFragment videoFragment;
    private CareFragment careFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_frame;
    }

    @Override
    protected void initPresenter() {

    }
    @Override
    protected void initView() {
        initData();
        StatusBarUtil.StatusBarLightMode(this);
        rxManager.on(AppConstant.MENU_SHOW_HIDE, new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                startAnim(aBoolean);
            }
        });
    }



    private void initData() {

        frameNavigation.addTab(R.mipmap.icon_news, "News", 0xff553b36);
        frameNavigation.addTab(R.mipmap.icon_beauty, "Beauty", 0xff8a6a64);
        frameNavigation.addTab(R.mipmap.icon_movie, "Movies & Tv", 0xff4a5965);
        frameNavigation.addTab(R.mipmap.icon_circle, "Circle", 0xff096c54);
        frameNavigation.setOnTabListener(new BottomNavigationBar.TabListener() {
            @Override
            public void onSelected(int i) {
                Log.e(TAG,"选中了"+i);
                SwitchTo(i);
            }
        });
        frameNavigation.setSelected(0);
//        frameNavigation.measure(0,1);
        hei= CommonUtil.dp2px(context,50);
    }


    public void startAnim(boolean startFlag){
        final ViewGroup.LayoutParams params=frameNavigation.getLayoutParams();
        ValueAnimator valueAnimator;
        ObjectAnimator objectAnimator;
        if(startFlag){
             valueAnimator=ValueAnimator.ofInt(0,hei);
             objectAnimator=ObjectAnimator.ofInt(frameNavigation,"alpha",0,1);
        }else{
             valueAnimator=ValueAnimator.ofInt(hei,0);
             objectAnimator=ObjectAnimator.ofInt(frameNavigation,"alpha",1,0);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.height= (int) valueAnimator.getAnimatedValue();
                frameNavigation.setLayoutParams(params);
            }
        });
        AnimatorSet set=new AnimatorSet();
        set.playTogether(valueAnimator,objectAnimator);
        set.setDuration(300);
        set.start();
    }


    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            channelFragment = (ChannelFragment) getSupportFragmentManager().findFragmentByTag("channelFragment");
            beautyFragment = (BeautyFragment) getSupportFragmentManager().findFragmentByTag("beautyFragment");
            videoFragment = (VideoFragment) getSupportFragmentManager().findFragmentByTag("videoFragment");
            careFragment = (CareFragment) getSupportFragmentManager().findFragmentByTag("careFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            channelFragment = new ChannelFragment();
            beautyFragment = new BeautyFragment();
            videoFragment = new VideoFragment();
            careFragment = new CareFragment();

            transaction.add(R.id.frame_main, channelFragment, "channelFragment");
            transaction.add(R.id.frame_main, beautyFragment, "beautyFragment");
            transaction.add(R.id.frame_main, videoFragment, "videoFragment");
            transaction.add(R.id.frame_main, careFragment, "careFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        frameNavigation.setSelected(currentTabPosition);
    }


    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(beautyFragment);
                transaction.hide(videoFragment);
                transaction.hide(careFragment);
                transaction.show(channelFragment);
                transaction.commitAllowingStateLoss();
                break;
            //美女
            case 1:
                transaction.hide(channelFragment);
                transaction.hide(videoFragment);
                transaction.hide(careFragment);
                transaction.show(beautyFragment);
                transaction.commitAllowingStateLoss();
                break;
            //视频
            case 2:
                transaction.hide(beautyFragment);
                transaction.hide(channelFragment);
                transaction.hide(careFragment);
                transaction.show(videoFragment);
                transaction.commitAllowingStateLoss();
                break;
            //关注
            case 3:
                transaction.hide(channelFragment);
                transaction.hide(beautyFragment);
                transaction.hide(videoFragment);
                transaction.show(careFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        if (frameNavigation != null) {
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, 0);
        }
    }

}
