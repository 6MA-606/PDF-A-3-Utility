package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.xml.XmpSerializer;

import com.example.utils.models.PDA3Document;

public class PDA3Utils {

    public PDA3Document createDocument() throws IOException {
        try {
            PDA3Document document = new PDA3Document();

            XMPMetadata xmp = XMPMetadata.createXMPMetadata();
            DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
            dc.setTitle("PDF/A-3 Document Example");
            
            PDFAIdentificationSchema idSchema = xmp.createAndAddPDFAIdentificationSchema();
            idSchema.setPart(3);
            idSchema.setConformance("B");
            
            ByteArrayOutputStream metadataOutput = new ByteArrayOutputStream();
            new XmpSerializer().serialize(xmp, metadataOutput, true);
            PDMetadata metadata = new PDMetadata(document);
            metadata.importXMPMetadata(metadataOutput.toByteArray());
            document.getDocumentCatalog().setMetadata(metadata);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try (InputStream colorProfile = classLoader.getResourceAsStream("sRGB.icc")) {
                PDOutputIntent intent = new PDOutputIntent(document, colorProfile);
                final String sRGBString = "sRGB IEC61966-2.1";
                intent.setInfo(sRGBString);
                intent.setOutputCondition(sRGBString);
                intent.setOutputConditionIdentifier(sRGBString);
                intent.setRegistryName("http://www.color.org");
                document.getDocumentCatalog().addOutputIntent(intent);
            }
            return document;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Ensure a return value in case of an exception
        }
    }

    public void addPDA3Metadata(PDDocument document) throws IOException {
        if (document == null) {
            throw new IllegalArgumentException("Document cannot be null");
        }
        PDMetadata metadata = new PDMetadata(document);
        document.getDocumentCatalog().setMetadata(metadata);
    }
}
