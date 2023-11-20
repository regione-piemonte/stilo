/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FetchMode;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ControlListGridField;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class ClassificheList extends CustomList {
	
	private ListGridField icona; 
    private ListGridField idClassificazione;
	private ListGridField tipo;
	private ListGridField descrizione;
	private ListGridField descrizioneEstesa;
	private ListGridField paroleChiave;
	private ListGridField indice;
	private ListGridField indiceXOrd;
	private ListGridField tsValidaDal;
	private ListGridField tsValidaFinoAl;
	private ListGridField flgSelXFinalita;	
	private ListGridField idClassificaSup;
	private ListGridField desClassificaSup;
	private ListGridField score;	
	private boolean classificheAbilitate;
	private ListGridField flgAbilATutti;
	
	public ClassificheList(final String nomeEntita, String classificheAbilitate){

		super(nomeEntita, false);		
		
		this.classificheAbilitate = classificheAbilitate != null && "1".equals(classificheAbilitate) ? true : false;
		
		idClassificazione = new ListGridField("idClassificazione");
		idClassificazione.setHidden(true);
		idClassificazione.setCanHide(false);
		
		icona = new ControlListGridField("icona");   	
		icona.setAttribute("custom", true);	
		icona.setShowHover(true);	
		icona.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				return buildIconHtml("titolario/tipo/" + record.getAttributeAsString("nroLivello") + ".png");											
			}
		});
		icona.setHoverCustomizer(new HoverCustomizer() {				
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum,
					int colNum) {		
				return null;
			}
		});
		
		tipo = new ListGridField("tipo");
		tipo.setHidden(true);
		tipo.setCanHide(false);		
		
		descrizione = new ListGridField("descrizione", I18NUtil.getMessages().titolario_list_descrizioneField_title());
		
		descrizioneEstesa = new ListGridField("descrizioneEstesa", I18NUtil.getMessages().titolario_list_descrizioneEstesaField_title());
		
		paroleChiave = new ListGridField("paroleChiave", I18NUtil.getMessages().titolario_list_paroleChiaveField_title());
		
		indice = new ListGridField("indice", I18NUtil.getMessages().titolario_list_indiceField_title());
		indice.setHidden(true);
		indice.setCanHide(false);		
		
		indiceXOrd = new ListGridField("indiceXOrd", I18NUtil.getMessages().titolario_list_indiceField_title());
		indiceXOrd.setDisplayField("indice");
		indiceXOrd.setSortByDisplayField(false);	
		
		tsValidaDal  = new ListGridField("tsValidaDal", I18NUtil.getMessages().titolario_list_tsValidaDalField_title());
		tsValidaDal.setType(ListGridFieldType.DATE);
		tsValidaDal.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		tsValidaDal.setWrap(false);
		
		tsValidaFinoAl  = new ListGridField("tsValidaFinoAl", I18NUtil.getMessages().titolario_list_tsValidaFinoAlField_title());
		tsValidaFinoAl.setType(ListGridFieldType.DATE);
		tsValidaFinoAl.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);		
		tsValidaFinoAl.setWrap(false);
		
		idClassificaSup = new ListGridField("idClassificaSup");
		idClassificaSup.setHidden(true);
		idClassificaSup.setCanHide(false);
		
		desClassificaSup = new ListGridField("desClassificaSup");		
		desClassificaSup.setHidden(true);
		desClassificaSup.setCanHide(false);
		
		flgSelXFinalita = new ListGridField("flgSelXFinalita");
		flgSelXFinalita.setHidden(true);
		flgSelXFinalita.setCanHide(false);
		
		score = new ListGridField("score", I18NUtil.getMessages().titolario_list_scoreField_title());
		score.setType(ListGridFieldType.INTEGER);
		score.setSortByDisplayField(false);
		score.setCellFormatter(new CellFormatter() {			
			@Override
			public String format(Object value, ListGridRecord record, int rowNum,
					int colNum) {
				Integer score = value != null && !"".equals(String.valueOf(value)) ? new Integer(String.valueOf(value)) : null;
				if(score != null) {
					String res = "";
					for(int i = 0; i < score; i++) {
						res += "<img src=\"images/score.png\" size=\"10\"/>";
					}
					return res;
				}
				return null;
			}
		});
		
		
		flgAbilATutti = new ListGridField("flgAbilATutti", I18NUtil.getMessages().titolario_list_flgAbilATuttiField_title());
		flgAbilATutti.setType(ListGridFieldType.ICON);
		flgAbilATutti.setWidth(30);
		flgAbilATutti.setIconWidth(16);
		flgAbilATutti.setIconHeight(16);
		Map<String, String> flgAbilATuttiIcons = new HashMap<String, String>();
		flgAbilATuttiIcons.put("1", "buttons/grant.png");
		flgAbilATutti.setValueIcons(flgAbilATuttiIcons);
		flgAbilATutti.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				if ("1".equals(record.getAttribute("flgAbilATutti"))) {
					return "Pubblica";
				}
				return null;
			}
		});
		
		setFields(
			icona,
			idClassificazione, 
			tipo,						
			descrizione,
			descrizioneEstesa,
			paroleChiave,
			indice,
			indiceXOrd,
			tsValidaDal,
			tsValidaFinoAl,
			idClassificaSup,
			desClassificaSup,
			flgSelXFinalita,			
			score,
			flgAbilATutti
		);
				
		setAutoFetchData(false);
		setDataFetchMode(FetchMode.BASIC);	
	}
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 50;
	}
	
	@Override  
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TitolarioDatasource", "idClassificazione", FieldType.TEXT);		  
		lGWTRestDataSource.performCustomOperation("get", record, new DSCallback() {							
			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if(response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);	
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				} 				
			}
		}, new DSRequest());	
	}
	
	@Override
	public void reloadFieldsFromCriteria(AdvancedCriteria criteria) {
		boolean isFulltextSearch = false;
		if(criteria != null && criteria.getCriteria() != null) {
			for(Criterion crit : criteria.getCriteria()) {
				if("searchFulltext".equals(crit.getFieldName())) {
					Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");					
					String parole = (String) value.get("parole");
					if(parole != null && !"".equals(parole)) {
						isFulltextSearch = true;
					}
				}
			}
		}
		if(isFulltextSearch) {
			score.setHidden(false);
		} else {
			score.setHidden(true);
		}			
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {		
				try {
					refreshFields();
				} catch(Exception e) {}
				markForRedraw();
			}
		});	
	}
	
	@Override  
	public void manageContextClick(Record record) {
		
	}
	
	@Override  
    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if(layout.isLookup() && record != null) {			
			if(record.getAttributeAsBoolean("flgSelXFinalita")) {
				return "font-weight:bold; color:#1D66B2";
			} else {
				return "font-weight:normal; color:gray";
			}			        
		} 
		return super.getCellCSSText(record, rowNum, colNum);
    } 
	
	@Override
	protected boolean isRecordSelezionabileForLookup(ListGridRecord record) {
		return record.getAttributeAsBoolean("flgSelXFinalita");
	}
	
	public boolean isListaClassificheAbilitate() 
	{
		return classificheAbilitate;
	};

	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() 
	{
		return true;
	};		
    /********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/
}
