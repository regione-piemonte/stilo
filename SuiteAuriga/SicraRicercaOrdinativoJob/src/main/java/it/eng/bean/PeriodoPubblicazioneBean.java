/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class PeriodoPubblicazioneBean {

	@NumeroColonna(numero = "1")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioPubblicazione;

	@NumeroColonna(numero = "2")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFinePubblicazione;

	public Date getDataInizioPubblicazione() {
		return dataInizioPubblicazione;
	}

	public void setDataInizioPubblicazione(Date dataInizioPubblicazione) {
		this.dataInizioPubblicazione = dataInizioPubblicazione;
	}

	public Date getDataFinePubblicazione() {
		return dataFinePubblicazione;
	}

	public void setDataFinePubblicazione(Date dataFinePubblicazione) {
		this.dataFinePubblicazione = dataFinePubblicazione;
	}
	
}
