package com.model;

import java.util.ArrayList;

class MeanLessWordsEval extends Evaluator {
    private ArrayList<Double> evalValue;

    MeanLessWordsEval() {
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