/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.wso2.ws.dataservice.Capitolo;
import org.wso2.ws.dataservice.Capitolo2;
import org.wso2.ws.dataservice.GetCapitolo;
import org.wso2.ws.dataservice.GetVociPeg;
import org.wso2.ws.dataservice.GetVociPegVLiv;
import org.wso2.ws.dataservice.VocePeg;

import it.datamanagement.sib.webservice.integrazioneauriga.service.InputDettagliContabiliV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordAggiornaAttoV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordCreaPropostaV0;
import it.datamanagement.sib.webservice.integrazioneauriga.service.RecordDettagliContabiliV0;
import it.eng.document.function.bean.SibEntry;
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
import it.eng.document.function.bean.SibRecordDettagliContabili;
import it.eng.utility.client.sib.determine.data.OutputAggiornaAtto;
import it.eng.utility.client.sib.determine.data.OutputCreaProposta;
import it.eng.utility.client.sib.determine.data.OutputElencoDettagliContabili;
import it.eng.utility.client.sib.peg.data.OutputGetCapitolo;
import it.eng.utility.client.sib.peg.data.OutputGetVociPeg;
import it.eng.utility.client.sib.peg.data.OutputGetVociPegVLiv;

@Mapper /* (componentModel = "spring") */
public interface ContabilitaSIBMapper {

	ContabilitaSIBMapper INSTANCE = Mappers.getMapper(ContabilitaSIBMapper.class);

	// -------------------------------------------------------------------------------------------------------------------------
	SibOutputAggiornaAtto outputAggiornaAttoToSIBOutputAggiornaAtto(OutputAggiornaAtto source);

	SibInputAggiornaAtto toSIBInputAggiornaAtto(RecordAggiornaAttoV0 source);

	@InheritInverseConfiguration
	RecordAggiornaAttoV0 fromSIBInputAggiornaAtto(SibInputAggiornaAtto source);

	// -------------------------------------------------------------------------------------------------------------------------
	SibOutputCreaProposta outputCreaPropostaToSIBOutputCreaProposta(OutputCreaProposta source);

	RecordCreaPropostaV0 sibInputCreaPropostaToRecordCreaPropostaV0(SibInputCreaProposta source);

	// -------------------------------------------------------------------------------------------------------------------------
	SibOutputElencoDettagliContabili outputElencoDettagliContabiliToSIBOutputElencoDettagliContabili(OutputElencoDettagliContabili source);

	SibRecordDettagliContabili recordDettagliContabiliV0ToSIBRecordDettagliContabili(RecordDettagliContabiliV0 source);

	InputDettagliContabiliV0 sibInputElencoDettagliContabiliToInputDettagliContabiliV0(SibInputElencoDettagliContabili source);

	// -------------------------------------------------------------------------------------------------------------------------
	@Mappings({ @Mapping(target = "entries", qualifiedByName = "entryToSIBEntry") })
	SibOutputGetVociPeg outputGetVociPegToSIBOutputGetVociPeg(OutputGetVociPeg source);

	@Mappings({ @Mapping(target = "entries", qualifiedByName = "entryToSIBEntryCapitolo") })
	SibOutputGetVociPeg outputGetVociPegToSIBOutputGetVociPegCapitolo(OutputGetVociPeg source);

	@Mappings({ @Mapping(target = "entries", qualifiedByName = "entryToSIBEntryVLiv") })
	SibOutputGetVociPeg outputGetVociPegVLivToSIBOutputGetVociPegVLiv(OutputGetVociPegVLiv source);

