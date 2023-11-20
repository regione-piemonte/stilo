/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.InizialiLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.MapCreator;

public class RubricaEmailLayout extends InizialiLayout {	

	protected ToolStripButton newContattoButton;
	protected ToolStripButton newGruppoButton;
	
	public RubricaEmailLayout() {
		this(null, null, null);
	}
	
	public RubricaEmailLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}
	
	public RubricaEmailLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("anagrafiche_rubricaemail", 
				new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idVoceRubrica", FieldType.TEXT), 
				new ConfigurableFilter("anagrafiche_rubricaemail"), 
				new RubricaEmailList("anagrafiche_rubricaemail") ,
				new RubricaEmailDetail("anagrafiche_rubricaemail"),
				finalita,
				flgSelezioneSingola,
				showOnlyDetail
		);  	
		
		multiselectButton.hide();	
		newButton.hide();
		
		if (!isAbilToIns()) {
			newContattoButton.hide();
			newGruppoButton.hide();			
		}

	}	

	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("EML/RUB;I");
	}
	
	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("EML/RUB;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("EML/RUB;FC");
	}	
	
	public static boolean isRecordAbilToMod(boolean idProvSoggRifValorizzato) {
		return !idProvSoggRifValorizzato && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgAttivo, boolean idProvSoggRifValorizzato) {
		return flgAttivo && !idProvSoggRifValorizzato && isAbilToDel();		
	}	
	
	@Override
	protected ToolStripButton[] getCustomNewButtons() {
		
		newContattoButton = new ToolStripButton();   
		newContattoButton.setIcon("anagrafiche/rubrica_email/newContatto.png");   
		newContattoButton.setIconSize(16); 
		newContattoButton.setPrompt("Nuovo contatto");
		newContattoButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				list.deselectAllRecords();
				changeDetail((GWTRestDataSource)list.getDataSource(), new RubricaEmailDetail("anagrafiche_rubricaemail"));
				detail.editNewRecord(new MapCreator("tipoIndirizzo","S","|*|").getProperties());					
				newMode();       	
			}   
		}); 
		
		newGruppoButton = new ToolStripButton();   
		newGruppoButton.setIcon("anagrafiche/rubrica_email/newGruppo.png");   
		newGruppoButton.setIconSize(16); 
		newGruppoButton.setPrompt("Nuova lista di distribuzione/gruppo");
		newGruppoButton.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				list.deselectAllRecords();
				changeDetail((GWTRestDataSource)list.getDataSource(), new RubricaGruppoEmailDetail("anagrafiche_rubricaemail"));
				detail.editNewRecord(new MapCreator("tipoIndirizzo","G","|*|").getProperties());					
				newMode();         	
			}   
		}); 
		
		ToolStripButton[] customNewButtons = {newContattoButton, newGruppoButton};
		return customNewButtons;
	}	

	@Override
	public void manageOnClickSearchButton() {
		super.manageOnClickSearchButton();
		setIniziale(null);
	}
	
	public String getBaseTitle() {
		return I18NUtil.getMessages().rubricaemail_lookupRubricaEmailPopup_title();		
	}

	@Override
	public String getNewDetailTitle() {
		if(detail instanceof RubricaEmailDetail) {
			return I18NUtil.getMessages().rubricaemail_detail_newContatto_title();
		} else if(detail instanceof RubricaGruppoEmailDetail) {
			return I18NUtil.getMessages().rubricaemail_detail_newGruppo_title();
		}
		return null;		
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().rubricaemail_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().rubricaemail_detail_view_title(getTipoEstremiRecord(record));		
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		Record record = new Record(detail.getValuesManager().getValues());
		
		boolean idProvSoggRifValorizzato = record.getAttribute("idProvSoggRif") != null && !"".equals(record.getAttribute("idProvSoggRif"));		
		boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("true");
		
		if(isRecordAbilToDel(flgValido, idProvSoggRifValorizzato)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		
		if(isRecordAbilToMod(idProvSoggRifValorizzato)) {
			editButton.show();
		} else{
			editButton.hide();
		}	
		
		altreOpButton.hide();
		
		boolean flgSelezionabile = finalita == null || !"SEL_SOLO_SINGOLI".equals(finalita) || !"G".equals(record.getAttributeAsString("tipoIndirizzo"));
		if(isLookup() && flgSelezionabile) {					
			lookupButton.show();
		} else {
			lookupButton.hide();
		}
	}

	@Override
	public void editMode(boolean fromViewMode) {
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();

		// Aggiungo nel tab dei record selezionati il soggetto selezionato
		newRecord.setAttribute("id", record.getAttributeAsString("idVoceRubrica"));			
		newRecord.setAttribute("indirizzoEmail", record.getAttributeAsString("indirizzoEmail"));	
		
		// Visualizzo l'icona	
		if("S".equals(record.getAttributeAsString("tipoIndirizzo"))) {
			newRecord.setAttribute("icona", "anagrafiche/rubrica_email/tipoIndirizzo/S.png");	
		} else if("G".equals(record.getAttributeAsString("tipoIndirizzo"))) {
			newRecord.setAttribute("icona", "anagrafiche/rubrica_email/tipoIndirizzo/G.png");	
		} else newRecord.setAttribute("icona", "blank.png");
		
		newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		
		return newRecord;
	}	
	
	@Override
	public void doLookup(Record record) {
		boolean flgSelezionabile = finalita == null || !"SEL_SOLO_SINGOLI".equals(finalita) || !"G".equals(record.getAttributeAsString("tipoIndirizzo"));
		if(flgSelezionabile) {
			super.doLookup(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().recordNonSelezionabileXFinalita_message(), "", MessageType.WARNING));
		}
	}

	@Override
	public void manageOnClickInizialeButton(ClickEvent event, String iniziale) {
		setIniziale(iniziale);		
		String nome = null;
		String parole = null;
		AdvancedCriteria criteria = getFilter().getCriteria();
		List<Criterion> criterionListWithoutNome = new ArrayList<Criterion>();
		if(criteria != null && criteria.getCriteria() != null) {
			for(Criterion crit : criteria.getCriteria()) {						
				if("nome".equals(crit.getFieldName())) {					
					nome = crit.getFieldName();
					Map value = JSOHelper.getAttributeAsMap(crit.getJsObj(), "value");
					parole = (String) value.get("parole");
//					Map mappa = (Map) crit.getValues(); 
//					parole = (String) ((Map) mappa.get("value")).get("parole");
					//parole = crit.get
				} else {
					criterionListWithoutNome.add(crit);
				}
			}
		}
		final Criterion[] criteriasWithoutNome = new Criterion[criterionListWithoutNome.size()];
		for(int i = 0; i < criterionListWithoutNome.size(); i++) {
			criteriasWithoutNome[i] = criterionListWithoutNome.get(i);
		}		
		
		if(parole != null && !"".equals(parole)) {
			SC.ask("Vuoi che sia ignorato il filtro per \"" + parole + "\"?", new BooleanCallback() {					
				@Override
				public void execute(Boolean value) {
					if(value) {
						doSearch(new AdvancedCriteria(OperatorId.AND, criteriasWithoutNome));	
					} else {
						doSearch();		
					}
				}
			});					
		} else {
			doSearch();
		}
		
		
//		if(nome != null && !"".equals(nome)) {
//			Layout.addMessage(new MessageBean("Il filtro per nome \"" + nome + "\" è stato ignorato", "", MessageType.WARNING));
//			doSearch(new AdvancedCriteria(OperatorId.AND, criteriasWithoutNome));											
//		} else {
//			doSearch();
//		}
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		
		GWTRestDataSource datasource = new GWTRestDataSource("AnagraficaRubricaEmailDataSource", "idEmail", FieldType.TEXT);
		
		return datasource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono,
		// altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}
}
