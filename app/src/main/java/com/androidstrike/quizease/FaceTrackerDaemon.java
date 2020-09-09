package com.androidstrike.quizease;

import android.content.Context;

import com.androidstrike.quizease.ui.quiz.eyeTracking.EyesTracker;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;

//this class daemon is responsible for setting the eyesTracker as base for detection of eyes
public class FaceTrackerDaemon implements MultiProcessor.Factory<Face> {
    private Context context;
    public FaceTrackerDaemon(Context context){
        this.context = context;
    }

    @Override
    public Tracker<Face> create(Face face) {
        return new EyesTracker(context);
    }
}
