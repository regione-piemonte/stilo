/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author DANCRIST
 *
 */

public class SaveImpostazioniCopertinaTimbroWindow extends ModalWindow {
	
	private DynamicForm mDynamicForm;
	private SelectItem posizioneTimbro;
	private SelectItem rotazioneTimbro;
	private SelectItem tipoPagina;
	private CheckboxItem skipSceltaOpzioniTimbroSegnatura;
	private CheckboxItem skipSceltaOpzioniCopertina;
	private CheckboxItem skipSceltaApponiTimbro;
	private CheckboxItem skipSceltaTimbroDocZip;

	public SaveImpostazioniCopertinaTimbroWindow() {
		super("config_utente_impostazioniCopertinaTimbro", true);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		setTitle(I18NUtil.getMessages().configUtenteMenuImpostazioniCopertinaTimbro_title());
		setIcon("file/timbra.gif");

		setWidth(600);
		setHeight(500);
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths(10, 10, 10, 10, "*");
		mDynamicForm.setPadding(5);
		mDynamicForm.setAlign(Alignment.LEFT);
		mDynamicForm.setTop(50);
		
		posizioneTimbro = new SelectItem("posizioneTimbro", "Posizione");
		posizioneTimbro.setWidth(150);
		posizioneTimbro.setStartRow(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("altoDx", "in alto a destra");
		lLinkedHashMap.put("altoSn", "in alto a sinistra");
		lLinkedHashMap.put("bassoDx", "in basso a destra");
		lLinkedHashMap.put("bassoSn", "in basso a sinistra");
		posizioneTimbro.setValueMap(lLinkedHashMap);	
		
		rotazioneTimbro = new SelectItem("rotazioneTimbro", "Rotazione");
		rotazioneTimbro.setWidth(90);
		rotazioneTimbro.setStartRow(true);
		LinkedHashMap<String, String> rotazioneValues = new LinkedHashMap<String, String>();
		rotazioneValues.put("orizzontale", "orizzontale");
		rotazioneValues.put("verticale", "verticale");
		rotazioneTimbro.setValueMap(rotazioneValues);	
		
		tipoPagina = new SelectItem("tipoPagina", "Pagine da timbrare");
		tipoPagina.setWidth(150);
		tipoPagina.setStartRow(true);
		LinkedHashMap<String, String> tipoPaginaValues = new LinkedHashMap<String, String>();
		tipoPaginaValues.put("prima", "prima");
		tipoPaginaValues.put("ultima", "ultima");
		tipoPaginaValues.put("tutte", "tutte");
		//tipoPaginaValues.put("intervallo", "intervallo");
		tipoPagina.setValueMap(tipoPaginaValues);		
		tipoPagina.addChangedHandler(new ChangedHandler() {			
			@Override
			public void onChanged(ChangedEvent event) {

				mDynamicForm.markForRedraw();
			}
		});
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setStartRow(true);

		skipSceltaOpzioniTimbroSegnatura = new CheckboxItem("skipSceltaOpzioniTimbroSegnatura",
				I18NUtil.getMessages().saveImpostazioniCopertinaTimbroWindow_skipSceltaOpzioniTimbroSegnatura());
		skipSceltaOpzioniTimbroSegnatura.setStartRow(false);

		skipSceltaOpzioniCopertina = new CheckboxItem("skipSceltaOpzioniCopertina", I18NUtil.getMessages().saveImpostazioniCopertinaTimbroWindow_skipSceltaOpzioniCopertina());
		skipSceltaOpzioniCopertina.setStartRow(false);
		
		skipSceltaApponiTimbro = new CheckboxItem("skipSceltaApponiTimbro", I18NUtil.getMessages().saveImpostazioniCopertinaTimbroWindow_skipSceltaApponiTimbro());
		skipSceltaApponiTimbro.setStartRow(false);
		skipSceltaApponiTimbro.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_TIMBRATURA_FILE_POST_REG");
			}
		});
		
		skipSceltaTimbroDocZip = new CheckboxItem("skipSceltaTimbroDocZip", I18NUtil.getMessages().saveImpostazioniCopertinaTimbroWindow_skipSceltaTimbroDocZip());
		skipSceltaTimbroDocZip.setStartRow(false);
		
		mDynamicForm.setItems(
				posizioneTimbro,
				rotazioneTimbro,
				tipoPagina,
				spacerItem,
				skipSceltaOpzioniTimbroSegnatura,
				spacerItem,
				skipSceltaOpzioniCopertina,
				spacerItem,
				skipSceltaApponiTimbro,
				spacerItem,
				skipSceltaTimbroDocZip
		);
		
		Button okButton = new Button("Ok");
		okButton.setIcon("ok.png");
		okButton.setIconSize(16);
		okButton.setAutoFit(false);
		okButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				mDynamicForm.clearErrors(true);
				manageOnOkButtonClick(new Record(mDynamicForm.getValues()));
				markForDestroy();
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(okButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
	
		VLayout spacerLayout = new VLayout();
		spacerLayout.setHeight100();
		spacerLayout.setWidth100();

		layout.addMember(mDynamicForm);
		layout.addMember(spacerLayout);

		portletLayout.addMember(layout);
		portletLayout.addMember(_buttons);

		addCloseClickHandler(new CloseClickHandler() {

			@Override
			public void onCloseClick(CloseClickEvent event) {
				markForDestroy();
			}
		});

		addItem(portletLayout);
		
	}
	
	public void clearValues() {
		mDynamicForm.clearValues();
	}

	public void setValues(Record values) {
		if (values != null) {
			mDynamicForm.editRecord(values);
		} else {
			mDynamicForm.editNewRecord();
		}
		mDynamicForm.clearErrors(true);
	}

	public void manageOnOkButtonClick(Record values) {

	}
	
}