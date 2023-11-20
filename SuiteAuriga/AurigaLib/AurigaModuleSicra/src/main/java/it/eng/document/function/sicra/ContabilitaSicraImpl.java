/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.document.function.bean.sicra.SicraInputAggiornaRifAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputArchiviaAtto;
import it.eng.document.function.bean.sicra.SicraInputRicercaAnagrafica;
import it.eng.document.function.bean.sicra.SicraInputRicercaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputRicercaOrdinativoAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraInputRicercaVociBilancio;
import it.eng.document.function.bean.sicra.SicraInputSetEsecutivitaAtto;
import it.eng.document.function.bean.sicra.SicraInputSetMovimentiAtto;
import it.eng.document.function.bean.sicra.SicraOutputAggiornaRifAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputArchiviaAtto;
import it.eng.document.function.bean.sicra.SicraOutputRicercaAnagrafica;
import it.eng.document.function.bean.sicra.SicraOutputRicercaAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputRicercaOrdinativoAttoLiquidazione;
import it.eng.document.function.bean.sicra.SicraOutputRicercaVociBilancio;
import it.eng.document.function.bean.sicra.SicraOutputSetEsecutivitaAtto;
import it.eng.document.function.bean.sicra.SicraOutputSetMovimentiAtto;
import it.eng.utility.data.Output;

@Service(name = "ContabilitaSicraImpl")
public class ContabilitaSicraImpl implements ContabilitaSicra {

	// private static final Logger logger = Logger.getLogger(ContabilitaSicraImpl.class);
	private final ContabilitaSicraMapper mapper;
	private final ContabilitaSicraBase service;

	public ContabilitaSicraImpl() {
		this.mapper = ContabilitaSicraMapper.INSTANCE;
		this.service = new ContabilitaSicraBaseImpl();
	}

	@Operation(name = "sicraRicercaVociBilancio")
	public SicraOutputRicercaVociBilancio sicraRicercaVociBilancio(SicraInputRicercaVociBilancio input) {
		final it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Richiesta in = mapper.sicraInputRicercaVociBilancioToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> out = service.ricercaVociBilancio(in);
		final SicraOutputRicercaVociBilancio output = mapper.outputRisultatoToSicraOutputRicercaVociBilancio(out);
		return output;
	}

	@Operation(name = "sicraSetMovimentiAtto")
	public SicraOutputSetMovimentiAtto sicraSetMovimentiAtto(SicraInputSetMovimentiAtto input) {
		final it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta in = mapper.sicraInputSetMovimentiAttoToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> out = service.setMovimentiAtto(in);
		final SicraOutputSetMovimentiAtto output = mapper.outputRisultatoToSicraOutputSetMovimentiAtto(out);
		return output;
	}

	@Operation(name = "sicraCancellazioneMovimentiAtto")
	public SicraOutputSetMovimentiAtto sicraCancellazioneMovimentiAtto(SicraInputSetMovimentiAtto input) {
		final it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta in = mapper.sicraInputSetMovimentiAttoToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> out = service.cancellazioneMovimentiAtto(in);
		final SicraOutputSetMovimentiAtto output = mapper.outputRisultatoToSicraOutputSetMovimentiAtto(out);
		return output;
	}

	@Operation(name = "sicraInserimentoMovimentiAtto")
	public SicraOutputSetMovimentiAtto sicraInserimentoMovimentiAtto(SicraInputSetMovimentiAtto input) {
		final it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta in = mapper.sicraInputSetMovimentiAttoToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> out = service.inserimentoMovimentiAtto(in);
		final SicraOutputSetMovimentiAtto output = mapper.outputRisultatoToSicraOutputSetMovimentiAtto(out);
		return output;
	}

	@Operation(name = "sicraAggiornamentoMovimentiAtto")
	public SicraOutputSetMovimentiAtto sicraAggiornamentoMovimentiAtto(SicraInputSetMovimentiAtto input) {
		final it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta in = mapper.sicraInputSetMovimentiAttoToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> out = service.aggiornamentoMovimentiAtto(in);
		final SicraOutputSetMovimentiAtto output = mapper.outputRisultatoToSicraOutputSetMovimentiAtto(out);
		return output;
	}

	@Operation(name = "sicraSetEsecutivitaAtto")
	public SicraOutputSetEsecutivitaAtto sicraSetEsecutivitaAtto(SicraInputSetEsecutivitaAtto input) {
		final it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta in = mapper.sicraInputSetEsecutivitaAttoToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> out = service.setEsecutivitaAtto(in);
		final SicraOutputSetEsecutivitaAtto output = mapper.outputRisultatoToSicraOutputSetEsecutivitaAtto(out);
		return output;
	}

	@Operation(name = "sicraArchiviaAtto")
	public SicraOutputArchiviaAtto sicraArchiviaAtto(SicraInputArchiviaAtto input) {
		final it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta in = mapper.sicraInputArchiviaAttoToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> out = service.archiviaAtto(in);
		final SicraOutputArchiviaAtto output = mapper.outputRisultatoToSicraOutputArchiviaAtto(out);
		return output;
	}
	
	@Operation(name = "sicraAggiornaRifAttoLiquidazione")
	public SicraOutputAggiornaRifAttoLiquidazione sicraAggiornaRifAttoLiquidazione(SicraInputAggiornaRifAttoLiquidazione input) {
		final it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta in = mapper.sicraInputAggiornaRifAttoLiquidazioneToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> out = service.aggiornaRifAttoLiquidazione(in);
		final SicraOutputAggiornaRifAttoLiquidazione output = mapper.outputRisultatoToSicraOutputAggiornaRifAttoLiquidazione(out);
		return output;
	}
	
	@Operation(name = "sicraRicercaAnagrafica")
	public SicraOutputRicercaAnagrafica sicraRicercaAnagrafica(SicraInputRicercaAnagrafica input) {
		final it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Richiesta in = mapper.sicraInputRicercaAnagraficaToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> out = service.ricercaAnagrafica(in);
		final SicraOutputRicercaAnagrafica output = mapper.outputRisultatoToSicraOutputRicercaAnagrafica(out);
		return output;
	}
	
	@Operation(name = "sicraRicercaAttoLiquidazione")
	public SicraOutputRicercaAttoLiquidazione sicraRicercaAttoLiquidazione(SicraInputRicercaAttoLiquidazione input) {
		final it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Richiesta in = mapper.sicraInputRicercaAttoLiquidazioneToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> out = service.ricercaAttoLiquidazione(in);
		final SicraOutputRicercaAttoLiquidazione output = mapper.outputRisultatoToSicraOutputRicercaAttoLiquidazione(out);
		return output;
	}
	
	@Operation(name = "sicraRicercaOrdinativoAttoLiquidazione")
	public SicraOutputRicercaOrdinativoAttoLiquidazione sicraRicercaOrdinativoAttoLiquidazione(SicraInputRicercaOrdinativoAttoLiquidazione input) {
		final it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta in = mapper.sicraInputRicercaAnagraficaToRichiesta(input);
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> out = service.ricercaOrdinativoAttoLiquidazione(in);
		final SicraOutputRicercaOrdinativoAttoLiquidazione output = mapper.outputRisultatoToSicraOutputRicercaOrdinativoAttoLiquidazione(out);
		return output;
	}

}// ContabilitaSicraImpl