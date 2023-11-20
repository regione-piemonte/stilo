/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaResultTrovaEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="emailTrovate")
public class ListaResultTrovaEmail {
	
	@XmlElement(name="email")
	private List<RigaResultTrovaEmail> items = new ArrayList<>();

	public List<RigaResultTrovaEmail> getItems() {
		return items;
	}
	public void setItems(List<RigaResultTrovaEmail> items) {
		this.items = items;
	}

}
