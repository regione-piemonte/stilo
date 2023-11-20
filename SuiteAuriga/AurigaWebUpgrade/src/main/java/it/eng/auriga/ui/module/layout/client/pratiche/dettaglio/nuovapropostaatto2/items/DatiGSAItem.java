/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class DatiGSAItem extends ReplicableItem {

	private ImgButtonItem addRaggruppamentoButton;
	private ImgButtonItem addProgressivoButton;

	// Contatore interno progressivi gruppi
	protected HashMap<Integer, Integer> countProgrGruppi = new HashMap<Integer, Integer>();
	
	@Override
	public ReplicableCanvas getCanvasToReply() {
		DatiGSACanvas lDatiGSACanvas = new DatiGSACanvas(this);
		return lDatiGSACanvas;
	}
	
	@Override
	public HLayout createAddButtonsLayout() {
		
		HLayout addButtonsLayout = new HLayout();
		addButtonsLayout.setMembersMargin(3);
		
		DynamicForm addButtonsForm = new DynamicForm();
		
		NestedFormItem addButtons = new NestedFormItem("add");
		addButtons.setWidth(40);
		addButtons.setNumCols(3);
		addButtons.setColWidths(16,16);
		
		addProgressivoButton = new ImgButtonItem("addProgressivoButton", "[SKIN]actions/add.png", "Nuovo progressivo");
		addProgressivoButton.setShowFocusedIcons(false);   
		addProgressivoButton.setShowOverIcons(false);      
		addProgressivoButton.setIconHeight(16);
		addProgressivoButton.setIconWidth(16); 
		addProgressivoButton.addIconClickHandler(new IconClickHandler() {
			
			@Override
			public void onIconClick(IconClickEvent event) {
				if(countProgrGruppi.size() > 0) {
					countProgrGruppi.put(countProgrGruppi.size(), countProgrGruppi.get(countProgrGruppi.size()) + 1);
					onClickNewButton();
					redraw();
				} else {
					if(isFromDettaglioMovimentoGSA()) {
						AurigaLayout.addMessage(new MessageBean("Per creare un nuovo progressivo bisogna prima inserire almeno un gruppo", "", MessageType.ERROR));
					} else {
						countProgrGruppi.put(countProgrGruppi.size() + 1, 1);
						onClickNewButton();
						redraw();
					}
				}
			}
		});
		
		if(isFromDettaglioMovimentoGSA()) {
			addRaggruppamentoButton = new ImgButtonItem("addRaggruppamentoButton", "buttons/add_gruppo.png", "Nuovo gruppo");
			addRaggruppamentoButton.setShowFocusedIcons(false);   
			addRaggruppamentoButton.setShowOverIcons(false);      
			addRaggruppamentoButton.setIconHeight(16);
			addRaggruppamentoButton.setIconWidth(16); 
			addRaggruppamentoButton.addIconClickHandler(new IconClickHandler() {
				
				@Override
				public void onIconClick(IconClickEvent event) {
					countProgrGruppi.put(countProgrGruppi.size() + 1, 1);
					onClickNewButton();
					redraw();
				}
			});
			
			addButtons.setNestedFormItems(addRaggruppamentoButton, addProgressivoButton);
		} else {
			addButtons.setNestedFormItems(addProgressivoButton);
		}
		
		addButtonsForm.setItems(addButtons);
		addButtonsForm.setMargin(0);
		addButtonsLayout.addMember(addButtonsForm);
		
		return addButtonsLayout;
	}
	
	@Override
	public void redraw() {
		super.redraw();
		if(isFromDettaglioMovimentoGSA()) {
			if(addProgressivoButton != null) {
				if(countProgrGruppi.size() == 0) {
					addProgressivoButton.hide();
				} else {
					addProgressivoButton.show();
				}
			}
		}
	}
	
	@Override
	public Record getCanvasDefaultRecord() {
		Record lRecord = new Record();
		lRecord.setAttribute("raggruppamento", countProgrGruppi.size());
		lRecord.setAttribute("progressivo", countProgrGruppi.get(countProgrGruppi.size()));
		return lRecord;
	}
	
	@Override
	public void setUpClickRemoveHandler(final VLayout lVLayout, final HLayout lHLayout) {
		ReplicableCanvas lReplicableCanvasToRemove = getReplicableCanvas((HLayout) lVLayout.getMember(getPosition(lHLayout)));
		Integer gruppoToRemove = lReplicableCanvasToRemove.getFormValuesAsRecord().getAttributeAsInt("raggruppamento");
		Integer progressivoToRemove = lReplicableCanvasToRemove.getFormValuesAsRecord().getAttributeAsInt("progressivo");
		setUpClickRemove(lVLayout, lHLayout);
		if(countProgrGruppi.get(gruppoToRemove) == 1) {
			countProgrGruppi.remove(gruppoToRemove);
			for(Integer gruppo : countProgrGruppi.keySet()) {
				if(gruppo > gruppoToRemove) {
					countProgrGruppi.put(gruppo - 1, countProgrGruppi.get(gruppo));
					countProgrGruppi.remove(gruppo);
				}
			}
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				ReplicableCanvas lReplicableCanvas = getReplicableCanvas((HLayout) lVLayout.getMember(i));
				if(lReplicableCanvas != null) {
					Record lRecord = lReplicableCanvas.getFormValuesAsRecord();
					if(lRecord.getAttributeAsInt("raggruppamento") > gruppoToRemove) {
						lRecord.setAttribute("raggruppamento", lRecord.getAttributeAsInt("raggruppamento") - 1);
						lReplicableCanvas.editRecord(lRecord);
					}
				}
			}
		} else {
			countProgrGruppi.put(gruppoToRemove, countProgrGruppi.get(gruppoToRemove) - 1);
			for (int i = 0; i < lVLayout.getMembers().length; i++) {
				ReplicableCanvas lReplicableCanvas = getReplicableCanvas((HLayout) lVLayout.getMember(i));
				if(lReplicableCanvas != null) {
					Record lRecord = lReplicableCanvas.getFormValuesAsRecord();
					if(lRecord.getAttributeAsInt("raggruppamento") == gruppoToRemove) {
						if(lRecord.getAttributeAsInt("progressivo") > progressivoToRemove) {
							lRecord.setAttribute("progressivo", lRecord.getAttributeAsInt("progressivo") - 1);
							lReplicableCanvas.editRecord(lRecord);
						}
					}
				}
			}
		}
		redraw();
	}
	
	@Override
	public void drawAndSetValue(RecordList lRecordList, List<Boolean> lListEditing) {
		super.drawAndSetValue(lRecordList, lListEditing);
		countProgrGruppi = new HashMap<Integer, Integer>();
		if(lRecordList != null) {
			for(int i = 0; i < lRecordList.getLength(); i++) {
				Record lRecord = lRecordList.get(i);
				Integer gruppo = lRecord.getAttributeAsInt("raggruppamento");
				if(!countProgrGruppi.containsKey(gruppo)) {
					countProgrGruppi.put(gruppo, 1);
				} else {
					countProgrGruppi.put(gruppo, countProgrGruppi.get(gruppo) + 1);
				}
			}
		}
		redraw();
	}
	
	public void reloadSelectContoPrimaNota() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((DatiGSACanvas)lReplicableCanvas).reloadSelectContoPrimaNota();
		}
	}
	
	public Boolean validateImportiDareAvereGruppi() {
		boolean valid = true;
		VLayout lVLayout = getVLayout();
		HashMap<Integer, Float> totaleImportiDareAvereGruppi = new HashMap<Integer, Float>();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			Record lRecordValues = lReplicableCanvas.getFormValuesAsRecord();
			Integer gruppo = lRecordValues.getAttributeAsInt("raggruppamento");
			if(!totaleImportiDareAvereGruppi.containsKey(gruppo)) {
				totaleImportiDareAvereGruppi.put(gruppo, new Float(0));
			}
			String pattern = "#,##0.00";
			float importo = 0;
			if(lRecordValues.getAttribute("importo") != null && !"".equals(lRecordValues.getAttribute("importo"))) {
				importo = new Float(NumberFormat.getFormat(pattern).parse((String) lRecordValues.getAttribute("importo"))).floatValue();			
			}
			if(lRecordValues.getAttribute("flgDareAvere") != null) {
				if("D".equals(lRecordValues.getAttribute("flgDareAvere"))) {
					totaleImportiDareAvereGruppi.put(gruppo, totaleImportiDareAvereGruppi.get(gruppo) + importo);
				} else if("A".equals(lRecordValues.getAttribute("flgDareAvere"))) {
					totaleImportiDareAvereGruppi.put(gruppo, totaleImportiDareAvereGruppi.get(gruppo) - importo);
				}
			}
		}
		String gruppiInError = null;
		for (Integer gruppo : totaleImportiDareAvereGruppi.keySet()) {
			if(totaleImportiDareAvereGruppi.get(gruppo).floatValue() != 0) {
				if(gruppiInError == null) {
					gruppiInError = "" + gruppo.intValue();
				} else {
					gruppiInError += ", " + gruppo.intValue();
				}
			}
		}
		if(gruppiInError != null && !"".equals(gruppiInError)) {
			if(getForm() != null && getName() != null) {
				getForm().setFieldErrors(getName(), "Per il/i raggruppamento/i " + gruppiInError + " la somma degli importi dare/avere non è uguale come atteso");
			}
			valid = false;
		}
		return valid;
	}
	
	public String getImportoDefaultValue() {
		return null;
	}
	
	public Record getRecordFiltriContoPrimaNota() {
		return new Record();
	}
	
	public boolean isFromDettaglioMovimentoGSA() {
		return false;
	}
	
}
