package com.model;

import java.util.ArrayList;

class VolumeEval extends Evaluator {
    private ArrayList<Double> evalValue;

    VolumeEval() {
        this.evalValue = new ArrayList<Double>();
    }

    // audioDataの実効値のデシベルを計算する
    @Override
    void calculation(double[] audioData) {
        double evalValue;
        evalValue = Utility.getDecibel(Utility.getRms(audioData));

        this.evalValue.add(evalValue);
    }

    @Override
    double returnResult() {
        double evalValue = 0;

        // デシベルの平均値を算出
        for(double value : this.evalValue){
            evalValue += value;
        }
        evalValue /= this.evalValue.size();

        return evalValue;
    }
}