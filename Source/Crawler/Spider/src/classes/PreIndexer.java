package classes;

import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PreIndexer {
	
	static String[] tofetch= {"h1","h2","h3","h4","h5","h6","em","strong","b"};
	Elements elements = null;
	
	public void writeToFile(int page_id, String page_url, int type_id, Document doc) {
		ArrayList<String> array = new ArrayList<String>();
			
		for(Element meta : doc.select("meta")) {
			String text = meta.attr("content").replaceAll("\\[.*?\\]", "");
			array = new ProcessStopWords().removeStopWords(text);
			for(String word : array) {
				WordIndexer.wordIndexer(word, page_id, 1, type_id);
			}
		}
		
		for(String tag: tofetch) {
			elements = doc.select(tag);
			for(Element e: elements) {
				array = new ProcessStopWords().removeStopWords(e.text());
				for(String word : array) {
					WordIndexer.wordIndexer(word, page_id,degreeReturn(tag), type_id);
				}
			}
		}
		array = null;
	}
	
	
	public static int degreeReturn(String tag){
		if(tag.equals("h1")) return 2;
		else if(tag.equals("h2")) return 3;
		else if(tag.equals("h3")) return 4;
		else if(tag.equals("h4")) return 5;
		else if(tag.equals("h5")) return 6;
		else if(tag.equals("h6")) return 7;
		else if(tag.equals("strong")) return 8;
		else if(tag.equals("b")) return 8;
		else if(tag.equals("em")) return 8;
		else if(tag.equals("p")) return 9;
		else if(tag.equals("pre")) return 9;
		else return 0;
	}
	
}
