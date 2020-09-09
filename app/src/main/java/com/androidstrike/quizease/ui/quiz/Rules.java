package com.androidstrike.quizease.ui.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.Model.Question;
import com.androidstrike.quizease.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Rules extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button quizStart;
    private CoordinatorLayout coordinatorLayout;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference questions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        loadQuestion(Common.courseId);

        coordinatorLayout = findViewById(R.id.rules_coordinate);

        recyclerView = findViewById(R.id.recycler_rules);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        quizStart = findViewById(R.id.btn_quiz_start);

        quizStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Rules.this, Quiz.class);
                startActivity(intent);
                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Course not Registered!", Snackbar.LENGTH_LONG);
                snackbar.show();
                finish();
            }
        });
    }

    private void loadQuestion(String courseId) {
        if (Common.questionList.size() > 0)
            Common.questionList.clear();
        questions.orderByChild("CourseId").equalTo(courseId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot postSnapShot : snapshot.getChildren()){
                            Question question = postSnapShot.getValue(Question.class);
                            Common.questionList.add(question);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //Randomize Questions
        Collections.shuffle(Common.questionList);
    }
}