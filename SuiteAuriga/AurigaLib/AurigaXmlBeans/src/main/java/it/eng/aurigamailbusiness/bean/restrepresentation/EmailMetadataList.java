/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

public class EmailMetadataList {
	
	private List<EmailMetadata> intestazione = new ArrayList<>(0);

	public List<EmailMetadata> getIntestazione() {
		return intestazione;
	}
	public void setIntestazione(List<EmailMetadata> intestazione) {
		this.intestazione = intestazione;
	}

}
