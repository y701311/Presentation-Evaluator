package com.model;

import java.util.ArrayList;

class SpeakingIntervalEval extends Evaluator {
    private ArrayList<Double> evalValue;

    SpeakingIntervalEval() {
        this.evalValue = new ArrayList<Double>();
    }

    @Override
    void calculation(double[] audioData) {

    }

    @Override
    double returnResult() {
        return 0.0;
    }
}