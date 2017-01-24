package br.com.rafaelfioretti.facebookdemo;
import android.app.Application;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by rafaelfioretti on 1/23/17.
 */


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}