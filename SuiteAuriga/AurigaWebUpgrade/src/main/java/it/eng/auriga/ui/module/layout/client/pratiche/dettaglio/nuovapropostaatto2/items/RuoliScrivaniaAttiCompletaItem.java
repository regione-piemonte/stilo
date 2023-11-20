/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.form.fields.FormItem;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class RuoliScrivaniaAttiCompletaItem extends ReplicableItem {

	private String idSvFieldName;
	private String idSvFromLoadDettFieldName;
	private String codUoFieldName;
	private String descrizioneFieldName;
	private String flgFirmatarioFieldName;
	private String motiviFieldName;
	private String flgRiacqVistoInRitornoIterFieldName;
	private String codiceFiscaleFieldName;
	
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName) {
		this(idSvFieldName, idSvFromLoadDettFieldName, codUoFieldName, descrizioneFieldName, null, null);
	}
	
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName, String flgFirmatarioFieldName) {
		this(idSvFieldName, idSvFromLoadDettFieldName, codUoFieldName, descrizioneFieldName, flgFirmatarioFieldName, null);
	}
	
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName, String flgFirmatarioFieldName, String motiviFieldName) {
		this(idSvFieldName, idSvFromLoadDettFieldName, codUoFieldName, descrizioneFieldName, flgFirmatarioFieldName, motiviFieldName, null);
	}
	
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName, String flgFirmatarioFieldName, String motiviFieldName, String flgRiacqVistoInRitornoIterFieldName) {
		this(idSvFieldName, idSvFromLoadDettFieldName, codUoFieldName, descrizioneFieldName, flgFirmatarioFieldName, motiviFieldName, flgRiacqVistoInRitornoIterFieldName, null);
	}
	public RuoliScrivaniaAttiCompletaItem(String idSvFieldName, String idSvFromLoadDettFieldName, String codUoFieldName, String descrizioneFieldName, String flgFirmatarioFieldName, String motiviFieldName, String flgRiacqVistoInRitornoIterFieldName, String codiceFiscaleFieldName) {
		this.setIdSvFieldName(idSvFieldName);
		this.setIdSvFromLoadDettFieldName(idSvFromLoadDettFieldName);
		this.setCodUoFieldName(codUoFieldName);
		this.setDescrizioneFieldName(descrizioneFieldName);
		this.setFlgFirmatarioFieldName(flgFirmatarioFieldName);
		this.setMotiviFieldName(motiviFieldName);
		this.setFlgRiacqVistoInRitornoIterFieldName(flgRiacqVistoInRitornoIterFieldName);
		this.setCodiceFiscaleFieldName(codiceFiscaleFieldName);
	}

	@Override
	public ReplicableCanvas getCanvasToReply() {
		RuoliScrivaniaAttiCompletaCanvas lRuoliScrivaniaAttiCompletaCanvas = new RuoliScrivaniaAttiCompletaCanvas(this);
		return lRuoliScrivaniaAttiCompletaCanvas;
	}
	
	public boolean skipObbligForEmptySelect() {
		return false;
	}
	
	public String getAltriParamLoadCombo() {
		return null;
	}
		
	public String getIdUdAtto() {
		return null;
	}
	
	public String getIdUdAttoDaAnn() {
		return null;
	}
	
	public boolean getFlgAbilitaAutoFetchDataSelectOrganigramma() {
		return false;
	}
	
	public boolean selectUniqueValueAfterChangedParams() {
		return false;
	}

	public void resetAfterChangedUoProponente() {
		if(getAltriParamLoadCombo() != null && getAltriParamLoadCombo().indexOf("$ID_UO_PROPONENTE$") != -1) {	
			resetAfterChangedParams();
		}
	}
	
	public void resetAfterChangedUoCompetente() {
		if(getAltriParamLoadCombo() != null && getAltriParamLoadCombo().indexOf("$ID_UO_COMPETENTE$") != -1) {	
			resetAfterChangedParams();
		}
	}
	
	public void resetAfterChangedFlgMeroIndirizzo() {
		if(getAltriParamLoadCombo() != null && getAltriParamLoadCombo().indexOf("$FLG_MERO_INDIRIZZO$") != -1) {	
			resetAfterChangedParams();
		}
	}	
	
	public void resetAfterChangedParams() {
		for(ReplicableCanvas lReplicableCanvas : getAllCanvas()) {
			((RuoliScrivaniaAttiCompletaCanvas)lReplicableCanvas).resetAfterChangedParams();
		}	
	}
	
	public void manageAfterChangedRequired() {
		
	}

	public void manageAfterReloadSelectInNotReplicableCanvas(boolean empty) {
	
	}
	
	public String getUoProponenteCorrente() {
		return null;
	}	
	
	public String getUoCompetenteCorrente() {
		return null;
	}
	
	public Boolean getFlgAttoMeroIndirizzo() {
		return null;
	}
	
	public boolean hideNriLivelliUo() {
		return false;
	}

	public boolean showFlgFirmatario() {
		return false;
	}
		
	public String getTitleFlgFirmatario() {
		return I18NUtil.getMessages().nuovaPropostaAtto2_detail_flgFirmatario_title();
	}
	
	public boolean getDefaultValueAsBooleanFlgFirmatario() {
		return false;
	}
	
	public boolean isEditableScrivania() {
		return true;
	}
	
	public boolean isEditableFlgFirmatario() {
		return true;
	}
	
	public boolean showMotivi() {
		return false;
	}
	
	public String getTitleMotivi() {
		return "Motivo/i";
	}

	public boolean isRequiredMotivi() {
		return false;
	}
	
	public boolean isEditableMotivi() {
		return true;
	}

	public boolean showFlgRiacqVistoInRitornoIter() {
		return false;
	}
	
	public void manageChangedScrivaniaSelezionata() {
		
	}
	
	public List<FormItem> getCustomItems(ReplicableCanvas canv) {
		return new ArrayList<FormItem>();
	}

	public String getIdSvFieldName() {
		return idSvFieldName;
	}

	public void setIdSvFieldName(String idSvFieldName) {
		this.idSvFieldName = idSvFieldName;
	}
	
	public String getIdSvFromLoadDettFieldName() {
		return idSvFromLoadDettFieldName;
	}
	
	public void setIdSvFromLoadDettFieldName(String idSvFromLoadDettFieldName) {
		this.idSvFromLoadDettFieldName = idSvFromLoadDettFieldName;
	}
	
	public String getCodUoFieldName() {
		return codUoFieldName;
	}

	public void setCodUoFieldName(String codUoFieldName) {
		this.codUoFieldName = codUoFieldName;
	}

	public String getDescrizioneFieldName() {
		return descrizioneFieldName;
	}

	public void setDescrizioneFieldName(String descrizioneFieldName) {
		this.descrizioneFieldName = descrizioneFieldName;
	}
	
	public String getFlgFirmatarioFieldName() {
		return flgFirmatarioFieldName;
	}

	public void setFlgFirmatarioFieldName(String flgFirmatarioFieldName) {
		this.flgFirmatarioFieldName = flgFirmatarioFieldName;
	}

	public String getMotiviFieldName() {
		return motiviFieldName;
	}

	public void setMotiviFieldName(String motiviFieldName) {
		this.motiviFieldName = motiviFieldName;
	}

	public String getFlgRiacqVistoInRitornoIterFieldName() {
		return flgRiacqVistoInRitornoIterFieldName;
	}

	public void setFlgRiacqVistoInRitornoIterFieldName(String flgRiacqVistoInRitornoIterFieldName) {
		this.flgRiacqVistoInRitornoIterFieldName = flgRiacqVistoInRitornoIterFieldName;
	}

	public String getCodiceFiscaleFieldName() {
		return codiceFiscaleFieldName;
	}
	
	public void setCodiceFiscaleFieldName(String codiceFiscaleFieldName) {
		this.codiceFiscaleFieldName = codiceFiscaleFieldName;
	}
	
}
