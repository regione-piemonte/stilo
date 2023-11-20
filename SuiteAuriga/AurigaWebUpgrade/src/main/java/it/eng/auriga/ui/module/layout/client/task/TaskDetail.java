/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.archivio.TaskWindow;
import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailSection;

import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class TaskDetail extends AttributiDinamiciDetail {

	protected TaskDetail instance;
	protected TaskWindow window;

	private HiddenItem instanceIdItem;
	private HiddenItem idProcessItem;
	private HiddenItem activityNameItem;
	private HiddenItem idEventTypeItem;
	private HiddenItem nomeEventTypeItem;
	private HiddenItem rowIdEventoItem;
	private HiddenItem idEventoItem;

	protected boolean saved = false;

	protected String mode;

	public TaskDetail(String nomeEntita, RecordList attributiAdd, Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista,
			Map mappaDocumenti) {

		super(nomeEntita, attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista, mappaDocumenti, null, null, null);

		instance = this;

	}

	public void setWindow(TaskWindow taskWindow) {
		window = taskWindow;
	}

	public TaskWindow getWindow() {
		return window;
	}

	public Record getDetailRecord() {
		final Record detailRecord = new Record(vm.getValues());
		detailRecord.setAttribute("attributiAdd", getMappaValori(detailRecord));
		detailRecord.setAttribute("tipiAttributiAdd", getMappaTipiValori(detailRecord));
		return detailRecord;
	}

	@Override
	public void addDetailMembers() {

		for (DetailSection detailSection : attributiDinamiciDetailSections) {
			addMember(detailSection);
		}

	}

	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
	}

	@Override
	public void editNewRecord(Map initialValues) {
		
		super.editNewRecord(initialValues);
	}

	public HiddenItem[] getOtherHiddenItems() {

		instanceIdItem = new HiddenItem("instanceId");
		idProcessItem = new HiddenItem("idProcess");
		activityNameItem = new HiddenItem("activityName");
		idEventTypeItem = new HiddenItem("idEventType");
		nomeEventTypeItem = new HiddenItem("nomeEventType");
		rowIdEventoItem = new HiddenItem("rowIdEvento");
		idEventoItem = new HiddenItem("idEvento");

		return new HiddenItem[] { instanceIdItem, idProcessItem, activityNameItem, idEventTypeItem, nomeEventTypeItem, rowIdEventoItem, idEventoItem };
	}

	public void salvaTask() {
		if (validate()) {
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			GWTRestService<Record, Record> lAurigaTaskDataSource = new GWTRestService<Record, Record>("AurigaTaskDataSource");
			lAurigaTaskDataSource.executecustom("salvaTask", getDetailRecord(), new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					Layout.hideWaitPopup();
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						saved = true;
						Layout.addMessage(new MessageBean("Task " + window.getNomeAttivita() + " completato con successo", "", MessageType.INFO));
						window.manageOnCloseClick();
					}
				}
			});
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	public void newMode() {
		this.mode = "new";
		setCanEdit(true);
	}

	public void viewMode() {
		this.mode = "view";
		setCanEdit(false);
	}

	public void editMode() {
		this.mode = "edit";
		setCanEdit(true);
	}

}
