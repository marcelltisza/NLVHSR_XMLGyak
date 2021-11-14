package xpathnlvhsr1110;

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

public class xPathNLVHSR {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		
		try {
			File xmlFile = new File("src/xpathnlvhsr1110/studentNLVHSR.xml");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			
			Document document = dBuilder.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			//-------------------LEKÉRDEZÉSEK-------------------
			
			//gyökérelem
			//String expression = "class";
			
			//student id=1
			//String expression = "class/student[@id=01]";
			
			//összes student
			//String expression = "class/student";
			
			//második diák
			//String expression = "class/student[position()=2]";
			
			//utolsó diák
			//String expression = "class/student[last()]";
			
			//utolsó elõtti diák
			//String expression = "class/student[last()-1]";
			
			//Elsõ kettõ diák
			//String expression = "class/student[position()<3]";
			
			//összes gyermekelem
			//String expression = "//*";
			
			//diak van attributum
			//String expression = "class/student[@*]";
			
			//20 evnel idosebb diakok
			String expression = "class/student[kor>20]";
			
			//-------------------------------------------------
			
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				
				System.out.println("\nAktuális elem: " + node.getNodeName());
				
				if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
					Element element = (Element) node;
					
					System.out.println("Hallgató ID: " + element.getAttribute("id"));
					System.out.println("Vezeteknev: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
					System.out.println("Keresztnev: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());
					System.out.println("Becenev: " + element.getElementsByTagName("becenev").item(0).getTextContent());
					System.out.println("Kor: " + element.getElementsByTagName("kor").item(0).getTextContent());
				}
			}
		}
		catch(ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(SAXException e) {
			e.printStackTrace();
		}
		catch(XPathExpressionException e) {
			e.printStackTrace();
		}
	}

}
