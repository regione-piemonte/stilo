/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public abstract class ElencoDocumentiDaConfrontareItem extends ReplicableItem {
	
	private DynamicForm addButtonsForm;
	
	private ImgButtonItem importaFileDocumentiBtn;
	
	private ReplicableCanvas lastCanvasToReplicate;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		ElencoDocumentiDaConfrontareCanvas lElencoDocumentiDaConfrontareCanvas = new ElencoDocumentiDaConfrontareCanvas();
		return lElencoDocumentiDaConfrontareCanvas;
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(true);
	}
	
	@Override
	public HLayout createAddButtonsLayout() {
		return new HLayout();
//		HLayout addButtonsLayout = super.createAddButtonsLayout();
//
//		NestedFormItem addButtons = new NestedFormItem("add");
//		addButtons.setWidth(40);
//		addButtons.setNumCols(2);
//		addButtons.setColWidths(16, 16);
//
//		importaFileDocumentiBtn = new ImgButtonItem("importaFileDocumentiBtn", "buttons/importaAtti.png", "Aggiungi documenti da confrontare");
//
//		importaFileDocumentiBtn.addIconClickHandler(new IconClickHandler() {
//
//			@Override
//			public void onIconClick(IconClickEvent event) {
//				ImportaDocumentiDaConfrontareMultiLookupArchivio lookupArchivioPopup = new ImportaDocumentiDaConfrontareMultiLookupArchivio(null);
//				lookupArchivioPopup.show();
//			}
//		});
//		
//		addButtons.setNestedFormItems(importaFileDocumentiBtn);
//		
//		addButtonsForm = new DynamicForm();
//		addButtonsForm.setNumCols(10);
//		addButtonsForm.setMargin(0);
//		addButtonsForm.setFields(addButtons);
//		
//		addButtonsLayout.addMember(addButtonsForm);
//
//		return addButtonsLayout;

	}
	
	public boolean showTipoAttoRif() {
		return true;
	}
	
	public boolean isRequiredAttoRiferimento() {
		return true;
	}
	
	public boolean isRequiredTipoAttoRif() {
		return true;
	}
	
	public String getTitleCategoriaReg() {
		return "Categoria";
	}
	
	public String getTitleTipoAttoRif() {
		return "Tipo atto";
	}
	
	public String getTipoLoadComboTipoAttoRif() {
		return null;
	}
	
	public String getAltriParamLoadComboTipoAttoRif() {
		return null;
	}
	
	public boolean getFlgSoloVldLoadComboTipoAttoRif() {
		return false;
	}
	
	public String getDefaultValueTipoAttoRif() {
		return null;
	}
	
	public abstract void visualizzaDocumento(Record recordDocumento, String schermata);
	
	public class ImportaDocumentiDaConfrontareMultiLookupArchivio extends LookupArchivioPopup {

		private List<Record> multiLookupList = new ArrayList<Record>();

		public ImportaDocumentiDaConfrontareMultiLookupArchivio(Record record) {
			super(record, null, false);
		}
		
		@Override
		public String getWindowTitle() {
			return "Seleziona i documenti da confrontare";
		}
		
		@Override
		public String getFinalita() {
			return getFinalitaAggiungiDocumentoDaConfrontare();
		}

		@Override
		public void manageOnCloseClick() {
			super.manageOnCloseClick();
			if ((multiLookupList != null) && (multiLookupList.size() > 0)) {
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere...");
				RecordList lRecordListDocumentToImport = new RecordList();
				for (Record record : multiLookupList) {
					Record lRecordToLoad = new Record();
					lRecordToLoad.setAttribute("idUd", record.getAttribute("idUdFolder"));
					lRecordListDocumentToImport.add(lRecordToLoad);
				}
				Record recordMassivo = new Record();
				recordMassivo.setAttribute("listaRecord", lRecordListDocumentToImport);
				GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				Layout.showWaitPopup("Operazione in corso: potrebbe richiedere qualche secondo. Attendere...");
				lGwtRestDataSource.performCustomOperation("getEstremiUnitaDocumentarie", recordMassivo, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							RecordList resultRecordList = response.getData()[0].getAttributeAsRecordList("listaRecord");
							for (int i = 0; i < resultRecordList.getLength(); i++) {
								Record resultRecord = resultRecordList.get(i);
								aggiungiDocumentiInLista(resultRecord);								
							}
						}
						Layout.hideWaitPopup();
					}
				}, new DSRequest());
			}

		}

		@Override
		public void manageMultiLookupBack(Record record) {
			multiLookupList.add(record);
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			if (multiLookupList != null) {
				for (int i = 0; i < multiLookupList.size(); i++) {
					Record values = multiLookupList.get(i);
					if (values.getAttribute("idUdFolder").equals(record.getAttribute("id"))) {
						multiLookupList.remove(i);
						break;
					}
				}
			}
		}

		@Override
		public void manageLookupBack(Record record) {
		}

	}
	
	public String getFinalitaAggiungiDocumentoDaConfrontare() {
		return "SEL_ATTI";
	}
	
	public void aggiungiDocumentiInLista (Record recordToAdd) {
		boolean aggiungiDocumento = true;
		String idUdDocumentoDaAggiungere = recordToAdd.getAttribute("idUd");
		ReplicableCanvas[] elencoCanvas = getAllCanvas();
		if (elencoCanvas != null) {
			for (ReplicableCanvas replicableCanvas : elencoCanvas) {
				if (replicableCanvas instanceof ElencoDocumentiDaConfrontareCanvas) {
					ElencoDocumentiDaConfrontareCanvas lElencoDocumentiDaConfrontareCanvas = (ElencoDocumentiDaConfrontareCanvas) replicableCanvas;
					String idUdDocumentoCanvas = lElencoDocumentiDaConfrontareCanvas.getIdUdDocumento();
					if (idUdDocumentoDaAggiungere != null && idUdDocumentoDaAggiungere.equalsIgnoreCase(idUdDocumentoCanvas)) {
						aggiungiDocumento = false;
						break;
					}
				}
			}
		}
		if (aggiungiDocumento) {
			ElencoDocumentiDaConfrontareCanvas lastCanvas = (ElencoDocumentiDaConfrontareCanvas) getLastCanvas();
			if (lastCanvas != null && !lastCanvas.isValid()) {
				lastCanvas.setFormValuesFromRecordAggiungiDocumento(recordToAdd);
			} else {
				ElencoDocumentiDaConfrontareCanvas canvas = (ElencoDocumentiDaConfrontareCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordAggiungiDocumento(recordToAdd);										
			}
		}
	}

}

