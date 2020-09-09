package com.androidstrike.quizease.ui.quiz.eyeTracking;

import android.content.Context;
import android.util.Log;

import com.androidstrike.quizease.Condition;
import com.androidstrike.quizease.ui.quiz.QuizActivity;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EyesTracker extends Tracker<Face> {

//    set threshold
    private final float THRESHOLD = 0.75f;

//    context
    private Context context;

//    public constructor used to call this function
    public EyesTracker(Context context) {
        this.context = context;
    }

//    update tracking data
    @Override
    public void onUpdate(Detector.Detections<Face> detections, Face face) {
        if (face.getIsLeftEyeOpenProbability() < THRESHOLD || face.getIsRightEyeOpenProbability() < THRESHOLD) {
            Log.i(TAG, "onUpdate: Close Eyes Detected");
            ((QuizActivity)context).updateMainView(Condition.USER_EYES_CLOSED);

    }else //if the eyeOpenProbability is < THRESHOLD for 15 seconds
        { Log.i(TAG, "onUpdate: Open Eyes Detected");
            ((QuizActivity)context).updateMainView(Condition.USER_EYES_OPEN);
    }
    }
//    this method is called when no face is detected
    @Override
    public void onMissing(Detector.Detections<Face> detections) {
        super.onMissing(detections);
        Log.i(TAG, "onUpdate: Face Not Detected yet!");
        ((QuizActivity)context).updateMainView(Condition.FACE_NOT_FOUND);
    }
        @Override
    public void onDone() {
        super.onDone();
    }
}
