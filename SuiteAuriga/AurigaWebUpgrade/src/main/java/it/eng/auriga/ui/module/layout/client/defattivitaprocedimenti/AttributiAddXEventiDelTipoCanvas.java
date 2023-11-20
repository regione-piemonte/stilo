/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;

import it.eng.auriga.ui.module.layout.client.attributiCustom.AttributiCustomWindow;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;

public class AttributiAddXEventiDelTipoCanvas extends ReplicableCanvas {

	private ExtendedTextItem nomeAttributo;
	private ExtendedTextItem etichettaAttributo;
	private CheckboxItem obbligatorioCheckAttributo;
	private CheckboxItem ripetibileCheckAttributo;
	private ExtendedTextItem maxNumAttributo;
	private ImgButtonItem buttonAttributiCustom;

	private CheckboxItem flgTabPrincipaleItem;
	private ExtendedTextItem codiceItem;
	private ExtendedTextItem labelItem;
	private DateTimeItem tsVldDalItem;
	private DateTimeItem tsVldAItem;
	private CheckboxItem tipologiaAssociataWfCheckAttributo;

	private ReplicableCanvasForm mDynamicForm;

	public AttributiAddXEventiDelTipoCanvas(AttributiAddXEventiDelTipoItem item) {
		super(item);
	}