	@Mappings({ @Mapping(source = "ENTRATEUSCITE", target = "entrateUscite"), @Mapping(source = "ESERCIZIO", target = "esercizio"),
			@Mapping(source = "CAPITOLO", target = "capitolo"), @Mapping(source = "ARTICOLO", target = "articolo"),
			@Mapping(source = "NUMERO", target = "numero"), @Mapping(source = "DESCRIZIONECAPITOLO", target = "descrizioneCapitolo"),
			@Mapping(source = "DESCRIZIONEDC", target = "descrizioneDC"), @Mapping(source = "DESCRIZIONECDR", target = "descrizioneCDR"),
			@Mapping(source = "DESCRIZIONECAN", target = "descrizioneCAN"), @Mapping(source = "COMPETENZAPLURIENNALE", target = "competenzaPluriennale"),
			@Mapping(source = "TITOLO", target = "titolo"), @Mapping(source = "FUNZIONE", target = "funzione"),
			@Mapping(source = "SERVIZIO", target = "servizio"), @Mapping(source = "INTERVENTO", target = "intervento"),
			@Mapping(source = "PDCLIVELLO1", target = "PDCLivello1"), @Mapping(source = "PDCLIVELLO2", target = "PDCLivello2"),
			@Mapping(source = "PDCLIVELLO3", target = "PDCLivello3"), @Mapping(source = "PDCLIVELLO4", target = "PDCLivello4"),
			@Mapping(source = "PDCLIVELLO5", target = "PDCLivello5"), @Mapping(source = "PDCARMONIZZATOALF", target = "PDCArmonizzatoAlf"),
			@Mapping(source = "MISSIONE", target = "missione"), @Mapping(source = "PROGRAMMA", target = "programma"), @Mapping(source = "CDR", target = "CDR"),
			@Mapping(source = "CAN", target = "CAN"), @Mapping(source = "DIREZIONECENTRALE", target = "direzioneCentrale"),
			@Mapping(source = "PROPOSTO", target = "proposto"), @Mapping(source = "PREVISIONE", target = "previsione"),
			@Mapping(source = "INIZIALE", target = "iniziale"), @Mapping(source = "APPROVATO", target = "approvato"),
			@Mapping(source = "TOTALEVARIAZIONI", target = "totaleVariazioni"), @Mapping(source = "ASSESTATO", target = "assestato"),
			@Mapping(source = "IMPEGNATOACCERTATO", target = "impegnatoAccertato"), @Mapping(source = "DISPONIBILE", target = "disponibile"),
			@Mapping(source = "MANDATIREVERSALIEMESSI", target = "mandatiReversaliEmessi"),
			@Mapping(source = "MANDATIREVERSALIPAGATI", target = "mandatiReversaliPagati"), @Mapping(source = "FOGLIA", target = "foglia") })
	@Named("entryToSIBEntry")
	SibEntry entryToSIBEntry(VocePeg source);

	@Mappings({ @Mapping(source = "ENTRATEUSCITE", target = "entrateUscite"), @Mapping(source = "ESERCIZIO", target = "esercizio"),
			@Mapping(source = "CAPITOLO", target = "capitolo"), @Mapping(source = "DESCRIZIONECAPITOLO", target = "descrizioneCapitolo"),
			@Mapping(source = "DESCRIZIONEDC", target = "descrizioneDC"), @Mapping(source = "COMPETENZAPLURIENNALE", target = "competenzaPluriennale"),
			@Mapping(source = "DIREZIONECENTRALE", target = "direzioneCentrale"), @Mapping(target = "CAN", ignore = true),
			@Mapping(target = "CDR", ignore = true), @Mapping(source = "FOGLIA", target = "foglia") })
	@Named("entryToSIBEntryCapitolo")
	SibEntry entryToSIBEntryCapitolo(VocePeg source);

	@Mappings({ @Mapping(source = "ENTRATEUSCITE", target = "entrateUscite"), @Mapping(source = "ESERCIZIO", target = "esercizio"),
			@Mapping(source = "PDCLIVELLO1", target = "PDCLivello1"), @Mapping(source = "PDCLIVELLO2", target = "PDCLivello2"),
			@Mapping(source = "PDCLIVELLO3", target = "PDCLivello3"), @Mapping(source = "PDCLIVELLO4", target = "PDCLivello4"),
			@Mapping(source = "PDCLIVELLO5", target = "PDCLivello5"), @Mapping(source = "DESCRIZIONE", target = "descrizioneCapitolo") })
	@Named("entryToSIBEntryVLiv")
	SibEntry entryToSIBEntryVLiv(Capitolo source);

	GetVociPeg sibInputGetVociPegToGetVociPeg(SibInputGetVociPeg source);

	GetVociPegVLiv sibInputGetVociPegVLivToGetVociPegVLiv(SibInputGetVociPegVLiv source);

	// -------------------------------------------------------------------------------------------------------------------------
	@Mappings({ @Mapping(source = "CAPITOLO", target = "capitolo"), @Mapping(source = "DESCRIZIONECAPITOLO", target = "descrizioneCapitolo"),
			@Mapping(source = "DESCRIZIONECDR", target = "descrizioneCDR") })
	SibEntry capitolo2ToSIBEntry(Capitolo2 source);

	@Mappings({ @Mapping(target = "entries", qualifiedByName = "capitolo2ToSIBEntry") })
	SibOutputGetCapitolo outputGetCapitoloToSIBOutputGetCapitolo(OutputGetCapitolo source);

	GetCapitolo sibInputGetCapitoloToGetCapitolo(SibInputGetCapitolo source);

	// -------------------------------------------------------------------------------------------------------------------------
}
