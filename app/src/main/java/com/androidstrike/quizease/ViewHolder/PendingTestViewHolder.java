package com.androidstrike.quizease.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.Interface.GoClickListener;
import com.androidstrike.quizease.Interface.ItemClickListener;
import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.R;

import java.util.ArrayList;
import java.util.List;

public class PendingTestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    public TextView txtCourseTitle, txtCourseCode;
    public Button goBtn;
    private ItemClickListener itemClickListener;
    private GoClickListener goClickListener;
    public static List<RegisteredCourses> listCourses = new ArrayList<>();


    public PendingTestViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
    }

    public PendingTestViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCourseTitle = itemView.findViewById(R.id.pending_course);
        txtCourseCode = itemView.findViewById(R.id.pending_course_code);
        goBtn = itemView.findViewById(R.id.pending_btn_go);

        itemView.setOnClickListener(this);

//        goBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goClickListener.onClick(v,getAdapterPosition(),false);
//            }
//        });

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setGoClickListener(GoClickListener goClickListener) {
        this.goClickListener = goClickListener;
    }

    public void onClick(View v){
        goClickListener.onClick(v,getAdapterPosition(),false);
    }

//
//    @Override
//    public void onClick(View v) {
//        goBtn.setOnClickListener(itemClickListener.onClick(v,getAdapterPosition(),false));
//
//        itemClickListener.onClick(v,getAdapterPosition(),false);
//    }
}
