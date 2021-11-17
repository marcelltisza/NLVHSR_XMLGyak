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
			// A DOM objektum létrehozása az XML dokumentumból
			File xmlFile = new File("XMLNLVHSR.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			// XPath objektum létrehozása
			XPath xPath = XPathFactory.newInstance().newXPath();

			//-------------------LEKÉRDEZÉSEK-------------------
			
			// 3000 Ft-nál nagyobb értékû rendelések
			String expression = "adatbazis/rendeles[fizetendo>3000]";
			
			// v2 azonosítójú vásárló
			//String expression = "adatbazis/vevo[@id='v2']";
			
			// 20 és 30 év közötti vásárlók
			//String expression = "adatbazis/vevo[kor>20 and kor<30]";
			
			// Az összes adatbázis bejegyzése
			//String expression = "adatbazis/*";
			
			//--------------------------------------------------
			
			// A query expression kiértékelése
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			
			// Az eredményül kapott elemek kiírása
			for (int j = 0; j < nodeList.getLength(); j++) {
				Node node = nodeList.item(j);
				System.out.println("\n>>> Elem típusa: " + node.getNodeName());
				Node nNode = nodeList.item(j);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)nNode;
					
					// Azonosító kiírása
					String id = elem.getAttribute("id");
					System.out.println("ID: " + id);
					
					// Tulajdonságok (gyermek elemek) kiírása
					String nodeContent = "";
					NodeList childNodes = elem.getChildNodes();
					for (int k = 0; k < childNodes.getLength(); k++) {
						if (childNodes.item(k).getTextContent().trim() != "") {
							nodeContent = normalizeText(childNodes.item(k).getTextContent().trim());
							System.out.println(childNodes.item(k).getNodeName() + ": " + nodeContent);
						}	
					}
				}
				System.out.println();
			}
		}
		// Esetleges hibák kezelése	
		catch(ParserConfigurationException | IOException | SAXException | XPathExpressionException ex) {
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
