package com.example.models;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<String> items = new ArrayList<>();

    public void pushItem(String item) {
        if (item == null || item.isEmpty()) {
            throw new IllegalArgumentException("Item cannot be null or empty");
        }
        items.add(item);
    }

    public PDA3Invoice toPDA3Invoice() throws IOException {
        return new PDA3Invoice(this);
    }

    public PDA3Invoice toPDA3Invoice(String templatePath) throws IOException {
        return new PDA3Invoice(this, templatePath);
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
