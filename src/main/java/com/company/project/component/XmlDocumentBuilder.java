package com.company.project.component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Node;

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
//                return getBytesFromXmlDocument(createOnlineAdvace(dto, "topUpDeposit"));
                return getBytesFromXmlDocument(createOnlineAdvace(dto, "getcardaccounts"));
//                 String payh = "D:\\garbage\\familnyAdvanced.txt";
//                 byte[] b;
//        try {
//            b = Files.readAllBytes(Paths.get(payh));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//                 return b;
//                
                 
               
            case BALANCE :
                return getBytesFromXmlDocument(createBalanceXml(dto));
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
        
        
        
        Element addOne = document.createElement("attribute");///
        addOne.setAttribute("name", "last4digits");
        addOne.setAttribute("value", "0074");
        
        Element addTwo = document.createElement("attribute");///
        addTwo.setAttribute("name", "documentSeries");
        addTwo.setAttribute("value", "SS");
        
        Element addThree = document.createElement("attribute");
        addThree.setAttribute("name", "documentNumber");
        addThree.setAttribute("value", "554488");
        
        Element addFour = document.createElement("attribute");///
        addFour.setAttribute("name", "name");
        addFour.setAttribute("value", "");
        
        Element addFive = document.createElement("attribute");///
        addFive.setAttribute("name", "ReceiverName");
        addFive.setAttribute("value", "brad");
        
        Element addSix = document.createElement("attribute");///
        addSix.setAttribute("name", "sender.phone");
        addSix.setAttribute("value", "0114448844");
        
        Element addSeven = document.createElement("attribute");/////
        addSeven.setAttribute("name", "sender.birthDate");
        addSeven.setAttribute("value", "01.01.1998");
        
        Element addEight = document.createElement("attribute");///
        addEight.setAttribute("name", "sender.firstName");
        addEight.setAttribute("value", "BRED");
        
        Element addNinth = document.createElement("attribute");////
        addNinth.setAttribute("name", "sender.address");
        addNinth.setAttribute("value", "my Epik AdreSS .,/");
        
        Element addTen = document.createElement("attribute");///
        addTen.setAttribute("name", "sender.isResident");
        addTen.setAttribute("value", "true");
        
        Element addEleven = document.createElement("attribute");///
        addEleven.setAttribute("name", "sender.doc.number");
        addEleven.setAttribute("value", "6650420");
        
        Element addTwelv = document.createElement("attribute");////
        addTwelv.setAttribute("name", "sender.doc.type");
        addTwelv.setAttribute("value", "U");
        
        Element add13 = document.createElement("attribute");
        add13.setAttribute("name", "sender.residence");
        add13.setAttribute("value", "GEORGIA");
        
        Element add14 = document.createElement("attribute");
        add14.setAttribute("name", "sender.country");
        add14.setAttribute("value", "GEO");
        
        Element add15 = document.createElement("attribute");////
        add15.setAttribute("name", "receiver.lastName");
        add15.setAttribute("value", "Radochina");
        
        Element add16 = document.createElement("attribute");///
        add16.setAttribute("name", "sender.doc.issuer");
        add16.setAttribute("value", "GVY UMVD");
        
        Element add17 = document.createElement("attribute");////
        add17.setAttribute("name", "leo-ps-terminalid");
        add17.setAttribute("value", "88");
        
        Element add18 = document.createElement("attribute");///
        add18.setAttribute("name", "email");
        add18.setAttribute("value", "380971234567");
        
        
        
        //// for edit purpose
        
        Element add20 = document.createElement("attribute");///
        add20.setAttribute("name", "receiver.lastName.edit");
        add20.setAttribute("value", "test-changed");
        
        Element add21 = document.createElement("attribute");///
        add21.setAttribute("name", "receiver.firstName.edit");
        add21.setAttribute("value", "test-changed");
        
        
        
        Element add19 = document.createElement("attribute");///
        add19.setAttribute("name", "reference");
        add19.setAttribute("value", "");
        
        //// for testing
        Element add22 = document.createElement("attribute");///
        add22.setAttribute("name", "T");
        add22.setAttribute("value", "REPORT");
        
        Element add23 = document.createElement("attribute");///
        add23.setAttribute("name", "D");
        add23.setAttribute("value", "31.05.2019");
        
        
        verify.appendChild(addOne);
