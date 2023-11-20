/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.utility.ui.module.core.shared.bean.VisualBean;


public class TipologiaPromozionePuntoVenditaBean extends VisualBean{

	@NumeroColonna(numero="1")
	private String codicePromozione;
		
	@NumeroColonna(numero="2")
	private String nomePromozione;
	
	
	@NumeroColonna(numero="3")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioValiditaPromozione;

	@NumeroColonna(numero="4")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFineValiditaPromozione;

	public String getCodicePromozione() {
		return codicePromozione;
	}

	public void setCodicePromozione(String codicePromozione) {
		this.codicePromozione = codicePromozione;
	}

	public String getNomePromozione() {
		return nomePromozione;
	}

	public void setNomePromozione(String nomePromozione) {
		this.nomePromozione = nomePromozione;
	}

	public Date getDataInizioValiditaPromozione() {
		return dataInizioValiditaPromozione;
	}

	public void setDataInizioValiditaPromozione(Date dataInizioValiditaPromozione) {
		this.dataInizioValiditaPromozione = dataInizioValiditaPromozione;
	}

	public Date getDataFineValiditaPromozione() {
		return dataFineValiditaPromozione;
	}

	public void setDataFineValiditaPromozione(Date dataFineValiditaPromozione) {
		this.dataFineValiditaPromozione = dataFineValiditaPromozione;
	}
	
}
