package com.leandoer.entity;

public enum Score {
    AWFUL(1),
    BAD(2),
    GOOD_ENOUGH(3),
    GOOD(4),
    BEST(5);

    int numericValue;

    Score(int numericValue) {
        this.numericValue = numericValue;
    }

    public int getNumericValue() {
        return numericValue;
    }
}