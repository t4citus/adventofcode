package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day10Test extends AbstractTestBase {

    @Value("classpath:/2021/day10.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day10.sample.txt")
    private Resource samplePuzzleInputResource;

    private static final Map<Character, Integer> ERROR_POINTS = Map.of(
            ')', 3,
            ']', 57,
            '}', 1197,
            '>', 25137);

    private static final Map<Character, Integer> MISSING_POINTS = Map.of(
            ')', 1,
            ']', 2,
            '}', 3,
            '>', 4);

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        int errorScore = errorScore(lines);

        // Then
        System.out.println("The error score is " + errorScore);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        int errorScore = errorScore(lines);

        // Then
        System.out.println("The error score is " + errorScore);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        long middleScore = middleScore(lines);

        // Then
        System.out.println("The middle score is " + middleScore);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        long middleScore = middleScore(lines);

        // Then
        System.out.println("The middle score is " + middleScore);
    }

    private static boolean isOpening(char bracket) {
        return (bracket == '(' || bracket == '[' || bracket == '{' || bracket == '<');
    }

    private static boolean isClosing(char bracket) {
        return (bracket == ')' || bracket == ']' || bracket == '}' || bracket == '>');
    }

    private enum Status {VALID, INVALID, INCOMPLETE}

    private record CheckResult(Status status, Character invalid, List<Character> missing) {

        public static CheckResult invalid(Character invalid) {
            return new CheckResult(Status.INVALID, invalid, null);
        }

        public static CheckResult valid() {
            return new CheckResult(Status.VALID, null, null);
        }

        public static CheckResult incomplete(List<Character> missing) {
            return new CheckResult(Status.INCOMPLETE, null, missing);
        }
    }

    private static CheckResult checkSyntax(String line) {
        final Stack<Character> brackets = new Stack<>();
        Character invalid = null;
        boolean isInvalid = false;

        for (char curr : line.toCharArray()) {
            // is opening bracket
            if (isOpening(curr)) {
                brackets.push(curr);
            }
            // is closing bracket
            else if (isClosing(curr)) {
                char last = brackets.peek();
                if (last == '(' && curr == ')') {
                    brackets.pop(); // remove bracket if its closed immediately
                } else if (last == '[' && curr == ']') {
                    brackets.pop(); // remove bracket if its closed immediately
                } else if (last == '{' && curr == '}') {
                    brackets.pop(); // remove bracket if its closed immediately
                } else if (last == '<' && curr == '>') {
                    brackets.pop(); // remove bracket if its closed immediately
                } else {
                    invalid = curr;
                    isInvalid = true;
                    break;
                }
            }
        }

        if (isInvalid)
            return CheckResult.invalid(invalid);

        if (!brackets.isEmpty()) {
            List<Character> missing = new ArrayList<>();

            while (!brackets.isEmpty()) {
                char last = brackets.pop();
                char found = switch (last) {
                    case '(' -> ')';
                    case '[' -> ']';
                    case '{' -> '}';
                    case '<' -> '>';
                    default -> throw new IllegalStateException("Unexpected value: " + last);
                };
                missing.add(found);
            }

            return CheckResult.incomplete(missing);
        }

        return CheckResult.valid();
    }

    private static int errorScore(final List<String> lines) {
        return lines.stream()
                .map(Day10Test::checkSyntax)
                .filter(res -> res.status() == Status.INVALID)
                .map(inv -> ERROR_POINTS.getOrDefault(inv.invalid(), 0))
                .reduce(Integer::sum)
                .orElse(0);
    }

    /**
     * Returns the score of a single line of missing brackets.
     */
    private static long score(final List<Character> missing) {
        long score = 0;
        for (Character ch : missing) {
            score = score * 5 + MISSING_POINTS.getOrDefault(ch, 0);
        }
        return score;
    }

    private static long middleScore(final List<String> lines) {
        final List<Long> sortedScores = lines.stream()
                .map(Day10Test::checkSyntax)
                .filter(res -> res.status() == Status.INCOMPLETE)
                .map(res -> score(res.missing()))
                .sorted(Comparator.naturalOrder())
                .toList();
        final int middle = (sortedScores.size() + 1) / 2;
        return sortedScores.get(middle - 1);
    }
}
