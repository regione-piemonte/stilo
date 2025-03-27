/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the T_FILE_DOC_TO_SIGN_H database table.
 * 
 */
@Entity
@Table(name="T_FILE_DOC_TO_SIGN_H")
@NamedQuery(name="TFileDocToSignH.findAll", query="SELECT t FROM TFileDocToSignH t")
public class TFileDocToSignH implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="FILE_REC_ID")
	private String fileRecId;

	@Column(name="DIG_SIGN_FMT_IN")
	private String digSignFmtIn;

	@Column(name="DIG_SIGN_FMT_OUT")
	private String digSignFmtOut;

	@Column(name="DIGEST_ALGHORITM_IN")
	private String digestAlghoritmIn;

	@Column(name="DIGEST_ALGHORITM_OUT")
	private String digestAlghoritmOut;

	@Column(name="DIGEST_ENCODING_IN")
	private String digestEncodingIn;

	@Column(name="DIGEST_ENCODING_OUT")
	private String digestEncodingOut;

	@Column(name="DIGEST_IN")
	private String digestIn;

	@Column(name="DIGEST_OUT")
	private String digestOut;

	@Column(name="FILE_TY_VS_DOC")
	private String fileTyVsDoc;

	@Column(name="FILENAME_IN")
	private String filenameIn;

	@Column(name="FILENAME_OUT")
	private String filenameOut;

	@Column(name="FLG_EDITABLE_PDF_IN")
	private BigDecimal flgEditablePdfIn;

	@Column(name="FLG_EDITABLE_PDF_OUT")
	private BigDecimal flgEditablePdfOut;

	@Column(name="FLG_FILE_SIGNED_IN")
	private BigDecimal flgFileSignedIn;

	@Column(name="FLG_FILE_SIGNED_OUT")
	private BigDecimal flgFileSignedOut;

	@Column(name="FLG_FMT_CONV_OUT")
	private String flgFmtConvOut;

	@Column(name="FLG_GRAPHIC_SIGN_OUT")
	private BigDecimal flgGraphicSignOut;

	@Column(name="FLG_NESTED_SIGN_OUT")
	private BigDecimal flgNestedSignOut;

	@Column(name="FLG_TO_GEN_FROM_MODEL")
	private BigDecimal flgToGenFromModel;

	@Column(name="FLG_TO_MARK")
	private BigDecimal flgToMark;

	@Column(name="FLG_TO_SIGN")
	private BigDecimal flgToSign;

	@Column(name="FLG_VALID_SIGN_IN")
	private BigDecimal flgValidSignIn;

	@Column(name="FLG_VALID_TS_IN")
	private BigDecimal flgValidTsIn;

	@Column(name="FLG_WITH_COMMENTS_PDF_IN")
	private BigDecimal flgWithCommentsPdfIn;

	@Column(name="FLG_WITH_COMMENTS_PDF_OUT")
	private BigDecimal flgWithCommentsPdfOut;

	@Column(name="GRAPHIC_SIGN_INFO")
	private String graphicSignInfo;

	@Column(name="INS_TIME")
	private Timestamp insTime;

	@Column(name="LAST_UPD_TIME")
	private Timestamp lastUpdTime;

	@Column(name="MARK_INFO")
	private String markInfo;

	@Column(name="MIMETYPE_IN")
	private String mimetypeIn;

	@Column(name="MIMETYPE_OUT")
	private String mimetypeOut;

	@Lob
	@Column(name="MODEL_DATA_TO_INJECT")
	private String modelDataToInject;

	@Column(name="MODEL_ID")
	private String modelId;

	@Column(name="MODEL_NAME")
	private String modelName;

	@Column(name="MODEL_URI")
	private String modelUri;

	@Column(name="ORD_NUMBER")
	private BigDecimal ordNumber;

	@Column(name="PROV_FILE_ID")
	private String provFileId;

	@Column(name="SIZE_BYTES_IN")
	private BigDecimal sizeBytesIn;

	@Column(name="SIZE_BYTES_OUT")
	private BigDecimal sizeBytesOut;

	@Column(name="URI_FILE_IN")
	private String uriFileIn;

	@Column(name="URI_FILE_OUT")
	private String uriFileOut;

	//bi-directional many-to-one association to TDocToSignH
	@ManyToOne
	@JoinColumn(name="DOC_REC_ID")
	private TDocToSignH TDocToSignH;

	public TFileDocToSignH() {
	}

	public String getFileRecId() {
		return this.fileRecId;
	}

	public void setFileRecId(String fileRecId) {
		this.fileRecId = fileRecId;
	}

	public String getDigSignFmtIn() {
		return this.digSignFmtIn;
	}

	public void setDigSignFmtIn(String digSignFmtIn) {
		this.digSignFmtIn = digSignFmtIn;
	}

	public String getDigSignFmtOut() {
		return this.digSignFmtOut;
	}

	public void setDigSignFmtOut(String digSignFmtOut) {
		this.digSignFmtOut = digSignFmtOut;
	}

	public String getDigestAlghoritmIn() {
		return this.digestAlghoritmIn;
	}

	public void setDigestAlghoritmIn(String digestAlghoritmIn) {
		this.digestAlghoritmIn = digestAlghoritmIn;
	}

	public String getDigestAlghoritmOut() {
		return this.digestAlghoritmOut;
	}

	public void setDigestAlghoritmOut(String digestAlghoritmOut) {
		this.digestAlghoritmOut = digestAlghoritmOut;
	}

	public String getDigestEncodingIn() {
		return this.digestEncodingIn;
	}

	public void setDigestEncodingIn(String digestEncodingIn) {
		this.digestEncodingIn = digestEncodingIn;
	}

	public String getDigestEncodingOut() {
		return this.digestEncodingOut;
	}

	public void setDigestEncodingOut(String digestEncodingOut) {
		this.digestEncodingOut = digestEncodingOut;
	}

	public String getDigestIn() {
		return this.digestIn;
	}

	public void setDigestIn(String digestIn) {
		this.digestIn = digestIn;
	}

	public String getDigestOut() {
		return this.digestOut;
	}

	public void setDigestOut(String digestOut) {
		this.digestOut = digestOut;
	}

	public String getFileTyVsDoc() {
		return this.fileTyVsDoc;
	}

	public void setFileTyVsDoc(String fileTyVsDoc) {
		this.fileTyVsDoc = fileTyVsDoc;
	}

	public String getFilenameIn() {
		return this.filenameIn;
	}

	public void setFilenameIn(String filenameIn) {
		this.filenameIn = filenameIn;
	}

	public String getFilenameOut() {
		return this.filenameOut;
	}

	public void setFilenameOut(String filenameOut) {
		this.filenameOut = filenameOut;
	}

	public BigDecimal getFlgEditablePdfIn() {
		return this.flgEditablePdfIn;
	}

	public void setFlgEditablePdfIn(BigDecimal flgEditablePdfIn) {
		this.flgEditablePdfIn = flgEditablePdfIn;
	}

	public BigDecimal getFlgEditablePdfOut() {
		return this.flgEditablePdfOut;
	}

	public void setFlgEditablePdfOut(BigDecimal flgEditablePdfOut) {
		this.flgEditablePdfOut = flgEditablePdfOut;
	}

	public BigDecimal getFlgFileSignedIn() {
		return this.flgFileSignedIn;
	}

	public void setFlgFileSignedIn(BigDecimal flgFileSignedIn) {
		this.flgFileSignedIn = flgFileSignedIn;
	}

	public BigDecimal getFlgFileSignedOut() {
		return this.flgFileSignedOut;
	}

	public void setFlgFileSignedOut(BigDecimal flgFileSignedOut) {
		this.flgFileSignedOut = flgFileSignedOut;
	}

	public String getFlgFmtConvOut() {
		return this.flgFmtConvOut;
	}

	public void setFlgFmtConvOut(String flgFmtConvOut) {
		this.flgFmtConvOut = flgFmtConvOut;
	}

	public BigDecimal getFlgGraphicSignOut() {
		return this.flgGraphicSignOut;
	}

	public void setFlgGraphicSignOut(BigDecimal flgGraphicSignOut) {
		this.flgGraphicSignOut = flgGraphicSignOut;
	}

	public BigDecimal getFlgNestedSignOut() {
		return this.flgNestedSignOut;
	}

	public void setFlgNestedSignOut(BigDecimal flgNestedSignOut) {
		this.flgNestedSignOut = flgNestedSignOut;
	}

	public BigDecimal getFlgToGenFromModel() {
		return this.flgToGenFromModel;
	}

	public void setFlgToGenFromModel(BigDecimal flgToGenFromModel) {
		this.flgToGenFromModel = flgToGenFromModel;
	}

	public BigDecimal getFlgToMark() {
		return this.flgToMark;
	}

	public void setFlgToMark(BigDecimal flgToMark) {
		this.flgToMark = flgToMark;
	}

	public BigDecimal getFlgToSign() {
		return this.flgToSign;
	}

	public void setFlgToSign(BigDecimal flgToSign) {
		this.flgToSign = flgToSign;
	}

	public BigDecimal getFlgValidSignIn() {
		return this.flgValidSignIn;
	}

	public void setFlgValidSignIn(BigDecimal flgValidSignIn) {
		this.flgValidSignIn = flgValidSignIn;
	}

	public BigDecimal getFlgValidTsIn() {
		return this.flgValidTsIn;
	}

	public void setFlgValidTsIn(BigDecimal flgValidTsIn) {
		this.flgValidTsIn = flgValidTsIn;
	}

	public BigDecimal getFlgWithCommentsPdfIn() {
		return this.flgWithCommentsPdfIn;
	}

	public void setFlgWithCommentsPdfIn(BigDecimal flgWithCommentsPdfIn) {
		this.flgWithCommentsPdfIn = flgWithCommentsPdfIn;
	}

	public BigDecimal getFlgWithCommentsPdfOut() {
		return this.flgWithCommentsPdfOut;
	}

	public void setFlgWithCommentsPdfOut(BigDecimal flgWithCommentsPdfOut) {
		this.flgWithCommentsPdfOut = flgWithCommentsPdfOut;
	}

	public String getGraphicSignInfo() {
		return this.graphicSignInfo;
	}

	public void setGraphicSignInfo(String graphicSignInfo) {
		this.graphicSignInfo = graphicSignInfo;
	}

	public Timestamp getInsTime() {
		return this.insTime;
	}

	public void setInsTime(Timestamp insTime) {
		this.insTime = insTime;
	}

	public Timestamp getLastUpdTime() {
		return this.lastUpdTime;
	}

	public void setLastUpdTime(Timestamp lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}

	public String getMarkInfo() {
		return this.markInfo;
	}

	public void setMarkInfo(String markInfo) {
		this.markInfo = markInfo;
	}

	public String getMimetypeIn() {
		return this.mimetypeIn;
	}

	public void setMimetypeIn(String mimetypeIn) {
		this.mimetypeIn = mimetypeIn;
	}

	public String getMimetypeOut() {
		return this.mimetypeOut;
	}

	public void setMimetypeOut(String mimetypeOut) {
		this.mimetypeOut = mimetypeOut;
	}

	public String getModelDataToInject() {
		return this.modelDataToInject;
	}

	public void setModelDataToInject(String modelDataToInject) {
		this.modelDataToInject = modelDataToInject;
	}

	public String getModelId() {
		return this.modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelUri() {
		return this.modelUri;
	}

	public void setModelUri(String modelUri) {
		this.modelUri = modelUri;
	}

	public BigDecimal getOrdNumber() {
		return this.ordNumber;
	}

	public void setOrdNumber(BigDecimal ordNumber) {
		this.ordNumber = ordNumber;
	}

	public String getProvFileId() {
		return this.provFileId;
	}

	public void setProvFileId(String provFileId) {
		this.provFileId = provFileId;
	}

	public BigDecimal getSizeBytesIn() {
		return this.sizeBytesIn;
	}

	public void setSizeBytesIn(BigDecimal sizeBytesIn) {
		this.sizeBytesIn = sizeBytesIn;
	}

	public BigDecimal getSizeBytesOut() {
		return this.sizeBytesOut;
	}

	public void setSizeBytesOut(BigDecimal sizeBytesOut) {
		this.sizeBytesOut = sizeBytesOut;
	}

	public String getUriFileIn() {
		return this.uriFileIn;
	}

	public void setUriFileIn(String uriFileIn) {
		this.uriFileIn = uriFileIn;
	}

	public String getUriFileOut() {
		return this.uriFileOut;
	}

	public void setUriFileOut(String uriFileOut) {
		this.uriFileOut = uriFileOut;
	}

	public TDocToSignH getTDocToSignH() {
		return this.TDocToSignH;
	}

	public void setTDocToSignH(TDocToSignH TDocToSignH) {
		this.TDocToSignH = TDocToSignH;
	}

}
