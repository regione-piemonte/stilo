/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.utility.ui.module.layout.client.common.CustomDetail;

import java.util.Map;

import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.BackgroundRepeat;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class AttributiDinamiciLayout extends VLayout {

	private AttributiDinamiciDetail detail;
	private ToolStrip toolStripButtons;
	private ToolStripButton buttonSalva;

	public AttributiDinamiciLayout(String nomeEntita, RecordList attributiAdd, Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista,
			Map mappaDocumenti) {

		setWidth100();
		setHeight100();
		setAutoDraw(false);
		setCanHover(false);
		setPadding(5);
		setOverflow(Overflow.AUTO);
		
		detail = new AttributiDinamiciDetail(nomeEntita, attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista, mappaDocumenti,
				null, null, null);

		buttonSalva = new ToolStripButton("Salva", "buttons/save.png");
		buttonSalva.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if (detail.validate()) {
					SC.say(((AttributiDinamiciDetail) detail).getValuesManager().getValues().toString());
				}
			}
		});

		toolStripButtons = new ToolStrip();
		toolStripButtons.setAlign(Alignment.CENTER);
		toolStripButtons.setHeight(30);
		toolStripButtons.setWidth100();
		toolStripButtons.setBackgroundColor("transparent");
		toolStripButtons.setBackgroundImage("blank.png");
		toolStripButtons.setBackgroundRepeat(BackgroundRepeat.REPEAT_X);
		toolStripButtons.setBorder("0px");
		toolStripButtons.addFill();
		toolStripButtons.addMember(buttonSalva);
		toolStripButtons.addFill();

		setMembers(detail, toolStripButtons);

	}

	public void setCanEdit(Boolean canEdit) {
		detail.setCanEdit(canEdit);
	}

}
