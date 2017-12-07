package com.lesgood.app.ui.book_2;

import android.Manifest.permission;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.OnTextChanged.Callback;
import com.bumptech.glide.Glide;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Guru;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.data.model.TimeSchedule;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.main.MainActivity;
import com.lesgood.app.util.AppUtils;
import com.lesgood.app.util.CustomTimeDialog;
import com.lesgood.app.util.DateFormatter;
import com.lesgood.app.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks;

/**
 * Created by Agus on 8/10/17.
 */

public class BookActivity extends BaseActivity implements OnMapReadyCallback,
    OnCameraIdleListener, OnDateSetListener,
    OnTimeSetListener, PermissionCallbacks {

  private static String TAG = "BookActivity";
  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Bind(R.id.txt_mengajar)
  TextView txtMengajar;

  @Bind(R.id.view_progress)
  LinearLayout viewProgress;

  @Bind(R.id.txt_guru)
  TextView txtGuru;

  @Bind(R.id.img_map)
  ImageView imgMap;

  @Bind(R.id.input_detail)
  EditText inputDetail;

  @Bind(R.id.btn_date)
  Button btnDate;

  @Bind(R.id.btn_time)
  Button btnTime;

  @Bind(R.id.input_siswa)
  EditText inputSiswa;

  @Bind(R.id.input_tarif_siswa)
  EditText inputTarifSiswa;

  @Bind(R.id.input_pertemuan)
  EditText inputPertemuan;

  @Bind(R.id.input_tarif_pertemuan)
  EditText inputTarifPertemuan;

  @Bind(R.id.radio_paket)
  RadioGroup radioPaket;

  @Bind(R.id.radio_paket_1)
  RadioButton radioPaket1;

  @Bind(R.id.radio_paket_2)
  RadioButton radioPaket2;

  @Bind(R.id.rel_map)
  RelativeLayout relMap;

  @Inject
  Order order;

  @Inject
  Guru guru;

  @Inject
  User user;

  @Inject
  BookActivityPresenter presenter;

  @Inject
  ScheduleAdapter scheduleAdapter;
  MenuItem menuDone;

  @Bind(R.id.rcvJadwal)
  RecyclerView rcvJadwal;
  @Bind(R.id.lyt_jml_siswa)
  LinearLayout lytJmlSiswa;
  @Bind(R.id.lyt_tarif)
  LinearLayout lytTarif;
  @Bind(R.id.btn_book)
  Button btnBook;

  @Bind(R.id.img_marker)
  ImageView imgMarker;

  private GoogleMap mMap;

  private boolean mapMode = false;

  private double latitude = 0;
  private double longitude = 0;

  Skill skill;

  int tarif = 0;
  int cleanTarif;
  int pertemuan = 0;
  int siswa = 0;
  double fee;
  int amount = 0;
  double discount = 0;
  int base_tarif = 0;
  String oldOid;
  List<Event> eventList = new ArrayList<>();
  Calendar calendar = Calendar.getInstance();
  SupportMapFragment mapFragment;
  private static final int RC_LOCATION_PERM = 205;
  Calendar calOrder;
  public static void startWithData(BaseActivity activity, Order order) {
    BaseApplication.get(activity).createBookComponent(order);
  }

  public static void start(Context context) {
    Intent starter = new Intent(context, BookActivity.class);
    context.startActivity(starter);
  }

  public static void startFromChangeTeacher(BaseActivity activity, String oldOid) {
    Intent intent = new Intent(activity, BookActivity.class);
    intent.putExtra("oldOid", oldOid);
    activity.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_2);
    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setTitle("Pesan Pengajar");
    //locationPerm();
    calOrder = Calendar.getInstance();
    mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
    Bundle extras = getIntent().getExtras();
    if (extras != null) {
      oldOid = extras.getString("oldOid");
    }
    Log.e("onCreate", "BookActivity" + oldOid);
    if (oldOid != null) {
      initWithChangeTeacher(oldOid);
    } else {
      init();
    }


  }

  private void initWithChangeTeacher(String oldOid) {
    presenter.getGuruSkill(guru.getUid(), order.getCode());
    presenter.getOrderById(oldOid);
  }

  private void showSchedules() {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
        LinearLayoutManager.HORIZONTAL, false);
    rcvJadwal.setLayoutManager(linearLayoutManager);
    rcvJadwal.setAdapter(scheduleAdapter);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.subscribe();
  }

  @Override
  protected void setupActivityComponent() {
    BaseApplication.get(this)
        .getBookComponent()
        .plus(new BookActivityModule(this))
        .inject(this);
  }

  @Override
  public void onBackPressed() {
    if (viewProgress.getVisibility() == View.GONE) {
      viewProgress.setVisibility(View.GONE);

      super.onBackPressed();
    }
    viewProgress.setVisibility(View.GONE);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.done, menu);
    menuDone = menu.findItem(R.id.menu_done);
    menuDone.setVisible(false);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == android.R.id.home) {
      if (mapMode) {
        hideMap();
      } else {
        finish();
      }
    }

    if (id == R.id.menu_done) {
      if (mapMode) {
        saveMap();
      } else {
        finish();
      }
    }

    return super.onOptionsItemSelected(item);
  }


  public void showLoading(boolean show) {
    if (show) {
      viewProgress.setVisibility(View.VISIBLE);
    } else {
      viewProgress.setVisibility(View.GONE);
    }
  }

  public void init() {
    txtMengajar.setText(order.getTitle());
    txtGuru.setText(guru.getFull_name());
    showLoading(true);
    presenter.getGuruSkill(guru.getUid(), order.getCode());
  }

  public void initSkill(Skill skill) {
    this.skill = skill;
    showLoading(false);
    inputSiswa.setText("1");
    inputPertemuan.setText("4");
    initPaket();
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;
    LatLng indonesia = new LatLng(-7.803249, 110.3398253);
    initMap(indonesia);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 16));
    mMap.setOnCameraIdleListener(this);
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    mMap.setMyLocationEnabled(true);
        /*mMap.setOnMyLocationButtonClickListener(() -> {
            LatLng latLng = new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude());
            return false;
        });*/
  }

  private void handleNewLatLng(LatLng pos) {
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
  }

  public void initMap(LatLng latLng) {
    latitude = latLng.latitude;
    longitude = latLng.longitude;
    String url =
        "http://maps.googleapis.com/maps/api/staticmap?zoom=16&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C"
            + latLng.latitude + "," + latLng.longitude + "+&sensor=false";
    Log.d("initmap", "url = " + url);
    Glide.with(this)
        .load(url)
        .placeholder(R.color.colorGrey400)
        .centerCrop()
        .dontAnimate()
        .into(imgMap);

  }

  public void initPaket() {
    double paketTarif1 = 20 * tarif;
    double paketTarif1after = paketTarif1 - (paketTarif1 * 0.05);
    double paketTarif2 = 30 * tarif;
    double paketTarif2after = paketTarif2 - (paketTarif1 * 0.05);

    String paket1 = "Pake 20 kali\n" +
        "diskon 5%\n" +
        "- Sebelum diskon Rp." + paketTarif1 + "\n" +
        "- Sesudah diskon Rp." + paketTarif1after;

    String paket2 = "Pake 30 kali\n" +
        "diskon 10%\n" +
        "- Sebelum diskon Rp." + paketTarif2 + "\n" +
        "- Sesudah diskon Rp." + paketTarif2after;

    radioPaket1.setText(paket1);
    radioPaket2.setText(paket2);

  }

  @OnClick(R.id.img_map)
  void showMap() {
    relMap.setVisibility(View.VISIBLE);
    mapMode = true;
    menuDone.setVisible(true);
  }

  private void hideMap() {
    relMap.setVisibility(View.GONE);
    menuDone.setVisible(false);
    mapMode = false;
  }

  private void saveMap() {
    LatLng latLng = mMap.getCameraPosition().target;
    latitude = latLng.latitude;
    longitude = latLng.longitude;
    initMap(latLng);
    hideMap();
  }

  @Override
  public void onCameraIdle() {
    LatLng latLng = mMap.getCameraPosition().target;
  }


  @OnTextChanged(value = R.id.input_siswa, callback = Callback.AFTER_TEXT_CHANGED)
  void onInputSiswa(Editable s) {
    if (TextUtils.isEmpty(s.toString())) {
      inputSiswa.setText("1");
      inputSiswa.selectAll();
      return;
    }

    siswa = Integer.valueOf(s.toString());
    if (siswa < 0 || siswa > 5) {
      inputSiswa.setText("1");
      siswa = 1;
    }

    int tarif = 0;
    double fee = 0;

    if (siswa == 1) {
      tarif = skill.getPrice1();
      base_tarif = skill.getPrice1();
    } else if (siswa == 2) {
      tarif = skill.getPrice2();
      base_tarif = skill.getPrice2();
    } else if (siswa == 3) {
      tarif = skill.getPrice3();
      base_tarif = skill.getPrice3();
    } else if (siswa == 4) {
      tarif = skill.getPrice4();
      base_tarif = skill.getPrice4();
    } else {
      tarif = skill.getPrice5();
      base_tarif = skill.getPrice5();
    }

    fee = tarif * 0.3;

    this.cleanTarif = tarif;
    this.fee = fee;
    this.tarif = (int) ((tarif + fee) + 0.5d);

    String tarifStr = "Rp." + this.tarif;
    inputTarifSiswa.setText(tarifStr);
    handleTotalPertemuan();
    initPaket();
    Log.e("base_tarif", "BookActivity" + base_tarif);
  }


  @OnTextChanged(value = R.id.input_pertemuan, callback = Callback.AFTER_TEXT_CHANGED)
  void onInputPertemuan(Editable s) {
    if (TextUtils.isEmpty(s.toString())) {
      inputPertemuan.setText("4");
      inputPertemuan.selectAll();
      return;
    }

    pertemuan = Integer.valueOf(s.toString());
    if (pertemuan < 3) {
      inputSiswa.setText("4");
      pertemuan = 4;
      Toast.makeText(this, "Minimal 4 pertemuan", Toast.LENGTH_SHORT).show();
    }else {
      handleTotalPertemuan();
    }


  }

  public void handleTotalPertemuan() {
    int tarif = pertemuan * this.tarif;
    this.amount = tarif;
    order.setAmount(tarif);

    String tarifStr = "Rp." + tarif;
    inputTarifPertemuan.setText(tarifStr);
  }




  public void setEvent(TimeSchedule date) {
    scheduleAdapter.onItemAdded(date);
    showSchedules();
  }

  public void setDate(TimeSchedule date) {
    btnDate.setText(date.getDay());
    btnTime.setText(date.getTime());
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    calendar.set(year, monthOfYear, dayOfMonth);
    btnDate.setText(DateFormatter.getDate(calendar.getTimeInMillis(), "EE dd MMM yyyy"));
  }

  @Override
  public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);

    btnTime.setText(DateFormatter.getDate(calendar.getTimeInMillis(), "HH:mm"));

  }

  @OnClick(R.id.btn_book)
  void book() {
    if (oldOid != null) {
      validateWithNewData();
    } else {
      validate();
    }

  }

  Order newOrder = null;

  private void validateWithNewData() {
    boolean cancel = false;
    View focusView = null;
    int totalPertemuan = Integer.valueOf(newOrder.getTotalPertemuan());
    String detilLokasi = newOrder.getDetailLocation();
    if (btnDate.getText().toString().equalsIgnoreCase("Tanggal")) {
      Toast.makeText(this, "Pilih Tanggal Terlebih dahulu", Toast.LENGTH_SHORT).show();
      cancel = true;
    }

    if (btnTime.getText().toString().equalsIgnoreCase("Jam")) {
      Toast.makeText(this, "Pilih Jam Terlebih dahulu", Toast.LENGTH_SHORT).show();
      cancel = true;
    }
    if (cancel) {
      if (focusView != null) {
        focusView.requestFocus();
      }

    } else {
      showLoading(true);
      Random rand = new Random();
      String oid = Integer.toString(rand.nextInt(99999));
      long ordertime = System.currentTimeMillis();
      order.setOid(oid);

      order.setAmount(newOrder.getAmount());
      order.setCode(skill.getCode());
      order.setGid(guru.getUid());
      order.setOrdertime(ordertime);
      order.setUid(user.getUid());
      order.setPertemuanTime(calOrder.getTimeInMillis());
      order.setFee(fee);
      order.setTotalSiswa(siswa);
      order.setLatitude(latitude);
      order.setLongitude(longitude);
      order.setDetailLocation(detilLokasi);
      order.setTotalPertemuan(totalPertemuan);
      order.setTotal(newOrder.getTotal());
      order.setStatus("change_guru");
      order.setStatusPayment(newOrder.getStatus());
      order.setOrderType(newOrder.getOid());
      order.setDiscount(discount);
      order.setCustomerPhone(user.getPhone());
      order.setCustomerName(user.getFull_name());
      order.setCustomerEmail(user.getEmail());
      order.setGuruEmail(guru.getEmail());
      order.setGuruPhone(guru.getPhone());
      order.setGuruName(guru.getFull_name());
      order.setGuruAddres(guru.getFullAddress());
      saveOrder(order);
    }
  }

  public void validate() {
    boolean cancel = false;
    View focusView = null;

    int totalPertemuan = Integer.valueOf(inputPertemuan.getText().toString());

    String detilLokasi = inputDetail.getText().toString();

    if (totalPertemuan < 3) {
      cancel = true;
      Toast.makeText(this, "Minimal 6 pertemuan", Toast.LENGTH_SHORT).show();
    }

    if (siswa < 1 || siswa > 5) {
      cancel = true;
      Toast.makeText(this, "Minimal siswa 1 dan maksimal 5", Toast.LENGTH_SHORT).show();
    }

    if (TextUtils.isEmpty(detilLokasi)) {
      inputDetail.setError("Wajib diisi");
      focusView = inputDetail;
      cancel = true;
    }

    if (btnDate.getText().toString().equalsIgnoreCase("Tanggal")) {
      Toast.makeText(this, "Pilih Tanggal Terlebih dahulu", Toast.LENGTH_SHORT).show();
      cancel = true;
    }

    if (btnTime.getText().toString().equalsIgnoreCase("Jam")) {
      Toast.makeText(this, "Pilih Jam Terlebih dahulu", Toast.LENGTH_SHORT).show();
      cancel = true;
    }

    if (cancel) {
      if (focusView != null) {
        focusView.requestFocus();
      }

    } else {
      showLoading(true);

      Random rand = new Random();
      String oid = Integer.toString(rand.nextInt(99999));

      long ordertime = System.currentTimeMillis();

      double fee = this.fee * totalPertemuan;

      int cleanAmount = cleanTarif * totalPertemuan;

      int disc = (int) (discount + 0.5d);
      int total = amount - disc;

      order.setOid(oid);
      order.setCreatedAt(ordertime);
      order.setTarif(base_tarif);
      order.setAmount(cleanAmount);
      order.setCode(skill.getCode());
      order.setGid(guru.getUid());
      order.setOrdertime(ordertime);
      order.setUid(user.getUid());
      order.setPertemuanTime(calOrder.getTimeInMillis());
      order.setFee(fee);
      order.setTotalSiswa(siswa);
      order.setLatitude(latitude);
      order.setLongitude(longitude);
      order.setDetailLocation(detilLokasi);
      order.setTotalPertemuan(totalPertemuan);
      order.setTotal(total);
      order.setStatus("pending_guru");
      order.setDiscount(discount);
      order.setCustomerPhone(user.getPhone());
      order.setCustomerName(user.getFull_name());
      order.setCustomerEmail(user.getEmail());
      order.setGuruEmail(guru.getEmail());
      order.setGuruPhone(guru.getPhone());
      order.setGuruName(guru.getFull_name());
      order.setGuruAddres(guru.getFullAddress());
      order.setCustomerPhone(user.getPhone());
      saveOrder(order);

    }
  }

  private void saveOrder(Order order) {
    Log.e("saveOrder", "BookActivity" + oldOid);
    if (oldOid != null) {
      presenter.saveOrder(order);
    } else {
      presenter.saveOrder(order);
    }

  }

  public void updateUiForCangeTeacher(Order orderFromData) {
    newOrder = orderFromData;
    txtMengajar.setText(orderFromData.getTitle());
    txtGuru.setText(guru.getFull_name());
    showLoading(true);
    radioPaket.setVisibility(View.GONE);
    inputDetail.setText(orderFromData.getDetailLocation());
    String pertemuan = String.valueOf(orderFromData.getTotalPertemuan());
    inputPertemuan.setText(pertemuan);
    presenter.getGuruSkill(orderFromData.getGid(), orderFromData.getCode());
    lytJmlSiswa.setVisibility(View.GONE);
    lytTarif.setVisibility(View.GONE);

  }

  public void set20Pertemuan(String pertemuan) {

    order.setPaket("Paket 20 kali pertemuan");
    inputPertemuan.setText(pertemuan);

    discount = amount * 0.05;
  }

  public void set30Pertemuan(String pertemuan) {

    order.setPaket("Paket 30 kali pertemuan");
    inputPertemuan.setText(pertemuan);

    discount = amount * 0.1;
  }

  @OnClick({R.id.radio_paket_1, R.id.radio_paket_2})
  public void radioGroupUpdate() {
    if (radioPaket1.isChecked()) {
      set20Pertemuan("20");
    }

    if (radioPaket2.isChecked()) {
      set30Pertemuan("30");
    }
  }

  public void successOrdering() {
    showLoading(false);
    String title = "Pesanan Dikirim";
    String desc = "Kami sendang melakukan konfirmasi pada pengajar";
    int icon = R.drawable.ic_appointment_reminders_primary_32dp;
    showAlertDialog(title, desc, icon);
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

  @AfterPermissionGranted(RC_LOCATION_PERM)
  public void locationPerm() {
    String[] perms = {permission.ACCESS_FINE_LOCATION,
        permission.ACCESS_COARSE_LOCATION};
    if (EasyPermissions.hasPermissions(this, permission.CAMERA)) {
      // Have permission, do the thing!
      //onMapMyLocation();
    } else {
      // Ask for one permission
      EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
          RC_LOCATION_PERM, perms);
    }
  }

  public void onMapMyLocation() {
    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    //mMap.setMyLocationEnabled(true);
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    // EasyPermissions handles the request result.
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {
    Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {
    Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

    if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
      new AppSettingsDialog.Builder(this).build().show();
    }
  }

  @OnClick(R.id.btn_date)
  public void onBtnDatePicker() {
    //showDialogDatePicker();
    AppUtils.showToas(this,"Pilih jam sesuai dengan hari yang tersedia terlebih dahulu");
  }

  private void showDialogDatePicker(long timeselected, String scheduleDay) {
    int h = Utils.getHours(timeselected);
    int m = Utils.getMinute(timeselected);
    DatePickerDialog dpd = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
          /*calOrder.set(year,monthOfYear,dayOfMonth);*/
          calOrder.set(year,monthOfYear,dayOfMonth,h,m);
          String selected = Utils.longToDay(calOrder.getTimeInMillis());
          int daySelected = Utils.getNumberDay(calOrder.getTimeInMillis());
          int daySchedule = Utils.converStringDayToInt(scheduleDay);

          if (daySchedule == daySelected){
            btnDate.setText(DateFormatter.getDate(calOrder.getTimeInMillis(), "EE dd MMM yyyy"));
          }else {
            handleWrongSelectedDay(selected,scheduleDay);
          }
        },
        calOrder.get(Calendar.YEAR),
        calOrder.get(Calendar.MONTH),
        calOrder.get(Calendar.DAY_OF_MONTH));

    dpd.show(getFragmentManager(), "Datepickerdialog");
  }

  private void handleWrongSelectedDay(String selected, String scheduleDay) {
    AppUtils.ShowDialogWithBtn(this,"Salah memilih hari", "Hari pada jadwal yang anda pilih "+Utils.dayFormated(scheduleDay)+" hari pada kalender yang anda pilih "+Utils.dayFormated(selected), (dialog, which) -> {
      dialog.dismiss();
    });
  }

  public void handleTimeSelected(long statTime , long endTime, String day, long timeSelected){
    if (Utils.getHours(timeSelected)>= Utils.getHours(statTime) && Utils.getHours(timeSelected)<= Utils.getHours(endTime)){
      if (Utils.getHours(timeSelected)== Utils.getHours(endTime)){
        if ( Utils.getMinute(timeSelected)<= Utils.getMinute(endTime)){
          btnTime.setText(DateFormatter.getDate(timeSelected, "HH:mm"));

          showDialogDatePicker(timeSelected,day);
        }else {
          handleWrongSelectedTime(statTime, endTime, timeSelected,day);
        }
      }
    }else {
      handleWrongSelectedTime(statTime, endTime, timeSelected,day);
    }
  }

  public void showDialogTimePicker(long startTime, long endTime, String day) {
    CustomTimeDialog dialog = new CustomTimeDialog(startTime,endTime,this,day);
    dialog.show(getFragmentManager(),"timedialog");

    /*Calendar cal = Calendar.getInstance();
    TimePickerDialog dpd = TimePickerDialog.newInstance(
        (view, hourOfDay, minute, second) -> {
          cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH),
              hourOfDay, minute, second);
          if (Utils.getHours(cal.getTimeInMillis())>= Utils.getHours(startTime) && Utils.getHours(cal.getTimeInMillis())<= Utils.getHours(endTime)){
            Log.e("showDialogTimePicker", "OK HOUR" );
            if (*//*Utils.getMinute(cal.getTimeInMillis())>= Utils.getMinute(startTime) &&*//* Utils.getMinute(cal.getTimeInMillis())<= Utils.getMinute(endTime)){
              Log.e("showDialogTimePicker", "OK MINUTE" );
              btnTime.setText(DateFormatter.getDate(cal.getTimeInMillis(), "HH:mm"));
              showDialogDatePicker(startTime,day);
            }else {
              Log.e("showDialogTimePicker", "ERROR MINUTE");
              handleWrongSelectedTime(startTime, endTime, cal.getTimeInMillis(),day);
            }
          }else {
            handleWrongSelectedTime(startTime, endTime, cal.getTimeInMillis(),day);
          }

          btnTime.setText(DateFormatter.getDate(cal.getTimeInMillis(), "HH:mm"));

        },
        cal.get(Calendar.HOUR_OF_DAY),
        cal.get(Calendar.MINUTE),
        true
    );
    Calendar startTimeCal = Calendar.getInstance();
    startTimeCal.setTimeInMillis(startTime);
    int sH = startTimeCal.get(Calendar.HOUR);
    int sM = startTimeCal.get(Calendar.MINUTE);
    int sS = startTimeCal.get(Calendar.SECOND);
    dpd.setStartTime(sH, sM, sS);
    dpd.setTitle("Waktu mulai");
    dpd.vibrate(true);
    dpd.dismissOnPause(true);
    dpd.show(getFragmentManager(), "TImepickerdialog");*/
  }
  private void handleWrongSelectedTime(long startTime, long endTime, long timeInMillis,
      String day) {
    String selectedTime = Utils.longToString(timeInMillis);
    String sTime = Utils.longToString(startTime);
    String eTime = Utils.longToString(endTime);
    AppUtils.ShowDialogWithBtn(this, "Guru tidak memiliki jadwal pada jam " + selectedTime,
        "Pilih antara jam " + sTime + " - " + eTime, (dialog, which) -> {
          dialog.dismiss();
          showDialogTimePicker(startTime, endTime, day);
        });
  }

}
