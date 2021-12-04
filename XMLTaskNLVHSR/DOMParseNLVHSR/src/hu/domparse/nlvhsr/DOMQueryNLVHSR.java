package hu.domparse.nlvhsr;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMQueryNLVHSR {

	public static void main(String[] args) {
		try {
			// A DOM objektum l�trehoz�sa az XML dokumentumb�l
			File xmlFile = new File("XMLNLVHSR.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			String message = "3000 Ft-n�l nagyobb �rt�k� rendel�sek:";
			System.out.println(message + "\n" + "-".repeat(message.length()));
			Query1(document);
			message = "v2 azonos�t�j� v�s�rl�:";
			System.out.println(message + "\n" + "-".repeat(message.length()));
			Query2(document);
			message = "20 �s 30 �v k�z�tti v�s�rl�k:";
			System.out.println(message + "\n" + "-".repeat(message.length()));
			Query3(document);
		}
		// Esetleges hib�k kezel�se	
		catch(ParserConfigurationException | IOException | SAXException ex) {
			System.out.println("Some error occured\nDescription:\n" + ex.getMessage());
			ex.printStackTrace();
		}
	}
				
	// Sz�veg formaz�sa a sz�p megjelen�s �rdek�ben
	private static String normalizeText(String text) {
		text = text.replaceAll("\\n", ", ");
		text = text.replaceAll("\\s+", " ");
		return text;
	}
	
	// 3000 Ft-n�l nagyobb �rt�k� rendel�sek
	private static void Query1(Document doc) {
		NodeList rendelesek = doc.getElementsByTagName("rendeles");
		for (int i = 0; i < rendelesek.getLength(); i++) {
			Element rendeles = (Element)rendelesek.item(i);
			NodeList childNodes = rendeles.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node childNode = childNodes.item(j);
				if (childNode.getNodeName().equals("fizetendo") )
					if (Integer.parseInt(childNode.getTextContent()) > 3000)
						printElement(rendeles);
			}
		}
	}
	
	// v2 azonos�t�j� v�s�rl�
	private static void Query2(Document doc) {
		NodeList vevok = doc.getElementsByTagName("vevo");
		for (int i = 0; i < vevok.getLength(); i++) {
			Element vevo = (Element)vevok.item(i);
			if (vevo.getAttributeNode("id").getValue().equals("v1"))
				printElement(vevo);
		}
	}
	
	// 20 �s 30 �v k�z�tti v�s�rl�k
	private static void Query3(Document doc) {
		NodeList vevok = doc.getElementsByTagName("vevo");
		for (int i = 0; i < vevok.getLength(); i++) {
			Element vevo = (Element)vevok.item(i);
			NodeList childNodes = vevo.getChildNodes();
			for (int j = 0; j < childNodes.getLength(); j++) {
				Node childNode = childNodes.item(j);
				if (childNode.getNodeName().equals("kor") ) {					
					int kor = Integer.parseInt(childNode.getTextContent());
					if (kor > 20 && kor < 30)
						printElement(vevo);
				}
			}
		}
	}
	
	private static void printElement(Element elem) {
		// Azonos�t� ki�r�sa
		String id = elem.getAttribute("id");
		System.out.println(">>> ID: " + id);
		
		// Tulajdons�gok (gyermek elemek) ki�r�sa
		String nodeContent = "";
		NodeList childNodes = elem.getChildNodes();
		for (int j = 0; j < childNodes.getLength(); j++) {
			if (childNodes.item(j).getTextContent().trim() != "") {
				nodeContent = normalizeText(childNodes.item(j).getTextContent().trim());
				System.out.println(childNodes.item(j).getNodeName() + ": " + nodeContent);
			}	
		}
		System.out.println();
	}
}
