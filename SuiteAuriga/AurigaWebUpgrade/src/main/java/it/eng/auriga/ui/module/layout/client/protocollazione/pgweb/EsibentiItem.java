/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.anagrafiche.LookupSoggettiPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class EsibentiItem extends ReplicableItem {
	
	private Boolean flgAncheMittente;
	
	// Flag per disabilitare la ricerca della denominazione all'onChanged
	protected boolean cercaInRubricaAfterChanged = true;

	@Override
	public ReplicableCanvas getCanvasToReply() {
		EsibenteCanvas lEsibenteCanvas = new EsibenteCanvas(this);
		return lEsibenteCanvas;
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
				EsibenteMultiLookupRubrica lookupRubricaPopup = new EsibenteMultiLookupRubrica(null);
				lookupRubricaPopup.show();
			}
		});

		return addButtons;
	}
	
	@Override
	public void setUpClickRemoveHandler(VLayout lVLayout, HLayout lHLayout) {
		super.setUpClickRemoveHandler(lVLayout, lHLayout);
		manageOnChanged();
	}
	
	public void manageOnChanged() {
		
	}
	
	public Boolean getFlgAncheMittente() {
		return flgAncheMittente;
	}
	
	public void setFlgAncheMittente(Boolean flgAncheMittente) {
		boolean isChanged = this.flgAncheMittente != null && flgAncheMittente != null && !this.flgAncheMittente.equals(flgAncheMittente);
		this.flgAncheMittente = flgAncheMittente;
		if(isChanged) {
			if(getForm() != null) {
				getForm().redraw();
			}
		}
	}
	
	public boolean isCercaInRubricaAfterChanged() {
		return cercaInRubricaAfterChanged;
	}

	public void setCercaInRubricaAfterChanged(boolean cercaInRubricaAfterChanged) {
		this.cercaInRubricaAfterChanged = cercaInRubricaAfterChanged;
	}

	public class EsibenteMultiLookupRubrica extends LookupSoggettiPopup {

		private List<EsibenteCanvas> multiLookupList = new ArrayList<EsibenteCanvas>();

		public EsibenteMultiLookupRubrica(Record record) {
			super(record, "PF", false);
		}
		
		@Override
		public String getFinalita() {
			return "SEL_PF";
		}

		@Override
		public void manageLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupBack(Record record) {
			EsibenteCanvas lastCanvas = (EsibenteCanvas) getLastCanvas();
			if (lastCanvas != null && !lastCanvas.isChangedAndValid()) {
				lastCanvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(lastCanvas);
			} else {
				EsibenteCanvas canvas = (EsibenteCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordRubrica(record);
				multiLookupList.add(canvas);
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			for (EsibenteCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if (values.getAttribute("idSoggetto").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}

		@Override
		public String[] getTipiAmmessi() {
			return new String[] { "PF" };
		}

	}
	
	public boolean getShowAncheMittente() {
		return true;
	}
	
	public boolean isRequiredDenominazione(boolean hasValue) {
		return true;
	}
	
	public boolean showItemsIndirizzo() {
		return true;
	}
	
//	public boolean isCognomeObbligatorio() {
//		return false;
//	}
//	
//	public boolean isNomeObbligatorio() {
//		return false;
//	}
	
	public boolean isTipoDocRiconoscimentoToShow() {
		return true;
	}
	
	public boolean isEstremiDocRiconoscimentoToShow() {
		return true;
	}
	
	public boolean isEmailItemToShow() {
		return false;
	}
	
	public boolean isEmailItemObbligatorio() {
		return false;
	}
	
	public boolean validazioneItemAbilitata(){
		return isVisible() && isObbligatorio();
	}

}
