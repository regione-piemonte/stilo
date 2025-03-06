package it.eng.auriga.opentext.service.cs.bean;

import java.io.Serializable;

/**
 * classe che mappa le info principali relative al contenuto di un documento
 * 
 * @author tbarbaro
 *
 */
public class OTDocumentContent implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8663716440670368763L;
	private byte[] content;
	private String mimeType;
	private String name;

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OTDocumentContent(byte[] content, String mimeType, String name) {
		this.content = content;
		this.mimeType = mimeType;
		this.name=name;
	}

	public OTDocumentContent() {}
	
}
