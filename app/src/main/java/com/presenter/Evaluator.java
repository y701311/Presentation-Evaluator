package com.presenter;

import android.content.Context;
import android.net.Uri;

import com.model.EvalController;
import com.view.MainActivity;

import java.io.IOException;

public class Evaluator {
    private final MainActivity activity;
    private final FileSelect fs;
    private EvaluationValues value;

    public Evaluator(MainActivity activity, FileSelect fs) {
        this.activity = activity;
        this.fs = fs;
        value = new EvaluationValues();
    }

    public void startEvaluate() {
        ResultDisplay resultDisplay = new ResultDisplay();
        EvalController evalController = new EvalController();
        Uri audioFilePath = fs.getFilePath();
        try {
            value = evalController.evalController(audioFilePath, activity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultDisplay.display(value, activity);
    }
}