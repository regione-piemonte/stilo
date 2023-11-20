/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;

/**
 * 
 * @author DANCRIST
 *
 */

public class ProcedimentiCollegatiList extends ListGrid {
	
	private ListGridField idProcessField;		
	private ListGridField descrizioneField; 
	private ListGridField detailField;
	
	public ProcedimentiCollegatiList(String nomeEntita) {
		super();
		
		setWidth100();
		setHeight100();  
		setShowHeader(true);
		setAlternateRecordStyles(true);
		setLeaveScrollbarGap(false);		
		setKeepInParentRect(true);
		setLoadingMessage(null);
		setDataFetchMode(FetchMode.LOCAL);		
		setShowEmptyMessage(false);
		setShowRollOver(false);		
		setCanReorderFields(false);
		setCanResizeFields(true);
		setCanReorderRecords(false);
		setCanHover(true);		
		setCanGroupBy(false);	
		setCanSort(false);
		setShowHeaderContextMenu(false);
		setNoDoubleClicks(true); 
		setVirtualScrolling(true);      
		setSelectionType(SelectionStyle.NONE);
		setCanAdaptWidth(true);
		
		idProcessField = new ListGridField("idProcess", "Id.");                      
		idProcessField.setHidden(true);
		
		descrizioneField = new ListGridField("descrizione", "Descrizione");  
		
		detailField = new ListGridField("dettaglio", "");
		detailField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		detailField.setType(ListGridFieldType.ICON);
		detailField.setWidth(30);
		detailField.setIconWidth(16);
		detailField.setIconHeight(16);
		detailField.setAlign(Alignment.CENTER);
		detailField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				return buildImgButtonHtml("buttons/detail.png");					
			}
		});
		detailField.setHoverCustomizer(new HoverCustomizer() {		
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
								
				return "Dettaglio procedimento";
			}
		});
		detailField.addRecordClickHandler(new RecordClickHandler() {
			
			@Override
			public void onRecordClick(RecordClickEvent event) {
				
				AurigaLayout.apriDettaglioPratica(event.getRecord().getAttribute("idProcess"), event.getRecord().getAttribute("descrizione"), new BooleanCallback() {

					@Override
					public void execute(Boolean isSaved) {
						
					}
				});
			}
		});
		
		setFields(idProcessField, descrizioneField, detailField);

	}
	
	protected String buildImgButtonHtml(String src) {
		return "<div style=\"cursor:pointer\" align=\"center\"><img src=\"images/" + src + "\" height=\"16\" width=\"16\" alt=\"\" /></div>";		
	}
	
	@Override
	public boolean canEditCell(int rowNum, int colNum) {
		return false;
	}
	
}