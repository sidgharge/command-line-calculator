package com.homeprojects.commandlinecalculator;

import com.homeprojects.commandlinecalculator.parser.Parser;

public class CommandLineCalculator {

    public int evaluate(String expression) {
        return new Parser(expression).parse().evaluate();
    }
}
