package com.linewow.xhyy.funlibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.linewow.xhyy.funlibrary.R;
import com.linewow.xhyy.funlibrary.util.Help;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by LXR on 2016/9/29.
 */
public class BeautyPhotoActivity extends AppCompatActivity {
    ImageView photoImg;
    Toolbar photoToolbar;
    RelativeLayout photoRelative;
    private String TAG="BeautyPhotoActivity";
    PhotoViewAttacher mPhotoViewAttacher;
    private String uri;
    private boolean mIsHidden;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_photo);
        photoImg= (ImageView) findViewById(R.id.photo_img);
        photoToolbar= (Toolbar) findViewById(R.id.photo_toolbar);
        photoRelative= (RelativeLayout) findViewById(R.id.photo_relative);
        initData();
        initView();


        photoToolbar.setAlpha(0.7f);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            getWindow().getSharedElementEnterTransition().addListener(mListener);
            getWindow().setSharedElementEnterTransition(new ChangeBounds());
        }
    }

    private Transition.TransitionListener mListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            Log.d("maat","xingfeifei");
            photoRelative.animate()
                    .alpha(1f)
                    .setDuration(1000L)
                    .setInterpolator(new AccelerateInterpolator())
                    .start();


        }

        @Override
        public void onTransitionEnd(Transition transition) {

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    private void setUpAttacher() {
        mPhotoViewAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                hideOrShowToolbar();
            }
        });
        mPhotoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(BeautyPhotoActivity.this)
                        .setMessage("小猪 收了他")
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {
                                anInterface.dismiss();
                            }
                        })
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface anInterface, int i) {

                                anInterface.dismiss();
                                saveImage();
                            }
                        }).show();
                return false;
            }
        });
    }

    private void saveImage() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File directory = new File(externalStorageDirectory,"LookLook");
        if (!directory.exists())
            directory.mkdir();
        Bitmap drawingCache = mPhotoViewAttacher.getImageView().getDrawingCache();
        try {
            File file = new File(directory, new Date().getTime() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            drawingCache.compress(Bitmap.CompressFormat.JPEG,100,fos);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            getApplicationContext().sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Snackbar.make(getCurrentFocus(),"阿偶出错了呢", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void initView() {
//        Glide.with(this)
//                .load(uri)
//                .asBitmap()
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .centerCrop()
//                .into(target);
        Picasso.with(this)
                .load(uri)
                .fit()
                .centerInside()
//                .transform(getTransformation(BeautyPhotoActivity.this))
                .into(photoImg,imageLoadedCallback);


        photoToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        photoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAnim();
            }
        });

    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            // do something with the bitmap
            // for demonstration purposes, let's just set it to an ImageView
            photoImg.setImageBitmap( bitmap );
            if(mPhotoViewAttacher!=null){
                mPhotoViewAttacher.update();
                Log.e(TAG,"glide更新了1");
            }else{
                mPhotoViewAttacher = new PhotoViewAttacher(photoImg);
                Log.e(TAG,"glide初始化了1");
                setUpAttacher();
            }
        }
    };




    Callback imageLoadedCallback = new Callback() {

        @Override
        public void onSuccess() {
            if(mPhotoViewAttacher!=null){
                mPhotoViewAttacher.update();
                Log.e(TAG,"更新了");
            }else{
                mPhotoViewAttacher = new PhotoViewAttacher(photoImg);
                setUpAttacher();
                Log.e(TAG,"初始化了");
            }
        }

        @Override
        public void onError() {
            // TODO Auto-generated method stub

        }
    };

    private void finishAnim() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            finishAfterTransition();
        }else{
            finish();
        }
    }

    private void initData() {
        uri=getIntent().getStringExtra("uri");
    }
    @Override
    protected void onResume() {
        super.onResume();
//        photoRelative.animate()
//                .alpha(0.2f)
//                .setDuration(1000L)
//                .setInterpolator(new AccelerateInterpolator())
//                .start();
    }

    protected void hideOrShowToolbar() {
        photoToolbar.animate()
                .translationY(mIsHidden ? 0 : -photoToolbar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHidden = !mIsHidden;
    };

//    private RequestListener loadListener = new RequestListener<String, GlideDrawable>() {
//        @Override
//        public boolean onResourceReady(GlideDrawable resource, String model,
//                                       Target<GlideDrawable> target, boolean isFromMemoryCache,
//                                       boolean isFirstResource) {
//            final Bitmap bitmap = GlideUtils.getBitmap(resource);
//            final int twentyFourDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                    24, MeiziPhotoDescribeActivity.this.getResources().getDisplayMetrics());
//            Palette.from(bitmap)
//                    .maximumColorCount(3)
//                    .clearFilters() /* by default palette ignore certain hues
//                        (e.g. pure black/white) but we don't want this. */
//                    .setRegion(0, 0, bitmap.getWidth() - 1, twentyFourDip) /* - 1 to work around
//                        https://code.google.com/p/android/issues/detail?id=191013 */
//                    .generate(new Palette.PaletteAsyncListener() {
//                        @Override
//                        public void onGenerated(Palette palette) {
//                            boolean isDark;
//                            @ColorUtils.Lightness int lightness = ColorUtils.isDark(palette);
//                            if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
//                                isDark = ColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
//                            } else {
//                                isDark = lightness == ColorUtils.IS_DARK;
//                            }
//
//                            // color the status bar. Set a complementary dark color on L,
//                            // light or dark color on M (with matching status bar icons)
//                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
//
//                                int statusBarColor = getWindow().getStatusBarColor();
//                                final Palette.Swatch topColor =
//                                        ColorUtils.getMostPopulousSwatch(palette);
//                                if (topColor != null &&
//                                        (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
//                                    statusBarColor = ColorUtils.scrimify(topColor.getRgb(),
//                                            isDark, SCRIM_ADJUSTMENT);
//                                }
//
//                                if (statusBarColor != getWindow().getStatusBarColor()) {
//                                    ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(
//                                            getWindow().getStatusBarColor(), statusBarColor);
//                                    statusBarColorAnim.addUpdateListener(new ValueAnimator
//                                            .AnimatorUpdateListener() {
//                                        @Override
//                                        public void onAnimationUpdate(ValueAnimator animation) {
//                                            getWindow().setStatusBarColor(
//                                                    (int) animation.getAnimatedValue());
//                                        }
//                                    });
//                                    statusBarColorAnim.setDuration(1000L);
//                                    statusBarColorAnim.setInterpolator(
//                                            new AccelerateInterpolator());
//                                    statusBarColorAnim.start();
//                                }
//                            }
//                        }
//                    });
//
//            return false;
//        }
//
//        @Override
//        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
//                                   boolean isFirstResource) {
//            return false;
//        }
//    };


    public Transformation getTransformation(final Context context) {

        Transformation transformation = new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {

                int targetWidth = getWindowWid((Activity) context);
                Log.e("MainActivity", "source.getHeight()=" + source.getHeight() + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);

                if (source.getWidth() == 0) {
                    return source;
                }

                //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight != 0 && targetWidth != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }

            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };

        return transformation;
    }

    public int getWindowWid(Activity act) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        return mDisplayMetrics.widthPixels;
    }

    public static void startBeautyPhotot(Context context,ImageView imageView,String uri){
        Intent intent=new Intent(context, BeautyPhotoActivity.class);
        intent.putExtra("uri",uri);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            final android.support.v4.util.Pair<View, String>[] pairs = Help.createSafeTransitionParticipants
                    ((Activity) context, false,new android.support.v4.util.Pair<>(imageView,"beauty"));
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, pairs);
            context.startActivity(intent, options.toBundle());
        }else {
            context.startActivity(intent);
        }
    }

}
