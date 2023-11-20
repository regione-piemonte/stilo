/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.document.NumeroColonna;

/**
 * 
 * @author DANCRIST
 *
 */

@XmlRootElement
public class ProcedimentiCollegatiOutBean implements Serializable {
	
	@NumeroColonna(numero = "1")
	private String idProcess;
	
	@NumeroColonna(numero = "2")
	private String descrizione;

	public String getIdProcess() {
		return idProcess;
	}

	public void setIdProcess(String idProcess) {
		this.idProcess = idProcess;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}