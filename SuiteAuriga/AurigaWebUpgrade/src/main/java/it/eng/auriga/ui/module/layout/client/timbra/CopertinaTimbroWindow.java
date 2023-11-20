/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.print.PreviewControl;
import it.eng.auriga.ui.module.layout.client.protocollazione.VisualizzaFatturaWindow;
import it.eng.utility.ui.module.core.client.callback.ServiceCallback;
import it.eng.utility.ui.module.core.client.datasource.GWTRestService;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.file.InfoFileRecord;

/**
 * 
 * @author DANCRIST
 *
 */

public class CopertinaTimbroWindow extends Window {
	
	protected CopertinaTimbroWindow window;
	private CopertinaTimbroForm form;
	private Boolean isMultiplo;
	private String provenienza; 
	private String tipologia;
	private String posizionale;
	
	public CopertinaTimbroWindow(String nomeEntita, boolean isJustWindow, CopertinaTimbroBean pCopertinaTimbroBean){
		this(nomeEntita,isJustWindow,pCopertinaTimbroBean,null,null,null,null);
	}
	
	/**
	 * 
	 * @param nomeEntita
	 * @param isJustWindow
	 * @param pCopertinaTimbroBean
	 * @param isMultiplo
	 * @param provenienza: SE PROVENGO DA FILE PRIMARIO O ALLEGATO
	 * @param tipologia :  SE T = TIPOLOGIA , NULL = SEGNATURA
	 */
	public CopertinaTimbroWindow(String nomeEntita, boolean isJustWindow, CopertinaTimbroBean pCopertinaTimbroBean,Boolean isMultiplo,
			String provenienza,String tipologia,String posizionale) {
		
		window = this;
		
		this.isMultiplo = isMultiplo;
		this.provenienza = provenienza;
		this.tipologia = tipologia;
		this.posizionale = posizionale;
		
		setIsModal(true);
		setModalMaskOpacity(50);
		setAutoCenter(true);
		setWidth(560);		
		setHeight(180);
		setKeepInParentRect(true);
		setTitle("Opzioni barcode");		
		setShowModalMask(true);
		setShowCloseButton(true);
		setShowMaximizeButton(false);
		setShowMinimizeButton(false);
		setShowTitle(true);	
		setHeaderControls(HeaderControls.HEADER_LABEL, HeaderControls.CLOSE_BUTTON);
				
		form = new CopertinaTimbroForm(pCopertinaTimbroBean,isMultiplo);
		
		Button confermaButton = new Button("Stampa");
		confermaButton.setIcon("ok.png");
		confermaButton.setIconSize(16); 
		confermaButton.setAutoFit(false);
		confermaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				
				if(form.validate()) {
					clickGeneraBarcode();
				}
			}
		});
				
		Button annullaButton = new Button("Annulla");
		annullaButton.setIcon("annulla.png");
		annullaButton.setIconSize(16); 
		annullaButton.setAutoFit(false);
		annullaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				close();
			}
		});		
				
		HStack buttons = new HStack(5);
		buttons.setAlign(Alignment.CENTER);
		buttons.setPadding(5);
		buttons.addMember(confermaButton);		
		buttons.addMember(annullaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);
		
		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
				
		layout.addMember(form);		
		layout.addMember(buttons);
		
		addItem(layout);	
		
		addCloseClickHandler(new CloseClickHandler() {
			
			@Override
			public void onCloseClick(CloseClickEvent event) {
				close();
			}
		});		
		
		setHeaderIcon("blank.png");
	}
	
	protected void clickGeneraBarcode() {
		Record lRecord = form.getValuesAsRecord();
		if (lRecord!=null){
			generaBarcode(lRecord);
		}
	}
	
	protected void generaBarcode(Record lRecord) {
		Layout.showWaitPopup("Generazione copertina in corso: potrebbe richiedere qualche secondo. Attendere...");
		GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("CopertinaTimbroDataSource");
		//STAMPA SINGOLA O MULTIPLA
		if(isMultiplo != null && isMultiplo){
			lGwtRestService.extraparam.put("isMultiplo", "true");
		}
		//PROVENIENZA= A allegato NULL file primario
		if(provenienza != null && "A".equals(provenienza)){
			lGwtRestService.extraparam.put("provenienza", "A");
		}
		//TIPOLOGIA= T tipologia NULL segnatura
		if(tipologia != null && "T".equals(tipologia)){
			lGwtRestService.extraparam.put("tipologia", "T");
		}
		//POSIZIONALE= P Nro documento + posizione NULL Nro documento
		if(posizionale != null && "P".equals(posizionale)){
			lGwtRestService.extraparam.put("posizionale", "P");
		}
		lGwtRestService.call(lRecord, new ServiceCallback<Record>() {
			@Override
			public void execute(Record object) {
				Layout.hideWaitPopup();
				if (object.getAttributeAsBoolean("result")){								
					copertinaCallBack(object);
					close();
				} else {
					Layout.addMessage(new MessageBean(object.getAttribute("error"), object.getAttribute("error"), MessageType.ERROR));
				}
			}
		});
	}
		
	private static void copertinaCallBack(Record object) {
		String uri = object.getAttribute("uri");
		String display = "Copertina.pdf";
		InfoFileRecord infoFile = new InfoFileRecord(object.getAttributeAsRecord("infoFile"));

		managePreviewFile(uri,display,false,infoFile);
	}
	
	public static void managePreviewFile(String uriFile,String nomeFile,Boolean remote,final InfoFileRecord infoFile) {
		
		final String display = nomeFile;
		final String uri = uriFile;
		final Boolean remoteUri = remote;
		if (infoFile != null && infoFile.getMimetype() != null && infoFile.getMimetype().equals("application/xml")) {
			Record lRecordFattura = new Record();
			lRecordFattura.setAttribute("uriFile", uri);
			lRecordFattura.setAttribute("remoteUri", remoteUri);
			GWTRestService<Record, Record> lGwtRestService = new GWTRestService<Record, Record>("VisualizzaFatturaDataSource");
			if (infoFile != null && infoFile.isFirmato() && infoFile.getTipoFirma().startsWith("CAdES")) {
				lGwtRestService.addParam("sbustato", "true");
			} else {
				lGwtRestService.addParam("sbustato", "false");
			}
			lGwtRestService.call(lRecordFattura, new ServiceCallback<Record>() {

				@Override
				public void execute(Record object) {

					if (object.getAttribute("html") != null && !"".equals(object.getAttribute("html"))) {
						VisualizzaFatturaWindow lVisualizzaFatturaWindow = new VisualizzaFatturaWindow(display, object);
						lVisualizzaFatturaWindow.show();
					} else {
						preview(display, uri, remoteUri, infoFile);
					}
				}
			});
		} else {
			preview(display, uri, remoteUri, infoFile);
		}
	}
	
	public static void preview(final String display, final String uri, final Boolean remoteUri, InfoFileRecord info) {
		PreviewControl.switchPreview(uri, remoteUri, info, "FileToExtractBean", display);
	}
	
	public void close() {
		window.markForDestroy();
	}
}