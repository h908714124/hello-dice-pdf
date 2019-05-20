package com.github.h908714124.dice;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;

class Combination {

    private static final int WIDTH = 40;
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

    void draw(PDPageContentStream contents, int x, int y) throws IOException {
        contents.drawImage(images[i], x, y);
        contents.drawImage(images[j], x + WIDTH, y);
        contents.drawImage(images[k], x, y + WIDTH);
        contents.drawImage(images[l], x + WIDTH, y + WIDTH);
    }
}
