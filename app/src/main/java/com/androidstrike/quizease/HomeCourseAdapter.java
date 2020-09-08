package com.androidstrike.quizease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstrike.quizease.Model.RegisteredCourses;

import java.util.ArrayList;
import java.util.List;

public class HomeCourseAdapter extends RecyclerView.Adapter<HomeCourseAdapter.HomeCourseViewHolder> {

    public static List<RegisteredCourses> listCourses = new ArrayList<>();
    private Context context;
    public static int mPosition;

    public HomeCourseAdapter(){

    }

    public HomeCourseAdapter(List<RegisteredCourses> listCourses,Context context) {
        this.context = context;
        this.listCourses = listCourses;
    }

    @NonNull
    @Override
    public HomeCourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create the item_content into the viewholder
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.home_course_item, parent, false);
        return new HomeCourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCourseViewHolder holder, int position) {

        mPosition = position;

        //set the view classes to the respective content from the database
        holder.txt_home_course_name.setText(listCourses.get(mPosition).getCourseTitle());
        holder.txt_home_course_code.setText(listCourses.get(mPosition).getCourseCode());
        holder.txt_home_course_credit.setText(listCourses.get(mPosition).getCourseCredit());
    }

    @Override
    public int getItemCount() {
        return listCourses.size();
    }

    public class HomeCourseViewHolder extends RecyclerView.ViewHolder{

        public TextView txt_home_course_name, txt_home_course_code, txt_home_course_credit;

        private Context mContext;
        HomeCourseAdapter homeCourseAdapter = new HomeCourseAdapter();


        public HomeCourseViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_home_course_name = itemView.findViewById(R.id.home_course);
            txt_home_course_code = itemView.findViewById(R.id.home_course_code);
            txt_home_course_credit = itemView.findViewById(R.id.home_course_credit);


        }

    }
}
