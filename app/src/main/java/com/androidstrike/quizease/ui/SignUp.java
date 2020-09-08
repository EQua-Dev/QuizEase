package com.androidstrike.quizease.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.Model.User;
import com.androidstrike.quizease.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    // "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private TextInputLayout email;
    private TextInputLayout userName;
    private TextInputLayout password;
    private TextInputLayout confirmPassword;
    private TextInputLayout regNo;
    private ProgressBar progressBarSignUp;
    public static final String prefUser = "username";

    EditText uemail, uuserName, upassword, uconfirmPassword, uregNo;

    Button btnSignUp;

    String emailInput;
    String usernameInput;
    String regNoInput;
    String passwordInput;
    String passwordConfirmInput;
    String passwordforConfirm;

    private FirebaseAuth mAuth;

    FirebaseAnalytics mFirebaseAnalytics;
    FirebaseDatabase database;
    DatabaseReference table_user;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

//        Firebase Init
//        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("Users");

        email = findViewById(R.id.text_input_email);
        userName = findViewById(R.id.text_input_username);
        password = findViewById(R.id.text_input_password);
        confirmPassword = findViewById(R.id.text_input_confirm_password);
        regNo = findViewById(R.id.text_input_regNo);
        btnSignUp = findViewById(R.id._activity_signup_btn_signup);
        progressBarSignUp = findViewById(R.id.progress_bar_signup);

        uemail = findViewById(R.id.edt_email);
        uuserName = findViewById(R.id.edt_username);
        uregNo = findViewById(R.id.edt_reg_no);
        upassword = findViewById(R.id.edt_password);
        uconfirmPassword = findViewById(R.id.edt_confirm_password);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailInput = email.getEditText().getText().toString().trim();
                usernameInput = userName.getEditText().getText().toString().trim();
                regNoInput = regNo.getEditText().getText().toString().trim();
                passwordInput = password.getEditText().getText().toString().trim();
                passwordConfirmInput = confirmPassword.getEditText().getText().toString().trim();




                if (emailInput.isEmpty()) {
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    email.setError("Field can't be empty");
                    uuserName.setText("");
                    email.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    email.setError("Please enter a valid email address");
                    uemail.setText("");
                    email.requestFocus();
                 }
                else if (usernameInput.isEmpty()) {
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    userName.setError("Field can't be empty");
                    uuserName.setText("");
                    userName.requestFocus();
                } else if (usernameInput.length() > 15) {
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    userName.setError("Username too long");
                    uuserName.setText("");
                    userName.requestFocus();
                 }else if (regNoInput.isEmpty()) {
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    regNo.setError("Field can't be empty");
                    uregNo.setText("");
                    regNo.requestFocus();
                } else if (regNoInput.length() > 15) {
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    regNo.setError("Reg No is not Valid");
                    uregNo.setText("");
                    regNo.requestFocus();
                } else if (passwordInput.isEmpty()) {
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    password.setError("Field can't be empty");
                    upassword.setText("");
                    password.requestFocus();
                } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    password.setError("Password too weak.Please add at least one Uppercase letter, lowercase letter and one number");
                    upassword.setText("");
                    password.requestFocus();
                }else if (passwordInput.length() < 8){
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    password.setError("Password must be upto 8 characters");
                    upassword.setText("");
                    password.requestFocus();
                }
                else if (passwordConfirmInput.isEmpty()){
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    confirmPassword.setError("Field can't be empty");
                    uconfirmPassword.setText("");
                    confirmPassword.requestFocus();
//                    password.setError(null);

                }else if (!passwordConfirmInput.equals(passwordInput)){
//            progressBarSignUp.setVisibility(View.INVISIBLE);
                    confirmPassword.setError("Password does not match");
                    uconfirmPassword.setText("");
                    confirmPassword.requestFocus();
//                    confirmPassword.setError(null);
                }else{
                    email.setError(null);
                    userName.setError(null);
                    regNo.setError(null);
                    password.setError(null);
                    confirmPassword.setError(null);
                    confirmInput();
                }

            }
        });

    }

    public void confirmInput() {
//        progressBarSignUp.setVisibility(View.VISIBLE);

//        if (!validateEmail() || !validateUsername() || !validatePassword() || !validateConfirmPassword() || !validateRegNo()) {

            final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
            mDialog.setMessage("Please wait...");
            mDialog.show();

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Check if user is already registered on the phone
                    if (dataSnapshot.child(uuserName.getText().toString()).exists() || dataSnapshot.child(uuserName.getText().toString()).exists()){
                        mDialog.dismiss();
                        Toast.makeText(SignUp.this, "User is already registered", Toast.LENGTH_LONG).show();

                    }else {
                        mDialog.dismiss();
                        User user = new User(uemail.getText().toString(), upassword.getText().toString(), uregNo.getText().toString());
                        table_user.child(uuserName.getText().toString()).setValue(user);

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(prefUser,"");
                        editor.apply();

                        Common.onlyUser = preferences.getString(prefUser, "");

                        Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        Intent afterSignUp = new Intent(SignUp.this, SignIn.class);
                        afterSignUp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        afterSignUp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(afterSignUp);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}

