/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public abstract class MittenteProtItem extends ReplicableItem {

	private boolean showFlgAssegnaAlMittente = true;
	// Flag per disabilitare la ricerca della denominazione all'onChanged
	protected boolean cercaInRubricaAfterChanged = true;

	public boolean getShowFlgAssegnaAlMittente() {
		return showFlgAssegnaAlMittente;
	}

	public void setShowFlgAssegnaAlMittente(boolean showFlgAssegnaAlMittente) {
		this.showFlgAssegnaAlMittente = showFlgAssegnaAlMittente;
		redraw();
	}
	
	public boolean isCercaInRubricaAfterChanged() {
		return cercaInRubricaAfterChanged;
	}

	public void setCercaInRubricaAfterChanged(boolean cercaInRubricaAfterChanged) {
		this.cercaInRubricaAfterChanged = cercaInRubricaAfterChanged;
	}

	public boolean getFlgAssegnaAlMittenteDefault() {
		return AurigaLayout.getParametroDBAsBoolean("ASSEGNAZIONE_MITT_DEFAULT");
	}
	
	public boolean getFlgSoloInOrganigramma() {
		return AurigaLayout.getParametroDBAsBoolean("MITT_SOLO_IN_ORGANIGRAMMA");
	}
	
	public boolean isRichiestaPubblicazioneAlbo() {
		return false;
	}
	
	@Override
	public void setUpClickRemoveHandler(VLayout lVLayout, HLayout lHLayout) {
		super.setUpClickRemoveHandler(lVLayout, lHLayout);
		manageOnChanged();
		manageChangedFlgAssegnaAlMittente(null);
	}
	
	public void manageOnChanged() {
		
	}

	public void manageChangeFlgAssegnaAlMittente(ChangeEvent event) {
		
	}
	
	public void manageChangedFlgAssegnaAlMittente(Record canvasRecord) {
		
	}
	
	public boolean isEmailMittenteItemObbligatorio() {
		return false;
	}

	public boolean getShowItemsIndirizzo() {
		return false;
	}
	
	public boolean getAllowOnlyPersonaFisica() {
		return false;
	}
	
	public boolean getAllowOnlyUnitaDiPersonale() {
		return false;
	}
	
	public boolean getShowItemSelTipoMittente() {
		return true;
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
		if ("G".equals(tipoSoggetto) || "PA".equals(tipoSoggetto) || "PG".equals(tipoSoggetto) || "UOI".equals(tipoSoggetto) || "AOOI".equals(tipoSoggetto)
				|| "AOOE".equals(tipoSoggetto))
			return true;
		else
			return false;
	}

	protected boolean isPersonaFisica(String tipoSoggetto) {
		if ("F".equals(tipoSoggetto) || "PF".equals(tipoSoggetto) || "UP".equals(tipoSoggetto))
			return true;
		else
			return false;
	}
	
	public void resetDefaultValueFlgAssegnaAlMittente() {
		
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
