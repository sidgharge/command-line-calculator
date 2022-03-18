package com.homeprojects.commandlinecalculator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidationTests {

    private final CommandLineCalculator calculator = new CommandLineCalculator();

    @Test
    public void noNumberAfterOperator() {
        assertThatThrownBy(() -> calculator.evaluate("5 + "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("number or opening bracket");
    }

    @Test
    public void noNumberAfterOperator2() {
        assertThatThrownBy(() -> calculator.evaluate("5 + 3 -"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("number or opening bracket");
    }

    @Test
    public void startsWithNumberOrBracket() {
        assertThatThrownBy(() -> calculator.evaluate("-5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("number or opening bracket");
    }

    @Test
    public void incompleteBrackets() {
        assertThatThrownBy(() -> calculator.evaluate("(5 + 2 - 3"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("bracket")
                .hasMessageContaining("not")
                .hasMessageContaining("closed");
    }
}
