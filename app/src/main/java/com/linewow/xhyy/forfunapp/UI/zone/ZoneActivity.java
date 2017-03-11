package com.linewow.xhyy.forfunapp.UI.zone;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.adapter.ZoneAdapter;
import com.linewow.xhyy.funlibrary.base.BaseActivity;
import com.linewow.xhyy.forfunapp.entity.CircleItem;
import com.linewow.xhyy.forfunapp.entity.CommentConfig;
import com.linewow.xhyy.forfunapp.entity.CommentItem;
import com.linewow.xhyy.forfunapp.entity.FavortItem;
import com.linewow.xhyy.forfunapp.entity.PageBean;
import com.linewow.xhyy.forfunapp.util.KeyBordUtil;
import com.linewow.xhyy.forfunapp.view.CommentListView;
import com.linewow.xhyy.forfunapp.view.LoadMoreFooterView;
import com.linewow.xhyy.funlibrary.util.CommonUtil;
import com.linewow.xhyy.funlibrary.util.StatusBarUtil;

import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LXR on 2017/3/6.
 */

public class ZoneActivity extends BaseActivity<ZonePresenter, ZoneModel> implements ZoneContract.View {
    @Bind(R.id.zone_recycler)
    IRecyclerView zoneRecycler;
    @Bind(R.id.zone_edit)
    EditText zoneEdit;
    @Bind(R.id.zone_send)
    ImageView zoneSend;
    @Bind(R.id.zone_edit_line)
    LinearLayout zoneEditLine;
    private String TAG = "ZoneActivity";
    private ZoneAdapter zoneAdapter;

    private CommentConfig config;
    private LinearLayoutManager manager;
    private int mSelectCircleItemH;
    private int mSelectCommentItemOffset;
    private int mCurrentKeyboardH;
    private int mScreenHeight;

