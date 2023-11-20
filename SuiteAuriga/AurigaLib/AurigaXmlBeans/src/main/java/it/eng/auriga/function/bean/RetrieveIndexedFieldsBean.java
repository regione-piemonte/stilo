/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RetrieveIndexedFieldsBean implements Serializable{

	private static final long serialVersionUID = -8022511859232540686L;

	private String identificativo;
	private  Collection<String> results;
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getIdentificativo() {
		return identificativo;
	}
	public void setResults(Collection<String> results) {
		this.results = results;
	}
	public Collection<String> getResults() {
		return results;
	}
}
