/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class GruppoSoggettiBean extends VisualBean{

	private String idGruppo;	
	
	@NumeroColonna(numero = "1")
	private String idSoggettiNonInterni; // id in rubrica usato per il salvataggio del destinatario
	@NumeroColonna(numero = "2")
	private String codiceRapidoGruppo;
	@NumeroColonna(numero = "3")
	private String nomeGruppo;
	@NumeroColonna(numero = "4")
	private String flagSoggettiGruppo;
	@NumeroColonna(numero = "5")
	private String idSoggettiInterni; // id interno usato per l'assegnazione (questo ce l'hanno solo i sogg. interni)
	@NumeroColonna(numero = "8")
	private String flgSelXAssegnazione;
	
	public String getIdGruppo() {
		return idGruppo;
	}
	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}
	public String getIdSoggettiNonInterni() {
		return idSoggettiNonInterni;
	}
	public void setIdSoggettiNonInterni(String idSoggettiNonInterni) {
		this.idSoggettiNonInterni = idSoggettiNonInterni;
	}
	public String getCodiceRapidoGruppo() {
		return codiceRapidoGruppo;
	}
	public void setCodiceRapidoGruppo(String codiceRapidoGruppo) {
		this.codiceRapidoGruppo = codiceRapidoGruppo;
	}
	public String getNomeGruppo() {
		return nomeGruppo;
	}
	public void setNomeGruppo(String nomeGruppo) {
		this.nomeGruppo = nomeGruppo;
	}
	public String getFlagSoggettiGruppo() {
		return flagSoggettiGruppo;
	}
	public void setFlagSoggettiGruppo(String flagSoggettiGruppo) {
		this.flagSoggettiGruppo = flagSoggettiGruppo;
	}
	public String getIdSoggettiInterni() {
		return idSoggettiInterni;
	}
	public void setIdSoggettiInterni(String idSoggettiInterni) {
		this.idSoggettiInterni = idSoggettiInterni;
	}
	public String getFlgSelXAssegnazione() {
		return flgSelXAssegnazione;
	}
	public void setFlgSelXAssegnazione(String flgSelXAssegnazione) {
		this.flgSelXAssegnazione = flgSelXAssegnazione;
	}

}
