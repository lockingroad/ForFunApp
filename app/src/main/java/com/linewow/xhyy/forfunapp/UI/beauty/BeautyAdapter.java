package com.linewow.xhyy.forfunapp.UI.beauty;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.linewow.xhyy.forfunapp.R;
import com.linewow.xhyy.forfunapp.entity.BeautyInfo;
import com.linewow.xhyy.forfunapp.view.beauty.BadgedFourThreeImageView;
import com.linewow.xhyy.forfunapp.view.beauty.DribbbleTarget;
import com.linewow.xhyy.forfunapp.view.beauty.ObservableColorMatrix;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LXR on 2017/1/23.
 */

public class BeautyAdapter extends BaseQuickAdapter<BeautyInfo> {
    private Context context;
    public BeautyAdapter(List<BeautyInfo> data) {
        super( R.layout.item_beauty,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final BeautyInfo beautyInfo) {
        if(context==null){
            context=baseViewHolder.getConvertView().getContext();
        }
        final BadgedFourThreeImageView img=baseViewHolder.getView(R.id.beauty_img);

        Glide.with(context)
                .load(beautyInfo.url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        if (!beautyInfo.hasFadedIn) {
                            img.setHasTransientState(true);
                            final ObservableColorMatrix cm = new ObservableColorMatrix();
                            final ObjectAnimator animator = ObjectAnimator.ofFloat(cm, ObservableColorMatrix.SATURATION, 0f, 1f);
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    img.setColorFilter(new ColorMatrixColorFilter(cm));
                                }
                            });
                            animator.setDuration(2000L);
                            animator.setInterpolator(new AccelerateInterpolator());
                            animator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    img.clearColorFilter();
                                    img.setHasTransientState(false);
                                    animator.start();
                                    beautyInfo.hasFadedIn = true;

                                }
                            });
                        }

                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(new DribbbleTarget(img,false));//波纹效果


    }
}
