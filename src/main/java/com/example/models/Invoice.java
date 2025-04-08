package com.example.models;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.exceptions.XmlConversionException;
import com.example.utils.XmlUtils;
import com.example.utils.models.PDA3Invoice;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Setter;

@XmlRootElement
@XmlType(propOrder = { "invoiceNumber", "date" })
@Setter
public class Invoice {
    private String invoiceNumber;
    private Date date = null;

    public PDA3Invoice toPDA3Invoice() throws IOException {
        return new PDA3Invoice(this);
    }

    public String toXmlString() throws XmlConversionException {
        return XmlUtils.objectToXmlString(this);
    }

    @XmlElement
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @XmlElement
    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(date);
    }

    public Date getRawDate() {
        return date;
    }
}
