package com.lesgood.app.ui.complete_order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.lesgood.app.BuildConfig;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseActivity;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.base.config.DefaultConfig;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Transaction;
import com.lesgood.app.data.model.TransactionResponse;
import com.lesgood.app.data.model.User;
import com.lesgood.app.ui.webview.WebViewActivity;
import com.lesgood.app.util.Utils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Agus on 5/13/17.
 */

public class CompleteOrderActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.view_progress)
    LinearLayout progressBar;

    @Bind(R.id.txt_title)
    TextView txtTitle;

    @Bind(R.id.txt_pertemuan)
    TextView txtPertemuan;

    @Bind(R.id.txt_amount)
    TextView txtAmount;

    @Bind(R.id.txt_disc)
    TextView txtDisc;

    @Bind(R.id.txt_fee)
    TextView txtFee;

    @Bind(R.id.txt_total)
    TextView txtTotal;

    @Bind(R.id.input_email)
    TextView inputEmail;

    @Inject
    Order order;

    @Inject
    User user;

    @Inject
    CompleteOrderPresenter presenter;

    @Inject
    DefaultConfig defaultConfig;

    String metode = "BT";


    public static void startWithOrder(BaseActivity activity, Order order) {
        Intent intent = new Intent(activity, CompleteOrderActivity.class);
        BaseApplication.get(activity).createOrderDetailComponent(order);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle("Selesaikan Pesanan");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        defaultConfig.setApiUrl(BuildConfig.BASE_URL);

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
                .plus(new CompleteOrderActivityModule(this))
                .inject(this);
    }

    public void init(){

        int disc = (int)(order.getDiscount()+0.5d);
        int fee = (int)(order.getFee()+0.5d);
        int total = (int)(order.getTotal()+0.5d);

        int amount = order.getAmount()+fee;

        txtPertemuan.setText(order.getTotalPertemuan()+" Pertemuan");
        txtTitle.setText(order.getTitle());
        txtAmount.setText(Utils.getRupiah(amount));
        txtTotal.setText(Utils.getRupiah(total)+" + service fee");
        txtDisc.setText(Utils.getRupiah(disc));
        inputEmail.setText(user.getEmail());
    }

    public void validate(){
        inputEmail.setError(null);
        boolean cancel = false;
        View focusView = null;

        String email = inputEmail.getText().toString();
        if (TextUtils.isEmpty(email)){
            inputEmail.setError("Harap masukan email untuk dikirim invoice");
            focusView = inputEmail;
            cancel = true;
        }else{
            if (!isValidEmail(email)){
                inputEmail.setError("Email tidak valid");
                focusView = inputEmail;
                cancel = true;
            }
        }

        if (cancel){
            focusView.requestFocus();
        }else{
            showLoading(true);

            int total = (int)(order.getTotal()+0.5d);
            String forSignature = BuildConfig.MERCHANT_CODE+order.getOid()+total+BuildConfig.MERCHANT_KEY;
            Log.d("signature", forSignature);

            Transaction transaction = new Transaction();
            transaction.setEmail(email);
            transaction.setCallbackUrl(BuildConfig.PAYMENT_URL);
            transaction.setMerchantUserInfo(user.getFull_name());
            transaction.setMerchantOrderId(order.getOid());
            transaction.setMerchantCode(BuildConfig.MERCHANT_CODE);
            transaction.setPaymentAmount(total);
            transaction.setProductDetails(order.getGuruName()+" - "+order.getTitle());
            transaction.setPaymentMethod(metode);
            transaction.setReturnUrl(BuildConfig.PAYMENT_URL+"orders");
            transaction.setSignature(Utils.md5(BuildConfig.MERCHANT_CODE+order.getOid()+total+BuildConfig.MERCHANT_KEY));
            presenter.sendTransaction(transaction);
        }
    }

    private boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public void showLoading(boolean show){
        if (show){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    public void showPayment(TransactionResponse response){
        if (response.getPaymentUrl() != null){
            showLoading(false);
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", response.getPaymentUrl());
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.method_atm)
    void paymentATM(){
        metode = "BT";
        validate();
    }

    @OnClick(R.id.method_cc)
    void paymentVC(){
        metode = "VC";
        validate();
    }

    @OnClick(R.id.method_bca)
    void paymentBCA(){
        metode = "BK";
        validate();
    }

    @OnClick(R.id.method_mandiri)
    void paymentMandiri(){
        metode = "MY";
        validate();
    }

    @OnClick(R.id.method_cimb)
    void paymentCimb(){
        metode = "CK";
        validate();
    }
}
