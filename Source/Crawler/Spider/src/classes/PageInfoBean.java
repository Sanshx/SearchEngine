package classes;

import java.util.HashSet;
import java.util.Set;

public class PageInfoBean {
	
	private Set<String> links = new HashSet<String>();
	private int number = 0;
	private boolean readStatus = true;
	
	public Set<String> getLinks() {
		return links;
	}
	public void setLinks(Set<String> links) {
		this.links = links;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public boolean isReadStatus() {
		return readStatus;
	}
	public void setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
	}	
	
}
