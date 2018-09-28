package com.company.project.component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static java.lang.String.*;

public class XmlDocumentBuilder {

    public static byte[] buildXmlDocument(RequestType type, DataTransferObject dto) {

        switch (type) {
            case VERIFY:
                return getBytesFromXmlDocument(createVerfyXml(dto));
            case PAYMENT:
                return getBytesFromXmlDocument(createPaymentXml(dto));
            case STATUS:
                return getBytesFromXmlDocument(createStatusXml(dto));
            case CANCEL:
                return getBytesFromXmlDocument(createCancelXml(dto));
            default:
                throw new IllegalArgumentException("invalid argument");
        }
    }

    private static Document createVerfyXml(DataTransferObject dto) {
        Document document = createEmptyDocument();

        Element request = createRequestElement(document, dto);
        document.appendChild(request);

        Element verify = document.createElement("verify");
        verify.setAttribute("service", valueOf(dto.getService()));
        verify.setAttribute("account", valueOf(dto.getAccount()));

        request.appendChild(verify);

        return document;
    }

    private static Document createPaymentXml(DataTransferObject dto) {
        Document document = createEmptyDocument();

        Element request = createRequestElement(document, dto);
        document.appendChild(request);

        Element payment = document.createElement("payment");
        payment.setAttribute("id", valueOf(dto.getId()));
        payment.setAttribute("sum", valueOf(dto.getSumm()));
        payment.setAttribute("check", valueOf(dto.getCheckNumber()));
        payment.setAttribute("service", valueOf(dto.getService()));
        payment.setAttribute("account", valueOf(dto.getAccount()));
        payment.setAttribute("date", dto.getDate().toString());

        request.appendChild(payment);

        return document;
    }

    private static Document createStatusXml(DataTransferObject dto) {
        Document document = createEmptyDocument();

        Element request = createRequestElement(document, dto);
        document.appendChild(request);

        Element status = document.createElement("status");
        status.setAttribute("id", valueOf(dto.getId()));

        request.appendChild(status);

        return document;
    }

    private static Document createCancelXml(DataTransferObject dto) {
        Document document = createEmptyDocument();

        Element request = createRequestElement(document, dto);
        document.appendChild(request);

        Element cancel = document.createElement("cancel");
        cancel.setAttribute("id", valueOf(dto.getId()));
        cancel.setAttribute("person", String.valueOf(dto.getPersonId()));

        request.appendChild(cancel);

        return document;
    }

    private static byte[] getBytesFromXmlDocument(Document document) {

        byte[] result = null;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult stream = new StreamResult(out);
            transformer.transform(source, stream);

            result = out.toByteArray();

        } catch (TransformerFactoryConfigurationError | IOException | TransformerException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Document createEmptyDocument() {
        Document document = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return document;
    }

    private static Element createRequestElement(Document document, DataTransferObject dto) {
        Element request = document.createElement("request");
        request.setAttribute("point", valueOf(dto.getPoint()));
        return request;
    }

    private static byte[] testStringSuplie(String request) {
        try {
            return request.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
