package com.example.qlbdt.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.qlbdt.R;
import com.example.qlbdt.database.UserDatabase;
import com.example.qlbdt.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private NavController navController;
    private UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleLogin();

        setSupportActionBar(binding.navToolbar);

        initView();
        initAction();
    }

    private void handleLogin() {
        userDatabase = new UserDatabase(this);
        if (userDatabase.getCurrentUserName() == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        } else {
            String currentUser = SplashScreenActivity.userDatabase.getString("currentUser", null);
            Toast.makeText(HomeActivity.this, "Welcome, " + currentUser, Toast.LENGTH_SHORT).show();
            Log.d("oam", userDatabase.getCurrentUserName());
        }
    }

    private void initAction() {
        binding.navView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
            showExitDialog();
            return false;
        });
    }

    private void showExitDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setMessage("Are you sure?");
        dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
            SharedPreferences.Editor edit = SplashScreenActivity.userDatabase.edit();
            edit.remove("currentUser");
            edit.apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
        dialog.setNegativeButton("No", (dialogInterface, i) -> {
        });
        dialog.show();
    }

    private void initView() {
        //region  new
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, binding.navDrawer);
        //endregion
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, binding.navDrawer);
    }

    @Override
    public void onBackPressed() {
        if (binding.navDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.navDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}