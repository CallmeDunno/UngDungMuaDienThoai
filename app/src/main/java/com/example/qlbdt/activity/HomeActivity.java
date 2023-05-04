package com.example.qlbdt.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.qlbdt.R;
import com.example.qlbdt.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.navToolbar);

        initView();
        initAction();
    }

    private void initAction() {
        binding.navView.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(menuItem -> {
            Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_SHORT).show();
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
            edit.remove("username");
            edit.apply();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
        });
        dialog.setNegativeButton("No", (dialogInterface, i) -> {});
        dialog.show();

    }

    private void initView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, binding.navDrawer, binding.navToolbar, R.string.open_drawer, R.string.close_drawer);
        binding.navDrawer.addDrawerListener(toggle);
        toggle.syncState();

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