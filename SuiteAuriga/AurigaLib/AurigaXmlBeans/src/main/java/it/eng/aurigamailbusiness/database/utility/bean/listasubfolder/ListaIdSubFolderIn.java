/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.aurigamailbusiness.annotation.Obbligatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListaIdSubFolderIn implements Serializable{

	private static final long serialVersionUID = 5537817252238423927L;
	@Obbligatorio
	private String idFolder;
	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}
	public String getIdFolder() {
		return idFolder;
	}
}
