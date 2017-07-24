package com.example.admin.restapi.support;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.admin.restapi.LoginActivity;
import com.example.admin.restapi.R;

public class Util {
    private static SharedPreferences sp;

    public static void setname(Context context, String s) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(String.valueOf(R.string.user_name), s);
        edit.apply();
    }

    public static String getname(Context context) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        return sp.getString(String.valueOf(R.string.user_name), "");
    }

    public static void setemail(Context context, String s) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(String.valueOf(R.string.user_email), s);
        edit.apply();
    }

    public static String getemail(Context context) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        return sp.getString(String.valueOf(R.string.user_email), "");
    }

    public static void setauthkey(Context context, String s) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(String.valueOf(R.string.user_auth_key), s);
        edit.apply();
    }

    public static String getauthkey(Context context) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        return sp.getString(String.valueOf(R.string.user_auth_key), "");
    }


    public static void setcreatedat(Context context, String s) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(String.valueOf(R.string.user_created_date), s);
        edit.apply();
    }

    public static String getcreatedat(Context context) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        return sp.getString(String.valueOf(R.string.user_created_date), "");
    }

    public static void logout(Context context) {
        sp = context.getSharedPreferences(String.valueOf(R.string.shared_database_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear().apply();
        context.startActivity(new Intent(context, LoginActivity.class));
    }
    public static void exit(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



}
