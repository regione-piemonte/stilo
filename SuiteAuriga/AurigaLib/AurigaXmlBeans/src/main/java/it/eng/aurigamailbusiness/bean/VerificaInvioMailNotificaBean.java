/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

@XmlRootElement
public class VerificaInvioMailNotificaBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 4879304120688018172L;
	
	private TipoNotifica tipoNotifica;

	private String idSpAoo;

	private Map<String, String> restrizioni;

	private Map<String, String> valori;
	
	private List<File> attachments;

	public TipoNotifica getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(TipoNotifica tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
		this.getUpdatedProperties().add("tipoNotifica");
	}

	public String getIdSpAoo() {
		return idSpAoo;
	}

	public void setIdSpAoo(String idSpAoo) {
		this.idSpAoo = idSpAoo;
		this.getUpdatedProperties().add("idSpAoo");
	}

	public Map<String, String> getRestrizioni() {
		return restrizioni;
	}

	public void setRestrizioni(Map<String, String> restrizioni) {
		this.restrizioni = restrizioni;
		this.getUpdatedProperties().add("restrizioni");
	}

	public Map<String, String> getValori() {
		return valori;
	}

	public void setValori(Map<String, String> valori) {
		this.valori = valori;
		this.getUpdatedProperties().add("valori");
	}

	public List<File> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<File> attachments) {
		this.attachments = attachments;
		this.getUpdatedProperties().add("attachments");
	}

}
