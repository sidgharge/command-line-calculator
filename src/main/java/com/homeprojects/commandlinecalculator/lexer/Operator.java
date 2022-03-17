package com.homeprojects.commandlinecalculator.lexer;

import com.homeprojects.commandlinecalculator.parser.BinaryExpression;

import java.util.Set;

public enum Operator {

    PLUS('+') {
        @Override
        public int operate(BinaryExpression left, BinaryExpression right) {
            return left.evaluate() + right.evaluate();
        }
    },

    MINUS('-') {
        @Override
        public int operate(BinaryExpression left, BinaryExpression right) {
            return left.evaluate() - right.evaluate();
        }
    },
    MULTIPLY('*') {
        @Override
        public int operate(BinaryExpression left, BinaryExpression right) {
            return left.evaluate() * right.evaluate();
        }
    },
    DIVIDE('/') {
        @Override
        public int operate(BinaryExpression left, BinaryExpression right) {
            return left.evaluate() / right.evaluate();
        }
    };

    private final char value;

    Operator(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public static Operator from(String value) {
        return switch (value) {
            case "+" -> PLUS;
            case "-" -> MINUS;
            case "*" -> MULTIPLY;
            case "/" -> DIVIDE;
            default -> throw new RuntimeException(value + " is not an operator");
        };
    }

    public static boolean isPriority(String value) {
        return value.equals("*") || value.equals("/");
    }

    public abstract int operate(BinaryExpression left, BinaryExpression right);

    private static final Set<Character> OPERATORS = Set.of('+', '-', '*', '/');

    public static boolean isOperator(char ch) {
        return OPERATORS.contains(ch);
    }
}
