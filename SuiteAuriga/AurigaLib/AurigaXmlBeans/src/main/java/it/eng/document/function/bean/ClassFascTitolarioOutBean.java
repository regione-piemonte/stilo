/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClassFascTitolarioOutBean extends ClassificheFascicoliDocumentoBean{

	@NumeroColonna(numero = "2")
	private String indice;
	
	@NumeroColonna(numero = "3")
	private String descrizione;
		
	@NumeroColonna(numero = "13")
	private String nomeFascSottoFasc;
	
	@NumeroColonna(numero = "14")
	private String flgCapofila;
	
	@NumeroColonna(numero = "15")
	private String estremiDocCapofila;	
	
	@NumeroColonna(numero = "16")
	private String nroSecondario;

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

	public String getNomeFascSottoFasc() {
		return nomeFascSottoFasc;
	}

	public void setNomeFascSottoFasc(String nomeFascSottoFasc) {
		this.nomeFascSottoFasc = nomeFascSottoFasc;
	}

	public String getFlgCapofila() {
		return flgCapofila;
	}

	public void setFlgCapofila(String flgCapofila) {
		this.flgCapofila = flgCapofila;
	}

	public String getEstremiDocCapofila() {
		return estremiDocCapofila;
	}

	public void setEstremiDocCapofila(String estremiDocCapofila) {
		this.estremiDocCapofila = estremiDocCapofila;
	}

	public String getNroSecondario() {
		return nroSecondario;
	}

	public void setNroSecondario(String nroSecondario) {
		this.nroSecondario = nroSecondario;
	}
	
}
