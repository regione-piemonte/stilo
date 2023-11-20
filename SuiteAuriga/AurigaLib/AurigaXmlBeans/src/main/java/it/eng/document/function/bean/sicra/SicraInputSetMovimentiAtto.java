/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SicraInputSetMovimentiAtto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SicraImpegno> impegno;
	private String messaggioXml;

	public List<SicraImpegno> getImpegno() {
		return impegno;
	}

	public void setImpegno(List<SicraImpegno> impegno) {
		this.impegno = impegno;
	}

	public String getMessaggioXml() {
		return messaggioXml;
	}

	public void setMessaggioXml(String messaggioXml) {
		this.messaggioXml = messaggioXml;
	}

}
