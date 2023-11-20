/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiTagEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="esitiTagEmail")
public class ListaEsitiTagEmail {
	
	@XmlElement(name="esitoTagEmail")
	private List<RigaEsitiTagEmail> items = new ArrayList<>();

	public List<RigaEsitiTagEmail> getItems() {
		return items;
	}
	public void setItems(List<RigaEsitiTagEmail> items) {
		this.items = items;
	}

}
