/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class FirmatariItem extends ReplicableItem {
	
	// Flag per disabilitare la ricerca della denominazione all'onChanged
	protected boolean cercaInRubricaAfterChanged = true;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		FirmatariCanvas lFirmatariCanvas = new FirmatariCanvas(this);
		return lFirmatariCanvas;
	}

	@Override
	protected ImgButton[] createAddButtons() {
		ImgButton[] addButtons = new ImgButton[2];

		addButtons[0] = new ImgButton();
		addButtons[0].setSrc("[SKIN]actions/add.png");
		addButtons[0].setShowDown(false);
		addButtons[0].setShowRollOver(false);
		addButtons[0].setSize(16);
		addButtons[0].setPrompt(I18NUtil.getMessages().newButton_prompt());
		addButtons[0].addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onClickNewButton();
			}
		});

		addButtons[1] = new ImgButton();
		addButtons[1].setSrc("lookup/rubricamulti.png");
		addButtons[1].setShowDown(false);
		addButtons[1].setShowRollOver(false);
		addButtons[1].setSize(16);
		addButtons[1].setPrompt(I18NUtil.getMessages().protocollazione_detail_multilookupRubricaButton_prompt());
		addButtons[1].addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				cercaInRubricaAfterChanged = false;
				FirmatariMultiLookupRubrica lookupRubricaPopup = new FirmatariMultiLookupRubrica(null);
				lookupRubricaPopup.show();
			}
		});

		return addButtons;
	}
		
	public boolean isCercaInRubricaAfterChanged() {
		return cercaInRubricaAfterChanged;
	}
	
	public void setCercaInRubricaAfterChanged(boolean cercaInRubricaAfterChanged) {
		this.cercaInRubricaAfterChanged = cercaInRubricaAfterChanged;
	}

	public boolean getShowEmail() {
		return false;
	}
	
	public boolean getShowTelefono() {
		return false;
	}
	
	@Override
	public void setUpClickRemoveHandler(VLayout lVLayout, HLayout lHLayout) {
		super.setUpClickRemoveHandler(lVLayout, lHLayout);
		manageOnChanged();
	}
	
	public void manageOnChanged() {
		
	}

	public class FirmatariMultiLookupRubrica extends LookupSoggettiPopup {

		private List<FirmatariCanvas> multiLookupList = new ArrayList<FirmatariCanvas>();

		public FirmatariMultiLookupRubrica(Record record) {
			super(record, null, false);
		}
		
		@Override
		public String getFinalita() {
			return "SEL_SOGG_EST";
		}

		@Override
		public void manageLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupBack(Record record) {
			FirmatariCanvas lastCanvas = (FirmatariCanvas) getLastCanvas();
			if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(lastCanvas);
			} else {
				FirmatariCanvas canvas = (FirmatariCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(canvas);
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			for (FirmatariCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if (values.getAttribute("idSoggetto").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}

		@Override
		public String[] getTipiAmmessi() {
//			return AurigaLayout.getParametroDBAsBoolean("SHOW_AOOI_IN_MITT_DEST") ? new String[] { "AOOI", "PA", "PF", "PG" } : new String[] { "PA", "PF", "PG" };
			return new String[] { "PF" };
		}

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
	
	public boolean isRequiredDenominazione(boolean hasValue){
		return true;
	}

}
