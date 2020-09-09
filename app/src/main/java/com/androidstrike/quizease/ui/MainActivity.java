package com.androidstrike.quizease.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidstrike.quizease.R;

public class MainActivity extends AppCompatActivity {

    private long backPressedTimeStartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sign up only occurs once after app launch
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        if (firstStart){
            signUp();
        }else {
            Intent signInIntent = new Intent(MainActivity.this, SignIn.class);
            startActivity(signInIntent);

        }
            }

    public void signUp(){

         Intent signUpIntent = new Intent(MainActivity.this, SignUp.class);
              startActivity(signUpIntent);

         SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
             SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstStart", false);
                editor.apply();
            }

    @Override
    public void onBackPressed() {
        if (backPressedTimeStartActivity + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Press Back Again to Finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTimeStartActivity = System.currentTimeMillis();
        }
}
