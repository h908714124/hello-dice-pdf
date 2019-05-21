package com.github.h908714124.dice;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static com.github.h908714124.dice.Oled.isPixel;

public class Dice {

    private static final int G = 100;
    private final PDDocument doc;

    private final PDImageXObject[] images;

    private Dice(PDImageXObject[] images, PDDocument doc) {
        this.images = images;
        this.doc = doc;
    }

    public static void main(String[] args) throws IOException {
        try (PDDocument doc = new PDDocument()) {

            PDImageXObject[] images = new PDImageXObject[6];
            for (int i = 0; i < 6; i++) {
                images[i] = PDImageXObject.createFromFile("src/main/resources/com/github/h908714124/dice/" + (i + 1) + ".png", doc);
            }
            new Dice(images, doc).drawDice();
            doc.save("out.pdf");
        }
    }

    private void drawDice() throws IOException {
        List<Combination> combinations = new ArrayList<>(6 * 6 * 6 * 6);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    for (int l = 0; l < 6; l++) {
                        combinations.add(new Combination(images, i, j, k, l));
                    }
                }
            }
        }
        Collections.shuffle(combinations);
        Collections.shuffle(combinations);


        Iterator<Combination> it = combinations.iterator();

        while (it.hasNext()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(doc, page)) {
                for (int col = 0; col < 5; col++) {
                    for (int row = 0; row < 7; row++) {
                        if (it.hasNext()) {
                            if (isPixel('A', col, 6 - row)) {
                                contentStream.setStrokingColor(Color.RED);
                            }
                            it.next().draw(contentStream, 70 + (col * G), 70 + (row * G));
                            contentStream.setStrokingColor(Color.BLACK);
                        }
                    }
                }
            }
        }
    }
}
