/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.dao.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

/**
 * Bean for capturing grid preference
 * 
 * @author matzanin
 *
 */

@XmlRootElement
public class DocToSignBean extends AbstractBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3383889209398628399L;
	
	private String docRecId;
	private String bpmProcInstId;
	private String bpmProcTyId;
	private Date certDateFrom;
	private Date certDateTo;
	private String certIdCode;
	private String certName;
	private Date docDate;
	private String docDescription;
	private String docDetails;
	private String docId;
	private String docTitle;
	private BigDecimal fileNumber;
	private String flgReqNumber;
	private String flgReqProtocol;
	private Date insTime;
	private String instIdBpmTaskToCompl;
	private String keyBpmTaskToCompl;
	private Date lastUpdTime;
	private String nameBpmTaskToCompl;
	private String numCategory;
	private String numRegisterCode;
	private BigDecimal numYear;
	private String outcomeBpmTaskToCompl;
	private Date outcomeTime;
	private String provDocId;
	private String provProcessId;
	private Date readTimeSender;
	private String senderApplication;
	private Date sendingTime;
	private String signNextAct;
	private String signNextActTo;
	private String signType;
	private String signatureContext;
	private String signerAnnotation;
	private String signerOutcome;
	private String signerUserid;
	private String verifiedBy;
	private String verifierAnnotation;
	private Date verifyTime;
	private List<FileDocToSignBean> listaFileDocToSign;
	
	public String getDocRecId() {
		return docRecId;
	}
	public void setDocRecId(String docRecId) {
		this.docRecId = docRecId;
		this.getUpdatedProperties().add("docRecId");
	}
	public String getBpmProcInstId() {
		return bpmProcInstId;
	}
	public void setBpmProcInstId(String bpmProcInstId) {
		this.bpmProcInstId = bpmProcInstId;
		this.getUpdatedProperties().add("bpmProcInstId");
	}
	public String getBpmProcTyId() {
		return bpmProcTyId;
	}
	public void setBpmProcTyId(String bpmProcTyId) {
		this.bpmProcTyId = bpmProcTyId;
		this.getUpdatedProperties().add("bpmProcTyId");
	}
	public Date getCertDateFrom() {
		return certDateFrom;
	}
	public void setCertDateFrom(Date certDateFrom) {
		this.certDateFrom = certDateFrom;
		this.getUpdatedProperties().add("certDateFrom");
	}
	public Date getCertDateTo() {
		return certDateTo;
	}
	public void setCertDateTo(Date certDateTo) {
		this.certDateTo = certDateTo;
		this.getUpdatedProperties().add("certDateTo");
	}
	public String getCertIdCode() {
		return certIdCode;
	}
	public void setCertIdCode(String certIdCode) {
		this.certIdCode = certIdCode;
		this.getUpdatedProperties().add("certIdCode");
	}
	public String getCertName() {
		return certName;
	}
	public void setCertName(String certName) {
		this.certName = certName;
		this.getUpdatedProperties().add("certName");
	}
	public Date getDocDate() {
		return docDate;
	}
	public void setDocDate(Date docDate) {
		this.docDate = docDate;
		this.getUpdatedProperties().add("docDate");
	}
	public String getDocDescription() {
		return docDescription;
	}
	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
		this.getUpdatedProperties().add("docDescription");
	}
	public String getDocDetails() {
		return docDetails;
	}
	public void setDocDetails(String docDetails) {
		this.docDetails = docDetails;
		this.getUpdatedProperties().add("docDetails");
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
		this.getUpdatedProperties().add("docId");
	}
	public String getDocTitle() {
		return docTitle;
	}
	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
		this.getUpdatedProperties().add("docTitle");
	}
	public BigDecimal getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(BigDecimal fileNumber) {
		this.fileNumber = fileNumber;
		this.getUpdatedProperties().add("fileNumber");
	}
	public String getFlgReqNumber() {
		return flgReqNumber;
	}
	public void setFlgReqNumber(String flgReqNumber) {
		this.flgReqNumber = flgReqNumber;
		this.getUpdatedProperties().add("flgReqNumber");
	}
	public String getFlgReqProtocol() {
		return flgReqProtocol;
	}
	public void setFlgReqProtocol(String flgReqProtocol) {
		this.flgReqProtocol = flgReqProtocol;
		this.getUpdatedProperties().add("flgReqProtocol");
	}
	public Date getInsTime() {
		return insTime;
	}
	public void setInsTime(Date insTime) {
		this.insTime = insTime;
		this.getUpdatedProperties().add("insTime");
	}
	public String getInstIdBpmTaskToCompl() {
		return instIdBpmTaskToCompl;
	}
	public void setInstIdBpmTaskToCompl(String instIdBpmTaskToCompl) {
		this.instIdBpmTaskToCompl = instIdBpmTaskToCompl;
		this.getUpdatedProperties().add("instIdBpmTaskToCompl");
	}
	public String getKeyBpmTaskToCompl() {
		return keyBpmTaskToCompl;
	}
	public void setKeyBpmTaskToCompl(String keyBpmTaskToCompl) {
		this.keyBpmTaskToCompl = keyBpmTaskToCompl;
		this.getUpdatedProperties().add("keyBpmTaskToCompl");
	}
	public Date getLastUpdTime() {
		return lastUpdTime;
	}
	public void setLastUpdTime(Date lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
		this.getUpdatedProperties().add("lastUpdTime");
	}
	public String getNameBpmTaskToCompl() {
		return nameBpmTaskToCompl;
	}
	public void setNameBpmTaskToCompl(String nameBpmTaskToCompl) {
		this.nameBpmTaskToCompl = nameBpmTaskToCompl;
		this.getUpdatedProperties().add("nameBpmTaskToCompl");
	}
	public String getNumCategory() {
		return numCategory;
	}
	public void setNumCategory(String numCategory) {
		this.numCategory = numCategory;
		this.getUpdatedProperties().add("numCategory");
	}
	public String getNumRegisterCode() {
		return numRegisterCode;
	}
	public void setNumRegisterCode(String numRegisterCode) {
		this.numRegisterCode = numRegisterCode;
		this.getUpdatedProperties().add("numRegisterCode");
	}
	public BigDecimal getNumYear() {
		return numYear;
	}
	public void setNumYear(BigDecimal numYear) {
		this.numYear = numYear;
		this.getUpdatedProperties().add("numYear");
	}
	public String getOutcomeBpmTaskToCompl() {
		return outcomeBpmTaskToCompl;
	}
	public void setOutcomeBpmTaskToCompl(String outcomeBpmTaskToCompl) {
		this.outcomeBpmTaskToCompl = outcomeBpmTaskToCompl;
		this.getUpdatedProperties().add("outcomeBpmTaskToCompl");
	}
	public Date getOutcomeTime() {
		return outcomeTime;
	}
	public void setOutcomeTime(Date outcomeTime) {
		this.outcomeTime = outcomeTime;
		this.getUpdatedProperties().add("outcomeTime");
	}
	public String getProvDocId() {
		return provDocId;
	}
	public void setProvDocId(String provDocId) {
		this.provDocId = provDocId;
		this.getUpdatedProperties().add("provDocId");
	}
	public String getProvProcessId() {
		return provProcessId;
	}
	public void setProvProcessId(String provProcessId) {
		this.provProcessId = provProcessId;
		this.getUpdatedProperties().add("provProcessId");
	}
	public Date getReadTimeSender() {
		return readTimeSender;
	}
	public void setReadTimeSender(Date readTimeSender) {
		this.readTimeSender = readTimeSender;
		this.getUpdatedProperties().add("readTimeSender");
	}
	public String getSenderApplication() {
		return senderApplication;
	}
	public void setSenderApplication(String senderApplication) {
		this.senderApplication = senderApplication;
		this.getUpdatedProperties().add("senderApplication");
	}
	public Date getSendingTime() {
		return sendingTime;
	}
	public void setSendingTime(Date sendingTime) {
		this.sendingTime = sendingTime;
		this.getUpdatedProperties().add("sendingTime");
	}
	public String getSignNextAct() {
		return signNextAct;
	}
	public void setSignNextAct(String signNextAct) {
		this.signNextAct = signNextAct;
		this.getUpdatedProperties().add("signNextAct");
	}
	public String getSignNextActTo() {
		return signNextActTo;
	}
	public void setSignNextActTo(String signNextActTo) {
		this.signNextActTo = signNextActTo;
		this.getUpdatedProperties().add("signNextActTo");
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
		this.getUpdatedProperties().add("signType");
	}
	public String getSignatureContext() {
		return signatureContext;
	}
	public void setSignatureContext(String signatureContext) {
		this.signatureContext = signatureContext;
		this.getUpdatedProperties().add("signatureContext");
	}
	public String getSignerAnnotation() {
		return signerAnnotation;
	}
	public void setSignerAnnotation(String signerAnnotation) {
		this.signerAnnotation = signerAnnotation;
		this.getUpdatedProperties().add("signerAnnotation");
	}
	public String getSignerOutcome() {
		return signerOutcome;
	}
	public void setSignerOutcome(String signerOutcome) {
		this.signerOutcome = signerOutcome;
		this.getUpdatedProperties().add("signerOutcome");
	}
	public String getSignerUserid() {
		return signerUserid;
	}
	public void setSignerUserid(String signerUserid) {
		this.signerUserid = signerUserid;
		this.getUpdatedProperties().add("signerUserid");
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
		this.getUpdatedProperties().add("verifiedBy");
	}
	public String getVerifierAnnotation() {
		return verifierAnnotation;
	}
	public void setVerifierAnnotation(String verifierAnnotation) {
		this.verifierAnnotation = verifierAnnotation;
		this.getUpdatedProperties().add("verifierAnnotation");
	}
	public Date getVerifyTime() {
		return verifyTime;
	}
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
		this.getUpdatedProperties().add("verifyTime");
	}
	public List<FileDocToSignBean> getListaFileDocToSign() {
		return listaFileDocToSign;
	}
	public void setListaFileDocToSign(List<FileDocToSignBean> listaFileDocToSign) {
		this.listaFileDocToSign = listaFileDocToSign;
		this.getUpdatedProperties().add("listaFileDocToSign");
	}
	
}
