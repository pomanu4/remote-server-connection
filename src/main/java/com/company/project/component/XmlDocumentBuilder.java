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
            case ONLINE_ADVANCE:
                return getBytesFromXmlDocument(createOnlineAdvace(dto, ""));
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
        
        
        
        
        Element addOne = document.createElement("attribute");
        addOne.setAttribute("name", "region");
        addOne.setAttribute("value", "H");
        
        Element addTwo = document.createElement("attribute");
        addTwo.setAttribute("name", "two");
        addTwo.setAttribute("value", "two");
        
        Element addThree = document.createElement("attribute");
        addThree.setAttribute("name", "three");
        addThree.setAttribute("value", "three");
        
        Element addFour = document.createElement("attribute");
        addFour.setAttribute("name", "four");
        addFour.setAttribute("value", "four");
        
        Element addFive = document.createElement("attribute");
        addFive.setAttribute("name", "five");
        addFive.setAttribute("value", "five");
        
        Element addSix = document.createElement("attribute");
        addSix.setAttribute("name", "six");
        addSix.setAttribute("value", "six");
        
        verify.appendChild(addOne);
//        verify.appendChild(addTwo);
//        verify.appendChild(addThree);
//        verify.appendChild(addFour);
//        verify.appendChild(addFive);
//        verify.appendChild(addSix);
        
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
        payment.setAttribute("date", dto.getDate());
        
        
        
        Element addOne = document.createElement("attribute");
        addOne.setAttribute("name", "region");
        addOne.setAttribute("value", "H");
        
        Element addTwo = document.createElement("attribute");
        addTwo.setAttribute("name", "PredPokSchet");
        addTwo.setAttribute("value", "222");
        
        Element addThree = document.createElement("attribute");
        addThree.setAttribute("name", "PeriodOplPred");
        addThree.setAttribute("value", "");
        
        Element addFour = document.createElement("attribute");
        addFour.setAttribute("name", "PeriodOpl");
        addFour.setAttribute("value", "");
        
        Element addFive = document.createElement("attribute");
        addFive.setAttribute("name", "Counter_IdCounter2");
        addFive.setAttribute("value", "1111");
        
        Element addSix = document.createElement("attribute");
        addSix.setAttribute("name", "Counter_IdCounter3");
        addSix.setAttribute("value", "5555");
        
        payment.appendChild(addOne);
        payment.appendChild(addTwo);
        payment.appendChild(addThree);
        payment.appendChild(addFour);
//        payment.appendChild(addFive);
//        payment.appendChild(addSix);
        
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
    
    private static  Document createOnlineAdvace(DataTransferObject dto, String function){
        Document document = createEmptyDocument();
        Element request = createRequestElement(document, dto);
        document.appendChild(request);
        
        Element advance = document.createElement("advanced");
        advance.setAttribute("service", String.valueOf(dto.getService()));
        advance.setAttribute("function", function);
        
        request.appendChild(advance);
        
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