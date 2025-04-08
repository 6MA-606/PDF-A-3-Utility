package com.example;


import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import com.example.models.Invoice;
import com.example.utils.models.PDA3Invoice;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws Exception {
        Options options = new Options();

        options.addOption("i", "json-invoice", true, "JSON file containing invoice data");
        options.addOption("o", "output-dir", true, "Output PDF file name");
        options.addOption("n", "output-name", true, "Output PDF file name");
        options.addOption("t", "template", true, "Template PDF file path");
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("i")) {
                String jsonFilePath = cmd.getOptionValue("i");

                ObjectMapper mapper = new ObjectMapper();
                Invoice invoice = mapper.readValue(new File(jsonFilePath), Invoice.class);

                String templatePath = cmd.getOptionValue("t");
                PDA3Invoice pda3Invoice = null;
                if (templatePath != null) {
                    pda3Invoice = invoice.toPDA3Invoice(templatePath);
                } else {
                    pda3Invoice = invoice.toPDA3Invoice();
                }
                String outputDir = cmd.getOptionValue("o", ".");
                String outputFileName = cmd.getOptionValue("n");

                pda3Invoice.saveToFile(outputDir, outputFileName);
                pda3Invoice.close();
            } else {
                System.out.println("No JSON file provided.");
            }
        } catch (Exception e) {
            System.out.println("Error processing command line arguments: " + e.getMessage());
        }
    }

}