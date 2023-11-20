/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRegProtAssociatoWindow;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class RegDaAnnullareCanvas extends ReplicableCanvas{

	private HiddenItem idUdHiddenItem;
	private NumericItem nroRegItem;
	private DateTimeItem tsRichAnnItem;
	private TextItem uteRichAnnItem;
	private TextItem motiviAnnItem;
	
	private ReplicableCanvasForm mDynamicForm;

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);

		idUdHiddenItem = new HiddenItem("idUd");
		
		nroRegItem = new NumericItem("nroReg", I18NUtil.getMessages().atti_autorizzazione_listaRegDaAnnullare_nroReg());
		nroRegItem.setWidth(250);
		
		ImgButtonItem dettaglioUdRegButton = new ImgButtonItem("dettaglioUdRegButton", "buttons/detail.png", I18NUtil.getMessages().atti_autorizzazione_listaRegDaAnnullare_dettaglioUdRegButton());
		dettaglioUdRegButton.setColSpan(1);
		dettaglioUdRegButton.setAlwaysEnabled(true);
		dettaglioUdRegButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				Record lRecord = new Record();
				lRecord.setAttribute("idUd", mDynamicForm.getValueAsString("idUd"));
				new DettaglioRegProtAssociatoWindow(lRecord, "Dettaglio prot. N° " + mDynamicForm.getValueAsString("nroReg"));
			}
		});
		dettaglioUdRegButton.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return mDynamicForm.getValueAsString("idUd") != null && !"".equals(mDynamicForm.getValueAsString("idUd"));
			}
		});
		
		tsRichAnnItem = new DateTimeItem("tsRichAnn", I18NUtil.getMessages().atti_autorizzazione_listaRegDaAnnullare_tsRichAnn());	
		tsRichAnnItem.setWidth(110);
		
		uteRichAnnItem = new TextItem("uteRichAnn", I18NUtil.getMessages().atti_autorizzazione_listaRegDaAnnullare_uteRichAnn());
		uteRichAnnItem.setWidth(160);
		
		motiviAnnItem = new TextItem("motiviAnn", I18NUtil.getMessages().atti_autorizzazione_listaRegDaAnnullare_motiviAnn());
		motiviAnnItem.setWidth(160);
		
		mDynamicForm.setFields(
				idUdHiddenItem, 
				nroRegItem, 
				dettaglioUdRegButton,
				tsRichAnnItem, 
				uteRichAnnItem, 
				motiviAnnItem);	

		mDynamicForm.setNumCols(12);
		mDynamicForm.setColWidths("10", "100", "10", "10", "100", "10", "100", "10", "100", "10", "100", "10");

		addChild(mDynamicForm);

	}	

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		nroRegItem.setCanEdit(false);
		tsRichAnnItem.setCanEdit(false);
		uteRichAnnItem.setCanEdit(false);
	}	
	
}