package com.t4citus.adventofcode.v2015;

import com.t4citus.adventofcode.AbstractTestBase;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Day4Test extends AbstractTestBase {

    @Value("classpath:/2015/day4.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() throws NoSuchAlgorithmException {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1);

        // When
        String line = lines.get(0);
        String md5 = null;
        int found = -1;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            md5 = encodeMd5(line + Integer.toString(i));
            if (md5.startsWith("00000")) {
                found = i;
                break;
            }
        }

        // Then
        System.out.println(found);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() throws NoSuchAlgorithmException {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1);

        // When
        String line = lines.get(0);
        String md5 = null;
        int found = -1;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            md5 = encodeMd5(line + Integer.toString(i));
            if (md5.startsWith("000000")) {
                found = i;
                break;
            }
        }

        // Then
        System.out.println(found);
    }

    private String encodeMd5(String phrase) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(phrase.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
