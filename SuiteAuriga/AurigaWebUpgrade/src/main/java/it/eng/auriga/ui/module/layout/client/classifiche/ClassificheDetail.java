/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class ClassificheDetail extends CustomDetail {

	private HiddenItem idClassificazione;
	private TextItem tipo;
	private TextItem descrizione;
	
	private DynamicForm form;

	public ClassificheDetail(String nomeEntita){
		
		super(nomeEntita);
		
		form = new DynamicForm();

		form.setNumCols(4);
		form.setColWidths(150, 120, 120, "*");
		
		idClassificazione = new HiddenItem("idClassificazione");
		
		tipo = new TextItem("tipo", "Tipo");
		tipo.setStartRow(true);		
		tipo.setRequired(true);
				
		descrizione = new TextItem("descrizione", "Descrizione");
		descrizione.setStartRow(true);
		descrizione.setRequired(true);
		descrizione.setColSpan(1);
				
		form.setFields(
			idClassificazione,
			tipo,
			descrizione
		);
		
		addSubForm(form);
		
	}

}
