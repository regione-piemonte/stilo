/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.DetailToolStripButton;
import it.eng.utility.ui.module.layout.client.portal.ModalWindow;

public class SelezionaTipoAttoWindow extends ModalWindow {

	private SelezionaTipoAttoWindow instance;
	private SelezionaTipoAttoDetail detail; 
	
	private ToolStrip detailToolStrip;
	private DetailToolStripButton avvioButton;
	
	private String idSeduta;
	private String organoCollegiale;
	private String finalitaOrgColl;
	private BooleanCallback afterAvvioCallBack;
	
	public SelezionaTipoAttoWindow() {
		this(null,null,null,null);
	}
	
	public SelezionaTipoAttoWindow(String idSeduta, String organoCollegiale, String finalitaOrgColl,
			final BooleanCallback afterAvvioCallBack) {
		super("seleziona_tipo_atto", true);
		
		instance = this;
		
		setModalMaskOpacity(50);
		setAutoCenter(true);
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_COPIA_ALLEGATI_ATTO")) {
			setWidth(850);
		} else {
			setWidth(730);
		}
		setHeight(200);
		setKeepInParentRect(true);
		
		this.idSeduta = idSeduta;
		this.organoCollegiale = organoCollegiale;
		this.finalitaOrgColl = finalitaOrgColl;
		this.afterAvvioCallBack = afterAvvioCallBack;
		
		if(isOrganoCollegiale()){
			setTitle("Seleziona tipo di atto/documento da aggiungere in seduta");
		} else {
			setTitle("Seleziona tipo atto");
		}
		
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		
		detail = new SelezionaTipoAttoDetail(instance, organoCollegiale);
		detail.setHeight100();
		detail.setAlign(Alignment.CENTER);	
		
		avvioButton = new DetailToolStripButton("Avvia", "menu/iter_atti.png");
		avvioButton.addClickHandler(new ClickHandler() { 
			
			@Override
			public void onClick(ClickEvent event) { 
				onSaveButtonClick();				
			}   
		}); 		

		detailToolStrip = new ToolStrip();   
		detailToolStrip.setWidth100();       
		detailToolStrip.setHeight(30);
		detailToolStrip.setStyleName(it.eng.utility.Styles.detailToolStrip);
		detailToolStrip.addFill(); //push all buttons to the right 
		detailToolStrip.addButton(avvioButton);
		
		VLayout detailLayout = new VLayout();  
		detailLayout.setMembers(detail, detailToolStrip);
		detailLayout.setHeight100();
		detailLayout.setWidth100();		
		
		setBody(detailLayout);
				
		setShowTitle(true);
		setOverflow(Overflow.AUTO);    			
		setHeaderIcon("menu/iter_atti.png");
	}
		
	public void onSaveButtonClick() {
		if (detail.validate()) {
			final Record lFormRecord = detail.getValuesManager().getValuesAsRecord();
			if (detail.isNuovoComeCopia()) {
				GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("NuovaPropostaAtto2CompletaDataSource");
				lGWTRestDataSource.executecustom("nuovoComeCopia", lFormRecord, new DSCallback() {

					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {

						Record[] data = response.getData();

						if (data.length > 0) {					
							final Record lRecordCopia = data[0];
							lFormRecord.setAttribute("recordInitialValues", lRecordCopia);
////							recordAvvio.setAttribute("idUd", lRecordCopia.getAttributeAsString("idUd"));
//							recordAvvio.setAttribute("idTipoDocProposta", lRecordCopia.getAttribute("tipoDocumento"));
//							recordAvvio.setAttribute("nomeTipoProc", lFormRecord.getAttribute("nomeTipoProc"));
//							recordAvvio.setAttribute("idTipoProc", lFormRecord.getAttribute("idTipoProc"));
//							recordAvvio.setAttribute("idTipo", lFormRecord.getAttribute("idTipoProc"));
//							recordAvvio.setAttribute("idProcess", lRecordCopia.getAttribute("idProcess"));
//							recordAvvio.setAttribute("nuovoComeCopia", true);
							
							if(isOrganoCollegiale()){
								lFormRecord.setAttribute("idSeduta", idSeduta);
								lFormRecord.setAttribute("organoCollegiale", organoCollegiale);
								lFormRecord.setAttribute("finalitaOrgColl", finalitaOrgColl);
								
								AurigaLayout.avviaPratica(lFormRecord, afterAvvioCallBack);
							} else {
								AurigaLayout.avviaPratica(lFormRecord);
							}

							instance.markForDestroy();		
						}
					}
				});
			} else {
				if(isOrganoCollegiale()){
					lFormRecord.setAttribute("idSeduta", idSeduta);
					lFormRecord.setAttribute("organoCollegiale", organoCollegiale);
					lFormRecord.setAttribute("finalitaOrgColl", finalitaOrgColl);
					
					AurigaLayout.avviaPratica(lFormRecord, afterAvvioCallBack);
				} else {
					AurigaLayout.avviaPratica(lFormRecord);
				}
				instance.markForDestroy();		
			}	
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}
	
	private Boolean isOrganoCollegiale() {
		return (idSeduta != null && !"".equalsIgnoreCase(idSeduta));
	}
	
}