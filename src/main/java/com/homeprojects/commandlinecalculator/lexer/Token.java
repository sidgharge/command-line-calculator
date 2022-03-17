package com.homeprojects.commandlinecalculator.lexer;

public record Token(String value, TokenType type, int startIndex, int endIndex) {

    @Override
    public String toString() {
        return " " + value + " ";
    }
}
