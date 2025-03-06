package it.eng.utility.oomanager.bean;

import java.net.URI;

/**
 * Bean che mappa le immagini caricate sul documento
 * @author Rigo Michele
 *
 */
public class ImageBean {

	private URI uri;
	private String name;
	private String imageODT;
	private byte[] data;
		
	public ImageBean() {}
	
	public ImageBean(URI uri,String name) {
		this.uri = uri;
		this.name = name;
	}
	
	public ImageBean(String name,String imageODT) {
		this.imageODT = imageODT;
		this.name = name;
	}
	
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public String getImageODT() {
		return imageODT;
	}

	public void setImageODT(String imageODT) {
		this.imageODT = imageODT;
	}

	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	} 
	
}
