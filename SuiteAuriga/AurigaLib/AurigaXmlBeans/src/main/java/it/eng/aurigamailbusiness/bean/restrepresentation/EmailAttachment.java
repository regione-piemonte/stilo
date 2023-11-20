/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * NON togliere assolutamente 'implements Serializable'
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="allegatoEmail")
public class EmailAttachment implements Serializable {

	private static final long serialVersionUID = -9193203869234874897L;
	
	@XmlElement(name="idMessaggio")
	private String messageid;
	
	@XmlElement(name="nomeFile")
	private String filename;
	
	@XmlElement(name="tipoMIME")
	private String mimetype;
	
	@XmlElement(name="idContenuto")
	private String contentID;
	
	@XmlElement(name="disposizione")
	private String disposition;
	
	@XmlElement(name="dimensione")
	private Long size;
	
	@XmlElement(name="contenuto")
	private byte[] contents;
	
	@XmlElement(name="hashCodificato")
	private String encodedHash;
	
	@XmlElement(name="codifica")
	private String encoding;
	
	@XmlElement(name="algoritmo")
	private String algoritmo;

	
	public byte[] getContents() {
		return contents;
	}
	public void setContents(byte[] contents) {
		this.contents = contents;
	}
	
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}

	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	
	public String getContentID() {
		return contentID;
	}
	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
	
	public String getEncodedHash() {
		return encodedHash;
	}
	public void setEncodedHash(String encodedHash) {
		this.encodedHash = encodedHash;
	}

	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getAlgoritmo() {
		return algoritmo;
	}
	public void setAlgoritmo(String algoritmo) {
		this.algoritmo = algoritmo;
	}

}
