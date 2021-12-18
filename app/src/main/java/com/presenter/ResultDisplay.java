package com.presenter;

import android.app.Activity;

import com.view.MainActivity;

public class ResultDisplay {
    public void display(EvaluationValues evaluationValues, MainActivity activity) {
        activity.transitionDisplayResult(evaluationValues);
    }
}