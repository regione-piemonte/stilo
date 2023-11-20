/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.ReplicableItem;

public class AttributiAddXEventiDelTipoItem extends ReplicableItem {

	@Override
	public ReplicableCanvas getCanvasToReply() {
		return new AttributiAddXEventiDelTipoCanvas(this);
	}

	@Override
	protected ImgButton[] createAddButtons() {
		
		ImgButton[] addButtons = new ImgButton[1];

		addButtons[0] = new ImgButton();
		addButtons[0].setSrc("lookup/attributiaddmulti.png");
		addButtons[0].setShowDown(false);
		addButtons[0].setShowRollOver(false);
		addButtons[0].setSize(16);
		addButtons[0].setPrompt("Aggiunta con scelta multipla da lista attributi custom");
		addButtons[0].addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DefAttivitaProcedimentiLookupAttributiCustom lookupAttributiCustomPopup = new DefAttivitaProcedimentiLookupAttributiCustom(null);
				lookupAttributiCustomPopup.show();
			}
		});

		return addButtons;
	}

	public class DefAttivitaProcedimentiLookupAttributiCustom extends LookupAttributiCustomPopup {

		private List<AttributiAddXEventiDelTipoCanvas> multiLookupList = new ArrayList<AttributiAddXEventiDelTipoCanvas>();

		public DefAttivitaProcedimentiLookupAttributiCustom(Record record) {
			super(record, null, null, false);
		}

		@Override
		public void manageLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupBack(Record record) {
			AttributiAddXEventiDelTipoCanvas lastCanvas = (AttributiAddXEventiDelTipoCanvas) getLastCanvas();
			if (lastCanvas != null && !lastCanvas.isChanged()) {
				lastCanvas.setFormValuesFromRecordAttributiCustom(record);
				multiLookupList.add(lastCanvas);
			} else {
				AttributiAddXEventiDelTipoCanvas canvas = (AttributiAddXEventiDelTipoCanvas) onClickNewButton();
				canvas.setFormValuesFromRecordAttributiCustom(record);
				multiLookupList.add(canvas);
			}
		}

		@Override
		public void manageMultiLookupUndo(Record record) {
			for (AttributiAddXEventiDelTipoCanvas canvas : multiLookupList) {
				Record values = canvas.getFormValuesAsRecord();
				if (values.getAttribute("nome").equals(record.getAttribute("id"))) {
					setUpClickRemove(canvas.getVLayout(), canvas.getHLayout());
				}
			}
		}
	}
	
	public String getFlgIsAssociataIterWf() {
		return "";
	}

}