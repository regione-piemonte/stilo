/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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

public interface ContabilitaSicra {

	SicraOutputRicercaVociBilancio sicraRicercaVociBilancio(SicraInputRicercaVociBilancio input);

	SicraOutputSetMovimentiAtto sicraSetMovimentiAtto(SicraInputSetMovimentiAtto input);

	SicraOutputSetMovimentiAtto sicraCancellazioneMovimentiAtto(SicraInputSetMovimentiAtto input);

	SicraOutputSetMovimentiAtto sicraInserimentoMovimentiAtto(SicraInputSetMovimentiAtto input);

	SicraOutputSetMovimentiAtto sicraAggiornamentoMovimentiAtto(SicraInputSetMovimentiAtto input);

	SicraOutputSetEsecutivitaAtto sicraSetEsecutivitaAtto(SicraInputSetEsecutivitaAtto input);

	SicraOutputArchiviaAtto sicraArchiviaAtto(SicraInputArchiviaAtto input);
	
	SicraOutputAggiornaRifAttoLiquidazione sicraAggiornaRifAttoLiquidazione(SicraInputAggiornaRifAttoLiquidazione input);
	
	SicraOutputRicercaAnagrafica sicraRicercaAnagrafica(SicraInputRicercaAnagrafica input);
	
	SicraOutputRicercaAttoLiquidazione sicraRicercaAttoLiquidazione(SicraInputRicercaAttoLiquidazione input);
	
	SicraOutputRicercaOrdinativoAttoLiquidazione sicraRicercaOrdinativoAttoLiquidazione(SicraInputRicercaOrdinativoAttoLiquidazione input);
	
}
