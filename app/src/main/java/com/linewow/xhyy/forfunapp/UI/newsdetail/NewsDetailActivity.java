package com.linewow.xhyy.forfunapp.UI.newsdetail;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.funlibrary.base.BaseActivity;
import com.linewow.xhyy.forfunapp.entity.NewsDetailEntity;
import com.linewow.xhyy.forfunapp.view.URLImageGetter;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by LXR on 2017/2/5.
 * 新闻详情活动
 */

public class NewsDetailActivity extends BaseActivity<NewsDetailPresenter,NewsDetailModel>implements NewsDetailContract.View {
    @Bind(R.id.detail_img)
    ImageView detailImg;
    @Bind(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @Bind(R.id.detail_content)
    TextView detailContent;
    @Bind(R.id.detail_prg)
    ProgressBar detailPrg;
    private String postID,imgURL;
    private URLImageGetter imageGetter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initPresenter() {
        presenter.setPostId(postID);
        presenter.setVM(this,model);
    }

    @Override
    protected void initView() {
        Intent intent=getIntent();
        postID=intent.getStringExtra("postID");
        imgURL=intent.getStringExtra("imgURL");
        Picasso.with(context).load(imgURL).fit().centerCrop().into(detailImg);

    }

    public static void startDetail(Context context,String postID,String imgURL){
        Intent intent=new Intent(context,NewsDetailActivity.class);
        intent.putExtra("postID",postID);
        intent.putExtra("imgURL",imgURL);
        context.startActivity(intent);
    }




    @Override
    public void showNews(NewsDetailEntity newsDetailEntity) {
        detailToolbar.setTitle(newsDetailEntity.title);
        detailToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        showContent(newsDetailEntity);
        detailPrg.setVisibility(View.GONE);
    }



    private void showContent(NewsDetailEntity newsDetailEntity) {
        int size=newsDetailEntity.getImg().size();
        String body=newsDetailEntity.getBody();
        if(size>1){
            imageGetter=new URLImageGetter(detailContent,body,size);
            detailContent.setText(Html.fromHtml(body,imageGetter,null));
        }else{
            detailContent.setText(Html.fromHtml(body));
        }
    }

}
