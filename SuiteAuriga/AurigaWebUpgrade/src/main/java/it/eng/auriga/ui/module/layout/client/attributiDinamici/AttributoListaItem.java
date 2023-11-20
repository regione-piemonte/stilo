/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Map;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AttributoListaItem extends ReplicableItem {

	private AttributiDinamiciDetail detail;
	private RecordList datiDettLista;
	private CallbackGenericFunction callbackGeneric;
	private int viewReplicableItemHeight = 450;

	public AttributoListaItem(AttributiDinamiciDetail detail, RecordList datiDettLista) {
		this(detail, datiDettLista, null);
	}

	public AttributoListaItem(AttributiDinamiciDetail detail, RecordList datiDettLista, CallbackGenericFunction callback) {
		this.setDetail(detail);
		this.datiDettLista = datiDettLista;
		this.callbackGeneric = callback;
		setShowDuplicaRigaButton(true);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {

		AttributoListaCanvas lAttributoListaCanvas = new AttributoListaCanvas(this);
		return lAttributoListaCanvas;

	}

	@Override
	protected ImgButton[] createAddButtons() {

		ImgButton[] addButtons = new ImgButton[1];

		addButtons[0] = new ImgButton();
		addButtons[0].setSrc("[SKIN]actions/add.png");
		addButtons[0].setShowDown(false);
		addButtons[0].setShowRollOver(false);
		addButtons[0].setSize(16);
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();
			}
		});

		return addButtons;

	}

	public RecordList getDatiDettLista() {
		return datiDettLista;
	}

	public void setDatiDettLista(RecordList datiDettLista) {
		this.datiDettLista = datiDettLista;
	}

	public CallbackGenericFunction getCallbackGeneric() {
		return callbackGeneric;
	}

	public void setCallbackGeneric(CallbackGenericFunction callbackGeneric) {
		this.callbackGeneric = callbackGeneric;
	}

	public AttributiDinamiciDetail getDetail() {
		return detail;
	}

	public void setDetail(AttributiDinamiciDetail detail) {
		this.detail = detail;
	}
	
	public boolean showCanvasSection() {
		return true;
	}

	public boolean isGestioneContenutiTabellaTrasp() {
		return false;
	}

	
	@Override
	public Boolean validate() {
		
		boolean valid = super.validate();
		showTabErrors(valid);
		return valid;
	}

	public Boolean validateSenzaObbligatorieta() {
		boolean valid = true;
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			for (DynamicForm form : lReplicableCanvas.getForm()) {
				for (FormItem item : form.getFields()) {
					if (item instanceof DocumentItem) {
						DocumentItem lDocumentItem = (DocumentItem) item;
						valid = lDocumentItem.validateSenzaObbligatorieta() && valid;
					} else {
						// solo se è valorizzato così escludo i controlli di obbligatorietà
						if (item.getValue() != null && !"".equals(String.valueOf(item.getValue()))) {
							valid = item.validate() && valid;
						}
					}
				}
			}
		}
		showTabErrors(valid);
		return valid;
	}

	public void showTabErrors(boolean valid) {
		try {
			TabSet tabSet = getForm() != null ? getForm().getTabSet() : null;
			String tabID = getForm() != null ? getForm().getTabID() : null;
			if (!valid && tabSet != null && tabID != null && !"".equals(tabID)) {
				tabSet.showTabErrors(tabID);
			}
		} catch (Exception e) {
		}
	}

	public int getViewReplicableItemHeight() {
		return viewReplicableItemHeight;
	}

	public void setViewReplicableItemHeight(int viewReplicableItemHeight) {
		this.viewReplicableItemHeight = viewReplicableItemHeight;
	}

	public void mostraVariazione() {
		VLayout lVLayout = getVLayout();
		if (lVLayout != null) {
			lVLayout.setBackgroundColor("#FFFAAF");
			lVLayout.setBorder("2px solid #FF0");
		}
	}

	public void mostraVariazioniColRighe(ArrayList<Map> variazioniAttrLista) {
		if (variazioniAttrLista != null && variazioniAttrLista.size() > 0) {
			VLayout lVLayout = getVLayout();
			for (int i = 0; i < getTotalMembers(); i++) {
				HLayout lHLayout = (HLayout) lVLayout.getMember(i);
				ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
				for (DynamicForm form : lReplicableCanvas.getForm()) {
					for (FormItem item : form.getFields()) {
						String flgVariazione = variazioniAttrLista.get(i) != null ? (String) variazioniAttrLista.get(i).get(item.getName()) : null;
						if (flgVariazione != null && "1".equals(flgVariazione)) {
							if (item instanceof AttributoListaItem) {
								AttributoListaItem lAttributoListaItem = (AttributoListaItem) item;
								lAttributoListaItem.mostraVariazione();
							} else if (item instanceof DocumentItem) {
								DocumentItem lDocumentItem = (DocumentItem) item;
								lDocumentItem.mostraVariazione();
							} else {
								item.setCellStyle(it.eng.utility.Styles.formCellVariazione);
								item.redraw();
							}
						}
					}
				}
			}
		}
	}
	
	public void manageOnChangedRequiredItemInCanvas() {
		
	}


}