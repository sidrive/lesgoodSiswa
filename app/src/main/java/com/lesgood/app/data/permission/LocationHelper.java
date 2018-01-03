package com.lesgood.app.data.permission;

import android.Manifest.permission;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnSuccessListener;
import com.lesgood.app.base.BaseApplication;
import com.lesgood.app.data.permission.PermissionUtils.PermissionResultCallback;
import java.util.ArrayList;

/**
 * Developer at Geekgarden.
 * Created by sim-x on 1/3/18.
 * Website geekgarden.id .
 * More info  geekgardendev@gmail.com.
 */

public class LocationHelper implements PermissionResultCallback, ConnectionCallbacks,
    OnConnectionFailedListener {
  private Context context;
  private Activity activity;
  private boolean isGaranted;
  private Location mLocation;
  private LocationManager locationManager;
  private GoogleApiClient googleApiClient;
  private String [] permissions = new String[]{permission.ACCESS_FINE_LOCATION,
      permission.ACCESS_COARSE_LOCATION,
      permission.READ_CONTACTS};
  private PermissionUtils permissionUtils;
  private final static int PLAY_SERVICES_REQUEST = 1000;
  private final static int REQUEST_CHECK_SETTINGS = 2000;

  public LocationHelper(Context context, LocationManager locationManager) {
    this.context = context;
    this.activity = BaseApplication.getActivity();
    this.permissionUtils = new PermissionUtils(context,this);
    this.locationManager = locationManager;


  }

  public void checkPermission(){

    Log.e("checkPermission", "LocationHelper" + permissions.toString());
    permissionUtils.checkPermission(permissions,"Need GPS permission for getting your location",1);
  }

  public boolean isGaranted(){
    return isGaranted;
  }

  public boolean isPlayServiceAviable(){
    GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
    int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
    if (resultCode != ConnectionResult.SUCCESS) {
      if (googleApiAvailability.isUserResolvableError(resultCode)) {
        googleApiAvailability.getErrorDialog(activity,resultCode,
            PLAY_SERVICES_REQUEST).show();
      } else {
        showToast("This device is not supported.");
      }
      return false;
    }
    return true;
  }

  private void showToast(String message) {
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
  }
  public void buildGoogleApiClient(){
    googleApiClient = new GoogleApiClient.Builder(context)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API).build();
    final LocationRequest mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(10000);
    mLocationRequest.setFastestInterval(5000);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
        .addLocationRequest(mLocationRequest);

    PendingResult<LocationSettingsResult> result =
        LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

    result.setResultCallback(locationSettingsResult -> {

      final Status status = locationSettingsResult.getStatus();

      switch (status.getStatusCode()) {
        case LocationSettingsStatusCodes.SUCCESS:
          // All location settings are satisfied. The client can initialize location requests here
          //getLocation();
          getLocation();
          break;
        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
          try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            status.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);

          } catch (IntentSender.SendIntentException e) {
            // Ignore the error.
          }
          break;
        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
          break;
      }
    });

  }

  private Location getLocation() {
    if (isGaranted()) {
      try {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        Log.e("getLocation", "LocationHelper" + mLocation);
        return mLocation;
      }
      catch (SecurityException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  /**
   * Method used to connect GoogleApiClient
   */
  public void connectApiClient()
  {
    googleApiClient.connect();
  }

  /**
   * Method used to get the GoogleApiClient
   */
  public GoogleApiClient getGoogleApiCLient()
  {
    return googleApiClient;
  }
  /**
   * Handles the permission results
   */
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    permissionUtils.requestPermissionResults(requestCode,permissions,grantResults);
  }

  /**
   * Handles the activity results
   */
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case REQUEST_CHECK_SETTINGS:
        switch (resultCode) {
          case Activity.RESULT_OK:
            // All required changes were successfully made
            mLocation = getLocation();
            break;
          case Activity.RESULT_CANCELED:
            // The user was asked to change settings, but chose not to
            break;
          default:
            break;
        }
        break;
    }
  }

  @Override
  public void PermissionGranted(int request_code) {
    isGaranted = true;
    Log.i("PERMISSION","GRANTED");
  }

  @Override
  public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
    Log.i("PERMISSION PARTIALLY","GRANTED");
  }

  @Override
  public void PermissionDenied(int request_code) {
    Log.i("PERMISSION","DENIED");
  }

  @Override
  public void NeverAskAgain(int request_code) {
    Log.i("PERMISSION","NEVER ASK AGAIN");
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    getLocation();
  }

  @Override
  public void onConnectionSuspended(int i) {
    googleApiClient.connect();
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }
}
