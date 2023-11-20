/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean di output per il metodo ListaIdFolderUtente
 * @author Rametta
 *
 */
@XmlRootElement
public class ListaIdFolderUtenteOut implements Serializable{

	private static final long serialVersionUID = 2692928925710435540L;
	private List<String> listIdFolders;

	public void setListIdFolders(List<String> listIdFolders) {
		this.listIdFolders = listIdFolders;
	}

	public List<String> getListIdFolders() {
		return listIdFolders;
	}
}
