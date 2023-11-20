/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.user.client.Random;
import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class InvioRaccomandateLayout extends CustomLayout {
	
	private String uuidAttuale;
	protected AdvancedCriteria searchCriteria = null;
//	private InvioRaccomandateDataSource datasource;

	public InvioRaccomandateLayout() {
		this(null, null, null);
	}

	public InvioRaccomandateLayout(String finalita, Boolean flgSelezioneSingola) {
		this(finalita, flgSelezioneSingola, null);
	}

	public InvioRaccomandateLayout(String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail) {
		super("invio_raccomandate", new GWTRestDataSource("InvioRaccomandateDataSource", "id_busta", FieldType.INTEGER), new InvioRaccomandateFilter("invio_raccomandate"),
				new InvioRaccomandateList("invio_raccomandate"), new CustomDetail("invio_raccomandate"), finalita, flgSelezioneSingola, showOnlyDetail);

		multiselectButton.hide();
		newButton.hide();
	}

	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("DC/ATT;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("DC/ATT;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("DC/ATT;FC");
	}

	@Override
	public void manageOnClickSearchButton() {
		super.manageOnClickSearchButton();
	}

	@Override
	public String getNewDetailTitle() {
		return I18NUtil.getMessages().attributi_custom_new_title();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().attributi_custom_edit_title(getTipoEstremiRecord(record));
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return I18NUtil.getMessages().attributi_custom_view_title(getTipoEstremiRecord(record));
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
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();

		newRecord.setAttribute("id", record.getAttributeAsString("nome"));
		newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		newRecord.setAttribute("icona", "menu/attributi_custom.png");

		return newRecord;
	}

	@Override
	public void doLookup(Record record) {
		if (isRecordSelezionabileForLookup(record)) {
			super.doLookup(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().recordNonSelezionabileXFinalita_message(), "", MessageType.WARNING));
		}
	}
	
	protected boolean isRecordSelezionabileForLookup(Record record) {
		return record.getAttributeAsString("appartenenza") == null || "".equalsIgnoreCase(record.getAttributeAsString("appartenenza"));
	}
	
	@Override
	public void doSimpleSearch(AdvancedCriteria criteria) {
		if (isStoppableSearch()) {
			getDatasource().setShowPrompt(false);
			int lInt = Random.nextInt();
			uuidAttuale = lInt + "";
			getDatasource().extraparam.put("uuid", uuidAttuale);
			stopSearchButton.show();
		}

		if(showPaginazioneItems()) {
			if(nroPaginaFirstButton != null) {
				nroPaginaFirstButton.disable();
			}
			if(nroPaginaPrevButton != null) {
				nroPaginaPrevButton.disable();	
			}
			if(nroPaginaNextButton != null) {
				nroPaginaNextButton.disable();	
			}
			if(nroPaginaLastButton != null) {
				nroPaginaLastButton.disable();
			}
		}
		
		GWTRestDataSource.settingFetchReferences(this, getDatasource().getServerid());
		list.reloadFieldsFromCriteria(criteria);
		list.deselectAllRecords();
		list.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
		list.clearSelezione();
		list.clearCheckBoxList();
		if (list.isGrouped()) {
			list.ungroup();
		}
		list.invalidateCache();
		try {
			list.getResultSet().setCriteria(criteria);
		} catch (Exception e) {
		}
		if (list.getDataAsRecordList().getLength() == 0) {
			list.fetchData(criteria);
		}
		getDatasource().extraparam.put("uuid", uuidAttuale);
		detail.getValuesManager().clearValues();
		isFetched = true;
		filtroAttivoImg.hide();
		if (criteria != null && criteria.getCriteria() != null) {
			for (Criterion lCriterion : criteria.getCriteria()) {
				if (lCriterion.getValueAsString() != null && !"".equals(lCriterion.getValueAsString())) {
					// Commento questa parte di codice altrimenti se rifaccio una ricerca e i filtri sono nascosti vengono passati vuoti, invece devo ripetere
					// l'ultima ricerca fatta
					// if(filterLayout.isVisible()) {
					// searchCriteria = filter.getCriteria(true);
					// } else {
					// searchCriteria = null;
					// }
					filtroAttivoImg.show();
					break;
				}
			}
		}
		searchCriteria = criteria;
//		setSearchCriteria(searchCriteria);
	}
	
}
