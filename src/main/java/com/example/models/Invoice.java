package com.example.models;

import java.io.IOException;
import java.sql.Date;

import com.example.utils.models.PDA3Invoice;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Invoice {
    public String invoiceNumber;
    public Date date = null;

    public PDA3Invoice toPDA3Invoice() throws IOException {
        return new PDA3Invoice(invoiceNumber, date);
    }
}
