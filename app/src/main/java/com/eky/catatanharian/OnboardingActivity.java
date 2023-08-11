package com.eky.catatanharian;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Eky Nugraha Heriawan
// 10120066
// IF-2
public class OnboardingActivity extends AppCompatActivity {
    ViewPager slideViewPager;
    LinearLayout dotIndicator;
    Button backButton, nextButton;
    TextView[] dots;
    OnboardingAdapter onboardingAdapter;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            backButton.setVisibility(position > 0 ? View.VISIBLE : View.INVISIBLE);
            nextButton.setText(position == 2 ? "Finish" : "Next");
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        getSupportActionBar().hide();

        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        slideViewPager = findViewById(R.id.slideViewPager);
        dotIndicator = findViewById(R.id.dotIndicator);

        onboardingAdapter = new OnboardingAdapter(this);
        slideViewPager.setAdapter(onboardingAdapter);

        setDotIndicator(0);
        slideViewPager.addOnPageChangeListener(viewPagerListener);

        backButton.setOnClickListener(v -> {
            if (getItem(0) > 0) {
                slideViewPager.setCurrentItem(getItem(-1), true);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (getItem(0) < 2)
                slideViewPager.setCurrentItem(getItem(1), true);
            else {
                Intent i = new Intent(OnboardingActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in, redirect to main activity
                    redirectToMain();
                }
            }
        };

        // Register the AuthStateListener
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setDotIndicator(int position) {
        dotIndicator.removeAllViews();
        dots = new TextView[3];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].setText(Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY));
            }
            dots[i].setTextSize(35);
            dots[i].setTextColor(ContextCompat.getColor(this, R.color.grey));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(ContextCompat.getColor(this, R.color.primary));
    }

    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }

    // This method is called when sign-up is successful
    private void redirectToMain() {
        Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
