package com.t4citus.adventofcode.v2022;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.List;

public class Day2Test extends AbstractTestBase {

    @Value("classpath:/2022/day2.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2022/day2.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final int sum = lines.stream()
                .map(Day2Test::parseRound)
                .mapToInt(round -> getScore(round.me) + getScore(round))
                .sum();

        // Then
        System.out.println("The sum of all rounds is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int sum = lines.stream()
                .map(Day2Test::parseRound)
                .mapToInt(round -> getScore(round.me) + getScore(round))
                .sum();

        // Then
        System.out.println("The sum of all rounds is " + sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final int sum = lines.stream()
                .map(Day2Test::parseRoundByOutcome)
                .mapToInt(round -> getScore(round.me) + getScore(round))
                .sum();

        // Then
        System.out.println("The sum of all rounds is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int sum = lines.stream()
                .map(Day2Test::parseRoundByOutcome)
                .mapToInt(round -> getScore(round.me) + getScore(round))
                .sum();

        // Then
        System.out.println("The sum of all rounds is " + sum);
    }

    private enum Shape {
        ROCK, PAPER, SCISSOR
    }

    private enum Outcome {
        WIN, DRAW, LOOSE
    }

    private record Round(Shape me, Shape other) {}

    private static Round parseRound(String line) {
        String[] parts = line.split(" ");
        return new Round(getShape(parts[1]), getShape(parts[0]));
    }

    private static Round parseRoundByOutcome(String line) {
        String[] parts = line.split(" ");
        Shape other = getShape(parts[0]);
        Outcome outcome = getOutcome(parts[1]);
        return new Round(getShapeByOutcome(other, outcome), other);
    }

    private static Shape getShape(final String s) {
        if (s.equals("A") || s.equals("X")) return Shape.ROCK;
        if (s.equals("B") || s.equals("Y")) return Shape.PAPER;
        if (s.equals("C") || s.equals("Z")) return Shape.SCISSOR;
        return null;
    }

    private static Outcome getOutcome(final String s) {
        if (s.equals("X")) return Outcome.LOOSE;
        if (s.equals("Y")) return Outcome.DRAW;
        if (s.equals("Z")) return Outcome.WIN;
        throw new IllegalStateException("Unable to parse outcome.");
    }

    private static Shape getShapeByOutcome(final Shape other, final Outcome outcome) {
        if (outcome == Outcome.DRAW) return other;

        if (other == Shape.ROCK && outcome == Outcome.WIN) return Shape.PAPER;
        if (other == Shape.ROCK && outcome == Outcome.LOOSE) return Shape.SCISSOR;

        if (other == Shape.PAPER && outcome == Outcome.WIN) return Shape.SCISSOR;
        if (other == Shape.PAPER && outcome == Outcome.LOOSE) return Shape.ROCK;

        if (other == Shape.SCISSOR && outcome == Outcome.WIN) return Shape.ROCK;
        if (other == Shape.SCISSOR && outcome == Outcome.LOOSE) return Shape.PAPER;

        throw new IllegalStateException("Unable to get shape by outcome.");
    }

    private static int getScore(final Shape shape) {
        if (shape == Shape.ROCK) return 1;
        if (shape == Shape.PAPER) return 2;
        if (shape == Shape.SCISSOR) return 3;
        return 0;
    }

    private static int getScore(final Round round) {
        final int won = 6;
        final int draw = 3;
        final int lost = 0;

        if (round.me == Shape.ROCK && round.other == Shape.SCISSOR) return won;
        if (round.me == Shape.ROCK && round.other == Shape.PAPER) return lost;

        if (round.me == Shape.PAPER && round.other == Shape.ROCK) return won;
        if (round.me == Shape.PAPER && round.other == Shape.SCISSOR) return lost;

        if (round.me == Shape.SCISSOR && round.other == Shape.PAPER) return won;
        if (round.me == Shape.SCISSOR && round.other == Shape.ROCK) return lost;

        return draw; // draw
    }
}
