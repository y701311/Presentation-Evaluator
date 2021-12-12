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
    EvalResult returnResult() {
        EvalResult evalResult = new EvalResult();
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
            evalResult.score = 100 - ((bestSilentRate - silentRate) / bestSilentRate) * 100;
            evalResult.evalDirection = evalResult.small;
        }else if(silentRate >= bestSilentRate){
            evalResult.score = 100 - ((silentRate - bestSilentRate) / (1 - bestSilentRate)) * 100;
            evalResult.evalDirection = evalResult.large;
        }

        if(0 <= evalResult.score && evalResult.score < 20){
            if(silentRate > bestSilentRate){
                evalResult.text = "長すぎ";
            }else{
                evalResult.text = "短すぎ";
            }
        }else if(20 <= evalResult.score && evalResult.score < 40){
            if(silentRate > bestSilentRate){
                evalResult.text = "長い";
            }else{
                evalResult.text = "短い";
            }
        }else if(40 <= evalResult.score && evalResult.score < 60){
            if(silentRate > bestSilentRate){
                evalResult.text = "ちょっと長い";
            }else{
                evalResult.text = "ちょっと短い";
            }
        }else if(60 <= evalResult.score && evalResult.score < 80){
            evalResult.text = "いい感じ";
        }else if(80 <= evalResult.score && evalResult.score <= 100){
            evalResult.text = "ばっちり！";
        }

        return evalResult;
    }
}