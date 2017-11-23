package com.lesgood.app.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.auth.FirebaseUser;
import com.lesgood.app.R;
import com.lesgood.app.base.BaseApplication;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import retrofit2.http.HEAD;

/**
 * Created by ikun on 20/11/17.
 */

public class AppUtils {
    private FirebaseUser muser;
    public static void  showToas(Context context,String msg){
        StyleableToast.makeText(context,msg, Toast.LENGTH_SHORT, R.style.MyToast).show();
    }
    public static void setAvatar(Context context, String imgUrl, ImageView img){
        if(imgUrl!=null){
            if (!imgUrl.equalsIgnoreCase("NOT")){
                Glide.with(context).load(imgUrl).placeholder(R.color.colorSoft).dontAnimate().into(img);
            }
        }else {

            Glide.with(context).load(R.drawable.ic_account_circle).placeholder(R.color.colorSoft).dontAnimate().into(img);
        }
    }
    public static void ShowDialogWithBtn(Context context,String title, String msg,
        DialogInterface.OnClickListener onOkListener){
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", onOkListener);
        builder.setNegativeButton("NO", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
