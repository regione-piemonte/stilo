/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;

import java.util.Date;

public class ClassificaBean extends VisualBean{

	@NumeroColonna(numero="1")
	private String idClassifica;
	@NumeroColonna(numero="2")
	private String indice;
	@NumeroColonna(numero="3")
	private String descrizione;
	@NumeroColonna(numero="4")
	private String descrizioneEstesa;
	@NumeroColonna(numero="7")
	private String provCI;
	@NumeroColonna(numero="8")
	private String flgAttiva;
	
	private Date dataInizioValidita;
	private Date dataFineValidita;
	
	public String getIdClassifica() {
		return idClassifica;
	}
	public void setIdClassifica(String idClassifica) {
		this.idClassifica = idClassifica;
	}
	public String getIndice() {
		return indice;
	}
	public void setIndice(String indice) {
		this.indice = indice;
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
	public String getProvCI() {
		return provCI;
	}
	public void setProvCI(String provCI) {
		this.provCI = provCI;
	}
	public String getFlgAttiva() {
		return flgAttiva;
	}
	public void setFlgAttiva(String flgAttiva) {
		this.flgAttiva = flgAttiva;
	}
	public Date getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Date dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
}
