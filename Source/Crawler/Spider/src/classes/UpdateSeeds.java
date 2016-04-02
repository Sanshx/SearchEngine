package classes;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UpdateSeeds {
	
	//Reading seeds.xml and updating it in database.
	public static void updateSeeds(String seeds) {
		
		System.out.println("Reading seeds.xml...\n");
		
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document = null;
		String website = null;
		String type = null;
		
		factory = DocumentBuilderFactory.newInstance();
		
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("Error: ParseConfigurationException while updating the seeds. ");
			return;
		}
		try {
			document = builder.parse(new FileInputStream(seeds));
		} catch (FileNotFoundException e) {
			System.out.println("Error: FileNotFoundException while updating the seeds.");
			return;
		} catch (SAXException e) {
			System.out.println("Error: SAX Exception while updating the seeds.");
			return;
		} catch (IOException e) {
			System.out.println("Error: IOException while updating the seeds.");
			return;
		}
		NodeList firsttagList = document.getDocumentElement().getChildNodes();
		
		for (int i = 0; i < firsttagList.getLength(); i++) {
			Node firsttag = firsttagList.item(i);
			if (firsttag instanceof Element) {
				NodeList secondtag = firsttag.getChildNodes();
				for (int j = 0; j < secondtag.getLength(); j++) {
						Node cNode = secondtag.item(j);
						if (cNode instanceof Element) {	
							website = cNode.getLastChild().getTextContent().trim();
							type = firsttag.getNodeName();
							InjectSeed.injectSeed(website, type);
					}	
				}
			}
		}
		
		//removing seeds from the seeds_table which have been removed the the seeds.xml
		InjectSeed.cleanSeeds();	
	}
	
}