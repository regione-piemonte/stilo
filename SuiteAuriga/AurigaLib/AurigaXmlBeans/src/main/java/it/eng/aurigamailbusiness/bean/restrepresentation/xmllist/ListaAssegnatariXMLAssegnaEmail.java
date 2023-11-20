/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaAssegnatariXMLAssegnaEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="assegnatariEmail")
public class ListaAssegnatariXMLAssegnaEmail {
	
	@XmlElement(name="assegnatarioEmail")
	private List<RigaAssegnatariXMLAssegnaEmail> items = new ArrayList<>();

	public List<RigaAssegnatariXMLAssegnaEmail> getItems() {
		return items;
	}
	public void setItems(List<RigaAssegnatariXMLAssegnaEmail> items) {
		this.items = items;
	}

}
