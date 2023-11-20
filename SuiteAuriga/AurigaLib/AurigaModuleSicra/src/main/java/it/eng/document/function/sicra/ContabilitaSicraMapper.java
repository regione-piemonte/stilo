/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import it.eng.document.function.bean.sicra.EsitoChiamata;
import it.eng.document.function.bean.sicra.SicraAttoArchiviazione;
import it.eng.document.function.bean.sicra.SicraAttoDefinitivo;
import it.eng.document.function.bean.sicra.SicraAttoDefinitivoLiquidazione;
import it.eng.document.function.bean.sicra.SicraAttoRicerca;
import it.eng.document.function.bean.sicra.SicraBudget;
import it.eng.document.function.bean.sicra.SicraDettaglioImpegno;
import it.eng.document.function.bean.sicra.SicraImpegnoDaRendereEsecutivo;
import it.eng.document.function.bean.sicra.SicraImpegnoSintetico;
import it.eng.document.function.bean.sicra.SicraInputAggiornaRifAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputArchiviaAtto;
import it.eng.document.function.bean.sicra.SicraInputRicercaAnagrafica;
import it.eng.document.function.bean.sicra.SicraInputRicercaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputRicercaOrdinativoAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputRicercaVociBilancio;
import it.eng.document.function.bean.sicra.SicraInputSetEsecutivitaAtto;
import it.eng.document.function.bean.sicra.SicraInputSetMovimentiAtto;
import it.eng.document.function.bean.sicra.SicraMessaggiAvviso;
import it.eng.document.function.bean.sicra.SicraMessaggiErrore;
import it.eng.document.function.bean.sicra.SicraMessaggio;
import it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto;
import it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica;
import it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputRicercaOrdinativoAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio;
import it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto;
import it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto;
import it.eng.document.function.bean.sicra.SicraPropostaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraTestataImpegno;
import it.eng.utility.data.Outcome;
import it.eng.utility.data.Output;

@Mapper(uses = ContabilitaSicraCustomMapper.class)
@DecoratedWith(ContabilitaSicraMapperDecorator.class)
public interface ContabilitaSicraMapper {

	ContabilitaSicraMapper INSTANCE = Mappers.getMapper(ContabilitaSicraMapper.class);

	/* RICERCA VOCI BILANCIO ********************************************************************************************/
	@Mappings(value = { @Mapping(source = "aclBilancio", target = "aclbilancio"), @Mapping(source = "annoCompetenza", target = "annocompetenza"),
			@Mapping(source = "codCategoria", target = "codcategoria"), @Mapping(source = "codCentroCosto", target = "codcentrocosto"),
			@Mapping(source = "codLavoro", target = "codlavoro"), @Mapping(source = "codMacroAggregato", target = "codmacroaggregato"),
			@Mapping(source = "codMissione", target = "codmissione"), @Mapping(source = "codProgetto", target = "codprogetto"),
			@Mapping(source = "codProgramma", target = "codprogramma"),
			@Mapping(source = "codResponsabileProcedimento", target = "codresponsabileprocedimento"),
			@Mapping(source = "centroDiCosto", target = "codresponsabileservizio"), @Mapping(source = "codTipoFinanz", target = "codtipofinanz"),
			@Mapping(source = "codTipologia", target = "codtipologia"), @Mapping(source = "codTipoSpesa", target = "codtipospesa"),
			@Mapping(source = "codTitolo", target = "codtitolo"), @Mapping(source = "copertoDaFPV", target = "copfpv"),
			@Mapping(source = "dataValuta", target = "datavaluta"), @Mapping(source = "flagRaggruppaCentroCosto", target = "flraggruppacentrocosto"),
			@Mapping(source = "flagRaggruppaCPT", target = "flraggruppacpt"), @Mapping(source = "flagRaggruppaFPV", target = "flraggruppafpv"),
			@Mapping(source = "flagRaggruppaLavoro", target = "flraggruppalavoro"), @Mapping(source = "flagRaggruppaPianoFin", target = "flraggruppapianofin"),
			@Mapping(source = "flagRaggruppaProgetto", target = "flraggruppaprogetto"),
			@Mapping(source = "flagRaggruppaTipoFinanz", target = "flraggruppatipofinanz"),
			@Mapping(source = "flagRaggruppaTipoSpesa", target = "flraggruppatipospesa"), @Mapping(source = "numCapitolo", target = "numcapitolo"),
			@Mapping(source = "siglaCPT", target = "siglacpt"), @Mapping(source = "siglaPianoFinanziario", target = "siglapianofinanziario") })
	it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Richiesta sicraInputRicercaVociBilancioToRichiesta(SicraInputRicercaVociBilancio source);

