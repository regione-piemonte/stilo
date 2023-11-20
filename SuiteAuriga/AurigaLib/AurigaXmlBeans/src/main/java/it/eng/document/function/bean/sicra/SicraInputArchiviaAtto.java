/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraInputArchiviaAtto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String parte;
	private SicraAttoArchiviazione atto;
	private Boolean soloProvvisori;
	private String messaggioXml;

	public Boolean getSoloProvvisori() {
		return soloProvvisori;
	}

	public void setSoloProvvisori(Boolean soloProvvisori) {
		this.soloProvvisori = soloProvvisori;
	}

	public String getMessaggioXml() {
		return messaggioXml;
	}

	public void setMessaggioXml(String messaggioXml) {
		this.messaggioXml = messaggioXml;
	}

	public SicraAttoArchiviazione getAtto() {
		return atto;
	}

	public void setAtto(SicraAttoArchiviazione atto) {
		this.atto = atto;
	}

	public String getParte() {
		return parte;
	}

	public void setParte(String parte) {
		this.parte = parte;
	}

}
