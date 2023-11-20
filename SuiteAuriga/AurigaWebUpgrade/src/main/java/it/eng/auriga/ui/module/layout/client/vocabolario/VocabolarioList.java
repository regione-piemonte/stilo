/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class VocabolarioList extends CustomList{
	
	private ListGridField valoreField;
	private ListGridField codiceValoreField;
	private ListGridField dtInizioValiditaField;
	private ListGridField dtFineValiditaField;
	private ListGridField significatoValoreField;
	private ListGridField valueGenVincoloField;
	private ListGridField flgValiditaValoreField;
	private ListGridField vocabolarioDiField;
	private ListGridField flgVisibSottoUoField;
	private ListGridField flgGestSottoUoField;

	public VocabolarioList(String nomeEntita) {
		super(nomeEntita);
		
		valoreField = new ListGridField("valore",I18NUtil.getMessages().vocabolario_valore());
		
		codiceValoreField = new ListGridField("codiceValore",I18NUtil.getMessages().vocabolario_codiceValore());
		
		dtInizioValiditaField = new ListGridField("dtInizioValidita",I18NUtil.getMessages().vocabolario_dtInizioValidita());
		dtInizioValiditaField.setType(ListGridFieldType.DATE);
		dtInizioValiditaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		dtFineValiditaField = new ListGridField("dtFineValidita",I18NUtil.getMessages().vocabolario_dtFineValidita());
		dtFineValiditaField.setType(ListGridFieldType.DATE);
		dtFineValiditaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		
		significatoValoreField = new ListGridField("significatoValore",I18NUtil.getMessages().vocabolario_significatoValore());
		
		valueGenVincoloField = new ListGridField("valueGenVincolo",I18NUtil.getMessages().vocabolario_valueGenVincolo());
	
		flgValiditaValoreField = new ListGridField("flgValiditaValore",I18NUtil.getMessages().vocabolario_flgValiditaValore());
		flgValiditaValoreField.setType(ListGridFieldType.ICON);
		flgValiditaValoreField.setWidth(30);
		flgValiditaValoreField.setIconWidth(16);
		flgValiditaValoreField.setIconHeight(16);
		Map<String, String> flgTipoIcons = new HashMap<String, String>();		
		flgTipoIcons.put("1", "ok.png");
		flgTipoIcons.put("0", "blank.png");
		flgValiditaValoreField.setValueIcons(flgTipoIcons);
		flgValiditaValoreField.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if("1".equals(record.getAttribute("flgValiditaValore"))) {
					return "Voce valida";
				}				
				return null;
			}
		});
		
		if (VocabolarioLayout.isPartizionamentoVocabolarioAbilitato()) {
			
			vocabolarioDiField = new ListGridField("vocabolarioDi", I18NUtil.getMessages().vocabolario_vocabolarioDi());

			flgVisibSottoUoField = new ListGridField("flgVisibSottoUo", I18NUtil.getMessages().vocabolario_flgVisibSottoUo());
			flgVisibSottoUoField.setType(ListGridFieldType.ICON);
			flgVisibSottoUoField.setWidth(30);
			flgVisibSottoUoField.setIconWidth(16);
			flgVisibSottoUoField.setIconHeight(16);
			Map<String, String> flgVisibSottoUoFieldIcons = new HashMap<String, String>();
			flgVisibSottoUoFieldIcons.put("1", "ok.png");
			flgVisibSottoUoFieldIcons.put("0", "blank.png");
			flgVisibSottoUoFieldIcons.put("", "blank.png");
			flgVisibSottoUoField.setValueIcons(flgVisibSottoUoFieldIcons);

			flgGestSottoUoField = new ListGridField("flgGestSottoUo", I18NUtil.getMessages().vocabolario_flgGestSottoUo());
			flgGestSottoUoField.setType(ListGridFieldType.ICON);
			flgGestSottoUoField.setWidth(30);
			flgGestSottoUoField.setIconWidth(16);
			flgGestSottoUoField.setIconHeight(16);
			Map<String, String> flgGestSottoUoFieldIcons = new HashMap<String, String>();
			flgGestSottoUoFieldIcons.put("1", "ok.png");
			flgGestSottoUoFieldIcons.put("0", "blank.png");
			flgGestSottoUoFieldIcons.put("", "blank.png");
			flgGestSottoUoField.setValueIcons(flgGestSottoUoFieldIcons);

			setFields(
					valoreField,
					codiceValoreField,
					significatoValoreField,
					dtInizioValiditaField,
					dtFineValiditaField,
					valueGenVincoloField,
					flgValiditaValoreField,
					vocabolarioDiField,
					flgVisibSottoUoField,
					flgGestSottoUoField
			);
			
		} else {
			
			setFields(
					valoreField,
					codiceValoreField,
					significatoValoreField,
					dtInizioValiditaField,
					dtFineValiditaField,
					valueGenVincoloField,
					flgValiditaValoreField					
			);
			
		}		
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
	
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);	
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		}, new DSRequest());	
	}
	
	@Override
	protected Canvas createFieldCanvas(String fieldName, final ListGridRecord record) {

		Canvas lCanvasReturn = null;

		if (fieldName.equals("buttons")) {

			ImgButton detailButton = buildDetailButton(record);  
			ImgButton modifyButton = buildModifyButton(record);
			ImgButton deleteButton = buildDeleteButton(record);			
			ImgButton lookupButton = buildLookupButton(record);

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
		return true;
	} 
        
    @Override
	protected boolean showModifyButtonField() {
		return VocabolarioLayout.isAbilToMod();
	}
    
    @Override
	protected boolean showDeleteButtonField() {
		return VocabolarioLayout.isAbilToDel();
	}    
    
    @Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgAbilModifica = record.getAttribute("flgAbilModifica") != null && record.getAttributeAsString("flgAbilModifica").equalsIgnoreCase("1");
		return VocabolarioLayout.isRecordAbilToMod(flgAbilModifica);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgAbilEliminazione = record.getAttribute("flgAbilEliminazione") != null && record.getAttributeAsString("flgAbilEliminazione").equalsIgnoreCase("1");
		return VocabolarioLayout.isRecordAbilToDel(flgAbilEliminazione);
	}	
	
	@Override
	protected void manageDeleteButtonClick(final ListGridRecord record) {
		SC.ask(I18NUtil.getMessages().deleteButtonAsk_message(), new BooleanCallback() {					
			@Override
			public void execute(Boolean value) {
				if(value) {							
					removeData(record, new DSCallback() {								
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
								Layout.addMessage(new MessageBean("Cancellazione avvenuta con successo", "", MessageType.INFO));
								if(layout != null) layout.hideDetail(true);
							} else {
								Layout.addMessage(new MessageBean("Si è verificato un errore, fallita cancellazione", "", MessageType.ERROR));										
							}																																	
						}
					});													
				}
			}
		});     	
	}
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