	@Mappings(value = { @Mapping(target = "budget", ignore = true), @Mapping(source = "outcome", target = "esitoChiamata") })
	SicraOutputRicercaVociBilancio outputRisultatoToSicraOutputRicercaVociBilancio(
			Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> source);

	@Mappings(value = { @Mapping(source = "annocompetenza", target = "annoCompetenza"), @Mapping(source = "codcentrocosto", target = "codCentroCosto"),
			@Mapping(source = "codlavoro", target = "codLavoro"), @Mapping(source = "codprogetto", target = "codProgetto"),
			@Mapping(source = "codrespproccapitolo", target = "codRespProcCapitolo"), @Mapping(source = "codrespservcapitolo", target = "codRespServCapitolo"),
			@Mapping(source = "codtipofinanz", target = "codTipoFinanz"), @Mapping(source = "codtipospesa", target = "codTipoSpesa"),
			@Mapping(source = "copfpv", target = "copFPV"), @Mapping(source = "descpt", target = "desCPT"),
			@Mapping(source = "descptcapitolo", target = "desCPTcapitolo"), @Mapping(source = "descentrocosto", target = "desCentroCosto"),
			@Mapping(source = "deslavoro", target = "desLavoro"), @Mapping(source = "despianofin", target = "desPianoFin"),
			@Mapping(source = "despianofincapitolo", target = "desPianoFinCapitolo"), @Mapping(source = "desprogetto", target = "desProgetto"),
			@Mapping(source = "desrespproccapitolo", target = "desRespProcCapitolo"), @Mapping(source = "desrespservcapitolo", target = "desRespServCapitolo"),
			@Mapping(source = "destipofinanz", target = "desTipoFinanz"), @Mapping(source = "destipospesa", target = "desTipoSpesa"),
			@Mapping(source = "descrizionebudget", target = "descrizioneBudget"), @Mapping(source = "documentiaperti", target = "documentiAperti"),
			@Mapping(source = "idcapitolo", target = "idCapitolo"), @Mapping(source = "idlavoro", target = "idLavoro"),
			@Mapping(source = "idprogetto", target = "idProgetto"), @Mapping(source = "idtipofinanz", target = "idTipoFinanz"),
			@Mapping(source = "idtipospesa", target = "idTipoSpesa"), @Mapping(source = "mandatiemessi", target = "mandatiEmessi"),
			@Mapping(source = "numcapitolo", target = "numCapitolo"), @Mapping(source = "prenotatodisponibile", target = "prenotatoDisponibile"),
			@Mapping(source = "sigcpt", target = "sigCPT"), @Mapping(source = "sigcptcapitolo", target = "sigCPTcapitolo"),
			@Mapping(source = "sigpianofin", target = "sigPianoFin"), @Mapping(source = "sigpianofincapitolo", target = "sigPianoFinCapitolo") })
	SicraBudget budgetToSicraBudget(it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato.ListaBudget.Budget source);

	List<SicraBudget> budgetsToSicraBudgets(List<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato.ListaBudget.Budget> source);

	it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Richiesta.TipoRisultato toTipoRisultato(String nome);

	EsitoChiamata esitoToEsitoChiamata(Outcome esito);

	// default
	// List<SicraBudget> risultatoToSicraBudgets(it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato source);

	/* SET MOVIMENTI ATTO ********************************************************************************************/
	it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta sicraInputSetMovimentiAttoToRichiesta(SicraInputSetMovimentiAtto source);

	// default
	@Mappings(value = { @Mapping(target = "impegno", ignore = true), @Mapping(target = "riscontro", ignore = true),
			@Mapping(target = "esitoChiamata", ignore = true) })
	SicraOutputSetMovimentiAtto outputRisultatoToSicraOutputSetMovimentiAtto(Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> source);

