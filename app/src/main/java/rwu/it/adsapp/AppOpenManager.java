package rwu.it.adsapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class AppOpenManager implements Application.ActivityLifecycleCallbacks {

    private AppOpenAd appOpenAd = null;
    private Activity currentActivity;
    private boolean isShowingAd = false;
    private final Application myApplication;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/9257395921"; // Your app open ad unit id

    public AppOpenManager(Application application) {
        this.myApplication = application;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        fetchAd();
    }

    public void fetchAd() {
        if (isAdAvailable()) return;

        AdRequest request = new AdRequest.Builder().build();

        AppOpenAd.load(
                myApplication,
                AD_UNIT_ID,
                request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AppOpenAd ad) {
                        appOpenAd = ad;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle failure if you want
                    }
                }
        );
    }

    public boolean isAdAvailable() {
        return appOpenAd != null;
    }

    public void showAdIfAvailable(final OnShowAdCompleteListener onShowAdCompleteListener) {
        if (!isShowingAd && isAdAvailable() && currentActivity != null) {
            isShowingAd = true;
            appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                    onShowAdCompleteListener.onShowAdComplete();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    appOpenAd = null;
                    isShowingAd = false;
                    onShowAdCompleteListener.onShowAdComplete();
                }
            });
            appOpenAd.show(currentActivity);
        } else {
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        currentActivity = null;
    }

    // Other lifecycle callback methods (empty implementation)
    @Override public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {}
    @Override public void onActivityResumed(@NonNull Activity activity) {}
    @Override public void onActivityPaused(@NonNull Activity activity) {}
    @Override public void onActivitySaveInstanceState(@NonNull Activity activity, Bundle outState) {}
    @Override public void onActivityDestroyed(@NonNull Activity activity) {}

    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }
}