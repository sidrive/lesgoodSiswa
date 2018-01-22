package com.lesgood.siswa.ui.detail_teacher;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lesgood.siswa.R;
import com.lesgood.siswa.base.BaseActivity;
import com.lesgood.siswa.base.BaseApplication;
import com.lesgood.siswa.data.model.Guru;
import com.lesgood.siswa.data.model.Order;
import com.lesgood.siswa.ui.book_2.BookActivity;
import com.lesgood.siswa.ui.pengalaman.PengalamanActivity;
import com.lesgood.siswa.ui.prestasi.PrestasiActivity;
import com.lesgood.siswa.ui.reviews.ReviewsActivity;
import com.lesgood.siswa.ui.skill.SkillActivity;
import com.lesgood.siswa.util.Utils;
import de.hdodenhof.circleimageview.CircleImageView;
import javax.inject.Inject;

/**
 * Created by Agus on 2/22/17.
 */

public class DetailTeacherActivity extends BaseActivity {

  private static String TAG = "ProfileFragment";
  private static int REQUEST_CODE_ADD_BRIEF = 1054;
  private static int REQUEST_CODE_SKIL = 1059;

  @Bind(R.id.view_progress)
  LinearLayout viewProgress;

  @Bind(R.id.rating_bar)
  RatingBar rating;

  @Bind(R.id.txt_rating)
  TextView totalrating;

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
  @Bind(R.id.btn_book)
  Button btnBook;
  String oldOrderId;
  public static void startWithData(BaseActivity activity, Guru user,Order order) {
    BaseApplication.get(activity).createDetailTeacherComponent(user);
    BaseApplication.get(activity).createBookComponent(order);
    Intent intent = new Intent(activity, DetailTeacherActivity.class);
    activity.startActivity(intent);
  }
  public static void startFromChangeTeacher(BaseActivity activity, Guru user,Order order, String oldOid) {
    BaseApplication.get(activity).createDetailTeacherComponent(user);
    BaseApplication.get(activity).createBookComponent(order);
    Intent intent = new Intent(activity, DetailTeacherActivity.class);
    intent.putExtra("old_oid",oldOid);
    activity.startActivity(intent);
  }
  public static void start(BaseActivity activity) {
      Intent starter = new Intent(activity, DetailTeacherActivity.class);
      activity.startActivity(starter);
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
    Bundle extras = getIntent().getExtras();
    if (extras!=null){
      oldOrderId = extras.getString("old_oid");
      iniFromChangeTeacher(oldOrderId);
    }else {
      init();
    }
  }

  private void iniFromChangeTeacher(String oldOrderId) {

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

  @SuppressLint("ResourceType")
  private void init() {
    txtName.setText(user.getFull_name());
    float ratings = user.getReview() / 10;
    totalrating.setText(String.valueOf(ratings));
    rating.setRating(ratings);
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

    if (user.getTotalSkill() > 0) {
      txtSkills.setText(user.getTotalSkill() + " Kemampuan mangajar");
    }

    if (user.getFullAddress() != null) {
      txtLocation.setText(user.getFullAddress());
    }

    if (user.getStartFrom() > 0) {
      initPrice(user.getStartFrom());
    }

    if (user.getPendidikan() != null) {
      txtPendidikan.setText(user.getPendidikan());
    }

    if (user.isVerified()) {
      txtVerified.setText("Status : Terverifikasi");
    }

    if (user.active == false) {
        btnBook.setClickable(false);
        btnBook.setText("Not Aviable");
    }else {
      btnBook.setClickable(true);
    }
  }

  public void initAbout(String content) {
    this.userAbout = content;
    txtAbout.setText(Html.fromHtml(content));
  }


  public void initPrice(int price) {

    this.user.setStartFrom(price);
    double fee = 0;

    fee = price * 0.3;

    int finaltarif = (int) ((price + fee) + 0.5d);
    txtPrice.setText("Mulai dari " + Utils.getRupiah(finaltarif) + " /100 menit");
  }


  @OnClick(R.id.btn_edit_about)
  void showEditAbout() {
    Log.e("showEditAbout", "DetailTeacherActivity" + userAbout);
       /* Intent intent = new Intent(this, BriefActivity.class);
        intent.putExtra("brief", userAbout);
        startActivityForResult(intent, REQUEST_CODE_ADD_BRIEF);*/
  }

  @OnClick(R.id.lin_skill)

  void showSkills() {
    startActivity(new Intent(this, SkillActivity.class));
  }

  @OnClick(R.id.lin_location)
  void showSetLocation() {

  }

  void Loading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }

  @OnClick(R.id.lin_prestasi)
  void showPrestasi() {
    startActivity(new Intent(this, PrestasiActivity.class));
  }

  @OnClick(R.id.lin_review)
  void showReviews() {
    startActivity(new Intent(this, ReviewsActivity.class));
  }

  @OnClick(R.id.lin_pengalaman)
  void showPengalaman() {
    startActivity(new Intent(this, PengalamanActivity.class));
  }

  @OnClick(R.id.btn_book)
  void showBook() {
    //Loading(true);
    //startActivity(new Intent(this, BookActivity.class));

    if (oldOrderId!=null){
      BookActivity.startFromChangeTeacher(this,oldOrderId);
    }else {
      BookActivity.start(this);
    }
  }

}