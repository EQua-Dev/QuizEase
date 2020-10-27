package com.androidstrike.quizease.ViewHolder;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.R;

public class LecturerContactVHolder extends RecyclerView.ViewHolder {

    public TextView txtLecturerName, txtLecturerCourse, txtLecturerMail, txtLecturerPhone;
    public RelativeLayout viewBackgroundCall, viewBackgroundMail, viewForeground;
    private Context context;

    public LecturerContactVHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
    }

    public LecturerContactVHolder(@NonNull View itemView) {
        super(itemView);

        txtLecturerName = itemView.findViewById(R.id.lecturer_name);
        txtLecturerCourse = itemView.findViewById(R.id.lecturer_course);
        txtLecturerMail = itemView.findViewById(R.id.lecturer_email);
        txtLecturerPhone = itemView.findViewById(R.id.lecturer_phone);

    }
}
