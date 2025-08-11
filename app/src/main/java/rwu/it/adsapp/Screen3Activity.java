package rwu.it.adsapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import rwu.it.adsapp.Screen4Activity;

public class Screen3Activity extends AppCompatActivity {

    private AdView adViewBanner;
    private Button btnNext;

    private InterstitialAd interstitialAd;

    private final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712"; // Correct Interstitial Ad Unit ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        adViewBanner = findViewById(R.id.adViewBanner);
        btnNext = findViewById(R.id.btnNext);

        // Load banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);

        // Load interstitial ad
        loadInterstitialAd();

        btnNext.setOnClickListener(v -> {
            if (interstitialAd != null) {
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d("Ads", "Interstitial Ad dismissed.");
                        interstitialAd = null;
                        openScreen4();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        Log.e("Ads", "Interstitial Ad failed to show: " + adError.getMessage());
                        interstitialAd = null;
                        openScreen4();
                    }
                });
                interstitialAd.show(Screen3Activity.this);
            } else {
                Log.d("Ads", "Interstitial Ad not ready, moving to next screen.");
                // Ad not loaded, proceed immediately
                openScreen4();
            }
        });
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,
                INTERSTITIAL_AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd ad) {
                        Log.d("Ads", "Interstitial Ad loaded successfully.");
                        interstitialAd = ad;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.e("Ads", "Interstitial Ad failed to load: " + loadAdError.getMessage());
                        interstitialAd = null;
                    }
                });
    }

    private void openScreen4() {
        Intent intent = new Intent(Screen3Activity.this, Screen4Activity.class);
        startActivity(intent);
        finish();
    }
}