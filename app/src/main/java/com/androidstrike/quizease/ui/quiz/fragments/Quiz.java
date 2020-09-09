package com.androidstrike.quizease.ui.quiz.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.Condition;
import com.androidstrike.quizease.FaceTrackerDaemon;
import com.androidstrike.quizease.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class Quiz extends Fragment implements View.OnClickListener {

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

    CoordinatorLayout coordinatorLayout;

    boolean flag = false;
    CameraSource cameraSource;

    public Quiz(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_quiz, container, false);


        coordinatorLayout = v.findViewById(R.id.layout_coordinate_quiz_question);

        //Firebase
        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
//            Toast.makeText(getActivity(), "Permission not granted!\n Grant permission and restart app", Toast.LENGTH_SHORT).show();
//        }else{
//            init();
//        }

        //Views
        txtScore = v.findViewById(R.id.txt_score);
        txtQuestionNum = v.findViewById(R.id.txt_total_question);
        questionText = v.findViewById(R.id.tv_question_display);

        progressBar = v.findViewById(R.id.progress_bar);

        btnA = v.findViewById(R.id.btn_option1);
        btnB = v.findViewById(R.id.btn_option2);
        btnC = v.findViewById(R.id.btn_option3);
        btnD = v.findViewById(R.id.btn_option4);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

        return v;
    }
//
//    private void init() {
//        flag = true;
//
//        initCameraSource();
//    }
//
//    private void initCameraSource() {
//        FaceDetector detector = new FaceDetector.Builder(getActivity())
//                .setTrackingEnabled(true)
//                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
//                .setMode(FaceDetector.FAST_MODE)
//                .build();
//
//        detector.setProcessor(new MultiProcessor.Builder(new FaceTrackerDaemon(getActivity())).build());
//
//        cameraSource = new CameraSource.Builder(getActivity(), detector)
//                .setRequestedPreviewSize(1024, 768)
//                .setFacing(CameraSource.CAMERA_FACING_FRONT)
//                .setRequestedFps(30.0f)
//                .build();
//
//        try {
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                //  Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
////                   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            cameraSource.start();
//        }
//        catch (IOException e) {
//            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }

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

                showQuestion(++index);
//                Intent intent = new Intent(this, DoneDel.class );
//                Bundle dataSend = new Bundle();
//                dataSend.putInt("SCORE",score);
//                dataSend.putInt("TOTAL",totalQuestion);
//                dataSend.putInt("CORRECT",correctAnswer);
//                intent.putExtras(dataSend);
//                startActivity(intent);
//                finish();
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
//            Intent intent = new Intent(this, DoneDel.class );
            Bundle dataSend = new Bundle();

            dataSend.putInt("SCORE",score);
            dataSend.putInt("TOTAL",totalQuestion);
            dataSend.putInt("CORRECT",correctAnswer);

            Done done = new Done();
            done.setArguments(dataSend);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.quiz_container, done, "findDone")
                    .addToBackStack(null)
                    .commit();

        }
    }
//
    @Override
    public void onResume() {
        super.onResume();
//
//        if (cameraSource != null) {
//            try {
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    // Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                cameraSource.start();
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

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

    @Override
    public void onPause() { //submit the quiz and release the camera
        super.onPause();
        Bundle dataSend = new Bundle();

        dataSend.putInt("SCORE",score);
        dataSend.putInt("TOTAL",totalQuestion);
        dataSend.putInt("CORRECT",correctAnswer);

        Done done = new Done();
        done.setArguments(dataSend);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.quiz_container, done, "findDone")
                .addToBackStack(null)
                .commit();
//        if (cameraSource!=null) {
//            cameraSource.stop();
//        }

        Log.e("EQUA", "onPause: " );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (cameraSource!=null) {
            cameraSource.release();
        }
    }

//
//    public void updateMainView(Condition condition){
//        switch (condition){
//            case USER_EYES_OPEN: //either quiz goes on or a void function is returned
////                setBackgroundGreen();
////                user_message.setText("Open eyes detected");
//                Snackbar snackbarOpen = Snackbar.make(coordinatorLayout, "Eyes Open", Snackbar.LENGTH_LONG);
//                snackbarOpen.show();
//                break;
//            case USER_EYES_CLOSED: //if for up to 15 seconds, then a warning (splash) screen is issued and sleeps after a certain number of seconds
////                setBackgroundOrange();
////                user_message.setText("Close eyes detected");
//                Snackbar snackbarClosed = Snackbar.make(coordinatorLayout, "Eyes Closed", Snackbar.LENGTH_LONG);
//                snackbarClosed.show();
//                break;
//            case FACE_NOT_FOUND: //if for up to 10 seconds, quiz is submitted
////                setBackgroundRed();
////                user_message.setText("User not found");
//                Snackbar snackbarNotFound = Snackbar.make(coordinatorLayout, "Not Found", Snackbar.LENGTH_LONG);
//                snackbarNotFound.show();
//                break;
////            default:
////                setBackgroundGrey();
////                user_message.setText("Hello World");
//        }
//    }
}