/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class ScrivaniaDetail extends CustomDetail {

	private HiddenItem id;
	private HiddenItem flgUdFolder;	
	private TextItem tipo;
	private TextItem nome;
	private DateItem tsApertura;
	private TextAreaItem oggetto;
	private TextItem altroIdentificativo;
	
	private DynamicForm form;

	public ScrivaniaDetail(String nomeEntita){
		
		super(nomeEntita);
		
		form = new DynamicForm();

		form.setNumCols(4);
		form.setColWidths(150, 120, 120, "*");
		
		id = new HiddenItem("id");
		
		flgUdFolder = new HiddenItem("flgUdFolder");
		
		tipo = new TextItem("tipo", "Tipo");
		tipo.setStartRow(true);		
		tipo.setRequired(true);
				
		nome = new TextItem("nome", "Nome");
		nome.setStartRow(true);
		nome.setRequired(true);
		nome.setColSpan(1);
		
		tsApertura = new DateItem("tsApertura", "Aperta il");
		tsApertura.setColSpan(1);
		tsApertura.setStartRow(true);

		oggetto = new TextAreaItem("oggetto", "Oggetto");
		oggetto.setStartRow(true);
		
		altroIdentificativo = new TextItem("altroIdentificativo", "Altro identificativo");
		altroIdentificativo.setStartRow(true);
		
		form.setFields(
			id,
			flgUdFolder,
			tipo,
			nome,
			tsApertura,
			oggetto,
			altroIdentificativo
		);
		
		form.setValuesManager(vm);
		addMember(form);
		setOverflow(Overflow.VISIBLE);
		
	}



}
