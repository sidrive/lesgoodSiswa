package com.lesgood.app.data.remote;

import android.app.Application;

import com.alamkanak.weekview.WeekViewEvent;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.lesgood.app.data.model.EmailConfirmation;
import com.lesgood.app.data.model.Pengalaman;
import com.lesgood.app.data.model.Prestasi;
import com.lesgood.app.data.model.Reviews;
import com.lesgood.app.data.model.Skill;
import com.lesgood.app.data.model.User;


/**
 * Created by Agus on 2/21/17.
 */

public class UserService {

    private Application application;
    private DatabaseReference databaseRef;
    private Query query;
    public UserService(Application application) {
        this.application = application;
        this.databaseRef = FirebaseDatabase.getInstance().getReference();
        this.query = FirebaseDatabase.getInstance().getReference();
    }

    public void createUser(User user) {
        if(user.getPhoto_url() == null) {
            user.setPhoto_url("NOT");
        }
        databaseRef.child("users").child(user.getUid()).setValue(user);

    }


    public DatabaseReference getUser(String userUid) {
        return databaseRef.child("users").child(userUid);
    }
    public Query getGutuIsActive(String uid){
        return databaseRef.child("users").child(uid).orderByChild("active").equalTo(true);
    }

    public Task<Void> updateUser(User user) {
        return databaseRef.child("users").child(user.getUid()).setValue(user);
    }

    public void deleteUser(String key) {

    }

    //users

    public void updateUserToken(String uid, String token){
        databaseRef.child("users").child(uid).child("token").setValue(token);
    }

    public void sendEmailConfirmation(EmailConfirmation emailConfirmation){
        databaseRef.child("confirmationEmailRequest").push().setValue(emailConfirmation);
    }

    public void verifyUser(String uid, boolean status){
        databaseRef.child("users").child(uid).child("verified").setValue(status);
    }

    // userabout

    public DatabaseReference getUserAbout(String uid){
        return databaseRef.child("users-about").child(uid);
    }


    public void updateAbout(String uid, String content){
        databaseRef.child("users-about").child(uid).setValue(content);
    }

    //userabout



    //userskills

    public DatabaseReference getUserSkills(String uid){
        return databaseRef.child("user-skills").child(uid);
    }

    public DatabaseReference getUserSkill(String uid, String code){
        return databaseRef.child("user-skills").child(uid).child(code);
    }

    public DatabaseReference getStatus(String uid){
        return databaseRef.child("user-status").child(uid);
    }

    public void updateTotalSkill(String uid, int total){
        databaseRef.child("users").child(uid).child("totalSkill").setValue(total);
    }

    public Task<Void> updateSkill(String uid, Skill skill){
        return databaseRef.child("user-skills").child(uid).child(skill.getCode()).setValue(skill);
    }

    public Task<Void> removeSkill(String uid, Skill skill){
        return databaseRef.child("user-skills").child(uid).child(skill.getCode()).removeValue();
    }

    //userskill


    //userschedule

    public Query getUserSchedule(String uid){
        return databaseRef.child("user_schedules").orderByChild("id").equalTo(uid);
    }

    public void updateSchedule(String uid, WeekViewEvent weekViewEvent){
        databaseRef.child("user_schedules").child(uid).child(Long.toString(weekViewEvent.getId())).setValue(weekViewEvent);
    }

    //userschedule

    //userreviews
    public DatabaseReference getUserReviews(String uid){
        return databaseRef.child("user-reviews").child(uid).child("reviews");
    }

    public Task<Void> updateReviews(String gid, String uid, Reviews reviews){
        return databaseRef.child("user-reviews").child(gid).child("reviews").child(reviews.getId()).setValue(reviews);
    }
    //userreview


    //userprestasi

    public DatabaseReference getUserPrestasi(String uid){
        return databaseRef.child("user-prestasi").child(uid);
    }

    public Task<Void> updatePrestasi(String uid, Prestasi prestasi){
        return databaseRef.child("user-prestasi").child(uid).child(prestasi.getId()).setValue(prestasi);
    }

    public Task<Void> removePrestasi(String uid, Prestasi prestasi){
        return databaseRef.child("user-prestasi").child(uid).child(prestasi.getId()).removeValue();
    }

    //userprestasi

    //userpengalaman

    public DatabaseReference getUserPengalaman(String uid){
        return databaseRef.child("user-pengalaman").child(uid);
    }

    public Task<Void> updatePengalaman(String uid, Pengalaman pengalaman){
        return databaseRef.child("user-pengalaman").child(uid).child(pengalaman.getId()).setValue(pengalaman);
    }

    public Task<Void> removePengalaman(String uid, Pengalaman pengalaman){
        return databaseRef.child("user-pengalaman").child(uid).child(pengalaman.getId()).removeValue();
    }

    //userprestasi


    //Userlocation
    public DatabaseReference getUserLocation(String uid){
        return databaseRef.child("user-location").child(uid);
    }


    //update price
    public  void updateUserPrice(String uid, int price){
        databaseRef.child("users").child(uid).child("startFrom").setValue(price);
    }

    //update price

    public DatabaseReference getUserPayment(String uid){
        return databaseRef.child("partner-payment").child(uid);
    }

    public Task<Void> setSaldoUser(String uid, int saldo){
        return databaseRef.child("users").child(uid).child("saldo").setValue(saldo);
    }
    public void updateStatus(String uid, boolean status){
        databaseRef.child("users").child(uid).child("active").setValue(status);
    }
    //update price

    public DatabaseReference getGurus(String code){
        return databaseRef.child("gurus").child(code);
    }

    public DatabaseReference getUserStatus(String uid){
        return databaseRef.child("users").child(uid);
    }

    public GeoFire getUserGeofire(DatabaseReference reference){
        return new GeoFire(reference);
    }
    public void geoQuery(double lat, double lng){
    }
}
