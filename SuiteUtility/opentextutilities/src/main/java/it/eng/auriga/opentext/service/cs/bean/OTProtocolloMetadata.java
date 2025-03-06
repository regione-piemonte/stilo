package it.eng.auriga.opentext.service.cs.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * mappa la categoria del DigitalWorkorder sul CS
 * @author tbarbaro
 *
 */
public class OTProtocolloMetadata implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idDocument;

	private String orderNumber;

	private Date creationDate;

	private Date openingDate;

	private Date archivingDate;
	
	//private Date executionCheckoutDatetime;
	
	private Date checkoutDate;

	private String program;

	private String partNumber;

	private Integer counterPage;

	private String manufactureApl;

	private String assemblyApl;

	private Boolean isSupportNote = false;

	private String tag;

	private String stateDWO;

	private String checkedOutBy;
	
	
	
	private boolean oTReserved;
	
	private String oTFileName;
	
	private List<FaseMetadataBean> elencoFasi = new ArrayList<FaseMetadataBean>();
	
	private int lastStampPage =0;

	
	public Long getIdDocument() {
		return idDocument;
	}

	public void setIdDocument(Long idDocument) {
		this.idDocument = idDocument;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Integer getCounterPage() {
		return counterPage;
	}

	public void setCounterPage(Integer counterPage) {
		this.counterPage = counterPage;
	}

	public String getManufactureApl() {
		return manufactureApl;
	}

	public void setManufactureApl(String manufactureApl) {
		this.manufactureApl = manufactureApl;
	}

	public String getAssemblyApl() {
		return assemblyApl;
	}

	public void setAssemblyApl(String assemblyApl) {
		this.assemblyApl = assemblyApl;
	}

	public Boolean getIsSupportNote() {
		return isSupportNote;
	}

	public void setIsSupportNote(Boolean isSupportNote) {
		this.isSupportNote = isSupportNote;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStateDWO() {
		return stateDWO;
	}

	public void setStateDWO(String stateDWO) {
		this.stateDWO = stateDWO;
	}

	public String getCheckedOutBy() {
		return checkedOutBy;
	}

	public void setCheckedOutBy(String checkedOutBy) {
		this.checkedOutBy = checkedOutBy;
	}

	

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(Date openingDate) {
		this.openingDate = openingDate;
	}

	public Date getArchivingDate() {
		return archivingDate;
	}

	public void setArchivingDate(Date archivingDate) {
		this.archivingDate = archivingDate;
	}

	

	public boolean getOTReserved() {
		return oTReserved;
	}

	public void setOTReserved(boolean oTReserved) {
		this.oTReserved = oTReserved;
	}

	public String getOTFileName() {
		return oTFileName;
	}

	public void setOTFileName(String oTFileName) {
		this.oTFileName = oTFileName;
	}


	

	
	public List<FaseMetadataBean> getElencoFasi() {
		return elencoFasi;
	}

	public void setElencoFasi(List<FaseMetadataBean> elencoFasi) {
		this.elencoFasi = elencoFasi;
	}

	
	public int getLastStampPage() {
		return lastStampPage;
	}

	public void setLastStampPage(int lastStampPage) {
		this.lastStampPage = lastStampPage;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}



	
	
}
