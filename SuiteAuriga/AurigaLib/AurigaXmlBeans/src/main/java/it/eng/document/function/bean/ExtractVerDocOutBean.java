/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ExtractVerDocOutBean implements Serializable{

	private String uriOut;
	private BigDecimal nroProgrVerOut;	
	private String displayFilenameVerOut;
	private String improntaVerOut;
	private String algoritmoImprontaVerOut;
	private String encodingImprontaVerOut;
	private BigDecimal dimensioneVerOut;
	private String mimetypeVerOut;
	private Integer flgFirmataVerOut;
	
	private Integer errorCode;
	private String errorContext;
	private String defaultMessage;
	
	
	public String getUriOut() {
		return uriOut;
	}
	public BigDecimal getNroProgrVerOut() {
		return nroProgrVerOut;
	}
	public String getDisplayFilenameVerOut() {
		return displayFilenameVerOut;
	}
	public String getImprontaVerOut() {
		return improntaVerOut;
	}
	public String getAlgoritmoImprontaVerOut() {
		return algoritmoImprontaVerOut;
	}
	public String getEncodingImprontaVerOut() {
		return encodingImprontaVerOut;
	}
	public BigDecimal getDimensioneVerOut() {
		return dimensioneVerOut;
	}
	public String getMimetypeVerOut() {
		return mimetypeVerOut;
	}
	public Integer getFlgFirmataVerOut() {
		return flgFirmataVerOut;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public String getErrorContext() {
		return errorContext;
	}
	public String getDefaultMessage() {
		return defaultMessage;
	}
	public void setUriOut(String uriOut) {
		this.uriOut = uriOut;
	}
	public void setNroProgrVerOut(BigDecimal nroProgrVerOut) {
		this.nroProgrVerOut = nroProgrVerOut;
	}
	public void setDisplayFilenameVerOut(String displayFilenameVerOut) {
		this.displayFilenameVerOut = displayFilenameVerOut;
	}
	public void setImprontaVerOut(String improntaVerOut) {
		this.improntaVerOut = improntaVerOut;
	}
	public void setAlgoritmoImprontaVerOut(String algoritmoImprontaVerOut) {
		this.algoritmoImprontaVerOut = algoritmoImprontaVerOut;
	}
	public void setEncodingImprontaVerOut(String encodingImprontaVerOut) {
		this.encodingImprontaVerOut = encodingImprontaVerOut;
	}
	public void setDimensioneVerOut(BigDecimal dimensioneVerOut) {
		this.dimensioneVerOut = dimensioneVerOut;
	}
	public void setMimetypeVerOut(String mimetypeVerOut) {
		this.mimetypeVerOut = mimetypeVerOut;
	}
	public void setFlgFirmataVerOut(Integer flgFirmataVerOut) {
		this.flgFirmataVerOut = flgFirmataVerOut;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public void setErrorContext(String errorContext) {
		this.errorContext = errorContext;
	}
	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	
	
	
	
	
	
}
