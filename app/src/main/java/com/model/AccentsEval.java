package com.model;

import java.util.ArrayList;

class AccentsEval extends Evaluator {
    private ArrayList<Double> evalValue;

    AccentsEval() {
        this.evalValue = new ArrayList<Double>();
    }

    // audioDataの実効値のデシベルを計算する
    @Override
    void calculation(double[] audioData) {
        double evalValue;
        final double speakingDecibel = 30;

        evalValue = Utility.getDecibel(Utility.getRms(audioData));

        // 話していると判定したデータのみについて評価
        if(evalValue >= speakingDecibel) {
            this.evalValue.add(evalValue);
        }
    }

    @Override
    double returnResult() {
        double score;
        final double diffRateMin = 3, diffRateMax = 10;
        double diffSum = 0, diffRate;

        // データと、その1つ前のデータとの差の平均値を算出
        for(int i = 1; i < this.evalValue.size(); i++){
            diffSum += Math.abs(this.evalValue.get(i) - this.evalValue.get(i-1));
        }
        diffRate = diffSum / this.evalValue.size();

        // 点数化
        if(diffRate >= diffRateMax){
            score = 100;
        }else if(diffRate <= diffRateMin){
            score = 0;
        }else{
            score = 100 * (diffRate - diffRateMin) / (diffRateMax - diffRateMin);
        }

        return diffRate;
    }
}
