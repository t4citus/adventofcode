package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11Test extends AbstractTestBase {

    @Value("classpath:/2020/day11.txt")
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day11_sample1.txt")
    private Resource sample1InputResource;

    @Value("classpath:/2020/day11_sample2.txt")
    private Resource sample2InputResource;

    @Value("classpath:/2020/day11_sample3.txt")
    private Resource sample3InputResource;

    private static final String EMPTY_SEAT = "L";
    private static final String OCCUPIED_SEAT = "#";
    private static final String FLOOR = ".";


    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        List<List<String>> currentGrid = borderedGrid(parseLines(lines));
        printGrid(currentGrid);
        boolean isStable = false;
        int numOccupied = -1;

        while (!isStable) {
            GridGeneration next = nextGeneration(currentGrid);
            currentGrid = next.grid;
            isStable = next.isStable;
            numOccupied = next.numOccupied;
        }

        // Then
        System.out.println("There are " + numOccupied + " seats after stabilization.");
        Assertions.assertThat(numOccupied).isEqualTo(37);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<List<String>> currentGrid = borderedGrid(parseLines(lines));
        printGrid(currentGrid);
        boolean isStable = false;
        int numOccupied = -1;

        while (!isStable) {
            GridGeneration next = nextGeneration(currentGrid);
            currentGrid = next.grid;
            isStable = next.isStable;
            numOccupied = next.numOccupied;
        }

        // Then
        System.out.println("There are " + numOccupied + " seats after stabilization.");
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(sample1InputResource);

        // When
        List<List<String>> currentGrid = parseLines(lines);
        printGrid(currentGrid);
        boolean isStable = false;
        int numOccupied = -1;

        while (!isStable) {
            GridGeneration next = nextGenerationTwo(currentGrid);
            currentGrid = next.grid;
            isStable = next.isStable;
            numOccupied = next.numOccupied;
        }

        // Then
        System.out.println("There are " + numOccupied + " seats after stabilization.");
        Assertions.assertThat(numOccupied).isEqualTo(26);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);

        // When
        List<List<String>> currentGrid = parseLines(lines);
        // printGrid(currentGrid);
        boolean isStable = false;
        int numOccupied = -1;

        while (!isStable) {
            GridGeneration next = nextGenerationTwo(currentGrid);
            currentGrid = next.grid;
            isStable = next.isStable;
            numOccupied = next.numOccupied;
        }

        // Then
        System.out.println("There are " + numOccupied + " seats after stabilization.");
    }

    private static List<List<String>> parseLines(List<String> lines) {
        return lines.stream()
                .map(line -> Arrays.asList(line.split("")))
                .collect(Collectors.toList());
    }

    private static List<List<String>> borderedGrid(List<List<String>> parsed) {
        int columnSize = parsed.get(0).size();
        List<List<String>> newGrid = new ArrayList<>();
        newGrid.add(createListWithDefault(columnSize + 2, FLOOR));

        for (List<String> row : parsed) {
            List<String> newList = new ArrayList<>();
            newList.add(".");
            newList.addAll(new ArrayList<>(row));
            newList.add(".");
            newGrid.add(newList);
        }

        newGrid.add(createListWithDefault(columnSize + 2, FLOOR));
        return newGrid;
    }

    private static GridGeneration nextGeneration(List<List<String>> grid) {
        // copy full grid (original is not mutated)
        List<List<String>> newGrid = new ArrayList<>();
        for (List<String> row : grid) {
            newGrid.add(new ArrayList<>(row));
        }

        boolean isStable = true;
        for (int i = 1; i < newGrid.size() - 1; i++) {
            for (int j = 1; j < newGrid.get(i).size() - 1; j++) {
                String center = newGrid.get(i).get(j);
                long numOccupied = Stream.of(
                                grid.get(i - 1).get(j - 1).equals(OCCUPIED_SEAT), // top left
                                grid.get(i - 1).get(j).equals(OCCUPIED_SEAT), // top
                                grid.get(i - 1).get(j + 1).equals(OCCUPIED_SEAT), // top right
                                grid.get(i).get(j - 1).equals(OCCUPIED_SEAT), // left
                                grid.get(i).get(j + 1).equals(OCCUPIED_SEAT), // right
                                grid.get(i + 1).get(j - 1).equals(OCCUPIED_SEAT), // bottom left
                                grid.get(i + 1).get(j).equals(OCCUPIED_SEAT), // bottom
                                grid.get(i + 1).get(j + 1).equals(OCCUPIED_SEAT)) // bottom right
                        .filter(b -> b)
                        .count();

                if (center.equals(EMPTY_SEAT) && numOccupied == 0L) {
                    newGrid.get(i).set(j, OCCUPIED_SEAT);
                    isStable = false;
                } else if (center.equals(OCCUPIED_SEAT) && numOccupied >= 4L) {
                    newGrid.get(i).set(j, EMPTY_SEAT);
                    isStable = false;
                }
            }
        }

        int numOccupied = 0;
        for (int i = 1; i < newGrid.size() - 1; i++) {
            for (int j = 1; j < newGrid.get(i).size() - 1; j++) {
                if (newGrid.get(i).get(j).equals(OCCUPIED_SEAT)) {
                    numOccupied++;
                }
            }
        }

        return new GridGeneration(newGrid, isStable, numOccupied);
    }

    private static GridGeneration nextGenerationTwo(List<List<String>> grid) {
        // copy full grid (original is not mutated)
        List<List<String>> newGrid = new ArrayList<>();
        for (List<String> row : grid) {
            newGrid.add(new ArrayList<>(row));
        }

        boolean isStable = true;
        for (int i = 0; i < newGrid.size(); i++) {
            for (int j = 0; j < newGrid.get(i).size(); j++) {
                String center = newGrid.get(i).get(j);
                int numOccupied = (int) nearestNeighbors(grid, i, j).stream()
                        .filter(n -> n.equals(OCCUPIED_SEAT))
                        .count();

                if (center.equals(EMPTY_SEAT) && numOccupied == 0) {
                    newGrid.get(i).set(j, OCCUPIED_SEAT);
                    isStable = false;
                } else if (center.equals(OCCUPIED_SEAT) && numOccupied >= 5) {
                    newGrid.get(i).set(j, EMPTY_SEAT);
                    isStable = false;
                }
            }
        }

        int allOccupied = 0;
        for (List<String> row : newGrid) {
            for (String col : row) {
                if (col.equals(OCCUPIED_SEAT)) {
                    allOccupied++;
                }
            }
        }

        return new GridGeneration(newGrid, isStable, allOccupied);
    }

    private static Stream<Arguments> validSample2GetXTestCases() {
        return Stream.of(
                Arguments.of(0, 0, List.of(), List.of("2", "3")),
                Arguments.of(0, 1, List.of("1"), List.of("3")),
                Arguments.of(0, 2, List.of("2", "1"), List.of()),
                Arguments.of(1, 0, List.of(), List.of("5", "6")),
                Arguments.of(1, 1, List.of("4"), List.of("6")),
                Arguments.of(1, 2, List.of("5", "4"), List.of()),
                Arguments.of(2, 0, List.of(), List.of("8", "9")),
                Arguments.of(2, 1, List.of("7"), List.of("9")),
                Arguments.of(2, 2, List.of("8", "7"), List.of())
        );
    }

    private static Stream<Arguments> validSample2GetYTestCases() {
        return Stream.of(
                Arguments.of(0, 0, List.of(), List.of("4", "7")),
                Arguments.of(0, 1, List.of(), List.of("5", "8")),
                Arguments.of(0, 2, List.of(), List.of("6", "9")),
                Arguments.of(1, 0, List.of("1"), List.of("7")),
                Arguments.of(1, 1, List.of("2"), List.of("8")),
                Arguments.of(1, 2, List.of("3"), List.of("9")),
                Arguments.of(2, 0, List.of("4", "1"), List.of()),
                Arguments.of(2, 1, List.of("5", "2"), List.of()),
                Arguments.of(2, 2, List.of("6", "3"), List.of())
        );
    }

    private static Stream<Arguments> validSample2GetD1TestCases() {
        return Stream.of(
                Arguments.of(0, 0, List.of(), List.of("5", "9")),
                Arguments.of(0, 1, List.of(), List.of("6")),
                Arguments.of(0, 2, List.of(), List.of()),
                Arguments.of(1, 0, List.of(), List.of("8")),
                Arguments.of(1, 1, List.of("1"), List.of("9")),
                Arguments.of(1, 2, List.of("2"), List.of()),
                Arguments.of(2, 0, List.of(), List.of()),
                Arguments.of(2, 1, List.of("4"), List.of()),
                Arguments.of(2, 2, List.of("5", "1"), List.of())
        );
    }

    private static Stream<Arguments> validSample2GetD2TestCases() {
        return Stream.of(
                Arguments.of(0, 0, List.of(), List.of()),
                Arguments.of(0, 1, List.of("4"), List.of()),
                Arguments.of(0, 2, List.of("5", "7"), List.of()),
                Arguments.of(1, 0, List.of(), List.of("2")),
                Arguments.of(1, 1, List.of("7"), List.of("3")),
                Arguments.of(1, 2, List.of("8"), List.of()),
                Arguments.of(2, 0, List.of(), List.of("5", "3")),
                Arguments.of(2, 1, List.of(), List.of("6")),
                Arguments.of(2, 2, List.of(), List.of())
        );
    }

    @ParameterizedTest
    @MethodSource("validSample2GetXTestCases")
    public void givenTestCases_whenGetX_thenReturnsAsExpected(Integer row, Integer col, List<String> expectedLeft, List<String> expectedRight) {
        // Given
        List<String> lines = readLines(sample2InputResource);

        // When
        List<List<String>> grid = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .collect(Collectors.toList());

        Neighbors neighbors = getX(grid, row, col);

        // Then
        Assertions.assertThat(neighbors).isNotNull();
        Assertions.assertThat(equals(neighbors.left, expectedLeft)).isTrue();
        Assertions.assertThat(equals(neighbors.right, expectedRight)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("validSample2GetYTestCases")
    public void givenTestCases_whenGetY_thenReturnsAsExpected(Integer row, Integer col, List<String> expectedLeft, List<String> expectedRight) {
        // Given
        List<String> lines = readLines(sample2InputResource);

        // When
        List<List<String>> grid = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .collect(Collectors.toList());

        Neighbors neighbors = getY(grid, row, col);

        // Then
        Assertions.assertThat(neighbors).isNotNull();
        Assertions.assertThat(equals(neighbors.left, expectedLeft)).isTrue();
        Assertions.assertThat(equals(neighbors.right, expectedRight)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("validSample2GetD1TestCases")
    public void givenTestCases_whenGetD1_thenReturnsAsExpected(Integer row, Integer col, List<String> expectedLeft, List<String> expectedRight) {
        // Given
        List<String> lines = readLines(sample2InputResource);

        // When
        List<List<String>> grid = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .collect(Collectors.toList());

        Neighbors neighbors = getD1(grid, row, col);

        // Then
        Assertions.assertThat(neighbors).isNotNull();
        Assertions.assertThat(equals(neighbors.left, expectedLeft)).isTrue();
        Assertions.assertThat(equals(neighbors.right, expectedRight)).isTrue();
    }

    @ParameterizedTest
    @MethodSource("validSample2GetD2TestCases")
    public void givenTestCases_whenGetD2_thenReturnsAsExpected(Integer row, Integer col, List<String> expectedLeft, List<String> expectedRight) {
        // Given
        List<String> lines = readLines(sample2InputResource);

        // When
        List<List<String>> grid = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .collect(Collectors.toList());

        Neighbors neighbors = getD2(grid, row, col);
        System.out.println(neighbors);

        // Then
        Assertions.assertThat(neighbors).isNotNull();
        Assertions.assertThat(equals(neighbors.left, expectedLeft)).isTrue();
        Assertions.assertThat(equals(neighbors.right, expectedRight)).isTrue();
    }

    @Test
    public void givenTestCase_whenNearestNeighbor_thenReturnedAsExpected() {
        // Given
        List<String> lines = readLines(sample3InputResource);

        // When
        List<List<String>> grid = lines.stream()
                .map(line -> Arrays.asList(line.split(" ")))
                .collect(Collectors.toList());

        List<String> nearest = nearestNeighbors(grid, 2, 2);
        int numOccupied = (int) nearest.stream().filter(n -> n.equals(OCCUPIED_SEAT)).count();

        // Then
        System.out.println("There are " + numOccupied + " occupied seats.");
        // Assertions.assertThat(numOccupied).isEqualTo(7);
    }

    private static void printGrid(List<List<String>> grid) {
        grid.forEach(System.out::println);
        System.out.println("---");
    }

    private static List<String> createListWithDefault(int size, String defaultValue) {
        List<String> newList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            newList.add(defaultValue);
        }
        return newList;
    }

    private static Neighbors getX(List<List<String>> grid, int row, int col) {
        List<String> all = grid.get(row);
        List<String> left = new ArrayList<>(all.subList(0, col));
        Collections.reverse(left);
        List<String> right = new ArrayList<>(all.subList(col + 1, all.size()));
        return new Neighbors(left, right);
    }

    private static Neighbors getY(List<List<String>> grid, int row, int col) {
        List<String> all = new ArrayList<>();
        for (List<String> gridRow : grid) {
            all.add(gridRow.get(col));
        }
        List<String> left = all.subList(0, row);
        Collections.reverse(left);
        List<String> right = all.subList(row + 1, all.size());
        return new Neighbors(left, right);
    }

    private static Neighbors getD1(List<List<String>> grid, int row, int col) {
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();

        final int maxRow = grid.size();
        final int maxCol = grid.get(0).size();
        int max = Math.max(maxRow, maxCol);

        for (int i = 1; i <= max; i++) {
            int x = col - i;
            int y = row - i;
            if (x < 0 || y < 0) {
                break;
            }
            left.add(grid.get(y).get(x));
        }

        for (int i = 1; i <= max; i++) {
            int x = col + i;
            int y = row + i;
            if (x >= maxCol || y >= maxRow) {
                break;
            }
            right.add(grid.get(y).get(x));
        }

        return new Neighbors(left, right);
    }

    private static Neighbors getD2(List<List<String>> grid, int row, int col) {
        List<String> left = new ArrayList<>();
        List<String> right = new ArrayList<>();

        final int maxRow = grid.size();
        final int maxCol = grid.get(0).size();
        int max = Math.max(maxRow, maxCol);

        for (int i = 1; i <= max; i++) {
            int x = col - i;
            int y = row + i;
            if (x < 0 || y >= maxRow) {
                break;
            }
            left.add(grid.get(y).get(x));
        }

        for (int i = 1; i <= max; i++) {
            int x = col + i;
            int y = row - i;
            if (x >= maxCol || y < 0) {
                break;
            }
            right.add(grid.get(y).get(x));
        }

        return new Neighbors(left, right);
    }

    private static List<String> nearestNeighbors(List<List<String>> grid, int row, int col) {
        List<Neighbors> neighbors = Arrays.asList(
                getX(grid, row, col),
                getY(grid, row, col),
                getD1(grid, row, col),
                getD2(grid, row, col)
        );

        List<String> nearest = new ArrayList<>();

        for (Neighbors n : neighbors) {
            for (String seat : n.left) {
                if (!seat.equals(FLOOR)) {
                    nearest.add(seat);
                    break;
                }
            }
            for (String seat : n.right) {
                if (!seat.equals(FLOOR)) {
                    nearest.add(seat);
                    break;
                }
            }
        }

        return nearest;
    }

    private static boolean equals(List<String> left, List<String> right) {
        if (left.size() != right.size())
            return false;

        for (int i = 0; i < left.size(); i++) {
            if (!left.get(i).equals(right.get(i)))
                return false;
        }

        return true;
    }

    @AllArgsConstructor
    @ToString
    private static class GridGeneration {
        private final List<List<String>> grid;
        private final boolean isStable;
        private final int numOccupied;
    }

    @AllArgsConstructor
    @ToString
    private static class Neighbors {
        private final List<String> left;
        private final List<String> right;
    }
}
