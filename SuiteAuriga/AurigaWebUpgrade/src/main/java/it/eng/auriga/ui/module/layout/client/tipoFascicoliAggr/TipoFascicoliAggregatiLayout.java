/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;

/**
 * 
 * @author cristiano
 *
 */

public class TipoFascicoliAggregatiLayout extends CustomLayout 
{
	private MultiToolStripButton addClassificaAUO;
	private MultiToolStripButton removeClassificaDaUO;
	private ToolStripButton addClassifiche;
	protected Record record;
	
	public TipoFascicoliAggregatiLayout() {
		this(null, null, null);
	}

	public TipoFascicoliAggregatiLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}

	public TipoFascicoliAggregatiLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("tipofascicoliaggr", 
			  new GWTRestDataSource("TipoFascicoliAggregatiDataSource", "idFolderType", FieldType.TEXT), 
			  new ConfigurableFilter("tipofascicoliaggr"), 
			  new TipoFascicoliAggregatiList("tipofascicoliaggr", null), 
			  new TipoFascicoliAggregatiDetail("tipofascicoliaggr"), 
			  finalita,
			  flgSelezioneSingola, 
			  showOnlyDetail);

		multiselectButton.hide();

		if (!isAbilToIns() && showNewButton()) {
			newButton.hide();
		}
	}
	
	public TipoFascicoliAggregatiLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail, Record record) {
		super("tipofascicoliaggr",  
			  getGWTRestDataSource(record), 
			  new ConfigurableFilter("tipofascicoliaggr"), 
			  new TipoFascicoliAggregatiList("tipofascicoliaggr", record.getAttribute("flgStatoAbilitazioneIn")), 
			  new TipoFascicoliAggregatiDetail("tipofascicoliaggr"), 
			  finalita,
			  flgSelezioneSingola, 
			  showOnlyDetail);

		
		this.record = record;
		
		multiselectButton.hide();
		newButton.hide();
		setMultiselect(true);
	}
	
	private static GWTRestDataSource getGWTRestDataSource(Record record)
	{
		GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TipoFascicoliAggregatiDataSource", "idFolderType", FieldType.TEXT);
		lGWTRestDataSource.addParam("viewTipologiaDaUO", record.getAttribute("viewTipologiaDaUO"));
		lGWTRestDataSource.addParam("flgStatoAbilitazioneIn", record.getAttribute("flgStatoAbilitazioneIn"));
		lGWTRestDataSource.addParam("flgTipologiaDestAbilIn",record.getAttribute("flgTipologiaDestAbilIn"));
		lGWTRestDataSource.addParam("idUo", record.getAttribute("idUo"));		
		return lGWTRestDataSource;		
	}

	public static boolean isAbilToIns() {
		
		return Layout.isPrivilegioAttivo("DC/TD;I");
	}

	public static boolean isAbilToMod() {
		
		return Layout.isPrivilegioAttivo("DC/ATT;M");
	}

	public static boolean isAbilToDel() {
		
		return Layout.isPrivilegioAttivo("DC/ATT;FC");
	}

	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		
		return !flgDiSistema && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgDiSistema) {
		
		return !flgDiSistema && isAbilToDel();
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().tipofascicoliaggr_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().tipofascicoliaggr_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().tipofascicoliaggr_view_title(getTipoEstremiRecord(record));
	}

	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("nome");
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		if (isAbilToDel()) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
		if (isAbilToMod()) {
			editButton.show();
		} else {
			editButton.hide();
		}
		altreOpButton.hide();
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}
	
	@Override
	public boolean getDefaultMultiselect() 
	{		
		return false;
	}
	
	@Override
	protected ToolStripButton[] getCustomNewButtons() 
	{		
		if(((TipoFascicoliAggregatiList)getList()).isListaTipoFascicoliAbilitate()) 
		{	
			if(addClassifiche == null) {
				addClassifiche = new ToolStripButton();   
				addClassifiche.setIcon("lookup/tipofascicolomulti.png");   
				addClassifiche.setIconSize(16); 
				addClassifiche.setPrompt("Aggiungi Tipo Fascicoli e UA");
				addClassifiche.addClickHandler(new ClickHandler() {	
					@Override
					public void onClick(ClickEvent event) 
					{
						final Record record = getRecord();
						record.setAttribute("flgStatoAbilitazioneIn", "0");			
						TipologieFascicoliWindows classifiche = new TipologieFascicoliWindows("Tipo Fascicoli e UA non abilitati alla U.0,", record) 
						{
							@Override
							public void manageOnCloseClick() 
							{
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
	protected MultiToolStripButton[] getMultiselectButtons() 
	{		
		if(((TipoFascicoliAggregatiList)getList()).isListaTipoFascicoliAbilitate()) 
		{
			if(removeClassificaDaUO == null) {			
				removeClassificaDaUO = new MultiToolStripButton("buttons/revoke.png", this, "Rimuovi dalla U.O.", false) 
				{				
					@Override
					public void doSomething() 
					{
						final RecordList listaClassificazioni = new RecordList();
						
						for(int i = 0; i < list.getSelectedRecords().length; i++) 
						{
							listaClassificazioni.add(list.getSelectedRecords()[i]);
						}
						final Record lRecord = getRecord();
						lRecord.setAttribute("listaTipoFascicoli", listaClassificazioni);
						
						final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TipoFascicoliAggregatiDataSource");
						lGWTRestDataSource.performCustomOperation("rimuoviTipoFscicoliDaUO", lRecord, new DSCallback() {							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request)
							{
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
				addClassificaAUO = new MultiToolStripButton("buttons/seleziona.png", this, "Aggiungi alla U.O.", false) 
				{				
					@Override
					public void doSomething() 
					{
						final RecordList listaClassificazioni = new RecordList();
						
						for(int i = 0; i < list.getSelectedRecords().length; i++) 
						{
							listaClassificazioni.add(list.getSelectedRecords()[i]);
						}
						final Record lRecord = getRecord();
						lRecord.setAttribute("listaTipoFascicoli", listaClassificazioni);
						
						final GWTRestDataSource lGWTRestDataSource = new GWTRestDataSource("TipoFascicoliAggregatiDataSource");
						lGWTRestDataSource.performCustomOperation("aggiungiTipoFscicoliAUO", lRecord, new DSCallback() {							
							@Override
							public void execute(DSResponse response, Object rawData, DSRequest request)
							{
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
					addClassificaAUO
			};
		}
		
	}
	
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}
	
	public boolean showNewButton(){
		return true;
	}
	
	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();		
		newRecord.setAttribute("id", record.getAttributeAsString("idFolderType"));
		newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		newRecord.setAttribute("icona", "menu/tipo_fascicoli_aggr.png");
		return newRecord;
	}
	
	public int getMultiLookupGridSize(){
		int size = multiLookupGrid.getRecordList().getLength();
		return size;
	}
}