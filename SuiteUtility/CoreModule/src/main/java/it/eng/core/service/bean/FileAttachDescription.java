package it.eng.core.service.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * usato per serializzare un parametro che deve essere inviato come allegato
 * @author Russo
 *
 */
@XmlRootElement
public class FileAttachDescription extends AttachDescription implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 427335498104210021L;	 
	private String contentDisposition;

	public FileAttachDescription(){
	}
	
	public FileAttachDescription(Integer parPosition, String contentDisposition) {
		super();
		this.setParPosition(parPosition);
		this.contentDisposition = contentDisposition;
	}
	 
	public String getContentDisposition() {
		return contentDisposition;
	}
	
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
		 
	}

}
