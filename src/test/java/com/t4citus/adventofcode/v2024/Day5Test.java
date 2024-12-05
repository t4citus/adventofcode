package com.t4citus.adventofcode.v2024;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day5Test extends AbstractTestBase {

    @Value("classpath:/2024/day5.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2024/day5.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final PrintRules printRules = parsePrintRules(lines);
        System.out.println(printRules.orderingRules());
        final int sum = printRules.manualPagesList().stream()
                .filter(mp -> isInCorrectOrder(mp, printRules.orderingRules()))
                .mapToInt(Day5Test::middle)
                .sum();

        // Then
        System.out.println("The sum of all middle values of correct pages is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final PrintRules printRules = parsePrintRules(lines);
        final int sum = printRules.manualPagesList().stream()
                .filter(mp -> isInCorrectOrder(mp, printRules.orderingRules()))
                .mapToInt(Day5Test::middle)
                .sum();

        // Then
        System.out.println("The sum of all middle values of correct pages is " + sum);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final PrintRules printRules = parsePrintRules(lines);
        final int sum = printRules.manualPagesList().stream()
                .filter(mp -> !isInCorrectOrder(mp, printRules.orderingRules()))
                .map(mp -> correctManualPageOrder(mp, printRules.orderingRules()))
                .mapToInt(Day5Test::middle)
                .sum();

        // Then
        System.out.println("The sum of all middle values of the corrected pages is " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final PrintRules printRules = parsePrintRules(lines);
        final int sum = printRules.manualPagesList().stream()
                .filter(mp -> !isInCorrectOrder(mp, printRules.orderingRules()))
                .map(mp -> correctManualPageOrder(mp, printRules.orderingRules()))
                .mapToInt(Day5Test::middle)
                .sum();

        // Then
        System.out.println("The sum of all middle values of the corrected pages is " + sum);
    }

    private record PrintRules(Map<Integer, Set<Integer>> orderingRules, List<List<Integer>> manualPagesList) {
    }

    private static PrintRules parsePrintRules(final List<String> lines) {

        Map<Integer, Set<Integer>> orderingRules = new HashMap<>();
        List<List<Integer>> manualPagesList = new ArrayList<>();
        boolean isFirstSection = true;

        for (String line : lines) {
            if (line.isEmpty() || line.isBlank()) {
                isFirstSection = false;
                continue;
            }
            if (isFirstSection) {
                String[] split = line.split("\\|");
                Integer key = Integer.parseInt(split[0]);
                Integer value = Integer.parseInt(split[1]);
                orderingRules.putIfAbsent(key, new HashSet<>());
                orderingRules.get(key).add(value);
            } else {
                manualPagesList.add(Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .toList());
            }
        }

        return new PrintRules(orderingRules, manualPagesList);
    }

    private static boolean isInCorrectOrder(final List<Integer> manualPages, final Map<Integer, Set<Integer>> orderingRules) {
        final int n = manualPages.size();
        for (int i = 0; i < n - 1; i++) {
            Integer from = manualPages.get(i);
            Integer to = manualPages.get(i + 1);
            if (!orderingRules.getOrDefault(from, Set.of()).contains(to)) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> correctManualPageOrder(final List<Integer> manualPages, final Map<Integer, Set<Integer>> orderingRules) {
        final LinkedList<Integer> corrected = new LinkedList<>();

        for (Integer pageNo : manualPages) {
            if (corrected.isEmpty()) {
                corrected.add(pageNo);
                continue;
            }
            final Set<Integer> allowed = orderingRules.getOrDefault(pageNo, Set.of());
            Integer foundIndex = null;
            for (int i = 0; i < corrected.size(); i++) {
                if (allowed.contains(corrected.get(i))) {
                    foundIndex = i;
                    break; // exit loop if the first index is found
                }
            }
            // In case an insert index is found, insert it. Append it otherwise.
            if (foundIndex != null) {
                corrected.add(foundIndex, pageNo);
            } else {
                corrected.add(pageNo);
            }
        }

        return corrected;
    }

    private static Integer middle(final List<Integer> manualPages) {
        final int n = manualPages.size();
        final int mid = (n - 1) / 2;
        return manualPages.get(mid);
    }
}
