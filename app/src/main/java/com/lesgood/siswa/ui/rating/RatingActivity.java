package com.lesgood.siswa.ui.rating;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lesgood.siswa.R;
import com.lesgood.siswa.base.BaseActivity;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.model.Reviews;
import com.lesgood.siswa.data.model.User;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ikun on 17/11/17.
 */

public class RatingActivity extends BaseActivity {
    @Bind(R.id.img_avatar)
    ImageView imgAvatar;

    @Bind(R.id.txt_nama_pengajar)
    TextView txtNama;

    @Bind(R.id.input_reviews)
    EditText reviews;

    @Bind(R.id.rating_bar)
    RatingBar ratingBar;

    @Inject
    Guru guru;

    @Inject
    User user;

    @Inject
    RatingActivityPresenter presenter;

    public static void startWithData(BaseActivity activity, Reviews reviews){
        /*BaseApplication.get(activity).createRatingComponent(reviews);*/
        Intent intent = new Intent(activity, RatingActivity.class);
        activity.startActivity(intent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        txtNama.setText(guru.getFull_name());
        if (user.getPhoto_url() != null) {
            if (!user.getPhoto_url().equalsIgnoreCase("NOT")) {
                Glide.with(this)
                        .load(user.getPhoto_url()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        Log.e("IMAGE_EXCEPTION", "Exception " + e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d("smtime img's not loaded", "n dis tex's not di");
                        return false;
                    }
                });
                Glide.with(this)
                        .load(user.getPhoto_url())
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);
            }
        }
    }

    @Override
    protected void setupActivityComponent() {

    }
}
