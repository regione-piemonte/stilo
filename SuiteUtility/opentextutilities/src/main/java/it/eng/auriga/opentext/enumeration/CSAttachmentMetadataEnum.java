package it.eng.auriga.opentext.enumeration;

public enum CSAttachmentMetadataEnum {

	PARENT_WORKORDER("Id Parent DWO"), TIPO_ATTACHMENT("Attachmet Type"), OTDataID("OTDataID"),
	OTReservedByName("OTReservedByName"),
	OTReserved("OTReserved"),
	OTName("OTName"),
	
	OTCreateDate("OTCreateDate"),
	
	OTReservedDate("OTReservedDate"),
	OTReservedTime("OTReservedTime"),
	
	OTLocation("Location");


	private String value;

	CSAttachmentMetadataEnum(String valueIn) {
		this.value = valueIn;
	}

	public String getValue() {
		return value;
	}

}
