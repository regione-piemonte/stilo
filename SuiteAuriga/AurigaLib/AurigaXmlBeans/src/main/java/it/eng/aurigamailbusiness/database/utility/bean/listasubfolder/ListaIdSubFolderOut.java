/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListaIdSubFolderOut implements Serializable {

	private static final long serialVersionUID = -4049296661887779505L;
	private List<String> folders;

	public void setFolders(List<String> folders) {
		this.folders = folders;
	}

	public List<String> getFolders() {
		return folders;
	}
}
