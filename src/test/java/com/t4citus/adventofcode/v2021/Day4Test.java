package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

public class Day3Test extends AbstractTestBase {

    @Value("classpath:/2021/day3.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = Arrays.asList(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010"
        );

        // When
        final Diagnostics diagnostics = gammaAndEpsilonRates(lines);
        final int powerConsumption = diagnostics.gamma() * diagnostics.epsilon();

        // Then
        System.out.println("The power consumption is " + powerConsumption);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        final Diagnostics diagnostics = gammaAndEpsilonRates(lines);
        final int powerConsumption = diagnostics.gamma() * diagnostics.epsilon();

        // Then
        System.out.println("The power consumption is " + powerConsumption);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = Arrays.asList(
                "00100",
                "11110",
                "10110",
                "10111",
                "10101",
                "01111",
                "00111",
                "11100",
                "10000",
                "11001",
                "00010",
                "01010"
        );

        // When
        final Diagnostics diagnostics = oxygenGeneratorAndScrubberRatings(lines);
        final int lifeSupportRating = diagnostics.oxygen() * diagnostics.scrubber();

        // Then
        System.out.println("The life support rating is " + lifeSupportRating);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        final Diagnostics diagnostics = oxygenGeneratorAndScrubberRatings(lines);
        final int lifeSupportRating = diagnostics.oxygen() * diagnostics.scrubber();

        // Then
        System.out.println("The life support rating is " + lifeSupportRating);
    }

    private record Diagnostics(int first, int second) {
        int gamma() {
            return first;
        }

        int epsilon() {
            return second;
        }

        int oxygen() {
            return first;
        }

        int scrubber() {
            return second;
        }
    }

    private static Diagnostics gammaAndEpsilonRates(final List<String> binaries) {
        int n = binaries.get(0).length();
        int[] zeroCounts = new int[n];
        int[] oneCounts = new int[n];

        for (String binary : binaries) {
            char[] chars = binary.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '1') {
                    oneCounts[i] += 1;
                } else {
                    zeroCounts[i] += 1;
                }
            }
        }

        int[] gammaBits = new int[n];
        int[] epsilonBits = new int[n];

        for (int i = 0; i < n; i++) {
            if (oneCounts[i] > zeroCounts[i]) {
                gammaBits[i] = 1;
                epsilonBits[i] = 0;
            } else {
                gammaBits[i] = 0;
                epsilonBits[i] = 1;
            }
        }

        int gamma = Integer.parseInt(toString(gammaBits), 2);
        int epsilon = Integer.parseInt(toString(epsilonBits), 2);
        return new Diagnostics(gamma, epsilon);
    }

    private static String toString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i : arr) {
            sb.append(i);
        }
        return sb.toString();
    }

    private static Diagnostics oxygenGeneratorAndScrubberRatings(List<String> lines) {
        final int n = lines.get(0).length();
        int oxygen = 0;
        int scrubber = 0;

        List<String> binaries = lines;
        for (int index = 0; index < n; index++) {
            Counts counts = countOnesAndZeros(binaries, index);
            binaries = (counts.ones() >= counts.zeros())
                    ? filter(binaries, index, '1')
                    : filter(binaries, index, '0');
            if (binaries.size() == 1) {
                oxygen = Integer.parseInt(binaries.get(0), 2);
            }
        }

        // reset list
        binaries = lines;
        for (int index = 0; index < n; index++) {
            Counts counts = countOnesAndZeros(binaries, index);
            binaries = (counts.ones() < counts.zeros())
                    ? filter(binaries, index, '1')
                    : filter(binaries, index, '0');
            if (binaries.size() == 1) {
                scrubber = Integer.parseInt(binaries.get(0), 2);
            }
        }

        return new Diagnostics(oxygen, scrubber);
    }

    private static List<String> filter(final List<String> binaries, final int index, final char expectedAtIndex) {
        return binaries.stream()
                .filter(bin -> bin.charAt(index) == expectedAtIndex)
                .toList();
    }

    private record Counts(int ones, int zeros) {
    }

    private static Counts countOnesAndZeros(final List<String> binaries, final int index) {
        int zeroCount = 0;
        int oneCount = 0;

        for (String bin : binaries) {
            char ch = bin.charAt(index);
            if (ch == '1') oneCount++;
            else if (ch == '0') zeroCount++;
        }

        return new Counts(oneCount, zeroCount);
    }
}
