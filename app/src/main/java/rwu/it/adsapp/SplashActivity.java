package rwu.it.adsapp;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class SplashActivity extends AppCompatActivity {

    private AppOpenAd appOpenAd;
    private boolean isAdShowing = false;

    // Test App Open Ad unit ID (replace with your own later)
    private String appOpenAdUnitId = "ca-app-pub-3940256099942544/9257395921";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MobileAds.initialize(this,initializationStatus -> {});
        loadAppOpenAd();
    }

    private void loadAppOpenAd() {
        AdRequest request = new AdRequest.Builder().build();

        AppOpenAd.load(
                this,
                appOpenAdUnitId,
                request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        appOpenAd = ad;
                        showAdIfAvailable();
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        openNextActivity();
                    }
                });
    }

    private void showAdIfAvailable() {
        if (appOpenAd != null && !isAdShowing) {
            appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    isAdShowing = false;
                    openNextActivity();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    isAdShowing = false;
                    openNextActivity();
                }
            });
            isAdShowing = true;
            appOpenAd.show(this);
        } else {
            openNextActivity();
        }
    }

    private void openNextActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

