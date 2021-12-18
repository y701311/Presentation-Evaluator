package com.model;

import java.util.ArrayList;

class SpeakingIntervalEval extends Evaluator {
    private final ArrayList<Double> evalValue;

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
    EvalResult returnResult() {
        EvalResult evalResult = new EvalResult();
        final double base = 40;
        int silentCount = 0;
        // 話していないとした割合
        double silentRate;
        final double bestSilentRate = 0.4;

        for(double value : this.evalValue){
            if(value <= base){
                silentCount++;
            }
        }
        silentRate = (double)silentCount / this.evalValue.size();

        if(silentRate < bestSilentRate){
            evalResult.score = 100 - ((bestSilentRate - silentRate) / bestSilentRate) * 100;
            evalResult.evalDirection = evalResult.small;
        }else if(silentRate >= bestSilentRate){
            evalResult.score = 100 - ((silentRate - bestSilentRate) / (1 - bestSilentRate)) * 100;
            evalResult.evalDirection = evalResult.large;
        }

        if(0 <= evalResult.score && evalResult.score < 20){
            if(silentRate > bestSilentRate){
                evalResult.text = "間が長すぎるかも";
            }else{
                evalResult.text = "間を意識してみよう";
            }
        }else if(20 <= evalResult.score && evalResult.score < 40){
            if(silentRate > bestSilentRate){
                evalResult.text = "間を短くしてみよう";
            }else{
                evalResult.text = "落ち着いて話してみよう";
            }
        }else if(40 <= evalResult.score && evalResult.score < 60){
            if(silentRate > bestSilentRate){
                evalResult.text = "すこし間が長いかも";
            }else{
                evalResult.text = "すこし間が短いかも";
            }
        }else if(60 <= evalResult.score && evalResult.score < 80){
            evalResult.text = "緩急をつけてみよう";
        }else if(80 <= evalResult.score && evalResult.score <= 100){
            evalResult.text = "完璧！";
        }

        return evalResult;
    }
}