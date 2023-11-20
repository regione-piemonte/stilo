/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;
import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import it.eng.document.function.bean.Flag;
import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AttributiVerXMLBean implements Serializable{
	
	private static final long serialVersionUID = -483078932590126871L;
	
	@XmlVariabile(nome="#DisplayFilename_Ver", tipo=TipoVariabile.SEMPLICE)
	private String displayFilename;
	
	@XmlVariabile(nome="#Cod_Ver", tipo=TipoVariabile.SEMPLICE)
	private String cod;
	
	
	@XmlVariabile(nome="#FlgPubblicata_Ver", tipo=TipoVariabile.SEMPLICE)
	private Flag flgPubblicata;	

	
	@XmlVariabile(nome="#FlgDaScansione_Ver", tipo=TipoVariabile.SEMPLICE)
	private Flag flgDaScansione;	

	
	@XmlVariabile(nome="#DtScansione_Ver", tipo=TipoVariabile.SEMPLICE)
	@TipoData(tipo=Tipo.DATA)
	private Date dataArrivo;
	
	@XmlVariabile(nome="#SpecificheScansione_Ver", tipo=TipoVariabile.SEMPLICE)
	private String specificheScansone;
	
	@XmlVariabile(nome="#Note_Ver", tipo=TipoVariabile.SEMPLICE)
	private String note;

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public Flag getFlgPubblicata() {
		return flgPubblicata;
	}

	public void setFlgPubblicata(Flag flgPubblicata) {
		this.flgPubblicata = flgPubblicata;
	}

	public Flag getFlgDaScansione() {
		return flgDaScansione;
	}

	public void setFlgDaScansione(Flag flgDaScansione) {
		this.flgDaScansione = flgDaScansione;
	}

	public Date getDataArrivo() {
		return dataArrivo;
	}

	public void setDataArrivo(Date dataArrivo) {
		this.dataArrivo = dataArrivo;
	}

	public String getSpecificheScansone() {
		return specificheScansone;
	}

	public void setSpecificheScansone(String specificheScansone) {
		this.specificheScansone = specificheScansone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	
		
}
