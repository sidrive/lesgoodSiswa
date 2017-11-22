package com.lesgood.app.data.remote;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    public DatabaseReference getDetalsOrder(String oid){
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
    //Siswa Absen
    public Task<Void> updateTotalPertemuan(String orderId,int pertemuan){
        return databaseRef.child("orders").child(orderId).child("totalPertemuan").setValue(pertemuan);
    }
    //
}
