package com.model;

public class Utility {

    // 配列の実効値を返す
    static double getRms(double[] array) {
        double rms = 0;

        // 二乗和
        for(double value : array){
            rms += value * value;
        }
        // 平均
        rms /= array.length;
        // 平方根
        rms = Math.sqrt(rms);

        return rms;
    }

}
