package com.homeprojects.commandlinecalculator;

import com.homeprojects.commandlinecalculator.parser.Parser;

import java.util.Scanner;

public class CommandLineCalculator {

    public int evaluate(String expression) {
        return new Parser(expression).parse().evaluate();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String expression = scanner.nextLine();
            int result = new CommandLineCalculator().evaluate(expression);
            System.out.println(result);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        scanner.close();
    }
}
