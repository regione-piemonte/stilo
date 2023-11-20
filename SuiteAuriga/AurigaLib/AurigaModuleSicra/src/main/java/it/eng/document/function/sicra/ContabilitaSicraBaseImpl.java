/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.data.Output;
import it.eng.utility.sicra.contabilita.ClientSicra;
import it.eng.utility.sicra.contabilita.ClientSpringSicra;

public class ContabilitaSicraBaseImpl implements ContabilitaSicraBase {

	private final ClientSicra clientSicra;

	public ContabilitaSicraBaseImpl() {
		this.clientSicra = ClientSpringSicra.getClient();
	}

	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> ricercaVociBilancio(
			it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> output = clientSicra.ricercaVociBilancio(richiesta);
		return output;
	}

	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> setMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> output = clientSicra.setMovimentiAtto(richiesta);
		return output;
	}

	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> cancellazioneMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> output = clientSicra.cancellazioneMovimentiAtto(richiesta);
		return output;
	}

	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> inserimentoMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> output = clientSicra.inserimentoMovimentiAtto(richiesta);
		return output;
	}

	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> aggiornamentoMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> output = clientSicra.aggiornamentoMovimentiAtto(richiesta);
		return output;
	}

	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> setEsecutivitaAtto(
			it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> output = clientSicra.setEsecutivitaAtto(richiesta);
		return output;
	}

	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> archiviaAtto(
			it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> output = clientSicra.archiviaAtto(richiesta);
		return output;
	}
	
	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> aggiornaRifAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> output = clientSicra.aggiornaRifAttoLiquidazione(richiesta);
		return output;
	}
	
	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> ricercaAnagrafica(
			it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> output = clientSicra.ricercaAnagrafica(richiesta);
		return output;
	}
	
	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> ricercaAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> output = clientSicra.ricercaAttoLiquidazione(richiesta);
		return output;
	}
	
	@Override
	public Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> ricercaOrdinativoAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta richiesta) {
		final Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> output = clientSicra.ricercaOrdinativoAttoLiquidazione(richiesta);
		return output;
	}
	
}
