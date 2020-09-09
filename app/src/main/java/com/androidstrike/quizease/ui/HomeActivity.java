package com.androidstrike.quizease.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.R;
import com.androidstrike.quizease.SettingsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static HomeActivity homeActivity = new HomeActivity();

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference registeredCourses;

    TextView txtFullName, txtRegNo;

    private AppBarConfiguration mAppBarConfiguration;

    DrawerLayout drawer;

    ActionBar actionBar;
    Toolbar toolbar;

//    User user = new User();
//    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//    String userEmail = firebaseUser.getEmail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(R.style.DarkTheme);
        }else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_landing);

        setUpSharedPreferences();

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        NavigationUI.setupWithNavController(navigationView,navController);
        NavigationUI.setupActionBarWithNavController(this, navController, drawer);


//        set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName= headerView.findViewById(R.id.txtFullName);
        txtRegNo = headerView.findViewById(R.id.txtRegNo);

        txtFullName.setText(Common.currentUser.getEmail());
//        txtRegNo.setText(user.getRegNo());
        txtRegNo.setText(Common.currentUser.getRegNo());

        navigationView = findViewById(R.id.nav_view);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(
                Navigation.findNavController(this, R.id.fragment_container),drawer
        );
    }

    private void setUpSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void setDarkMode(boolean darkMode){

        if (darkMode){
            // checks if the current current mode is night and sets it to day mode
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
//            DarkModeConfig
        }else{
            //sets it to night mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        finish();
        // sets the activity to restart by creating an intent to itself
        startActivity(new Intent(HomeActivity.this, HomeActivity.this.getClass()));
    }

    @Override
    public void onBackPressed() {
//        if the back button is pressed and the navigation drawer is open, it closes the drawer as opposed to closing the activity
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
//            if the back button is pressed and the navigation drawer is not open, the activity is closed
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_dark_mode) {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                //sets it to night mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

            finish();
            // sets the activity to restart by creating an intent to itself
            startActivity(new Intent(HomeActivity.this, HomeActivity.this.getClass()));
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("darkMode")){
            setDarkMode(sharedPreferences.getBoolean("darkMode", true));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
