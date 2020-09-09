package com.androidstrike.quizease.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.Interface.ItemClickListener;
import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.ui.quiz.Quiz;
import com.androidstrike.quizease.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{

    public static List<RegisteredCourses> listData = new ArrayList<>();
    private Context context;
    public static int mPosition;

    public CourseAdapter() {
    }

    public CourseAdapter(List<RegisteredCourses> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        create the item_content into the viewholder
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.courses_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseViewHolder holder, int position) {

        mPosition = position;

//        set the view classes to the respective content from the database
        holder.txt_course_name.setText(listData.get(mPosition).getCourseTitle());
        holder.txt_course_credit.setText(listData.get(mPosition).getCourseCredit());
        holder.txt_course_lecturer.setText(listData.get(mPosition).getCourseLecturer());
        holder.txt_course_code.setText(listData.get(mPosition).getCourseCode());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


//viewholder for the registered courses recycler view

    public class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txt_course_name, txt_course_lecturer, txt_course_credit, txt_course_code;

        public ItemClickListener itemClickListener;
        private Context mContext;
        public

        CourseAdapter courseAdapter = new CourseAdapter();

        public void setTxt_course_name(TextView txt_course_name){
            this.txt_course_name = txt_course_name;
        }

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_course_name = itemView.findViewById(R.id.txt_registered_course);
            txt_course_lecturer = itemView.findViewById(R.id.txt_course_lecturer);
            txt_course_credit = itemView.findViewById(R.id.txt_registered_course_credit);
            txt_course_code = itemView.findViewById(R.id.txt_registered_code);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openQuiz = new Intent(context, Quiz.class);
                    openQuiz.putExtra("course_id", listData.get(mPosition).getCourseCode());
                }
            });
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);

        }
    }


}
