package it.eng.core.service.bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class FileListAttachDescription implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5109494872791302279L;
	
	private ArrayList<Integer> parPosition;
	private ArrayList<String> contentDisposition;
	private Integer listSize;
	
	public void setParPosition(ArrayList<Integer> parPosition) {
		this.parPosition = parPosition;
	}
	public ArrayList<Integer> getParPosition() {
		return parPosition;
	}
	public void setContentDisposition(ArrayList<String> contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	public ArrayList<String> getContentDisposition() {
		return contentDisposition;
	}
	public void setListSize(Integer listSize) {
		this.listSize = listSize;
	}
	public Integer getListSize() {
		return listSize;
	}
	
}
