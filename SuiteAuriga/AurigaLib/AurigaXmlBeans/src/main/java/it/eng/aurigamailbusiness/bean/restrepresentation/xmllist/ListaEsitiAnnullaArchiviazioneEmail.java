/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.eng.aurigamailbusiness.bean.restrepresentation.row.RigaEsitiAnnullaArchiviazioneEmail;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="esitiAnnullaArchiviazioneEmail")
public class ListaEsitiAnnullaArchiviazioneEmail {
	
	@XmlElement(name="esitoAnnullaArchiviazioneEmail")
	private List<RigaEsitiAnnullaArchiviazioneEmail> items = new ArrayList<>();

	public List<RigaEsitiAnnullaArchiviazioneEmail> getItems() {
		return items;
	}
	public void setItems(List<RigaEsitiAnnullaArchiviazioneEmail> items) {
		this.items = items;
	}

}
