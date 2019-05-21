package com.github.h908714124.dice;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.h908714124.dice.Oled.isPixel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class DiceTest {

    @Test
    void sum10() {
        List<Combination> combinations = new Dice(new PDImageXObject[0], mock(PDDocument.class), "").allCombinations().stream()
                .filter(c -> c.sum() == 10)
                .collect(Collectors.toList());
        assertEquals(80, combinations.size());
    }

    @Test
    void sum12() {
        List<Combination> combinations = new Dice(new PDImageXObject[0], mock(PDDocument.class), "").allCombinations().stream()
                .filter(c -> c.sum() == 12)
                .collect(Collectors.toList());
        assertEquals(125, combinations.size());
    }

    @Test
    void countPixels() {
        String input = System.getProperty("testInput");
        char[] chars = input.toCharArray();
        int sum = 0;
        for (char c : chars) {
            sum += pixelsInChar(c);
        }
        System.out.println(input + " needs " + sum + " pixels");
    }

    private int pixelsInChar(char c) {
        int count = 0;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 7; row++) {
                if (isPixel(c, col, 6 - row)) {
                    count++;
                }
            }

        }
        return count;
    }
}