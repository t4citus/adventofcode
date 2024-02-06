package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13Test extends AbstractTestBase {

    @Value("classpath:/2020/day13.txt" )
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day13_sample1.txt" )
    private Resource sample1InputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        Schedule schedule = parseSchedule(lines);
        int nearestDeparture = nearestDeparture(schedule);

        // Then
        System.out.println("The nearest departure is " + nearestDeparture + ".");
        Assertions.assertThat(nearestDeparture).isEqualTo(295);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        Schedule schedule = parseSchedule(lines);
        int nearestDeparture = nearestDeparture(schedule);

        // Then
        System.out.println("The nearest departure is " + nearestDeparture + ".");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        List<String> ids = Arrays.asList(lines.get(1).split(","));
        List<Integer> busTimes = ids.stream()
                .map(id -> id.equals("x") ? 1 : Integer.parseInt(id))
                .toList();
        Long totalTime = 0L;
        Integer delta = busTimes.get(0);

        // Then
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given

        // When

        // Then
    }

    private static Schedule parseSchedule(List<String> lines) {
        int time = Integer.parseInt(lines.get(0));
        List<Bus> busses = Arrays.stream(lines.get(1).split(","))
                .filter(b -> !b.equals("x"))
                .map(b -> new Bus(Integer.parseInt(b), -1))
                .collect(Collectors.toList());
        return new Schedule(time, busses);
    }

    private static int nearestDeparture(Schedule schedule) {
        final int time = schedule.time;
        final List<Bus> busses = schedule.busses.stream()
                .map(bus -> new Bus(bus.id, nextDeparture(time, bus.id)))
                .sorted((b1, b2) -> b1.nextDeparture < b2.nextDeparture ? -1 : 1)
                .toList();
        busses.forEach(System.out::println);
        Bus first = busses.get(0);
        System.out.println("first bus: " + first);
        return first.id * (first.nextDeparture - time);
    }

    private static int nextDeparture(int time, int busId) {
        int times = time / busId;
        return busId * (times + 1);
    }

    @AllArgsConstructor
    @ToString
    private static class Bus {
        private int id;
        private int nextDeparture;
    }

    @AllArgsConstructor
    @ToString
    private static class Schedule {
        private int time;
        private List<Bus> busses;
    }
}
