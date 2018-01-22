package com.lesgood.siswa.ui.edit_profile;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lesgood.siswa.base.BasePresenter;
import com.lesgood.siswa.data.model.User;
import com.lesgood.siswa.data.remote.FirebaseImageService;
import com.lesgood.siswa.data.remote.UserService;


/**
 * Created by Agus on 4/20/17.
 */

public class EditProfilePresenter implements BasePresenter {
    EditProfileActivity activity;
    UserService userService;
    FirebaseImageService firebaseImageService;

    public EditProfilePresenter(EditProfileActivity activity, UserService userService, FirebaseImageService firebaseImageService){
        this.activity = activity;
        this.userService = userService;
        this.firebaseImageService = firebaseImageService;
    }


    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void updateProfile(final User user){
        userService.updateUser(user).addOnCompleteListener(
            task -> activity.successUpdateProfile(user));
    }

    public void uploadAvatar(final User user, byte[] data, final Uri uri){
        StorageReference avatarPartnerRef = firebaseImageService.getUserImageRefOriginal(user.getUid());

        UploadTask uploadTask = avatarPartnerRef.putFile(uri);
// Register observers to listen for when the download is done or if it fails


        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            System.out.print(exception);
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            Uri downloadUrl = taskSnapshot.getDownloadUrl();
            activity.successUploadImage(downloadUrl.toString());

        });
    }

}