package com.androidstrike.quizease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private TextInputLayout inputRegNo;
    private TextInputLayout inputPassword;
    private TextInputLayout inputUsername;

    EditText userRegNo, userPassword, userUsername;
    ProgressBar progressBarSignIn;
    Button btnSignIn;

    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference table_user = database.getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        inputRegNo = findViewById(R.id.text_input_regNo_signIn);
        inputPassword = findViewById(R.id.text_input_password_signIn);
        inputUsername = findViewById(R.id.text_input_username_signIn);
        btnSignIn = findViewById(R.id.activity_signin_btn_signin);

        userRegNo = findViewById(R.id.edt_reg_no_signIn);
        userPassword = findViewById(R.id.edt_password_signIn);
        userUsername = findViewById(R.id.edt_username_signIn);

        progressBarSignIn = findViewById(R.id.progress_bar_signin);

//        Firebase init
//        mAuth = FirebaseAuth.getInstance();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = userPassword.getText().toString().trim();
                String regNo = userRegNo.getText().toString().trim();
                String username = userUsername.getText().toString().trim();

                if (regNo.isEmpty()) {
                    inputRegNo.setError("Enter Reg No");
                    inputRegNo.requestFocus();
//            return;
                }else if (password.isEmpty()){
                    inputPassword.setError("Enter Password");
                    inputPassword.requestFocus();
                }else if (username.isEmpty()){
                    inputUsername.setError("Enter Username");
                    inputUsername.requestFocus();
                }else
                inputUsername.setError(null);
                inputRegNo.setError(null);
                inputPassword.setError(null);
                signIn();
            }
        });

    }

//
//    public boolean verify(){
//        if ()
//    }

    public void signIn(){

        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("Please wait...");
        mDialog.show();

        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                check if user exists or not in database
                if (dataSnapshot.child(userUsername.getText().toString()).exists()){

//                    Get user information
                    mDialog.dismiss();
                    User user = dataSnapshot.child(userUsername.getText().toString()).getValue(User.class);
                    user.setUsername(userUsername.getText().toString()); //set username
                    if (user.getPassword() .equals(userPassword.getText().toString())){
                        Intent homeIntent = new Intent(SignIn.this, HomeActivity.class);
                        Common.currentUser = user;
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
//        progressBarSignIn.setVisibility(View.VISIBLE);
//        String regNo =  userRegNo.getText().toString().trim();
//        String password = userPassword.getText().toString().trim();
//        String username = userUsername.getText().toString().trim();
//
//        if (regNo.isEmpty()) {
//            inputRegNo.setError("Enter Reg No");
//            inputRegNo.requestFocus();
//            return;
//        }if (password.isEmpty()){
//            inputPassword.setError("Enter Password");
//            inputPassword.requestFocus();
//        }
//        mAuth.signInWithEmailAndPassword(regNo, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()){
//                    progressBarSignIn.setVisibility(View.INVISIBLE);
//                    userRegNo.setText("");
//                    userPassword.setText("");
//                    Intent intent = new Intent(SignIn.this,HomeActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                }else {
//                    Toast.makeText(SignIn.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    progressBarSignIn.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
    }
}
