/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postainuscita.RicevutePostaInUscitaWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;

public class DettaglioEmailDestinatarioCanvas extends ReplicableCanvas {

	private ReplicableCanvasForm mDynamicForm;
		
	private HiddenItem idDestinatario;
	private TextItem account;
	private StaticTextItem statoConsolidamento;
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		account.setCanEdit(false);		
		statoConsolidamento.setCanEdit(true);
	}
	
	@Override
	public void disegna() {		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);		
		mDynamicForm.setNumCols(2);
		mDynamicForm.setColWidths("100", 20);
		
		idDestinatario = new HiddenItem("idDestinatario");

		account = new TextItem("account");
		account.setWidth(480);
		account.setShowTitle(false);		
		
		buildStatoConsolidamento();		
		
		mDynamicForm.setFields(idDestinatario, account, statoConsolidamento);		
		addChild(mDynamicForm);
	}

	private void buildStatoConsolidamento() {
		statoConsolidamento = new StaticTextItem("statoConsolidamento");
		statoConsolidamento.setShowValueIconOnly(true);
		statoConsolidamento.setShowTitle(false);  
		Map<String, String> lMap = new HashMap<String, String>();
		lMap.put("accettata", "postaElettronica/statoConsolidamento/accettata.png");
		lMap.put("avvertimenti_in_consegna", "postaElettronica/statoConsolidamento/avvertimenti_in_consegna.png");
		lMap.put("consegnata", "postaElettronica/statoConsolidamento/consegnata.png");
		lMap.put("consegnata_parzialmente", "postaElettronica/statoConsolidamento/consegnata_parzialmente.png");
		lMap.put("errori_in_consegna", "postaElettronica/statoConsolidamento/errori_in_consegna.png");
		lMap.put("non_accettata", "postaElettronica/statoConsolidamento/non_accettata.png");
		lMap.put("in_invio", "postaElettronica/statoConsolidamento/in_invio.png");
		statoConsolidamento.setValueIcons(lMap);
		statoConsolidamento.setColSpan(1);
		statoConsolidamento.setWidth(16);
		statoConsolidamento.setIconWidth(16);
		statoConsolidamento.setIconHeight(16);	
		statoConsolidamento.setIconVAlign(VerticalAlignment.BOTTOM);
		statoConsolidamento.setCellStyle(it.eng.utility.Styles.staticTextItem);		
		statoConsolidamento.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {	
				final String idEmailRif = ((DettaglioEmailDestinatarioItem)getItem()).getIdEmail();
				final String idDestinatario = mDynamicForm.getValueAsString("idDestinatario");
				GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("RicevutePostaInUscitaDataSource", "idEmail", FieldType.TEXT);
				lGWTRestDataSource.addParam("idEmailRif", idEmailRif);
				lGWTRestDataSource.addParam("idDestinatario", idDestinatario);
				lGWTRestDataSource.fetchData(null, new DSCallback() {					
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						
						RecordList data = response.getDataAsRecordList();   	
						if(data.getLength() == 0) {							
							Layout.addMessage(new MessageBean(I18NUtil.getMessages().ricevutePostaInUscitaWindow_emptyForDest_message(), "", MessageType.INFO));							
						} else if(data.getLength() == 1) {		                	
		                	RicevutePostaInUscitaWindow ricevutePostaInUscitaWindow = new RicevutePostaInUscitaWindow(data.get(0));						    						    						    					                			                
		                } else if(data.getLength() > 0) {		                	
		                	RicevutePostaInUscitaWindow ricevutePostaInUscitaWindow = new RicevutePostaInUscitaWindow(idEmailRif, idDestinatario);						                	
		                }
					}
				});	
			}
		});		
		statoConsolidamento.setShowIfCondition(new FormItemIfFunction() {
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				//Solo se in uscita
				return form.getValueAsString("statoConsolidamento")!=null && !"".equals(form.getValueAsString("statoConsolidamento"));
			}
		});
		statoConsolidamento.setItemHoverFormatter(new FormItemHoverFormatter() {			
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				
				String statoConsolidamento = (String) form.getValueAsString("statoConsolidamento");
				if(statoConsolidamento != null && !"".equals(statoConsolidamento)) {
					statoConsolidamento = statoConsolidamento.toString().replaceAll("_", " ");
					return statoConsolidamento;
				}
				return null;				
			}
		});
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[]{mDynamicForm};
	}

}
