/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class DatiContabiliAVBWindow extends ModalWindow {
	
	protected DatiContabiliAVBWindow window;
	protected DatiContabiliAVBDetail detail;	
	protected ListaDatiContabiliAVBItem gridItem;
	protected ToolStrip detailToolStrip;
	
	public DatiContabiliAVBWindow(ListaDatiContabiliAVBItem gridItem, String nomeEntita, Record record, boolean canEdit) {
		
		super(nomeEntita, false);
		
		settingsMenu.removeItem(separatorMenuItem);
		settingsMenu.removeItem(autoSearchMenuItem);
		
		setHeight(540);
		setWidth(950);
		setOverflow(Overflow.AUTO);    			
				
		window = this;
		this.gridItem = gridItem;
		
		final boolean isNew = (record == null);
						
		if(record != null) {
			if(canEdit) {
				setTitle(I18NUtil.getMessages().editDetail_titlePrefix() + " " + getTipoEstremiRecord(record));				
			} else {
				setTitle(I18NUtil.getMessages().viewDetail_titlePrefix() + " " + getTipoEstremiRecord(record));	
			}			
		} else {
			if(isImpegno()) {
				setTitle("Compila dati impegno");			
			}  else if(isAccertamento()) {
				setTitle("Compila dati accertamento");			
			} else if(isLiquidazione()) {
				setTitle("Compila dati liquidazione");			
			}
		}
		
		detail = new DatiContabiliAVBDetail(nomeEntita, gridItem/*, getCdCValueMap()*/);		
		detail.setHeight100();
		detail.setWidth100();
		
		if(canEdit) {
			
			final DetailToolStripButton saveButton = new DetailToolStripButton(I18NUtil.getMessages().formChooser_ok(), "ok.png");
			saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					saveButton.focusAfterGroup();
					if(detail.validate()) {
						final Record lRecord = detail.getRecordToSave();
						manageSaveData(isNew, lRecord);								
					} else {
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
					}
				}
			});
			
			DetailToolStripButton annullaButton = new DetailToolStripButton(I18NUtil.getMessages().undoButton_prompt(), "buttons/undo.png");
			annullaButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {				
					window.manageOnCloseClick();
				}
			});
			
			ToolStrip detailToolStrip = new ToolStrip();
			detailToolStrip.setWidth100();
			detailToolStrip.setHeight(30);
			detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
			detailToolStrip.addFill(); // push all buttons to the right		
			detailToolStrip.addButton(saveButton);
			detailToolStrip.addButton(annullaButton);
			
			VLayout detailLayout = new VLayout();  
			detailLayout.setOverflow(Overflow.HIDDEN);		
			detailLayout.setHeight100();
			detailLayout.setWidth100();		
			detailLayout.setMembers(detail, detailToolStrip);	
			
			setBody(detailLayout);
		} else {
			setBody(detail);
		}
		
		if(record != null) {
			detail.editRecord(record);
		} else {
			detail.editNewRecord();
		}
		
		detail.setCanEdit(canEdit);
		
		setIcon("blank.png");		
	}
	
	public void saveData(Record record) {
		
	}
	
	/**
	 * @param isNew
	 * @param lRecord
	 */
	public void manageSaveData(final boolean isNew, final Record lRecord) {
		saveData(lRecord);		
		if(isNew) {
			AfterSaveDatiSpesaDialogBox afterSaveDatiSpesaDialogBox = new AfterSaveDatiSpesaDialogBox("Vuoi inserire un nuovo dettaglio di spesa?") {

				@Override
				public void onClickButton(String scelta) {
					if(scelta != null && scelta.equals("SI")) {
						detail.editNewRecord();
						detail.reloadLivelliPdCItem();
					} else if(scelta != null && scelta.equals("CC")) {
						// per la copia lascio i dati così come sono invariati a maschera, perchè se richiamo l'editRecord mi perdo il valore di alcuni campi
//						detail.editRecord(new Record(detail.getValuesManager().getValues()));
						detail.reloadLivelliPdCItem();
					} else {
						window.manageOnCloseClick();
					}
				}
			};	
			afterSaveDatiSpesaDialogBox.show();
		} else {
			window.manageOnCloseClick();
		}
	}
	
	public boolean isImpegno() {
		return ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB() != null && ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.IMPEGNI);
	}
	
	public boolean isAccertamento() {
		return ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB() != null && ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.ACCERTAMENTI);
	}
	
	public boolean isLiquidazione() {
		return ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB() != null && ((ListaDatiContabiliAVBItem)gridItem).getTipoDatiContabiliAVB().equals(ListaDatiContabiliAVBItem.TipoDatiContabiliAVBEnum.LIQUIDAZIONI);
	}
	
	public String getTipoEstremiRecord(Record record) {
		String estremi = "";
		if(isImpegno()) {
			if(record.getAttribute("nroImpAcc") != null && !"".equals(record.getAttribute("nroImpAcc"))) {
				estremi += "impegno N° " + record.getAttribute("nroImpAcc") + " ";
			}
			if(record.getAttribute("subImpAcc") != null && !"".equals(record.getAttribute("subImpAcc"))) {
				estremi += "sub " + record.getAttribute("subImpAcc") + " ";
			}						
		} else if(isAccertamento()) {
			if(record.getAttribute("nroImpAcc") != null && !"".equals(record.getAttribute("nroImpAcc"))) {
				estremi += "accertamento N° " + record.getAttribute("nroImpAcc") + " ";
			}
			if(record.getAttribute("subImpAcc") != null && !"".equals(record.getAttribute("subImpAcc"))) {
				estremi += "sub " + record.getAttribute("subImpAcc") + " ";
			}	
		} else if(isLiquidazione()) {
			if(record.getAttribute("nroLiquidazione") != null && !"".equals(record.getAttribute("nroLiquidazione"))) {
				estremi += "liquidazione N° " + record.getAttribute("nroLiquidazione") + " ";
			}
			if(record.getAttribute("annoLiquidazione") != null && !"".equals(record.getAttribute("annoLiquidazione"))) {
				estremi += "anno " + record.getAttribute("annoLiquidazione") + " ";
			}	
		}  
		return estremi;
	}
	
//	public HashMap<String, String> getCdCValueMap() {
//		return new HashMap<String, String>();
//	}
	
}
