package rwu.it.adsapp;

import android.app.Application;

public class MyApp extends Application {

    private AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize AppOpenManager to manage app open ads
        appOpenManager = new AppOpenManager(this);
    }

    public AppOpenManager getAppOpenManager() {
        return appOpenManager;
    }
}