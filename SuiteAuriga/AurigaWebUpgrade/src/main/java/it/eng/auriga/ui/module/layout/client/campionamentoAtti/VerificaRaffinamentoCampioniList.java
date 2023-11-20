/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.utility.ui.module.layout.client.common.CustomList;

/**
 * 
 * @author matzanin
 *
 */

public class VerificaRaffinamentoCampioniList extends CustomList {

	private ListGridField codiceAttoStruttura;
	private ListGridField desCodiceAttoStruttura;
	private ListGridField flgCodiceAttoParticolare;
	private ListGridField idUoProponente;	
	private ListGridField numeroAttiAdottati;
	private ListGridField percentualeCampionamento;
	private ListGridField numeroAttiEstrattiXControllo;
	
	public VerificaRaffinamentoCampioniList(String nomeEntita) {
		
		super(nomeEntita);
		
		codiceAttoStruttura = new ListGridField("codiceAttoStruttura");
		codiceAttoStruttura.setHidden(true);
		codiceAttoStruttura.setCanHide(false);
		codiceAttoStruttura.setCanSort(false);
		codiceAttoStruttura.setCanGroupBy(false);
		
		desCodiceAttoStruttura = new ListGridField("desCodiceAttoStruttura", "Cod. atto/Struttura");
		
		flgCodiceAttoParticolare = new ListGridField("flgCodiceAttoParticolare", "Tipo atto particolare");		
			
		idUoProponente = new ListGridField("idUoProponente");
		idUoProponente.setHidden(true);
		idUoProponente.setCanHide(false);
		idUoProponente.setCanSort(false);
		idUoProponente.setCanGroupBy(false);
		
		numeroAttiAdottati = new ListGridField("numeroAttiAdottati", "N° atti adottati");
		numeroAttiAdottati.setType(ListGridFieldType.INTEGER);
		
		percentualeCampionamento = new ListGridField("percentualeCampionamento", "% atti estratti per controllo");
		// con virgola e due cifre decimali
		percentualeCampionamento.setType(ListGridFieldType.FLOAT);
		percentualeCampionamento.setCellFormatter(new CellFormatter() {
			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return NumberFormatUtility.getFormattedValue(record.getAttribute("percentualeCampionamento"));				
			}
		});
		percentualeCampionamento.setSortNormalizer(new SortNormalizer() {
			
			@Override
			public Object normalize(ListGridRecord record, String fieldName) {
				String pattern = "#,##0.00";
				float floatValue = 0;
				if(record.getAttribute("percentualeCampionamento") != null && !"".equals(record.getAttribute("percentualeCampionamento"))) {
					floatValue = new Float(NumberFormat.getFormat(pattern).parse((String) record.getAttribute("percentualeCampionamento"))).floatValue();			
				}
				return floatValue;
			}
		});
		
		numeroAttiEstrattiXControllo = new ListGridField("numeroAttiEstrattiXControllo", "N° atti estratti per controllo");
		numeroAttiEstrattiXControllo.setType(ListGridFieldType.INTEGER);
				
		setFields(
			codiceAttoStruttura,
			desCodiceAttoStruttura,
			flgCodiceAttoParticolare,
			idUoProponente,
			numeroAttiAdottati,
			percentualeCampionamento,
			numeroAttiEstrattiXControllo
		);

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