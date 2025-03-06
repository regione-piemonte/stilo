package it.eng.auriga.opentext.enumeration;

public enum CSMetadataEnum {
	
	
	
	
	Order_Number("Order Number", "OrderNumber"),
	Program("Program","Program"),
	Part_Number("Part Number", "PartNumber"),
	//questo è l'Apl definitivo
	Apl("Assembly Apl", "AssemblyApl"),
	Manufacture_Apl("Manufacture Apl", "ManufactureApl"),
	File_Path("File Path", ""),
	File_Type("File Type", ""),
	State("State", "StateDWO"),
	Opening_Date("Opening Date Time", "OpeningDateAndTime"),
	Archiving_Date("Archiving Date Time", "ArchivingDateAndTime"),
	Support_Note("Support Note", "IsSupportNote"),
	Last_Stamp_Page("Last Stamp Page", "LastPositionStamp"),
	Cost_Center("Cost Center", ""),
	Father_ODL("Father ODL", ""),
	Tag("Tag", "Tag"),
	OTDataID("OTDataID","IdWorkOrder"),
	OTCreateDate("Creation Date Time", "CreationDateAndTime"),
	OTReservedByName("OTReservedByName", "CheckedOutBy"),
	OTReservedDate("OTReservedDate", ""),
	OTReservedTime("OTReservedTime", ""),
	OTReserved("OTReserved", ""),
	OTLocation("Location", ""),
	OTName("OTName", ""),
	Phase_Sequence("Phase:Sequence", ""),
	Phase_RJob("Phase:RJob", ""),
	Phase_Barcode("Phase:Barcode", ""),
	Checkout_DateTime("Checkout Date Time", "");
	
	
	
	
	;
	
	
	private  String attributeNameCS, fieldBeanClient;
	
	
	private CSMetadataEnum(String valueCS, String valueBeanClient) {
		this.attributeNameCS = valueCS;
		this.fieldBeanClient= valueBeanClient;
		
	}

	public String getAttributeNameCS() {
		return attributeNameCS;
	}

	public String getFieldBeanClient() {
		return fieldBeanClient;
	}

	
	




}
