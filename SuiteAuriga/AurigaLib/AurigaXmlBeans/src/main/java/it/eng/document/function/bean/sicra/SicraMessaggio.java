/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraMessaggio implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipo;
	private String descrizione;
	private String messaggioXml;
	private SicraMessaggiAvviso messaggiAvviso;
	private SicraMessaggiErrore messaggiErrore;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getMessaggioXml() {
		return messaggioXml;
	}

	public void setMessaggioXml(String messaggioXml) {
		this.messaggioXml = messaggioXml;
	}

	public SicraMessaggiAvviso getMessaggiAvviso() {
		return messaggiAvviso;
	}

	public void setMessaggiAvviso(SicraMessaggiAvviso messaggiAvviso) {
		this.messaggiAvviso = messaggiAvviso;
	}

	public SicraMessaggiErrore getMessaggiErrore() {
		return messaggiErrore;
	}

	public void setMessaggiErrore(SicraMessaggiErrore messaggiErrore) {
		this.messaggiErrore = messaggiErrore;
	}

}
