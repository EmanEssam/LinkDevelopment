package com.linkdevelopment.www.linkdevelopment.utils;

import com.linkdevelopment.www.linkdevelopment.network.ConnectivityReceiver;

/**
 * Created by Eman on 20/07/2017.
 */

public class Application extends android.app.Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized Application getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
