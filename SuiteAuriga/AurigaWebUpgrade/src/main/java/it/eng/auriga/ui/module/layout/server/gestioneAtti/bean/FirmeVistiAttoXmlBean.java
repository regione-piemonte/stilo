/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

/**
 * 
 * @author dbe4235
 *
 */

public class FirmeVistiAttoXmlBean {
	
	
	private String idUd;
	
	/**
	 * Data e ora di firma (nel formato FMT_STD_TIMESTAMP)
	 */
	@NumeroColonna(numero="1")
	@TipoData(tipo = Tipo.DATA)
	private Date dataFirma;

	/**
	 *  Tipo di firma. D = Digitale, E = Elettronica (ovvero visto o firma leggera)
	 */
	@NumeroColonna(numero="2")
	private String tipoFirma;

	/**
	 * Nominativo firmatario
	 */
	@NumeroColonna(numero="3")
	private String firmatario;

	/**
	 * Ruolo con cui ha firmato/vistato
	 */
	@NumeroColonna(numero="4")
	private String ruolo;
	
	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public Date getDataFirma() {
		return dataFirma;
	}

	public void setDataFirma(Date dataFirma) {
		this.dataFirma = dataFirma;
	}

	public String getTipoFirma() {
		return tipoFirma;
	}

	public void setTipoFirma(String tipoFirma) {
		this.tipoFirma = tipoFirma;
	}

	public String getFirmatario() {
		return firmatario;
	}

	public void setFirmatario(String firmatario) {
		this.firmatario = firmatario;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

}