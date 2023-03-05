package com.example.qlbdt.fActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.qlbdt.R;
import com.example.qlbdt.fEnum.EStatusFragment;
import com.example.qlbdt.fFragment.AccountFragment;
import com.example.qlbdt.fFragment.HistoryFragment;
import com.example.qlbdt.fFragment.HomeFragment;
import com.example.qlbdt.fFragment.SearchFragment;
import com.example.qlbdt.fFragment.SupportFragment;
import com.example.qlbdt.fFragment.TutorialFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private int FRAGMENT_CURRENT = EStatusFragment.FRAGMENT_HOME.getStatusFragment();

    private void init(){
        drawerLayout = findViewById(R.id.nav_drawer);
        toolbar = findViewById(R.id.nav_toolbar);
        navigationView = findViewById(R.id.nav_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.nav_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        ReplaceFragment(new HomeFragment());
        navigationView.getMenu().findItem(R.id.nav_menu_item_home).setChecked(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_menu_item_home:
                        if (FRAGMENT_CURRENT != EStatusFragment.FRAGMENT_HOME.getStatusFragment()){
                            ReplaceFragment(new HomeFragment());
                            FRAGMENT_CURRENT = EStatusFragment.FRAGMENT_HOME.getStatusFragment();
                        }
                        break;
                    case R.id.nav_menu_item_search:
                        if (FRAGMENT_CURRENT != EStatusFragment.FRAGMENT_SEARCH.getStatusFragment()){
                            ReplaceFragment(new SearchFragment());
                            FRAGMENT_CURRENT = EStatusFragment.FRAGMENT_SEARCH.getStatusFragment();
                        }
                        break;
                    case R.id.nav_menu_item_account:
                        if (FRAGMENT_CURRENT != EStatusFragment.FRAGMENT_ACCOUNT.getStatusFragment()){
                            ReplaceFragment(new AccountFragment());
                            FRAGMENT_CURRENT = EStatusFragment.FRAGMENT_ACCOUNT.getStatusFragment();
                        }
                        break;
                    case R.id.nav_menu_item_history:
                        if (FRAGMENT_CURRENT != EStatusFragment.FRAGMENT_HISTORY.getStatusFragment()){
                            ReplaceFragment(new HistoryFragment());
                            FRAGMENT_CURRENT = EStatusFragment.FRAGMENT_HISTORY.getStatusFragment();
                        }
                        break;
                    case R.id.nav_menu_item_support:
                        if (FRAGMENT_CURRENT != EStatusFragment.FRAGMENT_SUPPORT.getStatusFragment()){
                            ReplaceFragment(new SupportFragment());
                            FRAGMENT_CURRENT = EStatusFragment.FRAGMENT_SUPPORT.getStatusFragment();
                        }
                        break;
                    case R.id.nav_menu_item_tutorial:
                        if (FRAGMENT_CURRENT != EStatusFragment.FRAGMENT_TUTORIAL.getStatusFragment()){
                            ReplaceFragment(new TutorialFragment());
                            FRAGMENT_CURRENT = EStatusFragment.FRAGMENT_TUTORIAL.getStatusFragment();
                        }
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}