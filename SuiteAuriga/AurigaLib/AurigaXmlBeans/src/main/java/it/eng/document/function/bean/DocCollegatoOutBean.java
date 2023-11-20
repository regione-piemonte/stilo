/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

@XmlRootElement
public class DocCollegatoOutBean implements Serializable{

	@XmlVariabile(nome="IdUD", tipo=TipoVariabile.SEMPLICE)
	private String idUd;
	
	@XmlVariabile(nome="Tipo", tipo=TipoVariabile.SEMPLICE)
	private TipoProtocollo tipo;
	
	@XmlVariabile(nome="Registro", tipo=TipoVariabile.SEMPLICE)
	private String registro;
	
	@XmlVariabile(nome="Anno", tipo=TipoVariabile.SEMPLICE)
	private String anno;
	
	@XmlVariabile(nome="Nro", tipo=TipoVariabile.SEMPLICE)
	private String nro;
	
	public String getIdUd() {
		return idUd;
	}

	public void setIdUd(String idUd) {
		this.idUd = idUd;
	}

	public TipoProtocollo getTipo() {
		return tipo;
	}
	
	public void setTipo(TipoProtocollo tipo) {
		this.tipo = tipo;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public String getNro() {
		return nro;
	}
	public void setNro(String nro) {
		this.nro = nro;
	}
}
