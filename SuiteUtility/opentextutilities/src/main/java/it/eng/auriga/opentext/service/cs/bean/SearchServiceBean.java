package it.eng.auriga.opentext.service.cs.bean;

public class SearchServiceBean {
	
	// whereCondition indicata nel seguente modo: "where1=\"" + CSDWOMetadataEnum.Order_Number.getAttributeNameCS() + "\": = \"" + dwo.getOrderNumber()) + "\" &where2=\"" + CSDWOMetadataEnum.Program.getAttributeNameCS() +"\": = \"" + dwo.getProgram() + "\" ";
	private String whereCondition;
	
	
	
	
	// bean che indica se va applicato uno specifico ordinamento. Di default il risultato sarà ordinato rispetto al OTFileName
	private SearchServiceSortingBean searchServiceSortingBean;
	
	//impostare il numero massimo di elementi da ricercare. Di default è 50
	private int numMaxToResearch = 5000;
	

	public String getWhereCondition() {
		return whereCondition;
	}

	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}



	

	public SearchServiceSortingBean getSearchServiceSortingBean() {
		return searchServiceSortingBean;
	}

	public void setSearchServiceSortingBean(SearchServiceSortingBean searchServiceSortingBean) {
		this.searchServiceSortingBean = searchServiceSortingBean;
	}

	public int getNumMaxToResearch() {
		return numMaxToResearch;
	}

	public void setNumMaxToResearch(int numMaxToResearch) {
		this.numMaxToResearch = numMaxToResearch;
	}
	
	
	

}
