/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.wso2.ws.dataservice.GetCapitolo;
import org.wso2.ws.dataservice.GetVociPeg;
import org.wso2.ws.dataservice.GetVociPegVLiv;

import it.datamanagement.sib.webservice.integrazioneauriga.service.InputDettagliContabiliV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordAggiornaAttoV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordCreaPropostaV0;
import it.eng.utility.client.sib.determine.data.OutputAggiornaAtto;
import it.eng.utility.client.sib.determine.data.OutputCreaProposta;
import it.eng.utility.client.sib.determine.data.OutputElencoDettagliContabili;
import it.eng.utility.client.sib.peg.data.OutputGetCapitolo;
import it.eng.utility.client.sib.peg.data.OutputGetVociPeg;
import it.eng.utility.client.sib.peg.data.OutputGetVociPegVLiv;

public interface ContabilitaSIBBase {

	OutputAggiornaAtto aggiornaAtto(RecordAggiornaAttoV0 input);

	OutputCreaProposta creaProposta(RecordCreaPropostaV0 input);

	OutputElencoDettagliContabili elencoDettagliContabili(InputDettagliContabiliV0 input);

	OutputGetVociPeg getVociPeg(GetVociPeg input);

	OutputGetVociPegVLiv getVociPegVLiv(GetVociPegVLiv input);

	OutputGetCapitolo getCapitolo(GetCapitolo input);
}
