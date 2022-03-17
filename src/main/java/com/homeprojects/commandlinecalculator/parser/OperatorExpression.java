package com.homeprojects.commandlinecalculator.parser;

import com.homeprojects.commandlinecalculator.lexer.Operator;

public record OperatorExpression(Operator operator, BinaryExpression left, BinaryExpression right, int startIndex, int endIndex) implements BinaryExpression {

    @Override
    public int evaluate() {
        return operator.operate(left, right);
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator.getValue() + " " + right.toString();
    }
}
