/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Arrays;
import java.util.HashSet;

import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author matzanin
 *
 */

public class RegoleCampionamentoList extends CustomList {

	private ListGridField tipologiaAtto;
//	private ListGridField codiceAtto;
	private ListGridField flgCodiceAttoParticolare;
	private ListGridField flgDeterminaAContrarre;
//	private ListGridField strutturaProponente;
	private ListGridField rangeImporto;
	private ListGridField percentualeCampionamento;
	private ListGridField idRegola;
	private ListGridField dataDecorrenzaRegola;

	public RegoleCampionamentoList(String nomeEntita) {
		
		super(nomeEntita);
		
		tipologiaAtto = new ListGridField("tipologiaAtto", "Tipologia atto");
		
//		codiceAtto = new ListGridField("codiceAtto", "Cod. atto");
		
		flgCodiceAttoParticolare = new ListGridField("flgCodiceAttoParticolare", "Tipo atto particolare");		
		
		flgDeterminaAContrarre = new ListGridField("flgDeterminaAContrarre", "Det. a contrarre");
		
//		strutturaProponente = new ListGridField("strutturaProponente", "Struttura");
		
		rangeImporto = new ListGridField("rangeImporto", "Range importo");
		
		percentualeCampionamento = new ListGridField("percentualeCampionamento", "% camp.");
		percentualeCampionamento.setType(ListGridFieldType.INTEGER);
		
		idRegola = new ListGridField("idRegola", "Id. regola");
		
		dataDecorrenzaRegola = new ListGridField("dataDecorrenzaRegola", "Decorrenza regola");
		dataDecorrenzaRegola.setType(ListGridFieldType.DATE);
		dataDecorrenzaRegola.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		dataDecorrenzaRegola.setWrap(false);
		
		setFields(
			tipologiaAtto,
//			codiceAtto,
			flgCodiceAttoParticolare,
			flgDeterminaAContrarre,
//			strutturaProponente,
			rangeImporto,
			percentualeCampionamento,
			idRegola,
			dataDecorrenzaRegola
		);

	}

	@Override
	protected String getBaseStyle(ListGridRecord record, int rowNum, int colNum) {
		boolean isIdRegolaValorizzata = record.getAttribute("idRegola") != null && !"".equals(record.getAttribute("idRegola").trim());
		boolean isDataDecorrenzaRegolaValorizzata = record.getAttribute("dataDecorrenzaRegola") != null && !"".equals(record.getAttribute("dataDecorrenzaRegola").trim());
		boolean isPercentualeCampionamentoValorizzata = record.getAttribute("percentualeCampionamento") != null && !"".equals(record.getAttribute("percentualeCampionamento").trim());
		if(isIdRegolaValorizzata && isDataDecorrenzaRegolaValorizzata && isPercentualeCampionamentoValorizzata) {
			if(record.getAttribute("colonneRegola") != null && !"".equals(record.getAttribute("colonneRegola"))) {
				StringSplitterClient st = new StringSplitterClient(record.getAttribute("colonneRegola"), "|*|");
				HashSet<String> setColonneRegola = new HashSet<String>(Arrays.asList(st.getTokens()));
				if (setColonneRegola.contains(getFieldName(colNum))) {  
					return it.eng.utility.Styles.cellVariazione; 		             
		        }
			}			
		}
		return super.getBaseStyle(record, rowNum, colNum);
	}	
	
	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	}

	@Override
	protected boolean showDetailButtonField() {
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {
		return false;
	}

	@Override
	protected boolean showDeleteButtonField() {
		return false;
	}

	@Override
	protected void manageDetailButtonClick(ListGridRecord record) {
		// da lasciare vuoto, per evitare che il click sul record della lista apra il dettaglio 
	}
	
	@Override
	protected void manageModifyButtonClick(ListGridRecord record) {
		
	}
	
	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	
}