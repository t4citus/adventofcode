package com.t4citus.adventofcode.v2015;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

public class Day1Test extends AbstractTestBase {

    @Value("classpath:/2015/day1.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1);

        // When
        String line = lines.get(0);
        int up = Arrays.stream(line.split("" )).filter(c -> c.equals("(" )).toList().size();
        int down = Arrays.stream(line.split("" )).filter(c -> c.equals(")" )).toList().size();
        int floor = up - down;

        // Then
        System.out.println(floor);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1);

        // When
        String line = lines.get(0);
        String[] instructions = line.split("");

        int floor = 0;
        int pos = 0;

        for (String instr : instructions) {
            pos++;
            if (instr.equals("(")) {
                floor++;
            }
            if (instr.equals(")")) {
                floor--;
            }
            if (floor == -1) {
                break;
            }
        }

        // Then
        System.out.println(pos);
    }
}
