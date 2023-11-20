/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

@XmlRootElement
public class XmlInfoStrutturaTabOutBean {

	@NumeroColonna(numero = "1")
	private String intestazioneColonna;
	
	@NumeroColonna(numero = "2")
	private String tipoColonna;
	
	@NumeroColonna(numero = "3")
	private String larghezzaColonna;
	
	@NumeroColonna(numero = "4")
	private String nroOrdinamentoColonna;
	
	@NumeroColonna(numero = "5")
	private String versoOrdinamentoColonna;
	
	@NumeroColonna(numero = "7")
	private String flgColonnaNascosta;

	public String getIntestazioneColonna() {
		return intestazioneColonna;
	}

	public void setIntestazioneColonna(String intestazioneColonna) {
		this.intestazioneColonna = intestazioneColonna;
	}

	public String getTipoColonna() {
		return tipoColonna;
	}

	public void setTipoColonna(String tipoColonna) {
		this.tipoColonna = tipoColonna;
	}

	public String getLarghezzaColonna() {
		return larghezzaColonna;
	}

	public void setLarghezzaColonna(String larghezzaColonna) {
		this.larghezzaColonna = larghezzaColonna;
	}

	public String getNroOrdinamentoColonna() {
		return nroOrdinamentoColonna;
	}

	public void setNroOrdinamentoColonna(String nroOrdinamentoColonna) {
		this.nroOrdinamentoColonna = nroOrdinamentoColonna;
	}

	public String getVersoOrdinamentoColonna() {
		return versoOrdinamentoColonna;
	}

	public void setVersoOrdinamentoColonna(String versoOrdinamentoColonna) {
		this.versoOrdinamentoColonna = versoOrdinamentoColonna;
	}

	public String getFlgColonnaNascosta() {
		return flgColonnaNascosta;
	}

	public void setFlgColonnaNascosta(String flgColonnaNascosta) {
		this.flgColonnaNascosta = flgColonnaNascosta;
	}

	
}
