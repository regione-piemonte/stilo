/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import it.eng.document.function.bean.acta.ActaNodoSmistamento;
import it.eng.document.function.bean.acta.ActaEsitoChiamata;
import it.eng.utility.client.acta.bean.NodoSmistamento;
import it.eng.utility.data.Outcome;

public class EsportazioneDocActaMapperImpl {

	/* ================ DA BEAN CLIENT A BEAN AURIGA ====================================================================================== */


	public ActaNodoSmistamento nodoSmistamentoToActaNodoSmistamento(NodoSmistamento input) {
		final ActaNodoSmistamento bean = new ActaNodoSmistamento();
        bean.setCodiceNodo(input.getCodiceNodo());
        bean.setIdNodo(input.getIdNodo());
		return bean;
	}

	public List<ActaNodoSmistamento> nodiSmistamentoToActaNodiSmistamento(List<NodoSmistamento> input) {
		if (input == null)
			return null;
		final List<ActaNodoSmistamento> beans = new ArrayList<>(input.size());
		for (NodoSmistamento nodoSmistamento : input) {
			beans.add(nodoSmistamentoToActaNodoSmistamento(nodoSmistamento));
		}
		return beans;
	}
	
    public ActaEsitoChiamata outcomeToEsitoChiamata(Outcome esito) {
        if ( esito == null ) {
            return null;
        }

        ActaEsitoChiamata esitoChiamata = new ActaEsitoChiamata();

        esitoChiamata.setMessaggio( esito.getMessaggio() );
        esitoChiamata.setOk( esito.isOk() );
        esitoChiamata.setRispostaNonRicevuta( esito.isRispostaNonRicevuta() );
        esitoChiamata.setTimeout( esito.isTimeout() );

        return esitoChiamata;
    }



}