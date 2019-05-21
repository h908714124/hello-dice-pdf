package com.github.h908714124.dice;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

class Combination {

    private static final float WIDTH = 40;
    private final PDImageXObject[] images;

    private final int i;
    private final int j;
    private final int k;
    private final int l;

    Combination(PDImageXObject[] images, int i, int j, int k, int l) {
        this.images = images;
        this.i = i;
        this.j = j;
        this.k = k;
        this.l = l;
    }

    void draw(PDPageContentStream contentStream, float x, float y) throws IOException {
        RoundRect.addRoundRect(contentStream, new Position(x - 8, y + (2 * WIDTH) + 3), 2 * WIDTH + 12, 2 * WIDTH + 10, 12);
        contentStream.stroke();
        contentStream.drawImage(images[i], x, y);
        contentStream.drawImage(images[j], x + WIDTH, y);
        contentStream.drawImage(images[k], x, y + WIDTH);
        contentStream.drawImage(images[l], x + WIDTH, y + WIDTH);
    }
}
