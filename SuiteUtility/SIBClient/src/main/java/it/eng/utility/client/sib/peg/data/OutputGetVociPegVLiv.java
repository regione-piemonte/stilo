package it.eng.utility.client.sib.peg.data;

import java.util.ArrayList;
import java.util.List;

import org.wso2.ws.dataservice.Capitolo;

import it.eng.utility.client.sib.Esito;

public class OutputGetVociPegVLiv extends Esito {

	private List<Capitolo> entries = new ArrayList<Capitolo>(0);

	public List<Capitolo> getEntries() {
		return entries;
	}

	public void setEntries(List<Capitolo> entries) {
		this.entries = entries;
	}

}
