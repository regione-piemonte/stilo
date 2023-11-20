/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;

import it.eng.utility.ui.module.layout.client.common.NestedFormItem;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * 
 * @author DANIELE CRISTIANO
 *
 */

public class DestinatariPrincipaliCanvas extends ReplicableCanvas{

	private ReplicableCanvasForm mDynamicForm;
	protected TextItem indirizzoDestinatariPrincipali;
	protected StaticTextItem statoTrasmissioneDestinatariPrincipali;
	protected TextItem estremiProtocolloDestinatariPrincipali;
	protected StaticTextItem pervenutaLetturaDestinatariPrincipali;
	protected NestedFormItem infoImageDestinatariPrincipaliItem;
	protected StaticTextItem pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali;
	protected StaticTextItem pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali;
	protected StaticTextItem pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali;
	protected StaticTextItem pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali;
	
	protected HiddenItem statoConsegnaItem;
	protected HiddenItem msgErrMancataConsegnaDestItem;

	public DestinatariPrincipaliCanvas(DestinatariPrincipaliItem item){
		super(item);
	}

	public DestinatariPrincipaliCanvas(DestinatariPrincipaliItem item, HashMap<String,String> parameters) {
		super(item, parameters);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setLeft(-2);

		indirizzoDestinatariPrincipali = new TextItem("indirizzo","Indirizzo");
		indirizzoDestinatariPrincipali.setWidth(350);
		indirizzoDestinatariPrincipali.setShowTitle(false);
		
		statoConsegnaItem = new HiddenItem("statoConsegna");
		statoConsegnaItem.setVisible(false);
		
		msgErrMancataConsegnaDestItem = new HiddenItem("msgErrMancataConsegnaDestinatario");
		msgErrMancataConsegnaDestItem.setVisible(false);

		statoTrasmissioneDestinatariPrincipali = new StaticTextItem("iconaStatoConsegna");
		statoTrasmissioneDestinatariPrincipali.setWidth(150);
		statoTrasmissioneDestinatariPrincipali.setShowValueIconOnly(true);
		statoTrasmissioneDestinatariPrincipali.setShowTitle(false); 
		
		/*Map<String, String> lMap3 = new HashMap<String, String>();
		lMap3.put("consegnata", "postaElettronica/statoConsolidamento/consegnata.png");
		lMap3.put("errori in consegna", "postaElettronica/statoConsolidamento/errori_in_consegna.png");
		lMap3.put("avvertimenti in consegna", "postaElettronica/statoConsolidamento/avvertimenti_in_consegna.png");
		lMap3.put("accettata", "postaElettronica/statoConsolidamento/accettata.png");
		lMap3.put("non accettata", "postaElettronica/statoConsolidamento/non_accettata.png");
		lMap3.put("consegnata parzialmente", "postaElettronica/statoConsolidamento/consegnata_parzialmente.png");
		lMap3.put("in invio", "postaElettronica/statoConsolidamento/in_invio.png");*/
		
		
		Map<String, String> lMap3 = new HashMap<String, String>();
		lMap3.put("OK", "postaElettronica/statoConsolidamento/consegnata.png");
		lMap3.put("KO", "postaElettronica/statoConsolidamento/ko-red.png");
		lMap3.put("IP", "postaElettronica/statoConsolidamento/presunto_ok.png");
		lMap3.put("W", "postaElettronica/statoConsolidamento/ko-arancione.png");
		lMap3.put("ND", "postaElettronica/statoConsolidamento/stato_consegna.png");
		
		
		
		statoTrasmissioneDestinatariPrincipali.setValueIcons(lMap3);
		statoTrasmissioneDestinatariPrincipali.setColSpan(1);
		statoTrasmissioneDestinatariPrincipali.setWidth(20);
		statoTrasmissioneDestinatariPrincipali.setIconWidth(16);
		statoTrasmissioneDestinatariPrincipali.setIconHeight(16);	
		statoTrasmissioneDestinatariPrincipali.setIconVAlign(VerticalAlignment.BOTTOM);
		statoTrasmissioneDestinatariPrincipali.setCellStyle(it.eng.utility.Styles.staticTextItem);
		statoTrasmissioneDestinatariPrincipali.setItemHoverFormatter(new FormItemHoverFormatter() {
			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				/*if(form.getValue("statoConsolidamento")!= null && form.getValue("statoConsolidamento").equals("consegnata"))
					return "Consegnata";
				else if(form.getValue("statoConsolidamento")!= null && form.getValue("statoConsolidamento").equals("errori in consegna"))
					return "Errori in consegna";
				else if(form.getValue("statoConsolidamento")!= null && form.getValue("statoConsolidamento").equals("avvertimenti in consegna"))
					return "Avvertimenti in consegna";
				else if(form.getValue("statoConsolidamento")!= null && form.getValue("statoConsolidamento").equals("accettata"))
					return "Accettata"; 
				else if(form.getValue("statoConsolidamento")!= null && form.getValue("statoConsolidamento").equals("non accettata"))
					return "Non accettata"; 
				else if(form.getValue("statoConsolidamento")!= null && form.getValue("statoConsolidamento").equals("consegnata parzialmente"))
					return "Consegnata parzialmente"; 
				else if(form.getValue("statoConsolidamento")!= null && form.getValue("statoConsolidamento").equals("in invio"))
					return "In invio"; */
				
				String result = "";
				Record statoConsegnaRecord = new Record(form.getValues());
				String stato = statoConsegnaRecord.getAttribute("iconaStatoConsegna");
				String statoInvio = statoConsegnaRecord.getAttribute("statoConsegna");
				if(statoConsegnaRecord.getAttributeAsString("msgErrMancataConsegnaDestinatario") != null
						&& !"".equals(statoConsegnaRecord.getAttributeAsString("msgErrMancataConsegnaDestinatario")) 
						&& ("KO".equals(stato) || "W".equals(stato)) ) {
					result = statoInvio + " - " + statoConsegnaRecord.getAttributeAsString("msgErrMancataConsegnaDestinatario");
				} else {
					result = statoInvio;
				}
				
				return result;
				
			}
		});

