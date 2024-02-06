package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day9Test extends AbstractTestBase {

    @Value("classpath:/2023/day9.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput();

        // When
        List<Integer> nextValues = lines.stream()
                .map(this::parseSequence)
                .map(Sequence::next)
                .toList();

        // Then
        System.out.println(nextValues);
    }

    Sequence parseSequence(String line) {
        Integer[] seq = Arrays.stream(line.split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
        return new Sequence(seq);
    }

    private void print(Integer[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    private List<String> samplePuzzleInput() {
        return Arrays.asList(
                "0 3 6 9 12 15",
                "1 3 6 10 15 21",
                "10 13 16 21 30 45"
        );
    }

    @ToString
    private static class Sequence {
        private Integer[] seq;
        private Integer[] delta;

        public Sequence(Integer[] seq) {
            this.seq = seq;
            this.delta = getDelta(seq);
        }

        Integer[] getDelta(Integer[] arr) {
            Integer[] data = new Integer[arr.length - 1];
            for (int i = 0; i < arr.length - 1; i++) {
                data[i] = arr[i + 1] - arr[i];
            }
            return data;
        }

        Integer next() {
            return delta[delta.length - 1] + seq[seq.length - 1];
        }
    }
}
