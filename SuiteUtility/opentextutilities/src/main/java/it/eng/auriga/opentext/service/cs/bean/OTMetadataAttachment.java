package it.eng.auriga.opentext.service.cs.bean;

import java.io.Serializable;



/**
 * mappa sul CS l'attachment collegato al DWO in elaborazione
 * @author tbarbaro
 *
 */
public class OTMetadataAttachment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long attachmentId;
	
	private Long parentDwoId;
	
	
	
	private String otTipoAttachment;
	
	private String oTReservedByName;
	
	private boolean oTReserved;
	
	private String oTFileName;
	
	public OTMetadataAttachment() {}
	
	public OTMetadataAttachment(long otAttachmentID) {
		
		this.attachmentId = otAttachmentID;
	}
	
	
	
	public long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public Long getParentDwoId() {
		return parentDwoId;
	}

	public void setParentDwoId(Long parentDwoId) {
		this.parentDwoId = parentDwoId;
	}

	

	public String getOtTipoAttachment() {
		return otTipoAttachment;
	}

	public void setOtTipoAttachment(String otTipoAttachment) {
		this.otTipoAttachment = otTipoAttachment;
	}

	public String getoTReservedByName() {
		return oTReservedByName;
	}

	public void setoTReservedByName(String oTReservedByName) {
		this.oTReservedByName = oTReservedByName;
	}

	public boolean getoTReserved() {
		return oTReserved;
	}

	public void setoTReserved(boolean oTReserved) {
		this.oTReserved = oTReserved;
	}

	public String getoTFileName() {
		return oTFileName;
	}

	public void setoTFileName(String oTFileName) {
		this.oTFileName = oTFileName;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof OTMetadataAttachment ){
			OTMetadataAttachment currentObj = (OTMetadataAttachment) obj;
			return currentObj.getAttachmentId() == this.getAttachmentId() ;
		}

		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	

}
