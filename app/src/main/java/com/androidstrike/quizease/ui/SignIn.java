package com.androidstrike.quizease.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.ForgotPassword;
import com.androidstrike.quizease.Model.User;
import com.androidstrike.quizease.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private TextInputLayout inputRegNo;
    private TextInputLayout inputPassword;
    private TextInputLayout inputUsername;

    EditText userRegNo, userPassword;
    ProgressBar progressBarSignIn;
    Button btnSignIn;
    TextView forgotPassword, userUsername;;
    String password, email, regNo;

    SharedPreferences preferences;


    //    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        inputRegNo = findViewById(R.id.text_input_regNo_signIn);
        inputPassword = findViewById(R.id.text_input_password_signIn);
//        inputUsername = findViewById(R.id.text_input_username_signIn);
        btnSignIn = findViewById(R.id.activity_signin_btn_signin);


//        userRegNo = findViewById(R.id.edt_reg_no_signIn);
        userPassword = findViewById(R.id.edt_password_signIn);
        userUsername = findViewById(R.id.edt_username_signIn);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        email = preferences.getString(SignUp.prefUser, "");

        userUsername.append("Welcome, "+email);

        progressBarSignIn = findViewById(R.id.progress_bar_signin);

        forgotPassword = findViewById(R.id.tv_forgot_password);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetIntent = new Intent(SignIn.this, ForgotPassword.class);
                startActivity(resetIntent);
            }
        });

//        Firebase init
//        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = userPassword.getText().toString().trim();
//                regNo = userRegNo.getText().toString().trim();
//                email = userUsername.getText().toString().trim();


//                if (regNo.isEmpty()) {
//                    inputRegNo.setError("Enter Reg No");
//                    inputRegNo.requestFocus();
////            return;
//                }else
                    if (password.isEmpty()){
                    inputPassword.setError("Enter Password");
                    inputPassword.requestFocus();
                }
//                    else if (email.isEmpty()){
//                    inputUsername.setError("Enter Username");
//                    inputUsername.requestFocus();
//                }else
//                inputUsername.setError(null);
//                inputRegNo.setError(null);
                inputPassword.setError(null);
                signIn();
            }
        });

    }


    public void signIn(){

        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                check if user exists or not in database
                if (dataSnapshot.child(email).exists()){

//                    Get user information
                    mDialog.dismiss();
                    User user = dataSnapshot.child(email).getValue(User.class);
//                    user.setUsername(userUsername.getText().toString()); //set username
                    assert user != null;
                    if (user.getPassword() .equals(userPassword.getText().toString())){
                        Intent homeIntent = new Intent(SignIn.this, HomeActivity.class);
                        Common.currentUser = user;
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(homeIntent);

                        finish();
                    }else{
                        Toast.makeText(SignIn.this, "Wrong Password or Reg No", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "User Does Not Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
