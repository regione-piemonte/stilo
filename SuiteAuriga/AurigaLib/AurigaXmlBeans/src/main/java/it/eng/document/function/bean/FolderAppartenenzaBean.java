/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FolderAppartenenzaBean implements Serializable{

	@NumeroColonna(numero = "1")
	private BigDecimal idFolder;

	public void setIdFolder(BigDecimal idFolder) {
		this.idFolder = idFolder;
	}

	public BigDecimal getIdFolder() {
		return idFolder;
	}
}
