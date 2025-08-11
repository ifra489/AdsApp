package rwu.it.adsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Screen4Activity extends AppCompatActivity {

    private AdView adViewBanner;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);

        adViewBanner = findViewById(R.id.adViewBanner);
        btnNext = findViewById(R.id.btnNext);

        AdRequest adRequest = new AdRequest.Builder().build();
        adViewBanner.loadAd(adRequest);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Screen4Activity.this, Screen5Activity.class);
            startActivity(intent);
        });
    }
}