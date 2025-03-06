package it.eng.utility.pdfUtility.bean;

import java.util.List;

public class PdfBean {

	private Boolean withComment = false;
	private Boolean editable = false;
	private Boolean containXForm = false;
	private Boolean multiLayer = false;
	private Boolean pdfA = false;
	
	private List<Integer> pagesWithComment;
	
	public Boolean getWithComment() {
		return withComment;
	}

	public void setWithComment(Boolean withComment) {
		this.withComment = withComment;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getMultiLayer() {
		return multiLayer;
	}

	public void setMultiLayer(Boolean multiLayer) {
		this.multiLayer = multiLayer;
	}

	public Boolean getPdfA() {
		return pdfA;
	}

	public void setPdfA(Boolean pdfA) {
		this.pdfA = pdfA;
	}
	
	public Boolean getContainXForm() {
		return containXForm;
	}

	public void setContainXForm(Boolean containXForm) {
		this.containXForm = containXForm;
	}

	public List<Integer> getPagesWithComment() {
		return pagesWithComment;
	}

	public void setPagesWithComment(List<Integer> pagesWithComment) {
		this.pagesWithComment = pagesWithComment;
	}
	
	
}
