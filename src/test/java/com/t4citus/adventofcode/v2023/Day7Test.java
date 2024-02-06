package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;

public class Day7Test extends AbstractTestBase {

    @Value("classpath:/2023/day7.txt" )
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput();

        // When
        List<Hand> hands = lines.stream()
                .map(this::parseHand)
                .sorted(Hand::compareTo)
                .toList();

        // Then
        hands.forEach(System.out::println);

        long total = 0;
        int index = 1;

        for (Hand hand : hands) {
            total = total + ((long) index * hand.bid);
            index++;
        }

        System.out.println("total: " + total);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        List<Hand> hands = lines.stream()
                .map(this::parseHand)
                .sorted(Hand::compareTo)
                .toList();

        // Then
        hands.forEach(System.out::println);

        long total = 0;
        int index = 1;

        for (Hand hand : hands) {
            total = total + ((long) index * hand.bid);
            index++;
        }

        System.out.println("total: " + total);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = samplePuzzleInput();

        // When
        List<Hand2> hands = lines.stream()
                .map(this::parseHand2)
                .sorted(Hand2::compareTo)
                .toList();

        // Then
        hands.forEach(System.out::println);

        long total = 0;
        int index = 1;

        for (Hand2 hand2 : hands) {
            total = total + ((long) index * hand2.bid);
            index++;
        }

