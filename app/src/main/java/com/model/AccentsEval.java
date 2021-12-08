package com.model;

import java.util.ArrayList;

class AccentsEval extends Evaluator {
    private ArrayList<Double> evalValue;

    AccentsEval() {
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
