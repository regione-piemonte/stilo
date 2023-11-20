/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RebuildedFileStored extends RebuildedFile {

	private String uriStorage;
	
	public String getUriStorage() {
		return uriStorage;
	}

	public void setUriStorage(String uriStorage) {
		this.uriStorage = uriStorage;
	}

	
}