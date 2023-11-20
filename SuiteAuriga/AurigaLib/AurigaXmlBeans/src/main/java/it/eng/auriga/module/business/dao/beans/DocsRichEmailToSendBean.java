/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Federico Cacco
 */

@XmlRootElement
public class DocsRichEmailToSendBean extends AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = -3324104384406716495L;

	private String idDocRichEmailToSend;

	// Appiattisco attributo TRichEmailToSend
	private String idRichiesta;

	private String idDoc;
	private String title;
	private Date dtDoc;
	private Date tsIns;
	private Date tsUltimoAgg;

	public String getIdDocRichEmailToSend() {
		return idDocRichEmailToSend;
	}

	public void setIdDocRichEmailToSend(String idDocRichEmailToSend) {
		this.idDocRichEmailToSend = idDocRichEmailToSend;
		this.getUpdatedProperties().add("idDocRichEmailToSend");
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
		this.getUpdatedProperties().add("idRichiesta");
	}

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
		this.getUpdatedProperties().add("idDoc");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.getUpdatedProperties().add("title");
	}

	public Date getDtDoc() {
		return dtDoc;
	}

	public void setDtDoc(Date dtDoc) {
		this.dtDoc = dtDoc;
		this.getUpdatedProperties().add("dtDoc");
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
		this.getUpdatedProperties().add("tsIns");
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
		this.getUpdatedProperties().add("tsUltimoAgg");
	}

}
