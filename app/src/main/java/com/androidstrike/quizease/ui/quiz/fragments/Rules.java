package com.androidstrike.quizease.ui.quiz.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.androidstrike.quizease.Common.Common;
import com.androidstrike.quizease.FaceTrackerDaemon;
import com.androidstrike.quizease.Model.Question;
import com.androidstrike.quizease.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Collections;

public class Rules extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button quizStart;
    private CoordinatorLayout coordinatorLayout;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    boolean flag = false;
    CameraSource cameraSource;

    public Rules(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_rules, container, false);


        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        loadQuestion(Common.courseId);

        coordinatorLayout = v.findViewById(R.id.rules_coordinate);

        recyclerView = v.findViewById(R.id.recycler_rules);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        quizStart = v.findViewById(R.id.btn_quiz_start);

        quizStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
//                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Camera Permission not granted! \n Grant permission and restart app", Snackbar.LENGTH_LONG);
//                    snackbar.show();
//                }else {
//                    init();
//                }

                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Good luck!", Snackbar.LENGTH_LONG);
                snackbar.show();

                Quiz quiz = new Quiz();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.quiz_container, quiz, "startQuiz")
                        .addToBackStack(null)
                        .commit();

            }
        });

        return v;
    }
//
//    private void init() {
//        flag = true;
//
//        initCameraSource();
//    }


    //method to create camera source from faceFactoryDaemon class
//    private void initCameraSource() { //will be called in quiz activity on create
////        created a detector
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
//            Snackbar snackbar = Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG);
//            snackbar.show();
//            e.printStackTrace();
//        }
//    }
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