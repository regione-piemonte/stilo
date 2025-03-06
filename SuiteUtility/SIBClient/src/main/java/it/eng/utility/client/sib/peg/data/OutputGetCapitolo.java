package it.eng.utility.client.sib.peg.data;

import java.util.ArrayList;
import java.util.List;

import org.wso2.ws.dataservice.Capitolo2;

import it.eng.utility.client.sib.Esito;

public class OutputGetCapitolo extends Esito {

	private List<Capitolo2> entries = new ArrayList<Capitolo2>(0);

	public List<Capitolo2> getEntries() {
		return entries;
	}

	public void setEntries(List<Capitolo2> entries) {
		this.entries = entries;
	}

}
