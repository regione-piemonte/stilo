/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomList;

import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.SortSpecifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.SortNormalizer;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class CaselleEmailList extends CustomList {
	
	private ListGridField idCasella;
	private ListGridField indirizzoEmail;
	private ListGridField idSpAoo;
	private ListGridField denominazione;
	private ListGridField tipoAccount;
	private ListGridField username;
	private ListGridField password;
	private ListGridField flgRicezioneAttiva;
	private ListGridField flgInvioAttivo;
	private ListGridField denominazioniUoAssociate;
	private ListGridField tsUltimoAggPassword;
	private ListGridField uteUltimoAggPassword;
	private ListGridField nroGiorniPasswordAttuale;
	private ListGridField idNodoScarico;
	private ListGridField intervalloScarico;
	private ListGridField nroMaxEmailScaricate;
	private ListGridField nroMaxTentativiScarico;
	private ListGridField nroMaxDestinatari;
	private ListGridField flgCancCopiaDiScaricoAttiva;
	

	public CaselleEmailList(String nomeEntita) {
		
		super(nomeEntita);
	
		idCasella = new ListGridField("idCasella");
		idCasella.setHidden(true);						
		idCasella.setCanHide(false);
		
		indirizzoEmail = new ListGridField("indirizzoEmail", I18NUtil.getMessages().caselleEmail_list_indirizzoEmailField_title());
		
		idSpAoo = new ListGridField("idSpAoo", I18NUtil.getMessages().caselleEmail_list_idSpAooField_title());
		
		denominazione = new ListGridField("denominazione", I18NUtil.getMessages().caselleEmail_list_denominazioneField_title());
		
		tipoAccount = new ListGridField("iconaTipoAccount", I18NUtil.getMessages().caselleEmail_list_tipoAccountField_title());  
		tipoAccount.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
		tipoAccount.setAlign(Alignment.CENTER);
		tipoAccount.setWrap(false);		
		tipoAccount.setWidth(30);
		tipoAccount.setAttribute("custom", true);	
		tipoAccount.setShowHover(true);
		tipoAccount.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {										
				String tipoAccount = (String) record.getAttribute("tipoAccount");
				if(tipoAccount != null && !"".equals(tipoAccount)) {
					return buildIconHtml("caselleEmail/tipoAccount/" + tipoAccount + ".png");												
				}
				return null;					
			}
		});
		tipoAccount.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String tipoAccount = record.getAttribute("tipoAccount");
				if(tipoAccount != null) {		
					if("C".equals(tipoAccount)) {
						return I18NUtil.getMessages().caselleEmail_tipoAccount_C_value();
					} else if("O".equals(tipoAccount)) {
						return I18NUtil.getMessages().caselleEmail_tipoAccount_O_value();
					} else if("N".equals(tipoAccount)) {
						return I18NUtil.getMessages().caselleEmail_tipoAccount_N_value();
					}
				}
				return null;				
			}
		});								
		
		username = new ListGridField("username", I18NUtil.getMessages().caselleEmail_list_usernameField_title());
		
		password = new ListGridField("password");
		password.setHidden(true);						
		password.setCanHide(false);
		
		flgRicezioneAttiva = new ListGridField("flgRicezioneAttiva", I18NUtil.getMessages().caselleEmail_list_flgRicezioneAttivaField_title());		
		flgRicezioneAttiva.setType(ListGridFieldType.INTEGER);
		flgRicezioneAttiva.setAlign(Alignment.CENTER);
		flgRicezioneAttiva.setWrap(false);		
		flgRicezioneAttiva.setIconWidth(16);
		flgRicezioneAttiva.setIconHeight(16);
		flgRicezioneAttiva.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgRicezioneAttiva = record.getAttribute("flgRicezioneAttiva");
				if(flgRicezioneAttiva != null && "1".equals(flgRicezioneAttiva)) {
					return buildIconHtml("ok.png");
				}
				return null;
			}
		});	 
		flgRicezioneAttiva.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String flgRicezioneAttiva = record.getAttribute("flgRicezioneAttiva");
				if(flgRicezioneAttiva != null && "1".equals(flgRicezioneAttiva)) {
					return I18NUtil.getMessages().caselleEmail_list_flgRicezioneAttivaField_title();
				}			
				return null;
			}
		});		
		
		flgInvioAttivo = new ListGridField("flgInvioAttivo", I18NUtil.getMessages().caselleEmail_list_flgInvioAttivoField_title());		
		flgInvioAttivo.setType(ListGridFieldType.INTEGER);
		flgInvioAttivo.setAlign(Alignment.CENTER);
		flgInvioAttivo.setWrap(false);		
		flgInvioAttivo.setIconWidth(16);
		flgInvioAttivo.setIconHeight(16);
		flgInvioAttivo.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgInvioAttivo = record.getAttribute("flgInvioAttivo");
				if(flgInvioAttivo != null && "1".equals(flgInvioAttivo)) {
					return buildIconHtml("ok.png");
				}
				return null;
			}
		});	 
		flgInvioAttivo.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String flgInvioAttivo = record.getAttribute("flgInvioAttivo");
				if(flgInvioAttivo != null && "1".equals(flgInvioAttivo)) {
					return I18NUtil.getMessages().caselleEmail_list_flgInvioAttivoField_title();
				}			
				return null;
			}
		});	
		
		denominazioniUoAssociate = new ListGridField("denominazioniUoAssociate", I18NUtil.getMessages().caselleEmail_list_denominazioniUoAssociateField_title());
		
		tsUltimoAggPassword = new ListGridField("tsUltimoAggPassword", I18NUtil.getMessages().caselleEmail_list_tsUltimoAggPasswordField_title());
		tsUltimoAggPassword.setType(ListGridFieldType.DATE);
		tsUltimoAggPassword.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		
		uteUltimoAggPassword = new ListGridField("uteUltimoAggPassword", I18NUtil.getMessages().caselleEmail_list_uteUltimoAggPasswordField_title());
		
		nroGiorniPasswordAttuale = new ListGridField("nroGiorniPasswordAttuale", I18NUtil.getMessages().caselleEmail_list_nroGiorniPasswordAttualeField_title());
		nroGiorniPasswordAttuale.setType(ListGridFieldType.INTEGER);
		
		idNodoScarico = new ListGridField("idNodoScarico", I18NUtil.getMessages().caselleEmail_list_idNodoScaricoField_title());
		
		intervalloScarico = new ListGridField("intervalloScarico", I18NUtil.getMessages().caselleEmail_list_intervalloScaricoField_title());
		intervalloScarico.setType(ListGridFieldType.INTEGER);
		
		nroMaxEmailScaricate = new ListGridField("nroMaxEmailScaricate", I18NUtil.getMessages().caselleEmail_list_nroMaxEmailScaricateField_title());
		nroMaxEmailScaricate.setType(ListGridFieldType.INTEGER);
		
		nroMaxTentativiScarico = new ListGridField("nroMaxTentativiScarico", I18NUtil.getMessages().caselleEmail_list_nroMaxTentativiScaricoField_title());
		nroMaxTentativiScarico.setType(ListGridFieldType.INTEGER);
		
		nroMaxDestinatari = new ListGridField("nroMaxDestinatari", I18NUtil.getMessages().caselleEmail_list_nroMaxDestinatariField_title());
		nroMaxDestinatari.setType(ListGridFieldType.INTEGER);
		
		flgCancCopiaDiScaricoAttiva = new ListGridField("flgCancCopiaDiScaricoAttiva", I18NUtil.getMessages().caselleEmail_list_flgCancCopiaDiScaricoAttivaField_title());		
		flgCancCopiaDiScaricoAttiva.setType(ListGridFieldType.INTEGER);
		flgCancCopiaDiScaricoAttiva.setAlign(Alignment.CENTER);
		flgCancCopiaDiScaricoAttiva.setWrap(false);		
		flgCancCopiaDiScaricoAttiva.setIconWidth(16);
		flgCancCopiaDiScaricoAttiva.setIconHeight(16);
		flgCancCopiaDiScaricoAttiva.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String flgCancCopiaDiScaricoAttiva = record.getAttribute("flgCancCopiaDiScaricoAttiva");
				if(flgCancCopiaDiScaricoAttiva != null && "1".equals(flgCancCopiaDiScaricoAttiva)) {
					return buildIconHtml("ok.png");
				}
				return null;
			}
		});	 
		flgCancCopiaDiScaricoAttiva.setHoverCustomizer(new HoverCustomizer() {			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String flgCancCopiaDiScaricoAttiva = record.getAttribute("flgCancCopiaDiScaricoAttiva");
				if(flgCancCopiaDiScaricoAttiva != null && "1".equals(flgCancCopiaDiScaricoAttiva)) {
					return I18NUtil.getMessages().caselleEmail_list_flgCancCopiaDiScaricoAttivaField_title();
				}			
				return null;
			}
		});	
		
		setFields(new ListGridField[] {
				idCasella,
				indirizzoEmail,
				idSpAoo,
				denominazione,
				tipoAccount,
				username,
				password,
				flgRicezioneAttiva,
				flgInvioAttivo,
				denominazioniUoAssociate,
				tsUltimoAggPassword,
				uteUltimoAggPassword,
				nroGiorniPasswordAttuale,
				idNodoScarico,
				intervalloScarico,
				nroMaxEmailScaricate,
				nroMaxTentativiScarico,
				nroMaxDestinatari,
				flgCancCopiaDiScaricoAttiva
		});  
		
	}
	
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		final GWTRestDataSource lCaselleEmailDatasource = ((GWTRestDataSource)getDataSource());
		lCaselleEmailDatasource.getData(record, new DSCallback() {
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
					Record detailRecord = response.getData()[0];			
					CaselleEmailDetail caselleEmailDetail = new CaselleEmailDetail("caselle_email", detailRecord);
					caselleEmailDetail.editRecord(detailRecord);	
					layout.changeDetail(lCaselleEmailDatasource, caselleEmailDetail);
					callback.execute(response, null, new DSRequest());								
				}
			}
		});
	}
	
	@Override  
	public void manageContextClick(final Record record) {	
		if(record != null) {
			showRowContextMenu(getRecord(getRecordIndex(record)));			
		}
	}
	
	public void showRowContextMenu(final ListGridRecord record) {
		
		Menu contextMenu = new Menu();	
		
		MenuItem visualizzaMenuItem = new MenuItem(I18NUtil.getMessages().detailButton_prompt(), "buttons/detail.png");   
		visualizzaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {
				manageDetailButtonClick(record);				
			}   
		});   			
		contextMenu.addItem(visualizzaMenuItem);
		
		if(CaselleEmailLayout.isAbilToMod()) {
			MenuItem modificaMenuItem = new MenuItem(I18NUtil.getMessages().modifyButton_prompt(), "buttons/modify.png");   
			modificaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
				@Override
				public void onClick(MenuItemClickEvent event) {
					manageModifyButtonClick(record);
				}   
			});   			
			contextMenu.addItem(modificaMenuItem);
		}
		
		if(CaselleEmailLayout.isAbilToModToCreaComeCopia()) {	
			MenuItem creaComeCopiaMenuItem = new MenuItem(I18NUtil.getMessages().caselleEmail_list_creaComeCopiaContextMenuItem_title(), "protocollazione/nuovaProtComeCopia.png");
			creaComeCopiaMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
				@Override
				public void onClick(MenuItemClickEvent event) {
					final GWTRestDataSource lCaselleEmailDatasource = ((GWTRestDataSource)getDataSource());
					lCaselleEmailDatasource.getData(record, new DSCallback() {
						@Override
						public void execute(DSResponse response, Object rawData, DSRequest request) {
							if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
								Record detailRecord = response.getData()[0];			
								CaselleEmailComeCopiaDetail caselleEmailComeCopiaDetail = new CaselleEmailComeCopiaDetail("caselle_email", detailRecord);
								caselleEmailComeCopiaDetail.editRecord(detailRecord);	
								layout.changeDetail(lCaselleEmailDatasource, caselleEmailComeCopiaDetail);	
								layout.newMode();
							}
						}
					});										
				}
			});
			contextMenu.addItem(creaComeCopiaMenuItem);
		}
				
		MenuItem soggettiAbilitatiMenuItem_title = new MenuItem(I18NUtil.getMessages().caselleEmail_list_soggettiAbilitatiContextMenuItem_title(), "buttons/grant.png");
		soggettiAbilitatiMenuItem_title.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {   	      
			@Override
			public void onClick(MenuItemClickEvent event) {				
				SoggettiAbilitatiCasellaWindow soggettiAbilitatiCasellaWindow = new SoggettiAbilitatiCasellaWindow(((CaselleEmailLayout)getLayout()), record);
				soggettiAbilitatiCasellaWindow.show();
			}
		});
		contextMenu.addItem(soggettiAbilitatiMenuItem_title);
//		
		contextMenu.addSort(new SortSpecifier("title", SortDirection.ASCENDING));			
		contextMenu.showContextMenu();
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};
	 
	@Override
	protected boolean showDetailButtonField() {
		return true;
	}
		
	@Override
	protected boolean showModifyButtonField() {
		return CaselleEmailLayout.isAbilToMod();
	}
	
	@Override
	protected boolean showDeleteButtonField() {
		return CaselleEmailLayout.isAbilToDel();
	}	
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
