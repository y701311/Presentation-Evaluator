package com.model;

import android.util.Pair;

import java.util.ArrayList;

abstract class Evaluator {
    private ArrayList<Double> evalValue;

    abstract void calculation(double[] audioData);

    abstract Pair<Double, String> returnResult();
}