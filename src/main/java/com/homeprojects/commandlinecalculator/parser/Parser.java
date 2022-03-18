package com.homeprojects.commandlinecalculator.parser;

import com.homeprojects.commandlinecalculator.lexer.Lexer;
import com.homeprojects.commandlinecalculator.lexer.Operator;
import com.homeprojects.commandlinecalculator.lexer.Token;
import com.homeprojects.commandlinecalculator.lexer.TokenType;

import java.util.List;
import java.util.Set;

import static com.homeprojects.commandlinecalculator.lexer.Operator.isPriority;
import static com.homeprojects.commandlinecalculator.lexer.TokenType.*;

public class Parser {

    private final String expression;

    private List<Token> tokens;

    private int index;

    private int numberOfOpenBrackets;

    public Parser(String expression) {
        this.expression = expression;
        this.index = 0;
        this.numberOfOpenBrackets = 0;
    }

    public BinaryExpression parse() {
        tokens = new Lexer(expression).tokenize();
        BinaryExpression binaryExpression = parseInternal();
        if (numberOfOpenBrackets > 0) {
            throw new IllegalArgumentException(numberOfOpenBrackets + " brackets are not closed");
        }
        return binaryExpression;
    }

    private BinaryExpression parseInternal() {
        BinaryExpression left = getNumberOrBracketExpression();
        if (left == null) {
            throw new IllegalArgumentException("Expected number");
        }
        return parse(left);
    }

    private BinaryExpression parse(BinaryExpression left) {
        Token token = nextToken();
        if (token == null) {
            return left;
        }
        if (token.type().equals(CLOSED_BRACKET)) {
            numberOfOpenBrackets--;
            return left;
        }

        BinaryExpression right = getNumberOrBracketExpression();
        Token nextOperatorToken = peekToken(OPERATOR, CLOSED_BRACKET);
        if (!isPriority(token.value()) && nextOperatorToken != null && Operator.isOperator(nextOperatorToken.value().charAt(0)) && isPriority(nextOperatorToken.value())) {
            right = parse(right);
        }
        BinaryExpression operator = buildOperatorExpression(left, token, right);
        return parse(operator);
    }

    private OperatorExpression buildOperatorExpression(BinaryExpression left, Token operatorToken, BinaryExpression right) {
        return new OperatorExpression(Operator.from(operatorToken.value()), left, right, operatorToken.startIndex(), operatorToken.endIndex());
    }

    private BinaryExpression getNumberOrBracketExpression() {
        Token token = nextToken();
        if (token == null) {
            throw new IllegalArgumentException("Expected a number or opening bracket at the end");
        }
        if (token.type().equals(NUMBER)) {
            return new NumberExpression(token.value(), token.startIndex(), token.endIndex());
        }
        if (token.type().equals(OPEN_BRACKET)) {
            numberOfOpenBrackets++;
            return parseInternal();
        }
        throw new IllegalArgumentException("Unexpected token at " + token.startIndex() + ". Expected a number or opening bracket");
    }

    private Token nextToken(TokenType expectedType) {
        Token token = nextToken();
        if (token == null) {
            return null;
        }
        if (!token.type().equals(expectedType)) {
            throw new IllegalArgumentException("Invalid character");
        }
        return token;
    }

    private Token nextToken() {
        if (index >= tokens.size()) {
            return null;
        }
        return tokens.get(index++);
    }

    private Token peekToken(TokenType... expectedTypes) {
        if (index >= tokens.size()) {
            return null;
        }
        Token token = tokens.get(index);
        if (!Set.of(expectedTypes).contains(token.type())) {
            throw new IllegalArgumentException("Invalid character at " + token.startIndex());
        }
        return token;
    }
}
