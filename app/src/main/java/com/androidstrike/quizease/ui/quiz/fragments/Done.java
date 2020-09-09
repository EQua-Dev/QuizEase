package com.androidstrike.quizease.ui.quiz.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.Model.QuestionScore;
import com.androidstrike.quizease.R;
import com.androidstrike.quizease.ui.HomeActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Done extends Fragment {

    Button btnTryAgain;
    TextView txtResultScore,getTextResultQuestion;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference question_score;

    CameraSource cameraSource;


    public Done(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_done, container, false);

        if (cameraSource!=null) {
            cameraSource.release();
        }


        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        txtResultScore = v.findViewById(R.id.txtTotalScore);
        getTextResultQuestion = v.findViewById(R.id.txtTotalQuestion);
        progressBar = v.findViewById(R.id.doneProgressBar);
        btnTryAgain = v.findViewById(R.id.btn_try_again);

        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        //Get data from bundle and set to view
        Bundle extras = getArguments();
        if (extras != null){
            int score = extras.getInt("SCORE");
            int totalQuestion = extras.getInt("TOTAL");
            int correctAsnwer = extras.getInt("CORRECT");

            txtResultScore.setText(String.format("SCORE : %d", score));
            getTextResultQuestion.setText(String.format("PASSED : %d / %d",correctAsnwer,totalQuestion));

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAsnwer);

            //Upload score to DB
            question_score.child(String.format("%s_%s", Common.currentUser.getRegNo(),
                    Common.courseId))
                    .setValue(new QuestionScore(String.format("%s_%s", Common.currentUser.getRegNo(),
                            Common.courseId),
                            Common.currentUser.getRegNo(),
                            String.valueOf(score),
                            Common.courseId,
                            Common.courseName));
        }


        return v;
    }
}