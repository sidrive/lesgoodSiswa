package com.lesgood.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.lesgood.app.R;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Agus on 5/10/17.
 */

public class Utils {
    public static String getRupiah(int amount){
        String angka = Integer.toString(amount);
        NumberFormat rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        String rupiah = rupiahFormat.format(Double.parseDouble(angka));
        return  "Rp."+rupiah;
    }

    public static double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        return Double.longBitsToDouble(prefs.getLong(key, Double.doubleToLongBits(defaultValue)));
    }

    public static SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void showToas(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void setAvatar(Context context, String imgUrl, ImageView img){
        if (imgUrl !=null){
            if (!imgUrl.equalsIgnoreCase("NOT")){
                Glide.with(context).load(imgUrl).placeholder(R.color.colorSoft).dontAnimate().into(img);
            }
        }
        else {
            Glide.with(context).load(R.drawable.ic_account_circle).placeholder(R.color.colorSoft).dontAnimate().into(img);
        }
    }
    public static String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, ''yyyy");
        return format.format(date);
    }
    public static String longToString(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat format = new SimpleDateFormat("kk:mm");
        return format.format(date);
    }
    public static int getNumberDay(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.get(Calendar.DAY_OF_WEEK);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    public static String longToDay(long date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        SimpleDateFormat format = new SimpleDateFormat("EEE");
        return format.format(date);
    }
    public static int getHours(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.HOUR);
    }
    public static int getMinute(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MINUTE);
    }
    public static String dayFormated(String day){
        String dayFormated;
        if (day.equals("Sun")){
            dayFormated = "Minggu";
        }else if (day.equals("Mon")){
            dayFormated = "Senen";
        }
        else if (day.equals("Tue")){
            dayFormated = "Selasa";
        }
        else if (day.equals("Wed")){
            dayFormated = "Rabu";
        }
        else if (day.equals("Thu")){
            dayFormated = "Kamis";
        }
        else if (day.equals("Fri")){
            dayFormated = "Jum'at";
        }
        else {
            dayFormated = "Sabtu";
        }
        return dayFormated;
    }
    public static int converStringDayToInt(String day){
        int daySelected = 0;
        if (day.equals("Sun") || day.equals("Mig")){
            daySelected = 1;
        }else if (day.equals("Mon") || day.equals("Sen") ){
            daySelected = 2;
        }
        else if (day.equals("Tue") || day.equals("Sel")){
            daySelected = 3;
        }
        else if (day.equals("Wed") || day.equals("Rab")){
            daySelected = 4;
        }
        else if (day.equals("Thu") || day.equals("Kam")){
            daySelected = 5;
        }
        else if (day.equals("Fri") || day.equals("Jum")){
            daySelected = 6;
        }
        else if (day.equals("Sat") || day.equals("Sab")){
            daySelected = 7;
        }
        return  daySelected;
    }
}
