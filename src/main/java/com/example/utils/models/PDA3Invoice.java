package com.example.utils.models;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import com.example.models.Invoice;

public class PDA3Invoice extends PDA3Document {
    
    private Invoice invoice;
    
    public PDA3Invoice(Invoice invoice) throws IOException {
        super();

        this.invoice = invoice;

        this.createInvoicePage();
        this.attachXml();
    }

    public PDA3Invoice(Invoice invoice, String templatePath) throws IOException {
        super(templatePath);

        this.invoice = invoice;

        this.attachXml();
    }

    private void createInvoicePage() throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        this.addPage(page);

        PDContentWriter contentWriter = new PDContentWriter(this, page);
        contentWriter.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD));
        contentWriter.setFontSize(20);
        contentWriter.writeText("INVOICE", 60, (842 - 57));
        contentWriter.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA));
        contentWriter.setFontSize(12);
        contentWriter.writeText("NO.", 460, (842 - 57));
        contentWriter.writeText(this.invoice.getInvoiceNumber(), 485, (842 - 57));
        contentWriter.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD));
        contentWriter.setFontSize(12);

        contentWriter.writeText("Date:", 60, (842 - 106));
        contentWriter.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA));
        contentWriter.writeText(this.invoice.getDate(), 100, (842 - 106));

        contentWriter.close();
    }

    private void attachXml() throws IOException {

        try {
            String xmlString = this.invoice.toXmlString();
            String fileName = "invoice.xml";
            String mimeType = "text/xml";
    
            this.attachText(xmlString, fileName, mimeType);
        } catch (Exception e) {
            throw new IOException("Error attaching XML file", e);
        }
    }

    public void saveToFile(String filePath, String fileName) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        if (fileName == null || fileName.isEmpty()) {
            fileName = "invoice-" + this.invoice.getInvoiceNumber() + ".pdf";
        }
        this.save(filePath + "/" + fileName);
    }
}
