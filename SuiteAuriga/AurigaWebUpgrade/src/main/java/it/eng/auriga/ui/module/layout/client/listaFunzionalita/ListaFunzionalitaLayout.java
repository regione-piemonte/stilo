/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public abstract class ListaFunzionalitaLayout extends CustomLayout{
	
	protected String idProfilo;
	protected String nomeProfilo;
	protected String flgTpDestAbil;
	private ToolStripButton addAbilFunzioni;
	protected MultiToolStripButton aggiungiAbilFunzioniMultiButton;
	protected MultiToolStripButton rimuoviAbilFunzioniMultiButton;
	
	
	public ListaFunzionalitaLayout() {
		this(null, false, false);
	}
	
	public ListaFunzionalitaLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}
	
	public ListaFunzionalitaLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("lista_funzionalita", 
				new GWTRestDataSource("AurigaListaFunzionalitaDataSource", "codice", FieldType.TEXT),  
				new ConfigurableFilter("lista_funzionalita"),
				new ListaFunzionalitaList("lista_funzionalita"), 
				new CustomDetail("lista_funzionalita"), 
				finalita, 
				flgSelezioneSingola, 
				showOnlyDetail);

		multiselectButton.hide();

		newButton.hide();
		
		setMultiselect(false);
	}
	
	public ListaFunzionalitaLayout(GWTRestDataSource pGWTRestDataSource, String idProfilo, String nomeProfilo){
		this(pGWTRestDataSource,idProfilo,nomeProfilo, "PR");
	}
	
	public ListaFunzionalitaLayout(GWTRestDataSource pGWTRestDataSource, String idProfilo, String nomeProfilo, String flgTpDestAbil){
			
		super("lista_funzionalita",
			 pGWTRestDataSource,
		     new ConfigurableFilter("lista_funzionalita"), 
		     new ListaFunzionalitaList("lista_funzionalita"),
		     new CustomDetail("lista_funzionalita"),
		     null, null, false);
			  
		this.idProfilo = idProfilo;
		this.nomeProfilo = nomeProfilo;
		this.flgTpDestAbil = flgTpDestAbil;
		
		multiselectButton.hide();		
		newButton.hide();	
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
	
	public abstract boolean getCanEditProfilo();
	
	@Override
	public boolean getDefaultMultiselect() 
	{		
		return getCanEditProfilo();
	}
		
	@Override
	protected ToolStripButton[] getCustomNewButtons() 
	{		
		if(isFunzioniAbilitate() && getCanEditProfilo()) 
		{	
			if(addAbilFunzioni == null) {
				addAbilFunzioni = new ToolStripButton();   
				addAbilFunzioni.setIcon("lookup/abilfunzionimulti.png");   
				addAbilFunzioni.setIconSize(16); 
				addAbilFunzioni.setPrompt("Aggiungi abilitazioni a funzioni");
				addAbilFunzioni.addClickHandler(new ClickHandler() {	
					@Override
					public void onClick(ClickEvent event) 
					{
						ListaFunzionalitaWindow listaFunzionalita = new ListaFunzionalitaWindow("Funzioni non ancora abilitate per il profilo", idProfilo, nomeProfilo, false, true, flgTpDestAbil) 
						{
							@Override
							public void manageOnCloseClick() 
							{
								super.manageOnCloseClick();
								doSearch();
							}
						};	
						listaFunzionalita.show();						
					}   
				}); 
			}
			return new ToolStripButton[]{
					addAbilFunzioni
			};
		
		} else return new ToolStripButton[]{};
	}
	
	@Override
	protected MultiToolStripButton[] getMultiselectButtons() 
	{		
		if(isFunzioniAbilitate() && getCanEditProfilo()) 
		{
			if(rimuoviAbilFunzioniMultiButton == null) {
				rimuoviAbilFunzioniMultiButton = new MultiToolStripButton("buttons/revoke.png", this, "Rimuovi dal profilo", false) {
					@Override
					public boolean toShow() {					
						return getCanEditProfilo();					
					}
					@Override
					public void doSomething() {
						final RecordList listaFunzioni = new RecordList();
						
						for(int i = 0; i < list.getSelectedRecords().length; i++) {
							listaFunzioni.add(list.getSelectedRecords()[i]);
						}
						
						Record lRecord = new Record();
						lRecord.setAttribute("idProfilo", idProfilo);
						lRecord.setAttribute("listaFunzioni", listaFunzioni);		
						lRecord.setAttribute("flgTpObjPrivTo", flgTpDestAbil);
						
						list.getDataSource().performCustomOperation("rimuoviAbilFunzioniProfilo", lRecord, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								
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
					rimuoviAbilFunzioniMultiButton				
			};	
		} else {
			if(aggiungiAbilFunzioniMultiButton == null) {			
				aggiungiAbilFunzioniMultiButton = new MultiToolStripButton("buttons/seleziona.png", this, "Aggiungi al profilo", false) 
				{								
					@Override
					public void doSomething() 
					{
						final RecordList listaFunzioni = new RecordList();
						
						for(int i = 0; i < list.getSelectedRecords().length; i++) {
							listaFunzioni.add(list.getSelectedRecords()[i]);
						}
						
						Record lRecord = new Record();
						lRecord.setAttribute("idProfilo", idProfilo);
						lRecord.setAttribute("listaFunzioni", listaFunzioni);	
						lRecord.setAttribute("flgTpObjPrivTo", flgTpDestAbil);
						
						list.getDataSource().performCustomOperation("aggiungiAbilFunzioniProfilo", lRecord, new DSCallback() {
							@Override
							public void execute(DSResponse response,
									Object rawData, DSRequest request) {
								
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
					aggiungiAbilFunzioniMultiButton
			};
		}
	}
	
	@Override
	public Boolean getDetailAuto() {
		return false;
	}

	public String getIdProfilo() {
		return idProfilo;
	}

	public void setIdProfilo(String idProfilo) {
		this.idProfilo = idProfilo;
	}
	
	public abstract boolean isFunzioniAbilitate();

	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();		
		newRecord.setAttribute("id", record.getAttributeAsString("codice"));
		newRecord.setAttribute("nome", record.getAttributeAsString("descrizione"));
		newRecord.setAttribute("icona", "lookup/abilfunzioni.png");
		return newRecord;
	}
	
	public int getMultiLookupGridSize(){
		int size = multiLookupGrid.getRecordList().getLength();
		return size;
	}
}