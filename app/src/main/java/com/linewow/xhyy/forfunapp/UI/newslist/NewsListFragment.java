package com.linewow.xhyy.forfunapp.UI.newslist;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.UI.newsdetail.NewsDetailActivity;
import com.linewow.xhyy.forfunapp.adapter.NewsListAdapter;
import com.linewow.xhyy.funlibrary.base.BaseFragment;
import com.linewow.xhyy.forfunapp.entity.NewsSummary;
import com.linewow.xhyy.forfunapp.entity.other.ChannelEntity;

import java.util.List;

import butterknife.Bind;

/**
 * Created by LXR on 2017/1/17.
 */

public class NewsListFragment extends BaseFragment<NewsListPresenter,NewsListModel>implements NewsListContract.View {
    @Bind(R.id.newslist_recycler)
    RecyclerView newslistRecycler;
    @Bind(R.id.newslist_swipe)
    SwipeRefreshLayout newslistSwipe;

    private NewsListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newslist;
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(NewsListFragment.this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        newslistRecycler.setLayoutManager(manager);
        newslistRecycler.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void initPresenter() {
        ChannelEntity entity=getArguments().getParcelable("info");
        presenter.setEntity(entity);
        presenter.setVM(this,model);
    }


    @Override
    public void start(List<NewsSummary> list) {
        adapter=new NewsListAdapter(list);
        newslistRecycler.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.load();
            }
        });
        newslistRecycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                NewsSummary newsSummary=adapter.getItem(i);
                NewsDetailActivity.startDetail(NewsListFragment.this.getActivity(),newsSummary.getPostid(),newsSummary.getImgsrc());
            }
        });
    }

    @Override
    public void refresh(List<NewsSummary> list) {
        newslistSwipe.setRefreshing(false);
        adapter.setNewData(list);
    }

    @Override
    public void load(List<NewsSummary> list) {
        adapter.addData(list);
    }

    @Override
    public void loadComplete() {
        adapter.loadComplete();
    }


    @Override
    public void scrollToTop() {
        newslistRecycler.smoothScrollToPosition(0);
    }
}
