package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day5Test extends AbstractTestBase {

    @Value("classpath:/2020/day5.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList("BFFFBBFRRR", "FFFBBBFRRR", "BBFFBBFRLL" );

        // When
        int max = lines.stream()
                .map(this::findSeatId)
                .mapToInt(i -> i)
                .max().orElse(-1);

        // Then
        System.out.println("The max seat ID is " + max + "." );
        Assertions.assertThat(max).isEqualTo(820);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 798);

        // When
        int max = lines.stream()
                .map(this::findSeatId)
                .mapToInt(i -> i)
                .max().orElse(-1);

        // Then
        System.out.println("The max seat ID is " + max + "." );
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given

        // When

        // Then
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 798);

        // When
        Set<Integer> seated = lines.stream()
                .map(this::findSeatId)
                .collect(Collectors.toSet());
        System.out.println(seated.size());

        Integer mySeatId = null;
        int max = 127 * 8 + 8;

        for (int seatId = 0; seatId < max; seatId++) {
            if (!seated.contains(seatId) && seated.contains(seatId - 1) && seated.contains(seatId + 1)) {
                mySeatId = seatId;
                break;
            }
        }

        // Then
        System.out.println("My seat ID is " + mySeatId + "." );
    }

    private int findRow(String instructions) {
        String bin = instructions.replaceAll("F", "0" ).replaceAll("B", "1" );
        return Integer.parseInt(bin, 2);
    }

    private int findSeat(String instructions) {
        String bin = instructions.replaceAll("L", "0" ).replaceAll("R", "1" );
        return Integer.parseInt(bin, 2);
    }

    private int findSeatId(String line) {
        String rowInstructions = line.substring(0, 7);
        String seatInstructions = line.substring(7);

        int row = findRow(rowInstructions);
        int seat = findSeat(seatInstructions);
        int seatId = row * 8 + seat;
        // System.out.println(line + ": row = " + row + ", seat = " + seat + " seatId = " + seatId);
        return seatId;
    }
}