		pervenutaLetturaDestinatariPrincipali = new StaticTextItem("flgRicevutaLettura");
		pervenutaLetturaDestinatariPrincipali.setWidth(150);
		pervenutaLetturaDestinatariPrincipali.setShowValueIconOnly(true);
		pervenutaLetturaDestinatariPrincipali.setShowTitle(false);  
		Map<String, String> lMap4 = new HashMap<String, String>();
		lMap4.put("true", "postaElettronica/flgRicevutaLettura.png");
		lMap4.put("false ", "blank.png");
		pervenutaLetturaDestinatariPrincipali.setValueIcons(lMap4);
		pervenutaLetturaDestinatariPrincipali.setColSpan(1);
		pervenutaLetturaDestinatariPrincipali.setWidth(20);
		pervenutaLetturaDestinatariPrincipali.setIconWidth(16);
		pervenutaLetturaDestinatariPrincipali.setIconHeight(16);	
		pervenutaLetturaDestinatariPrincipali.setIconVAlign(VerticalAlignment.BOTTOM);
		pervenutaLetturaDestinatariPrincipali.setCellStyle(it.eng.utility.Styles.staticTextItem);
		pervenutaLetturaDestinatariPrincipali.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("flgRicevutaLettura") != null && form.getValue("flgRicevutaLettura").equals("1");
			}
		});
		pervenutaLetturaDestinatariPrincipali.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Pervenuta ricevuta di avvenuta lettura";
			}
		});
	
		final SpacerItem spacerItemFirmaFile = new SpacerItem(); 
		spacerItemFirmaFile.setColSpan(1);
		spacerItemFirmaFile.setWidth(20);		
    	
		ImgButtonItem spacerFileItem = new ImgButtonItem("flgRicevutaLettura", "blank.png", "");
		spacerFileItem.setShowTitle(false);
		spacerFileItem.setAlwaysEnabled(true);
		spacerFileItem.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean showSpacerFile = form.getValue("flgRicevutaLettura") == null;//form.getValue("flgRicevutaLettura") != null && new Boolean(form.getValueAsString("flgRicevutaLettura"));
				spacerItemFirmaFile.setVisible(showSpacerFile);
				return showSpacerFile;
			}
		});
		
		
		estremiProtocolloDestinatariPrincipali  = new TextItem("estremiProt","Prot. assegnato");
		estremiProtocolloDestinatariPrincipali.setWidth(350);
		estremiProtocolloDestinatariPrincipali.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isCategoriaInteropSegn();
			}
		});
		
		infoImageDestinatariPrincipaliItem = new NestedFormItem("infoButtonsEstremi");
		infoImageDestinatariPrincipaliItem.setShowTitle(false);
		infoImageDestinatariPrincipaliItem.setColSpan(1);
		infoImageDestinatariPrincipaliItem.setEndRow(true);		
		infoImageDestinatariPrincipaliItem.setWidth(100);
		infoImageDestinatariPrincipaliItem.setNumCols(6);
		infoImageDestinatariPrincipaliItem.setColWidths(16,16,16);
		infoImageDestinatariPrincipaliItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return isCategoriaInteropSegn();
			}
		});

		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali = new StaticTextItem("flgNotifInteropConf");
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setWidth(150);
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setShowValueIconOnly(true);
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setShowTitle(false); 
		Map<String, String> lMap5 = new HashMap<String, String>();
		lMap5.put("1", "postaElettronica/notifInteropConf.png");
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setValueIcons(lMap5);
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setColSpan(1);
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setWidth(20);
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setIconWidth(16);
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setIconHeight(16);	
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setIconVAlign(VerticalAlignment.BOTTOM);
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setCellStyle(it.eng.utility.Styles.staticTextItem);
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return form.getValue("flgNotifInteropConf") != null && form.getValue("flgNotifInteropConf").equals("1");
			}
		});
		pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Pervenuta/e notifiche interoperabili di conferma";
			}
		});
		
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali = new StaticTextItem("flgNotifInteropEcc");
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setWidth(150);
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setShowValueIconOnly(true);
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setShowTitle(false); 
		Map<String, String> lMap6 = new HashMap<String, String>();
		lMap6.put("1", "postaElettronica/notifInteropEcc.png");
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setValueIcons(lMap6);
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setColSpan(1);
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setWidth(20);
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setIconWidth(16);
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setIconHeight(16);	
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setIconVAlign(VerticalAlignment.BOTTOM);
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setCellStyle(it.eng.utility.Styles.staticTextItem);
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
			
				return form.getValue("flgNotifInteropEcc") != null && form.getValue("flgNotifInteropEcc").equals("1");
			}
		});
		pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Pervenuta/e notifiche interoperabili di eccezione";
			}
		});
		
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali = new StaticTextItem("flgNotifInteropAgg");
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setWidth(150);
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setShowValueIconOnly(true);
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setShowTitle(false); 
		Map<String, String> lMap7 = new HashMap<String, String>();
		lMap7.put("1", "postaElettronica/notifInteropAgg.png");
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setValueIcons(lMap7);
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setColSpan(1);
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setWidth(20);
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setIconWidth(16);
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setIconHeight(16);	
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setIconVAlign(VerticalAlignment.BOTTOM);
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setCellStyle(it.eng.utility.Styles.staticTextItem);
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
			
				return form.getValue("flgNotifInteropAgg") != null && form.getValue("flgNotifInteropAgg").equals("1");
			}
		});
		pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Pervenuta/e notifiche interoperabili di aggiornamento";
			}
		});
		
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali = new StaticTextItem("flgNotifInteropAnn");
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setWidth(150);
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setShowValueIconOnly(true);
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setShowTitle(false); 
		Map<String, String> lMap8 = new HashMap<String, String>();
		lMap8.put("1", "postaElettronica/notifInteropAnn.png");
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setValueIcons(lMap8);
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setColSpan(1);
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setWidth(20);
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setIconWidth(16);
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setIconHeight(16);	
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setIconVAlign(VerticalAlignment.BOTTOM);
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setCellStyle(it.eng.utility.Styles.staticTextItem);
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
			
				return form.getValue("flgNotifInteropAnn") != null && form.getValue("flgNotifInteropAnn").equals("1");
			}
		});
		pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali.setItemHoverFormatter(new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return "Pervenuta/e notifiche interoperabili di annullamento protocollazione";
			}
		});
		
		infoImageDestinatariPrincipaliItem.setNestedFormItems(
				pervenutaNotificheInteroperabiliConfermaDestinatariPrincipali,
				pervenutaNotificheInteroperabiliEccezioneDestinatariPrincipali,
				pervenutaNotificheInteroperabiliAggiornamentoDestinatariPrincipali,
				pervenutaNotificheInteroperabiliAnnullamentoDestinatariPrincipali
				);
		mDynamicForm.setFields(
				indirizzoDestinatariPrincipali,
				statoTrasmissioneDestinatariPrincipali,
				pervenutaLetturaDestinatariPrincipali,
				spacerItemFirmaFile,
				estremiProtocolloDestinatariPrincipali,
				infoImageDestinatariPrincipaliItem,
				statoConsegnaItem,
				msgErrMancataConsegnaDestItem
				);	

		mDynamicForm.setNumCols(20);

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
	}

	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
	}
	private boolean isCategoriaInteropSegn(){
		final Record detailRecord = ((DestinatariPrincipaliItem)getItem()).getDetailRecord();
		return detailRecord != null && detailRecord.getAttribute("categoria") != null && detailRecord.getAttribute("categoria").equals("INTEROP_SEGN");
	}
}
