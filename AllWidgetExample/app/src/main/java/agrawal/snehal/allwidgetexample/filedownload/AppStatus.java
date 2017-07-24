package agrawal.snehal.allwidgetexample.filedownload;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;

public class AppStatus {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 234;
    private static final int MY_PERMISSIONS_REQUEST_INTERNET_INTENT = 345;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 456;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA_INTENT = 567;
    private static final int MY_PERMISSIONS_REQUEST_NETWORK_STATE_INTENT = 678;
    @SuppressLint({"StaticFieldLeak"})
    private static AppStatus instance = new AppStatus();

    public static AppStatus getInstance(Context ctx) {
        ctx.getApplicationContext();
        return instance;
    }

    public static boolean isConnected(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null) && (networkInfo.isConnected());
    }

    public static boolean isWifiConnected(@NonNull Context context) {
        return isConnected(context, 1);
    }

    public static boolean isMobileConnected(@NonNull Context context) {
        return isConnected(context, 0);
    }

    private static boolean isConnected(@NonNull Context context, int type) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (VERSION.SDK_INT < 21) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(type);
            return (networkInfo != null) && (networkInfo.isConnected());
        }
        return isConnected(connMgr, type);
    }

    @TargetApi(21)
    private static boolean isConnected(@NonNull ConnectivityManager connMgr, int type) {
        Network[] networks = connMgr.getAllNetworks();
        for (Network mNetwork : networks) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(mNetwork);
            if ((networkInfo != null) && (networkInfo.getType() == type) && (networkInfo.isConnected())) {
                return true;
            }
        }
        return false;
    }


    public static boolean checkCallPermission(final Context context) {
        int currentAPIVersion = VERSION.SDK_INT;
        if (currentAPIVersion >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CALL_PHONE)) {
                    Builder alertBuilder = new Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission requested");
                    alertBuilder.setMessage("Call permission is necessary");
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }
                    });

                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }
                return false;
            }
            return true;
        }
        return true;
    }


    public static boolean checkReadPermission(final Context context) {
        int currentAPIVersion = VERSION.SDK_INT;
        if (currentAPIVersion >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Builder alertBuilder = new Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission requested");
                    alertBuilder.setMessage("Read storage permission is necessary");
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });

                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            }
            return true;
        }
        return true;
    }


    public static boolean checkWritePermission(final Context context) {
        int currentAPIVersion = VERSION.SDK_INT;
        if (currentAPIVersion >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Builder alertBuilder = new Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission requested");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });

                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            }
            return true;
        }
        return true;
    }


    public static boolean checkCameraPermission(final Context context) {
        int currentAPIVersion = VERSION.SDK_INT;
        if (currentAPIVersion >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    Builder alertBuilder = new Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission requested");
                    alertBuilder.setMessage("Camera permission is necessary");
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{"android.permission.CAMERA"}, MY_PERMISSIONS_REQUEST_CAMERA_INTENT);
                        }
                    });

                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA_INTENT);
                }
                return false;
            }
            return true;
        }
        return true;
    }


    public static boolean checkInternetPermission(final Context context) {
        int currentAPIVersion = VERSION.SDK_INT;
        if (currentAPIVersion >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.INTERNET)) {
                    Builder alertBuilder = new Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission requested");
                    alertBuilder.setMessage("Internet access is necessary");
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET_INTENT);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET_INTENT);
                }
                return false;
            }
            return true;
        }
        return true;
    }


    public static boolean checkNetworkState(final Context context) {
        int currentAPIVersion = VERSION.SDK_INT;
        if (currentAPIVersion >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) != 0) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_NETWORK_STATE)) {
                    Builder alertBuilder = new Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission requested");
                    alertBuilder.setMessage("Access network state is necessary");
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, MY_PERMISSIONS_REQUEST_NETWORK_STATE_INTENT);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, MY_PERMISSIONS_REQUEST_NETWORK_STATE_INTENT);
                }
                return false;
            }
            return true;
        }
        return true;
    }
}
