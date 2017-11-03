package com.lesgood.app.ui.order_detail;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.ui.complete_order.CompleteOrderActivity;
import com.lesgood.app.ui.main.MainActivity;
import com.lesgood.app.util.DateFormatter;

import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 2/23/17.
 */

public class OrderDetailActivity extends BaseActivity {
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

    @Bind(R.id.img_map)
    ImageView imgMap;

    @Bind(R.id.txt_detail_lokasi)
    TextView txtDetailLokasi;

    @Bind(R.id.txt_distance)
    TextView txtDistance;

    @Bind(R.id.lin_action)
    LinearLayout linAction;

    @Inject
    Order order;

    @Inject
    OrderDetailPresenter presenter;


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
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();
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
        if (id == android.R.id.home){
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



    public void init(){
        txtCustomerName.setText(order.getCustomerName().toUpperCase());
        txtOrderId.setText("#"+order.getOid());
        String statusOrder =  order.getStatus();
        if(statusOrder.equalsIgnoreCase("pending_guru")){
        txtStatus.setText("Menunggu Konfirmasi Guru");
        }else{
        txtStatus.setText("Menunggu Pembayaran");
        }
        //txtStatus.setText(order.getStatus().toUpperCase());
        txtDate.setText(DateFormatter.getDate(order.getPertemuanTime(), "EEE, dd MMM yyyy, HH:mm"));
        txtProduct.setText(order.getTitle());
        txtSiswa.setText(String.valueOf(order.getTotalSiswa()));
        txtPertemuan.setText(String.valueOf(order.getTotalPertemuan())+" kali");
        txtDetailLokasi.setText(order.getDetailLocation());

        String url = "http://maps.googleapis.com/maps/api/staticmap?zoom=16&size=800x400&maptype=roadmap%20&markers=color:red%7Clabel:S%7C" + order.getLatitude() + "," + order.getLongitude() + "+&sensor=false";

        Glide.with(this)
                .load(url)
                .placeholder(R.color.colorGrey400)
                .centerCrop()
                .dontAnimate()
                .into(imgMap);



        int fee = (int)(order.getFee()+0.5d);
        int disc = (int)(order.getDiscount()+0.5d);
        int total = (int)(order.getTotal()+0.5d);

        txtAmount.setText("Rp."+toRupiah(order.getAmount()+fee));
        txtDisc.setText("Rp."+toRupiah(disc));
        txtTotal.setText("Rp."+toRupiah(total));

        handleStatus(order.getStatus());

    }

    public void handleStatus(String status){
        if (status.equalsIgnoreCase("pending_murid")){
            linAction.setVisibility(View.VISIBLE);
        }else {
            linAction.setVisibility(View.GONE);
        }

        if (status.equalsIgnoreCase("cancel_guru")){
            String title = "Pesanan Dibatalkan";
            String desc = "Pesanan telah dibatalkan oleh pengajar";
            int icon = R.drawable.ic_appointment_reminders_primary_32dp;
            showAlertDialog(title, desc, icon);
        }
    }

    public void showProgress(boolean show){
        if (show){
            viewProgress.setVisibility(View.VISIBLE);
        }else {
            viewProgress.setVisibility(View.GONE);
        }
    }

    private String toRupiah(int amount){
        String angka = Integer.toString(amount);
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        String rupiah = rupiahFormat.format(Double.parseDouble(angka));
        return rupiah;
    }

    @OnClick(R.id.btn_positif)
    void accept(){
        CompleteOrderActivity.startWithOrder(this, order);
    }

    @OnClick(R.id.btn_negatif)
    void decline(){
        showProgress(true);
        presenter.declineOrder(order);
    }

    public void successAction(Order order){
        if (order.getStatus().equalsIgnoreCase("pending_murid")){
            CompleteOrderActivity.startWithOrder(this, order);
        }else if (order.getStatus().equalsIgnoreCase("cancel_guru")){
            String title = "Pembatalan pesanan";
            String desc = "Pesanan telah dibatalkan";
            int icon = R.drawable.ic_appointment_reminders_primary_32dp;
            showAlertDialog(title, desc, icon);
        }
    }

    private void showAlertDialog(String title, String desc, int icon){
        final Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        new AlertDialog.Builder(this)
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

}
