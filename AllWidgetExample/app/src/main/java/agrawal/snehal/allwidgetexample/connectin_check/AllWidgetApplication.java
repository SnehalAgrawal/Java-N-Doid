package agrawal.snehal.allwidgetexample.connectin_check;

import android.app.Application;
import android.text.TextUtils;


public class AllWidgetApplication extends Application {
    private static AllWidgetApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AllWidgetApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
