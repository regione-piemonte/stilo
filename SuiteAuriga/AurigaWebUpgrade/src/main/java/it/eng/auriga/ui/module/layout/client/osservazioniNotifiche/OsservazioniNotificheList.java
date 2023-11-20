/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class OsservazioniNotificheList extends CustomList {
	
	private ListGridField idOsservazioneNotifica;
	private ListGridField livelloPriorita;
	private ListGridField dataOsservazioneNotifica;
	private ListGridField mittenteOsservazioneNotifica;
	private ListGridField nominativoDelegato;
	private ListGridField messaggioOsservazioneNotifica;
	private ListGridField messaggioOsservazioneNotificaHTML;
	private ListGridField destinatariOsservazioneNotifica;
	private ListGridField flgPersonaleOsservazioneNotifica;
	private ListGridField flgEsclusivoOsservazioneNotifica;

	public OsservazioniNotificheList(String nomeEntita) {

		super(nomeEntita, false);		
		
		idOsservazioneNotifica = new ListGridField("idOsservazioneNotifica");
		idOsservazioneNotifica.setCanHide(false); 
		idOsservazioneNotifica.setCanGroupBy(false);   
		idOsservazioneNotifica.setHidden(true);

		livelloPriorita = new ListGridField("livelloPriorita", I18NUtil.getMessages().osservazioniNotifiche_list_priorita_title());
		livelloPriorita.setType(ListGridFieldType.ICON);
		livelloPriorita.setWidth(30);
		livelloPriorita.setIconWidth(16);
		livelloPriorita.setIconHeight(16);
		Map<String, String> flginiziativaIcons = new HashMap<String, String>();
		flginiziativaIcons.put("-1", "prioritaBassa.png");
		flginiziativaIcons.put("0",  "prioritaMedia.png");
		flginiziativaIcons.put("1",  "prioritaAlta.png");
		flginiziativaIcons.put("2",  "prioritaAltissima.png");
		flginiziativaIcons.put("",  "blank.png");
		livelloPriorita.setValueIcons(flginiziativaIcons);
		livelloPriorita.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String valuePriorita = record.getAttribute("livelloPriorita");
				Integer priorita = valuePriorita != null && !"".equals(String.valueOf(valuePriorita)) ? new Integer(String.valueOf(valuePriorita)) : null;
				if(priorita != null) {
					String res = "";
					switch(priorita){				
						case -1:		// priorita' bassa
							res = I18NUtil.getMessages().prioritaBassa_Alt_value();
							break;
						case 0:		// priorita' normale
							res = I18NUtil.getMessages().prioritaNormale_Alt_value();
							break;
						case 1:		// priorita' alta
							res = I18NUtil.getMessages().prioritaAlta_Alt_value();
							break;
						case 2:		// priorita' altissima
							res = I18NUtil.getMessages().prioritaAltissima_Alt_value();
							break;
					} 
					return res;
				}
				return null;	
			}
		});

		dataOsservazioneNotifica = new ListGridField("dataOsservazioneNotifica", I18NUtil.getMessages().osservazioniNotifiche_list_dataNotifica_title());
		dataOsservazioneNotifica.setType(ListGridFieldType.DATE);
		dataOsservazioneNotifica.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		mittenteOsservazioneNotifica = new ListGridField("mittenteOsservazioneNotifica", I18NUtil.getMessages().osservazioniNotifiche_list_mittente_title());
		
		nominativoDelegato = new ListGridField("nominativoDelegato", I18NUtil.getMessages().osservazioniNotifiche_list_aNomeDi_title());
		
		messaggioOsservazioneNotificaHTML = new ListGridField("messaggioOsservazioneNotificaHTML");
		messaggioOsservazioneNotificaHTML.setCanHide(false); 
		messaggioOsservazioneNotificaHTML.setCanGroupBy(false);   
		messaggioOsservazioneNotificaHTML.setHidden(true);
		
		messaggioOsservazioneNotifica = new ListGridField("messaggioOsservazioneNotifica", I18NUtil.getMessages().osservazioniNotifiche_list_messaggio_title());
		messaggioOsservazioneNotifica.setType(ListGridFieldType.TEXT);
		messaggioOsservazioneNotifica.setCellAlign(Alignment.LEFT);
		messaggioOsservazioneNotifica.setAttribute("custom", true);
		messaggioOsservazioneNotifica.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				event.cancel();
				
				Object realValue = event.getRecord() != null ? event.getRecord().getAttribute("realValue"+event.getFieldNum()) : null;
				String msgOssNtfTEXT = (realValue != null) ? (String) realValue : (String) event.getRecord().getAttribute("messaggioOsservazioneNotifica");
				String msgOssNtfHTML = (realValue != null) ? (String) realValue : (String) event.getRecord().getAttribute("messaggioOsservazioneNotificaHTML");
				Record record = new Record();
				if (msgOssNtfHTML != null && !"".equals(msgOssNtfHTML)) {
					record.setAttribute("message", msgOssNtfHTML);
					new VisualizzaMessaggioOsservazioni("visualizza_messaggio_ossservazioni",record);
				}else{
					record.setAttribute("message", msgOssNtfTEXT);
					new VisualizzaMessaggioOsservazioni("visualizza_messaggio_ossservazioni",record);
				}
			}
		});
		
		destinatariOsservazioneNotifica = new ListGridField("destinatariOsservazioneNotifica", I18NUtil.getMessages().osservazioniNotifiche_list_destinatario_title());
		
		flgPersonaleOsservazioneNotifica = new ListGridField("flgPersonaleOsservazioneNotifica", I18NUtil.getMessages().osservazioniNotifiche_list_personale_title());
		flgPersonaleOsservazioneNotifica.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgPersonaleOsservazioneNotifica.setType(ListGridFieldType.ICON);
		flgPersonaleOsservazioneNotifica.setAlign(Alignment.CENTER);
		flgPersonaleOsservazioneNotifica.setWrap(false);
		flgPersonaleOsservazioneNotifica.setWidth(30);
		flgPersonaleOsservazioneNotifica.setIconWidth(16);
		flgPersonaleOsservazioneNotifica.setIconHeight(16);
		flgPersonaleOsservazioneNotifica.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgPersonaleOsservazioneNotifica = (String) record.getAttribute("flgPersonaleOsservazioneNotifica");
				if (flgPersonaleOsservazioneNotifica != null && "true".equals(flgPersonaleOsservazioneNotifica)) {
					return buildIconHtml("osservazione_personale.png");
				}
				return null;
			}
		});
		flgPersonaleOsservazioneNotifica.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNoAssocAuto = (String) record.getAttribute("flgPersonaleOsservazioneNotifica");
				if (flgNoAssocAuto != null && "true".equals(flgNoAssocAuto)) {
					return "Assegnato personalmente a me";
				}
				return null;
			}
		});
				
		flgEsclusivoOsservazioneNotifica = new ListGridField("flgEsclusivoOsservazioneNotifica", I18NUtil.getMessages().osservazioniNotifiche_list_esclusivo_title());
		flgEsclusivoOsservazioneNotifica.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		flgEsclusivoOsservazioneNotifica.setType(ListGridFieldType.ICON);
		flgEsclusivoOsservazioneNotifica.setAlign(Alignment.CENTER);
		flgEsclusivoOsservazioneNotifica.setWrap(false);
		flgEsclusivoOsservazioneNotifica.setWidth(32);
		flgEsclusivoOsservazioneNotifica.setIconWidth(32);
		flgEsclusivoOsservazioneNotifica.setIconHeight(32);
		flgEsclusivoOsservazioneNotifica.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNoAssocAuto = (String) record.getAttribute("flgEsclusivoOsservazioneNotifica");
				if (flgNoAssocAuto != null && "true".equals(flgNoAssocAuto)) {
					return buildIconHtml("osservazione_esclusiva.png");
				}
				return null;
			}
		});
		flgEsclusivoOsservazioneNotifica.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgNoAssocAuto = (String) record.getAttribute("flgEsclusivoOsservazioneNotifica");
				if (flgNoAssocAuto != null && "true".equals(flgNoAssocAuto)) {
					return "Assegnato esclusivamente a me";
				}
				return null;
			}
		});
	
		setFields(new ListGridField[] { 
				 messaggioOsservazioneNotificaHTML,
				 idOsservazioneNotifica,
				 livelloPriorita,
				 dataOsservazioneNotifica,
				 mittenteOsservazioneNotifica,
				 nominativoDelegato,
				 messaggioOsservazioneNotifica,
				 destinatariOsservazioneNotifica,
				 flgPersonaleOsservazioneNotifica,
				 flgEsclusivoOsservazioneNotifica
				 }
		);  

	}	

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {

		return true;
	};
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
