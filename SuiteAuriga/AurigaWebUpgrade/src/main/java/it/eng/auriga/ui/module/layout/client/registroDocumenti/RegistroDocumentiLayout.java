/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

public class RegistroDocumentiLayout extends CustomLayout{

	
	public RegistroDocumentiLayout(String nomeEntita){
		
		super(nomeEntita,
			  new GWTRestDataSource("RegistroDocumentiDataSource", "idUd", FieldType.TEXT),
			  new ConfigurableFilter(nomeEntita), 
			  new RegistroDocumentiList(nomeEntita),
			  new CustomDetail(nomeEntita)
		    );
		
		multiselectButton.hide();	
		
		if (!isAbilToIns()) {
			newButton.hide();
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
	
	public static boolean isRecordAbilToMod(boolean flgDiSistema) {
		return !flgDiSistema && isAbilToMod();
	}
	
	public static boolean isRecordAbilToDel(boolean flgValido, boolean flgDiSistema) {
		return flgValido && !flgDiSistema && isAbilToDel();
	}
	
	@Override
	public String getTipoEstremiRecord(Record record) {
		return record.getAttribute("cognome") + " " + record.getAttribute("nome");	
	}
	
	@Override
	public String getNewDetailTitle() {
		return "Nuovo utente";
	}
	
	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Dettaglio utente " + getTipoEstremiRecord(record);
	}
	
	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Modifica utente " + getTipoEstremiRecord(record);
	}
	
//	@Override
//	public void viewMode() {
//		
//		super.viewMode();				
//		Record record = new Record(detail.getValuesManager().getValues());
//		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsString("flgDiSistema").equals("1");
//		final boolean flgValido  = record.getAttribute("flgValido") != null && record.getAttributeAsString("flgValido").equals("1");				
//		if(isRecordAbilToDel(flgValido, flgDiSistema)){
//			deleteButton.show();
//		} else {
//			deleteButton.hide();
//		}	
//		if(isRecordAbilToMod(flgDiSistema)) {
//			editButton.show();
//		} else{
//			editButton.hide();
//		}				
//		altreOpButton.hide();		
//	}
	
}
