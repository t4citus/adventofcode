package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day12Test extends AbstractTestBase {

    @Value("classpath:/2021/day12.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day12.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<String, List<String>> paths = parsePaths(lines);
        final int pathCount = countPaths(paths);

        // Then
        System.out.println("The total number of paths is " + pathCount);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<String, List<String>> paths = parsePaths(lines);
        final int pathCount = countPaths(paths);

        // Then
        System.out.println("The total number of paths is " + pathCount);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Map<String, List<String>> paths = parsePaths(lines);
        final int pathCount = countPathsWithDoubleVisit(paths);

        // Then
        System.out.println("The total number of paths is " + pathCount);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Map<String, List<String>> paths = parsePaths(lines);
        final int pathCount = countPathsWithDoubleVisit(paths);

        // Then
        System.out.println("The total number of paths is " + pathCount);
    }

    private static Map<String, List<String>> parsePaths(final List<String> lines) {
        final Map<String, List<String>> paths = new HashMap<>();

        for (String line : lines) {
            final String[] split = line.split("-");
            final String start = split[0];
            final String end = split[1];
            paths.putIfAbsent(start, new ArrayList<>());
            paths.putIfAbsent(end, new ArrayList<>());
            paths.get(start).add(end);
            paths.get(end).add(start);
        }

        paths.remove("end");

        return paths;
    }

    private static int countPaths(final Map<String, List<String>> paths) {
        final List<List<String>> pathPermutations = new ArrayList<>();

        final Queue<List<String>> pathQueue = new LinkedList<>();
        pathQueue.add(List.of("start"));

        while (!pathQueue.isEmpty()) {
            final List<String> curr = pathQueue.poll();
            final String last = curr.get(curr.size() - 1);

            if (last.equals("end")) {
                pathPermutations.add(curr);
                continue;
            }

            final List<String> neighbors = paths.get(last);

            for (String neighbor : neighbors) {
                if (isSmallCave(neighbor)) {
                    if (!curr.contains(neighbor)) {
                        pathQueue.add(copyOf(curr, neighbor));
                    }
                } else {
                    pathQueue.add(copyOf(curr, neighbor));
                }
            }
        }

        return pathPermutations.size();
    }

    private static int countPathsWithDoubleVisit(final Map<String, List<String>> paths) {
        final List<List<String>> pathPermutations = new ArrayList<>();

        final Queue<List<String>> pathQueue = new LinkedList<>();
        pathQueue.add(List.of("start"));

        final Queue<Boolean> mayDoubleVisitQueue = new LinkedList<>();
        mayDoubleVisitQueue.add(Boolean.TRUE);

        while (!pathQueue.isEmpty()) {
            final List<String> curr = pathQueue.poll();
            final String last = curr.get(curr.size() - 1);
            final Boolean mayDoubleVisit = mayDoubleVisitQueue.poll();

            if (last.equals("end")) {
                pathPermutations.add(curr);
                continue;
            }

            final List<String> neighbors = paths.get(last);

            for (String neighbor : neighbors) {
                if ("start".equals(neighbor)) {
                    continue;
                }
                if (isSmallCave(neighbor)) {
                    if (!curr.contains(neighbor)) {
                        pathQueue.add(copyOf(curr, neighbor));
                        mayDoubleVisitQueue.add(mayDoubleVisit); // keep since it was the first visit
                    } else if (mayDoubleVisit == Boolean.TRUE) {
                        pathQueue.add(copyOf(curr, neighbor));
                        mayDoubleVisitQueue.add(Boolean.FALSE);
                    }
                } else {
                    pathQueue.add(copyOf(curr, neighbor));
                    mayDoubleVisitQueue.add(mayDoubleVisit); // keep since big caves can be visited unlimited times
                }
            }
        }

        return pathPermutations.size();
    }

    private static boolean isSmallCave(final String name) {
        return name.toLowerCase().equals(name);
    }

    private static List<String> copyOf(final List<String> list, final String element) {
        final List<String> newList = new ArrayList<>(List.copyOf(list));
        newList.add(element);
        return newList;
    }
}