	@Override
	public void disegna() {

		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
		//mDynamicForm.setCellBorder(1);

		nomeAttributo = new ExtendedTextItem("nome", I18NUtil.getMessages().attributi_custom_nome());
		nomeAttributo.setWidth(300);
		nomeAttributo.setColSpan(1);
		nomeAttributo.setCellStyle(it.eng.utility.Styles.cssBlack);
		nomeAttributo.setTooltip("");
		nomeAttributo.setTitleStyle(it.eng.utility.Styles.formTitleBlack);
		nomeAttributo.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isTabPrinc = (flgTabPrincipaleItem.getValue() != null && (Boolean) flgTabPrincipaleItem.getValue() ? true : false);
				if(isTabPrinc)
					nomeAttributo.setColSpan(4);
				else 
					nomeAttributo.setColSpan(1);
				return true;
			}
		});

		etichettaAttributo = new ExtendedTextItem("etichetta", I18NUtil.getMessages().attributi_custom_etichetta());
		etichettaAttributo.setWidth(350);
		etichettaAttributo.setColSpan(1);
		etichettaAttributo.setCellStyle(it.eng.utility.Styles.cssBlack);
		etichettaAttributo.setTooltip("");
		etichettaAttributo.setTitleStyle(it.eng.utility.Styles.formTitleBlack);

		obbligatorioCheckAttributo = new CheckboxItem("checkObbligatorio", I18NUtil.getMessages().def_attivita_procedimenti_canvas_obbligatorio());
		obbligatorioCheckAttributo.setColSpan(1);
		obbligatorioCheckAttributo.setAlign(Alignment.LEFT);
		obbligatorioCheckAttributo.setWidth(50);
		
		tipologiaAssociataWfCheckAttributo = new CheckboxItem("checkExtraIterWf", I18NUtil.getMessages().def_attivita_procedimenti_canvas_associato_wf());
		tipologiaAssociataWfCheckAttributo.setColSpan(1);
		tipologiaAssociataWfCheckAttributo.setAlign(Alignment.LEFT);
		tipologiaAssociataWfCheckAttributo.setWidth(50);
		tipologiaAssociataWfCheckAttributo.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				String flgIsAssociataIterWf = ((AttributiAddXEventiDelTipoItem) getItem()).getFlgIsAssociataIterWf();
				return new Boolean(flgIsAssociataIterWf != null && !flgIsAssociataIterWf.equals("") && "true".equals(flgIsAssociataIterWf) ? true : false);
			}
		});

		ripetibileCheckAttributo = new CheckboxItem("checkRipetibile", I18NUtil.getMessages().def_attivita_procedimenti_canvas_ripetibile());
		ripetibileCheckAttributo.setColSpan(1);
		ripetibileCheckAttributo.setWidth(50);
		ripetibileCheckAttributo.setAlign(Alignment.LEFT);
		ripetibileCheckAttributo.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.clearValue("maxNumValori");				
				mDynamicForm.redraw();
			}
		});

		maxNumAttributo = new ExtendedTextItem("maxNumValori", I18NUtil.getMessages().def_attivita_procedimenti_canvas_nrMaxVal());
		maxNumAttributo.setWidth(50);
		maxNumAttributo.setColSpan(1);
		maxNumAttributo.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return new Boolean(ripetibileCheckAttributo.getValue() != null && (Boolean) ripetibileCheckAttributo.getValue() ? true : false);
			}
		});

		buttonAttributiCustom = new ImgButtonItem("buttonAttributiCustom", "buttons/view.png", "Visualizza dettaglio attributo custom");
		buttonAttributiCustom.setShowTitle(false);
		buttonAttributiCustom.setAlwaysEnabled(true);
		buttonAttributiCustom.setColSpan(1);
		buttonAttributiCustom.setAlign(Alignment.LEFT);
		buttonAttributiCustom.setIconWidth(16);
		buttonAttributiCustom.setIconHeight(16);
		buttonAttributiCustom.setIconVAlign(VerticalAlignment.BOTTOM);
		buttonAttributiCustom.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				dettaglioAttributoCustomDetail((String) nomeAttributo.getValue());
			}
		});

		flgTabPrincipaleItem = new CheckboxItem("flgTabPrincipale", I18NUtil.getMessages().def_attivita_procedimenti_canvas_inTabPrincipale());
		flgTabPrincipaleItem.setColSpan(3);
		flgTabPrincipaleItem.setWidth(50);
		flgTabPrincipaleItem.setAlign(Alignment.LEFT);
		flgTabPrincipaleItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicForm.redraw();
			}
		});

		codiceItem = new ExtendedTextItem("codice", I18NUtil.getMessages().def_attivita_procedimenti_canvas_inTabCodice());
		codiceItem.setWidth(100);
		codiceItem.setColSpan(1);
		codiceItem.setStartRow(true);
		codiceItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isTabPrinc = (flgTabPrincipaleItem.getValue() != null && (Boolean) flgTabPrincipaleItem.getValue() ? true : false);
				return !isTabPrinc;
			}
		});

		labelItem = new ExtendedTextItem("label", I18NUtil.getMessages().def_attivita_procedimenti_canvas_inTabLabel());
		labelItem.setWidth(350);
		labelItem.setColSpan(1);
		labelItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isTabPrinc = (flgTabPrincipaleItem.getValue() != null && (Boolean) flgTabPrincipaleItem.getValue() ? true : false);
				return !isTabPrinc;
			}
		});
		
		tsVldDalItem = new DateTimeItem("tsVldDal", I18NUtil.getMessages().def_attivita_procedimenti_canvas_tsVldDal());
		tsVldDalItem.setColSpan(1);
		tsVldDalItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean isTabPrinc = (flgTabPrincipaleItem.getValue() != null && (Boolean) flgTabPrincipaleItem.getValue() ? true : false);
				if(isTabPrinc) {
					tsVldDalItem.setColSpan(1);
					tsVldDalItem.setStartRow(true);
				} else {
					tsVldDalItem.setColSpan(3);
					tsVldDalItem.setStartRow(false);
				}
				return true;
			}
		});
		
		tsVldAItem = new DateTimeItem("tsVldA", I18NUtil.getMessages().def_attivita_procedimenti_canvas_tsVldA());
		tsVldAItem.setColSpan(1);

		mDynamicForm.setFields(
				nomeAttributo, etichettaAttributo, buttonAttributiCustom, obbligatorioCheckAttributo, ripetibileCheckAttributo, maxNumAttributo,flgTabPrincipaleItem, tipologiaAssociataWfCheckAttributo,
				codiceItem, labelItem, tsVldDalItem, tsVldAItem
		);

		mDynamicForm.setNumCols(15);
		mDynamicForm.setColWidths(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
		addChild(mDynamicForm);

	}

	public void dettaglioAttributoCustomDetail(String nome) {
		AttributiCustomWindow _windowAttributeCustom = new AttributiCustomWindow(nome);
		_windowAttributeCustom.show();
	}

	public class DefAttivitaProcLookupCustomPopup extends LookupAttributiCustomPopup {

		public DefAttivitaProcLookupCustomPopup(Record record, String tipoMittente) {
			super(record, null, null, true);
		}

		@Override
		public void manageLookupBack(Record record) {
			setFormValuesFromRecordAttributiCustom(record);
		}

		@Override
		public void manageMultiLookupBack(Record record) {
		}

		@Override
		public void manageMultiLookupUndo(Record record) {

		}

	}

	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicForm };
	}

	protected void clearFormAttributoCustomValues() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("flgTabPrincipale", "");
		lRecord.setAttribute("codice", "");
		lRecord.setAttribute("label", "");
		lRecord.setAttribute("tsVldDal", "");
		lRecord.setAttribute("tsVldA", "");
		lRecord.setAttribute("nome", "");
		lRecord.setAttribute("etichetta", "");
		lRecord.setAttribute("checkObbligatorio", "");
		lRecord.setAttribute("checkRipetibile", "");
		lRecord.setAttribute("maxNumValori", "");
		nomeAttributo.setTooltip("");
		etichettaAttributo.setTooltip("");
		mDynamicForm.setValues(lRecord.toMap());
	}

	protected void clearIdAttributoCustom() {
		mDynamicForm.clearErrors(true);
		Record lRecord = mDynamicForm.getValuesAsRecord();
		lRecord.setAttribute("nome", "");
		mDynamicForm.setValues(lRecord.toMap());
	}

	public void setFormValuesFromRecordAttributiCustom(Record record) {
		SC.echo(record.getJsObj());
		// Pulisco i dati del soggetto
		clearIdAttributoCustom();
		clearFormAttributoCustomValues();
		mDynamicForm.clearErrors(true);

		String nomeAttributoString = record.getAttribute("nome");

		if (nomeAttributoString != null) {
			mDynamicForm.setValue("nome", nomeAttributoString);
			mDynamicForm.setValue("etichetta", record.getAttribute("etichetta"));
			mDynamicForm.setValue("checkObbligatorio", record.getAttribute("checkObbligatorio"));
			mDynamicForm.setValue("checkRipetibile", record.getAttribute("checkRipetibile"));
			mDynamicForm.setValue("maxNumAttributo", record.getAttribute("maxNumAttributo"));
			mDynamicForm.setValue("flgTabPrincipale", record.getAttribute("flgTabPrincipale"));
			mDynamicForm.setValue("codice", record.getAttribute("codice"));
			mDynamicForm.setValue("label", record.getAttribute("label"));
			mDynamicForm.setValue("tsVldDal", record.getAttribute("tsVldDal"));
			mDynamicForm.setValue("tsVldA", record.getAttribute("tsVldA"));
			nomeAttributo.setTooltip(nomeAttributoString);
			etichettaAttributo.setTooltip(record.getAttribute("etichetta"));
			mDynamicForm.markForRedraw();
		} else {
			mDynamicForm.setFieldErrors("nome", "errore");
		}
	}
	
	@Override
	public void setCanEdit(Boolean canEdit) {
		super.setCanEdit(canEdit);
		nomeAttributo.setCanEdit(false);
		etichettaAttributo.setCanEdit(false);		
	}

}
