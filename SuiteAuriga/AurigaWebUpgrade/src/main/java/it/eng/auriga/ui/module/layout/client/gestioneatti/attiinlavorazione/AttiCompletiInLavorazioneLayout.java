/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiCompletiLayout;
import it.eng.auriga.ui.module.layout.client.gestioneatti.AttiDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;

/**
 * Visualizza la lista degli atti in lavorazione completi
 * 
 * @author Dancrist
 *
 */
public class AttiCompletiInLavorazioneLayout extends AttiCompletiLayout {

	public AttiCompletiInLavorazioneLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}

	public AttiCompletiInLavorazioneLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	public AttiCompletiInLavorazioneLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}

	public AttiCompletiInLavorazioneLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super(	nomeEntita,
				new GWTRestDataSource("AttiCompletiInLavorazioneDataSource", "idProcedimento", FieldType.TEXT), 
				createFilter(nomeEntita), 
				new AttiCompletiInLavorazioneList(nomeEntita),
				new AttiDetail(nomeEntita), finalita, flgSelezioneSingola, showOnlyDetail);
	}

	private static AttiCompletiInLavorazioneFilter createFilter(String nomeEntita) {

		Map<String, String> extraParam = new HashMap<String, String>();

		extraParam.put("showFilterDeterminaContrarreConGara", Boolean.toString(AurigaLayout.isAttivoClienteCMTO()));
		extraParam.put("showFilterTipoAffidamento", Boolean.toString(AurigaLayout.isAttivoClienteCMTO()));
		extraParam.put("showFilterDeterminaRimodulazioneSpesaPostAggiudica", Boolean.toString(AurigaLayout.isAttivoClienteCMTO()));
		extraParam.put("showFilterMaterieTipiAtto", Boolean.toString(AurigaLayout.isAttivoClienteCMTO()));
		extraParam.put("showFilterLiquidazioneContestualeImpegni", Boolean.toString(AurigaLayout.isAttivoClienteCMTO()));
		extraParam.put("showFilterLiquidazioneContestualeAltriAspettiCont", Boolean.toString(AurigaLayout.isAttivoClienteCMTO()));
		extraParam.put("showFilterDeterminaAggiudicaGara", Boolean.toString(AurigaLayout.isAttivoClienteCMTO()));
		extraParam.put("showFilterSmistamentoAtti", Boolean.toString(isAttivoSmistamentoAtti()));
		extraParam.put("showFilterCentroDiCosto", Boolean.toString(AurigaLayout.isAttivoClienteCOTO()));
		extraParam.put("showFilterDataScadenza", Boolean.toString(AurigaLayout.getParametroDBAsBoolean("ATTIVO_ITER_LIQUIDAZIONI")));
		extraParam.put("showFilterUoCompetente", Boolean.toString(AurigaLayout.getParametroDBAsBoolean("ATTIVA_UO_COMPETENTE_ATTI_GARE")));
		extraParam.put("showFilterPresenzaOpere", Boolean.toString(AurigaLayout.isAttivoClienteADSP()));
		extraParam.put("showFilterOpera", Boolean.toString(AurigaLayout.isAttivoClienteADSP()));
		extraParam.put("showFilterSottoTipologiaAtto", Boolean.toString(AurigaLayout.isAttivoClienteADSP()));
		extraParam.put("showFilterProgrammazioneAcquisti", Boolean.toString(AurigaLayout.isAttivoClienteCOTO()));
		extraParam.put("showFilterCui", Boolean.toString(AurigaLayout.isAttivoClienteCOTO()));
		
		return new AttiCompletiInLavorazioneFilter(nomeEntita, extraParam);
	}
}