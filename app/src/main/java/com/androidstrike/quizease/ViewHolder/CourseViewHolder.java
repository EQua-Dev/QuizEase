package com.androidstrike.quizease.ViewHolder;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.Interface.ItemClickListener;
import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.R;

import java.util.ArrayList;
import java.util.List;

public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_course_name, txt_course_detail_click, txt_course_credit;

    public ItemClickListener itemClickListener;

    public void setTxt_course_name(TextView txt_course_name){
        this.txt_course_name = txt_course_name;
    }

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_course_name = itemView.findViewById(R.id.txt_registered_course);
        txt_course_detail_click = itemView.findViewById(R.id.txt_course_detail_click);
        txt_course_credit = itemView.findViewById(R.id.txt_registered_course_credit);
    }

    @Override
    public void onClick(View v) {

    }
}

