package com.example.utils.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;

import com.example.utils.PDA3Utils;

public class PDA3Document extends PDDocument {

    public PDA3Document() throws IOException {
        super();
        PDA3Utils utils = new PDA3Utils();
        utils.addPDA3Metadata(this);
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

            Map<String, PDComplexFileSpecification> embeddedFileMap = new HashMap<String, PDComplexFileSpecification>();
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
