/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.module.business.dao.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;
import it.eng.utility.ui.servlet.bean.MimeTypeFirmaBean;

/**
 * Bean for capturing grid preference
 * 
 * @author matzanin
 *
 */

@XmlRootElement
public class FileDocToSignBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -1263155515865872219L;
	
	// Attributi derivati dalla entity
	private String fileRecId;
	private String digSignFmtIn;
	private String digSignFmtOut;
	private String digestAlghoritmIn;
	private String digestAlghoritmOut;
	private String digestEncodingIn;
	private String digestEncodingOut;
	private String digestIn;
	private String digestOut;
	private String fileTyVsDoc;
	private String filenameIn;
	private String filenameOut;
	private BigDecimal flgEditablePdfIn;
	private BigDecimal flgEditablePdfOut;
	private BigDecimal flgFileSignedIn;
	private BigDecimal flgFileSignedOut;
	private String flgFmtConvOut;
	private BigDecimal flgGraphicSignOut;
	private BigDecimal flgNestedSignOut;
	private BigDecimal flgToGenFromModel;
	private BigDecimal flgToMark;
	private BigDecimal flgToSign;
	private BigDecimal flgValidSignIn;
	private BigDecimal flgValidTsIn;
	private BigDecimal flgWithCommentsPdfIn;
	private BigDecimal flgWithCommentsPdfOut;
	private String graphicSignInfo;
	private Date insTime;
	private Date lastUpdTime;
	private String markInfo;
	private String mimetypeIn;
	private String mimetypeOut;
	private String modelDataToInject;
	private String modelId;
	private String modelName;
	private String modelUri;
	private BigDecimal ordNumber;
	private String provFileId;
	private BigDecimal sizeBytesIn;
	private BigDecimal sizeBytesOut;
	private String uriFileIn;
	private String uriFileOut;
	private DocToSignBean docToSign;
	
	// Variabili di appoggio per gestione modelli
	String tipoModello;
	MimeTypeFirmaBean infoFileModello;
	
	public String getFileRecId() {
		return fileRecId;
	}
	public void setFileRecId(String fileRecId) {
		this.fileRecId = fileRecId;
		this.getUpdatedProperties().add("fileRecId");
	}
	public String getDigSignFmtIn() {
		return digSignFmtIn;
	}
	public void setDigSignFmtIn(String digSignFmtIn) {
		this.digSignFmtIn = digSignFmtIn;
		this.getUpdatedProperties().add("digSignFmtIn");
	}
	public String getDigSignFmtOut() {
		return digSignFmtOut;
	}
	public void setDigSignFmtOut(String digSignFmtOut) {
		this.digSignFmtOut = digSignFmtOut;
		this.getUpdatedProperties().add("digSignFmtOut");
	}
	public String getDigestAlghoritmIn() {
		return digestAlghoritmIn;
	}
	public void setDigestAlghoritmIn(String digestAlghoritmIn) {
		this.digestAlghoritmIn = digestAlghoritmIn;
		this.getUpdatedProperties().add("digestAlghoritmIn");
	}
	public String getDigestAlghoritmOut() {
		return digestAlghoritmOut;
	}
	public void setDigestAlghoritmOut(String digestAlghoritmOut) {
		this.digestAlghoritmOut = digestAlghoritmOut;
		this.getUpdatedProperties().add("digestAlghoritmOut");
	}
	public String getDigestEncodingIn() {
		return digestEncodingIn;
	}
	public void setDigestEncodingIn(String digestEncodingIn) {
		this.digestEncodingIn = digestEncodingIn;
		this.getUpdatedProperties().add("digestEncodingIn");
	}
	public String getDigestEncodingOut() {
		return digestEncodingOut;
	}
	public void setDigestEncodingOut(String digestEncodingOut) {
		this.digestEncodingOut = digestEncodingOut;
		this.getUpdatedProperties().add("digestEncodingOut");
	}
	public String getDigestIn() {
		return digestIn;
	}
	public void setDigestIn(String digestIn) {
		this.digestIn = digestIn;
		this.getUpdatedProperties().add("digestIn");
	}
	public String getDigestOut() {
		return digestOut;
	}
	public void setDigestOut(String digestOut) {
		this.digestOut = digestOut;
		this.getUpdatedProperties().add("digestOut");
	}
	public String getFileTyVsDoc() {
		return fileTyVsDoc;
	}
	public void setFileTyVsDoc(String fileTyVsDoc) {
		this.fileTyVsDoc = fileTyVsDoc;
		this.getUpdatedProperties().add("fileTyVsDoc");
	}
	public String getFilenameIn() {
		return filenameIn;
	}
	public void setFilenameIn(String filenameIn) {
		this.filenameIn = filenameIn;
		this.getUpdatedProperties().add("filenameIn");
	}
	public String getFilenameOut() {
		return filenameOut;
	}
	public void setFilenameOut(String filenameOut) {
		this.filenameOut = filenameOut;
		this.getUpdatedProperties().add("filenameOut");
	}
	public BigDecimal getFlgEditablePdfIn() {
		return flgEditablePdfIn;
	}
	public void setFlgEditablePdfIn(BigDecimal flgEditablePdfIn) {
		this.flgEditablePdfIn = flgEditablePdfIn;
		this.getUpdatedProperties().add("flgEditablePdfIn");
	}
	public BigDecimal getFlgEditablePdfOut() {
		return flgEditablePdfOut;
	}
	public void setFlgEditablePdfOut(BigDecimal flgEditablePdfOut) {
		this.flgEditablePdfOut = flgEditablePdfOut;
		this.getUpdatedProperties().add("flgEditablePdfOut");
	}
	public BigDecimal getFlgFileSignedIn() {
		return flgFileSignedIn;
	}
	public void setFlgFileSignedIn(BigDecimal flgFileSignedIn) {
		this.flgFileSignedIn = flgFileSignedIn;
		this.getUpdatedProperties().add("flgFileSignedIn");
	}
	public BigDecimal getFlgFileSignedOut() {
		return flgFileSignedOut;
	}
	public void setFlgFileSignedOut(BigDecimal flgFileSignedOut) {
		this.flgFileSignedOut = flgFileSignedOut;
		this.getUpdatedProperties().add("flgFileSignedOut");
	}
	public String getFlgFmtConvOut() {
		return flgFmtConvOut;
	}
	public void setFlgFmtConvOut(String flgFmtConvOut) {
		this.flgFmtConvOut = flgFmtConvOut;
		this.getUpdatedProperties().add("flgFmtConvOut");
	}
	public BigDecimal getFlgGraphicSignOut() {
		return flgGraphicSignOut;
	}
	public void setFlgGraphicSignOut(BigDecimal flgGraphicSignOut) {
		this.flgGraphicSignOut = flgGraphicSignOut;
		this.getUpdatedProperties().add("flgGraphicSignOut");
	}
	public BigDecimal getFlgNestedSignOut() {
		return flgNestedSignOut;
	}
	public void setFlgNestedSignOut(BigDecimal flgNestedSignOut) {
		this.flgNestedSignOut = flgNestedSignOut;
		this.getUpdatedProperties().add("flgNestedSignOut");
	}
	public BigDecimal getFlgToGenFromModel() {
		return flgToGenFromModel;
	}
	public void setFlgToGenFromModel(BigDecimal flgToGenFromModel) {
		this.flgToGenFromModel = flgToGenFromModel;
		this.getUpdatedProperties().add("flgToGenFromModel");
	}
	public BigDecimal getFlgToMark() {
		return flgToMark;
	}
	public void setFlgToMark(BigDecimal flgToMark) {
		this.flgToMark = flgToMark;
		this.getUpdatedProperties().add("flgToMark");
	}
	public BigDecimal getFlgToSign() {
		return flgToSign;
	}
	public void setFlgToSign(BigDecimal flgToSign) {
		this.flgToSign = flgToSign;
		this.getUpdatedProperties().add("flgToSign");
	}
	public BigDecimal getFlgValidSignIn() {
		return flgValidSignIn;
	}
	public void setFlgValidSignIn(BigDecimal flgValidSignIn) {
		this.flgValidSignIn = flgValidSignIn;
		this.getUpdatedProperties().add("flgValidSignIn");
	}
	public BigDecimal getFlgValidTsIn() {
		return flgValidTsIn;
	}
	public void setFlgValidTsIn(BigDecimal flgValidTsIn) {
		this.flgValidTsIn = flgValidTsIn;
		this.getUpdatedProperties().add("flgValidTsIn");
	}
	public BigDecimal getFlgWithCommentsPdfIn() {
		return flgWithCommentsPdfIn;
	}
	public void setFlgWithCommentsPdfIn(BigDecimal flgWithCommentsPdfIn) {
		this.flgWithCommentsPdfIn = flgWithCommentsPdfIn;
		this.getUpdatedProperties().add("flgWithCommentsPdfIn");
	}
	public BigDecimal getFlgWithCommentsPdfOut() {
		return flgWithCommentsPdfOut;
	}
	public void setFlgWithCommentsPdfOut(BigDecimal flgWithCommentsPdfOut) {
		this.flgWithCommentsPdfOut = flgWithCommentsPdfOut;
		this.getUpdatedProperties().add("flgWithCommentsPdfOut");
	}
	public String getGraphicSignInfo() {
		return graphicSignInfo;
	}
	public void setGraphicSignInfo(String graphicSignInfo) {
		this.graphicSignInfo = graphicSignInfo;
		this.getUpdatedProperties().add("graphicSignInfo");
	}
	public Date getInsTime() {
		return insTime;
	}
	public void setInsTime(Date insTime) {
		this.insTime = insTime;
		this.getUpdatedProperties().add("insTime");
	}
	public Date getLastUpdTime() {
		return lastUpdTime;
	}
	public void setLastUpdTime(Date lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
		this.getUpdatedProperties().add("lastUpdTime");
	}
	public String getMarkInfo() {
		return markInfo;
	}
	public void setMarkInfo(String markInfo) {
		this.markInfo = markInfo;
		this.getUpdatedProperties().add("markInfo");
	}
	public String getMimetypeIn() {
		return mimetypeIn;
	}
	public void setMimetypeIn(String mimetypeIn) {
		this.mimetypeIn = mimetypeIn;
		this.getUpdatedProperties().add("mimetypeIn");
	}
	public String getMimetypeOut() {
		return mimetypeOut;
	}
	public void setMimetypeOut(String mimetypeOut) {
		this.mimetypeOut = mimetypeOut;
		this.getUpdatedProperties().add("mimetypeOut");
	}
	public String getModelDataToInject() {
		return modelDataToInject;
	}
	public void setModelDataToInject(String modelDataToInject) {
		this.modelDataToInject = modelDataToInject;
		this.getUpdatedProperties().add("modelDataToInject");
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
		this.getUpdatedProperties().add("modelId");
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
		this.getUpdatedProperties().add("modelName");
	}
	public String getModelUri() {
		return modelUri;
	}
	public void setModelUri(String modelUri) {
		this.modelUri = modelUri;
		this.getUpdatedProperties().add("modelUri");
	}
	public BigDecimal getOrdNumber() {
		return ordNumber;
	}
	public void setOrdNumber(BigDecimal ordNumber) {
		this.ordNumber = ordNumber;
		this.getUpdatedProperties().add("ordNumber");
	}
	public String getProvFileId() {
		return provFileId;
	}
	public void setProvFileId(String provFileId) {
		this.provFileId = provFileId;
		this.getUpdatedProperties().add("provFileId");
	}
	public BigDecimal getSizeBytesIn() {
		return sizeBytesIn;
	}
	public void setSizeBytesIn(BigDecimal sizeBytesIn) {
		this.sizeBytesIn = sizeBytesIn;
		this.getUpdatedProperties().add("sizeBytesIn");
	}
	public BigDecimal getSizeBytesOut() {
		return sizeBytesOut;
	}
	public void setSizeBytesOut(BigDecimal sizeBytesOut) {
		this.sizeBytesOut = sizeBytesOut;
		this.getUpdatedProperties().add("sizeBytesOut");
	}
	public String getUriFileIn() {
		return uriFileIn;
	}
	public void setUriFileIn(String uriFileIn) {
		this.uriFileIn = uriFileIn;
		this.getUpdatedProperties().add("uriFileIn");
	}
	public String getUriFileOut() {
		return uriFileOut;
	}
	public void setUriFileOut(String uriFileOut) {
		this.uriFileOut = uriFileOut;
		this.getUpdatedProperties().add("uriFileOut");
	}
	public DocToSignBean getDocToSign() {
		return docToSign;
	}
	public void setDocToSign(DocToSignBean docToSign) {
		this.docToSign = docToSign;
		this.getUpdatedProperties().add("docToSign");
	}
	public String getTipoModello() {
		return tipoModello;
	}
	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
		this.getUpdatedProperties().add("tipoModello");
	}
	public MimeTypeFirmaBean getInfoFileModello() {
		return infoFileModello;
	}
	public void setInfoFileModello(MimeTypeFirmaBean infoFileModello) {
		this.infoFileModello = infoFileModello;
		this.getUpdatedProperties().add("infoFileModello");
	}

}
