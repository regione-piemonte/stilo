/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe per la registrazione del protocollo da associarsi ad un attach di una email
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class ProtocolloAttachmentBean implements Serializable {

	private static final long serialVersionUID = -3584785130304495317L;

	private String idAttachment;

	private Byte numeroAllegatoRegProt;

	public String getIdAttachment() {
		return idAttachment;
	}

	public void setIdAttachment(String idAttachment) {
		this.idAttachment = idAttachment;
	}

	public Byte getNumeroAllegatoRegProt() {
		return numeroAllegatoRegProt;
	}

	public void setNumeroAllegatoRegProt(Byte numeroAllegatoRegProt) {
		this.numeroAllegatoRegProt = numeroAllegatoRegProt;
	}

}
