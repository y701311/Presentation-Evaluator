package com.model;

import java.util.ArrayList;

class SpeakingSpeedEval extends Evaluator {
    private ArrayList<Double> evalValue;

    SpeakingSpeedEval() {
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