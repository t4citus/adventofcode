package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day6Test extends AbstractTestBase {

    @Value("classpath:/2023/day6.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput();

        // When
        List<Race> races = parseRaces(lines);
        System.out.println(races);

        long mult = 1;

        for (Race race : races) {
            long winCount = 0;
            for (long holdingTime = 0; holdingTime <= race.time; holdingTime++) {
                if (isWayToWin(holdingTime, race)) {
                    winCount++;
                }
            }
            mult *= winCount;
        }

        // Then
        System.out.println(mult);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 2);

        // When
        List<Race> races = parseRaces(lines);
        System.out.println(races);

        long mult = 1;

        for (Race race : races) {
            long winCount = 0;
            for (long holdingTime = 0; holdingTime <= race.time; holdingTime++) {
                if (isWayToWin(holdingTime, race)) {
                    winCount++;
                }
            }
            mult *= winCount;
        }

        // Then
        System.out.println(mult);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput();

        // When
        Race race = parseRace(lines);
        System.out.println(race);

        long winCount = 0;
        for (long holdingTime = 0; holdingTime <= race.time; holdingTime++) {
            if (isWayToWin(holdingTime, race)) {
                winCount++;
            }
        }

        // Then
        System.out.println(winCount);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 2);

        // When
        Race race = parseRace(lines);
        System.out.println(race);

        int winCount = 0;
        for (int holdingTime = 0; holdingTime <= race.time; holdingTime++) {
            if (isWayToWin(holdingTime, race)) {
                winCount++;
            }
        }

        // Then
        System.out.println(winCount);
    }

    private List<String> samplePuzzleInput() {
        return Arrays.asList(
                "Time:      7  15   30",
                "Distance:  9  40  200"
        );
    }

    private List<Race> parseRaces(List<String> lines) {
        List<Integer> times = Arrays.stream(lines.get(0).substring("Time:".length()).trim().split(" " ))
                .filter(v -> v != null && v.trim().length() > 0)
                .map(Integer::parseInt)
                .toList();
        List<Integer> distances = Arrays.stream(lines.get(1).substring("Distance:".length()).trim().split(" " ))
                .filter(v -> v != null && v.trim().length() > 0)
                .map(Integer::parseInt)
                .toList();

        if (times.size() != distances.size()) {
            throw new IllegalStateException("Sizes are incompatible.");
        }

        List<Race> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), distances.get(i)));
        }
        return races;
    }

    private Race parseRace(List<String> lines) {
        String time = Arrays.stream(lines.get(0).substring("Time:".length()).trim().split(" " ))
                .filter(v -> v != null && v.trim().length() > 0)
                .collect(Collectors.joining());
        String distance = Arrays.stream(lines.get(1).substring("Distance:".length()).trim().split(" " ))
                .filter(v -> v != null && v.trim().length() > 0)
                .collect(Collectors.joining());

        return new Race(Long.parseLong(time), Long.parseLong(distance));
    }

    private boolean isWayToWin(long holdingTime, Race raceToBeat) {
        if (holdingTime == 0) {
            return 0 > raceToBeat.distance;
        }
        long rest = raceToBeat.time - holdingTime;
        long dist = holdingTime * rest;
        return dist > raceToBeat.distance;
    }

    @AllArgsConstructor
    @ToString
    private static class Race {
        private long time;
        private long distance;
    }
}
