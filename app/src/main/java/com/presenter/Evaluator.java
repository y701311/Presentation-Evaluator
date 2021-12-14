package com.presenter;

import static java.lang.System.exit;

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
    }

    public void startEvaluate() {
        ResultDisplay resultDisplay = new ResultDisplay();
        FileSelect fileSelect = new FileSelect();
        EvalController evalController = new EvalController();
        Uri audioFilePath = fs.getFilePath();
        try {
            value = evalController.evalController(audioFilePath, (Context) activity);
        } catch (IOException e) {
            exit(1);
            e.printStackTrace();
        }
        resultDisplay.display(value);
    }
}