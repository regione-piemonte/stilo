/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegEmergenzaOutBean implements Serializable{

	@XmlVariabile(nome="Registro", tipo=TipoVariabile.SEMPLICE)
	private String registro;
	@XmlVariabile(nome="Anno", tipo=TipoVariabile.SEMPLICE)
	private String anno;
	@XmlVariabile(nome="Nro", tipo=TipoVariabile.SEMPLICE)
	private String nro;
	@XmlVariabile(nome="TsRegistrazione", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA)
	private Date tsRegistrazione;
	@XmlVariabile(nome="IdUserReg", tipo=TipoVariabile.SEMPLICE)
	private String idUserReg;
	@XmlVariabile(nome="IdUOReg", tipo=TipoVariabile.SEMPLICE)
	private String idUoReg;
	
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
	public Date getTsRegistrazione() {
		return tsRegistrazione;
	}
	public void setTsRegistrazione(Date tsRegistrazione) {
		this.tsRegistrazione = tsRegistrazione;
	}
	public String getIdUserReg() {
		return idUserReg;
	}
	public void setIdUserReg(String idUserReg) {
		this.idUserReg = idUserReg;
	}
	public String getIdUoReg() {
		return idUoReg;
	}
	public void setIdUoReg(String idUoReg) {
		this.idUoReg = idUoReg;
	}
}
