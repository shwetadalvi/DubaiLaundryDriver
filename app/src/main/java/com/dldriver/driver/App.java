package com.dldriver.driver;

import android.app.Application;

import com.dldriver.driver.utils.ConnectionReceiver;
import com.mazenrashed.printooth.Printooth;

public class App extends Application {
    private static App mInstance;

    @Override public void onCreate() {
        super.onCreate();
        Printooth.INSTANCE.init(this);
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }
    public static synchronized App getInstance() {
        return mInstance;
    }

}