//        verify.appendChild(addTwo);
//        verify.appendChild(addThree);
//        verify.appendChild(addFour);
//        verify.appendChild(addFive);
//        verify.appendChild(addSix);
//        verify.appendChild(addSeven);
//        verify.appendChild(addEight);
//        verify.appendChild(addNinth);
//        verify.appendChild(addTen);
//        verify.appendChild(addEleven);
//        verify.appendChild(addTwelv);
//        verify.appendChild(add13);
//        verify.appendChild(add14);
//        verify.appendChild(add15);
//        verify.appendChild(add16);
//        verify.appendChild(add17);
//        verify.appendChild(add18);
//        verify.appendChild(add19);
//        verify.appendChild(add20);
//        verify.appendChild(add21);
//        verify.appendChild(add22);
//        verify.appendChild(add23);



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
        
        
        
        Element addOne = document.createElement("attribute");///
        addOne.setAttribute("name", "phone");
        addOne.setAttribute("value", "");
        
        Element addTwo = document.createElement("attribute");///
        addTwo.setAttribute("name", "documentType");
        addTwo.setAttribute("value", "qwerty");
        
        Element addThree = document.createElement("attribute");
        addThree.setAttribute("name", "documentSeries");
        addThree.setAttribute("value", "cc");
        
        Element addFour = document.createElement("attribute");///
        addFour.setAttribute("name", "documentNumber");
        addFour.setAttribute("value", "12344");
        
        Element addFive = document.createElement("attribute");///
        addFive.setAttribute("name", "banKCode");
        addFive.setAttribute("value", "505");
        
        Element addSix = document.createElement("attribute");///
        addSix.setAttribute("name", "pay_comm");
        addSix.setAttribute("value", "8");
        
        Element addSeven = document.createElement("attribute");/////
        addSeven.setAttribute("name", "sender.birthDate");
        addSeven.setAttribute("value", "01.01.1998");
        
        Element addEight = document.createElement("attribute");///
        addEight.setAttribute("name", "sender.firstName");
        addEight.setAttribute("value", "BRAD");
        
        Element addNinth = document.createElement("attribute");////
        addNinth.setAttribute("name", "sender.address");
        addNinth.setAttribute("value", "my Epik AdreSS .,/");
        
        Element addTen = document.createElement("attribute");///
        addTen.setAttribute("name", "sender.isResident");
        addTen.setAttribute("value", "true");
        
        Element addEleven = document.createElement("attribute");///
        addEleven.setAttribute("name", "sender.doc.number");
        addEleven.setAttribute("value", "6650420");
        
        Element addTwelv = document.createElement("attribute");////
        addTwelv.setAttribute("name", "sender.doc.type");
        addTwelv.setAttribute("value", "U");
        
        Element add13 = document.createElement("attribute");
        add13.setAttribute("name", "sender.residence");
        add13.setAttribute("value", "GEORGIA");
        
        Element add14 = document.createElement("attribute");
        add14.setAttribute("name", "sender.country");
        add14.setAttribute("value", "GEO");
        
        Element add15 = document.createElement("attribute");////
        add15.setAttribute("name", "receiver.lastName");
        add15.setAttribute("value", "Radochina");
        
        Element add16 = document.createElement("attribute");///
        add16.setAttribute("name", "sender.doc.issuer");
        add16.setAttribute("value", "GVY UMVD");
        
        Element add17 = document.createElement("attribute");////
        add17.setAttribute("name", "receiver.firstName");
        add17.setAttribute("value", "Oksana");
        
        Element add18 = document.createElement("attribute");///
        add18.setAttribute("name", "leo-ps-terminalid");
        add18.setAttribute("value", "88");
        
        Element add19 = document.createElement("attribute");///
        add19.setAttribute("name", "reference");
        add19.setAttribute("value", "");
        
        
        payment.appendChild(addOne);
//        payment.appendChild(addTwo);
//        payment.appendChild(addThree);
//        payment.appendChild(addFour);
//        payment.appendChild(addFive);
//        payment.appendChild(addSix);
//        payment.appendChild(addSeven);
//        payment.appendChild(addEight);
//        payment.appendChild(addNinth);
//        payment.appendChild(addTen);
//        payment.appendChild(addEleven);
//        payment.appendChild(addTwelv);
//        payment.appendChild(add13);
//        payment.appendChild(add14);
//        payment.appendChild(add15);
//        payment.appendChild(add16);
//        payment.appendChild(add17);
//        payment.appendChild(add18);
//        payment.appendChild(add19);
        
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
    
    private static Document createBalanceXml(DataTransferObject dto) {
        Document document = createEmptyDocument();

        Element request = createRequestElement(document, dto);
        document.appendChild(request);

        Element balance = document.createElement("balance");

        request.appendChild(balance);

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
        
        Element att1 = document.createElement("attribute");
        att1.setAttribute("name", "merchant");
        att1.setAttribute("value", "000000020000001");
        
        Element att2 = document.createElement("attribute");
        att2.setAttribute("name", "id1");
        att2.setAttribute("value", "m.zinin@iboxbank.online");
        
        Element att3 = document.createElement("attribute");
        att3.setAttribute("name", "sum");
        att3.setAttribute("value", String.valueOf(dto.getSumm() / 100.00));
               
        Element att4 = document.createElement("attribute");
        att4.setAttribute("name", "number");
        att4.setAttribute("value", "777888");
        
        Element att5 = document.createElement("attribute");
        att5.setAttribute("name", "TPPstreetType");
        att5.setAttribute("value", "пр.");
        
        Element att6 = document.createElement("attribute");
        att6.setAttribute("name", "TPPstreetName");
        att6.setAttribute("value", "Лісовий");
        
        Element att7 = document.createElement("attribute");
        att7.setAttribute("name", "TPPhouseNumber");
        att7.setAttribute("value", "22");
        
        Element att8 = document.createElement("attribute");
        att8.setAttribute("name", "TPPowner");
        att8.setAttribute("value", "ТОВ Лео");
        
        Element att9 = document.createElement("attribute");
        att9.setAttribute("name", "TPPprocessing");
        att9.setAttribute("value", "ТОВ Мїйвь Інк");
        
        Element att10 = document.createElement("attribute");
        att10.setAttribute("name", "TPPtype");
        att10.setAttribute("value", "4");
        
        
        advance.appendChild(att1);
        advance.appendChild(att2);
        advance.appendChild(att3);
//        advance.appendChild(att4);
//        advance.appendChild(att5);
//        advance.appendChild(att6);
//        advance.appendChild(att7);
//        advance.appendChild(att8);
//        advance.appendChild(att9);
//        advance.appendChild(att10);
        
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
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
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