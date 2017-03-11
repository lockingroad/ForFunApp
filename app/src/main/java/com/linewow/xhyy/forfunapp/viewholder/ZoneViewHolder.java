package com.linewow.xhyy.forfunapp.viewholder;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.UI.zone.ZonePresenter;
import com.linewow.xhyy.forfunapp.adapter.CommentAdapter;
import com.linewow.xhyy.forfunapp.adapter.FavortListAdapter;
import com.linewow.xhyy.forfunapp.app.AppCache;
import com.linewow.xhyy.forfunapp.entity.CircleItem;
import com.linewow.xhyy.forfunapp.entity.CommentConfig;
import com.linewow.xhyy.forfunapp.entity.CommentItem;
import com.linewow.xhyy.forfunapp.entity.FavortItem;
import com.linewow.xhyy.forfunapp.spannable.ISpanClick;
import com.linewow.xhyy.forfunapp.util.DatasUtil;
import com.linewow.xhyy.forfunapp.util.ImageLoaderUtils;
import com.linewow.xhyy.forfunapp.view.CommentListView;
import com.linewow.xhyy.forfunapp.view.FavortListView;
import com.linewow.xhyy.forfunapp.view.MultiImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by LXR on 2017/3/6.
 */
public class ZoneViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.zone_avatar)
    ImageView zoneAvatar;
    @Bind(R.id.zone_nick)
    TextView zoneNick;
    @Bind(R.id.zone_del)
    ImageView zoneDel;
    @Bind(R.id.zone_url)
    TextView zoneUrl;
    @Bind(R.id.zone_view_stub)
    ViewStub zoneViewStub;
    @Bind(R.id.zone_distance)
    TextView zoneDistance;
    @Bind(R.id.zone_like)
    TextView zoneLike;
    @Bind(R.id.zone_reply)
    TextView zoneReply;
    @Bind(R.id.zone_dig)
    View zoneDig;
    @Bind(R.id.zone_send_tv)
    TextView zoneSendTv;
    @Bind(R.id.zone_send_img)
    ImageView zoneSendImg;
    @Bind(R.id.zone_send_line)
    LinearLayout zoneSendLine;
    @Bind(R.id.zone_bottom)
    LinearLayout zoneBottom;
    @Bind(R.id.zone_like_tv)
    FavortListView zoneLikeTv;
    @Bind(R.id.zone_comment)
    CommentListView zoneComment;

    private int type;
    private ZonePresenter presenter;
    private Context context;
    private CircleItem circleItem;
    private int position;
    private View itemView;
    private ImageView urlImg;
    private TextView urlTv;
    private MultiImageView multiImg;


    private FavortListAdapter favortAdapter;
    private CommentAdapter commentAdapter;

    private String TAG="ZoneViewHolder";

    public ZoneViewHolder(View itemView, Context context, int type) {
        super(itemView);
        this.itemView = itemView;
        this.context = context;
        this.type = type;
        ButterKnife.bind(this, itemView);
        initView();
    }

    public static ZoneViewHolder createView(Context context, int type) {
        return new ZoneViewHolder(LayoutInflater.from(context).inflate(R.layout.item_zone, null), context, type);
    }

    private void initView() {
        switch (type){
            case 2:
                zoneViewStub.setLayoutResource(R.layout.item_zone_url);
                zoneViewStub.inflate();
                urlImg= (ImageView) itemView.findViewById(R.id.url_img);
                urlTv= (TextView) itemView.findViewById(R.id.url_tv);
                break;
            case 1:
            default:
                zoneViewStub.setLayoutResource(R.layout.item_zone_img);
                zoneViewStub.inflate();
                multiImg= (MultiImageView) itemView.findViewById(R.id.zone_multi);
                break;
        }

        commentAdapter=new CommentAdapter(context);
        zoneComment.setAdapter(commentAdapter);
        favortAdapter=new FavortListAdapter();
        zoneLikeTv.setAdapter(favortAdapter);
    }


    public void setData(ZonePresenter presenter, final CircleItem circleItem, final int position){
        if(presenter==null||circleItem==null){
            return;
        }

        boolean hasFavort=circleItem.getGoodjobs().size()>0;
        boolean hasComment=circleItem.getReplys().size()>0;

        List<FavortItem>favortItems=circleItem.getGoodjobs();
        List<CommentItem>commentItems=circleItem.getReplys();


        this.presenter=presenter;
        this.circleItem=circleItem;
        this.position=position;

        zoneNick.setText(circleItem.getNickName());
        zoneUrl.setText(circleItem.getContent());
        zoneDel.setVisibility(AppCache.getInstance().getUserId().equals(circleItem.getUserId())?View.VISIBLE:View.GONE);

        ImageLoaderUtils.displayRound(context,zoneAvatar, DatasUtil.getRandomPhotoUrl());

        zoneSendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment(circleItem.getId(),circleItem.getUserId(),position);
            }
        });

        zoneLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String favort=circleItem.getCurUserFavortId();
                Log.e(TAG,"favort"+favort);
                if(!TextUtils.isEmpty(favort)){
                    favort(circleItem.getId(),circleItem.getUserId(),position,zoneLike,false);
                }else{
                    favort(circleItem.getId(),circleItem.getUserId(),position,zoneLike,true);
                }
            }
        });
        switch (type){
            case 2:
                urlTv.setText(circleItem.getLinkTitle());
                break;
            case 1:
            default:

                List<String>photos=circleItem.getPictureList();
                if(photos!=null&&photos.size()>0){
                    multiImg.setList(photos);
                    multiImg.setVisibility(View.VISIBLE);
                }else{
                    multiImg.setVisibility(View.GONE);
                }
                break;
        }

        if(hasFavort){
            favortAdapter.setDatas(favortItems);
            favortAdapter.notifyDataSetChanged();
            zoneLikeTv.setVisibility(View.VISIBLE);
            zoneLikeTv.setSpanClickListener(new ISpanClick() {
                @Override
                public void onClick(int position) {
                    Log.e(TAG,"like position"+position);
                }
            });
        }else{
            zoneLikeTv.setVisibility(View.GONE);
        }

        if(hasComment){
            commentAdapter.setDatas(commentItems);
            commentAdapter.notifyDataSetChanged();
            zoneComment.setVisibility(View.VISIBLE);
            zoneComment.setOnItemClick(new CommentListView.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Log.e(TAG,"comment position"+position);
                }
            });
        }else{
            zoneComment.setVisibility(View.GONE);
        }
    }

    private void comment(String id, String userId, int position) {
        CommentConfig config=new CommentConfig();
        config.circlePosition=position;
        config.commentType= CommentConfig.Type.PUBLIC;
        config.setPublishId(id);
        config.setPublishUserId(userId);
        presenter.updateEdit(config);
    }

    private void favort(String id, String userId, int position, View view, boolean likeFlag) {
        if(likeFlag){
            presenter.like(id,userId,position,view);
        }else{
            presenter.unLike(id, userId, position);
        }
    }
}
