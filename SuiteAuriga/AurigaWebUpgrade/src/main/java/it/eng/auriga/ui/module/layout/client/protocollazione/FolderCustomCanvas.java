/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.archivio.LookupArchivioPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.util.StringUtil;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemHoverFormatter;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

public class FolderCustomCanvas extends ReplicableCanvas {

	private HiddenItem idHiddenItem;
	private HiddenItem livelloRiservatezzaHiddenItem;
	private TextItem pathItem;
	private ExtendedTextItem codiceItem;
	private CheckboxItem flgCapofilaItem;
	private ExtendedTextItem capofilaItem;
	private ExtendedTextItem docCorrenteItem;
	private ImgButtonItem lookupArchivioButton;
	private ReplicableCanvasForm mDynamicForm;

	public FolderCustomCanvas(FolderCustomItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		mDynamicForm.setValidateOnChange(false);

		idHiddenItem = new HiddenItem("id");

		livelloRiservatezzaHiddenItem = new HiddenItem("livelloRiservatezza");

		// indice
		pathItem = new ExtendedTextItem("path");
		pathItem.setShowTitle(false);
		pathItem.setWidth(650);
		pathItem.setColSpan(1);
		pathItem.setRequired(true);
		pathItem.setItemHoverFormatter(true, new FormItemHoverFormatter() {

			@Override
			public String getHoverHTML(FormItem item, DynamicForm form) {
				return StringUtil.asHTML((String) item.getValue());
			}
		});

		codiceItem = new ExtendedTextItem("codice", I18NUtil.getMessages().protocollazione_detail_codiceItem_title());
		codiceItem.setEndRow(false);
		codiceItem.setWidth(120);
		// codiceItem.setColSpan(4);
		codiceItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// isNotMoreValid();
				// manageBlur();
			}
		});
		codiceItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_ATT_CUSTOM_TIPO_GUI");
			}
		});

		flgCapofilaItem = new CheckboxItem("flgCapofila");
		flgCapofilaItem.setTitle(I18NUtil.getMessages().protocollazione_detail_capofilaItem_title());
		flgCapofilaItem.setShowLabel(false);
		flgCapofilaItem.setShowTitle(true);
		flgCapofilaItem.setLabelAsTitle(true);
		// flgCapofilaItem.setColSpan(1);
		flgCapofilaItem.setWidth(20);
		flgCapofilaItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				markForRedraw();
			}
		});
		flgCapofilaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CAPOFILA") && !((FolderCustomItem) getItem()).isFolderizzazioneMassiva();
			}
		});

		docCorrenteItem = new ExtendedTextItem();
		docCorrenteItem.setShowTitle(false);
		docCorrenteItem.setEndRow(false);
		docCorrenteItem.setWidth(120);
		docCorrenteItem.setColSpan(1);
		docCorrenteItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				docCorrenteItem.setCanEdit(false);
				docCorrenteItem.setValue("Doc. corrente");
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CAPOFILA") && (flgCapofilaItem.getValue() != null && flgCapofilaItem.getValueAsBoolean());
			}
		});

		capofilaItem = new ExtendedTextItem("capofila") {

			@Override
			public void setCanEdit(Boolean canEdit) {
				
				super.setCanEdit(canEdit);
				setHint(canEdit ? "Registro N.ro/Anno" : null);
				setShowHintInField(true);
			}
		};
		capofilaItem.setShowTitle(false);
		capofilaItem.setEndRow(false);
		capofilaItem.setWidth(120);
		// capofilaItem.setColSpan(1);
		capofilaItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				// isNotMoreValid();
				// manageBlur();
			}
		});
		capofilaItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return AurigaLayout.getParametroDBAsBoolean("ATTIVA_CAPOFILA") && (flgCapofilaItem.getValue() == null || !flgCapofilaItem.getValueAsBoolean());
			}
		});

		// BOTTONI : seleziona dall'archivio, nuovo
		lookupArchivioButton = new ImgButtonItem("lookupArchivioButton", "lookup/archivio.png", "Sfoglia");
		lookupArchivioButton.setEndRow(false);
		lookupArchivioButton.setColSpan(1);
		lookupArchivioButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				
				ClassificaFascicoloLookupArchivio lookupArchivioPopup = new ClassificaFascicoloLookupArchivio(mDynamicForm.getValuesAsRecord());
				lookupArchivioPopup.show();
			}
		});

		mDynamicForm.setFields(idHiddenItem, livelloRiservatezzaHiddenItem, pathItem, codiceItem, flgCapofilaItem, capofilaItem, docCorrenteItem,
				lookupArchivioButton);

		mDynamicForm.setNumCols(19);
		mDynamicForm.setColWidths("50", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		addChild(mDynamicForm);

	}

	public void setFormValuesFromRecordArchivio(Record record) {
		mDynamicForm.clearErrors(true);
		String id = record.getAttribute("idUdFolder");
		if (id == null || "".equals(id)) {
			id = record.getAttribute("idFolder");
		}
		setIfNotNull("id", id);
		setIfNotNull("livelloRiservatezza", record, "livelloRiservatezza");
		String path = record.getAttribute("percorsoNome");
		if (path == null || "".equals(path)) {
			String percorsoFolderApp = record.getAttribute("percorsoFolderApp");
			if (percorsoFolderApp != null && !"".equals(percorsoFolderApp)) {
				path = record.getAttribute("percorsoFolderApp") + "/" + record.getAttribute("nome");
			} else {
				path = record.getAttribute("nome");
			}
		}
		setIfNotNull("path", path);
		String codice = record.getAttribute("nroSecondario");
		if (codice == null || "".equals(codice)) {
			codice = record.getAttribute("codice");
		}
		setIfNotNull("codice", codice);
		String capofila = record.getAttribute("estremiDocCapofila");
		if (capofila == null || "".equals(capofila)) {
			capofila = record.getAttribute("capofila");
		}
		setIfNotNull("capofila", capofila);
		mDynamicForm.markForRedraw();
	}

	private void setIfNotNull(String property, Record record, String attribute) {
		if (mDynamicForm.getValue(property) == null) {
			mDynamicForm.setValue(property, record.getAttribute(attribute));
		} else {
			if (!mDynamicForm.getValue(property).equals(record.getAttribute(attribute))) {
				mDynamicForm.setValue(property, record.getAttribute(attribute));
			}
		}
	}

	private void setIfNotNull(String property, String value) {
		if (mDynamicForm.getValue(property) == null) {
			mDynamicForm.setValue(property, value);
		} else {
			if (!mDynamicForm.getValue(property).equals(value)) {
				mDynamicForm.setValue(property, value);
			}
		}
	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	public class ClassificaFascicoloLookupArchivio extends LookupArchivioPopup {

		public ClassificaFascicoloLookupArchivio(Record record) {
			super(record, true);
		}

		public ClassificaFascicoloLookupArchivio(Record record, String idRootNode) {
			super(record, idRootNode, true);
		}

		@Override
		public String getFinalita() {			
			return "FOLDERIZZA_UD";
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordArchivio(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {

		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}
	}

	@Override
	public void setCanEdit(Boolean canEdit) {
		
		super.setCanEdit(canEdit);
		pathItem.setCanEdit(false);
	}

}
