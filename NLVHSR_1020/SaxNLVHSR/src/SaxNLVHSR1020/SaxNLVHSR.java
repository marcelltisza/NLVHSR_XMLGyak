package SaxNLVHSR1020;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxNLVHSR {

	public static void main(String[] args) {
		try {
		    SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() { 
				public Map<String, Boolean> elementFlags = new HashMap<String, Boolean>();
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
					System.out.println(indentValue(elementFlags) + qName + formattedAttributeList(attributes) + " start");
					AddEntry(elementFlags, qName, true);
				}
				
				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					AddEntry(elementFlags, qName, false);
					System.out.println(indentValue(elementFlags) + qName + " end");
				}
				
				@Override
				public void characters(char[] ch, int start, int length) throws SAXException {
					System.out.println(indentValue(elementFlags) + new String(ch, start, length));
				}
			};
			
			saxParser.parse("src/SaxNLVHSR1020/macskakNLVHSR.xml", handler);
		}
		catch(Exception e) { }

	}	
	public static String indentValue(Map<String, Boolean> map) {
		String spaces = "";
		
		for (Map.Entry<String, Boolean> entry : map.entrySet()) {
	        if (entry.getKey() != null && entry.getValue() == true)
	        	spaces += "  ";
	    }
		
		return spaces;
	}

	public static void AddEntry(Map<String, Boolean> map, String key, Boolean value) {
		if (map.get(key) == null)
			map.put(key, value);
		else
			map.replace(key, value);
	}
	
	public static String formattedAttributeList(Attributes attributes) {
		if (attributes.getLength() == 0)
			return "";
		
		String attributeList = ", { ";
		for (int i = 0; i < attributes.getLength(); i++)
			attributeList += attributes.getLocalName(i) + ":" + attributes.getValue(i) + " ";
		return attributeList + "}";
	}
}
