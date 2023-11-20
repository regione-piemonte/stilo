/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.DSOperationType;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.common.ConfigurableFilter;
import it.eng.utility.ui.module.layout.client.common.CustomLayout;

public class ConfigurazioneFlussiLayout extends CustomLayout {

	public ConfigurazioneFlussiLayout(String nomeEntita) {
		this(nomeEntita, null, null, null);
	}
	
	public ConfigurazioneFlussiLayout(String nomeEntita, String finalita) {
		this(nomeEntita, finalita, null, null);
	}

	
	public ConfigurazioneFlussiLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola) {
		this(nomeEntita, finalita, flgSelezioneSingola, null);
	}
	
	public ConfigurazioneFlussiLayout(String nomeEntita, String finalita, Boolean flgSelezioneSingola, Boolean showOnlyDetail){
		  super(nomeEntita, 
				new GWTRestDataSource("ConfigurazioneFlussiDatasource"), 
				new ConfigurableFilter(nomeEntita), 
				new ConfigurazioneFlussiList(nomeEntita), 
				new ConfigurazioneFlussiDetail(nomeEntita), 
				finalita,
				flgSelezioneSingola,
				showOnlyDetail);
		  
		  newButton.setPrompt("Aggiungi fase");
		  
		  editButton.setPrompt("Profilatura");
		  
		  multiselectButton.hide();	
		  
		  if (!isAbilToIns()) {
			  newButton.hide();
		  }
	}
	
	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("DC/WF;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("DC/WF;M");
	}

	public static boolean isAbilToDel() {
		return Layout.isPrivilegioAttivo("DC/WF;FC");
	}
	
	@Override
	 public boolean getDefaultDetailAuto() {
		return false;
	}
	
	@Override
	public String getNewDetailTitle() {
		return "Creazione nuova fase";
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Dettaglio " + (getTipoEstremiRecord(record));
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		return "Profilatura " + (getTipoEstremiRecord(record));
	}

	public String getTipoEstremiRecord(Record record) {
		if(record.getAttribute("idTask") != null && !"".equals(record.getAttribute("idTask"))) {
			return " task " + record.getAttribute("nomeTask") + " dell'iter workflow " + record.getAttribute("codTipoFlusso");
		} else {
			return " fase " + record.getAttribute("nomeFase") + " dell'iter workflow " + record.getAttribute("codTipoFlusso");
		}
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
		if(isAbilToDel() && isRecordAbilToDel(record)) {
			deleteButton.show();
		} else {
			deleteButton.hide();
		}
		if(isAbilToMod()) {
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
	public void manageNewClick() {
		String codTipoflusso = getCodTipoFlusso();
		if(codTipoflusso != null && !"".equals(codTipoflusso)) {
			super.manageNewClick();
			Map<String, Object> initialValues = new HashMap<String, Object>();
			initialValues.put("codTipoFlusso", getCodTipoFlusso());
			detail.editNewRecord(initialValues);
			detail.newMode();
		} else {
			AurigaLayout.addMessage(new MessageBean("Obbligatorio selezionare un iter workflow", "", MessageType.ERROR));
		}
	}
	
	public String getCodTipoFlusso() {
		String codTipoFlusso = null;
		AdvancedCriteria criteria = filter.getCriteria(true);
		if (criteria != null && criteria.getCriteria() != null){
			for (Criterion lCriterion : criteria.getCriteria()) {	
				if("codTipoFlusso".equals(lCriterion.getFieldName())) {     
					codTipoFlusso = lCriterion.getValueAsString();
					break;
				}									
			}
		}	
		return codTipoFlusso;
	}
	
	protected void realSave(final Record record) {
		DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();
		Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
		DSCallback callback = buildDSCallback();
		try {
			if ((saveOperationType != null && saveOperationType.equals(DSOperationType.ADD))) {
				detail.getDataSource().addData(record, callback);
			} else {
				detail.getDataSource().updateData(record, callback);
			}
		} catch (Exception e) {
			Layout.hideWaitPopup();
		}
	}
	
	protected boolean isRecordAbilToDel(Record record) {
		String idTask = record.getAttributeAsString("idTask");
		if (idTask == null || "".equalsIgnoreCase(idTask)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	protected String getDeleteButtonAskMessage() {
		return I18NUtil.getMessages().configurazione_flussi_deleteButtonAsk_message();
	}
	
}
