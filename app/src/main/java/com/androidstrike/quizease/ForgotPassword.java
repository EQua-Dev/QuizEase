package com.androidstrike.quizease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidstrike.quizease.ui.SignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private TextInputLayout inputEmail;
    private EditText uemail;
    private Button btnPassReset;
    ProgressBar progressBar;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        inputEmail = findViewById(R.id.text_input_email_p_reset);
        uemail = findViewById(R.id.edt_email_p_reset);
        progressBar = findViewById(R.id.progressbar);
        btnPassReset = findViewById(R.id.button_reset_password);

        btnPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = uemail.getText().toString().trim();

                if (email.isEmpty()) {
                    inputEmail.setError("Enter Reg No");
                    inputEmail.requestFocus();
//            return;
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    inputEmail.setError("Valid email required");
                    inputEmail.requestFocus();
                }
                else
                    inputEmail.setError(null);
                resetPassword();
            }
        });

    }

    private void resetPassword() {

        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.INVISIBLE);
                if (task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check Your Email", Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                Intent resetDoneIntent = new Intent(ForgotPassword.this, SignIn.class);
                startActivity(resetDoneIntent);
            }
        });
    }

}
