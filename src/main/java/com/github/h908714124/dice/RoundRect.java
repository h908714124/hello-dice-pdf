package com.github.h908714124.dice;


import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;

/**
 * Stolen from https://github.com/ralfstuckert/pdfbox-layout
 */
class RoundRect {

    private final static float BEZ = 0.551915024494f;

    /**
     * create points clockwise starting in upper left corner
     *
     * <pre>
     *     a          b
     *      ----------
     *     /          \
     *  h |            | c
     *    |            |
     *    |            |
     *   g \          / d
     *      ----------
     *     f          e
     * </pre>
     *
     * @param contentStream the content stream.
     * @param upperLeft the upper left point
     * @param width the width
     * @param height the height
     * @param cornerRadiusX the corner radius in x direction
     * @throws IOException by pdfbox
     */
    static void addRoundRect(
            PDPageContentStream contentStream,
            Position upperLeft,
            float width,
            float height,
            float cornerRadiusX) throws IOException {
        float nettoWidth = width - 2 * cornerRadiusX;
        float nettoHeight = height - 2 * cornerRadiusX;

        // top line
        Position a = new Position(upperLeft.getX() + cornerRadiusX,
                upperLeft.getY());
        Position b = new Position(a.getX() + nettoWidth, a.getY());
        // right line
        Position c = new Position(upperLeft.getX() + width, upperLeft.getY()
                - cornerRadiusX);
        Position d = new Position(c.getX(), c.getY() - nettoHeight);
        // bottom line
        Position e = new Position(
                upperLeft.getX() + width - cornerRadiusX, upperLeft.getY()
                - height);
        Position f = new Position(e.getX() - nettoWidth, e.getY());
        // left line
        Position g = new Position(upperLeft.getX(), upperLeft.getY() - height
                + cornerRadiusX);
        Position h = new Position(g.getX(), upperLeft.getY()
                - cornerRadiusX);

        float bezX = cornerRadiusX * BEZ;
        float bezY = cornerRadiusX * BEZ;

        contentStream.moveTo(a.getX(), a.getY());
        addLine(contentStream, a.getX(), a.getY(), b.getX(), b.getY());
        contentStream.curveTo(b.getX() + bezX, b.getY(), c.getX(),
                c.getY() + bezY, c.getX(), c.getY());
        // contentStream.addLine(c.getX(), c.getY(), d.getX(), d.getY());
        addLine(contentStream, c.getX(), c.getY(), d.getX(), d.getY());
        contentStream.curveTo(d.getX(), d.getY() - bezY, e.getX() + bezX,
                e.getY(), e.getX(), e.getY());
        // contentStream.addLine(e.getX(), e.getY(), f.getX(), f.getY());
        addLine(contentStream, e.getX(), e.getY(), f.getX(), f.getY());
        contentStream.curveTo(f.getX() - bezX, f.getY(), g.getX(),
                g.getY() - bezY, g.getX(), g.getY());
        addLine(contentStream, g.getX(), g.getY(), h.getX(), h.getY());
        contentStream.curveTo(h.getX(), h.getY() + bezY, a.getX() - bezX,
                a.getY(), a.getX(), a.getY());
    }

    /**
     * Using lines won't give us a continuing path, which looks silly on fill.
     * So we are approximating lines with bezier curves... is there no better
     * way?
     */
    private static void addLine(
            final PDPageContentStream contentStream,
            float x1,
            float y1,
            float x2,
            float y2) throws IOException {
        float xMid = (x1 + x2) / 2f;
        float yMid = (y1 + y2) / 2f;
        contentStream.curveTo1(xMid, yMid, x2, y2);
    }
}