	@Mappings(value = { @Mapping(source = "annoCompetenza", target = "annocompetenza"), @Mapping(source = "codCentroCosto", target = "codcentrocosto"),
			@Mapping(source = "codificaCapitolo", target = "codificacapitolo"), @Mapping(source = "codLavoro", target = "codlavoro"),
			@Mapping(source = "codProgetto", target = "codprogetto"), @Mapping(source = "codTipoFinanz", target = "codtipofinanz"),
			@Mapping(source = "codTipoSpesa", target = "codtipospesa"), @Mapping(source = "copertoDaFPV", target = "copertofpv"),
			@Mapping(source = "creditoDebitoPredefinito", target = "creditodebitopredefinito"), @Mapping(source = "dataValiditaAl", target = "datavaliditaal"),
			@Mapping(source = "dataValiditaDal", target = "datavaliditadal"), @Mapping(source = "dataValuta", target = "datavaluta"),
			@Mapping(source = "idCapitolo", target = "idcapitolo"), @Mapping(source = "idFornitore", target = "idfornitore"),
			@Mapping(source = "siglaCPT", target = "siglacpt"), @Mapping(source = "siglaPianoFin", target = "siglapianofin") })
	it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta.Impegno.Dettaglio sicraDettaglioImpegnoToDettaglio(SicraDettaglioImpegno source);

	@Mappings(value = { @Mapping(source = "annoAtto", target = "annoatto"), @Mapping(source = "codAnnuale", target = "codannuale"),
			@Mapping(source = "dataAtto", target = "dataatto"), @Mapping(source = "dataRegistrazione", target = "dataregistrazione"),
			@Mapping(source = "flagNoDodicesimiImp", target = "flgNoDodicesimiImp"), @Mapping(source = "idImpegno", target = "idimpegno"),
			@Mapping(source = "idPrenotazionePartenza", target = "idprenotazionepartenza"), @Mapping(source = "numAtto", target = "numatto"),
			@Mapping(source = "tipoAtto", target = "tipoatto"), @Mapping(source = "annoAttoOri", target = "annoattoOri"),
			@Mapping(source = "dataAttoOri", target = "dataattoOri"), @Mapping(source = "numAttoOri", target = "numattoOri"),
			@Mapping(source = "tipoAttoOri", target = "tipoattoOri") })
	it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta.Impegno.Testata sicraTestataImpegnoToTestata(SicraTestataImpegno source);

	@Mappings(value = { @Mapping(source = "codannuale", target = "codAnnuale"), @Mapping(source = "idimpegno", target = "idImpegno") })
	SicraImpegnoSintetico impegnoToSicraImpegnoSintetico(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato.Impegno source);

	@Mappings(value = { @Mapping(source = "avvisi", target = "messaggiAvviso"), @Mapping(source = "errori", target = "messaggiErrore") })
	SicraMessaggio messaggiSetMovimentiAttoToSicraMessaggio(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato.Messaggi source);

	@Mappings(value = { @Mapping(source = "messaggi", target = "riscontro"), @Mapping(target = "esitoChiamata", ignore = true) })
	SicraOutputSetMovimentiAtto risultatoToSicraOutputSetMovimentiAtto(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato source);

	@Mappings(value = { @Mapping(source = "errore", target = "errori") })
	SicraMessaggiErrore erroriSetMovimentiAttoToSicraMessaggiErrore(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato.Messaggi.Errori source);

	@Mappings(value = { @Mapping(source = "avviso", target = "avvisi") })
	SicraMessaggiAvviso avvisiSetMovimentiAttoToSicraMessaggiAvviso(it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato.Messaggi.Avvisi source);

	/* SET ESECUTIVITA ATTO ********************************************************************************************/
	// default
	@Mappings(value = { @Mapping(target = "impegno.testata", ignore = true) })
	it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta sicraInputSetEsecutivitaAttoToRichiesta(SicraInputSetEsecutivitaAtto source);

