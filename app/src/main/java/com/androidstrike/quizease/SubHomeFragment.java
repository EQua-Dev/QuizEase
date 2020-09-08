package com.androidstrike.quizease;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidstrike.quizease.Database.Database;
import com.androidstrike.quizease.Model.RegisteredCourses;
import com.androidstrike.quizease.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class SubHomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference registeredCourses;

    TextView txtCourse, txtCode, txtCredit;

    List<RegisteredCourses> registeredCoursesList = new ArrayList<>();

    HomeCourseAdapter homeCourseAdapter;

    public SubHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sub_home, container, false);

        recyclerView = v.findViewById(R.id.recycler_home_courses);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        txtCourse = v.findViewById(R.id.home_course);
        txtCode = v.findViewById(R.id.home_course_code);
        txtCredit = v.findViewById(R.id.home_course_credit);

        loadHomeCourses();

        return v;
    }

    private void loadHomeCourses() {
        registeredCoursesList = new Database(this.getActivity()).getCourses();
        homeCourseAdapter = new HomeCourseAdapter(registeredCoursesList, this.getActivity());
        recyclerView.setAdapter(homeCourseAdapter);
    }


}
