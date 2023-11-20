/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;

public class RevocaNuovaPropostaAtto2Detail extends NuovaPropostaAtto2Detail {
	
	public RevocaNuovaPropostaAtto2Detail(String nomeEntita) {
		this(nomeEntita, null, null);
	}
	
	public RevocaNuovaPropostaAtto2Detail(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs) {
		this(nomeEntita, attributiAddDocTabs, null);
	}
	
	public RevocaNuovaPropostaAtto2Detail(String nomeEntita, LinkedHashMap<String, String> attributiAddDocTabs, String idTipoDoc) {
		
		super(nomeEntita, attributiAddDocTabs, idTipoDoc);
		
		enableDisableTabs();
		showHideSections();
	}
	
	@Override
	public boolean isAbilToSelUffPropEsteso() {
		return false;
	}
	
	@Override
	protected String getIdUdAttoDaAnnullare() {
		return getValueAsString("idUdAttoDeterminaAContrarre");
	}
	
	@Override
	protected String getTitleAttoDeterminaAContrarre() {
		return "Atto da annullare/revocare";
	}
	
	@Override
	protected void createCaratteristicheProvvedimentoForm() {
		
		super.createCaratteristicheProvvedimentoForm();
		
		flgDeterminaAContrarreTramiteProceduraGaraItem.setVisible(false); 
		flgDeterminaAggiudicaProceduraGaraItem.setVisible(false); 
		flgDeterminaRimodulazioneSpesaGaraAggiudicataItem.setVisible(false); 
		flgDeterminaAssunzioneAumentoRiduzioneOrarioLavoroItem.setVisible(false); 		
	}

	@Override
	protected void ricaricaTabSet() {
		tabSet.setTabs(tabDatiScheda);
	}
	
	@Override
	public boolean showDetailSectionCIG() {
		return false;
	}
	
	@Override
	public boolean showDetailSectionOpzioniSpesa() {
		return false;
	}
	
	@Override
	public boolean showLoghiAggiuntiviDispositivo() {
		return false;
	}
	
	@Override
	public void editNewRecord() {
	
		super.editNewRecord();
		
		if(flgSpesaItem != null) {
			flgSpesaItem.setValue(_FLG_SPESA_NO);
		}
	}	
		
	@Override
	public void editNewRecord(Map initialValues) {
	
		super.editNewRecord(initialValues);
		
		if(flgSpesaItem != null) {
			flgSpesaItem.setValue(_FLG_SPESA_NO);
		}
	}	
			
	@Override
	public void editRecord(Record record) {
		
		super.editRecord(record);
		
		if(flgSpesaItem != null) {
			flgSpesaItem.setValue(_FLG_SPESA_NO);
		}	
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		
		super.setCanEdit(canEdit);
			
		if(listaUfficioProponenteItem != null) {
			listaUfficioProponenteItem.setCanEdit(false);
			listaUfficioProponenteItem.setTabIndex(-1);
		}		

		if(ufficioProponenteItem != null) {
			ufficioProponenteItem.setCanEdit(false);
			ufficioProponenteItem.setTabIndex(-1);
		}	
		
		if(listaRdPItem != null) {
			listaRdPItem.setCanEdit(false);
			listaRdPItem.setTabIndex(-1);
		}	
		
		if(listaRUPItem != null) {
			listaRUPItem.setCanEdit(false);
			listaRUPItem.setTabIndex(-1);
		}	
		
		if(listaDirigentiConcertoItem != null) {
			listaDirigentiConcertoItem.setCanEdit(false);
			listaDirigentiConcertoItem.setTabIndex(-1);
		}
		
		setCanEdit(false, caratteristicheProvvedimentoForm1); 
		setCanEdit(false, caratteristicheProvvedimentoForm2); 		
	}
	
	@Override
	public Record getRecordToSave() {
		Record lRecordToSave = super.getRecordToSave();
		lRecordToSave.setAttribute("flgDettRevocaAtto", true);
		return lRecordToSave;
	}
	
}
