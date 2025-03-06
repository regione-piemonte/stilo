package it.eng.suiteutility.dynamiccodedetector.utils;

import java.util.ArrayList;
import java.util.List;

public class CheckPdfCommentiFileBean {
	
	Boolean flgContieneCommenti = false ;
	List<Integer> pageWithCommentBox = new ArrayList<Integer>();
	
	public Boolean getFlgContieneCommenti() {
		return flgContieneCommenti;
	}
	public void setFlgContieneCommenti(Boolean flgContieneCommenti) {
		this.flgContieneCommenti = flgContieneCommenti;
	}
	public List<Integer> getPageWithCommentBox() {
		return pageWithCommentBox;
	}
	public void setPageWithCommentBox(List<Integer> pageWithCommentBox) {
		this.pageWithCommentBox = pageWithCommentBox;
	}	
	
}
