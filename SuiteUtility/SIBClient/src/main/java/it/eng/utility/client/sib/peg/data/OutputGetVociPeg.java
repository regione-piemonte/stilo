/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.wso2.ws.dataservice.VocePeg;

import it.eng.utility.client.sib.Esito;

public class OutputGetVociPeg extends Esito {

	private List<VocePeg> entries = new ArrayList<VocePeg>(0);

	public List<VocePeg> getEntries() {
		return entries;
	}

	public void setEntries(List<VocePeg> entries) {
		this.entries = entries;
	}

}
