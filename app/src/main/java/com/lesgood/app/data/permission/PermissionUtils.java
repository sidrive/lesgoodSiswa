package com.lesgood.app.data.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.lesgood.app.base.BaseApplication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Developer at Geekgarden.
 * Created by sim-x on 1/3/18.
 * Website geekgarden.id .
 * More info  geekgardendev@gmail.com.
 */

public class PermissionUtils {
  Context context;
  Activity activity;
  PermissionResultCallback permissionResultCallback;
  ArrayList<String> permissions;
  ArrayList<String> permissionsNeed;
  String msg = "";
  int reqCode;
  public PermissionUtils(Context context) {
    this.context = context;
    this.activity = BaseApplication.getActivity();
    this.permissionResultCallback = (PermissionResultCallback)context;
  }

  public PermissionUtils(Context context,
      PermissionResultCallback permissionResultCallback) {
    this.context = context;
    this.activity = BaseApplication.getActivity();
    this.permissionResultCallback = permissionResultCallback;
  }

  public void checkPermission(ArrayList<String> permissions, String msg, int reqCode){
    this.permissions = permissions;
    this.msg = msg;
    this.reqCode = reqCode;
    if (VERSION.SDK_INT >= 23){
      if (chekAndRequestPermissions(permissions,reqCode)){
        permissionResultCallback.PermissionGranted(reqCode);
      }
    }else {
      permissionResultCallback.PermissionDenied(reqCode);
    }
  }

  public boolean chekAndRequestPermissions(ArrayList<String> permissions, int reqCode){
    if (permissions.size()>0){
      this.permissionsNeed = new ArrayList<>();
      for (int i = 0; i < permissions.size(); i++) {
        int hasPermission = ContextCompat.checkSelfPermission(activity,permissions.get(i));
        if (hasPermission != PackageManager.PERMISSION_GRANTED){
          permissionsNeed.add(permissions.get(i));
        }
        if (!permissionsNeed.isEmpty()){
          ActivityCompat.requestPermissions(activity,permissionsNeed.toArray(new String[permissionsNeed.size()]),reqCode);
          return false;
        }
      }
    }
    return true;
  }

  public void requestPermissionResults(final int reqCode,  final String permissions [], int [] grantResults){
    switch (reqCode) {
      case 1:

        if (grantResults.length>0){
          Map<String,Integer> perm = new HashMap<>();
          for (int i = 0; i < permissions.length; i++) {
            perm.put(permissions[i],grantResults[i]);
          }
          final ArrayList<String> penddingPermissions = new ArrayList<>();
          for (int i = 0; i < permissionsNeed.size(); i++) {
            if (perm.get(permissionsNeed.get(i))!=PackageManager.PERMISSION_GRANTED){
              if (ActivityCompat.shouldShowRequestPermissionRationale(activity,permissionsNeed.get(i))){
                penddingPermissions.add(permissionsNeed.get(i));
              }else {
                permissionResultCallback.NeverAskAgain(reqCode);
                Toast.makeText(activity,"Goto Setting and enable permissions",Toast.LENGTH_SHORT).show();
                return;
              }
            }
          }

          if (penddingPermissions.size()>0){
            showMessageOKCancel(msg, new OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                  case DialogInterface.BUTTON_POSITIVE:
                    checkPermission(penddingPermissions,msg,reqCode);
                    break;
                  case DialogInterface.BUTTON_NEGATIVE:
                    if (permissions.length == penddingPermissions.size())
                      permissionResultCallback.PermissionDenied(reqCode);
                    else
                      permissionResultCallback.PartialPermissionGranted(reqCode,penddingPermissions);
                    break;
                }
              }
            });
          }

        }else {
          permissionResultCallback.PermissionGranted(reqCode);
        }
        break;

    }
  }
  private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
    new AlertDialog.Builder(activity)
        .setMessage(message)
        .setPositiveButton("Ok", okListener)
        .setNegativeButton("Cancel", okListener)
        .create()
        .show();
  }
  public interface PermissionResultCallback {
    void PermissionGranted(int request_code);
    void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions);
    void PermissionDenied(int request_code);
    void NeverAskAgain(int request_code);
  }
}
