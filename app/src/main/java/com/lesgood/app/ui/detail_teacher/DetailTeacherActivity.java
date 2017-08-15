package com.lesgood.app.ui.detail_teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.book_2.BookActivity;
import com.lesgood.app.ui.brief.BriefActivity;
import com.lesgood.app.ui.edit_profile.EditProfileActivity;
import com.lesgood.app.ui.pengalaman.PengalamanActivity;
import com.lesgood.app.ui.prestasi.PrestasiActivity;
import com.lesgood.app.ui.reviews.ReviewsActivity;
import com.lesgood.app.ui.skill.SkillActivity;
import com.lesgood.app.util.Utils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Agus on 2/22/17.
 */

public class DetailTeacherActivity extends BaseActivity {

    private static String TAG = "ProfileFragment";
    private static int REQUEST_CODE_ADD_BRIEF = 1054;
    private static int REQUEST_CODE_SKIL = 1059;

    @Bind(R.id.txt_name)
    TextView txtName;

    @Bind(R.id.txt_about)
    TextView txtAbout;

    @Bind(R.id.txt_skills)
    TextView txtSkills;

    @Bind(R.id.txt_location)
    TextView txtLocation;

    @Bind(R.id.img_avatar)
    CircleImageView imgAvatar;

    @Bind(R.id.img_bg_avatar)
    ImageView imgBgAvatar;

    @Bind(R.id.txt_price)
    TextView txtPrice;

    @Bind(R.id.txt_verified)
    TextView txtVerified;

    @Bind(R.id.txt_pendidikan)
    TextView txtPendidikan;

    @Inject
    DetailTeacherPresenter presenter;

    @Inject
    Guru user;

    String userAbout = "";

    public static void startWithData(BaseActivity activity, Guru user, Order order){
        BaseApplication.get(activity).createDetailTeacherComponent(user);
        BaseApplication.get(activity).createBookComponent(order);
        Intent intent = new Intent(activity, DetailTeacherActivity.class);

        activity.startActivity(intent);
    }

    @Override
    protected void setupActivityComponent() {
        BaseApplication.get(this)
                .getDetailTeacherComponent()
                .plus(new DetailTeacherActivityModule(this))
                .inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_techear);
        ButterKnife.bind(this);


        setTitle("Profil");
        init();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
        init();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    private void init(){
        txtName.setText(user.getFull_name());
        if (user.getPhoto_url() != null) {
            if (!user.getPhoto_url().equalsIgnoreCase("NOT")){
                Glide.with(this)
                        .load(user.getPhoto_url()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e("IMAGE_EXCEPTION", "Exception " + e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d("smtime img's not loaded",  "n dis tex's not di");
                        return false;
                    }
                })
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgBgAvatar);

                Glide.with(this)
                        .load(user.getPhoto_url())
                        .placeholder(R.color.colorSoft)
                        .dontAnimate()
                        .into(imgAvatar);
            }
        }

        if (user.getTotalSkill() > 0){
            txtSkills.setText(user.getTotalSkill()+" Kemampuan mangajar");
        }

        if (user.getFullAddress() != null){
            txtLocation.setText(user.getFullAddress());
        }

        if (user.getStartFrom() > 0){
            initPrice(user.getStartFrom());
        }

        if (user.getPendidikan() != null){
            txtPendidikan.setText(user.getPendidikan());
        }

        if (user.isVerified()){
            txtVerified.setText("Status : Terverifikasi");
        }
    }

    public void initAbout(String content){
        this.userAbout = content;
        txtAbout.setText(Html.fromHtml(content));
    }


    public void initPrice(int price){
        this.user.setStartFrom(price);
        txtPrice.setText("Mulai dari "+Utils.getRupiah(price)+" /100 menit");
    }


    @OnClick(R.id.btn_edit_about)
    void showEditAbout(){
        Intent intent = new Intent(this, BriefActivity.class);
        intent.putExtra("brief", userAbout);
        startActivityForResult(intent, REQUEST_CODE_ADD_BRIEF);
    }

    @OnClick(R.id.lin_skill)
    void showSkills(){
        startActivityForResult(new Intent(this, SkillActivity.class), REQUEST_CODE_SKIL);
    }

    @OnClick(R.id.lin_location)
    void showSetLocation(){

    }

    @OnClick(R.id.lin_prestasi)
    void showPrestasi(){
        startActivity(new Intent(this, PrestasiActivity.class));
    }

    @OnClick(R.id.lin_review)
    void showReviews(){
        startActivity(new Intent(this, ReviewsActivity.class));
    }

    @OnClick(R.id.lin_pengalaman)
    void showPengalaman(){
        startActivity(new Intent(this, PengalamanActivity.class));
    }

    @OnClick(R.id.btn_book)
    void showBook(){
        startActivity(new Intent(this, BookActivity.class));
    }

}
