/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * 
 * @author cristiano
 *
 */


public class DatiOperazioneSuRichAccessoAttiXmlBean {

	@XmlVariabile(nome = "#DataAppuntamento", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataAppuntamento;
	
	@XmlVariabile(nome = "#OrarioAppuntamento", tipo = TipoVariabile.SEMPLICE)
	private String orarioAppuntamento;
	
	@XmlVariabile(nome = "#DataPrelievo", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataPrelievo;
	
	@XmlVariabile(nome = "#DataRestituzionePrelievo", tipo = TipoVariabile.SEMPLICE)
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataRestituzionePrelievo;

	public Date getDataAppuntamento() {
		return dataAppuntamento;
	}

	public void setDataAppuntamento(Date dataAppuntamento) {
		this.dataAppuntamento = dataAppuntamento;
	}

	public String getOrarioAppuntamento() {
		return orarioAppuntamento;
	}

	public void setOrarioAppuntamento(String orarioAppuntamento) {
		this.orarioAppuntamento = orarioAppuntamento;
	}

	public Date getDataPrelievo() {
		return dataPrelievo;
	}

	public void setDataPrelievo(Date dataPrelievo) {
		this.dataPrelievo = dataPrelievo;
	}

	public Date getDataRestituzionePrelievo() {
		return dataRestituzionePrelievo;
	}

	public void setDataRestituzionePrelievo(Date dataRestituzionePrelievo) {
		this.dataRestituzionePrelievo = dataRestituzionePrelievo;
	}
		
}
