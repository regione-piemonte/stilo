/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistroEmergenza implements Serializable{

	@NumeroColonna(numero = "1")
	private String fisso = "E";
	
	@NumeroColonna(numero = "2")
	private String registro;
	
	@NumeroColonna(numero = "3")
	private String anno;
	
	@NumeroColonna(numero = "4")
	private String nro;
	
	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA)
	private Date dataRegistrazione;
	
	@NumeroColonna(numero = "6")
	private String idUserReg;
	
	@NumeroColonna(numero = "9")
	private String idUoReg;
	
	@NumeroColonna(numero = "14")
	private String flgAnnullamento;
	
	@NumeroColonna(numero = "18")
	private String motiviAnnullamento;
	
	public String getFisso() {
		return fisso;
	}
	public void setFisso(String fisso) {
		this.fisso = fisso;
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
	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}
	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
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
	public String getFlgAnnullamento() {
		return flgAnnullamento;
	}
	public void setFlgAnnullamento(String flgAnnullamento) {
		this.flgAnnullamento = flgAnnullamento;
	}
	public String getMotiviAnnullamento() {
		return motiviAnnullamento;
	}
	public void setMotiviAnnullamento(String motiviAnnullamento) {
		this.motiviAnnullamento = motiviAnnullamento;
	}
	
}