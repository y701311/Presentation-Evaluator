package com.model;

import org.jtransforms.fft.DoubleFFT_1D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utility {

    // 配列の実効値を返す
    static double getRms(double[] array) {
        double rms = 0;

        // 二乗和
        for (double value : array) {
            rms += value * value;
        }
        // 平均
        rms /= array.length;
        // 平方根
        rms = Math.sqrt(rms);

        return rms;
    }

    // 人間の最小可聴音である20[μPa]を基準としたデシベルを返す
    static double getDecibel(double rms) {
        final double Base = 0.00002;
        if(rms / Base <= 1){
            return 0;
        }else{
            return 20 * Math.log10(rms / Base);
        }
    }

    static double[] getFormant(double[] d, int samplingRate) {
        double[] data = Arrays.copyOf(d, d.length);
        new DoubleFFT_1D(data.length).realForward(data);
        double[] power = new double[data.length / 2];
        double power_avg = 0;
        List<Integer> formant = new ArrayList<>();
        for (int i = 0; i < power.length; ++i) {
            double re = data[2 * i];
            double im = data[2 * i + 1];
            power[i] = Math.sqrt(re * re + im * im);
            power_avg += power[i];
        }
        power_avg /= power.length;

        for (int i = 0; i < power.length / 5; ++i)
            if (power[i] > power_avg * 8) {
                formant.add(i);
                i += 75 / (samplingRate / power.length);
            }

        if (formant.size() <= 1) return new double[]{-1, -1};
        else return new double[]{
                formant.get(0) * (samplingRate / power.length),
                formant.get(1) * (samplingRate / power.length)};
    }
}
