/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WSDeleteFolderBean implements Serializable {

	
	private String idFolder;
	private String flgTipoDel;
	
	

	public String getIdFolder() {
		return idFolder;
	}

	public void setIdFolder(String idFolder) {
		this.idFolder = idFolder;
	}

	public String getFlgTipoDel() {
		return flgTipoDel;
	}

	public void setFlgTipoDel(String flgTipoDel) {
		this.flgTipoDel = flgTipoDel;
	}
	

	}
