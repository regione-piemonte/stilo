/* * SPDX-License-Identifier: AGPL-3.0-or-later * * (C) Copyright 2023 Regione Piemonte * */
package it.eng.auriga.ui.module.layout.client.protocollazione;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class RelVsPraticheApplEsterneCanvas extends ReplicableCanvas {

	private HiddenItem idFolderHiddenItem;
	private TextItem codApplEstItem;
	private TextItem codPraticaItem;
	private DateTimeItem tsAssociazioneApplEstItem;
	private CheckboxItem flgDaAssociareAssociatoItem;
	private HiddenItem flgAssociazioneApplEstHiddenItem;
	private ReplicableCanvasForm mDynamicForm;

	public RelVsPraticheApplEsterneCanvas(RelVsPraticheApplEsterneItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setValidateOnChange(false);

		idFolderHiddenItem = new HiddenItem("idFolder");

		codApplEstItem = new TextItem("codApplEst", "Applicazione") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		codApplEstItem.setWidth(200);
		
		codPraticaItem = new TextItem("codPratica", "Cod. pratica") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		codPraticaItem.setWidth(200);

		tsAssociazioneApplEstItem = new DateTimeItem("tsAssociazioneApplEst", "Doc. associato dall'applicazione il") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
			}
		};
		tsAssociazioneApplEstItem.setWidth(120);
		
		flgDaAssociareAssociatoItem = new CheckboxItem("flgDaAssociareAssociato", "documento da associare/associato") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				if(mDynamicForm.getValue("flgAssociazioneApplEst") != null && "1".equals(mDynamicForm.getValue("flgAssociazioneApplEst"))) {
					super.setCanEdit(false);
				} else {
					super.setCanEdit(canEdit);
				}
			}
		};
		flgDaAssociareAssociatoItem.setWidth(20);
		flgDaAssociareAssociatoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.markForRedraw();
			}
		});

		flgAssociazioneApplEstHiddenItem = new HiddenItem("flgAssociazioneApplEst");

		mDynamicForm.setFields(idFolderHiddenItem, codApplEstItem, codPraticaItem, tsAssociazioneApplEstItem, flgDaAssociareAssociatoItem, flgAssociazioneApplEstHiddenItem);

		mDynamicForm.setNumCols(19);
		mDynamicForm.setColWidths("50", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		addChild(mDynamicForm);

	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}


}
