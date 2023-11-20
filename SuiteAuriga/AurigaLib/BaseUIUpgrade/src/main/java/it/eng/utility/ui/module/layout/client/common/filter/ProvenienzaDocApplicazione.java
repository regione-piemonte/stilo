/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.types.FieldType;

import it.eng.utility.ui.module.layout.client.common.filter.item.ProvenienzaDocApplicazioneItem;

public class ProvenienzaDocApplicazione extends CustomDataSourceField {
	
	public ProvenienzaDocApplicazione(){
		super(FieldType.CUSTOM);		
	}
	
	public ProvenienzaDocApplicazione(String name) {
		super(name, FieldType.CUSTOM);		
	}

	public ProvenienzaDocApplicazione(String name, String title) {
		super(name, FieldType.CUSTOM, title);
	}

	public ProvenienzaDocApplicazione(String name, String title, int length) {
		super(name, FieldType.CUSTOM, title, length);
	}

	public ProvenienzaDocApplicazione(String name, String title, int length, boolean required) {
		super(name, FieldType.CUSTOM, title, length, required);
	}
	
	protected void init() {

		setAttribute("customType", "proven_doc_applicazione");		

		ProvenienzaDocApplicazioneItem editorType = new ProvenienzaDocApplicazioneItem(); 
		setEditorType(editorType);

	}

}
