/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.annotation.Obbligatorio;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di input per il metodo listaIdFolderUtente
 * @author Rametta
 *
 */
@XmlRootElement
public class ListaIdFolderUtenteIn implements Serializable{

	private static final long serialVersionUID = -5496564978792003902L;

	@Obbligatorio
	private String idUtente;
	@Obbligatorio
	private List<String> classificazioniFolder;
	private String idEnteAOO;

	public void setIdUtente(String idUtente) {
		this.idUtente = idUtente;
	}

	public String getIdUtente() {
		return idUtente;
	}

	public void setClassificazioniFolder(List<String> classificazioniFolder) {
		this.classificazioniFolder = classificazioniFolder;
	}

	public List<String> getClassificazioniFolder() {
		return classificazioniFolder;
	}

	public void setIdEnteAOO(String idEnteAOO) {
		this.idEnteAOO = idEnteAOO;
	}

	public String getIdEnteAOO() {
		return idEnteAOO;
	}
	
	
	
}
