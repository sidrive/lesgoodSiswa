package com.lesgood.app.ui.order;

import android.util.Log;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lesgood.app.BuildConfig;
import com.lesgood.app.base.BasePresenter;
import com.lesgood.app.data.model.CekPayment;
import com.lesgood.app.data.model.CekPaymentResponse;
import com.lesgood.app.data.model.Order;
import com.lesgood.app.data.model.User;
import com.lesgood.app.data.remote.OrderService;
import com.lesgood.app.data.remote.PaymentService;
import com.lesgood.app.util.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * Created by Agus on 5/2/17.
 */

public class PlaceHolderFragmentPresenter implements BasePresenter {
    PlaceholderFragment fragment;
    OrderService orderService;
    DatabaseReference databaseRef;
    User user;
    ChildEventListener orderEventListener;
    PaymentService paymentService;
    CekPayment cekPayment;
    public PlaceHolderFragmentPresenter(PlaceholderFragment fragment, OrderService orderService,
        User user, Retrofit retrofit){
        this.fragment = fragment;
        this.orderService = orderService;
        this.user = user;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
        this.paymentService = retrofit.create(PaymentService.class);
        this.cekPayment = new CekPayment();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (orderEventListener != null) databaseRef.removeEventListener(orderEventListener);
    }

    public void getOrders(final String status){
        orderEventListener = orderService.getOrders().orderByChild("uid")
            .equalTo(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Order order =dataSnapshot.getValue(Order.class);


                if (order != null) {
                    cekAllOrderPayament(order);
                    if (status.equals("waiting")){
                        if (order.getStatus()!=null){
                            if (order.getStatus().equals("pending_guru")
                                || order.getStatus().equals("pending_murid")
                                || order.getStatus().equals("pending")){
                                fragment.showAddedOrder(order);

                            }
                        }

                    }

                    if (status.equals("complete")){
                        if (order.getStatus()!=null){
                            if (order.getStatus().equalsIgnoreCase("SUCCESS")){
                                fragment.showAddedOrder(order);
                            }
                        }

                    }
                    if (status.equals("Ganti Guru")){
                        if (order.getStatusGantiGuru()!=null){
                            if (order.getStatusGantiGuru().equalsIgnoreCase("request")){
                                fragment.showAddedOrder(order);
                            }
                        }

                    }
                    if (status.equals("history")){
                        if (order.getStatusGantiGuru()!=null){
                            if (!order.getStatusGantiGuru().equalsIgnoreCase("request")){
                                fragment.showAddedOrder(order);
                            }
                        }

                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Order order =dataSnapshot.getValue(Order.class);
                if (order != null) if (order.getStatus().equals(status)) fragment.showChangedOrder(order);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public  void cekAllOrderPayament(Order order){
        String signature = Utils.md5(BuildConfig.MERCHANT_CODE+order.getOid()+BuildConfig.MERCHANT_KEY);

        cekPayment.setSignature(signature);
        cekPayment.setMerchantCode(BuildConfig.MERCHANT_CODE);
        cekPayment.setMerchantOrderId(order.getOid());
        Observable<CekPaymentResponse> responseObservable = paymentService.cekTransactions(cekPayment).subscribeOn(
            Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        responseObservable.subscribe(
            cekPaymentResponse -> {
                updateStatusPayment(cekPaymentResponse.getMerchantOrderId(),cekPaymentResponse.getStatusMessage());
                if (cekPaymentResponse.getStatusCode().equals("00")){
                    orderService.updateStatusOrder(cekPaymentResponse.getMerchantOrderId(),cekPaymentResponse.getStatusMessage());
                }
            },
            throwable -> {
                Log.e("cekAllOrderPayament", "PlaceHolderFragmentPresenter" + throwable.getMessage());
            }
        );
    }
    public void updateStatusPayment(String oid, String status){
        orderService.updateOrderPaymentStatus(oid,status).addOnCompleteListener(task -> {
            Log.e("updateStatusPayment", "SUCCESS UPDATE PAYMENT" );
        }).addOnFailureListener(e -> Log
            .e("updateStatusPayment", "PlaceHolderFragmentPresenter" + e.getMessage()));
    }
}
