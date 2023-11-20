/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;
import it.eng.document.NumeroColonna;


@XmlRootElement
public class TipoServizioXmlBean {
	
	@NumeroColonna(numero = "1")
	private String idTipoServizio;

	public String getIdTipoServizio() {
		return idTipoServizio;
	}

	public void setIdTipoServizio(String idTipoServizio) {
		this.idTipoServizio = idTipoServizio;
	}
}
