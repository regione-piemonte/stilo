/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;
import it.eng.utility.ui.module.layout.client.common.MapCreator;

public class OggettarioLayout extends CustomLayout {

	private String flgTipoProv;
	
	public OggettarioLayout() {
		this(null, null, null);
	}

	public OggettarioLayout(Boolean flgSelezioneSingola) {
		this(null, flgSelezioneSingola, null);
	}

	public OggettarioLayout(String flgTipoProv, Boolean flgSelezioneSingola) {
		this(flgTipoProv, flgSelezioneSingola, null);
	}

	public OggettarioLayout(String flgTipoProv, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {				
		super("oggettario", 
				new GWTRestDataSource("OggettarioDataSource", "idModello", FieldType.TEXT),  
				new OggettarioFilter("oggettario", new MapCreator("flgTipoProv",flgTipoProv,"|*|").getProperties()), 
				new OggettarioList("oggettario") ,
				new OggettarioDetail("oggettario", flgTipoProv),
				null,
				flgSelezioneSingola,
				showOnlyDetail
		); 	

		this.flgTipoProv = flgTipoProv;

		multiselectButton.hide();	

		if (!isAbilToIns()) {
			newButton.hide();
		}
	}	

	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("UT/MO/PR;I");
	}
	
	public static boolean isRecordAbilToMod(boolean flagModificabile, boolean flgCreatoDaMe, boolean flgDiSistema) {
		return (flagModificabile) || (!flgDiSistema && (Layout.isPrivilegioAttivo("UT/MO/PB;M") || (flgCreatoDaMe && Layout.isPrivilegioAttivo("UT/MO/PR;M"))));
	}

	public static boolean isRecordAbilToDel(boolean flagCancellabile, boolean flgCreatoDaMe, boolean flgDiSistema) {
		return (flagCancellabile) || (!flgDiSistema && (Layout.isPrivilegioAttivo("UT/MO/PB;FC") || (flgCreatoDaMe && Layout.isPrivilegioAttivo("UT/MO/PR;FC"))));
	}
	
	
	public static boolean isPartizionamentoRubricaAbilitato() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PARTIZ_RUBRICA_X_UO");
	}
	
	public static boolean isAbilInserireSoggInQualsiasiUo() {
		return Layout.isPrivilegioAttivo("UT/MO/PB;I");
	}
	
	public static boolean isAbilModificareSoggInQualsiasiUo() {
		return Layout.isPrivilegioAttivo("UT/MO/PB;M");
	}
	
	public static boolean isAbilInserireModificareSoggPrivato(){
		return Layout.isPrivilegioAttivo("UT/MO/PR;I");
	}

	public static boolean isAbilInserireInUo(){
		return Layout.isPrivilegioAttivo("UT/MO/UO;I");
	}
	
	public static boolean isAbilModificareInUo(){
		return Layout.isPrivilegioAttivo("UT/MO/UO;M");
	}
	
	public static boolean isAbilInserireInPubblico(){
		return Layout.isPrivilegioAttivo("UT/MO/PB;I");
	}
	
	public static boolean isAbilModificareInPubblico(){
		return Layout.isPrivilegioAttivo("UT/MO/PB;M");
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

		if(flgTipoProv != null && !"".equals(flgTipoProv)) {
			criterionList.add(new Criterion("flgVersoRegistrazione", OperatorId.EQUALS, flgTipoProv));
		}							

		Criterion[] criterias = new Criterion[criterionList.size()];
		for(int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().oggettario_detail_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().oggettario_detail_edit_title(getTipoEstremiRecord(record));		
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().oggettario_detail_view_title(getTipoEstremiRecord(record));		
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
		final boolean flgCreatoDaMe  = record.getAttribute("flgCreatoDaMe") != null && record.getAttributeAsBoolean("flgCreatoDaMe");
		final boolean flgDiSistema  = record.getAttribute("flgDiSistema") != null && record.getAttributeAsBoolean("flgDiSistema");
		final boolean flgModificabile  = record.getAttribute("flgModificabile") != null && record.getAttributeAsBoolean("flgModificabile");
		final boolean flgCancellabile  = record.getAttribute("flgCancellabile") != null && record.getAttributeAsBoolean("flgCancellabile");
		if(isRecordAbilToDel(flgCancellabile, flgCreatoDaMe, flgDiSistema)){
			deleteButton.show();
		} else {
			deleteButton.hide();
		}	
		if(isRecordAbilToMod(flgModificabile, flgCreatoDaMe, flgDiSistema)) {
			editButton.show();
		} else{
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
	protected Record createMultiLookupRecord(Record record) {

		final Record newRecord = new Record();
		newRecord.setAttribute("id", record.getAttributeAsString("idModello"));			
		newRecord.setAttribute("nome",  record.getAttributeAsString("nome"));	
		newRecord.setAttribute("icona", "buttons/modello.png");	

		return newRecord;
	}
	
	
}