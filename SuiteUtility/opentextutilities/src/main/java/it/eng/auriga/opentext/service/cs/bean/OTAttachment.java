package it.eng.auriga.opentext.service.cs.bean;

public class OTAttachment {
	
	private OTDocumentContent documentContent;
	
	private Long idAttachment;
	
	private Long idDWO;
	
	private String attachmentType;

	public OTDocumentContent getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(OTDocumentContent documentContent) {
		this.documentContent = documentContent;
	}

	public Long getIdAttachment() {
		return idAttachment;
	}

	public void setIdAttachment(Long idAttachment) {
		this.idAttachment = idAttachment;
	}

	public Long getIdDWO() {
		return idDWO;
	}

	public void setIdDWO(Long idDWO) {
		this.idDWO = idDWO;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	
	

}
