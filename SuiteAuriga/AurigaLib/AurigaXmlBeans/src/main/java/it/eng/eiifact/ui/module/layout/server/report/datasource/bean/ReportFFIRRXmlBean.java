/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import java.util.Date;

public class ReportFFIRRXmlBean {

	@NumeroColonna(numero="1")
    private String idReport;
	
	@NumeroColonna(numero="2")
	private String invoiceNumber;
	
	@NumeroColonna(numero="3")
	@TipoData(tipo=Tipo.DATA)
    private Date creationDate;
	
	@NumeroColonna(numero="4")
	@TipoData(tipo=Tipo.DATA)
    private Date postingDate;
	
	@NumeroColonna(numero="5")
	private String postingStatus;
	
    @NumeroColonna(numero="6")
	private String invoiceType;

    @NumeroColonna(numero="7")	
	private String billingDocumentDescription;
	
	@NumeroColonna(numero="8")
	private String soldTo;
	
	@NumeroColonna(numero="9")
	private String soldToName;

	public String getIdReport() {
		return idReport;
	}

	public void setIdReport(String idReport) {
		this.idReport = idReport;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getPostingStatus() {
		return postingStatus;
	}

	public void setPostingStatus(String postingStatus) {
		this.postingStatus = postingStatus;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getBillingDocumentDescription() {
		return billingDocumentDescription;
	}

	public void setBillingDocumentDescription(String billingDocumentDescription) {
		this.billingDocumentDescription = billingDocumentDescription;
	}

	public String getSoldTo() {
		return soldTo;
	}

	public void setSoldTo(String soldTo) {
		this.soldTo = soldTo;
	}

	public String getSoldToName() {
		return soldToName;
	}

	public void setSoldToName(String soldToName) {
		this.soldToName = soldToName;
	}
					
}