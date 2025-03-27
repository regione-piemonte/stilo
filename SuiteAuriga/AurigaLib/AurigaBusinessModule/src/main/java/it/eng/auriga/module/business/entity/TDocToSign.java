/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the T_DOC_TO_SIGN database table.
 * 
 */
@Entity
@Table(name="T_DOC_TO_SIGN")
@NamedQuery(name="TDocToSign.findAll", query="SELECT t FROM TDocToSign t")
public class TDocToSign implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DOC_REC_ID")
	private String docRecId;

	@Column(name="BPM_PROC_INST_ID")
	private String bpmProcInstId;

	@Column(name="BPM_PROC_TY_ID")
	private String bpmProcTyId;

	@Temporal(TemporalType.DATE)
	@Column(name="CERT_DATE_FROM")
	private Date certDateFrom;

	@Temporal(TemporalType.DATE)
	@Column(name="CERT_DATE_TO")
	private Date certDateTo;

	@Column(name="CERT_ID_CODE")
	private String certIdCode;

	@Column(name="CERT_NAME")
	private String certName;

	@Temporal(TemporalType.DATE)
	@Column(name="DOC_DATE")
	private Date docDate;

	@Column(name="DOC_DESCRIPTION")
	private String docDescription;

	@Lob
	@Column(name="DOC_DETAILS")
	private String docDetails;

	@Column(name="DOC_ID")
	private String docId;

	@Column(name="DOC_TITLE")
	private String docTitle;

	@Column(name="FILE_NUMBER")
	private BigDecimal fileNumber;

	@Column(name="FLG_REQ_NUMBER")
	private String flgReqNumber;

	@Column(name="FLG_REQ_PROTOCOL")
	private String flgReqProtocol;

	@Column(name="INS_TIME")
	private Timestamp insTime;

	@Column(name="INST_ID_BPM_TASK_TO_COMPL")
	private String instIdBpmTaskToCompl;

	@Column(name="KEY_BPM_TASK_TO_COMPL")
	private String keyBpmTaskToCompl;

	@Column(name="LAST_UPD_TIME")
	private Timestamp lastUpdTime;

	@Column(name="NAME_BPM_TASK_TO_COMPL")
	private String nameBpmTaskToCompl;

	@Column(name="NUM_CATEGORY")
	private String numCategory;

	@Column(name="NUM_REGISTER_CODE")
	private String numRegisterCode;

	@Column(name="NUM_YEAR")
	private BigDecimal numYear;

	@Column(name="OUTCOME_BPM_TASK_TO_COMPL")
	private String outcomeBpmTaskToCompl;

	@Temporal(TemporalType.DATE)
	@Column(name="OUTCOME_TIME")
	private Date outcomeTime;

	@Column(name="PROV_DOC_ID")
	private String provDocId;

	@Column(name="PROV_PROCESS_ID")
	private String provProcessId;

	@Temporal(TemporalType.DATE)
	@Column(name="READ_TIME_SENDER")
	private Date readTimeSender;

	@Column(name="SENDER_APPLICATION")
	private String senderApplication;

	@Column(name="SENDING_TIME")
	private Timestamp sendingTime;

	@Column(name="SIGN_NEXT_ACT")
	private String signNextAct;

	@Column(name="SIGN_NEXT_ACT_TO")
	private String signNextActTo;

	@Column(name="SIGN_TYPE")
	private String signType;

	@Column(name="SIGNATURE_CONTEXT")
	private String signatureContext;

	@Lob
	@Column(name="SIGNER_ANNOTATION")
	private String signerAnnotation;

	@Column(name="SIGNER_OUTCOME")
	private String signerOutcome;

	@Column(name="SIGNER_USERID")
	private String signerUserid;

	@Column(name="VERIFIED_BY")
	private String verifiedBy;

	@Lob
	@Column(name="VERIFIER_ANNOTATION")
	private String verifierAnnotation;

	@Temporal(TemporalType.DATE)
	@Column(name="VERIFY_TIME")
	private Date verifyTime;

	//bi-directional many-to-one association to TFileDocToSign
	@OneToMany(mappedBy="TDocToSign")
	private List<TFileDocToSign> TFileDocToSigns;

	public TDocToSign() {
	}
	
	public TDocToSign(String docRecId, String bpmProcInstId, String bpmProcTyId, Date certDateFrom, Date certDateTo,
			String certIdCode, String certName, Date docDate, String docDescription, String docDetails, String docId,
			String docTitle, BigDecimal fileNumber, String flgReqNumber, String flgReqProtocol, Timestamp insTime,
			String instIdBpmTaskToCompl, String keyBpmTaskToCompl, Timestamp lastUpdTime, String nameBpmTaskToCompl,
			String numCategory, String numRegisterCode, BigDecimal numYear, String outcomeBpmTaskToCompl,
			Date outcomeTime, String provDocId, String provProcessId, Date readTimeSender, String senderApplication,
			Timestamp sendingTime, String signNextAct, String signNextActTo, String signType, String signatureContext,
			String signerAnnotation, String signerOutcome, String signerUserid, String verifiedBy,
			String verifierAnnotation, Date verifyTime, List<TFileDocToSign> tFileDocToSigns) {
		super();
		this.docRecId = docRecId;
		this.bpmProcInstId = bpmProcInstId;
		this.bpmProcTyId = bpmProcTyId;
		this.certDateFrom = certDateFrom;
		this.certDateTo = certDateTo;
		this.certIdCode = certIdCode;
		this.certName = certName;
		this.docDate = docDate;
		this.docDescription = docDescription;
		this.docDetails = docDetails;
		this.docId = docId;
		this.docTitle = docTitle;
		this.fileNumber = fileNumber;
		this.flgReqNumber = flgReqNumber;
		this.flgReqProtocol = flgReqProtocol;
		this.insTime = insTime;
		this.instIdBpmTaskToCompl = instIdBpmTaskToCompl;
		this.keyBpmTaskToCompl = keyBpmTaskToCompl;
		this.lastUpdTime = lastUpdTime;
		this.nameBpmTaskToCompl = nameBpmTaskToCompl;
		this.numCategory = numCategory;
		this.numRegisterCode = numRegisterCode;
		this.numYear = numYear;
		this.outcomeBpmTaskToCompl = outcomeBpmTaskToCompl;
		this.outcomeTime = outcomeTime;
		this.provDocId = provDocId;
		this.provProcessId = provProcessId;
		this.readTimeSender = readTimeSender;
		this.senderApplication = senderApplication;
		this.sendingTime = sendingTime;
		this.signNextAct = signNextAct;
		this.signNextActTo = signNextActTo;
		this.signType = signType;
		this.signatureContext = signatureContext;
		this.signerAnnotation = signerAnnotation;
		this.signerOutcome = signerOutcome;
		this.signerUserid = signerUserid;
		this.verifiedBy = verifiedBy;
		this.verifierAnnotation = verifierAnnotation;
		this.verifyTime = verifyTime;
		TFileDocToSigns = tFileDocToSigns;
	}

	public String getDocRecId() {
		return this.docRecId;
	}

	public void setDocRecId(String docRecId) {
		this.docRecId = docRecId;
	}

	public String getBpmProcInstId() {
		return this.bpmProcInstId;
	}

	public void setBpmProcInstId(String bpmProcInstId) {
		this.bpmProcInstId = bpmProcInstId;
	}

	public String getBpmProcTyId() {
		return this.bpmProcTyId;
	}

	public void setBpmProcTyId(String bpmProcTyId) {
		this.bpmProcTyId = bpmProcTyId;
	}

	public Date getCertDateFrom() {
		return this.certDateFrom;
	}

	public void setCertDateFrom(Date certDateFrom) {
		this.certDateFrom = certDateFrom;
	}

	public Date getCertDateTo() {
		return this.certDateTo;
	}

	public void setCertDateTo(Date certDateTo) {
		this.certDateTo = certDateTo;
	}

	public String getCertIdCode() {
		return this.certIdCode;
	}

	public void setCertIdCode(String certIdCode) {
		this.certIdCode = certIdCode;
	}

	public String getCertName() {
		return this.certName;
	}

	public void setCertName(String certName) {
		this.certName = certName;
	}

	public Date getDocDate() {
		return this.docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public String getDocDescription() {
		return this.docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public String getDocDetails() {
		return this.docDetails;
	}

	public void setDocDetails(String docDetails) {
		this.docDetails = docDetails;
	}

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocTitle() {
		return this.docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public BigDecimal getFileNumber() {
		return this.fileNumber;
	}

	public void setFileNumber(BigDecimal fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getFlgReqNumber() {
		return this.flgReqNumber;
	}

	public void setFlgReqNumber(String flgReqNumber) {
		this.flgReqNumber = flgReqNumber;
	}

	public String getFlgReqProtocol() {
		return this.flgReqProtocol;
	}

	public void setFlgReqProtocol(String flgReqProtocol) {
		this.flgReqProtocol = flgReqProtocol;
	}

	public Timestamp getInsTime() {
		return this.insTime;
	}

	public void setInsTime(Timestamp insTime) {
		this.insTime = insTime;
	}

	public String getInstIdBpmTaskToCompl() {
		return this.instIdBpmTaskToCompl;
	}

	public void setInstIdBpmTaskToCompl(String instIdBpmTaskToCompl) {
		this.instIdBpmTaskToCompl = instIdBpmTaskToCompl;
	}

	public String getKeyBpmTaskToCompl() {
		return this.keyBpmTaskToCompl;
	}

	public void setKeyBpmTaskToCompl(String keyBpmTaskToCompl) {
		this.keyBpmTaskToCompl = keyBpmTaskToCompl;
	}

	public Timestamp getLastUpdTime() {
		return this.lastUpdTime;
	}

	public void setLastUpdTime(Timestamp lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public String getNameBpmTaskToCompl() {
		return this.nameBpmTaskToCompl;
	}

	public void setNameBpmTaskToCompl(String nameBpmTaskToCompl) {
		this.nameBpmTaskToCompl = nameBpmTaskToCompl;
	}

	public String getNumCategory() {
		return this.numCategory;
	}

	public void setNumCategory(String numCategory) {
		this.numCategory = numCategory;
	}

	public String getNumRegisterCode() {
		return this.numRegisterCode;
	}

	public void setNumRegisterCode(String numRegisterCode) {
		this.numRegisterCode = numRegisterCode;
	}

	public BigDecimal getNumYear() {
		return this.numYear;
	}

	public void setNumYear(BigDecimal numYear) {
		this.numYear = numYear;
	}

	public String getOutcomeBpmTaskToCompl() {
		return this.outcomeBpmTaskToCompl;
	}

	public void setOutcomeBpmTaskToCompl(String outcomeBpmTaskToCompl) {
		this.outcomeBpmTaskToCompl = outcomeBpmTaskToCompl;
	}

	public Date getOutcomeTime() {
		return this.outcomeTime;
	}

	public void setOutcomeTime(Date outcomeTime) {
		this.outcomeTime = outcomeTime;
	}

	public String getProvDocId() {
		return this.provDocId;
	}

	public void setProvDocId(String provDocId) {
		this.provDocId = provDocId;
	}

	public String getProvProcessId() {
		return this.provProcessId;
	}

	public void setProvProcessId(String provProcessId) {
		this.provProcessId = provProcessId;
	}

	public Date getReadTimeSender() {
		return this.readTimeSender;
	}

	public void setReadTimeSender(Date readTimeSender) {
		this.readTimeSender = readTimeSender;
	}

	public String getSenderApplication() {
		return this.senderApplication;
	}

	public void setSenderApplication(String senderApplication) {
		this.senderApplication = senderApplication;
	}

	public Timestamp getSendingTime() {
		return this.sendingTime;
	}

	public void setSendingTime(Timestamp sendingTime) {
		this.sendingTime = sendingTime;
	}

	public String getSignNextAct() {
		return this.signNextAct;
	}

	public void setSignNextAct(String signNextAct) {
		this.signNextAct = signNextAct;
	}

	public String getSignNextActTo() {
		return this.signNextActTo;
	}

	public void setSignNextActTo(String signNextActTo) {
		this.signNextActTo = signNextActTo;
	}

	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignatureContext() {
		return this.signatureContext;
	}

	public void setSignatureContext(String signatureContext) {
		this.signatureContext = signatureContext;
	}

	public String getSignerAnnotation() {
		return this.signerAnnotation;
	}

	public void setSignerAnnotation(String signerAnnotation) {
		this.signerAnnotation = signerAnnotation;
	}

	public String getSignerOutcome() {
		return this.signerOutcome;
	}

	public void setSignerOutcome(String signerOutcome) {
		this.signerOutcome = signerOutcome;
	}

	public String getSignerUserid() {
		return this.signerUserid;
	}

	public void setSignerUserid(String signerUserid) {
		this.signerUserid = signerUserid;
	}

	public String getVerifiedBy() {
		return this.verifiedBy;
	}

	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}

	public String getVerifierAnnotation() {
		return this.verifierAnnotation;
	}

	public void setVerifierAnnotation(String verifierAnnotation) {
		this.verifierAnnotation = verifierAnnotation;
	}

	public Date getVerifyTime() {
		return this.verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public List<TFileDocToSign> getTFileDocToSigns() {
		return this.TFileDocToSigns;
	}

	public void setTFileDocToSigns(List<TFileDocToSign> TFileDocToSigns) {
		this.TFileDocToSigns = TFileDocToSigns;
	}

	public TFileDocToSign addTFileDocToSign(TFileDocToSign TFileDocToSign) {
		getTFileDocToSigns().add(TFileDocToSign);
		TFileDocToSign.setTDocToSign(this);

		return TFileDocToSign;
	}

	public TFileDocToSign removeTFileDocToSign(TFileDocToSign TFileDocToSign) {
		getTFileDocToSigns().remove(TFileDocToSign);
		TFileDocToSign.setTDocToSign(null);

		return TFileDocToSign;
	}

}
