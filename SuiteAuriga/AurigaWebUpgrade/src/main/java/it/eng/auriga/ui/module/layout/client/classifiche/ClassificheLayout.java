/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.titolario.TitolarioDetail;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

public class ClassificheLayout extends CustomLayout {
	protected String idPianoClassif;
	protected Boolean flgSoloAttive;
	protected String tsRif;
	protected Record record;
	
	private ToolStripButton addClassifiche;
	
	private MultiToolStripButton addClassificaAUO;
	private MultiToolStripButton removeClassificaDaUO;
	
	
	public ClassificheLayout() {
		this(null, null, false, null);
	}
	
	public ClassificheLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null, null);
	}
	
	public ClassificheLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		this(finalita, flgSelezioneSingola, showOnlyDetail, null);
	}
	
	public ClassificheLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail, Record record){
		
		super("titolario", 
			  getGWTRestDataSource(record),
			  new ConfigurableFilter("classifiche"), 
			  new ClassificheList("classifiche", record.getAttribute("flgClassificheAttiva")), 
			  new TitolarioDetail("classifiche"), 
			  finalita, 
			  flgSelezioneSingola, 
			  showOnlyDetail);
		
		this.record = record;
		
		multiselectButton.hide();		
		newButton.hide();					
	}	
	
	private static GWTRestDataSource getGWTRestDataSource(Record record) {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ClassificheDatasource", "idClassificazione", FieldType.TEXT);
		lGWTRestDataSource.addParam("classificheAbilitate", record.getAttribute("flgClassificheAttiva"));
		lGWTRestDataSource.addParam("idUo", record.getAttribute("idUo"));
		lGWTRestDataSource.addParam("flgTpDestAbil", record.getAttribute("flgTpDestAbil"));
		
		return lGWTRestDataSource;		
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ClassificheDatasource", "idClassificazione", FieldType.TEXT);
		lGWTRestDataSource.addParam("classificheAbilitate", record.getAttribute("flgClassificheAttiva"));
		lGWTRestDataSource.addParam("idUo", record.getAttribute("idUo"));
		lGWTRestDataSource.addParam("flgTpDestAbil", record.getAttribute("flgTpDestAbil"));
		lGWTRestDataSource.setForceToShowPrompt(false);

		return lGWTRestDataSource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		} else {
			return super.extractRecords(fields);
		}
	}
	
	public static boolean isAbilToIns() {
		return false;
	}
	
	public static boolean isAbilToMod() {
		return false;
	}
	
	public static boolean isAbilToDel() {
		return false;
	}
	
	@Override
	public void viewMode() {
		super.viewMode();
		
		if(!isAbilToMod()) 
			editButton.hide();
		
		if(!isAbilToDel()) 
			deleteButton.hide();
		
		altreOpButton.hide();	
		
		Record record = new Record(detail.getValuesManager().getValues());
		if(isLookup() && record.getAttributeAsBoolean("flgSelXFinalita")) {
			lookupButton.show();
		} else {
			lookupButton.hide();
		}
	}
	
	public void setMultiselect(Boolean multiselect) {
		super.setMultiselect(multiselect);		
		if  ( (getFinalita() != null && !"".equals(getFinalita()))  || !isAbilToIns() )   {
			this.newButton.hide();			
		}					
	}
	
	@Override
	public boolean getDefaultMultiselect() {		
		return true;
	}
	
	@Override
	protected ToolStripButton[] getCustomNewButtons() {		
		if(((ClassificheList)getList()).isListaClassificheAbilitate()) {	
			if(addClassifiche == null) {
				addClassifiche = new ToolStripButton();   
				addClassifiche.setIcon("lookup/titolariomulti.png");   
				addClassifiche.setIconSize(16); 
				addClassifiche.setPrompt("Aggiungi classifica");
				addClassifiche.addClickHandler(new ClickHandler() {	
					
					@Override
					public void onClick(ClickEvent event) {
						final Record record = getRecord();
						record.setAttribute("flgClassificheAttiva", "0");	
						record.setAttribute("flgTpDestAbil", "UO");
						ClassificheWindow classifiche = new ClassificheWindow("Classifiche non abilitate alla U.0,", record) {
							
							@Override
							public void manageOnCloseClick() {
								super.manageOnCloseClick();
								doSearch();
							}
						};	
						classifiche.show();						
					}   
				}); 
			}
			return new ToolStripButton[]{
					addClassifiche
			};
		
		} else return new ToolStripButton[]{};
	}
	
	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {		
		if(((ClassificheList)getList()).isListaClassificheAbilitate()) {
			if(removeClassificaDaUO == null) {			
				removeClassificaDaUO = new MultiToolStripButton("buttons/revoke.png", this, "Rimuovi dalla U.O.", false) {				
					@Override
					public void doSomething() {
						final RecordList listaClassificazioni = new RecordList();
						
						for(int i = 0; i < list.getSelectedRecords().length; i++) {
							listaClassificazioni.add(list.getSelectedRecords()[i]);
						}
						final Record lRecord = getRecord();
						lRecord.setAttribute("listaClassifiche", listaClassificazioni);
						
						final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ClassificheDatasource");
						lGWTRestDataSource.performCustomOperation("rimuoviClassificheDaUO", lRecord, new DSCallback() {							
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if(response.getStatus() == DSResponse.STATUS_SUCCESS) 
								{						
									doSearch();													
								} 				
							}
						}, new DSRequest());								
					}
				};
			}
			return new MultiToolStripButton[]{				
					removeClassificaDaUO
			};
		} else {
			if(addClassificaAUO == null) {			
				addClassificaAUO = new MultiToolStripButton("buttons/seleziona.png", this, "Aggiungi alla U.O.", false) {				
					@Override
					public void doSomething() {
						final RecordList listaClassificazioni = new RecordList();
						
						for(int i = 0; i < list.getSelectedRecords().length; i++) {
							listaClassificazioni.add(list.getSelectedRecords()[i]);
						}
						final Record lRecord = getRecord();
						lRecord.setAttribute("listaClassifiche", listaClassificazioni);
						
						final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("ClassificheDatasource");
						lGWTRestDataSource.performCustomOperation("aggiungiClassificheAUO", lRecord, new DSCallback() {							
							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request) {
								if(response.getStatus() == DSResponse.STATUS_SUCCESS) {						
									doSearch();												
								} 				
							}
						}, new DSRequest());								
					}
				};
			}
			return new MultiToolStripButton[]{				
					addClassificaAUO
			};
		}
		
	}
	
	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		if(criteria == null) {
			criteria = new AdvancedCriteria();
		} else {
			for(Criterion crit : criteria.getCriteria()) {
				criterionList.add(crit);
			}
		}									
		if(idPianoClassif != null && !"".equals(idPianoClassif)) {
			criterionList.add(new Criterion("idPianoClassif", OperatorId.EQUALS, idPianoClassif));
		}
		if(flgSoloAttive != null) {
			criterionList.add(new Criterion("flgSoloAttive", OperatorId.EQUALS, flgSoloAttive));
		}				
		if(tsRif != null && !"".equals(tsRif)) {
			criterionList.add(new Criterion("tsRif", OperatorId.EQUALS, tsRif));
		}			
		Criterion[] criterias = new Criterion[criterionList.size()];
		for(int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}		
	
	public String getIdPianoClassif() {
		return idPianoClassif;
	}
	
	public Boolean getFlgSoloAttive() {
		return flgSoloAttive;
	}
	
	public String getTsRif() {
		return tsRif;
	}
	
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
	
	
	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();		
		newRecord.setAttribute("id", record.getAttributeAsString("idClassificazione"));
		newRecord.setAttribute("nome", record.getAttributeAsString("descrizione"));
		newRecord.setAttribute("icona", "menu/titolario.png");
		return newRecord;
	}
	
	public int getMultiLookupGridSize(){
		int size = multiLookupGrid.getRecordList().getLength();
		return size;
	}
}

