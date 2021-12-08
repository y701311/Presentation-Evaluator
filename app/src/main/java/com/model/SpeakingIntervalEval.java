package com.model;

import java.util.ArrayList;

class SpeakingIntervalEval extends Evaluator {
    private ArrayList<Double> evalValue;

    SpeakingIntervalEval() {
        this.evalValue = new ArrayList<Double>();
    }

    // audioDataの実効値のデシベルを計算する
    @Override
    void calculation(double[] audioData) {
        double evalValue;
        evalValue = Utility.getDecibel(Utility.getRms(audioData));

        this.evalValue.add(evalValue);
    }

    // 値が一定値以下なら話していないとして、そのような時間の割合を評価
    @Override
    double returnResult() {
        double evalValue = 0;
        final double base = 30;// ささやき声くらい
        int silentCount = 0;
        // 話していないとした割合
        double silentRate;
        final double bestSilentRate = 0.2;

        for(double value : this.evalValue){
            if(value <= base){
                silentCount++;
            }
        }
        silentRate = (double)silentCount / this.evalValue.size();

        if(silentRate < bestSilentRate){
            evalValue = 100 - ((bestSilentRate - silentRate) / bestSilentRate) * 100;
        }else if(silentRate >= bestSilentRate){
            evalValue = 100 - ((silentRate - bestSilentRate) / (1 - bestSilentRate)) * 100;
        }

        return evalValue;
    }
}