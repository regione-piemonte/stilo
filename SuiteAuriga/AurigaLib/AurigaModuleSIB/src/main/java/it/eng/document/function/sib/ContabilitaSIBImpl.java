/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import org.wso2.ws.dataservice.GetCapitolo;
import org.wso2.ws.dataservice.GetVociPeg;
import org.wso2.ws.dataservice.GetVociPegVLiv;

import it.datamanagement.sib.webservice.integrazioneauriga.service.InputDettagliContabiliV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordAggiornaAttoV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordCreaPropostaV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordDettagliContabiliV0;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.SibInputAggiornaAtto;
import it.eng.document.function.bean.SibInputCreaProposta;
import it.eng.document.function.bean.SibInputElencoDettagliContabili;
import it.eng.document.function.bean.SibInputGetCapitolo;
import it.eng.document.function.bean.SibInputGetVociPeg;
import it.eng.document.function.bean.SibInputGetVociPegVLiv;
import it.eng.document.function.bean.SibOutputAggiornaAtto;
import it.eng.document.function.bean.SibOutputCreaProposta;
import it.eng.document.function.bean.SibOutputElencoDettagliContabili;
import it.eng.document.function.bean.SibOutputGetCapitolo;
import it.eng.document.function.bean.SibOutputGetVociPeg;
import it.eng.utility.client.sib.determine.data.OutputAggiornaAtto;
import it.eng.utility.client.sib.determine.data.OutputCreaProposta;
import it.eng.utility.client.sib.determine.data.OutputElencoDettagliContabili;
import it.eng.utility.client.sib.peg.data.OutputGetCapitolo;
import it.eng.utility.client.sib.peg.data.OutputGetVociPeg;
import it.eng.utility.client.sib.peg.data.OutputGetVociPegVLiv;

@Service(name = "ContabilitaSIBImpl")
public class ContabilitaSIBImpl implements ContabilitaSIB {

	// private static Logger logger = Logger.getLogger(ContabilitaSIBImpl.class);
	private ContabilitaSIBMapper mapper;
	private ContabilitaSIBBase service;

	public ContabilitaSIBImpl() {
		this.mapper = ContabilitaSIBMapper.INSTANCE;
		this.service = new ContabilitaSIBBaseImpl(); // purtroppo non posso fare altrimenti! diversamente richiederebbe tempo
	}

	@Operation(name = "sibAggiornaAtto")
	public SibOutputAggiornaAtto sibAggiornaAtto(SibInputAggiornaAtto input) {
		final RecordAggiornaAttoV0 in = mapper.fromSIBInputAggiornaAtto(input);
		final OutputAggiornaAtto out = service.aggiornaAtto(in);
		final SibOutputAggiornaAtto output = mapper.outputAggiornaAttoToSIBOutputAggiornaAtto(out);
		return output;
	}

	@Operation(name = "sibCreaProposta")
	public SibOutputCreaProposta sibCreaProposta(SibInputCreaProposta input) {
		final RecordCreaPropostaV0 in = mapper.sibInputCreaPropostaToRecordCreaPropostaV0(input);
		final OutputCreaProposta out = service.creaProposta(in);
		final SibOutputCreaProposta output = mapper.outputCreaPropostaToSIBOutputCreaProposta(out);
		return output;
	}

	@Operation(name = "sibElencoDettagliContabili")
	public SibOutputElencoDettagliContabili sibElencoDettagliContabili(SibInputElencoDettagliContabili input) {
		final InputDettagliContabiliV0 in = mapper.sibInputElencoDettagliContabiliToInputDettagliContabiliV0(input);
		final OutputElencoDettagliContabili out = service.elencoDettagliContabili(in);
		final String tipoTitolo = input.getTipoTitolo();
		if (tipoTitolo != null && (!tipoTitolo.trim().isEmpty())) {
			final List<RecordDettagliContabiliV0> list = new ArrayList<RecordDettagliContabiliV0>();
			if (out.getList() != null) {
				for (RecordDettagliContabiliV0 item : out.getList()) {
					if (tipoTitolo.equalsIgnoreCase(item.getTipoTitolo())) {
						list.add(item);
					}
				} // for
				out.setList(list);
			}
		}
		final SibOutputElencoDettagliContabili output = mapper.outputElencoDettagliContabiliToSIBOutputElencoDettagliContabili(out);

		return output;
	}

	@Operation(name = "sibGetVociPeg")
	public SibOutputGetVociPeg sibGetVociPeg(SibInputGetVociPeg input) {
		final GetVociPeg in = mapper.sibInputGetVociPegToGetVociPeg(input);
		final OutputGetVociPeg out = service.getVociPeg(in);
		// for (Entry entry : out.getEntries()) {
		// if (StringUtils.isNotBlank(entry.getSERVIZIO()))
		// logger.debug("SERVIZIO: " + entry.getSERVIZIO());
		// }
		final SibOutputGetVociPeg output = mapper.outputGetVociPegToSIBOutputGetVociPeg(out);
		return output;
	}

	@Operation(name = "sibGetVociPegCapitolo")
	public SibOutputGetVociPeg sibGetVociPegCapitolo(SibInputGetVociPeg input) {
		final GetVociPeg in = mapper.sibInputGetVociPegToGetVociPeg(input);
		final OutputGetVociPeg out = service.getVociPeg(in);
		final SibOutputGetVociPeg output = mapper.outputGetVociPegToSIBOutputGetVociPegCapitolo(out);
		return output;
	}

	@Operation(name = "sibGetVociPegVLiv")
	public SibOutputGetVociPeg sibGetVociPegVLiv(SibInputGetVociPegVLiv input) {
		final GetVociPegVLiv in = mapper.sibInputGetVociPegVLivToGetVociPegVLiv(input);
		final OutputGetVociPegVLiv out = service.getVociPegVLiv(in);
		final SibOutputGetVociPeg output = mapper.outputGetVociPegVLivToSIBOutputGetVociPegVLiv(out);
		return output;
	}

	@Operation(name = "sibGetCapitolo")
	public SibOutputGetCapitolo sibGetCapitolo(SibInputGetCapitolo input) {
		final GetCapitolo in = mapper.sibInputGetCapitoloToGetCapitolo(input);
		final OutputGetCapitolo out = service.getCapitolo(in);
		final SibOutputGetCapitolo output = mapper.outputGetCapitoloToSIBOutputGetCapitolo(out);
		return output;
	}

	public ContabilitaSIBMapper getMapper() {
		return mapper;
	}

	public void setMapper(ContabilitaSIBMapper mapper) {
		this.mapper = mapper;
	}

	public ContabilitaSIBBase getService() {
		return service;
	}

	public void setService(ContabilitaSIBBase service) {
		this.service = service;
	}

}// ContabilitaSIBImpl