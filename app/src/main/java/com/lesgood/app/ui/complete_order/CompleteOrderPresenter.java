package com.lesgood.app.ui.complete_order;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.Transaction;
import com.lesgood.app.data.model.TransactionResponse;
import com.lesgood.app.data.remote.OrderService;
import com.lesgood.app.data.remote.PaymentService;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Agus on 5/13/17.
 */

public class CompleteOrderPresenter implements BasePresenter {
    CompleteOrderActivity activity;
    Order order;
    OrderService orderService;
    Retrofit retrofit;
    PaymentService paymentService;
    Disposable disposable;
    public CompleteOrderPresenter(CompleteOrderActivity activity, OrderService orderService, Order order, Retrofit retrofit){
        this.activity = activity;
        this.orderService =orderService;
        this.order = order;
        this.paymentService = retrofit.create(PaymentService.class);

    }



    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }


    protected void sendTransaction(Transaction transaction){
        order.setCustomerEmail(transaction.getEmail());
        Call<TransactionResponse> call = paymentService.transaction(transaction);
        call.enqueue(transCallback);
    }
    private Callback<TransactionResponse> transCallback = new Callback<TransactionResponse>() {
        @Override
        public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
            Log.d("onrespomse",response.toString());
            if (response.isSuccessful()){
                TransactionResponse transactionResponse = response.body();
                if (transactionResponse.getPaymentUrl() != null){
                    if (transactionResponse.getReference() != null) {
                        order.setPaymentUrl(transactionResponse.getPaymentUrl());
                        order.setReference(transactionResponse.getReference());
                        updateOrder(order, transactionResponse);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<TransactionResponse> call, Throwable t) {
            Log.d("error", t.toString());
        }
    };
    public void prosessTransaction(Transaction transaction){
        Observable<TransactionResponse> observable = paymentService.sendTransactions(transaction).subscribeOn(
            Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(
            transactionResponse -> {
                order.setPaymentUrl(transactionResponse.getPaymentUrl());
                order.setReference(transactionResponse.getReference());
                activity.showPayment(transactionResponse);
            },
            throwable -> {
                Log.e("prosessTransaction", "CompleteOrderPresenter" + throwable.getMessage());
            }
            );

    }

    public void updateOrder(Order order, final TransactionResponse response){
        orderService.order(order).addOnCompleteListener(task -> activity.showPayment(response));
    }
}
