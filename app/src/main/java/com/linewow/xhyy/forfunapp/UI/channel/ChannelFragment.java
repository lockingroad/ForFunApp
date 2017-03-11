package com.linewow.xhyy.forfunapp.UI.channel;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.UI.newslist.NewsListFragment;
import com.linewow.xhyy.forfunapp.app.AppConstant;
import com.linewow.xhyy.funlibrary.base.BaseFragment;
import com.linewow.xhyy.forfunapp.base.BaseFragmentAdapter;
import com.linewow.xhyy.forfunapp.entity.other.ChannelEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LXR on 2017/1/17.
 */

public class ChannelFragment extends BaseFragment<ChannelPresenter,ChannelModel> implements ChannelContract.View {
    @Bind(R.id.channel_toolbar)
    Toolbar channelToolbar;
    @Bind(R.id.channel_tab)
    TabLayout channelTab;
    @Bind(R.id.channel_appbar)
    AppBarLayout channelAppbar;
    @Bind(R.id.channel_vp)
    ViewPager channelVp;
    @Bind(R.id.channel_float_button)
    FloatingActionButton channelFloatButton;

    private BaseFragmentAdapter adapter;
    private FragmentManager fm;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void initView() {
        fm=getFragmentManager();
    }

    @Override
    protected void initPresenter() {
        presenter.setVM(this,model);
    }

    @Override
    public void start(List<ChannelEntity> list) {
        List<String>titles=getTitles(list);
        List<Fragment>fragments=getFragments(list);
        adapter=new BaseFragmentAdapter(fm,fragments,titles);
        channelVp.setAdapter(adapter);
        channelTab.setupWithViewPager(channelVp);
    }

    private List<Fragment> getFragments(List<ChannelEntity> list) {
        List<Fragment>listFragments=new ArrayList<>();
        for(ChannelEntity entity:list){
            NewsListFragment newsListFragment=new NewsListFragment();
            Bundle bundle=new Bundle();
            bundle.putParcelable("info",entity);
            newsListFragment.setArguments(bundle);
            listFragments.add(newsListFragment);
        }
        return listFragments;
    }

    private List<String> getTitles(List<ChannelEntity> list) {
        List<String>titles=new ArrayList<>();
        for(ChannelEntity temp:list){
            titles.add(temp.getTitle());
        }
        return titles;
    }



    @OnClick(R.id.channel_float_button)
    public void onClick() {
        rxManager.post(AppConstant.NEWS_LIST_TO_TOP,"");
    }

}
