/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class TopograficoForm extends DynamicForm {
	
	protected TopograficoForm _form;
	
	protected TextItem codiceRapidoItem;	
	protected TextItem nomeItem;	
	protected TextItem descrizioneItem;
	protected TextAreaItem noteItem;

	
	protected HiddenItem idTopograficoItem;	
	
	public TopograficoForm() {
		
		_form = this;
		
		setKeepInParentRect(true);
		setWidth("99%");
		setPadding(20);
		setAlign(Alignment.CENTER);
		setNumCols(5);
		setColWidths(120,1,1,"*","*");
		
		idTopograficoItem = new HiddenItem("idTopografico");
	
		codiceRapidoItem = new TextItem("codiceRapido", I18NUtil.getMessages().topografico_detail_codiceRapidoItem_title());
		codiceRapidoItem.setWidth(120);	
				
		nomeItem = new TextItem("nome", I18NUtil.getMessages().topografico_detail_nomeItem_title());
		nomeItem.setStartRow(true);
		nomeItem.setRequired(true);
		nomeItem.setWidth(250);	

		descrizioneItem = new TextItem("descrizione", I18NUtil.getMessages().topografico_detail_descrizioneItem_title());
		descrizioneItem.setStartRow(true);
		descrizioneItem.setWidth(250);	

		
		noteItem = new TextAreaItem("note", I18NUtil.getMessages().topografico_detail_noteItem_title());	
		noteItem.setStartRow(true);
		noteItem.setLength(4000);
		noteItem.setHeight(40);
		noteItem.setWidth(650);			
		
		
		setItems(
				idTopograficoItem,	
				codiceRapidoItem,
				nomeItem,
				descrizioneItem,
				noteItem		
		);		
		
		
	}
	
	public void setCanEdit(boolean canEdit) {
		for(FormItem item : _form.getFields()) {	 					
			item.setCanEdit(canEdit);
			item.redraw();
		}
	}
	
}
