package com.homeprojects.commandlinecalculator.parser;

import com.homeprojects.commandlinecalculator.lexer.Lexer;
import com.homeprojects.commandlinecalculator.lexer.Operator;
import com.homeprojects.commandlinecalculator.lexer.Token;
import com.homeprojects.commandlinecalculator.lexer.TokenType;

import java.util.List;

import static com.homeprojects.commandlinecalculator.lexer.Operator.isPriority;
import static com.homeprojects.commandlinecalculator.lexer.TokenType.NUMBER;
import static com.homeprojects.commandlinecalculator.lexer.TokenType.OPERATOR;

public class Parser {

    private final String expression;

    private List<Token> tokens;

    private int index;

    public Parser(String expression) {
        this.expression = expression;
        this.index = 0;
    }

    public BinaryExpression parse() {
        tokens = new Lexer(expression).tokenize();

        BinaryExpression left = getNumberExpression();
        if (left == null) {
            throw new IllegalArgumentException("Expected number");
        }
        return parse(left);
    }

    private BinaryExpression parse(BinaryExpression left) {
        Token operatorToken = nextToken(OPERATOR);
        if (operatorToken == null) {
            return left;
        }
        BinaryExpression right = getNumberExpression();

        Token nextOperatorToken = peekToken(OPERATOR);
        if (!isPriority(operatorToken.value()) && nextOperatorToken != null && isPriority(nextOperatorToken.value())) {
            right = parse(right);
        }
        BinaryExpression operator = buildOperatorExpression(left, operatorToken, right);
        return parse(operator);
    }

    private OperatorExpression buildOperatorExpression(BinaryExpression left, Token operatorToken, BinaryExpression right) {
        return new OperatorExpression(Operator.from(operatorToken.value()), left, right, operatorToken.startIndex(), operatorToken.endIndex());
    }

    private BinaryExpression getNumberExpression() {
        Token number = nextToken(NUMBER);
        if (number == null) {
            return null;
        }
        return new NumberExpression(number.value(), number.startIndex(), number.endIndex());
    }

    private Token nextToken(TokenType expectedType) {
        if (index >= tokens.size()) {
            return null;
        }
        Token token = tokens.get(index++);
        if (!token.type().equals(expectedType)) {
            throw new IllegalArgumentException("Invalid character");
        }
        return token;
    }

    private Token peekToken(TokenType expectedType) {
        if (index >= tokens.size()) {
            return null;
        }
        Token token = tokens.get(index);
        if (!token.type().equals(expectedType)) {
            throw new IllegalArgumentException("Invalid character at " + token.startIndex());
        }
        return token;
    }
}
