/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiDetail;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiLayout;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

/**
 * Visualizza la lista degli atti in lavorazione
 * 
 * @author massimo malvestio
 *
 */
public class AttiInLavorazioneLayout extends AttiLayout {

	public AttiInLavorazioneLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public AttiInLavorazioneLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public AttiInLavorazioneLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public AttiInLavorazioneLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(nomeEntita, new GWTRestDataSource("AttiInLavorazioneDatasource", "idProcedimento", FieldType.TEXT), createFilter(nomeEntita), new AttiInLavorazioneList(nomeEntita),
				new AttiDetail(nomeEntita), finalita, flgSelezioneSingola, showOnlyDetail);
	}

	private static AttiInLavorazioneFilter createFilter(String nomeEntita) {

		Map<String, String> extraParam = new HashMap<String, String>();

		extraParam.put("showFilterSmistamentoAtti", Boolean.toString(isAttivoSmistamentoAtti()));

		return new AttiInLavorazioneFilter(nomeEntita, extraParam);
	}
}