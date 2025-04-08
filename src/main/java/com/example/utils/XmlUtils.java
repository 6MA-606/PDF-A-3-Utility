package com.example.utils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import com.example.exceptions.XmlConversionException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

public class XmlUtils {

    private XmlUtils() {
        // Utility class, no instantiation
    }
    
    public static String objectToXmlString(Object object) throws XmlConversionException {
        try {
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            marshaller.marshal(object, outputStream);

            String xmlString = outputStream.toString(StandardCharsets.UTF_8);
            outputStream.close();

            return xmlString;
        } catch (Exception e) {
            throw new XmlConversionException("Error converting object to XML string", e);
        }
    }
}
