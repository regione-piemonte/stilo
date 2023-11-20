/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class TipologiaOffertaPuntoVenditaXmlBean {

	
	@NumeroColonna(numero="1")
	private String codiceOfferta;

	@NumeroColonna(numero="2")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioValiditaOfferta;

	@NumeroColonna(numero="3")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFineValiditaOfferta;

	
	public String getCodiceOfferta() {
		return codiceOfferta;
	}

	public void setCodiceOfferta(String codiceOfferta) {
		this.codiceOfferta = codiceOfferta;
	}

	public Date getDataInizioValiditaOfferta() {
		return dataInizioValiditaOfferta;
	}

	public void setDataInizioValiditaOfferta(Date dataInizioValiditaOfferta) {
		this.dataInizioValiditaOfferta = dataInizioValiditaOfferta;
	}

	public Date getDataFineValiditaOfferta() {
		return dataFineValiditaOfferta;
	}

	public void setDataFineValiditaOfferta(Date dataFineValiditaOfferta) {
		this.dataFineValiditaOfferta = dataFineValiditaOfferta;
	}

	

	

}
