package it.eng.utility.client.sib.determine.data;

import java.util.List;

import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordAggiornaAttoV0;
import it.eng.utility.client.sib.Esito;

public class OutputAggiornaAtto extends Esito {

	private List<RecordAggiornaAttoV0> list;

	public List<RecordAggiornaAttoV0> getList() {
		return list;
	}

	public void setList(List<RecordAggiornaAttoV0> list) {
		this.list = list;
	}

}
