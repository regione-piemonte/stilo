/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiUnlockEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="esitiUnlockEmail")
public class ListaEsitiUnlockEmail {
	
	@XmlElement(name="esitoUnlockEmail")
	private List<RigaEsitiUnlockEmail> items = new ArrayList<>();

	public List<RigaEsitiUnlockEmail> getItems() {
		return items;
	}
	public void setItems(List<RigaEsitiUnlockEmail> items) {
		this.items = items;
	}

}