	@Mappings(value = { @Mapping(source = "attoDefinitivo", target = "definitivo"), @Mapping(source = "parte", target = "EU"),
			@Mapping(source = "attoRicerca", target = "proposta") })
	it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta.Impegno.Testata sicraImpegnoDaRendereEsecutivoToTestata(
			SicraImpegnoDaRendereEsecutivo source);

	@Mappings(value = { @Mapping(source = "annoAtto", target = "annoatto"), @Mapping(source = "dataAtto", target = "dataatto"),
			@Mapping(source = "numAtto", target = "numatto") })
	it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta.Impegno.Testata.Proposta sicraAttoRicercaToProposta(SicraAttoRicerca source);
	
	@Mappings(value = { @Mapping(source = "annoAtto", target = "annoatto"), @Mapping(source = "dataAtto", target = "dataatto"),
			@Mapping(source = "numAtto", target = "numatto") })
	it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta.Impegno.Testata.Definitivo sicraAttoDefinitivoToDefinitivo(SicraAttoDefinitivo source);
	
	// default
	@Mappings(value = { @Mapping(target = "impegno", ignore = true), @Mapping(target = "riscontro", ignore = true),
			@Mapping(target = "esitoChiamata", ignore = true) })
	SicraOutputSetEsecutivitaAtto outputRisultatoToSicraOutputSetEsecutivitaAtto(
			Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> source);

	@Mappings(value = { @Mapping(source = "messaggi", target = "riscontro"), @Mapping(target = "esitoChiamata", ignore = true) })
	SicraOutputSetEsecutivitaAtto risultatoToSicraOutputSetEsecutivitaAtto(it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato source);

	SicraMessaggio messaggiSetEsecutivitaAttoToSicraMessaggio(it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato.Messaggi source);

	@Mappings(value = { @Mapping(source = "codannuale", target = "codAnnuale"), @Mapping(source = "idimpegno", target = "idImpegno") })
	SicraImpegnoSintetico impegnoToSicraImpegnoSintetico(it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato.Impegno source);

	/* ARCHIVIA ATTO ********************************************************************************************/
	@Mappings(value = { @Mapping(source = "annoAtto", target = "annoatto"), @Mapping(source = "dataAtto", target = "dataatto"),
			@Mapping(source = "numAtto", target = "numatto") })
	it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta.Atto sicraAttoArchiviazioneToAtto(SicraAttoArchiviazione source);

	@Mappings(value = { @Mapping(source = "parte", target = "EU") })
	it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta sicraInputArchiviaAttoToRichiesta(SicraInputArchiviaAtto source);

	SicraMessaggio messaggiArchiviaAttoToSicraMessaggio(it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato.Messaggi source);

	@Mappings(value = { @Mapping(source = "messaggi", target = "riscontro"), @Mapping(target = "esitoChiamata", ignore = true) })
	SicraOutputArchiviaAtto risultatoToSicraOutputArchiviaAtto(it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato source);

	// default
	@Mappings(value = { @Mapping(target = "riscontro", ignore = true), @Mapping(target = "esitoChiamata", ignore = true) })
	SicraOutputArchiviaAtto outputRisultatoToSicraOutputArchiviaAtto(Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> source);
	
	/* RICERCA ANAGRAFICA ********************************************************************************************/
	@Mappings(value = { @Mapping(source = "applicazione", target = "applicazione"), @Mapping(source = "codice", target = "codice"),
			@Mapping(source = "codiceFiscale", target = "codiceFiscale"), @Mapping(source = "codiceTrasco", target = "codiceTrasco"), 
			@Mapping(source = "dataFineAttivita", target = "dataFineAttivita"), @Mapping(source = "escludiDaBonificare", target = "escludiDaBonificare"), 
			@Mapping(source = "escludiNominativoFalso", target = "escludiNominativoFalso"), @Mapping(source = "escludiSoggettoIrriconoscibile", target = "escludiSoggettoIrriconoscibile"), 
			@Mapping(source = "numMaxRisultati", target = "numMaxRisultati"), @Mapping(source = "partitaIva", target = "partitaIva"), 
			@Mapping(source = "stringaRicerca", target = "stringaRicerca") })
	it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Richiesta sicraInputRicercaAnagraficaToRichiesta(SicraInputRicercaAnagrafica source);
	
