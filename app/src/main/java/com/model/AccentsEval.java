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
    EvalResult returnResult() {
        EvalResult evalResult = new EvalResult();
        final double diffRateMin = 3, diffRateMax = 10;
        double diffSum = 0, diffRate;

        // データと、その1つ前のデータとの差の平均値を算出
        for(int i = 1; i < this.evalValue.size(); i++){
            diffSum += Math.abs(this.evalValue.get(i) - this.evalValue.get(i-1));
        }
        if(this.evalValue.size() != 0) {
            diffRate = diffSum / this.evalValue.size();
        }else{
            diffRate = 0;
        }

        // 点数化
        if(diffRate >= diffRateMax){
            evalResult.score = 100;
        }else if(diffRate <= diffRateMin){
            evalResult.score = 0;
        }else{
            evalResult.score = 100 * (diffRate - diffRateMin) / (diffRateMax - diffRateMin);
        }
        if(evalResult.score < 0){
            evalResult.score = 0;
        }

        if(0 <= evalResult.score && evalResult.score < 20){
            evalResult.text = "まだまだ足りない";
        }else if(20 <= evalResult.score && evalResult.score < 40){
            evalResult.text = "ちょっと足りない";
        }else if(40 <= evalResult.score && evalResult.score < 60){
            evalResult.text = "そこそこ";
        }else if(60 <= evalResult.score && evalResult.score < 80){
            evalResult.text = "いい感じ";
        }else if(80 <= evalResult.score && evalResult.score <= 100){
            evalResult.text = "ばっちり！";
        }

        return evalResult;
    }
}
