package rwu.it.adsapp;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Screen1Activity extends AppCompatActivity {

    private AdView adViewBanner;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen1);

        adViewBanner = findViewById(R.id.adViewBanner);
        btnNext = findViewById(R.id.btnNext);

        // Load banner ad
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);

        // Next button click - open Screen2Activity
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Screen1Activity.this, Screen2Activity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onPause() {
        if (adViewBanner != null) {
            adViewBanner.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adViewBanner != null) {
            adViewBanner.resume();
        }
    }

    @Override
    protected void onDestroy() {
        if (adViewBanner != null) {
            adViewBanner.destroy();
        }
        super.onDestroy();
    }
}