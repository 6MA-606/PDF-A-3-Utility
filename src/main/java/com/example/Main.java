package com.example;

import com.example.models.Invoice;
import com.example.utils.models.PDA3Invoice;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Invoice invoice = mapper.readValue(System.in, Invoice.class);
        PDA3Invoice pda3Invoice = invoice.toPDA3Invoice();
        pda3Invoice.save("invoice.pdf");
    }

}