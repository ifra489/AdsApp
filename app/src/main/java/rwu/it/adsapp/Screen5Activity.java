package rwu.it.adsapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Screen5Activity extends AppCompatActivity {

    private AdView adViewBanner;
    private Button btnFinish, btnWatchRewardAd;

    private RewardedAd rewardedAd;
    private final String REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917"; // Test Rewarded Ad ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);

        adViewBanner = findViewById(R.id.adViewBanner);
        btnFinish = findViewById(R.id.btnFinish);
        btnWatchRewardAd = findViewById(R.id.btnWatchRewardAd);

        // Load banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);

        // Load rewarded ad
        loadRewardedAd();

        btnFinish.setOnClickListener(v -> finishAffinity());  // Close app

        btnWatchRewardAd.setOnClickListener(v -> {
            if (rewardedAd != null) {
                rewardedAd.show(Screen5Activity.this, rewardItem -> {
                    // Reward the user here
                    Toast.makeText(Screen5Activity.this,
                            "You earned: " + rewardItem.getAmount() + " " + rewardItem.getType(),
                            Toast.LENGTH_LONG).show();
                    // You can add logic to unlock features or give points here
                    // Reload ad for next time
                    loadRewardedAd();
                });
            } else {
                Toast.makeText(Screen5Activity.this, "Ad not loaded yet, please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, REWARDED_AD_UNIT_ID, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd ad) {
                rewardedAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                rewardedAd = null;
            }
        });
    }
}