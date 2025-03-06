package it.eng.auriga.opentext.service.cs.bean;

import it.eng.auriga.opentext.enumeration.DirectionSortingOnCSEnum;

public class SearchServiceSortingBean {
	
	private String sortedMetadata;
	
	private DirectionSortingOnCSEnum sortedDirection;

	public String getSortedMetadata() {
		return sortedMetadata;
	}

	public void setSortedMetadata(String sortedMetadata) {
		this.sortedMetadata = sortedMetadata;
	}

	public DirectionSortingOnCSEnum getSortedDirection() {
		return sortedDirection;
	}

	public void setSortedDirection(DirectionSortingOnCSEnum sortedDirection) {
		this.sortedDirection = sortedDirection;
	}

	
	
	

}
