package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5Test extends AbstractTestBase {

    @Value("classpath:/2023/day5.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput();

        // When
        Almanac almanac = parseAlmanac(lines);
        System.out.println(almanac);

        long min = Long.MAX_VALUE;

        for (Long seed : almanac.seeds) {
            long soil = almanac.seedToSoil.resolve(seed);
            long fertilizer = almanac.soilToFertilizer.resolve(soil);
            long water = almanac.fertilizerToWater.resolve(fertilizer);
            long light = almanac.waterToLight.resolve(water);
            long temp = almanac.lightToTemp.resolve(light);
            long humidity = almanac.tempToHumidity.resolve(temp);
            long location = almanac.humidityToLocation.resolve(humidity);
            System.out.println(seed + " -> " + location);
            min = Math.min(min, location);
        }

        // Then
        System.out.println("min: " + min);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 219);

        // When
        Almanac almanac = parseAlmanac(lines);
        System.out.println(almanac);

        long min = Long.MAX_VALUE;

        for (Long seed : almanac.seeds) {
            long soil = almanac.seedToSoil.resolve(seed);
            long fertilizer = almanac.soilToFertilizer.resolve(soil);
            long water = almanac.fertilizerToWater.resolve(fertilizer);
            long light = almanac.waterToLight.resolve(water);
            long temp = almanac.lightToTemp.resolve(light);
            long humidity = almanac.tempToHumidity.resolve(temp);
            long location = almanac.humidityToLocation.resolve(humidity);
            System.out.println(seed + " -> " + location);
            min = Math.min(min, location);
        }

        // Then
        System.out.println("min: " + min);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput();

        // When
        Almanac almanac = parseAlmanac(lines);
        System.out.println(almanac);

        long min = Long.MAX_VALUE;

        for (int i = 0; i < almanac.seeds.size(); i += 2) {
            long start = almanac.seeds.get(i);
            long range = almanac.seeds.get(i + 1);
            for (long seed = start; seed < start + range; seed++) {
                long soil = almanac.seedToSoil.resolve(seed);
                long fertilizer = almanac.soilToFertilizer.resolve(soil);
                long water = almanac.fertilizerToWater.resolve(fertilizer);
                long light = almanac.waterToLight.resolve(water);
                long temp = almanac.lightToTemp.resolve(light);
                long humidity = almanac.tempToHumidity.resolve(temp);
                long location = almanac.humidityToLocation.resolve(humidity);
                min = Math.min(min, location);
            }
        }

        // Then
        System.out.println("min: " + min);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 219);

        // When
        Almanac almanac = parseAlmanac(lines);
        System.out.println(almanac);

        long min = Long.MAX_VALUE;

        for (int i = 0; i < almanac.seeds.size(); i += 2) {
            long start = almanac.seeds.get(i);
            long range = almanac.seeds.get(i + 1);
            for (long seed = start; seed < start + range; seed++) {
                long soil = almanac.seedToSoil.resolve(seed);
                long fertilizer = almanac.soilToFertilizer.resolve(soil);
                long water = almanac.fertilizerToWater.resolve(fertilizer);
                long light = almanac.waterToLight.resolve(water);
                long temp = almanac.lightToTemp.resolve(light);
                long humidity = almanac.tempToHumidity.resolve(temp);
                long location = almanac.humidityToLocation.resolve(humidity);
                min = Math.min(min, location);
            }
        }

        // Then
        System.out.println("min: " + min);
    }

    private List<String> samplePuzzleInput() {
        return Arrays.asList(
                "seeds: 79 14 55 13",
                "",
                "seed-to-soil map:",
                "50 98 2",
                "52 50 48",
                "",
                "soil-to-fertilizer map:",
                "0 15 37",
                "37 52 2",
                "39 0 15",
                "",
                "fertilizer-to-water map:",
                "49 53 8",
                "0 11 42",
                "42 0 7",
                "57 7 4",
                "",
                "water-to-light map:",
                "88 18 7",
                "18 25 70",
                "",
                "light-to-temperature map:",
                "45 77 23",
                "81 45 19",
                "68 64 13",
                "",
                "temperature-to-humidity map:",
                "0 69 1",
                "1 0 69",
                "",
                "humidity-to-location map:",
                "60 56 37",
                "56 93 4"
        );
    }

    private Almanac parseAlmanac(List<String> lines) {
        List<Long> seeds = null;
        Recipe seedToSoil = new Recipe();
        Recipe soilToFertilizer = new Recipe();
        Recipe fertilizerToWater = new Recipe();
        Recipe waterToLight = new Recipe();
        Recipe lightToTemp = new Recipe();
        Recipe tempToHumidity = new Recipe();
        Recipe humidityToLocation = new Recipe();
        Recipe currentRecipe = null;
        for (String line : lines) {
            if (line.equals("" )) {
                continue;
            }
            if (line.startsWith("seeds:" )) {
                seeds = Arrays.stream(line.substring("seeds:".length()).trim().split(" " ))
                        .map(Long::parseLong)
                        .toList();
                continue;
            }
            if (line.startsWith("seed-to-soil map:" )) {
                currentRecipe = seedToSoil;
                continue;
            }
            if (line.startsWith("soil-to-fertilizer map:" )) {
                currentRecipe = soilToFertilizer;
                continue;
            }
            if (line.startsWith("fertilizer-to-water map:" )) {
                currentRecipe = fertilizerToWater;
                continue;
            }
            if (line.startsWith("water-to-light map:" )) {
                currentRecipe = waterToLight;
                continue;
            }
            if (line.startsWith("light-to-temperature map:" )) {
                currentRecipe = lightToTemp;
                continue;
            }
            if (line.startsWith("temperature-to-humidity map:" )) {
                currentRecipe = tempToHumidity;
                continue;
            }
            if (line.startsWith("humidity-to-location map:" )) {
                currentRecipe = humidityToLocation;
                continue;
            }

            String[] split = line.split(" ");
            currentRecipe.mappings.add(SourceDestinationMapping.builder()
                    .destRangeStart(Long.parseLong(split[0].trim()))
                    .sourceRangeStart(Long.parseLong(split[1].trim()))
                    .rangeLength(Long.parseLong(split[2].trim()))
                    .build());
        }

        return Almanac.builder()
                .seeds(seeds)
                .seedToSoil(seedToSoil)
                .soilToFertilizer(soilToFertilizer)
                .fertilizerToWater(fertilizerToWater)
                .waterToLight(waterToLight)
                .lightToTemp(lightToTemp)
                .tempToHumidity(tempToHumidity)
                .humidityToLocation(humidityToLocation)
                .build();
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Setter
    @Getter
    @ToString
    private static class Almanac {
        private List<Long> seeds;
        private Recipe seedToSoil;
        private Recipe soilToFertilizer;
        private Recipe fertilizerToWater;
        private Recipe waterToLight;
        private Recipe lightToTemp;
        private Recipe tempToHumidity;
        private Recipe humidityToLocation;
    }

    @Getter
    @ToString
    private static class Recipe {
        private final List<SourceDestinationMapping> mappings;

        public Recipe() {
            mappings = new ArrayList<>();
        }

        public long resolve(long value) {
            for (SourceDestinationMapping mapping : mappings) {
                if (mapping.isInRange(value)) {
                    return value - mapping.sourceRangeStart + mapping.destRangeStart;
                }
            }
            return value;
        }
    }

    @AllArgsConstructor
    @Builder
    @Getter
    @ToString
    private static class SourceDestinationMapping {
        private long destRangeStart;
        private long sourceRangeStart;
        private long rangeLength;

        private boolean isInRange(long value) {
            return value >= sourceRangeStart && value < (sourceRangeStart + rangeLength);
        }
    }
}
