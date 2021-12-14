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
        double decibelMean = 0;
        final double bestVolume = 50;

        // デシベルの平均値を算出
        for(double value : this.evalValue){
            decibelMean += value;
        }
        decibelMean /= this.evalValue.size();

        // 点数化
        // 大きすぎるなら点数化しやすいように丸める
        if(decibelMean >= bestVolume * 2){
            decibelMean = bestVolume * 2;
        }
        evalValue = 100 - Math.abs(bestVolume - decibelMean);

        return evalValue;
    }
}