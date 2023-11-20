/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProtocolloRicevuto implements Serializable{

	@XmlVariabile(nome="#RifDocRicevuto", tipo=TipoVariabile.SEMPLICE)
	private String origine;
	@XmlVariabile(nome="#EstremiRegDocRicevuto", tipo=TipoVariabile.SEMPLICE)
	private String numero;
	@XmlVariabile(nome="#AnnoDocRicevuto", tipo=TipoVariabile.SEMPLICE)
	private String anno;
	@TipoData(tipo=Tipo.DATA_SENZA_ORA)
	@XmlVariabile(nome="#DtDocRicevuto", tipo=TipoVariabile.SEMPLICE)
	private Date data;
	public String getOrigine() {
		return origine;
	}
	public void setOrigine(String origine) {
		this.origine = origine;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}	
	
}
