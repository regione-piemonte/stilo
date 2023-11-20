/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.protocollazione.PreviewLayout;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomSimpleTree;
import it.eng.utility.ui.module.layout.client.common.file.CssAndDimensionFileInput;
import it.eng.utility.ui.module.layout.client.common.file.FileUploadItemWithFirmaAndMimeType;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class WizardDocumentTree extends CustomSimpleTree {
	
	private WizardLayout wizardLayout;
	
	public WizardDocumentTree(String nomeEntita, WizardLayout pWizardLayout, String idProcess,String idUd) {
	
		super("scrivania");
		
		wizardLayout = pWizardLayout;
		
		TreeGridField nomeField = new TreeGridField("nome");
		nomeField.setHidden(true);
		
		TreeGridField uploadField = new TreeGridField("upload","");
		uploadField.setBaseStyle(it.eng.utility.Styles.cabinetVuotoImage);
		setShowRecordComponents(true);
		setShowHover(false);
		setCursor(Cursor.DEFAULT);
		
		setFields(nomeField, uploadField);  
		GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("WizardDocumentiTreeDatasource", "idDocumento", FieldType.TEXT);
		lGwtRestDataSource.extraparam.put("idProcess", idProcess);
		lGwtRestDataSource.extraparam.put("idUd", idUd);
		
		setDataSource(lGwtRestDataSource);
		setCellHeight(2);
		
	}

	protected void manageRecordClick(Record record) {
		if (record.getAttribute("uri")!= null && 
				!record.getAttribute("uri").trim().equals("")){
			Object infoFile = record.getAttributeAsObject("info");
			boolean remoteUri = record.getAttributeAsBoolean("remoteUri");
			InfoFileRecord info = new InfoFileRecord(infoFile);
			@SuppressWarnings("unused")
			PreviewLayout lPreviewLayout = new PreviewLayout(record.getAttribute("uri"), remoteUri, 
					info, "FileToExtractBean", wizardLayout.getViewer(), wizardLayout, false);
		} else {
			wizardLayout.showNoFile();
		}
	}

	@Override
	protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {
		String nome = record.getAttribute("nome");	
		String uri = record.getAttribute("uri");	
		if (getFieldName(colNum).equals("upload")){
			VLayout vLayout = new VLayout();
			vLayout.setPadding(0);
			vLayout.setMembersMargin(0);
			Label labelNome = new Label("<i>" + ((nome.length() <= 20) ? nome : nome.substring(0, 20) + "...") + "</i>");
			labelNome.setWrap(false);
			labelNome.setPrompt(nome);
			labelNome.setHeight(20);
			String immagine ="vuotoImage";
			String cursor="";
			String prompt="Selezionare un file da caricare";
			if(uri != null && !"".equals(uri)) {
				immagine = "pdfImage";
				cursor = "cursor:pointer";
				prompt = "Visualizza file";
			}
			Label labelImg = new Label("<img src='layout/sc/skins/auriga/images/file/"+immagine+".png' style='height:75px;display:block;"+cursor+"' />");
			labelImg.setPrompt(prompt);
			labelImg.setHeight(80);
			labelImg.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					manageRecordClick(record);
				}   				
			});
			DynamicForm lDynamicForm = new DynamicForm();			
			lDynamicForm.setHeight(2);
			lDynamicForm.setShowHover(false);
			lDynamicForm.setCursor(Cursor.DEFAULT);
			lDynamicForm.setOverflow(Overflow.HIDDEN);
			lDynamicForm.setKeepInParentRect(true);
			TreeUploadEnd lTreeUploadEnd = new TreeUploadEnd(this, record);
			CssAndDimensionFileInput lCssAndDimensionFileInput = new CssAndDimensionFileInput();
			lCssAndDimensionFileInput.setHeight(2);
			lCssAndDimensionFileInput.setWidth(2);
			lCssAndDimensionFileInput.setCssClass("cabinetVuoto");
			lCssAndDimensionFileInput.setCursor(Cursor.DEFAULT);
			lCssAndDimensionFileInput.setShowHover(false);
			FileUploadItemWithFirmaAndMimeType lFileUploadItemWithFirmaAndMimeType = new FileUploadItemWithFirmaAndMimeType(lTreeUploadEnd, lTreeUploadEnd, lCssAndDimensionFileInput);
			lFileUploadItemWithFirmaAndMimeType.setWidth(2);
			lFileUploadItemWithFirmaAndMimeType.setHeight(2);			
			lDynamicForm.setFields(lFileUploadItemWithFirmaAndMimeType);
			vLayout.setHeight(2);
			vLayout.setWidth(2);
			vLayout.setCursor(Cursor.DEFAULT);
			vLayout.addMember(labelNome);
			vLayout.addMember(labelImg);
			vLayout.addMember(lDynamicForm);			
			return vLayout;
		}
		return null;
	}

	public void updateBean(Record lRecord) {
//		Record lRecord = new Record();
//		lRecord.setAttribute("id", idTipoDocumento);
//		lRecord.setAttribute("uri", uri);
		updateData(lRecord, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				manageUpdateResponse(response, rawData, request);
			}
		});
		
	}

	protected void manageUpdateResponse(DSResponse response, Object rawData,DSRequest request) {
		if (response.getStatus() == DSResponse.STATUS_SUCCESS){
			fetchData();
			refreshFields();
			redraw();
		}
	}

//	public void updateBean(Record lRecord) {
////		Record lRecord = new Record();
////		lRecord.setAttribute("id", idTipoDocumento);
////		lRecord.setAttribute("info", info);
//		getDataSource().updateData(lRecord, new DSCallback() {
//			@Override
//			public void execute(DSResponse response, Object rawData, DSRequest request) {
//				manageUpdateResponse(response, rawData, request);
//			}
//		});
//	}

	public WizardLayout getWizardLayout() {
		return wizardLayout;
	}

	public void setWizardLayout(WizardLayout wizardLayout) {
		this.wizardLayout = wizardLayout;
	}
}