/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

/**
 * 
 * @author dbe4235
 *
 */

public class MotivoAnnullamentoSedutePopup extends ModalWindow {
	
	private ValuesManager vm;
	protected DynamicForm mDynamicForm;
	private TextAreaItem motivazioneItem;
	
	private Button confermaButton;
	private Button annullaButton;

	public MotivoAnnullamentoSedutePopup() {
		super("motivo_annullamento_sedute");
		
		setTitle("Conferma annullamento seduta");
		setHeaderIcon("delibere/annullaSeduta.png");

		setAutoCenter(true);
		setWidth(500);
		setHeight(150);
		
		this.vm = new ValuesManager();
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setKeepInParentRect(true);
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setWidth100();
		mDynamicForm.setHeight100();
		mDynamicForm.setNumCols(5);
		mDynamicForm.setColWidths(1, 1, 1, 1, "*");
		mDynamicForm.setCellPadding(7);
		mDynamicForm.setCanSubmit(true);
		mDynamicForm.setAlign(Alignment.LEFT);
		mDynamicForm.setTop(50);
		mDynamicForm.setValuesManager(vm);
		
		motivazioneItem = new TextAreaItem("motivazione", "Motivazione annullamento");
		motivazioneItem.setColSpan(1);
		motivazioneItem.setAlign(Alignment.LEFT);	
		motivazioneItem.setLength(4000);
		motivazioneItem.setHeight(82);
		motivazioneItem.setWidth(480);
		if(AurigaLayout.getParametroDBAsBoolean("OBBLIG_MOTIVI_ANN_SEDUTA")) {
			motivazioneItem.setRequired(true);
		}
		
		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setColSpan(1);
		spacerItem.setStartRow(true);
		
		StaticTextItem titleWarning = new StaticTextItem("titleWarning", "<b>Attenzione: una seduta annullata non può essere ripristinata</b>");
		titleWarning.setStartRow(false);
		titleWarning.setColSpan(1);
		titleWarning.setTitleAlign(Alignment.LEFT);
		
		mDynamicForm.setItems(motivazioneItem, spacerItem, titleWarning);
		
		confermaButton = new Button("Conferma");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16);
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(vm.validate()) {
					vm.clearErrors(true);				
					Record record = new Record(vm.getValues());
					manageOnOkButtonClick(record);
					markForDestroy();
				}
			}
		});
		
		annullaButton = new Button("Annulla"); 
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16);
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				//Chiudo la finestra
				markForDestroy();
			}
		});
		
		HStack _buttons = new HStack(5);
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(confermaButton);
		_buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		// Creo il VLAYOUT e gli aggiungo il TABSET
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

		setBody(portletLayout);
		
	}
	
	public void manageOnOkButtonClick(Record values) {

	}
	
	public class TitleStaticTextItem extends StaticTextItem {

		public TitleStaticTextItem(String title, int width) {
			setTitle(title);
			setColSpan(1);
			setDefaultValue(title + AurigaLayout.getSuffixFormItemTitle());
			setWidth(width);
			setShowTitle(false);
			setAlign(Alignment.RIGHT);
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
			setWrap(false);
		}

		@Override
		public void setCanEdit(Boolean canEdit) {
			setTextBoxStyle(it.eng.utility.Styles.formTitle);
		}
	}

}