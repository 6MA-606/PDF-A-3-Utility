package com.example;

import java.io.IOException;

import com.example.utils.models.Invoice;

public class Main {

    public static void main(String[] args) throws IOException {

        Invoice invoice = new Invoice("123456");
        try {
            invoice.addInvoicePage();
            invoice.attachFile("./invoice.xml", "invoice.xml", "application/xml");
            // invoice.attachFile("./invoice.mp4", "invoice.mp4", "video/mp4");
            // invoice.attachFile("./invoice.sh", "invoice.ps1", "text/plain");
            invoice.save("Invoice.pdf");
            invoice.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}