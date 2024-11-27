package com.t4citus.adventofcode.v2021;

import com.t4citus.adventofcode.AbstractTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day4Test extends AbstractTestBase {

    @Value("classpath:/2021/day4.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2021/day4.sample.txt")
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Game game = parseLines(lines);
        final int finalScore = playFirstBingoWins(game);

        // Then
        System.out.println("The final score is " + finalScore);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Game game = parseLines(lines);
        final int finalScore = playFirstBingoWins(game);

        // Then
        System.out.println("The final score is " + finalScore);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(samplePuzzleInputResource);

        // When
        final Game game = parseLines(lines);
        final int finalScore = playLastBingoWins(game);

        // Then
        System.out.println("The final score is " + finalScore);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        final List<String> lines = readLines(puzzleInputResource);

        // When
        final Game game = parseLines(lines);
        final int finalScore = playLastBingoWins(game);

        // Then
        System.out.println("The final score is " + finalScore);
    }

    private static class Bingo {
        private final Map<String, FieldValue> fields = new HashMap<>();
        private boolean hasBingo = false;

        public Bingo(final List<String> rows) {
            for (int row = 1; row <= rows.size(); row++) {
                List<Integer> columns = Arrays.stream(rows.get(row - 1).split(" "))
                        .filter(s -> s != null && !s.trim().equals(""))
                        .map(Integer::parseInt)
                        .toList();
                for (int col = 1; col <= columns.size(); col++) {
                    addField(row, col, columns.get(col - 1));
                }
            }
        }

        public void addField(final int row, final int col, final int val) {
            fields.put(key(row, col), new FieldValue(val, false));
        }

        public boolean markFieldAndVerify(final int val) {
            for (int row = 1; row <= 5; row++) {
                for (int col = 1; col <= 5; col++) {
                    FieldValue fieldValue = fields.get(key(row, col));
                    if (fieldValue.getValue() == val) {
                        fieldValue.setChecked(true);

                        // check row
                        boolean isBingo = true;
                        for (int i = 1; i <= 5; i++) {
                            if (fields.get(key(row, i)).isNotChecked()) {
                                isBingo = false;
                            }
                        }

                        if (isBingo) return true;

                        // check column
                        isBingo = true;
                        for (int i = 1; i <= 5; i++) {
                            if (fields.get(key(i, col)).isNotChecked()) {
                                isBingo = false;
                            }
                        }

                        if (isBingo) return true;
                    }
                }
            }

            return false;
        }

        public String key(final int row, final int col) {
            return row + ":" + col;
        }

        public int sumOfUnchecked() {
            return fields.values()
                    .stream()
                    .filter(FieldValue::isNotChecked)
                    .map(FieldValue::getValue)
                    .reduce(Integer::sum)
                    .orElse(0);
        }

        public boolean hasBingo() {
            return hasBingo;
        }

        public void setHasBingo(boolean hasBingo) {
            this.hasBingo = hasBingo;
        }

        @Override
        public String toString() {
            return fields.toString();
        }
    }

    private static class FieldValue {
        final int value;
        boolean isChecked;

        public FieldValue(int value, boolean isChecked) {
            this.value = value;
            this.isChecked = isChecked;
        }

        public int getValue() {
            return value;
        }

        public boolean isNotChecked() {
            return !isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }

    private record Game(List<Integer> picks, List<Bingo> allFields) {
    }

    private static Game parseLines(final List<String> lines) {
        final List<Integer> picks = Arrays.stream(lines.get(0).split(","))
                .map(Integer::parseInt)
                .toList();

        final List<Bingo> allFields = new ArrayList<>();
        final int max = lines.size();
        int from = 2;
        int to = from + 5;

        while (to <= max) {
            allFields.add(new Bingo(lines.subList(from, to)));
            from = to + 1;
            to = from + 5;
        }

        return new Game(picks, allFields);
    }

    private static int playFirstBingoWins(final Game game) {
        final List<Integer> picks = game.picks();
        final List<Bingo> allFields = game.allFields();

        for (Integer pick : picks) {
            for (Bingo field : allFields) {
                boolean hasBingo = field.markFieldAndVerify(pick);
                if (hasBingo) {
                    return pick * field.sumOfUnchecked();
                }
            }
        }

        throw new IllegalStateException("No bingo found.");
    }

    private static int playLastBingoWins(final Game game) {
        final List<Integer> picks = game.picks();
        final List<Bingo> allFields = game.allFields();
        int bingoCount = 0;

        for (Integer pick : picks) {
            for (Bingo field : allFields) {
                if (!field.hasBingo()) {
                    boolean hasBingo = field.markFieldAndVerify(pick);
                    if (hasBingo) {
                        field.setHasBingo(true);
                        bingoCount++;

                        if (bingoCount == allFields.size())
                            return pick * field.sumOfUnchecked();
                    }
                }
            }
        }

        throw new IllegalStateException("No bingo found.");
    }
}
