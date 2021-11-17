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
			
			// XPath objektum l�trehoz�sa
			XPath xPath = XPathFactory.newInstance().newXPath();

			//-------------------LEK�RDEZ�SEK-------------------
			
			// 3000 Ft-n�l nagyobb �rt�k� rendel�sek
			String expression = "adatbazis/rendeles[fizetendo>3000]";
			
			// v2 azonos�t�j� v�s�rl�
			//String expression = "adatbazis/vevo[@id='v2']";
			
			// 20 �s 30 �v k�z�tti v�s�rl�k
			//String expression = "adatbazis/vevo[kor>20 and kor<30]";
			
			// Az �sszes adatb�zis bejegyz�se
			//String expression = "adatbazis/*";
			
			//--------------------------------------------------
			
			// A query expression ki�rt�kel�se
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			
			// Az eredm�ny�l kapott elemek ki�r�sa
			for (int j = 0; j < nodeList.getLength(); j++) {
				Node node = nodeList.item(j);
				System.out.println("\n>>> Elem t�pusa: " + node.getNodeName());
				Node nNode = nodeList.item(j);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)nNode;
					
					// Azonos�t� ki�r�sa
					String id = elem.getAttribute("id");
					System.out.println("ID: " + id);
					
					// Tulajdons�gok (gyermek elemek) ki�r�sa
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
		// Esetleges hib�k kezel�se	
		catch(ParserConfigurationException | IOException | SAXException | XPathExpressionException ex) {
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
