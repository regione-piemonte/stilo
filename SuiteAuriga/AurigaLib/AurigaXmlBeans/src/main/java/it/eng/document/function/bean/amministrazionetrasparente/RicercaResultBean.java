/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ricercaResult")
public class RicercaResultBean implements Serializable {

	private static final long serialVersionUID = 1731422633068529914L;

	@XmlElement(name = "nroPag")
	String nroPag;
	
	@XmlElement(name = "pagSize")
	String pagSize;
	
	@XmlElement(name = "nroTotRec")
	String nroRecTot;
	
	@XmlElement(name = "nroRecInPag")
	String pagRecInPag;
	
	@XmlElement(name = "listaConteggiXSezione")
	List<XmlConteggiXSezioneOutBean> listaConteggiXSezione;
	
	@XmlElement(name = "listaContenutoFromRicerca")
	List<XmlContenutiRicercaResultOutBean> listaContenutoFromRicerca;
	
	Esito esito;

	public String getNroPag() {
		return nroPag;
	}

	public void setNroPag(String nroPag) {
		this.nroPag = nroPag;
	}

	public String getPagSize() {
		return pagSize;
	}

	public void setPagSize(String pagSize) {
		this.pagSize = pagSize;
	}

	public String getNroRecTot() {
		return nroRecTot;
	}

	public void setNroRecTot(String nroRecTot) {
		this.nroRecTot = nroRecTot;
	}

	public String getPagRecInPag() {
		return pagRecInPag;
	}

	public void setPagRecInPag(String pagRecInPag) {
		this.pagRecInPag = pagRecInPag;
	}

	public List<XmlConteggiXSezioneOutBean> getListaConteggiXSezione() {
		return listaConteggiXSezione;
	}

	public void setListaConteggiXSezione(List<XmlConteggiXSezioneOutBean> listaConteggiXSezione) {
		this.listaConteggiXSezione = listaConteggiXSezione;
	}

	public List<XmlContenutiRicercaResultOutBean> getListaContenutoFromRicerca() {
		return listaContenutoFromRicerca;
	}

	public void setListaContenutoFromRicerca(List<XmlContenutiRicercaResultOutBean> listaContenutoFromRicerca) {
		this.listaContenutoFromRicerca = listaContenutoFromRicerca;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

}
