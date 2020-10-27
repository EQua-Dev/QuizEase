package com.androidstrike.quizease.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.Model.UpdateProfile;
import com.androidstrike.quizease.R;
import com.androidstrike.quizease.ui.SignUp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment{

    private ImageView ivEdtProfileImage, profileImage;
    private TextView txtEmail, txtPhone, txtBoys;
    private FloatingActionButton fab;

    FirebaseDatabase database;
    DatabaseReference table_users;


    String newEmail;
    String newPhone;

    ImageView iv;
    private static final int TAKE_PICTURE = 1;
    private static final int PICK_PICTURE = 100;
    private Uri imageUri;
    public static final String SHARED_PREFS = "sharedPrefs";

    public static final String KEY_EMAIL = "keyEmail";
    public static final String KEY_PHONE = "keyPhone";

    File photo;

    String [] addImage = {"Take a photo", "Select from Gallery"};

    private String emailAddressEdited, phoneNumberEdited;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_profile, container, false);
        ivEdtProfileImage = view.findViewById(R.id.image_change);
//        ivEditPhone = view.findViewById(R.id.edit_phone_profile);
        txtBoys = view.findViewById(R.id.edit_phone_profile);
        txtEmail = view.findViewById(R.id.txt_email_profile);
        txtPhone = view.findViewById(R.id.txt_phone_profile);
        profileImage = view.findViewById(R.id.profile_image);
        fab = view.findViewById(R.id.fab);
        profileImage = view.findViewById(R.id.profile_image);


        database = FirebaseDatabase.getInstance();
        table_users = database.getReference("Contacts");

        setEmailAddress(newEmail);
        setPhoneNumber(newPhone);

//        fab.setVisibility(View.INVISIBLE);

        ivEdtProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                Intent picIntent = new Intent();
                picIntent.setType("image/**");
                picIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(picIntent, "Select Image"), PICK_PICTURE);
            }
        });

        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneAlertDialog();
//
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmailAlertDialog();

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
        if (requestCode == PICK_PICTURE){
            ivEdtProfileImage.setVisibility(View.INVISIBLE);
            profileImage.setImageURI(imageReturnedIntent.getData());
//
//            SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString(KEY_PHONE, updatedNumber);
//            editor.apply();
        }
    }


    private void showPhoneAlertDialog() {
        AlertDialog.Builder phoneAlertDialog = new AlertDialog.Builder(getActivity());
        phoneAlertDialog.setTitle("Edit Phone Number");
        phoneAlertDialog.setMessage("Enter Your New Phone Number");

        final EditText edtPhoneNew = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtPhoneNew.setLayoutParams(lp);
        phoneAlertDialog.setView(edtPhoneNew); // adds edittext to the alert dialog
        phoneAlertDialog.setIcon(R.drawable.ic_contact_phone_black_24dp);

        phoneAlertDialog.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final ProgressDialog mDialog = new ProgressDialog(getActivity());
                mDialog.setMessage("Please wait...");
                mDialog.show();

                UpdateProfile updateProfile = new UpdateProfile(
                        Common.onlyUser,
                        edtPhoneNew.getText().toString(),
                        Common.currentUser.getEmail()
                        );

                String updatedNumber = edtPhoneNew.getText().toString();

                SharedPreferences userPreferences;
                userPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String userName = userPreferences.getString(SignUp.prefUser, "");

                table_users.child(userName).setValue(updateProfile);
                mDialog.dismiss();
                Toast.makeText(getActivity(), "phone number Updated", Toast.LENGTH_SHORT).show();


//                newPhone = edtPhoneNew.getText().toString();
//                if (newPhone.isEmpty() || newPhone.length() != 10 ){
//                    edtPhoneNew.setError("Enter a Valid phone number");
//                    edtPhoneNew.requestFocus();
//                }else {
//                    PhoneAuthProvider.getInstance()
//                            .verifyPhoneNumber(
//                                    newPhone, 60, TimeUnit.SECONDS, requireActivity(),phoneAuthCallbacks
//                            );
//                    FirebaseAuth.getInstance()
//                            .getCurrentUser().updatePhoneNumber()
//                }
                SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_PHONE, updatedNumber);
                editor.apply();
                setPhoneNumber(updatedNumber);
            }
        });
        phoneAlertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        phoneAlertDialog.show();
    }

    private void showEmailAlertDialog() {
        AlertDialog.Builder emailAlertDialog = new AlertDialog.Builder(getActivity());
        emailAlertDialog.setTitle("Edit Email");
        emailAlertDialog.setMessage("Enter Your New Email Address");

        final EditText edtEmailNew = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtEmailNew.setLayoutParams(lp);
        emailAlertDialog.setView(edtEmailNew); //adds edittext to the alert dialog
        emailAlertDialog.setIcon(R.drawable.ic_email_black_24dp);

        emailAlertDialog.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newEmail = edtEmailNew.getText().toString();
                SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_EMAIL, newEmail);
                editor.apply();
                setEmailAddress(newEmail);
            }
        });
        emailAlertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        emailAlertDialog.show();
    }

//    @Override
//    public void applyPhoneText(String phoneNumber) {
//        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(KEY_PHONE, phoneNumber);
//        editor.apply();
//        phoneNumberEdited = phoneNumber;
//        setPhoneNumber(phoneNumberEdited);
//    }

    private void setPhoneNumber(String phoneNew){
        newPhone = phoneNew;
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        phoneNew = preferences.getString(KEY_PHONE, txtPhone.getText().toString());
        txtPhone.setText(phoneNew);
    }

//    @Override
//    public void applyEmailText(String emailAddress) {
//        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(KEY_EMAIL, emailAddress);
//        editor.apply();
//        emailAddressEdited = emailAddress;
//        setEmailAddress(emailAddressEdited);
//    }


    private void setEmailAddress(String emailNew){
        newEmail = emailNew;
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        emailNew = preferences.getString(KEY_EMAIL, txtEmail.getText().toString());
        txtEmail.setText(emailNew);
    }
//
//    private void loadPreferences(View view){
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
//
//        if (sharedPreferences.contains(KEY_EMAIL)){
//            txtEmail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
//        }
//    }
}
