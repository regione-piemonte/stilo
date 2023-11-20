/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FatturaAIRLIQDocumentoIn extends CreaModFatturaInBean {
	
	private static final long serialVersionUID = -4011655723485405230L;
	
	@XmlVariabile(nome="TIPO_INVIO_DEFAULT_Ud", tipo = TipoVariabile.SEMPLICE)
	private String tipoInvioDefault;

	@XmlVariabile(nome="INVIO_DA_APPLICARE_Ud", tipo = TipoVariabile.SEMPLICE)
	private String invioDaApplicare;
	
	@XmlVariabile(nome="INDIRIZZO_EMAIL_DEST_Ud", tipo = TipoVariabile.LISTA)
	private List<IndirizzoEmailDest> indirizziEmailDest;

	
	public String getTipoInvioDefault() {
		return tipoInvioDefault;
	}

	public void setTipoInvioDefault(String tipoInvioDefault) {
		this.tipoInvioDefault = tipoInvioDefault;
	}

	public String getInvioDaApplicare() {
		return invioDaApplicare;
	}

	public void setInvioDaApplicare(String invioDaApplicare) {
		this.invioDaApplicare = invioDaApplicare;
	}

	public List<IndirizzoEmailDest> getIndirizziEmailDest() {
		return indirizziEmailDest;
	}

	public void setIndirizziEmailDest(List<IndirizzoEmailDest> indirizziEmailDest) {
		this.indirizziEmailDest = indirizziEmailDest;
	}
}

