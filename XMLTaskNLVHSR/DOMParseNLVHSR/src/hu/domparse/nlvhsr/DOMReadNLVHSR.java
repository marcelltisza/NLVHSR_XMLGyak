package hu.domparse.nlvhsr;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMReadNLVHSR {

	public static void main(String[] args) {
		try {
			
			// A DOM objektum létrehozása az XML dokumentumból
			File xmlFile = new File("XMLNLVHSR.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			// Gyökér elem kiírása (adatbazis)
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName() + "\n");
			
			// Az adatbázis elemeinek kiiratása tulajdonságaikkal együtt formázva
			String[] tagNames = { "vevo", "konyv", "kiado", "rendeles", "konyvRendeles", "torzsKartya" };
			for (String tagName : tagNames) {
				NodeList nodeList = doc.getElementsByTagName(tagName);
				String message = tagName + " elemek:";
				System.out.println("\n" + message);
				System.out.println("-".repeat(message.length()) + "\n");
				
				// Elemek tulajdonságainak és azonosítójának kiírása
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node nNode = nodeList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element elem = (Element)nNode;
						
						// Azonosító kiírása
						String id = elem.getAttribute("id");
						System.out.println(">>> ID: " + id);
						
						// Tulajdonságok (gyermek elemek) kiírása
						String nodeContent = "";
						NodeList childNodes = elem.getChildNodes();
						for (int j = 0; j < childNodes.getLength(); j++) {
							if (childNodes.item(j).getTextContent().trim() != "") {
								nodeContent = normalizeText(childNodes.item(j).getTextContent().trim());
								System.out.println(childNodes.item(j).getNodeName() + ": " + nodeContent);
							}	
						}
					}
					System.out.println();
				}
			}
			
		// Esetleges hibák kezelése	
		} catch (SAXException | IOException | ParserConfigurationException ex) {
			System.out.println("Some error occured\nDescription:\n" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	// Szöveg formazása a szép megjelenés érdekében
	private static String normalizeText(String text) {
		text = text.replaceAll("\\n", ", ");
		text = text.replaceAll("\\s+", " ");
		return text;
	}

}
