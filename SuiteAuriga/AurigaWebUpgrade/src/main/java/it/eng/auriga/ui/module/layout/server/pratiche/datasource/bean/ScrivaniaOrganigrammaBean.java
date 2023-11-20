/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

public class ScrivaniaOrganigrammaBean extends VisualBean {

	// 1) Id Scrivania
	@NumeroColonna(numero = "1")	
	private String idSv;

	// 2) Id. UO
	@NumeroColonna(numero = "2")	
	private String idUo;

	// 3) Id. utente
	@NumeroColonna(numero = "3")	
	private String idUt;

	// 4) Codice/livelli UO
	@NumeroColonna(numero = "4")	
	private String codUo;

	// 5) Descrizione (quella della UO di appartenenza + quella della scrivania)
	@NumeroColonna(numero = "5")	
	private String descrizione;

	// 6) Descrizione estesa (quella della UO di appartenenza, con UO superiori, + quella della scrivania)
	@NumeroColonna(numero = "6")	
	private String descrizioneEstesa;
	
	@NumeroColonna(numero = "8")	
	private String codiceFiscale;

	public String getIdSv() {
		return idSv;
	}

	public void setIdSv(String idSv) {
		this.idSv = idSv;
	}

	public String getIdUo() {
		return idUo;
	}

	public void setIdUo(String idUo) {
		this.idUo = idUo;
	}

	public String getIdUt() {
		return idUt;
	}

	public void setIdUt(String idUt) {
		this.idUt = idUt;
	}

	public String getCodUo() {
		return codUo;
	}

	public void setCodUo(String codUo) {
		this.codUo = codUo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneEstesa() {
		return descrizioneEstesa;
	}

	public void setDescrizioneEstesa(String descrizioneEstesa) {
		this.descrizioneEstesa = descrizioneEstesa;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
}
