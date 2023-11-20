/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class OggettiElaboratiList extends CustomList {
	
	public OggettiElaboratiList(String nomeEntita) {
		
		super(nomeEntita);		
		
		// Colonne visibili	
		ListGridField tipoOggettoElaboratoField 	   = new ListGridField("tipoOggettoElaborato",       I18NUtil.getMessages().oggetti_elaborati_list_tipoOggettoElaboratoField_title());
		ListGridField idOggettoElaboratoField 		   = new ListGridField("idOggettoElaborato", 	     I18NUtil.getMessages().oggetti_elaborati_list_idOggettoElaboratoField_title());
		ListGridField dataUltimaElaborazioneField 	   = new ListGridField("dataUltimaElaborazione", 	 I18NUtil.getMessages().oggetti_elaborati_list_dataUltimaElaborazioneField_title());              dataUltimaElaborazioneField.setType(ListGridFieldType.DATE);       dataUltimaElaborazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);			
		ListGridField codiceErroreElaborazioneField    = new ListGridField("codiceErroreElaborazione", 	 I18NUtil.getMessages().oggetti_elaborati_list_codiceErroreElaborazioneField_title());			
		ListGridField descErroreElaborazioneField      = new ListGridField("descErroreElaborazione", 	 I18NUtil.getMessages().oggetti_elaborati_list_descErroreElaborazioneField_title());
		ListGridField numeroElaborazioniField          = new ListGridField("numeroElaborazioni", 	     I18NUtil.getMessages().oggetti_elaborati_list_numeroElaborazioniField_title());
		ListGridField estremiOggettoElaboratoField     = new ListGridField("estremiOggettoElaborato", 	 I18NUtil.getMessages().oggetti_elaborati_list_estremiOggettoElaboratoField_title());
		ListGridField esitoElaborazioneField           = new ListGridField("esitoElaborazione", 		 I18NUtil.getMessages().oggetti_elaborati_list_esitoElaborazioneField_title()); 
		esitoElaborazioneField.setHeaderBaseStyle("hiddenHeaderButton"); 
		esitoElaborazioneField.setType(ListGridFieldType.ICON);
		esitoElaborazioneField.setWidth(30);
		esitoElaborazioneField.setIconWidth(16);
		esitoElaborazioneField.setIconHeight(16);
		Map<String, String> esitoElaborazioneValueIcons = new HashMap<String, String>();
		esitoElaborazioneValueIcons.put("OK", "ok.png");
		esitoElaborazioneValueIcons.put("KO", "message/error.png");
		esitoElaborazioneValueIcons.put(""  , "blank.png");		
		esitoElaborazioneField.setValueIcons(esitoElaborazioneValueIcons);
		esitoElaborazioneField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				// TODO Auto-generated method stub
				if("OK".equals(record.getAttribute("esitoElaborazione")) ) {
					return I18NUtil.getMessages().oggetti_elaborati_list_esitoElaborazioneField_esito_OK_value();
				}				
				
				if("KO".equals(record.getAttribute("esitoElaborazione"))) {
					return I18NUtil.getMessages().oggetti_elaborati_list_esitoElaborazioneField_esito_KO_value();
				}
				
				return null;
			}
		});
		
		recordClickHandler.removeHandler();
		
		setFields(new ListGridField[] {
				                        tipoOggettoElaboratoField,
				                        idOggettoElaboratoField,
				                        dataUltimaElaborazioneField,
				                        codiceErroreElaborazioneField,
				                        descErroreElaborazioneField,
				                        numeroElaborazioniField,
				                        estremiOggettoElaboratoField,
				                        esitoElaborazioneField 
		                             }
				  );  				  
	}	
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
	
	// Definisco i bottoni DETTAGLIO/MODIFICA/CANCELLA/SELEZIONA
		@Override  
		protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {
			Canvas lCanvasReturn = null;
			if (fieldName.equals("buttons")) {	
				ImgButton detailButton = buildDetailButton(record);  
				ImgButton modifyButton = buildModifyButton(record);  
				ImgButton deleteButton = buildDeleteButton(record);  			
				ImgButton lookupButton = buildLookupButton(record);						
				if(!isRecordAbilToView(record)) {	
					detailButton.disable();			
				}			
				if(!isRecordAbilToMod(record)) {	
					modifyButton.disable();			
				}			
				if(!isRecordAbilToDel(record)) {	
					deleteButton.disable();			
				}
				// creo la colonna BUTTON
				HLayout recordCanvas = new HLayout(3);
				recordCanvas.setHeight(22);   
				recordCanvas.setWidth(getButtonsFieldWidth());
				recordCanvas.setAlign(Alignment.RIGHT);
				recordCanvas.setLayoutRightMargin(3);   
				recordCanvas.setMembersMargin(7);
				recordCanvas.addMember(detailButton);			
				recordCanvas.addMember(modifyButton);
				recordCanvas.addMember(deleteButton);			
				if(layout.isLookup()) {
					if(!isRecordSelezionabileForLookup(record)) {
						lookupButton.disable();
					}
					recordCanvas.addMember(lookupButton);		// aggiungo il bottone SELEZIONA				
				}
				lCanvasReturn = recordCanvas;	
			}			
			return lCanvasReturn;
		}
		
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {
		return OggettiElaboratiLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return OggettiElaboratiLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToView(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		return OggettiElaboratiLayout.isRecordAbilToView(recProtetto);
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		return OggettiElaboratiLayout.isRecordAbilToMod(recProtetto);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equalsIgnoreCase("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equalsIgnoreCase("1");
		return OggettiElaboratiLayout.isRecordAbilToDel(flgValido, recProtetto);	
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}