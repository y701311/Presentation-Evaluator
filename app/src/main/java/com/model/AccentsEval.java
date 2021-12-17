package com.model;

import java.util.ArrayList;

class AccentsEval extends Evaluator {
    private final ArrayList<Double> evalValue;

    AccentsEval() {
        this.evalValue = new ArrayList<Double>();
    }

    // audioDataの実効値のデシベルを計算する
    @Override
    void calculation(double[] audioData) {
        double evalValue;
        final double speakingDecibel = 20;

        evalValue = Utility.getDecibel(Utility.getRms(audioData));

        // 話していると判定したデータのみについて評価
        if(evalValue >= speakingDecibel) {
            this.evalValue.add(evalValue);
        }
    }

    @Override
    EvalResult returnResult() {
        EvalResult evalResult = new EvalResult();
        final double bestDiffRate = 4.8;
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
        evalResult.score = 100 - 50 * Math.abs(diffRate - bestDiffRate);
        if(evalResult.score < 0){
            evalResult.score = 0;
        }

        if(0 <= evalResult.score && evalResult.score < 20){
            evalResult.text = "大事な言葉を意識しよう";
        }else if(20 <= evalResult.score && evalResult.score < 40){
            evalResult.text = "メリハリを意識してみよう";
        }else if(40 <= evalResult.score && evalResult.score < 60){
            evalResult.text = "正しい強弱を意識してみよう";
        }else if(60 <= evalResult.score && evalResult.score < 80){
            evalResult.text = "表現を工夫してみよう";
        }else if(80 <= evalResult.score && evalResult.score <= 100){
            evalResult.text = "完璧！";
        }

        return evalResult;
    }
}
