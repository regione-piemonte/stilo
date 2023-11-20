/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

public class EmailMetadataOrderingList {
	
	private List<EmailMetadataOrdering> metadato = new ArrayList<>(0);

	public List<EmailMetadataOrdering> getMetadato() {
		return metadato;
	}
	public void setMetadato(List<EmailMetadataOrdering> metadato) {
		this.metadato = metadato;
	}

}
