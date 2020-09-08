package com.androidstrike.quizease.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidstrike.quizease.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment{

    private ImageView ivEdtProfileImage, profileImage;
    private TextView txtEmail, txtPhone, txtBoys;
    private FloatingActionButton fab;

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

        iv = view.findViewById(R.id.test_profile_image);

        setEmailAddress(newEmail);
        setPhoneNumber(newPhone);

//        fab.setVisibility(View.INVISIBLE);

        ivEdtProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                takePhone();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Upload Photo");
                builder.setItems(addImage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(takePicture, TAKE_PICTURE);
                        }else if (which == 1){
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, PICK_PICTURE);
                        }
                    }
                });
                builder.show();
            }
        });

        txtPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneAlertDialog();
//                EditProfilePhoneDialog editProfilePhoneDialog = new EditProfilePhoneDialog();
//                editProfilePhoneDialog.show(getFragmentManager(),"edit phone dialog");
//                setPhoneNumber(phoneNumberEdited);
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmailAlertDialog();
//                EditProfileEmailDialog editProfileEmailDialog = new EditProfileEmailDialog();
//                editProfileEmailDialog.show(getFragmentManager(),"edit email dialog");
//                setEmailAddress(emailAddressEdited);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (resultCode){
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK){
                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    profileImage.setImageBitmap(imageBitmap);
                }
                break;
            case PICK_PICTURE:
                if (requestCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    profileImage.setImageURI(selectedImage);
                }
                break;
        }
    }

    //    public void takePhone() {
//        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        photoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
//        photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Pic.jpg");
//
////                photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
////                        Uri.fromFile(photo));
//                imageUri = Uri.fromFile(photo);
//                startActivityForResult(photoIntent, TAKE_PICTURE);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////        if (requestCode == 0){
////            switch (requestCode){
////                case getActivity().RESULT_OK:
////                    if (photo.exists()){
//////                        profileImage.setImageResource();
////                    }else {
////                        Toast.makeText(getActivity(), "Error Loading Image", Toast.LENGTH_SHORT).show();
////                    }
////                    break;
////                case getActivity().RESULT_CANCELED:
////                    break;
////                default:
////                    break;
////            }
////        }
//        switch (requestCode){
//            case TAKE_PICTURE:
//                if (resultCode == Activity.RESULT_OK){
//                    Uri selectedImage = imageUri;
//                    requireActivity().getContentResolver().notifyChange(selectedImage, null);
//                    ImageView imageView = ivEdtProfileImage;
//                    ContentResolver cr = requireActivity().getContentResolver();
//                    Bitmap bitmap;
//                    try {
//                        bitmap = MediaStore.Images.Media
//                                .getBitmap(cr, selectedImage);
//                        imageView.setImageBitmap(bitmap);
//                        Toast.makeText(getActivity(), selectedImage.toString(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        Toast.makeText(getActivity(), "Failed to load", Toast.LENGTH_SHORT).show();
//                        Log.e("Camera", e.toString());
//                    }
//                }
//        }
//    }

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
                newPhone = edtPhoneNew.getText().toString();
                SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_PHONE, newPhone);
                editor.apply();
                setPhoneNumber(newPhone);
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
