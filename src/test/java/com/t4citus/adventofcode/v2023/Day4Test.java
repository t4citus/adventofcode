package com.t4citus.adventofcode.v2023;

import com.t4citus.adventofcode.AbstractTestBase;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day4Test extends AbstractTestBase {

    @Value("classpath:/2023/day4.txt")
    private Resource puzzleInputResource;

    @Test
    public void givenSamplePuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
        );

        // When
        Integer sum = lines.stream()
                .map(this::parseCard)
                .map(card -> calculatePoints(card.intersect().size()))
                .reduce(0, Integer::sum);

        // Then
        System.out.println("sum: " + sum);
        Assertions.assertThat(sum).isEqualTo(13);
    }

    @Test
    public void givenSamplePuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = Arrays.asList(
                "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
        );

        // When
        List<ScratchCard> cards = lines.stream()
                .map(this::parseScratchCard)
                .toList();
        // cards.forEach(System.out::println);

        Map<Integer, ScratchCard> allCards = cards.stream()
                .collect(Collectors.toMap(card -> card.cardNo, Function.identity()));

        List<Round> allRounds = new ArrayList<>();

        Round currentRound = new Round();
        currentRound.roundNo = 1;
        currentRound.cards = cards;
        currentRound.cardsAdded = cards.size();
        currentRound.print();
        allRounds.add(currentRound);

        while (currentRound.cardsAdded > 0) {
            Round nextRound = processScratchCards(allCards, currentRound);
            currentRound = nextRound;
            allRounds.add(currentRound);
            nextRound.print();
        }

        // Then
        int sum = allRounds.stream()
                .map(round -> round.cards.size())
                .reduce(0, Integer::sum);
        System.out.println("sum: " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve1_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 205);

        // When
        Integer sum = lines.stream()
                .map(this::parseCard)
                .map(card -> calculatePoints(card.intersect().size()))
                .reduce(0, Integer::sum);

        // Then
        System.out.println("sum: " + sum);
    }

    @Test
    public void givenPuzzleInput_whenSolve2_thenReturnsAsExpected() {
        // Given
        List<String> lines = readLines(puzzleInputResource);
        assertLines(lines, 205);

        // When
        List<ScratchCard> cards = lines.stream()
                .map(this::parseScratchCard)
                .toList();
        // cards.forEach(System.out::println);

        Map<Integer, ScratchCard> allCards = cards.stream()
                .collect(Collectors.toMap(card -> card.cardNo, Function.identity()));

        List<Round> allRounds = new ArrayList<>();

        Round currentRound = new Round();
        currentRound.roundNo = 1;
        currentRound.cards = cards;
        currentRound.cardsAdded = cards.size();
        //currentRound.print();
        allRounds.add(currentRound);

        while (currentRound.cardsAdded > 0) {
            Round nextRound = processScratchCards(allCards, currentRound);
            currentRound = nextRound;
            allRounds.add(currentRound);
            //nextRound.print();
        }

        // Then
        int sum = allRounds.stream()
                .map(round -> round.cards.size())
                .reduce(0, Integer::sum);
        System.out.println("sum: " + sum);
    }

    @Test
    public void givenMatches_whenCalculatePoints_thenReturnAsExpected() {
        Assertions.assertThat(calculatePoints(0)).isEqualTo(0);
        Assertions.assertThat(calculatePoints(1)).isEqualTo(1);
        Assertions.assertThat(calculatePoints(2)).isEqualTo(2);
        Assertions.assertThat(calculatePoints(3)).isEqualTo(4);
        Assertions.assertThat(calculatePoints(4)).isEqualTo(8);
        Assertions.assertThat(calculatePoints(5)).isEqualTo(16);
    }

    private Card parseCard(String line) {
        int cardNo = Integer.parseInt(line.split(":")[0].substring("Card".length()).trim());
        String[] numbersParts = line.split(":")[1].split("\\|");
        Set<String> winningNumbers = Arrays.stream(numbersParts[0].split(" "))
                .map(num -> num.trim().length() == 0 ? null : num.trim())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<String> pickedNumbers = Arrays.stream(numbersParts[1].split(" "))
                .map(num -> num.trim().length() == 0 ? null : num.trim())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Card card = new Card(cardNo);
        card.winningNumbers.addAll(winningNumbers);
        card.pickedNumbers.addAll(pickedNumbers);
        return card;
    }

    private ScratchCard parseScratchCard(String line) {
        int cardNo = Integer.parseInt(line.split(":")[0].substring("Card".length()).trim());
        String[] numbersParts = line.split(":")[1].split("\\|");
        Set<String> winningNumbers = Arrays.stream(numbersParts[0].split(" "))
                .map(num -> num.trim().length() == 0 ? null : num.trim())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<String> pickedNumbers = Arrays.stream(numbersParts[1].split(" "))
                .map(num -> num.trim().length() == 0 ? null : num.trim())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Set<String> intersect = new HashSet<>(winningNumbers);
        intersect.retainAll(pickedNumbers);

        ScratchCard card = new ScratchCard();
        card.cardNo = cardNo;
        card.matches = intersect.size();
        card.cardId = "C" + cardNo + ":";
        return card;
    }

    private Round processScratchCards(Map<Integer, ScratchCard> allCards, Round round) {
        Round newRound = new Round();
        newRound.roundNo = round.roundNo + 1;
        newRound.cards = new ArrayList<>();
        newRound.cardsAdded = 0;

        for (ScratchCard card : round.cards) {
            if (card.matches > 0) {
                for (int no = 1; no <= card.matches; no++) {
                    ScratchCard found = allCards.get(card.cardNo + no);
                    ScratchCard newCard = new ScratchCard();
                    newCard.cardNo = found.cardNo;
                    newCard.matches = found.matches;
                    newCard.cardId = card.cardId + "C" + found.cardNo + ":";
                    newRound.cards.add(newCard);
                    newRound.cardsAdded += 1;
                }
            }
        }

        return newRound;
    }

    @ToString
    private static class Card {
        private int cardNo;
        private Set<String> winningNumbers;
        private Set<String> pickedNumbers;

        public Card(int cardNo) {
            this.cardNo = cardNo;
            this.pickedNumbers = new HashSet<>();
            this.winningNumbers = new HashSet<>();
        }

        private Set<String> intersect() {
            Set<String> intersect = new HashSet<>(winningNumbers);
            intersect.retainAll(pickedNumbers);
            return intersect;
        }
    }

    @ToString
    private static class ScratchCard {
        private int cardNo;
        private int matches;
        private String cardId;
    }

    @ToString
    private static class Round {
        private int roundNo;
        private List<ScratchCard> cards;
        private int cardsAdded;

        public void print() {
            System.out.println("ROUND " + roundNo + " (" + cardsAdded + " added)");
            cards.forEach(System.out::println);
            System.out.println();
        }
    }

    private int calculatePoints(int matches) {
        if (matches <= 0) {
            return 0;
        }
        if (matches == 1) {
            return 1;
        }
        int points = 1;
        for (int i = 2; i <= matches; i++) {
            points = points * 2;
        }
        return points;
    }
}
