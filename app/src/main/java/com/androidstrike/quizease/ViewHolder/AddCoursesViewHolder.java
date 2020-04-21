package com.androidstrike.quizease.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.Interface.ItemClickListener;
import com.androidstrike.quizease.R;

public class AddCoursesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtCourseCode, txtCourseTitile, txtCourseCredit;
    public ImageView ivCourseImage;

    public Button addButton;

    private ItemClickListener itemClickListener;

    public AddCoursesViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCourseCode = itemView.findViewById(R.id.course_code);
        ivCourseImage = itemView.findViewById(R.id.course_image);
        txtCourseTitile = itemView.findViewById(R.id.course_title);
        txtCourseCredit = itemView.findViewById(R.id.course_credit);
        addButton = itemView.findViewById(R.id.button_add_course);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;


    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
