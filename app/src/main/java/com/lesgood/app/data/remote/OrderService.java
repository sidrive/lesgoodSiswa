package com.lesgood.app.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.lesgood.app.data.model.HistoryOders;
import com.lesgood.app.data.model.Order;

/**
 * Created by Agus on 2/27/17.
 */

public class OrderService {
    DatabaseReference databaseRef;

    public OrderService(){
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getOrders(){
        return databaseRef.child("orders");
    }

    public DatabaseReference getDetailsOrder(String oid){
        return databaseRef.child("orders").child(oid);
    }
    public DatabaseReference getOrderItems(String oid){
        return databaseRef.child("order-items").child(oid);
    }

    public Task<Void> approveOrder(String oid){
        return databaseRef.child("orders").child(oid).child("status").setValue("pending");
    }

    public Task<Void> declineOrder(String oid){
        return databaseRef.child("orders").child(oid).child("status").setValue("cancel_murid");
    }

    public Task<Void> saveOrder(Order order){
        return databaseRef.child("orders").child(order.getOid()).setValue(order);
    }
    public Task<Void> order(Order order){
        return databaseRef.child("orders").child(order.getOid()).setValue(order);
    }
    public Task<Void> updateOrderPaymentStatus(String oid,String status){
        return databaseRef.child("orders").child(oid).child("statusPayment").setValue(status);
    }
    public Task<Void> updateStatusOrder(String oid,String status){
        return databaseRef.child("orders").child(oid).child("status").setValue(status);
    }
    //Siswa Absen
    public Task<Void> updateTotalPertemuan(String orderId,int pertemuan){
        return databaseRef.child("orders").child(orderId).child("totalPertemuan").setValue(pertemuan);
    }
    //

    //Temporery Order
    public Task<Void> createTempOrder(String oid_temp,Order order){
        return databaseRef.child("temp-orders").child(oid_temp).setValue(order);
    }
    public Task<Void> deleteTempOrder(String temp_oid){
        return databaseRef.child("temp-orders").child(temp_oid).removeValue();
    }
    //pustaka
    public Query getPusataka(String code){
        return databaseRef.child("pustaka-pdf").orderByChild("code").equalTo(code);
    }

    //Ganti Pengajar
    public Task<Void> createChangeTheacher(HistoryOders historyOders){
        return databaseRef.child("history-orders").child(historyOders.getOid()).setValue(historyOders);
    }
    public Task<Void> updateStatusChangeTheacher(String oid,String status){
        return databaseRef.child("history-orders").child(oid).child("status").setValue(status);
    }
    public Task<Void> updateStatusOrderChangeTheacher(String oid,String status){
        return databaseRef.child("orders").child(oid).child("ganti_guru").setValue(status);
    }

}
