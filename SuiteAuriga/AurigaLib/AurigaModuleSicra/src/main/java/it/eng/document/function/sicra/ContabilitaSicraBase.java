/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.data.Output;

public interface ContabilitaSicraBase {

	Output<it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Risultato> ricercaVociBilancio(
			it.eng.utility.sicra.contabilita.xsd.ricerca_voci_bilancio.Richiesta richiesta);

	Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> setMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta);

	Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> cancellazioneMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta);

	Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> inserimentoMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta);

	Output<it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Risultato> aggiornamentoMovimentiAtto(
			it.eng.utility.sicra.contabilita.xsd.set_movimenti_atto.Richiesta richiesta);

	Output<it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Risultato> setEsecutivitaAtto(
			it.eng.utility.sicra.contabilita.xsd.set_esecutivita_atto.Richiesta richiesta);

	Output<it.eng.utility.sicra.contabilita.xsd.archivia_atto.Risultato> archiviaAtto(it.eng.utility.sicra.contabilita.xsd.archivia_atto.Richiesta richiesta);
	
	Output<it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Risultato> aggiornaRifAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.aggiorna_rif_atto_liquidazione.Richiesta richiesta);
	
	Output<it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Risultato> ricercaAnagrafica(
			it.eng.utility.sicra.contabilita.xsd.ricerca_anagrafica.Richiesta richiesta);
	
	Output<it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Risultato> ricercaAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.ricerca_atto_liquidazione.Richiesta richiesta);
	
	Output<it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Risultato> ricercaOrdinativoAttoLiquidazione(
			it.eng.utility.sicra.contabilita.xsd.ricerca_ordinativo_atto_liquidazione.Richiesta richiesta);
}
