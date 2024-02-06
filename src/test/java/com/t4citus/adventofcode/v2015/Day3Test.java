package com.t4citus.adventofcode.v2015;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day3Test extends AbstractTestBase {

    @Value("classpath:/2015/day3.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1);

        // When
        String line = lines.get(0);
        String[] moves = line.split("");

        int x = 0;
        int y = 0;
        Set<String> visited = new HashSet<>();
        visited.add(x + ":" + y);

        for (String move : moves) {
            if (move.equals("^")) {
                y++;
            }
            if (move.equals("v")) {
                y--;
            }
            if (move.equals("<")) {
                x--;
            }
            if (move.equals(">")) {
                x++;
            }
            visited.add(x + ":" + y);
        }

        // Then
        System.out.println(visited.size());
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1);

        // When
        String line = lines.get(0);
        String[] moves = line.split("");

        Point santa = new Point(0, 0);
        Point robo = new Point(0, 0);
        boolean moveSanta = true;
        Set<String> visited = new HashSet<>();
        visited.add(santa.toString());
        visited.add(robo.toString());


        for (String move : moves) {
            if (move.equals("^")) {
                if (moveSanta) {
                    santa.y++;
                } else {
                    robo.y++;
                }
            }
            if (move.equals("v")) {
                if (moveSanta) {
                    santa.y--;
                } else {
                    robo.y--;
                }
            }
            if (move.equals("<")) {
                if (moveSanta) {
                    santa.x--;
                } else {
                    robo.x--;
                }
            }
            if (move.equals(">")) {
                if (moveSanta) {
                    santa.x++;
                } else {
                    robo.x++;
                }
            }
            if (moveSanta) {
                visited.add(santa.toString());
            } else {
                visited.add(robo.toString());
            }
            moveSanta = !moveSanta;
        }

        // Then
        System.out.println(visited.size());
    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return x + ":" + y;
        }
    }
}
