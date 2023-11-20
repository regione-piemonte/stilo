/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

/**
 * Classe per la mappatura del campo XMLRequestIn (DMPK_GAE.ArchiviaFileEventiRicevuto) 
 * 
 * @author denbraga
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ArchiviaFileEventiRicevutoRequest {
	
	@XmlVariabile(nome = "#CodSistemaMittente", tipo = TipoVariabile.SEMPLICE)
	private String codSistemaMittente;
	
	@XmlVariabile(nome = "#CodPbRicevente", tipo = TipoVariabile.SEMPLICE)
	private String codPbRicevente;
	
	@XmlElementWrapper(nillable = true)
	@XmlVariabile(nome = "#@CodTipiEventi", tipo = TipoVariabile.LISTA)
	private List<TipoEvento> codTipiEventi;
	
	public String getCodSistemaMittente() {
		return codSistemaMittente;
	}
	public void setCodSistemaMittente(String codSistemaMittente) {
		this.codSistemaMittente = codSistemaMittente;
	}
	
	public String getCodPbRicevente() {
		return codPbRicevente;
	}
	public void setCodPbRicevente(String codPbRicevente) {
		this.codPbRicevente = codPbRicevente;
	}
	
	public List<TipoEvento> getCodTipiEventi() {
		return codTipiEventi;
	}
	public void setCodTipiEventi(List<TipoEvento> codTipiEventi) {
		this.codTipiEventi = codTipiEventi;
	}
}
