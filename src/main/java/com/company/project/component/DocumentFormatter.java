package com.company.project.component;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

@Component
public class DocumentFormatter {

	public String printDocument(String xmlDocument) {
		try {
			InputSource inputSource = new InputSource(new StringReader(xmlDocument));
			final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource)
					.getDocumentElement();
			final Boolean keepDeclaration = Boolean.valueOf(xmlDocument.startsWith("<?xml"));

			final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
			final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
			final LSSerializer writer = impl.createLSSerializer();
			writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE);
			writer.getDomConfig().setParameter("xml-declaration", keepDeclaration);

			return writer.writeToString(document);
		} catch (Exception ex) {
			throw new RuntimeException("exception in DocumentFormatter class", ex);
		}
	}

}
