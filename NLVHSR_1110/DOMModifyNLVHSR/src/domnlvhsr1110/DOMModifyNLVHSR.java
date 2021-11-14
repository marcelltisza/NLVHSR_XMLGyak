package domnlvhsr1110;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMModifyNLVHSR {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		File xmlFile = new File("src/domnlvhsr1110/nyelvekNLVHSR.xml");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document document = dBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();
		
		NodeList nodes = document.getElementsByTagName("szerver_nyelvek");
		System.out.println(nodes.getLength());
		for	(int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				if ("Oracle".equals(node.getAttributes().getNamedItem("ceg").getTextContent())) {
					NodeList childNodes = node.getChildNodes();
					System.out.println(childNodes.getLength());
					
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node childNode = childNodes.item(j);
						System.out.println(childNode.getTextContent());
						
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
							if ("Oracle 01".equals(childNode.getTextContent().trim())) {
								childNode.setTextContent("OOP");
								System.out.println("true 1");
							}
							
							if ("Oracle 02".equals(childNode.getTextContent())) {
								childNode.setTextContent("Document-oriented DB");
								System.out.println("true 2");
							}
						}
					}
				}
			}
		}
		
		File myFile = new File("src/domnlvhsr1110/nyelvekNLVHSR.xml");
		writeXml(document, myFile);

	}
	
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