        System.out.println("total: " + total);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 1000);

        // When
        List<Hand2> hands = lines.stream()
                .map(this::parseHand2)
                .sorted(Hand2::compareTo)
                .toList();

        // Then
        hands.forEach(System.out::println);

        long total = 0;
        int index = 1;

        for (Hand2 hand2 : hands) {
            total = total + ((long) index * hand2.bid);
            index++;
        }

        System.out.println("total: " + total);
    }

    private List<String> samplePuzzleInput() {
        return Arrays.asList(
                "32T3K 765",
                "T55J5 684",
                "KK677 28",
                "KTJJT 220",
                "QQQJA 483"
        );
    }

    private Hand parseHand(String line) {
        String[] split = line.split(" ");
        return new Hand(split[0], Integer.parseInt(split[1]));
    }

    private Hand2 parseHand2(String line) {
        String[] split = line.split(" ");
        return new Hand2(split[0], Integer.parseInt(split[1]));
    }

    @ToString
    private static class Hand implements Comparable<Hand> {
        private static final Map<String, Integer> CARD_POINTS = new HashMap<>();

        static {
            CARD_POINTS.put("A", 14);
            CARD_POINTS.put("K", 13);
            CARD_POINTS.put("Q", 12);
            CARD_POINTS.put("J", 11);
            CARD_POINTS.put("T", 10);
            CARD_POINTS.put("9", 9);
            CARD_POINTS.put("8", 8);
            CARD_POINTS.put("7", 7);
            CARD_POINTS.put("6", 6);
            CARD_POINTS.put("5", 5);
            CARD_POINTS.put("4", 4);
            CARD_POINTS.put("3", 3);
            CARD_POINTS.put("2", 2);
        }

        private String cards;
        private String cardsWithResolvedJoker;
        private int bid;
        private int rank;
        private int type;

        public Hand(String cards, int bid) {
            this.cards = cards;
            this.bid = bid;
            this.type = resolveType();
        }

        private int resolveType() {
            String[] split = cards.split("");
            Map<String, Integer> m = new HashMap<>();
            for (String s : split) {
                if (!m.containsKey(s)) {
                    m.put(s, 1);
                } else {
                    m.put(s, m.get(s) + 1);
                }
            }

            int fiveOfKind = 0;
            int fourOfKind = 0;
            int threeOfKind = 0;
            int twoOfKind = 0;

            for (Map.Entry<String, Integer> entry : m.entrySet()) {
                if (entry.getValue() == 5) {
                    fiveOfKind++;
                }
                if (entry.getValue() == 4) {
                    fourOfKind++;
                }
                if (entry.getValue() == 3) {
                    threeOfKind++;
                }
                if (entry.getValue() == 2) {
                    twoOfKind++;
                }
            }

            // Five of a kind
            if (fiveOfKind == 1) {
                return 7;
            }

            // Four of a kind
            if (fourOfKind == 1) {
                return 6;
            }

            // Full house
            if (threeOfKind == 1 && twoOfKind == 1) {
                return 5;
            }

            // Three of a kind
            if (threeOfKind == 1 && twoOfKind == 0) {
                return 4;
            }

            // Two pair
            if (twoOfKind == 2) {
                return 3;
            }

            // One pair
            if (twoOfKind == 1 && threeOfKind == 0) {
                return 2;
            }

            // High card
            return 1;
        }


        @Override
        public int compareTo(Hand o) {
            if (this.type > o.type) {
                return 1;
            }
            if (this.type < o.type) {
                return -1;
            }

            String[] left = this.cards.split("");
            String[] right = o.cards.split("");

            for (int i = 0; i < left.length; i++) {
                if (CARD_POINTS.get(left[i]) > CARD_POINTS.get(right[i])) {
                    return 1;
                }
                if (CARD_POINTS.get(left[i]) < CARD_POINTS.get(right[i])) {
                    return -1;
                }
            }
            return 0;
        }
    }

    @ToString
    private static class Hand2 implements Comparable<Hand2> {
        private static final Map<String, Integer> CARD_POINTS = new HashMap<>();

        static {
            CARD_POINTS.put("A", 14);
            CARD_POINTS.put("K", 13);
            CARD_POINTS.put("Q", 12);
            CARD_POINTS.put("J", 1);
            CARD_POINTS.put("T", 10);
            CARD_POINTS.put("9", 9);
            CARD_POINTS.put("8", 8);
            CARD_POINTS.put("7", 7);
            CARD_POINTS.put("6", 6);
            CARD_POINTS.put("5", 5);
            CARD_POINTS.put("4", 4);
            CARD_POINTS.put("3", 3);
            CARD_POINTS.put("2", 2);
        }

        private String cards;
        private String cardsWithJoker;
        private int bid;
        private int rank;
        private int type;

        public Hand2(String cards, int bid) {
            this.cards = resolveJoker(cards);
            this.bid = bid;
            this.type = resolveType();
            this.cardsWithJoker = cards;
        }

        private int resolveType() {
            String[] split = cards.split("");
            Map<String, Integer> m = new HashMap<>();
            for (String s : split) {
                if (!m.containsKey(s)) {
                    m.put(s, 1);
                } else {
                    m.put(s, m.get(s) + 1);
                }
            }

            int fiveOfKind = 0;
            int fourOfKind = 0;
            int threeOfKind = 0;
            int twoOfKind = 0;

            for (Map.Entry<String, Integer> entry : m.entrySet()) {
                if (entry.getValue() == 5) {
                    fiveOfKind++;
                }
                if (entry.getValue() == 4) {
                    fourOfKind++;
                }
                if (entry.getValue() == 3) {
                    threeOfKind++;
                }
                if (entry.getValue() == 2) {
                    twoOfKind++;
                }
            }

            // Five of a kind
            if (fiveOfKind == 1) {
                return 7;
            }

            // Four of a kind
            if (fourOfKind == 1) {
                return 6;
            }

            // Full house
            if (threeOfKind == 1 && twoOfKind == 1) {
                return 5;
            }

            // Three of a kind
            if (threeOfKind == 1 && twoOfKind == 0) {
                return 4;
            }

            // Two pair
            if (twoOfKind == 2) {
                return 3;
            }

            // One pair
            if (twoOfKind == 1 && threeOfKind == 0) {
                return 2;
            }

            // High card
            return 1;
        }


        @Override
        public int compareTo(Hand2 o) {
            if (this.type > o.type) {
                return 1;
            }
            if (this.type < o.type) {
                return -1;
            }

            String[] left = this.cardsWithJoker.split("");
            String[] right = o.cardsWithJoker.split("");

            for (int i = 0; i < left.length; i++) {
                if (CARD_POINTS.get(left[i]) > CARD_POINTS.get(right[i])) {
                    return 1;
                }
                if (CARD_POINTS.get(left[i]) < CARD_POINTS.get(right[i])) {
                    return -1;
                }
            }
            return 0;
        }

        private static String resolveJoker(String cards) {
            if (!cards.contains("J")) {
                return cards;
            }

            List<Hand> possibleHands = new ArrayList<>();

            for (String face : CARD_POINTS.keySet()) {
                possibleHands.add(new Hand(cards.replaceAll("J", face), 0));
            }

            possibleHands.sort(Hand::compareTo);
            Hand last = possibleHands.get(possibleHands.size() - 1);
            return last.cards;
        }
    }
}
