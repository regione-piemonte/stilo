/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.attributiDinamici.AttributiDinamiciDetail;

import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class ElenchiAlbiDetail extends AttributiDinamiciDetail {

	public ElenchiAlbiDetail(String nomeEntita, RecordList attributiAdd, Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista,
			Map mappaDocumenti) {
		// TODO Auto-generated constructor stub
		super(nomeEntita, attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista, mappaDocumenti, null, null, null);
	}

	@Override
	public HiddenItem[] getOtherHiddenItems() {
		
		HiddenItem idElencoAlboItem = new HiddenItem("idElencoAlbo");
		return new HiddenItem[] { idElencoAlboItem };
	}

	public Record getDetailRecord() {
		final Record detailRecord = new Record(vm.getValues());
		detailRecord.setAttribute("values", getMappaValori(detailRecord));
		detailRecord.setAttribute("types", getMappaTipiValori(detailRecord));
		return detailRecord;
	}

}
