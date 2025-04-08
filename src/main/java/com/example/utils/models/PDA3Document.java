package com.example.utils.models;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;

import com.example.utils.PDA3Utils;

public class PDA3Document extends PDDocument {

    public PDA3Document() throws IOException {
        super();
        PDA3Utils utils = new PDA3Utils();
        utils.addPDA3Metadata(this);
    }

    // TODO: Add a constructor that takes a template PDF file and copies its pages to the new document
    public PDA3Document(String templatePath) throws IOException {
        super();
        try {
            PDDocument templatePDF = Loader.loadPDF(new File(templatePath));
            PDPageTree pageTree = templatePDF.getPages();
            pageTree.forEach(page -> {
                PDPage newPage = new PDPage(page.getMediaBox());
                newPage.setResources(page.getResources());

                // // Read the source page content stream and wrap it in a PDStream
                // try (InputStream contents = page.getContents()) {
                //     PDStream stream = new PDStream(this, contents); // `this` is your PDDocument
                //     newPage.setContents(stream); 
                // } // Critical line
                // this.addPage(newPage);
            });
            templatePDF.close();
        } catch (Exception e) {
            throw new IOException("Error loading template: " + e.getMessage(), e);
        }
        PDA3Utils utils = new PDA3Utils();
        utils.addPDA3Metadata(this);
    }

    public void attachText(String text, String attachmentName, String mimeType) throws IOException {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }

        if (attachmentName == null || attachmentName.isEmpty()) {
            attachmentName = "attachment.txt";
        }

        try (InputStream attachmentStream = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8))) {
            
            PDEmbeddedFile embeddedFile = new PDEmbeddedFile(this, attachmentStream);
            embeddedFile.setSubtype(mimeType);

            PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();
            fileSpec.setFile(attachmentName);
            fileSpec.setEmbeddedFile(embeddedFile);

            Map<String, PDComplexFileSpecification> embeddedFileMap = new HashMap<>();
            embeddedFileMap.put(attachmentName, fileSpec);

            PDEmbeddedFilesNameTreeNode fileSpecTree = new PDEmbeddedFilesNameTreeNode();
            fileSpecTree.setNames(embeddedFileMap);

            PDDocumentNameDictionary nameDict = new PDDocumentNameDictionary(this.getDocumentCatalog());
            nameDict.setEmbeddedFiles(fileSpecTree);
            this.getDocumentCatalog().setNames(nameDict);

        } catch (IOException e) {
            throw new IOException("Error attaching text: " + e.getMessage(), e);
        }
    }

    public void attachFile(String filePath, String mimeType) throws IOException {
        attachFile(filePath, null, mimeType);
    }

    public void attachFile(String filePath, String attachmentName, String mimeType) throws IOException {

        String fileNameInDocument = null;

        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        if (attachmentName == null || attachmentName.isEmpty()) {
            fileNameInDocument = filePath.substring(filePath.lastIndexOf("/") + 1);
        } else {
            fileNameInDocument = attachmentName;
        }

        try (InputStream attachmentStream = new FileInputStream(filePath)) {
            
            PDEmbeddedFile embeddedFile = new PDEmbeddedFile(this, attachmentStream);
            embeddedFile.setSubtype(mimeType);

            PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();
            fileSpec.setFile(fileNameInDocument);
            fileSpec.setEmbeddedFile(embeddedFile);

            Map<String, PDComplexFileSpecification> embeddedFileMap = new HashMap<>();
            embeddedFileMap.put(fileNameInDocument, fileSpec);

            PDEmbeddedFilesNameTreeNode fileSpecTree = new PDEmbeddedFilesNameTreeNode();
            fileSpecTree.setNames(embeddedFileMap);

            PDDocumentNameDictionary nameDict = new PDDocumentNameDictionary(this.getDocumentCatalog());
            nameDict.setEmbeddedFiles(fileSpecTree);
            this.getDocumentCatalog().setNames(nameDict);

        } catch (IOException e) {
            throw new IOException("Error attaching file: " + e.getMessage(), e);
        }
    }
}
