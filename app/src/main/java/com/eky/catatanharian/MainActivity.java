package com.eky.catatanharian;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

// Eky Nugraha Heriawan
// 10120066
// IF-2
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        final ProfileFragment profileFragment = new ProfileFragment();
        final CatatanFragment catatanFragment = new CatatanFragment();
        final AppInfoFragment appInfoFragment = new AppInfoFragment();

        loadFragment(profileFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_profile:
                    loadFragment(profileFragment);
                    return true;
                case R.id.menu_daily_notes:
                    loadFragment(catatanFragment);
                    return true;
                case R.id.menu_app_info:
                    loadFragment(appInfoFragment);
                    return true;
                case R.id.menu_logout:
                    signOut();
                    return true;
            }
            return false;
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show();
        redirectToLogin(); // Redirect to the login page after logout
    }

    private void redirectToLogin() {
        Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }
}