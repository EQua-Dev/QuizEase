package com.androidstrike.quizease;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditProfilePhoneDialog extends AppCompatDialogFragment {

    private EditText editTextPhone;
    private PhoneChangeDialogueListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.activity_edit_profile_phone_dialog, null);
        builder.setView(view)
                .setTitle("Edit Phone Number")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phoneNumber =editTextPhone.getText().toString();
                listener.applyPhoneText(phoneNumber);
            }
        });
        editTextPhone = view.findViewById(R.id.edit_text_phone_number);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (PhoneChangeDialogueListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement ExampleDialogListener");
        }
    }

    public interface PhoneChangeDialogueListener{
        void applyPhoneText(String phoneNumber);
    }
}
