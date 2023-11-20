/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.io.Serializable;

public class ReportCogitoLogFiltriXmlBean implements Serializable {
	
	@XmlVariabile(nome="DataDa", tipo=TipoVariabile.SEMPLICE)
	private String dataDa;
	
	@XmlVariabile(nome="DataA", tipo=TipoVariabile.SEMPLICE)
	private String dataA;
		
	@XmlVariabile(nome="IdUO", tipo=TipoVariabile.SEMPLICE)
	private String idUO;
	
	@XmlVariabile(nome="FlgIncluseSottoUO", tipo=TipoVariabile.SEMPLICE)
	private String flgIncluseSottoUO;
	
	@XmlVariabile(nome="IdUser", tipo=TipoVariabile.SEMPLICE)
	private String idUtente;

	@XmlVariabile(nome="IdClassificazioneSuggerita", tipo=TipoVariabile.SEMPLICE)
	private String idClassificazioneSuggerita;

	@XmlVariabile(nome="IdClassificazioneScelta", tipo=TipoVariabile.SEMPLICE)
	private String idClassificazioneScelta;

	@XmlVariabile(nome="IdEsito", tipo=TipoVariabile.SEMPLICE)
	private String idEsito;

	@XmlVariabile(nome="Errore", tipo=TipoVariabile.SEMPLICE)
	private String errore;

	public String getDataDa() {
		return dataDa;
	}

	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}

	public String getDataA() {
		return dataA;
	}

	public void setDataA(String dataA) {
		this.dataA = dataA;
	}

	public String getIdUO() {
		return idUO;
	}

	public void setIdUO(String idUO) {
		this.idUO = idUO;
	}

	public String getFlgIncluseSottoUO() {
		return flgIncluseSottoUO;
	}

	public void setFlgIncluseSottoUO(String flgIncluseSottoUO) {
		this.flgIncluseSottoUO = flgIncluseSottoUO;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getIdClassificazioneSuggerita() {
		return idClassificazioneSuggerita;
	}

	public void setIdClassificazioneSuggerita(String idClassificazioneSuggerita) {
		this.idClassificazioneSuggerita = idClassificazioneSuggerita;
	}

	public String getIdClassificazioneScelta() {
		return idClassificazioneScelta;
	}

	public void setIdClassificazioneScelta(String idClassificazioneScelta) {
		this.idClassificazioneScelta = idClassificazioneScelta;
	}

	public String getIdEsito() {
		return idEsito;
	}

	public void setIdEsito(String idEsito) {
		this.idEsito = idEsito;
	}

	public String getErrore() {
		return errore;
	}

	public void setErrore(String errore) {
		this.errore = errore;
	}

	

}