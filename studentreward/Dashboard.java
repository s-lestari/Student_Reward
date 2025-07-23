package com.example.studentreward;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.studentreward.databinding.ActivityDashboardBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String role = getIntent().getStringExtra("role");
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (role.equals("lecturer")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();

            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.nav_point) {
                    selectedFragment = new PointFragment();
                } else if (item.getItemId() == R.id.nav_leaderboard) {
                    selectedFragment = new LeaderboardFragment();
                } else if (item.getItemId() == R.id.nav_reward) {
                    selectedFragment = new RewardFragment();
                }

                return loadFragment(selectedFragment);
            });
        } else if (role.equals("student")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeStudentFragment())
                    .commit();

            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new HomeStudentFragment();
                } else if (item.getItemId() == R.id.nav_point) {
                    selectedFragment = new PointStudentFragment();
                } else if (item.getItemId() == R.id.nav_leaderboard) {
                    selectedFragment = new LeaderboardFragment();
                } else if (item.getItemId() == R.id.nav_reward) {
                    selectedFragment = new RewardStudentFragment();
                }

                return loadFragment(selectedFragment);

            });
        }
    }
    private boolean loadFragment(Fragment selectedFragment) {
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
            return true;
        }
        return false;
    }


}


