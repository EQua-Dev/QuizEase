package com.androidstrike.quizease.fragments;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidstrike.quizease.AddCourses;
import com.androidstrike.quizease.Database.Database;
import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.R;
import com.androidstrike.quizease.ViewHolder.CourseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Courses extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference registeredCourses;

    TextView txtRegisteredCourse, txtCourseLecturer, txtCreditTotal, txtCourseCode;
    FloatingActionButton fab;

    List<RegisteredCourses> registeredCoursesList = new ArrayList<>();

    CourseAdapter courseAdapter;

    public Courses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_courses, container, false);

        database = FirebaseDatabase.getInstance();
        registeredCourses = database.getReference("RegisteredCourses");

        recyclerView = v.findViewById(R.id.recycler_courses);
//        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        txtRegisteredCourse = v.findViewById(R.id.txt_registered_course);
        txtCourseLecturer = v.findViewById(R.id.txt_course_lecturer);
        txtCourseCode = v.findViewById(R.id.txt_registered_code);
        txtCreditTotal = v.findViewById(R.id.txt_course_credit_total);
        fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCourses addCourses = new AddCourses();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addCourses, "findAddCourses")
                        .addToBackStack(null)
                        .commit();

//                getActivity().getSupportFragmentManager()
//                        .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

//        fab.setVisibility(View.INVISIBLE);

        loadListRegCourses();

        return v;
    }


    private void loadListRegCourses() {
        registeredCoursesList = new Database(this.getActivity()).getCourses();
        courseAdapter = new CourseAdapter(registeredCoursesList, this.getActivity());
        recyclerView.setAdapter(courseAdapter);

//        calculate total credit units
        int total = 0;
        for (RegisteredCourses registeredCourses:registeredCoursesList)
            total+=(Integer.parseInt(registeredCourses.getCourseCredit()));
        txtCreditTotal.setText(String.valueOf(total));


    }

}
