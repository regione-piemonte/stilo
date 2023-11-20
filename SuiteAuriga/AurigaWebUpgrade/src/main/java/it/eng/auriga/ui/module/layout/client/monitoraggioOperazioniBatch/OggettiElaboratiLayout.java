/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class OggettiElaboratiLayout extends CustomLayout {	
	
	private String idRichiesta;

	public OggettiElaboratiLayout() {
		this("monitoraggio_operazioni_batch_lista_oggetti_elaborati", null);
	}
	
	public OggettiElaboratiLayout(String nomeEntita) {
		this(nomeEntita, null);
	}	
	
	public OggettiElaboratiLayout(String nomeEntita, String idRichiesta) {				
		super(nomeEntita, 
				new GWTRestDataSource("OggettiElaboratiDatasource", "idRichiesta", FieldType.TEXT),
				new ConfigurableFilter(nomeEntita), 
				new OggettiElaboratiList(nomeEntita) ,
				new CustomDetail(nomeEntita),
				null,
				true,
				false
		);
		multiselectButton.hide();	
		
		this.idRichiesta = idRichiesta;

		this.setLookup(false);
		
		if (!isAbilToIns()) {
			newButton.hide();
		}					
	}	
	
	@Override
	public boolean isForcedToAutoSearch() {

		if(idRichiesta != null)
			return true;
		else
			return super.isForcedToAutoSearch();
	}

	@Override
	public boolean isStoppableSearch() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean isAbilToViewDetail(){
		// TODO Auto-generated method stub
		return false;
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
	
	public static boolean isAbilToView() {
		return false;
	}	
	
	public static boolean isRecordAbilToView(boolean flgLocked) {
		// TODO Auto-generated method stub
		return !flgLocked && isAbilToView();
	}

	public static boolean isRecordAbilToMod(boolean flgLocked) {
		// TODO Auto-generated method stub
		return !flgLocked && isAbilToMod();
	}

	public static boolean isRecordAbilToDel(boolean flgAttivo, boolean flgLocked) {
		// TODO Auto-generated method stub
		return flgAttivo && !flgLocked && isAbilToDel();
	}	
		
	@Override
	public void doSimpleSearch(AdvancedCriteria criteria) {

		if (idRichiesta != null){
			criteria.addCriteria("idRichiesta", idRichiesta);	
		}
		
		super.doSimpleSearch(criteria);
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		// TODO Auto-generated method stub
		super.viewMode();		
		Record record = new Record(detail.getValuesManager().getValues());
		final boolean recProtetto  = record.getAttribute("recProtetto") != null && record.getAttributeAsString("recProtetto").equals("1");
		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");
		if(isRecordAbilToDel(flgValido, recProtetto)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isRecordAbilToMod(recProtetto)) {
			editButton.show();
		} else{
			editButton.hide();
		}		
		altreOpButton.hide();	
	}

	@Override
	public void editMode(boolean fromViewMode) {
		// TODO Auto-generated method stub
		super.editMode(fromViewMode);
		altreOpButton.hide();
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		// TODO Auto-generated method stub
		final Record newRecord = new Record();

		// TODO ......
		return newRecord;
	}	
	
	public void reloadList() {
		doSearch();
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		GWTRestDataSource datasource = new GWTRestDataSource("OggettiElaboratiDatasource", "idRichiesta", FieldType.TEXT);
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