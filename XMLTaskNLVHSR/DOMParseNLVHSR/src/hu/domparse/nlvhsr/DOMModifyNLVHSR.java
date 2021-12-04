package hu.domparse.nlvhsr;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMModifyNLVHSR {

	public static void main(String[] args) {
		try {
			// A DOM objektum l�trehoz�sa az XML dokumentumb�l
			File xmlFile = new File("XMLNLVHSR.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			// A v1-es azonos�t�j� v�s�rl� kor�nak m�dos�t�sa 19-re
			NodeList nodes = document.getElementsByTagName("vevo");
			for	(int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if (node.getAttributes().getNamedItem("id").getTextContent().equals("v1")) {
						NodeList childNodes = node.getChildNodes();
						for (int j = 0; j < childNodes.getLength(); j++) {
							Node childNode = childNodes.item(j);
							if (childNode.getNodeName().equals("kor")) {
								childNode.setTextContent("19");
							}
						}
					}
				}
			}
			
			// Az k1-es azonos�t�j� k�nyv �r�nak m�dos�t�sa 5000-re
			nodes = document.getElementsByTagName("konyv");
			for	(int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if (node.getAttributes().getNamedItem("id").getTextContent().equals("k1")) {
						NodeList childNodes = node.getChildNodes();
						for (int j = 0; j < childNodes.getLength(); j++) {
							Node childNode = childNodes.item(j);
							if (childNode.getNodeName().equals("ar")) {
								childNode.setTextContent("5000");
							}
						}
					}
				}
			}
			
			//A kia1-es kiad� el�rhet�s�geinek a kib�v�t�se a valami@gmail.com email c�mmel
			nodes = document.getElementsByTagName("kiado");
			for	(int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					if (node.getAttributes().getNamedItem("id").getTextContent().equals("kia1")) {
						NodeList childNodes = node.getChildNodes();
						for (int j = 0; j < childNodes.getLength(); j++) {
							Node childNode = childNodes.item(j);
							if (childNode.getNodeName().equals("elerhetoseg")) {
								Element newElement = document.createElement("email");
								newElement.setTextContent("valami@gmail.com");
								childNode.appendChild(newElement);
							}
						}
					}
				}
			}
			
			// Kiirat�s
			File myFile = new File("XMLNLVHSR.xml");
			writeXml(document, myFile);
		} 
		// Esetleges hib�k kezel�se	
		catch (ParserConfigurationException | SAXException | IOException | TransformerException ex) {
			System.out.println("Some error occured\nDescription:\n" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	// A m�dos�tott XML dokumentum kiirat�sa f�jlba �s a konzolra
	private static void writeXml(Document doc, File output) throws TransformerException, UnsupportedEncodingException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transf = transformerFactory.newTransformer();
		transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amunt", "2");
		
		DOMSource source = new DOMSource(doc);
		
		StreamResult console = new StreamResult(System.out);
		StreamResult file = new StreamResult(output);
		
		transf.transform(source, console);
		transf.transform(source, file);
	}
}
