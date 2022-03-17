package com.homeprojects.commandlinecalculator.lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String expression;

    private int index;

    private final List<Token> tokens;

    public Lexer(String expression) {
        this.expression = expression;
        index = 0;
        tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (true) {
            Token token = nextToken();
            if (token == null) {
                break;
            }
            tokens.add(token);
        }
        return tokens;
    }

    private Token nextToken() {
        if (index >= expression.length()) {
            return null;
        }
        if (Character.isWhitespace(expression.charAt(index))) {
            index++;
            return nextToken();
        }
        if (Character.isDigit(expression.charAt(index))) {
            return getNumberToken();
        }
        if (Operator.isOperator(expression.charAt(index))) {
            return getOperatorToken();
        }
        if (expression.charAt(index) == '(') {
            index++;
            return new Token("(", TokenType.OPEN_BRACKET, index - 1, index - 1);
        }
        if (expression.charAt(index) == ')') {
            index++;
            return new Token(")", TokenType.CLOSED_BRACKET, index - 1, index - 1);
        }
        throw new IllegalArgumentException("Unexpected token at " + index);
    }

    private Token getOperatorToken() {
        Token token = new Token(String.valueOf(expression.charAt(index)), TokenType.OPERATOR, index, index);
        index++;
        return token;
    }

    private Token getNumberToken() {
        int start = index;
        while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
            index++;
        }
        int end = index - 1;
        return new Token(expression.substring(start, end + 1), TokenType.NUMBER, start, end);
    }
}
