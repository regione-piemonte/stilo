package it.eng.utility.client.sib.determine.data;

import java.util.List;

import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordDettagliContabiliV0;
import it.eng.utility.client.sib.Esito;

public class OutputElencoDettagliContabili extends Esito {

	private List<RecordDettagliContabiliV0> list;

	public List<RecordDettagliContabiliV0> getList() {
		return list;
	}

	public void setList(List<RecordDettagliContabiliV0> list) {
		this.list = list;
	}

}
