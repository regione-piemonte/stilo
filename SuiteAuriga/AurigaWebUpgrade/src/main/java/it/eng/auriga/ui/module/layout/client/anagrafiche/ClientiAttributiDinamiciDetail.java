/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class ClientiAttributiDinamiciDetail extends AttributiDinamiciDetail {

	protected ClientiAttributiDinamiciDetail instance;
	protected String mode;

	public ClientiAttributiDinamiciDetail(String nomeEntita, RecordList attributiAdd, Map mappaDettAttrLista, Map mappaValoriAttrLista,
			Map mappaVariazioniAttrLista, Map mappaDocumenti) {
		super(nomeEntita, attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista, mappaDocumenti, null, null, null);
		instance = this;
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
