/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.FilteredSelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;


public class ChiusuraMassivaEmailForm extends DynamicForm{

	private ValuesManager lValuesManager;
	
	private NumericItem nroGiorniAperturaItem;
	private NumericItem nroGiorniSenzaOperazioniItem;
	private DateItem dataInvioDaItem;
	private DateItem dataInvioAItem;	
	private FilteredSelectItemWithDisplay uoAssegnazioneItem;
	private SelectItem caselleItem;
	
	
	public ChiusuraMassivaEmailForm(String nomeEntita){
		
		setWrapItemTitles(false);
		setNumCols(5);
		setColWidths("200", "10", "10", "10", "*");

		
		// *********************************************
		// CASELLE di ricezione o invio delle mail
		// *********************************************		
		caselleItem = new SelectItem("caselle", "Caselle ricezione/invio");
		caselleItem.setDisplayField("value");
		caselleItem.setValueField("key");
		GWTRestDataSource accounts = new GWTRestDataSource("AccountInvioEmailDatasource");
		caselleItem.setOptionDataSource(accounts);
		caselleItem.setMultiple(true);
		caselleItem.setStartRow(true);	
		caselleItem.setWidth(400);
		caselleItem.setColSpan(4);
		
		// *********************************************
		// UO Assegnazione
		// *********************************************
		uoAssegnazioneItem = createFilteredSelectItemWithDisplay("uoAssegnazione", 
				                                                  "Assegnate a", 
                                                                  "LoadComboUoCollegateUtenteAdminDatasource", 
                                                                  new String[]{"codice", "descrizione"}, 
                                                                  new String[]{"idUo"}, 
                                                                  new String[]{I18NUtil.getMessages().organigramma_list_codUoField_title(),  I18NUtil.getMessages().organigramma_list_descrizioneField_title() },               
                                                                  new Object[]{100, 230}, 
                                                                  340, 
                                                                  "idUo", 
                                                                  "descrizione");                 

		// Colonna ICONA TIPO UO
		List<ListGridField> organigrammaPickListFields = new ArrayList<ListGridField>();
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_VIS_ICONA_TIPO_UO")) {
			ListGridField typeNodoField = new ListGridField("iconaTypeNodo", "Tipo");
			typeNodoField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			typeNodoField.setAlign(Alignment.CENTER);
			typeNodoField.setWidth(30);
			typeNodoField.setShowHover(false);
			typeNodoField.setCanFilter(false);
			typeNodoField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("iconaTypeNodo") != null && !"".equals(record.getAttributeAsString("iconaTypeNodo"))) {
						return "<div align=\"center\"><img src=\"images/organigramma/tipo/" + record.getAttribute("iconaTypeNodo")
								+ ".png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			organigrammaPickListFields.add(typeNodoField);
		}
		
