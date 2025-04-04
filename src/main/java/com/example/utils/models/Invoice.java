package com.example.utils.models;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class Invoice extends PDA3Document {
    
    private String invoiceNumber;

    public Invoice(String invoiceNumber) throws IOException {
        super();
        if (invoiceNumber == null || invoiceNumber.isEmpty()) {
            throw new IllegalArgumentException("Invoice number cannot be null or empty");
        }
        this.invoiceNumber = invoiceNumber;

        initialize();
    }

    private void initialize() throws IOException {
        addInvoicePage();
    }

    public void addInvoicePage() throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        this.addPage(page);

        PDContentWriter contentWriter = new PDContentWriter(this, page);
        contentWriter.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD));
        contentWriter.setFontSize(20);
        contentWriter.writeText("INVOICE", 60, (842 - 57));
        contentWriter.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA));
        contentWriter.setFontSize(12);
        contentWriter.writeText("NO.", 460, (842 - 57));
        contentWriter.writeText(invoiceNumber, 485, (842 - 57));
        contentWriter.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD));
        contentWriter.setFontSize(12);

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = sdf.format(today);
        contentWriter.writeText("Date:", 60, (842 - 106));
        contentWriter.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA));
        contentWriter.writeText(formattedDate, 100, (842 - 106));

        contentWriter.close();
    }
}
