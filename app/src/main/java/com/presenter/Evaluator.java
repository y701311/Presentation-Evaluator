package com.presenter;

import android.content.Context;

import com.model.EvalController;
import com.view.MainActivity;

import java.nio.file.Path;

public class Evaluator {
    private final MainActivity activity;
    private EvaluationValues value;

    public Evaluator(MainActivity activity) {
        this.activity = activity;
    }

    public void startEvaluate() {
        ResultDisplay resultDisplay = new ResultDisplay();
        FileSelect fileSelect = new FileSelect();
        EvalController evalController = new EvalController();
        Path audioFilePath = fileSelect.getFilePath();

        //value = evalController.evalController(audioFilePath, (Context)activity);
        resultDisplay.display(value);
    }

    EvaluationValues getEvaluationValues() {
        return value;
    }
}