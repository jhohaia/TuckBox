package com.example.tuckbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Splash extends AppCompatActivity {
//    TextView title,description;
    LottieAnimationView lottieAnimationView;
    Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
          Intent i = new Intent(Splash.this,MainActivity.class);
          startActivity(i);
          finish();
//        title = findViewById(R.id.title);
//        description = findViewById(R.id.description);
          lottieAnimationView = findViewById(R.id.carousel);
//        title.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
//        description.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
          lottieAnimationView.animate().alpha(-1400).setDuration(1000).setStartDelay(4000);
            }
        },5500);


    }
}