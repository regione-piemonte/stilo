/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.math.BigDecimal;


/**
 * The persistent class for the DMT_NESTLE_FFIRR database table.
 * 
 */
@Entity
@Table(name="DMT_NESTLE_FFIRR")
public class DmtNestleFFIRR implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_FFIRR")
	private String idFFIRR;
	
	@Column(name="CANC_INDICATOR")
	private String cancellationIndicator; 
	
	@Column(name="INVOICE_NUMBER")
	private String invoiceNumber;
	
	@Column(name="CREATION_DATE")
	private Date creationDate;
	
	@Column(name="POSTING_DATE")
	private Date postingDate;
	
	@Column(name="POSTING_STATUS")
	private String postingStatus;
	
	@Column(name="INVOICE_TYPE")
	private String invoiceType;
	
	@Column(name="BILLING_DOC_DESC")
	private String billingDocumentDescription;
	
	@Column(name="SOLD_TO")
	private String soldTo;
	
	@Column(name="SOLD_TO_NAME")
	private String soldToName;
	
	@Column(name="PAYER")
	private String payer;
	
	@Column(name="PAYER_NAME")
	private String payerName;
	
	@Column(name="BILL_TO")
	private String billTo;
	
	@Column(name="BILL_TO_NAME")
	private String billToName;
	
	@Column(name="SALES_ORDER")
	private String salesOrder;
	
	@Column(name="BROKER")
	private String broker;
	
	@Column(name="BROKER_NAME")
	private String brokerName;
	
	@Column(name="NET_VALUE")
	private String netValue;
	
	@Column(name="CURRENCY")
	private String currency;
	
	@Column(name="OUTPUT_TYPE")
	private String outputType;
	
	@Column(name="OUTPUT_TYPE_DESC")
	private String outputTypeDescription;
	
	@Column(name="MEDIUM")
	private String medium;
	
	@Column(name="OUTPUT_STATUS")
	private String outputStatus;
	
	@Column(name="PROCESSED_DATE")
	private Date processedDate;
	
	@Column(name="USER_ID_BILLING_DOC")
	private String userIdBillingDoc;
	
	@Column(name="USER_ID_OUTPUT")
	private String userIdOutput;
	
	@Column(name="MULTIPLE_OUTPUT_INDICATOR")
	private String multipleOutputIndicator;

	@Column(name="COD_APPL_OWNER")
	private String codApplOwner;
	
	@Column(name="FLG_ANN")
	private BigDecimal flgAnn;

	@Column(name="TS_INS")
	private Timestamp tsIns;

    public DmtNestleFFIRR() {
    }

	public String getIdFFIRR() {
		return idFFIRR;
	}

	public void setIdFFIRR(String idFFIRR) {
		this.idFFIRR = idFFIRR;
	}

	public String getCancellationIndicator() {
		return cancellationIndicator;
	}

	public void setCancellationIndicator(String cancellationIndicator) {
		this.cancellationIndicator = cancellationIndicator;
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

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getBillTo() {
		return billTo;
	}

	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}

	public String getBillToName() {
		return billToName;
	}

	public void setBillToName(String billToName) {
		this.billToName = billToName;
	}

	public String getSalesOrder() {
		return salesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getNetValue() {
		return netValue;
	}

	public void setNetValue(String netValue) {
		this.netValue = netValue;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public String getOutputTypeDescription() {
		return outputTypeDescription;
	}

	public void setOutputTypeDescription(String outputTypeDescription) {
		this.outputTypeDescription = outputTypeDescription;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getOutputStatus() {
		return outputStatus;
	}

	public void setOutputStatus(String outputStatus) {
		this.outputStatus = outputStatus;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public String getUserIdBillingDoc() {
		return userIdBillingDoc;
	}

	public void setUserIdBillingDoc(String userIdBillingDoc) {
		this.userIdBillingDoc = userIdBillingDoc;
	}

	public String getUserIdOutput() {
		return userIdOutput;
	}

	public void setUserIdOutput(String userIdOutput) {
		this.userIdOutput = userIdOutput;
	}

	public String getMultipleOutputIndicator() {
		return multipleOutputIndicator;
	}

	public void setMultipleOutputIndicator(String multipleOutputIndicator) {
		this.multipleOutputIndicator = multipleOutputIndicator;
	}

	public String getCodApplOwner() {
		return codApplOwner;
	}

	public void setCodApplOwner(String codApplOwner) {
		this.codApplOwner = codApplOwner;
	}

	public BigDecimal getFlgAnn() {
		return flgAnn;
	}

	public void setFlgAnn(BigDecimal flgAnn) {
		this.flgAnn = flgAnn;
	}

	public Timestamp getTsIns() {
		return tsIns;
	}

	public void setTsIns(Timestamp tsIns) {
		this.tsIns = tsIns;
	}

}