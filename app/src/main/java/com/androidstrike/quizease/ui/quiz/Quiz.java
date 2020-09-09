package com.androidstrike.quizease.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Quiz extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000;
    final static long TIMEOUT = 7000; //7 seconds for now
    int progressValue = 0;

    CountDownTimer mCountDown;

    int index=0,score=0,thisQuestion=0,totalQuestion,correctAnswer;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    ProgressBar progressBar;
    Button btnA, btnB, btnC, btnD;
    TextView txtScore, txtQuestionNum, questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("EQUA", "onCreate: Quiz Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Firebase
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        //Views
        txtScore = findViewById(R.id.txt_score);
        txtQuestionNum = findViewById(R.id.txt_total_question);
        questionText = findViewById(R.id.tv_question_display);

        progressBar = findViewById(R.id.progress_bar);

        btnA = findViewById(R.id.btn_option1);
        btnB = findViewById(R.id.btn_option2);
        btnC = findViewById(R.id.btn_option3);
        btnD = findViewById(R.id.btn_option4);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mCountDown.cancel();
        if (index < totalQuestion) // questions have not been exhausted
        {
            Button clickedbutton = (Button)v;
            if (clickedbutton.getText().equals(Common.questionList.get(index).getAnswer())){
                //choose correct answer
                score+=10;
                correctAnswer++;
                showQuestion(++index); // next question
            }else {
                // choose wrong question
                Intent intent = new Intent(this, Done.class );
                Bundle dataSend = new Bundle();
                dataSend.putInt("SCORE",score);
                dataSend.putInt("TOTAL",totalQuestion);
                dataSend.putInt("CORRECT",correctAnswer);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }
            txtScore.setText(String.format("%d", score));
        }
    }

    private void showQuestion(int i) {
        if (index < totalQuestion){
            thisQuestion++;
            txtQuestionNum.setText(String.format("%d/ %d", thisQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;

//            if (Common.questionList.get(index).getIsImageQuestion().equals("true")){
//                //if it is an image question
//                Picasso.get().load(Common.questionList.get(index).getQuestion())
//                        .into(question_image);
//                question_image.setVisibility(View.VISIBLE);
//                questionText.setVisibility(View.INVISIBLE);
//            }
//            else {
                //if it is a text question
                questionText.setText(Common.questionList.get(index).getQuestion());
//                question_image.setVisibility(View.INVISIBLE);
//                questionText.setVisibility(View.VISIBLE);
//            }

            btnA.setText(Common.questionList.get(index).getOption1());
            btnB.setText(Common.questionList.get(index).getOption2());
            btnC.setText(Common.questionList.get(index).getOption3());
            btnD.setText(Common.questionList.get(index).getOption4());

            mCountDown.start(); //start timer
        }else {
            //if it is the last question
            Intent intent = new Intent(this, Done.class );
            Bundle dataSend = new Bundle();
            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();

        mCountDown = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                showQuestion(++index);
            }
        };
        showQuestion(index);
    }

}
