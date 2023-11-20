/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.utility.ui.module.layout.client.common.CustomDetail;

/**
 * 
 * @author cristiano
 *
 */

public class ItemLavorazioneMailLayout extends CustomDetail {

	protected DynamicForm form;
	protected ItemLavorazioneMailItem itemLavorazioneMailItem;

	protected String tipoRel;

	public ItemLavorazioneMailLayout(String pTipoRel, ValuesManager vm) {
		super("itemLavorazioneMailLayout", vm);
		tipoRel = pTipoRel;

		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setNumCols(7);
		form.setColWidths("10", "10", "10", "10", "10", "*", "*");
		form.setMargin(10);
		form.setValuesManager(vm);

		itemLavorazioneMailItem = new ItemLavorazioneMailItem() {

			@Override
			public void trasformaInAllegatoEmail(Record record) {
				trasformaInAllegatoEmailFromItemLavorazione(record);
			};

			@Override
			public boolean isDettaglioMail() {
				return tipoRel != null && !"".equals(tipoRel) && "dettaglio_email".equals(tipoRel) ? true : false;
			}

			@Override
			public boolean showStampaFileButton() {
				return showAbilToPrint();
			}
		};
		itemLavorazioneMailItem.setName("listaItemInLavorazione");
		itemLavorazioneMailItem.setShowTitle(false);
		itemLavorazioneMailItem.setStartRow(true);

		form.setItems(itemLavorazioneMailItem);

		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		layout.addMember(form);

		addMember(layout);

	}

	public Boolean showAbilToPrint() {
		return false;
	}

	public DynamicForm getForm() {
		return form;
	}

	public void trasformaInAllegatoEmailFromItemLavorazione(Record record) {

	}

	public ItemLavorazioneMailItem getItemLavorazioneMailItem() {
		return itemLavorazioneMailItem;
	}

	public void setItemLavorazioneMailItem(ItemLavorazioneMailItem itemLavorazioneMailItem) {
		this.itemLavorazioneMailItem = itemLavorazioneMailItem;
	}

}
