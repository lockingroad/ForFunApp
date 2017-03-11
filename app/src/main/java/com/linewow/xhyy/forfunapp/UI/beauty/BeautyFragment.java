package com.linewow.xhyy.forfunapp.UI.beauty;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.app.AppConstant;
import com.linewow.xhyy.funlibrary.base.BaseFragment;
import com.linewow.xhyy.forfunapp.entity.BeautyInfo;
import com.linewow.xhyy.funlibrary.activity.BeautyPhotoActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LXR on 2017/1/19.
 */

public class BeautyFragment extends BaseFragment<BeautyPresenter,BeautyModel> implements BeautyContract.View {
    @Bind(R.id.beauty_recycler)
    RecyclerView beautyRecycler;
    @Bind(R.id.beauty_swipe)
    SwipeRefreshLayout beautySwipe;
    private String TAG = "BeautyFragment";
    private BeautyAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        beautyRecycler.setLayoutManager(manager);
        beautyRecycler.setItemAnimator(new DefaultItemAnimator());
    }


    @OnClick(R.id.channel_float_button)
    public void onClick() {
        rxManager.post(AppConstant.NEWS_LIST_TO_TOP,"");
    }

    @Override
    protected void initPresenter() {
        presenter.setVM(this,model);
    }

    @Override
    public void refresh(List<BeautyInfo> list) {
        adapter.setNewData(list);
        beautySwipe.setRefreshing(false);
    }

    @Override
    public void load(List<BeautyInfo> list) {
        adapter.addData(list);
    }

    @Override
    public void err(String err) {

    }

    @Override
    public void start(List<BeautyInfo> list) {
        adapter=new BeautyAdapter(list);
        beautyRecycler.setAdapter(adapter);
        beautySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                presenter.load();
            }
        });
        beautyRecycler.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                BeautyInfo info=adapter.getItem(i);
                ImageView imageView= (ImageView) view.findViewById(R.id.beauty_img);
                BeautyPhotoActivity.startBeautyPhotot(context,imageView,info.url);
            }
        });
    }

    @Override
    public void loadComplete() {
        adapter.loadComplete();
    }

    @Override
    public void scrollToTop() {
        beautyRecycler.smoothScrollToPosition(0);
    }

}
