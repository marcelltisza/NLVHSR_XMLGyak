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
			
			// A DOM objektum l�trehoz�sa az XML dokumentumb�l
			File xmlFile = new File("XMLNLVHSR.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			// Gy�k�r elem ki�r�sa (adatbazis)
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName() + "\n");
			
			// Az adatb�zis elemeinek kiirat�sa tulajdons�gaikkal egy�tt form�zva
			String[] tagNames = { "vevo", "konyv", "kiado", "rendeles", "konyvRendeles", "torzsKartya" };
			for (String tagName : tagNames) {
				NodeList nodeList = doc.getElementsByTagName(tagName);
				String message = tagName + " elemek:";
				System.out.println("\n" + message);
				System.out.println("-".repeat(message.length()) + "\n");
				
				// Elemek tulajdons�gainak �s azonos�t�j�nak ki�r�sa
				for (int i = 0; i < nodeList.getLength(); i++) {
					Node nNode = nodeList.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element elem = (Element)nNode;
						
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
					}
					System.out.println();
				}
			}
			
		// Esetleges hib�k kezel�se	
		} catch (SAXException | IOException | ParserConfigurationException ex) {
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

}
