/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "dettaglioDocumentoComplesso")
public class DettaglioTabellaOutBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1066618231668068492L;
	
	@XmlElement(name = "titolo")
	String titolo;
	
	@XmlElement(name = "flgPresentiInfoInDett")
	String flgPresentiInfoInDett;
	
	@XmlElement(name = "infoDettInDettaglio")
	String infoDettInDettaglio;
	
	@XmlElement(name = "infoDettInSez")
	String infoDettInSez;
	
	@XmlElement(name = "flgInfoDettUguali")
	String flgInfoDettUguali;
	
	@XmlElement(name = "datiAgg")
	String datiAgg;
	
	@XmlElement(name = "nroPaginaIO")
	Integer nroPaginaIO;
	
	@XmlElement(name = "infoStrutturaTab")
	List<XmlInfoStrutturaTabOutBean> infoStrutturaTab;
	
	@XmlElement(name = "nroRecTotali")
	Integer nroRecTotali;
	
	@XmlElement(name = "nroPagineTot")
	Integer nroPagineTot;
	
	@XmlElement(name = "nroRecInPagina")
	Integer nroRecInPagina;
	
	@XmlElement(name = "valoriRecTab")
	LinkedHashMap<Integer, LinkedList<String>> valoriRecTab;
	
	Esito esito;

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getFlgPresentiInfoInDett() {
		return flgPresentiInfoInDett;
	}

	public void setFlgPresentiInfoInDett(String flgPresentiInfoInDett) {
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

	public String getFlgInfoDettUguali() {
		return flgInfoDettUguali;
	}

	public void setFlgInfoDettUguali(String flgInfoDettUguali) {
		this.flgInfoDettUguali = flgInfoDettUguali;
	}

	public String getDatiAgg() {
		return datiAgg;
	}

	public void setDatiAgg(String datiAgg) {
		this.datiAgg = datiAgg;
	}

	public Integer getNroPaginaIO() {
		return nroPaginaIO;
	}

	public void setNroPaginaIO(Integer nroPaginaIO) {
		this.nroPaginaIO = nroPaginaIO;
	}

	public List<XmlInfoStrutturaTabOutBean> getInfoStrutturaTab() {
		return infoStrutturaTab;
	}

	public void setInfoStrutturaTab(List<XmlInfoStrutturaTabOutBean> infoStrutturaTab) {
		this.infoStrutturaTab = infoStrutturaTab;
	}

	public Integer getNroRecTotali() {
		return nroRecTotali;
	}

	public void setNroRecTotali(Integer nroRecTotali) {
		this.nroRecTotali = nroRecTotali;
	}

	public Integer getNroPagineTot() {
		return nroPagineTot;
	}

	public void setNroPagineTot(Integer nroPagineTot) {
		this.nroPagineTot = nroPagineTot;
	}

	public Integer getNroRecInPagina() {
		return nroRecInPagina;
	}

	public void setNroRecInPagina(Integer nroRecInPagina) {
		this.nroRecInPagina = nroRecInPagina;
	}

	public LinkedHashMap<Integer, LinkedList<String>> getValoriRecTab() {
		return valoriRecTab;
	}

	public void setValoriRecTab(LinkedHashMap<Integer, LinkedList<String>> valoriRecTab) {
		this.valoriRecTab = valoriRecTab;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

}
