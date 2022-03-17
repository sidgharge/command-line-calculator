package com.homeprojects.commandlinecalculator.parser;

public record NumberExpression(String value, int startIndex, int endIndex) implements BinaryExpression {

    @Override
    public int evaluate() {
        return Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
