package com.androidstrike.quizease.ui.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidstrike.quizease.Condition;
import com.androidstrike.quizease.FaceTrackerDaemon;
import com.androidstrike.quizease.R;
import com.androidstrike.quizease.ui.quiz.fragments.Rules;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class QuizActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference questions;

    boolean flag = false;
    CameraSource cameraSource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference("Questions");

        coordinatorLayout =findViewById(R.id.layout_quiz_coordinate);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.quiz_container, new Rules());
        ft.commit();


//        check camera permission
//        if camera permission is provided my task is initiated

        //will be checked in rules' activity
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            Toast.makeText(this, "Permission not granted!\n Grant permission and restart app", Toast.LENGTH_SHORT).show();
        }else{
            init();
        }
    }

    private void init() {
        flag = true;

        initCameraSource();
    }

    //method to create camera source from faceFactoryDaemon class
    private void initCameraSource() { //will be called in quiz activity on create
//        created a detector
        FaceDetector detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(true)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        detector.setProcessor(new MultiProcessor.Builder(new FaceTrackerDaemon(QuizActivity.this)).build());

        cameraSource = new CameraSource.Builder(this, detector)
                .setRequestedPreviewSize(1024, 768)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                //  Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
//                   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            cameraSource.start();
        }
        catch (IOException e) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() { //for quizEase, show done activity here
        super.onResume();
        Log.e("EQUA", "onResume: " );
        if (cameraSource != null) {
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //  Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                cameraSource.start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() { //submit the quiz and release the camera
        super.onPause();
        Log.e("EQUA", "onPause: " );
        if (cameraSource!=null) {
            cameraSource.stop();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("EQUA", "onDestroy: ");
        if (cameraSource!=null) {
            cameraSource.release();
        }
    }

    //update view
    public void updateMainView(Condition condition){
        switch (condition){
            case USER_EYES_OPEN: //either quiz goes on or a void function is returned
                Snackbar snackbarOpen = Snackbar.make(coordinatorLayout, "Open", Snackbar.LENGTH_LONG);
                snackbarOpen.show();
                break;
            case USER_EYES_CLOSED: //if for up to 15 seconds, then a warning (splash) screen is issued and sleeps after a certain number of seconds
                Snackbar snackbarClosed = Snackbar.make(coordinatorLayout, "Closed", Snackbar.LENGTH_LONG);
                snackbarClosed.show();
                break;
            case FACE_NOT_FOUND: //if for up to 10 seconds, quiz is submitted
                Snackbar snackbarNotFound = Snackbar.make(coordinatorLayout, "Not Found", Snackbar.LENGTH_LONG);
                snackbarNotFound.show();
                break;
//            default:
//                setBackgroundGrey();
//                user_message.setText("Hello World");
        }
    }

}
