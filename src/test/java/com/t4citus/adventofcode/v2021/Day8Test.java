package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day8Test extends AbstractTestBase {

    @Value("classpath:/2021/day8.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day8.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final List<Signals> signals = lines.stream()
                .map(Day8Test::parseLine)
                .toList();
        final Integer count = signals.stream()
                .map(Signals::expectedDigitsCount)
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println("The total count of expected digits is " + count);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final List<Signals> signals = lines.stream()
                .map(Day8Test::parseLine)
                .toList();
        final Integer count = signals.stream()
                .map(Signals::expectedDigitsCount)
                .reduce(Integer::sum)
                .orElse(0);

        // Then
        System.out.println("The total count of expected digits is " + count);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Integer totalSum = lines.stream()
                .map(Day8Test::parseLine)
                .map(Signals::outputNumber)
                .reduce(Integer::sum)
                .orElseThrow(IllegalStateException::new);

        // Then
        System.out.println("The total number is " + totalSum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Integer totalSum = lines.stream()
                .map(Day8Test::parseLine)
                .map(Signals::outputNumber)
                .reduce(Integer::sum)
                .orElseThrow(IllegalStateException::new);

        // Then
        System.out.println("The total number is " + totalSum);
    }

    private static Signals parseLine(final String line) {
        final String[] parts = line.split("\\|");

        List<String> uniqueSignals = Arrays.stream(parts[0].trim().split(" "))
                .map(String::trim)
                .toList();

        List<String> outputSignals = Arrays.stream(parts[1].trim().split(" "))
                .map(String::trim)
                .toList();

        return new Signals(uniqueSignals, outputSignals);
    }

    private record Signals(List<String> uniqueSignals, List<String> outputSignals) {
        public int expectedDigitsCount() {
            // 1 = 2 digits, 4 = 4 digits, 7 = 3 digits, or 8 = 7 digits
            Set<Integer> digitLengths = Set.of(2, 4, 3, 7);
            int count = 0;
            for (String signal : outputSignals) {
                if (digitLengths.contains(signal.length())) {
                    count++;
                }
            }
            return count;
        }

        public int outputNumber() {
            final Map<Integer, List<Set<String>>> sortedByLength = new HashMap<>();

            // init
            for (String uniqueSignal : uniqueSignals()) {
                int len = uniqueSignal.length();
                List<Set<String>> stored = sortedByLength.getOrDefault(len, new ArrayList<>());
                stored.add(Set.of(uniqueSignal.split("")));
                sortedByLength.put(len, stored);
            }

            final Set<String> one = sortedByLength.get(2).get(0);
            final Set<String> four = sortedByLength.get(4).get(0);
            final Set<String> seven = sortedByLength.get(3).get(0);
            final Set<String> eight = sortedByLength.get(7).get(0);

            // 9 is the only number with length of 6 that contains all elements from 4
            final Set<String> nine = sortedByLength.get(6).stream()
                    .filter(candidate -> candidate.containsAll(four))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

            // 3 is the only number with length of 5 that contains all elements from 7
            final Set<String> three = sortedByLength.get(5).stream()
                    .filter(candidate -> candidate.containsAll(seven))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

            // 5 is the only number with length of 5 that contains all elements of 9 minus the elements of 1
            final Set<String> five = sortedByLength.get(5).stream()
                    .filter(candidate -> candidate.containsAll(new FluentSet(nine).minus(one).get()))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);
            sortedByLength.get(5).remove(five);

            // 6 is the only number with length of 6 that contains all elements of 8 minus the elements of 1
            final Set<String> six = sortedByLength.get(6).stream()
                    .filter(candidate -> candidate.containsAll(new FluentSet(eight).minus(one).get()))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);
            sortedByLength.get(6).remove(six);

            // 0 is the only number with length of 6 that contains all elements of 8 minus the elements of 4
            // plus the elements of 1
            final Set<String> zero = sortedByLength.get(6).stream()
                    .filter(candidate -> candidate.containsAll(new FluentSet(eight).minus(four).plus(one).get()))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

            // 2 is the only number with the length of 5 that contains all elements of 8 minus the elements of 4
            final Set<String> two = sortedByLength.get(5).stream()
                    .filter(candidate -> candidate.containsAll(new FluentSet(eight).minus(four).get()))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new);

            // resolve output number
            int outputNumber = 0;
            final List<Set<String>> values = List.of(zero, one, two, three, four, five, six, seven, eight, nine);
            final int[] multiplier = new int[] { 1000, 100, 10, 1 };

            for (int i = 0; i < outputSignals().size(); i++) {
                final Set<String> target = Set.of(outputSignals().get(i).split(""));
                for (int j = 0; j < values.size(); j++) {
                    if (target.equals(values.get(j))) {
                        outputNumber += j * multiplier[i];
                        break;
                    }
                }
            }

            return outputNumber;
        }
    }

    /**
     * Fluent interface for a set to ease inline initialization
     */
    private static class FluentSet {
        private final Set<String> set;

        public FluentSet(final Set<String> initialSet) {
            this.set = new HashSet<>(initialSet);
        }

        public FluentSet plus(final Set<String> plusSet) {
            set.addAll(plusSet);
            return this;
        }

        public FluentSet minus(final Set<String> minusSet) {
            set.removeAll(minusSet);
            return this;
        }

        public Set<String> get() {
            return set;
        }
    }
}
