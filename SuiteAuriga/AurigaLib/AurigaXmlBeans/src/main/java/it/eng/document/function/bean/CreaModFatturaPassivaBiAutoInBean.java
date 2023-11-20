/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModFatturaPassivaBiAutoInBean extends CreaModFatturaInBean {

	private static final long serialVersionUID = -6609881844566442763L;

	@XmlVariabile(nome = "FATT_FLG_INS_DA_AURGUI_NO_XML_Doc", tipo = TipoVariabile.SEMPLICE)
	private String flgInsManualeNoXml;


	@XmlVariabile(nome = "ID_ASSEGNATARIO_DOC_Ud", tipo = TipoVariabile.SEMPLICE)
	private String idAssegnatario;

	@XmlVariabile(nome = "DESC_ASSEGNATARIO_DOC_Ud", tipo = TipoVariabile.SEMPLICE)
	private String descAssegnatario;
	
	public String getFlgInsManualeNoXml() {
		return flgInsManualeNoXml;
	}

	public void setFlgInsManualeNoXml(String flgInsManualeNoXml) {
		this.flgInsManualeNoXml = flgInsManualeNoXml;
	}

	public String getIdAssegnatario() {
		return idAssegnatario;
	}

	public void setIdAssegnatario(String idAssegnatario) {
		this.idAssegnatario = idAssegnatario;
	}

	public String getDescAssegnatario() {
		return descAssegnatario;
	}

	public void setDescAssegnatario(String descAssegnatario) {
		this.descAssegnatario = descAssegnatario;
	}
		
}
