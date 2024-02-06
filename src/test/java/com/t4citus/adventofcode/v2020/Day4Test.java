package com.t4citus.adventofcode.v2020;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4Test extends AbstractTestBase {

    private static final Set<String> EYE_COLOR = new HashSet<>(Arrays.asList(
            "amb", "blu", "brn", "gry", "grn", "hzl", "oth"
    ));

    @Value("classpath:/2020/day4.txt" )
    private Resource puzzleInputResource;

    @Value("classpath:/2020/day4_sample.txt" )
    private Resource samplePuzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(samplePuzzleInputResource);
        assertLines(lines, 13);

        // When
        long valid = parseLines(lines).stream()
                .map(this::validatePassport1)
                .filter(v -> v)
                .count();

        // Then
        System.out.println("There are " + valid + " valid passports.");
        Assertions.assertThat(valid).isEqualTo(2);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1056);

        // When
        long valid = parseLines(lines).stream()
                .map(this::validatePassport1)
                .filter(v -> v)
                .count();

        // Then
        System.out.println("There are " + valid + " valid passports.");
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
        assertLines(lines, 1056);

        // When
        long valid = parseLines(lines).stream()
                .map(this::validatePassport2)
                .filter(v -> v)
                .count();

        // Then
        System.out.println("There are " + valid + " valid passports.");
    }

    private List<String> parseLines(List<String> lines) {
        List<String> parsed = new ArrayList<>();
        List<String> parts = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().length() == 0) {
                parsed.add(parts.stream()
                        .map(String::trim)
                        .collect(Collectors.joining(" " )));
                parts = new ArrayList<>();
            } else {
                parts.add(line);
            }
        }
        if (!parts.isEmpty()) {
            parsed.add(parts.stream()
                    .map(String::trim)
                    .collect(Collectors.joining(" " )));
        }

        return parsed;
    }

    private boolean validatePassport1(String passport) {
        /*
        byr (Birth Year)
        iyr (Issue Year)
        eyr (Expiration Year)
        hgt (Height)
        hcl (Hair Color)
        ecl (Eye Color)
        pid (Passport ID)
         */

        String[] parts = passport.split(" ");
        Map<String, String> data = new HashMap<>();

        for (String part : parts) {
            String[] split = part.split(":");
            data.put(split[0], split[1]);
        }

        return data.containsKey("byr")
                && data.containsKey("iyr")
                && data.containsKey("eyr")
                && data.containsKey("hgt")
                && data.containsKey("hcl")
                && data.containsKey("ecl")
                && data.containsKey("pid");
    }

    private boolean validatePassport2(String passport) {
        /*
        byr (Birth Year)
        iyr (Issue Year)
        eyr (Expiration Year)
        hgt (Height)
        hcl (Hair Color)
        ecl (Eye Color)
        pid (Passport ID)
         */

        String[] parts = passport.split(" ");
        Map<String, String> data = new HashMap<>();

        for (String part : parts) {
            String[] split = part.split(":");
            data.put(split[0], split[1]);
        }

        /*
        byr (Birth Year) - four digits; at least 1920 and at most 2002.
        iyr (Issue Year) - four digits; at least 2010 and at most 2020.
        eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
        hgt (Height) - a number followed by either cm or in:
            If cm, the number must be at least 150 and at most 193.
            If in, the number must be at least 59 and at most 76.
        hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
        ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
        pid (Passport ID) - a nine-digit number, including leading zeroes.
        cid (Country ID) - ignored, missing or not.
        */

        return checkYear(data.get("byr"), 1920, 2002)
                && checkYear(data.get("iyr"), 2010, 2020)
                && checkYear(data.get("eyr"), 2020, 2030)
                && checkHeight(data.get("hgt"))
                && checkHairColor(data.get("hcl"))
                && checkEyeColor(data.get("ecl"))
                && checkPasswordId(data.get("pid"))
        ;
    }

    private boolean checkYear(String yearValue, int minYear, int maxYear) {
        if (yearValue == null || yearValue.isEmpty()) {
            return false;
        }
        if (yearValue.length() != 4) {
            return false;
        }
        Integer year = tryParseInt(yearValue, 0);
        return year >= minYear && year <= maxYear;
    }

    private boolean checkHeight(String heightValue) {
        if (heightValue == null || heightValue.isEmpty()) {
            return false;
        }
        if (heightValue.endsWith("cm")) {
            String rest = heightValue.substring(0, heightValue.length() - "cm".length());
            Integer cm = tryParseInt(rest, 0);
            return cm >= 150 && cm <= 193;
        }
        if (heightValue.endsWith("in")) {
            String rest = heightValue.substring(0, heightValue.length() - "in".length());
            Integer in = tryParseInt(rest, 0);
            return in >= 59 && in <= 76;
        }
        return false;
    }

    private boolean checkHairColor(String colorValue) {
        if (colorValue == null || colorValue.isEmpty()) {
            return false;
        }
        if (colorValue.startsWith("#") && colorValue.length() == 7) {
            Pattern pattern = Pattern.compile("#[0-9a-f]{6}");
            Matcher matcher = pattern.matcher(colorValue);
            return matcher.find();
        }
        return false;
    }

    private boolean checkEyeColor(String colorValue) {
        if (colorValue == null || colorValue.isEmpty()) {
            return false;
        }
        return EYE_COLOR.contains(colorValue);
    }

    private boolean checkPasswordId(String val) {
        if (val == null || val.isEmpty()) {
            return false;
        }
        if (val.length() == 9) {
            Pattern pattern = Pattern.compile("[0-9]{9}");
            Matcher matcher = pattern.matcher(val);
            return matcher.find();
        }
        return false;
    }

    private Integer tryParseInt(String s, Integer valueIfNull) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception e) {
            return valueIfNull;
        }
    }
}
