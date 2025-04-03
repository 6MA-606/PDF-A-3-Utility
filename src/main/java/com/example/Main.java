package com.example;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;

import com.example.utils.PDA3Utils;
import com.example.utils.models.PDA3Document;
import com.example.utils.models.PDContentWriter;

public class Main {
    // Invoice PDF/A-3 Document Example Data
    private static final String INVOICE_TITLE = "Invoice";
    private static final String INVOICE_SUBTITLE = "Invoice #12345";
    private static final String INVOICE_CONTENT = "This is a PDF/A-3 document.";
    private static final String INVOICE_FOOTER = "Created using Apache PDFBox.";
    private static final float TITLE_FONT_SIZE = 16;
    private static final float SUBTITLE_FONT_SIZE = 12;
    private static final float CONTENT_FONT_SIZE = 10;
    private static final float TITLE_X = 100;
    private static final float TITLE_Y = 700;
    private static final float SUBTITLE_X = 100;
    private static final float SUBTITLE_Y = 675;
    private static final float CONTENT_X = 100;
    private static final float CONTENT_Y = 650;
    private static final String OUTPUT_FILE = "Invoice.pdf";
    public static void main(String[] args) {
        try {
            PDA3Utils pdfa3Utils = new PDA3Utils();
            try (PDA3Document document = pdfa3Utils.createDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);
                PDContentWriter contentWriter = new PDContentWriter(document, page);
                contentWriter.setFontSize(16);
                contentWriter.writeText("Invoice", 100, 700);
                contentWriter.setFontSize(12);
                contentWriter.writeText("This is a PDF/A-3 document.", 100, 675);
                contentWriter.setFontSize(10);
                contentWriter.writeText("Created using Apache PDFBox.", 100, 650);
                contentWriter.close();
                document.save("hello.pdf");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}