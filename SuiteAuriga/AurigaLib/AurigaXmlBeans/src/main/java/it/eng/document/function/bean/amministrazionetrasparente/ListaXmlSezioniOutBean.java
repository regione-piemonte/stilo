/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "listaSezioni")
public class ListaXmlSezioniOutBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1559745744089080188L;

	@XmlElement(name = "listaSezioni")
	List<SezioneTreeBean> listaSezioni;
	
	Esito esito;

	public List<SezioneTreeBean> getListaSezioni() {
		return listaSezioni;
	}

	public void setListaSezioni(List<SezioneTreeBean> listaSezioni) {
		this.listaSezioni = listaSezioni;
	}

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}
	
}
