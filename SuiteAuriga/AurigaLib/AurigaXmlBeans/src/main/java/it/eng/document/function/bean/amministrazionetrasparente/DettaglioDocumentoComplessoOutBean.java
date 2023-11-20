/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dettaglioDocumentoComplesso")
public class DettaglioDocumentoComplessoOutBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1066618231668068492L;
	
	@XmlElement(name = "titolo")
	String titolo;
	
	@XmlElement(name = "flgFilePrimario")
	Integer flgFilePrimario;
	
	@XmlElement(name = "nroFileAllegati")
	Integer nroFileAllegati;
	
	@XmlElement(name = "flgPresentiInfoInDett")
	Integer flgPresentiInfoInDett;
	
	@XmlElement(name = "infoDettInDettaglio")
	String infoDettInDettaglio;
	
	@XmlElement(name = "infoDettInSez")
	String infoDettInSez;
	
	@XmlElement(name = "flgInfoDettUguali")
	Integer flgInfoDettUguali;
	
	@XmlElement(name = "datiAgg")
	String datiAgg;
	
	@XmlElement(name = "datiFilePrimario")
	List<DettaglioDatiFileBean> datiFilePrimario;
	
	@XmlElement(name = "datiFileAllegati")
	List<DettaglioDatiFileBean> datiFileAllegati;
	
	Esito esito;

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Integer getFlgFilePrimario() {
		return flgFilePrimario;
	}

	public void setFlgFilePrimario(Integer flgFilePrimario) {
		this.flgFilePrimario = flgFilePrimario;
	}

	public Integer getNroFileAllegati() {
		return nroFileAllegati;
	}

	public void setNroFileAllegati(Integer nroFileAllegati) {
		this.nroFileAllegati = nroFileAllegati;
	}

	public Integer getFlgPresentiInfoInDett() {
		return flgPresentiInfoInDett;
	}

	public void setFlgPresentiInfoInDett(Integer flgPresentiInfoInDett) {
		this.flgPresentiInfoInDett = flgPresentiInfoInDett;
	}

	public String getInfoDettInDettaglio() {
		return infoDettInDettaglio;
	}

	public void setInfoDettInDettaglio(String infoDettInDettaglio) {
		this.infoDettInDettaglio = infoDettInDettaglio;
	}

	public String getInfoDettInSez() {
		return infoDettInSez;
	}

	public void setInfoDettInSez(String infoDettInSez) {
		this.infoDettInSez = infoDettInSez;
	}

	public Integer getFlgInfoDettUguali() {
		return flgInfoDettUguali;
	}

	public void setFlgInfoDettUguali(Integer flgInfoDettUguali) {
		this.flgInfoDettUguali = flgInfoDettUguali;
	}

	public String getDatiAgg() {
		return datiAgg;
	}

	public void setDatiAgg(String datiAgg) {
		this.datiAgg = datiAgg;
	}

	public List<DettaglioDatiFileBean> getDatiFilePrimario() {
		return datiFilePrimario;
	}

	public void setDatiFilePrimario(List<DettaglioDatiFileBean> datiFilePrimario) {
		this.datiFilePrimario = datiFilePrimario;
	}

	public List<DettaglioDatiFileBean> getDatiFileAllegati() {
		return datiFileAllegati;
	}

	public void setDatiFileAllegati(List<DettaglioDatiFileBean> datiFileAllegati) {
		this.datiFileAllegati = datiFileAllegati;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

}