    private int mEditTextBodyHeight;
    private LoadMoreFooterView loadMoreFooterView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zone;
    }

    @Override
    protected void initPresenter() {
        presenter.setVM(ZoneActivity.this, model);
        loadData();
    }

    private void loadData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getData();
            }
        }, 1000);
    }

    @Override
    protected void initView() {
        manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        zoneRecycler.setLayoutManager(manager);
        zoneAdapter = new ZoneAdapter(context, presenter);
        zoneRecycler.setIAdapter(zoneAdapter);
        View headView=View.inflate(context,R.layout.item_zone_header,null);
        zoneRecycler.addHeaderView(headView);
        loadMoreFooterView= (LoadMoreFooterView) zoneRecycler.getLoadMoreFooterView();
        zoneRecycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(zoneEditLine.getVisibility()==View.VISIBLE){
                    updateEdit(View.GONE,null);
                }
                return false;
            }
        });

        zoneRecycler.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                zoneAdapter.getPageBean().setRefresh(true);
                loadData();
            }
        });

        zoneRecycler.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                loadData();
            }
        });

        setViewTreeObserver();
        StatusBarUtil.StatusBarLightMode(ZoneActivity.this);

    }

    @Override
    public void getData(List<CircleItem> circleItems, PageBean pageBean) {
        Log.e(TAG, "得到数据的大小" + circleItems.size());
        if(zoneAdapter.getPageBean().isRefresh()){
            zoneRecycler.setRefreshing(false);
            zoneAdapter.getPageBean().setRefresh(false);
            zoneAdapter.reset(circleItems);
        }else{
            zoneAdapter.addAll(circleItems);
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        }


    }

    @Override
    public void unLike(int position, String userId) {
        Log.e(TAG, "打印userid" + userId);
        CircleItem circleItem = zoneAdapter.get(position);
        List<FavortItem> items = circleItem.getGoodjobs();
        for (int i = 0; i < items.size(); i++) {
            Log.e(TAG, "alluserid" + items.get(i).getUserId());
            if (items.get(i).getUserId().equals(userId)) {
                Log.e(TAG, "remove掉了");
                items.remove(i);
            }
        }
        zoneAdapter.notifyItemChanged(position);
    }

    @Override
    public void like(int position, FavortItem item) {
        Log.e(TAG, "加入的userid" + item.getUserId());
        zoneAdapter.getData().get(position).getGoodjobs().add(item);
        zoneAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateEdit(int visible, CommentConfig config) {
        zoneEditLine.setVisibility(visible);
        this.config=config;

        measureOffset(config);

        if(config!=null&&CommentConfig.Type.REPLY.equals(config.getCommentType())){
            zoneEdit.setHint("回复"+config.getName()+":");
        }else{
            zoneEdit.setHint("说点什么吧");
        }
        if(View.VISIBLE==visible){
            zoneEdit.requestFocus();
            KeyBordUtil.showSoftKeyboard(zoneEdit);
        }
        if(View.GONE==visible){
            KeyBordUtil.hideSoftKeyboard(zoneEdit);
        }
    }



    private void measureOffset(CommentConfig config) {
        if(config==null){
            return;
        }
        int headViewCount=zoneRecycler.getHeaderContainer().getChildCount();
        Log.e(TAG,"headViewCount"+headViewCount);
        int selectPosition=config.circlePosition+headViewCount+1;
        Log.e(TAG,"selectPosition"+selectPosition);
        View selectCircleItem=manager.findViewByPosition(selectPosition);
        if(selectCircleItem!=null){
            mSelectCircleItemH=selectCircleItem.getHeight()- CommonUtil.dp2px(context,48);
            Log.e(TAG,"mSelectCircleItemH"+mSelectCircleItemH);
            if(config.commentType== CommentConfig.Type.REPLY){
                Log.e(TAG,"reply回复的comment");
                CommentListView commentListView= (CommentListView) selectCircleItem.findViewById(R.id.zone_comment);
                if(commentListView!=null){
                    View selectCommentItem=commentListView.getChildAt(config.commentPosition);
                    if(selectCommentItem!=null){
                        mSelectCommentItemOffset=0;
                        View parentView=selectCommentItem;
                        do{
                            int subItemBottom=parentView.getBottom();
                            Log.e(TAG,"subItemBottom--->"+subItemBottom);
                            parentView= (View) parentView.getParent();
                            if(parentView!=null){
                                mSelectCommentItemOffset+=(parentView.getHeight()-subItemBottom);
                            }
                        } while (parentView!=null);
                    }

                }
            }
        }
    }

    @Override
    public void addComment(int position, CommentItem commentItem) {
        zoneAdapter.getData().get(position).getReplys().add(commentItem);
        zoneAdapter.notifyItemChanged(position);
    }

    @OnClick(R.id.zone_send)
    public void onClick() {
        String content=zoneEdit.getText().toString();
        presenter.addComment(content,config);
        updateEdit(View.GONE,null);
    }


    private void setViewTreeObserver(){
        ViewTreeObserver treeObserver=zoneRecycler.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect=new Rect();
                zoneRecycler.getWindowVisibleDisplayFrame(rect);
                int statusBarH=getStatusBarHeight();
                int screenH=zoneRecycler.getRootView().getHeight();

                if(rect.top!=statusBarH){
                    rect.top=statusBarH;
                }

                int keyboardH=screenH-(rect.bottom-rect.top);
                if(keyboardH==mCurrentKeyboardH){
                    return;
                }
                mCurrentKeyboardH=keyboardH;
                mScreenHeight=screenH;


                mEditTextBodyHeight=zoneEditLine.getHeight();
                if(zoneRecycler!=null&&config!=null){
                    int index=config.circlePosition+zoneRecycler.getHeaderContainer().getChildCount()+1;
                    Log.e(TAG,"头部的数目"+zoneRecycler.getHeaderContainer().getChildCount()+"---"+index);
                    manager.scrollToPositionWithOffset(index,getListviewOffset(config));
                }
            }
        });
    }


    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getListviewOffset(CommentConfig config){
        if(config==null){
            return 0;
        }
        Log.e(TAG,"screenHei"+mScreenHeight);
        WindowManager wm= (WindowManager) getSystemService(WINDOW_SERVICE);
        int hei=wm.getDefaultDisplay().getHeight();
        Log.e(TAG,"屏幕的高度"+hei);
        int listviewOffset=mScreenHeight-mSelectCircleItemH-mCurrentKeyboardH-mEditTextBodyHeight;
        if(config.commentType== CommentConfig.Type.REPLY){
            listviewOffset=listviewOffset+mSelectCommentItemOffset;
        }
        return listviewOffset;

    }

}
