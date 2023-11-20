/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Attachment;
import it.eng.module.foutility.beans.generated.Response.FileoperationResponse.FileOperationResults;
import it.eng.module.foutility.beans.generated.Response.GenericError;

import java.io.File;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class FOResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2815497713572928537L;

	private FileOperationResults foresults;
	private GenericError genericError;
	
	@Attachment
	@XmlTransient
	private File responsefile;
	
	public FileOperationResults getForesults() {
		return foresults;
	}
	public void setForesults(FileOperationResults foresults) {
		this.foresults = foresults;
	}
	public File getResponsefile() {
		return responsefile;
	}
	public void setResponsefile(File responsefile) {
		this.responsefile = responsefile;
	}
	public GenericError getGenericError() {
		return genericError;
	}
	public void setGenericError(GenericError genericError) {
		this.genericError = genericError;
	}
	
}
