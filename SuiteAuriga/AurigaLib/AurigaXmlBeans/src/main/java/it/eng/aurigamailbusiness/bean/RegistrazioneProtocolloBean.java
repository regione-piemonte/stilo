/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegistrazioneProtocolloBean extends InfoProtocolloBean {

	private static final long serialVersionUID = -3550804011262243976L;

	private RegistrazioneProtocollo registrazione;
	
	private List<ProtocolloAttachmentBean> attachmentsProtocollati;

	public RegistrazioneProtocollo getRegistrazione() {
		return registrazione;
	}

	public void setRegistrazione(RegistrazioneProtocollo registrazione) {
		this.registrazione = registrazione;
	}

	public List<ProtocolloAttachmentBean> getAttachmentsProtocollati() {
		return attachmentsProtocollati;
	}

	public void setAttachmentsProtocollati(List<ProtocolloAttachmentBean> attachmentsProtocollati) {
		this.attachmentsProtocollati = attachmentsProtocollati;
	}

}
