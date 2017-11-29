package com.lesgood.app.ui.order_detail;


import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Reviews;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.complete_order.CompleteOrderActivity;
import com.lesgood.app.ui.list.ListActivity;
import com.lesgood.app.ui.main.MainActivity;
import com.lesgood.app.util.AppUtils;
import com.lesgood.app.util.DateFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;
import javax.inject.Inject;

/**
 * Created by Agus on 2/23/17.
 */

public class OrderDetailActivity extends BaseActivity {

  @BindString(R.string.error_field_required)
  String errRequired;

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.view_progress)
  LinearLayout viewProgress;

  @Bind(R.id.txt_customer_name)
  TextView txtCustomerName;

  @Bind(R.id.txt_product)
  TextView txtProduct;

  @Bind(R.id.txt_tota_person)
  TextView txtSiswa;

  @Bind(R.id.txt_pertemuan)
  TextView txtPertemuan;

  @Bind(R.id.txt_date)
  TextView txtDate;

  @Bind(R.id.txt_order_id)
  TextView txtOrderId;

  @Bind(R.id.txt_status)
  TextView txtStatus;

  @Bind(R.id.txt_amount)
  TextView txtAmount;

  @Bind(R.id.txt_disc)
  TextView txtDisc;

  @Bind(R.id.txt_total)
  TextView txtTotal;

  @Bind(R.id.btn_reviews)
  Button btn_reviews;

  @Bind(R.id.img_map)
  ImageView imgMap;

  @Bind(R.id.txt_detail_lokasi)
  TextView txtDetailLokasi;

  @Bind(R.id.txt_distance)
  TextView txtDistance;

  @Bind(R.id.txt_nama_siswa)
  TextView txtNamaSiswa;

  @Bind(R.id.txt_alamat_siswa)
  TextView txtAlamatSiswa;

  @Bind(R.id.txt_telp_siswa)
  TextView txtTelpSiswa;

  @Bind(R.id.txt_email_siswa)
  TextView txtEmailSiswa;

  @Bind(R.id.txt_nama_guru)
  TextView txtGuru;

  @Bind(R.id.txt_alamat_guru)
  TextView txtAlamatGuru;

  @Bind(R.id.txt_telp_guru)
  TextView txtTelpGuru;

  @Bind(R.id.txt_email_guru)
  TextView txtEmailGuru;

  @Bind(R.id.lin_action)
  LinearLayout linAction;

    /*@Bind(R.id.lin_action2)
    LinearLayout linAction2;*/

  @Inject
  Order order;

  @Inject
  User user;

  /*@Inject
  Guru guru;*/

  @Inject
  OrderDetailPresenter presenter;
  @Bind(R.id.lyt_btn_ganti_pengajar)
  LinearLayout lytBtnGantiPengajar;
  @Bind(R.id.rcv_pustaka)
  RecyclerView rcvPustaka;
  @Bind(R.id.lyt_pustaka)
  LinearLayout lytPustaka;
  @Bind(R.id.btn_absent)
  Button btnAbsent;
  @Bind(R.id.lyt_btn_review)
  LinearLayout lytBtnReview;


  public static void startWithOrder(BaseActivity activity, Order order) {
    Intent intent = new Intent(activity, OrderDetailActivity.class);
    BaseApplication.get(activity).createOrderDetailComponent(order);
    activity.startActivity(intent);

  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_order_detail);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    presenter.getDetailOrder(order.getOid());
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.getDetailOrder(order.getOid());
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenter.unsubscribe();
    BaseApplication.get(this).releaseOrderDetailComponent();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      finish();
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this)
        .getOrderDetailComponent()
        .plus(new OrderDetailActivityModule(this))
        .inject(this);
  }


  public void init(Order order) {

    txtCustomerName.setText(order.getCustomerName().toUpperCase());
    txtOrderId.setText("#" + order.getOid());

    txtDate.setText(DateFormatter.getDate(order.getPertemuanTime(), "EEE, dd MMM yyyy, HH:mm"));
    txtProduct.setText(order.getTitle());
    txtSiswa.setText(String.valueOf(order.getTotalSiswa()));
    txtPertemuan.setText(String.valueOf(order.getTotalPertemuan()) + " kali");
    txtDetailLokasi.setText(order.getDetailLocation());
    if (order.getTotalPertemuan() == 0) {
      lytBtnReview.setVisibility(View.VISIBLE);
      lytBtnGantiPengajar.setVisibility(View.GONE);
    }
    if (order.getTotalPertemuan() > 0 && order.getStatus().equalsIgnoreCase("success")) {
      btnAbsent.setVisibility(View.VISIBLE);
      lytBtnGantiPengajar.setVisibility(View.VISIBLE);
    } else {
      btnAbsent.setVisibility(View.GONE);
    }

    String url =
        "http://maps.googleapis.com/maps/api/staticmap?zoom=16&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C"
            + order.getLatitude() + "," + order.getLongitude() + "+&sensor=false";

    Glide.with(this)
        .load(url)
        .placeholder(R.color.colorGrey400)
        .centerCrop()
        .dontAnimate()
        .into(imgMap);

    int fee = (int) (order.getFee() + 0.5d);
    int disc = (int) (order.getDiscount() + 0.5d);
    int total = (int) (order.getTotal() + 0.5d);

    txtAmount.setText("Rp." + toRupiah(order.getAmount() + fee));
    txtDisc.setText("Rp." + toRupiah(disc));
    txtTotal.setText("Rp." + toRupiah(total));

    handleStatus(order.getStatus());

    txtNamaSiswa.setText(order.getCustomerName());
    txtGuru.setText(order.getGuruName());
    txtAlamatSiswa.setText(order.getDetailLocation());
    txtAlamatGuru.setText(order.getGuruAddres());
    txtTelpSiswa.setText(order.getCustomerPhone());
    txtTelpGuru.setText(order.getGuruPhone());
    txtEmailSiswa.setText(order.getCustomerEmail());
    txtEmailGuru.setText(order.getGuruEmail());

  }

  private void setLayoutOrderSuccess() {
    lytPustaka.setVisibility(View.VISIBLE);

    linAction.setVisibility(View.GONE);
  }

  public void handleStatus(String status) {
    if (status.equalsIgnoreCase("success")) {
      setLayoutOrderSuccess();
    } else if (status.equalsIgnoreCase("pending_murid")) {
      txtStatus.setText("Menunggu Pembayaran");
      linAction.setVisibility(View.VISIBLE);
      btn_reviews.setVisibility(View.INVISIBLE);
    } else if (status.equalsIgnoreCase("pending_guru")) {
      txtStatus.setText("Menunggu Konfirmasi Guru");
      linAction.setVisibility(View.INVISIBLE);
      btn_reviews.setVisibility(View.INVISIBLE);
    } else if (status.equalsIgnoreCase("cancel_guru")) {
      String title = "Pesanan Dibatalkan";
      String desc = "Pesanan telah dibatalkan oleh pengajar";
      int icon = R.drawable.ic_appointment_reminders_primary_32dp;
      showAlertDialog(title, desc, icon);
    }
  }

  public void showProgress(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }

  private String toRupiah(int amount) {
    String angka = Integer.toString(amount);
    NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
    String rupiah = rupiahFormat.format(Double.parseDouble(angka));
    return rupiah;
  }

  @OnClick(R.id.btn_positif)
  void accept() {
    CompleteOrderActivity.startWithOrder(this, order);
  }

  @OnClick(R.id.btn_negatif)
  void decline() {
    showProgress(true);
    presenter.declineOrder(order);
  }

  public void successAction(Order order) {
    if (order.getStatus().equalsIgnoreCase("pending_murid")) {
      CompleteOrderActivity.startWithOrder(this, order);
    } else if (order.getStatus().equalsIgnoreCase("cancel_murid")) {
      String title = "Pembatalan pesanan";
      String desc = "Pesanan telah dibatalkan";
      int icon = R.drawable.ic_appointment_reminders_primary_32dp;
      showAlertDialog(title, desc, icon);
    }
  }

  private void showAlertDialog(String title, String desc, int icon) {
    final Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    new Builder(this)
        .setTitle(title)
        .setMessage(desc)
        .setCancelable(false)
        .setPositiveButton("OK", (dialog, which) -> {
          // continue with delete
          dialog.dismiss();
          startActivity(intent);
        })
        .setIcon(icon)
        .show();
  }

  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }

  @OnClick(R.id.btn_reviews)
  void showAddReviews() {
    final Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setContentView(R.layout.dialog_insert_prestasi);

    final EditText inputReview = (EditText) dialog.findViewById(R.id.input_prestasi);
    final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.simpleRatingBar);
    Button btnPositif = (Button) dialog.findViewById(R.id.btn_positif);
    Button btnNegatif = (Button) dialog.findViewById(R.id.btn_negatif);

    btnPositif.setOnClickListener(v -> {
      final String review = inputReview.getText().toString();
      final float rating = ratingBar.getRating();
      final String reviewer = order.getCustomerName();
      if (TextUtils.isEmpty(review)) {
        inputReview.setError(errRequired);
        inputReview.requestFocus();
      } else {
        showLoading(true);
        Reviews reviews = new Reviews(UUID.randomUUID().toString(), review, rating, reviewer);
        presenter.updateReview(reviews);
      }
      dialog.dismiss();
    });

    btnNegatif.setOnClickListener(v -> dialog.dismiss());

    dialog.show();

  }

  @OnClick(R.id.btn_ganti_pengajar)
  public void onBtnGantiPengajarClicked() {
    AppUtils
        .ShowDialogWithBtn(this, "Ganti Pengajar", "Apakah anda yakin untuk menganti pengajar ?",
            gantiPengajarClickListener);
  }

  @OnClick(R.id.btn_absent)
  public void onBtnAbsenClicked() {
    AppUtils.ShowDialogWithBtn(this, "Absen Harian", "Selesai les untuk hari ini ?",
        absenClickListener);
  }

  public OnClickListener gantiPengajarClickListener = (dialog, which) -> {
    openListGuru(order.getTitle(),order.getCode(),order.getOid());
    dialog.dismiss();
  };

  private void openListGuru(String title, String code, String oid) {
    ListActivity.startFromChangeTeacher(this,code,title,oid);
    finish();
  }

  public OnClickListener absenClickListener = (dialog, which) -> {
    dialog.dismiss();
    presenter.absenLes();
  };
}
