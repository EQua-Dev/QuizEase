package com.androidstrike.quizease.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder>{

    private List<RegisteredCourses> listData = new ArrayList<>();
    private Context context;

    public CourseAdapter(List<RegisteredCourses> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.courses_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
//        todo here you will add an image icon that shines red if there is a pending test on that course
//        todo for this, you will add in the course's json a column for updating pending quiz
        holder.txt_course_name.setText(listData.get(position).getCourseTitle());
        holder.txt_course_credit.setText(listData.get(position).getCourseCredit());

        holder.txt_course_detail_click.setText("Click here for course details");
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
