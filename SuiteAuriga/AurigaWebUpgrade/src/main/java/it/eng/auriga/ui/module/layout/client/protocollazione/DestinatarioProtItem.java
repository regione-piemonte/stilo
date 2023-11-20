/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class DestinatarioProtItem extends ReplicableItem {

	private boolean showFlgAssegnaAlDestinatario = true;
	// Flag per disabilitare la ricerca della denominazione all'onChanged
	protected boolean cercaInRubricaAfterChanged = true;

	public boolean getShowFlgAssegnaAlDestinatario() {
		return showFlgAssegnaAlDestinatario;
	}

	public void setShowFlgAssegnaAlDestinatario(boolean showFlgAssegnaAlDestinatario) {
		this.showFlgAssegnaAlDestinatario = showFlgAssegnaAlDestinatario;
		redraw();
	}
		
	public boolean isCercaInRubricaAfterChanged() {
		return cercaInRubricaAfterChanged;
	}

	public void setCercaInRubricaAfterChanged(boolean cercaInRubricaAfterChanged) {
		this.cercaInRubricaAfterChanged = cercaInRubricaAfterChanged;
	}
	
	public boolean isDimOrganigrammaNonStd() {
		return AurigaLayout.getParametroDBAsBoolean("DIM_ORGANIGRAMMA_NONSTD") && AurigaLayout.isPrivilegioAttivo("SEL/DEST/ES");		
	}

	public boolean getFlgAssegnaAlDestinatarioDefault() {
		return AurigaLayout.getParametroDBAsBoolean("ASSEGNAZIONE_DEST_DEFAULT");
	}
	
	public boolean getFlgSoloInOrganigramma() {
		return AurigaLayout.getParametroDBAsBoolean("DEST_SOLO_IN_ORGANIGRAMMA");
	}
	
	@Override
	public void setUpClickRemoveHandler(VLayout lVLayout, HLayout lHLayout) {
		super.setUpClickRemoveHandler(lVLayout, lHLayout);
		manageOnChanged();
		manageChangedFlgAssegnaAlDestinatario(null);
	}
	
	public void manageOnChanged() {
		
	}
	
	public void manageChangeFlgAssegnaAlDestinatario(ChangeEvent event) {
		
	}
	
	public void manageChangedFlgAssegnaAlDestinatario(Record canvasRecord) {
		
	}
	
	public boolean isFlgAssegnazioneCondivisioneMutuamenteEsclusivi() {
		return AurigaLayout.getParametroDBAsBoolean("SHOW_CC_IN_DEST_REG") && AurigaLayout.getParametroDBAsBoolean("FLG_CC_SOLO_PER_DEST_IN_ORGANIGRAMMA");
	}
	
	public boolean isForzaAssegnazioneCondivisione() {
		return AurigaLayout.getParametroDBAsBoolean("SHOW_CC_IN_DEST_REG") && AurigaLayout.getParametroDBAsBoolean("FORZA_ASS_COPIA_CC_DEST_REG");
	}

//	public String getTitleAggiungiDestInAssegnatariButton() {
//		return null;
//	}
//
//	public boolean getShowAggiungiDestInAssegnatariButton() {
//		return false;
//	}
//
//	public boolean getShowAggiungiDestInAssegnatariButtonInCanvas(Record lRecordCanvas) {
//		return false;
//	}

	public boolean getShowItemsIndirizzo() {
		return false;
	}

	public void setCanEditMezzoTrasmissioneMode(boolean mode) {
		setCanEdit(mode);
	}
	
	public String getIdUdProtocollo() {
		try {
			Record lDetailRecord = new Record(getForm().getValuesManager().getValues());
			return lDetailRecord.getAttribute("idUd");
		} catch(Exception e) {}
		return null;
	}
	
	public boolean isProtInModalitaWizard() {
		return false;
	}
	
	public boolean isSupportoOriginaleProtValorizzato() {
		return false;		
	}
	
	public String getSupportoOriginaleProt() {
		return null;
	}
	
	public boolean isAttivoAssegnatarioUnicoProt() {
		return false;
	}
	
	public boolean isAttivoAssegnatarioUnicoCartaceoProt() {
		return false;
	}	
	
	public boolean isAttivaRestrAssCartaceoProt() {
		return false;
	}
	
	public int getNroAssegnazioniProt() {
		return 0;
	}

	public boolean isInBozza() {
		return false;
	}

	protected boolean isPersonaGiuridica(String tipoSoggetto) {
		if (tipoSoggetto != null) {
			if ("G".equals(tipoSoggetto) || "PA".equals(tipoSoggetto) || "PG".equals(tipoSoggetto) || "UOI".equals(tipoSoggetto) || "AOOI".equals(tipoSoggetto)
					|| "AOOE".equals(tipoSoggetto))
				return true;
		}
		return false;
	}

	protected boolean isPersonaFisica(String tipoSoggetto) {
		if (tipoSoggetto != null) {
			if ("F".equals(tipoSoggetto) || "PF".equals(tipoSoggetto) || "UP".equals(tipoSoggetto))
				return true;
		}
		return false;
	}
	
	public void resetDefaultValueFlgAssegnaAlDestinatario() {
		
	}
	
	@Override
	public void redraw() {
		super.redraw();
		VLayout lVLayout = getVLayout();
		for (int i = 0; i < getTotalMembers(); i++) {
			HLayout lHLayout = (HLayout) lVLayout.getMember(i);
			ReplicableCanvas lReplicableCanvas = getReplicableCanvas(lHLayout);
			lReplicableCanvas.redraw();
		}
	}
	
	public void reloadTipoValueMap() {
		
	}

}