	@Mappings(value = { @Mapping(target = "listaAnagrafiche", ignore = true), @Mapping(source = "outcome", target = "esitoChiamata") })
	SicraOutputRicercaAnagrafica outputRisultatoToSicraOutputRicercaAnagrafica(
			Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> source);
	
	/* RICERCA ATTO LIQUIDAZIONE ********************************************************************************************/
	@Mappings(value = { @Mapping(source = "numeroAtto", target = "numeroAtto"), @Mapping(source = "annoAtto", target = "annoAtto"),
			@Mapping(source = "siglaTipoAtto", target = "siglaTipoAtto"), @Mapping(source = "dataAtto", target = "dataAtto"), 
			@Mapping(source = "settoreAtto", target = "settoreAtto"), @Mapping(source = "tipoLiquidazione", target = "tipoLiquidazione") })
	it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Richiesta sicraInputRicercaAttoLiquidazioneToRichiesta(SicraInputRicercaAttoLiquidazione source);
	
	@Mappings(value = { @Mapping(target = "liquidazione", ignore = true), @Mapping(source = "outcome", target = "esitoChiamata") })
	SicraOutputRicercaAttoLiquidazione outputRisultatoToSicraOutputRicercaAttoLiquidazione(
			Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> source);
	
	/* RICERCA ORDINATIVI ATTO LIQUIDAZIONE ********************************************************************************************/
	@Mappings(value = { @Mapping(source = "numeroAtto", target = "numeroAtto"), @Mapping(source = "annoAtto", target = "annoAtto"),
			@Mapping(source = "siglaTipoAtto", target = "siglaTipoAtto"), @Mapping(source = "dataAtto", target = "dataAtto"), 
			@Mapping(source = "settoreAtto", target = "settoreAtto") })
	it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta sicraInputRicercaAnagraficaToRichiesta(SicraInputRicercaOrdinativoAttoLiquidazione source);
	
	@Mappings(value = { @Mapping(target = "liquidazione", ignore = true), @Mapping(source = "outcome", target = "esitoChiamata") })
	SicraOutputRicercaOrdinativoAttoLiquidazione outputRisultatoToSicraOutputRicercaOrdinativoAttoLiquidazione(
			Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> source);
	
	/* AGGIORNA RIF ATTO LIQUIDAZIONE ********************************************************************************************/
	@Mappings(value = { @Mapping(source = "numeroAtto", target = "numeroAtto"), @Mapping(source = "annoAtto", target = "annoAtto"),
			@Mapping(source = "siglaTipoAtto", target = "siglaTipoAtto"), @Mapping(source = "dataAtto", target = "dataAtto"),
			@Mapping(source = "settoreAtto", target = "settoreAtto") })
	it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta.Proposta sicraPropostaAttoLiquidazioneToProposta(SicraPropostaAttoLiquidazione source);
	
	@Mappings(value = { @Mapping(source = "numeroAtto", target = "numeroAtto"), @Mapping(source = "annoAtto", target = "annoAtto"),
			@Mapping(source = "siglaTipoAtto", target = "siglaTipoAtto"), @Mapping(source = "dataAtto", target = "dataAtto"),
			@Mapping(source = "settoreAtto", target = "settoreAtto") })
	it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta.AttoDefinitivo sicraAttoDefinitivoLiquidazioneToAttoDefinitivo(SicraAttoDefinitivoLiquidazione source);
	
	@Mappings(value = {@Mapping(target = "attoDefinitivo", ignore = true), @Mapping(target = "proposta", ignore = true)})
	it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta sicraInputAggiornaRifAttoLiquidazioneToRichiesta(SicraInputAggiornaRifAttoLiquidazione source);
	
	@Mappings(value = { @Mapping(target = "totLiquidazioniAggiornate", ignore = true), @Mapping(source = "outcome", target = "esitoChiamata") })
	SicraOutputAggiornaRifAttoLiquidazione outputRisultatoToSicraOutputAggiornaRifAttoLiquidazione(
			Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> source);
	
}// ContabilitaSicraMapper