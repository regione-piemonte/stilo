/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.gestioneatti.attiinlavorazione.AttiInLavorazioneSimpleTree;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class MostraIterSvoltoModalWindows extends ModalWindow {

	protected MostraIterSvoltoModalWindows _window;
	protected DynamicForm _form;
	protected String idPrecessIn;
	
	protected TextItem descrizioneEventoItem;
	protected TextItem descrizioneUtenteItem;
	protected TextItem dettagliEventoItem;
	protected ButtonItem cercaButtonItem;
	protected ButtonItem chiudiButton;

	protected AttiInLavorazioneSimpleTree or;

	public MostraIterSvoltoModalWindows(String idPrecessIn, String estremi) {

		super("mostra_iter_svolto", true);

		this.idPrecessIn = idPrecessIn;
		_window = this;

		String title = I18NUtil.getMessages().atti_inlavorazione_iter_svolto_title() + " " + ((estremi != null) ? (estremi) : (""));
		setTitle(title);

		setAutoCenter(true);

		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);

		_form = new DynamicForm();
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		// _form.setHeight100();
		_form.setNumCols(3);
		_form.setColWidths(30, "*", 200);
		_form.setCellPadding(5);
		_form.setWrapItemTitles(false);
		_form.setTitle("prova");

		descrizioneEventoItem = new TextItem("descrizioneEvento", I18NUtil.getMessages().atti_inlavorazione_iter_svolto_filtro_nome());
		descrizioneEventoItem.setWidth("100%");
		descrizioneEventoItem.setStartRow(true);
		descrizioneEventoItem.setTabIndex(1);

		descrizioneUtenteItem = new TextItem("descrizioneUtente", I18NUtil.getMessages().atti_inlavorazione_iter_svolto_filtro_svolta_da());
		descrizioneUtenteItem.setWidth("100%");
		descrizioneUtenteItem.setStartRow(true);
		descrizioneUtenteItem.setTabIndex(2);

		dettagliEventoItem = new TextItem("dettagliEvento", I18NUtil.getMessages().atti_inlavorazione_iter_svolto_filtro_messaggio());
		dettagliEventoItem.setWidth("100%");
		dettagliEventoItem.setStartRow(true);
		dettagliEventoItem.setTabIndex(3);

		cercaButtonItem = new ButtonItem(I18NUtil.getMessages().atti_inlavorazione_iter_svolto_bottone_cerca());
		cercaButtonItem.setIcon("search.png");
		cercaButtonItem.setStartRow(false);
		cercaButtonItem.setAlign(Alignment.RIGHT);
		cercaButtonItem.setTabIndex(4);
		cercaButtonItem.setAutoFit(false);
		cercaButtonItem.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				refreshTree();
			}
		});

		_form.setFields(new FormItem[] { descrizioneEventoItem, descrizioneUtenteItem, dettagliEventoItem, cercaButtonItem });

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("AttiInLavorazioneTreeDatasource", true, "idNode", FieldType.TEXT);
		or = new AttiInLavorazioneSimpleTree("attiInLavorazione");
		or.setDataSource(lGwtRestDataSource);
		or.loadTree(idPrecessIn, null, null, null, null, null, null, null, null);

		// Creo il VLAYOUT e gli aggiungo il TABSET
		VLayout portletLayout = new VLayout();
		portletLayout.setHeight100();
		portletLayout.setWidth100();
		portletLayout.setOverflow(Overflow.VISIBLE);
		DetailSection detailSectionRicerca = new DetailSection(I18NUtil.getMessages().atti_inlavorazione_iter_svolto_form_title(), false, true, false, _form);

		layout.addMember(detailSectionRicerca);
		layout.addMember(or);

		portletLayout.addMember(layout);

		setBody(portletLayout);

		setIcon("pratiche/task/iter_svolto.png");
		setWidth(700);
		setHeight("85%");

	}
	
	public DynamicForm getForm() {
		return _form;
	}

	public void editRecordPerModificaInvio(Record record) {
		_form.editRecord(record);
		markForRedraw();
	}

	private void refreshTree() {
		String codTipoEventoIn = null;
		String desEventoIn = descrizioneEventoItem.getValueAsString();
		String desUserIn = descrizioneUtenteItem.getValueAsString();
		String desEsitoIn = null;
		String dettagliEventoIn = dettagliEventoItem.getValueAsString();
		or.refreshTree(idPrecessIn, codTipoEventoIn, desEventoIn, desUserIn, desEsitoIn, dettagliEventoIn, null, null, null);
	}
}