		// Colonna CODICE
		ListGridField codiceField = new ListGridField("codice", I18NUtil.getMessages().organigramma_list_codUoField_title());
		codiceField.setWidth(80);
		codiceField.setShowHover(true);
		codiceField.setHoverCustomizer(new HoverCustomizer() {
			
			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				return record.getAttribute("descrizioneEstesa");
			}
		});
		codiceField.setCanFilter(false);
		organigrammaPickListFields.add(codiceField);
		
		// Colonna DESCRIZIONE
		ListGridField descrizioneField = new ListGridField("descrizione", I18NUtil.getMessages().organigramma_list_descrizioneField_title());
		descrizioneField.setWidth("*");
		descrizioneField.setCanFilter(false);
		organigrammaPickListFields.add(descrizioneField);
		
		if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
			ListGridField flgPuntoProtocolloField = new ListGridField("flgPuntoProtocollo", "Punto di Protocollo");
			flgPuntoProtocolloField.setHeaderBaseStyle(it.eng.utility.Styles.hiddenHeaderButton);
			flgPuntoProtocolloField.setAlign(Alignment.CENTER);
			flgPuntoProtocolloField.setWidth(30);
			flgPuntoProtocolloField.setShowHover(true);
			flgPuntoProtocolloField.setCanFilter(false);
			flgPuntoProtocolloField.setCellFormatter(new CellFormatter() {

				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "<div align=\"center\"><img src=\"images/organigramma/puntoProtocollo.png\" height=\"16\" width=\"16\" alt=\"\" /></div>";
					}
					return null;
				}
			});
			flgPuntoProtocolloField.setHoverCustomizer(new HoverCustomizer() {

				@Override
				public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (record != null && record.getAttribute("flgPuntoProtocollo") != null && record.getAttributeAsBoolean("flgPuntoProtocollo")) {
						return "Punto di Protocollo";
					}
					return null;
				}
			});
			organigrammaPickListFields.add(flgPuntoProtocolloField);
		}
		uoAssegnazioneItem.setPickListFields(organigrammaPickListFields.toArray(new ListGridField[organigrammaPickListFields.size()]));
		uoAssegnazioneItem.setFilterLocally(false);		
		uoAssegnazioneItem.setAutoFetchData(false);
		uoAssegnazioneItem.setAlwaysFetchMissingValues(true);
		uoAssegnazioneItem.setFetchMissingValues(true);
		uoAssegnazioneItem.setValueField("id");
		uoAssegnazioneItem.setWidth(400);
		uoAssegnazioneItem.setClearable(true);
		uoAssegnazioneItem.setShowIcons(true);
		uoAssegnazioneItem.setColSpan(4);
		uoAssegnazioneItem.setMultiple(true);
		uoAssegnazioneItem.setStartRow(true);	
		uoAssegnazioneItem.setItemHoverFormatter(new FormItemHoverFormatter() {
			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return item.getSelectedRecord() != null ? item.getSelectedRecord().getAttributeAsString("descrizioneEstesa") : null;
			}
		});
		
		
		// *********************************************
		// Range Data di invio
		// *********************************************
		dataInvioDaItem = new DateItem("dataInvioDa", "Data di invio dal");
		dataInvioDaItem.setStartRow(true);		
		dataInvioAItem = new DateItem("dataInvioA", "al");
		dataInvioAItem.setStartRow(false);
		
		// *********************************************
		// Numero di giorni apertura
		// *********************************************
		nroGiorniAperturaItem = new NumericItem("nroGiorniApertura", "Aperte da oltre GG");
		nroGiorniAperturaItem.setStartRow(true);
		nroGiorniAperturaItem.setLength(4);
		nroGiorniAperturaItem.setWidth(100);
				
		// *********************************************
		// Numero di giorni senza operazioni
		// *********************************************
		nroGiorniSenzaOperazioniItem = new NumericItem("nroGiorniSenzaOperazioni", "Senza operazioni da oltre GG");
		nroGiorniSenzaOperazioniItem.setStartRow(true);
		nroGiorniSenzaOperazioniItem.setLength(4);
		nroGiorniSenzaOperazioniItem.setWidth(100);
				
		lValuesManager = new ValuesManager();
		
		setValuesManager(lValuesManager);

		setFields( caselleItem,
				   uoAssegnazioneItem,
				   dataInvioDaItem,
				   dataInvioAItem,
				   nroGiorniAperturaItem,
				   nroGiorniSenzaOperazioniItem
		);		
		
		markForRedraw();
	}
	
	public Record getRecord(){
		if (lValuesManager.validate()){
			Record lRecord = new Record(lValuesManager.rememberValues());
			return lRecord;
		} else return null;
	}	
	
	@Override
	protected void onDestroy() {
		if (lValuesManager != null) {
			try {
				lValuesManager.destroy();
			} catch (Exception e) {
			}
		}
		super.onDestroy();
	}
	
	
	private FilteredSelectItemWithDisplay createFilteredSelectItemWithDisplay(String name,
                                                                              String title, 
                                                                              String datasourceName, 
                                                                              String[] campiVisibili,
                                                                              String[] campiHidden, 
                                                                              String[] descrizioni, 
                                                                              Object[] width,
                                                                              int widthTotale, 
                                                                              String idField, 
                                                                              String displayField) 
	{
        SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource(datasourceName, idField, FieldType.TEXT, campiVisibili, true);
        FilteredSelectItemWithDisplay lFilteredSelectItemWithDisplay = new FilteredSelectItemWithDisplay(name, title, lGwtRestDataSource) {

            @Override
            public void onOptionClick(Record record) {
                super.onOptionClick(record);
                manageOnOptionClick(getName(), record);
            }

            @Override
            protected void clearSelect() {
                super.clearSelect();
                manageClearSelect(getName());
            }

       };

       int i = 0;
       List<ListGridField> lList = new ArrayList<ListGridField>();
       for (String lString : campiVisibili) {
           ListGridField field = new ListGridField(lString, descrizioni[i]);
           if (width[i] instanceof String) {
               field.setWidth((String) width[i]);
           } 
           else{
               field.setWidth((Integer) width[i]);
               }
           i++;
           lList.add(field);
       }

       for (String lString : campiHidden) {
           ListGridField field = new ListGridField(lString, lString);
           field.setHidden(true);
           lList.add(field);
       }

       lFilteredSelectItemWithDisplay.setPickListFields(lList.toArray(new ListGridField[] {}));
       lFilteredSelectItemWithDisplay.setFilterLocally(true);
       lFilteredSelectItemWithDisplay.setValueField(idField);
       lFilteredSelectItemWithDisplay.setOptionDataSource(lGwtRestDataSource);
       lFilteredSelectItemWithDisplay.setWidth(widthTotale);
       lFilteredSelectItemWithDisplay.setRequired(false);
       lFilteredSelectItemWithDisplay.setClearable(true);
       lFilteredSelectItemWithDisplay.setShowIcons(true);
       lFilteredSelectItemWithDisplay.setDisplayField(displayField);
       return lFilteredSelectItemWithDisplay;
    }
	
	private void manageOnOptionClick(String name, Record record){
	}
	
	private void manageClearSelect(String name){		
		lValuesManager.setValue("uoAssegnazione", "");
		lValuesManager.clearErrors(true);		
	}
}