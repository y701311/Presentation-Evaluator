package com.model;

import java.util.ArrayList;

abstract class Evaluator {
    private ArrayList<Double> evalValue;

    abstract void calculation(double[] audioData);

    abstract double returnResult();
}