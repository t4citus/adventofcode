package com.t4citus.adventofcode.v2019;

import com.t4citus.adventofcode.AbstractTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day2Test extends AbstractTestBase {

    @Value("classpath:/2019/day2.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2019/day2.sample.txt")
    private Resource samplePuzzleInputResource;

    private static Stream<Arguments> intCodeTestCases() {
        return Stream.of(
                Arguments.of(0, 3500),
                Arguments.of(1, 2),
                Arguments.of(2, 2),
                Arguments.of(3, 2),
                Arguments.of(4, 30)
        );
    }

    @ParameterizedTest
    @MethodSource("intCodeTestCases")
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected(final int lineNo, final int expectedFirst) {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final int[] codes = parseCodes(lines.get(lineNo));
        final int first = first(codes);

        // Then
        System.out.println("The value at position 0 is " + first);
        Assertions.assertThat(first).isEqualTo(expectedFirst);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int[] codes = parseCodes(lines.get(0));
        // add 1202 information
        codes[1] = 12;
        codes[2] = 2;
        final int first = first(codes);

        // Then
        System.out.println("The value at position 0 is " + first);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final int[] codes = parseCodes(lines.get(0));
        final int expectedOutput = 19690720;
        int noun = 0, outputForNoun = 0;

        // Note the verb increases the result only by 1 in comparison to the noun. That's why a loop is used to
        // determine the noun first until the result is bigger than the expected output.
        for (int i = 0; i < 1000; i++) {
            final int[] copied = Arrays.copyOf(codes, codes.length);
            copied[1] = i;
            final int first = first(copied);
            if (first > expectedOutput) break;
            noun = i;
            outputForNoun = first;
        }

        // The verb is just the difference between the output for the noun and the expected output.
        final int verb = expectedOutput - outputForNoun;
        final int output = 100 * noun + verb;

        // Then
        System.out.println("The noun is " + noun + " and the verb is " + verb + ". The output is " + output);
    }

    private static int[] parseCodes(final String line) {
        return Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    private static int first(final int[] codes) {
        int index = 0;
        final int n = codes.length;

        while (index < n) {
            int opCode = codes[index];

            if (opCode == 99) {
                break;
            }

            if (opCode == 1) {
                int pos1 = codes[index + 1];
                int pos2 = codes[index + 2];
                int pos3 = codes[index + 3];
                codes[pos3] = codes[pos1] + codes[pos2];
                index += 4;
                continue;
            }

            if (opCode == 2) {
                int pos1 = codes[index + 1];
                int pos2 = codes[index + 2];
                int pos3 = codes[index + 3];
                codes[pos3] = codes[pos1] * codes[pos2];
                index += 4;
                continue;
            }

            break;
        }

        // System.out.println(Arrays.toString(codes));
        return codes[0];
    }
}
