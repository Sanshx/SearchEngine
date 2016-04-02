package javaClasses;

import java.io.File;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class Matcher {

	public String match(String string, File xmlfile) throws ParserConfigurationException, SAXException, IOException
	{ 
		String result="";
		
		String[] words = string.split("\\s+");
		int arraysize = words.length;
		string = words[arraysize-1];
		Document doc = new ReadXML().read(xmlfile);
		NodeList first = doc.getDocumentElement().getChildNodes();
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		String freq = null;
		
		for(int i=0;i<first.getLength();i++) {
			if(first.item(i).getTextContent().startsWith(string)) {
				freq=first.item(i).getAttributes().getNamedItem("freq").getNodeValue();
				int intfreq=Integer.parseInt(freq);
				hmap.put(first.item(i).getTextContent(), intfreq);
			}
		}
     
		String prevwords="";
		for(int i=0;i<(arraysize-1);i++){
    	  prevwords = prevwords.concat(words[i]);
    	  prevwords = prevwords.concat(" ");
		}
      
      Map<String, Integer> map = sortByValues(hmap); 
      Set<Entry<String, Integer>> set = map.entrySet();
      Iterator<Entry<String, Integer>> iterator2 = set.iterator();
      int count=1;
      while(iterator2.hasNext() && count<=4)
      {
          Entry<String, Integer> me = iterator2.next();
          result=result.concat("<option>"+prevwords+""+me.getKey()+"</option>");  
          count++;
     }
      return result;
      }
      
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	private static HashMap<String, Integer> sortByValues(HashMap<String, Integer> map) { 
	      List list = new LinkedList(map.entrySet());
	      // Defined Custom Comparator here
	      Collections.sort(list, new Comparator() {
	           public int compare(Object o1, Object o2) {
	              return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
	           }
	      });
	      HashMap<String, Integer> sortedHashMap = new LinkedHashMap<String, Integer>();
	      for (Iterator it = list.iterator(); it.hasNext();) {
	             Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
	             sortedHashMap.put(entry.getKey(), entry.getValue());
	      } 
	      return sortedHashMap;
	 }

	
}
