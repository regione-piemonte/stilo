/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.mail.internet.MimeMessage;

/**
 * classe che contiene le informazioni della mail allegata alla mail principale
 * 
 * @author mzanetti
 *
 */

public class AttachmentMailInfo extends AttachmentInfo {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttachmentMailInfo other = (AttachmentMailInfo) obj;
		if (this.getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!this.getId().equals(other.getId()))
			return false;
		return true;
	}

	private MimeMessage mimeMessage;

	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
	}

}
