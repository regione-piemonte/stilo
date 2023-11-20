/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreaModFatturaScontrinoNespressoInBean extends CreaModFatturaInBean {

	private static final long serialVersionUID = 3874211477676922181L;

	
	@XmlVariabile(nome="INDIRIZZO_EMAIL_DEST_Ud", tipo = TipoVariabile.LISTA)
	private List<IndirizzoEmailDest> indirizziEmailDest;
	
	@XmlVariabile(nome = "DEN_DEST_SPEDIZ_Doc", tipo = TipoVariabile.SEMPLICE)
	private String denDestSpediz;


	@XmlVariabile(nome = "CANALE_PROVENIENZA_DOC_Doc", tipo = TipoVariabile.SEMPLICE)
	private String canaleProvenienzaDoc;
	
	
	public List<IndirizzoEmailDest> getIndirizziEmailDest() {
		return indirizziEmailDest;
	}

	public void setIndirizziEmailDest(List<IndirizzoEmailDest> indirizziEmailDest) {
		this.indirizziEmailDest = indirizziEmailDest;
	}

	public String getDenDestSpediz() {
		return denDestSpediz;
	}

	public void setDenDestSpediz(String denDestSpediz) {
		this.denDestSpediz = denDestSpediz;
	}

	public String getCanaleProvenienzaDoc() {
		return canaleProvenienzaDoc;
	}

	public void setCanaleProvenienzaDoc(String canaleProvenienzaDoc) {
		this.canaleProvenienzaDoc = canaleProvenienzaDoc;
	}
	
}
