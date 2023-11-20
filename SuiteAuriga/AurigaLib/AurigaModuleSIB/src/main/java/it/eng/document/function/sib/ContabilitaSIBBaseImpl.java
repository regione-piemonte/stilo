/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.wso2.ws.dataservice.GetCapitolo;
import org.wso2.ws.dataservice.GetVociPeg;
import org.wso2.ws.dataservice.GetVociPegVLiv;

import it.datamanagement.sib.webservice.integrazioneauriga.service.InputDettagliContabiliV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordAggiornaAttoV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordCreaPropostaV0;
import it.eng.utility.client.sib.determine.ClientSIBDetermine;
import it.eng.utility.client.sib.determine.ClientSpringSIBDetermine;
import it.eng.utility.client.sib.determine.data.OutputAggiornaAtto;
import it.eng.utility.client.sib.determine.data.OutputCreaProposta;
import it.eng.utility.client.sib.determine.data.OutputElencoDettagliContabili;
import it.eng.utility.client.sib.peg.ClientSIBPEG;
import it.eng.utility.client.sib.peg.ClientSpringSIBPEG;
import it.eng.utility.client.sib.peg.data.OutputGetCapitolo;
import it.eng.utility.client.sib.peg.data.OutputGetVociPeg;
import it.eng.utility.client.sib.peg.data.OutputGetVociPegVLiv;

public class ContabilitaSIBBaseImpl implements ContabilitaSIBBase {

	private ClientSIBDetermine clientDetermine;
	private ClientSIBPEG clientPEG;

	public ContabilitaSIBBaseImpl() {
		this.clientDetermine = ClientSpringSIBDetermine.getClient();
		this.clientPEG = ClientSpringSIBPEG.getClient();
	}

	public OutputAggiornaAtto aggiornaAtto(RecordAggiornaAttoV0 input) {
		final OutputAggiornaAtto output = clientDetermine.aggiornaAtto(input);
		return output;
	}

	public OutputCreaProposta creaProposta(RecordCreaPropostaV0 input) {
		final OutputCreaProposta output = clientDetermine.creaProposta(input);
		return output;
	}

	public OutputElencoDettagliContabili elencoDettagliContabili(InputDettagliContabiliV0 input) {
		final OutputElencoDettagliContabili output = clientDetermine.elencoDettagliContabili(input);
		return output;
	}

	public OutputGetVociPeg getVociPeg(GetVociPeg input) {
		final OutputGetVociPeg output = clientPEG.getVociPeg(input);
		return output;
	}

	public OutputGetVociPegVLiv getVociPegVLiv(GetVociPegVLiv input) {
		final OutputGetVociPegVLiv output = clientPEG.getVociPegVLiv(input);
		return output;
	}

	public OutputGetCapitolo getCapitolo(GetCapitolo input) {
		final OutputGetCapitolo output = clientPEG.getCapitolo(input);
		return output;
	}

	public ClientSIBDetermine getClientDetermine() {
		return clientDetermine;
	}

	public void setClientDetermine(ClientSIBDetermine clientDetermine) {
		this.clientDetermine = clientDetermine;
	}

	public ClientSIBPEG getClientPEG() {
		return clientPEG;
	}

	public void setClientPEG(ClientSIBPEG clientPEG) {
		this.clientPEG = clientPEG;
	}

}// ContabilitaSIBBaseImpl