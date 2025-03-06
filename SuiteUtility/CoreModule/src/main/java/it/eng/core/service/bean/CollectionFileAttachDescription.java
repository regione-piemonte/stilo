package it.eng.core.service.bean;

import java.io.Serializable;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;
 /**
  * descrizione per una collezione di attach
  * @author Russo
  *
  */
@XmlRootElement
public class CollectionFileAttachDescription   extends AttachDescription implements Serializable{
	//parametro utilizzato per verificare se la stringa serializzata è un CollectionFileAttachDescription
	public static final String collMarker="@@CollectionFileAttachDescription@@";
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5109494872791302279L;
	
	private String marker;
	private String collectionClassName;
	private ArrayList<String> contentDisposition;
	private Integer listSize;
 
	public String getMarker() {
		return marker;
	}
	public void setMarker(String marker) {
		this.marker = marker;
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
	public String getCollectionClassName() {
		return collectionClassName;
	}
	public void setCollectionClassName(String collectionClassName) {
		this.collectionClassName = collectionClassName;
	}
	
}
