package com.example.utils.models;

import java.io.IOException;

import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class PDContentWriter {

    private static final float INITIAL_FONT_SIZE = 12;
    private static final PDFont INITIAL_FONT = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private PDPageContentStream contentStream; 
    private PDFont font = INITIAL_FONT;
    private float fontSize = INITIAL_FONT_SIZE;

    public PDContentWriter(PDDocument document, PDPage page) throws IOException {
        this.contentStream = new PDPageContentStream(document, page);
        this.contentStream.setFont(INITIAL_FONT, INITIAL_FONT_SIZE);
    }

    public void close() throws IOException {
        if (this.contentStream != null) {
            this.contentStream.close();
        }
    }

    public void writeText(String text, float x, float y) throws IOException {
        this.contentStream.beginText();
        this.contentStream.newLineAtOffset(x, y);
        this.contentStream.showText(text);
        this.contentStream.endText();
    }
    
    private void updateFont() throws IOException {
        if (this.contentStream != null) {
            this.contentStream.setFont(this.font, this.fontSize);
        }
    }

    public void setFont(PDFont font) throws IOException {
        if (font == null) {
            throw new IllegalArgumentException("Font cannot be null");
        }
        this.font = font;
        updateFont();
    }

    public void setFontSize(float fontSize) throws IOException {
        if (fontSize <= 0) {
            throw new IllegalArgumentException("Font size must be greater than zero");
        }
        this.fontSize = fontSize;
        updateFont();
    }

    public PDPageContentStream getPageContentStream() {
        return this.contentStream;
    }

    public PDFont getFont() {
        return this.font;
    }

    public float getFontSize() {
        return this.fontSize;
    }
}
