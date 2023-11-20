/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.form.DynamicForm;

import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HeaderItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.validator.Validator;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.DateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedDateTimeItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedTextItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TitleItem;
import it.eng.utility.ui.module.layout.shared.util.FrontendUtil;

public class AttributoListaCanvas extends ReplicableCanvas {
	
	protected boolean editing;

	private ReplicableCanvasForm[] forms;

	public AttributoListaCanvas(AttributoListaItem item) {
		super(item);		
	}

	@Override
	public void disegna() {

		final VLayout lVLayoutCanvas = new VLayout();
		lVLayoutCanvas.setHeight(5);
		lVLayoutCanvas.setOverflow(Overflow.VISIBLE);

		RecordList datiDettLista = ((AttributoListaItem) getItem()).getDatiDettLista();
		CallbackGenericFunction callback = ((AttributoListaItem) getItem()).getCallbackGeneric();

		Map<Integer, RecordList> mappaAttributiRiga = new HashMap<Integer, RecordList>();

		if (datiDettLista != null && datiDettLista.getLength() > 0) {

			for (int i = 0; i < datiDettLista.getLength(); i++) {
				Record dett = datiDettLista.get(i);

				int riga = 0;
				if (dett.getAttribute("riga") != null && !"".equals(dett.getAttribute("riga"))) {
					riga = Integer.parseInt(dett.getAttribute("riga"));
				}

				RecordList recordList = mappaAttributiRiga.get(new Integer(riga));
				if (recordList == null) {
					recordList = new RecordList();
				}
				recordList.add(dett);
				mappaAttributiRiga.put(new Integer(riga), recordList);
			}

		}

		List<Integer> righeRiquadro = new ArrayList<Integer>(mappaAttributiRiga.keySet());
		Collections.sort(righeRiquadro);

		List<DynamicForm> listaForms = new ArrayList<DynamicForm>();

		ReplicableCanvasForm lHiddenForm = new ReplicableCanvasForm();
//		lHiddenForm.setHeight(0);
		lHiddenForm.setOverflow(Overflow.HIDDEN);
		
		HiddenItem idValoreLista = new HiddenItem("idValoreLista");

		lHiddenForm.setItems(idValoreLista);

		listaForms.add(lHiddenForm);
		
		for (Integer rigaRiquadro : righeRiquadro) {

			RecordList datiDettRiga = mappaAttributiRiga.get(rigaRiquadro);

			AttributoListaForm form = new AttributoListaForm(this, datiDettRiga, righeRiquadro.size(), callback);
			form.setWrapItemTitles(false);
			listaForms.add(form);
		}

		forms = listaForms.toArray(new ReplicableCanvasForm[listaForms.size()]);

		lVLayoutCanvas.setMembers(forms);

		// controllo che ci siano più di due form per far vedere la sezione, perchè conto anche il primo che è nascosto
		if (((AttributoListaItem) getItem()).showCanvasSection() && forms != null && forms.length > 2) {
			AttributoListaCanvasSection attributoListaCanvasSection = new AttributoListaCanvasSection(lVLayoutCanvas, 2, forms);
			lVLayoutCanvas.setMembers(attributoListaCanvasSection);
		}
		
		addChild(lVLayoutCanvas);

		alignFormItemTitleWidth(false);
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		return forms;
	}

	public class AttributoListaForm extends ReplicableCanvasForm {
		
		public AttributoListaForm(final AttributoListaCanvas canvas, RecordList datiDettRiga, int numRighe, CallbackGenericFunction callback) {

			Map<String, RecordList> mappaAttributiNested = new HashMap<String, RecordList>();

			if (datiDettRiga != null && datiDettRiga.getLength() > 0) {

				Map<Integer, Record> mappaColonne = new HashMap<Integer, Record>(datiDettRiga.getLength());

				for (int i = 0; i < datiDettRiga.getLength(); i++) {

					Record dett = datiDettRiga.get(i);

					String nomeAttrApp = dett.getAttribute("nomeAttrApp");
					if (nomeAttrApp != null && !"".equals(nomeAttrApp)) {
						if (!mappaAttributiNested.containsKey(nomeAttrApp)) {
							mappaAttributiNested.put(nomeAttrApp, new RecordList());
						}
						mappaAttributiNested.get(nomeAttrApp).add(dett);

					} else {
						mappaColonne.put(new Integer(dett.getAttribute("numero")), dett);
					}
				}

				List<Integer> colonne = new ArrayList<Integer>(mappaColonne.keySet());
				Collections.sort(colonne);

				int numCols = (colonne.size() * 2) + 2;
				Object[] colWidths = new Object[numCols];
				for (int i = 0; i < numCols; i++) {
					if (i < (numCols - 2)) {									
						Integer colNum = 0;
						// Se e' pari 
						if (i%2==0 && (i/2 < colonne.size()) ){
							int a = i/2;
							colNum = colonne.get(a);
						
							String tipo = "";
							if (mappaColonne.get(colNum)!=null) {
								tipo = mappaColonne.get(colNum).getAttribute("tipo");
							}						
							if (tipo.equalsIgnoreCase("CKEDITOR"))
								colWidths[i] = 800;	
							else
								colWidths[i] = 10;
						}
						else{
							colWidths[i] = 10;
						}
					} else {
						colWidths[i] = "*";
					}
				}

				setNumCols(numCols);
				setColWidths(colWidths);

				List<FormItem> items = new ArrayList<FormItem>();

				for (int i = 0; i < colonne.size(); i++) {

					Integer nroColonna = colonne.get(i);

					Record dett = mappaColonne.get(nroColonna);

					FormItem item = null;
					if ("DATE".equals(dett.getAttribute("tipo"))) {
						item = buildDateItem(dett);
					} else if ("DATETIME".equals(dett.getAttribute("tipo"))) {
						item = buildDateTimeItem(dett);
					} else if ("TEXT".equals(dett.getAttribute("tipo"))) {
						if (existValoriScelta(dett)){
							String valoriScelta = getValoriScelta(dett);
							item = buildComboBoxValoriSceltaItem(dett, valoriScelta);
						}
						else{
							item = buildTextItem(dett);	
						}
					} else if ("TEXT-AREA".equals(dett.getAttribute("tipo"))) {
						item = buildTextAreaItem(dett/* , getNumCols() */);
					} else if ("CHECK".equals(dett.getAttribute("tipo"))) {
						item = buildCheckItem(dett);
					} else if ("INTEGER".equals(dett.getAttribute("tipo"))) {
						item = buildIntegerItem(dett);
					} else if ("EURO".equals(dett.getAttribute("tipo"))) {
						item = buildEuroItem(dett);
					} else if ("DECIMAL".equals(dett.getAttribute("tipo"))) {
						item = buildDecimalItem(dett);
					} else if ("COMBO-BOX".equals(dett.getAttribute("tipo"))) {
						item = buildComboBoxItem(dett);
					} else if ("RADIO".equals(dett.getAttribute("tipo"))) {
						item = buildRadioItem(dett);
					} else if ("DOCUMENT".equals(dett.getAttribute("tipo"))) {
						item = buildDocumentItem(dett);
					} else if ("CKEDITOR".equals(dett.getAttribute("tipo"))) {
						item = buildCKEditorItem(dett, numCols);
					} else if ("DOCUMENTLIST".equals(dett.getAttribute("tipo"))) {
						item = buildDocumentListItem(dett);
					} else if ("CUSTOM".equals(dett.getAttribute("tipo"))) {
						item = buildCustomItem(dett, callback);
					}

					if (item != null) {

						if (dett.getAttribute("obbligatorio") != null && "1".equals(dett.getAttribute("obbligatorio"))) {
							if(item instanceof CheckboxItem) {
								item.setValidators(buildRequiredCheckValidator());									
							} else {
								item.setRequired(true);
							}
							item.setAttribute("obbligatorio", true);
							if(item instanceof ExtendedTextItem) {
								((ExtendedTextItem)item).addChangedBlurHandler(new ChangedHandler() {
									
									@Override
									public void onChanged(ChangedEvent event) {
										((AttributoListaItem)canvas.getItem()).manageOnChangedRequiredItemInCanvas();
									}
								});
									
							} else if(item instanceof ExtendedNumericItem) {
								((ExtendedNumericItem)item).addChangedBlurHandler(new ChangedHandler() {
									
									@Override
									public void onChanged(ChangedEvent event) {
										((AttributoListaItem)canvas.getItem()).manageOnChangedRequiredItemInCanvas();
									}
								});										
							} else if(item instanceof ExtendedTextAreaItem) {
								((ExtendedTextAreaItem)item).addChangedBlurHandler(new ChangedHandler() {
									
									@Override
									public void onChanged(ChangedEvent event) {
										((AttributoListaItem)canvas.getItem()).manageOnChangedRequiredItemInCanvas();
									}
								});
							} else if(item instanceof ExtendedDateItem) {
								((ExtendedDateItem)item).addChangedHandler(new ChangedHandler() {
									
									@Override
									public void onChanged(ChangedEvent event) {
										((AttributoListaItem)canvas.getItem()).manageOnChangedRequiredItemInCanvas();
									}
								});										
							} else if(item instanceof ExtendedDateTimeItem) {
								((ExtendedDateTimeItem)item).addChangedHandler(new ChangedHandler() {
									
									@Override
									public void onChanged(ChangedEvent event) {
										((AttributoListaItem)canvas.getItem()).manageOnChangedRequiredItemInCanvas();
									}
								});										
							} else if(!(item instanceof DocumentItem)) {
								item.addChangedHandler(new ChangedHandler() {
									
									@Override
									public void onChanged(ChangedEvent event) {
										((AttributoListaItem)canvas.getItem()).manageOnChangedRequiredItemInCanvas();
									}
								});													
							}
						}

						// nelle sezioni ripetute non dobbiamo mai fare il redrawOnChange di un textItem o si sposta il cursore mentre scriviamo
						if (!((item instanceof com.smartgwt.client.widgets.form.fields.TextItem) || (item instanceof com.smartgwt.client.widgets.form.fields.TextAreaItem))) {
							item.setRedrawOnChange(true);
						}

						item.setWidth(getWidthItem(item, dett));
						
						if (dett.getAttribute("modificabile") != null && "1".equals(dett.getAttribute("modificabile"))) {
							item.setAttribute("modificabile", true);
						}
						
						if (dett.getAttribute("valoreDefault") != null && !"".equals(dett.getAttribute("valoreDefault"))) {
							item.setDefaultValue(dett.getAttribute("valoreDefault"));
						}

						if (dett.getAttribute("regularExpr") != null && !"".equals(dett.getAttribute("regularExpr"))) {
							// ATTENZIONE: questo sovrascrive tutti gli eventuali altri validatori dell'item							
							RegExpValidator regExpValidator = buildRegExpValidator(dett.getAttribute("regularExpr"));
							item.setValidators(regExpValidator);
						}

						boolean showTitle = true;
						// se ho un solo campo (1 colonna e 1 riga) nascondo la label (sarà uguale a quella dell'attributo lista)
						if (item.getTitle() == null || "".equals(item.getTitle()) || (colonne.size() == 1 && numRighe == 1)) {
							showTitle = false;
						}
						item.setShowTitle(showTitle);

						// se è il primo item della riga
						if (items.size() == 0 && !(item instanceof AttributoListaItem)) {
							if (!(item instanceof CheckboxItem) && showTitle) {
								item.setAttribute("alignTitle", true);
							}
						}
						
						if (isGestioneContenutiTabellaTrasp()){
							item.setTitleOrientation(TitleOrientation.TOP);
							item.setTitleAlign(Alignment.LEFT);
							item.setTitle(item.getTitle() != null ? item.getTitle().replaceAll(" ", "&nbsp;") : null);
							item.setWrapTitle(true);
						}

						items.add(item);	
					}
				}

				setItems(items.toArray(new FormItem[items.size()]));
			}
		}
	}

	protected void alignFormItemTitleWidth(boolean afterChangedTitle) {
		int maxItemTitleWidth = 0;
		List<FormItem> fieldsToAlignTitle = new ArrayList<FormItem>();
		for (DynamicForm form : forms) {
			int i = 0;
			for (FormItem formItem : form.getFields()) {
				if (!(formItem instanceof HiddenItem) && formItem.getVisible()) {
					if (formItem.getAttributeAsBoolean("alignTitle") != null && formItem.getAttributeAsBoolean("alignTitle") && i == 0) {
						fieldsToAlignTitle.add(formItem);
						if (!(formItem instanceof TitleItem) && !(formItem instanceof StaticTextItem)) {
							String realTitle = formItem.getAttribute("realTitle");
							if (realTitle != null && !realTitle.startsWith("<span")) {
								boolean required = (formItem.getRequired() != null ? formItem.getRequired() : false)
										|| (formItem.getAttributeAsBoolean("obbligatorio") != null ? formItem.getAttributeAsBoolean("obbligatorio") : false);
								int length = required ? (realTitle.length() + 5) : realTitle.length();
								int width ;
								if (formItem.getTitleStyle().toUpperCase().contains("BOLD") || formItem.getTitleStyle().toUpperCase().contains("FORMTITLE")) {
									width = required ? (int)((length * 7.5) + (length / 4)): (int)(length * 7.5);
								} else {
									width = required ? (int)((length * 7) + (length / 4)) : (int)(length * 7);
								}
								if (width > maxItemTitleWidth) {
									maxItemTitleWidth = width;
								}
							}
						}
					}
					i++;
				}
			}
		}
		//TODO 
		//il valore di maxItemTitleWidthToAlign per allineare le label dei campi invece di essere unico per tutte 
		//le sezioni (parametro DB MAX_TITLE_WIDTH_TO_ALIGN), potrebbe essere un attributo della sezione stessa 
		if (maxItemTitleWidth > getMaxItemTitleWidthToAlign()) {
			maxItemTitleWidth = getMaxItemTitleWidthToAlign();
		}
		for (FormItem formItem : fieldsToAlignTitle) {
			boolean required = (formItem.getRequired() != null ? formItem.getRequired() : false)
					|| (formItem.getAttributeAsBoolean("obbligatorio") != null ? formItem.getAttributeAsBoolean("obbligatorio") : false);
			if (formItem instanceof TitleItem) {
				((TitleItem) formItem).setTitle(setTitleAlign("", maxItemTitleWidth, required, afterChangedTitle));
			} else if (formItem instanceof StaticTextItem) {
				((StaticTextItem) formItem).setValue(setTitleAlign("", maxItemTitleWidth + 5, required, afterChangedTitle));
			} else {
				String realTitle = formItem.getAttribute("realTitle");
				if (realTitle != null && !realTitle.startsWith("<span")) {
					int length = required ? (realTitle.length() + 5) : realTitle.length();
					int width ;
					if (formItem.getTitleStyle().toUpperCase().contains("BOLD") || formItem.getTitleStyle().toUpperCase().contains("FORMTITLE")) {
						width = required ? (int)((length * 7.5) + (length / 4)): (int)(length * 7.5);
					} else {
						width = required ? (int)((length * 7) + (length / 4)) : (int)(length * 7);
					}
					if (width > maxItemTitleWidth) {
						formItem.setTitle(setTitleAlign(formItem.getTitle(), width, required, afterChangedTitle));
					} else {
						formItem.setTitle(setTitleAlign(formItem.getTitle(), maxItemTitleWidth, required, afterChangedTitle));
					}
				}
			}
		}
		markForRedraw();
	}

	public static int getMaxItemTitleWidthToAlign() {
		String maxItemTitleWidth = AurigaLayout.getParametroDB("MAX_TITLE_WIDTH_TO_ALIGN");
		if (maxItemTitleWidth != null && !"".equals(maxItemTitleWidth)) {
			return Integer.parseInt(maxItemTitleWidth);
		}
		return AttributiDinamiciDetail._MAX_TITLE_WIDTH_TO_ALIGN;
	}

	protected String setTitleAlign(String title, int width, boolean required, boolean afterChangedTitle) {
		if (isGestioneContenutiTabellaTrasp()){
			return title;
		}
		if (title != null && title.startsWith("<span")) {
			if(afterChangedTitle) {
				int start = title.indexOf(">") + 1;
				int end = title.indexOf("</span>", start);
				return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title.substring(start, end) + "</span>";
			} 
			return title;
		}
		return "<span style=\"width: " + (required ? width : width + 1) + "px; display: inline-block;\">" + title + "</span>";
	}

	private RegExpValidator buildRegExpValidator(String expression) {
		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator.setExpression(expression);
		regExpValidator.setErrorMessage("Valore non valido");
		return regExpValidator;
	}
	
	protected ExtendedDateItem buildDateItem(Record dett) {
		ExtendedDateItem item = new ExtendedDateItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setColSpan(1);
		return item;
	}

	protected ExtendedDateTimeItem buildDateTimeItem(Record dett) {
		ExtendedDateTimeItem item = new ExtendedDateTimeItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setColSpan(1);
		return item;
	}

	protected ExtendedTextItem buildTextItem(Record dett) {
		ExtendedTextItem item = new ExtendedTextItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setColSpan(1);
		if (dett.getAttribute("numMaxCaratteri") != null && !"".equals(dett.getAttribute("numMaxCaratteri"))) {
			item.setLength(new Integer(dett.getAttribute("numMaxCaratteri")));
		}
		if (dett.getAttribute("textCase") != null && !"".equals(dett.getAttribute("textCase"))) {
			if (dett.getAttribute("textCase").equalsIgnoreCase("UPPER")) {
				item.setCharacterCasing(CharacterCasing.UPPER);
			} else if (dett.getAttribute("textCase").equalsIgnoreCase("LOWER")) {
				item.setCharacterCasing(CharacterCasing.LOWER);
			}
		}
		return item;
	}

	protected ExtendedTextAreaItem buildTextAreaItem(Record dett/* , int numCols */) {
		ExtendedTextAreaItem item = new ExtendedTextAreaItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setColSpan(1);
		if (dett.getAttribute("altezza") != null && !"".equals(dett.getAttribute("altezza"))) {
			item.setHeight(new Integer(dett.getAttribute("altezza")) * 20);
		}
		if (dett.getAttribute("numMaxCaratteri") != null && !"".equals(dett.getAttribute("numMaxCaratteri"))) {
			item.setLength(new Integer(dett.getAttribute("numMaxCaratteri")));
		}
		return item;
	}

	protected CheckboxItem buildCheckItem(Record dett) {
		CheckboxItem item = new CheckboxItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setColSpan(1);
		item.setShowTitle(true);
		item.setTitleOrientation(TitleOrientation.RIGHT);
		item.setTitleVAlign(VerticalAlignment.BOTTOM);
		return item;
	}

	protected ExtendedNumericItem buildIntegerItem(Record dett) {
		ExtendedNumericItem item = new ExtendedNumericItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setColSpan(1);
		item.setKeyPressFilter("[0-9]");
		if (dett.getAttribute("numMaxCaratteri") != null && !"".equals(dett.getAttribute("numMaxCaratteri"))) {
			item.setLength(new Integer(dett.getAttribute("numMaxCaratteri")));
		}
		return item;
	}
	
	private CustomValidator buildRequiredCheckValidator() {
		CustomValidator requiredCheckValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				return value != null ? (Boolean) value : false;				
			}
		};
		requiredCheckValidator.setErrorMessage("Obbligatorio spuntare la casella");
		return requiredCheckValidator;
	}

	private RegExpValidator buildFloatPrecisionValidator(Integer numCifreDecimali) {
		RegExpValidator precisionValidator = new RegExpValidator();
		if (numCifreDecimali.intValue() > 0) {
			precisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1," + numCifreDecimali + "})?)$");
			precisionValidator.setErrorMessage("Valore non valido o superato il limite di " + numCifreDecimali + " cifre decimali");
		} else {
			precisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*)$");
			precisionValidator.setErrorMessage("Valore non valido");
		}
		return precisionValidator;
	}

	private CustomValidator buildFloatLengthValidator(final Integer numMaxCaratteri) {
		CustomValidator lengthValidator = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (numMaxCaratteri.intValue() > 0) {
					if (value != null && !"".equals((String) value)) {
						String valore = (String) value;
						valore = valore.replaceAll("\\.", "");
						valore = valore.replaceAll(",", "");
						return valore.length() <= numMaxCaratteri.intValue();
					}
				}
				return true;
			}
		};
		lengthValidator.setErrorMessage("La lunghezza massima è di " + numMaxCaratteri + " cifre");
		return lengthValidator;
	}

	protected ExtendedNumericItem buildEuroItem(final Record dett) {
		final ExtendedNumericItem item = new ExtendedNumericItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setColSpan(1);
		item.setKeyPressFilter("[0-9.]");
		final Integer numCifreDecimali = (dett.getAttribute("numCifreDecimali") != null && !"".equals(dett.getAttribute("numCifreDecimali"))) ? new Integer(
				dett.getAttribute("numCifreDecimali")) : 0;
		if (numCifreDecimali.intValue() > 0) {
			item.setKeyPressFilter("[0-9.,]");
			item.addChangedBlurHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					((AttributoListaForm)item.getForm()).setValue(dett.getAttribute("nome"), getFormattedEuroValue((String) event.getValue(), numCifreDecimali));
				}
			});
		}
		Validator precisionValidator = buildFloatPrecisionValidator(numCifreDecimali);
		Integer numMaxCaratteri = (dett.getAttribute("numMaxCaratteri") != null && !"".equals(dett.getAttribute("numMaxCaratteri"))) ? new Integer(
				dett.getAttribute("numMaxCaratteri")) : 0;
		Validator lengthValidator = buildFloatLengthValidator(numMaxCaratteri);
		item.setValidators(precisionValidator, lengthValidator);		
		return item;
	}
	
	private	String getFormattedEuroValue(String value, Integer numCifreDecimali) {
		if(value != null && !"".equals(value)) {
			try {
				String pattern = "#,##0";
				if(numCifreDecimali.intValue() > 0) {
					pattern += ".";
					for(int i = 0; i < numCifreDecimali.intValue(); i++) {
						pattern += "0";
					}
				}
				String groupingSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator();
				String decimalSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator();			
				double val = new Double(NumberFormat.getFormat(pattern).parse(value)).doubleValue();			
				NumberFormat format = NumberFormat.getFormat(pattern);
				return format.format(val).replace(groupingSeparator, ".").replace(decimalSeparator, ",");
			} catch(Exception e) {}
		}
		return value;		
	}

	protected ExtendedNumericItem buildDecimalItem(final Record dett) {
		final ExtendedNumericItem item = new ExtendedNumericItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setColSpan(1);
		item.setKeyPressFilter("[0-9.]");
		final Integer numCifreDecimali = (dett.getAttribute("numCifreDecimali") != null && !"".equals(dett.getAttribute("numCifreDecimali"))) ? new Integer(
				dett.getAttribute("numCifreDecimali")) : 0;
		if (numCifreDecimali.intValue() > 0) {
			item.setKeyPressFilter("[0-9.,]");
			item.addChangedBlurHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					((AttributoListaForm)item.getForm()).setValue(dett.getAttribute("nome"), getFormattedDecimalValue((String) event.getValue(), numCifreDecimali));
				}
			});
		}
		Validator precisionValidator = buildFloatPrecisionValidator(numCifreDecimali);
		Integer numMaxCaratteri = (dett.getAttribute("numMaxCaratteri") != null && !"".equals(dett.getAttribute("numMaxCaratteri"))) ? new Integer(
				dett.getAttribute("numMaxCaratteri")) : 0;
		Validator lengthValidator = buildFloatLengthValidator(numMaxCaratteri);
		item.setValidators(precisionValidator, lengthValidator);		
		return item;
	}
	
	private	String getFormattedDecimalValue(String value, Integer numCifreDecimali) {
		if(value != null && !"".equals(value)) {
			try {
				String pattern = "#,##0";
				if(numCifreDecimali.intValue() > 0) {
					pattern += ".";
					for(int i = 0; i < numCifreDecimali.intValue(); i++) {
						pattern += "0";
					}
				}
				String groupingSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().groupingSeparator();
				String decimalSeparator = LocaleInfo.getCurrentLocale().getNumberConstants().decimalSeparator();			
				double val = new Double(NumberFormat.getFormat(pattern).parse(value)).doubleValue();			
				NumberFormat format = NumberFormat.getFormat(pattern);
				return format.format(val).replace(groupingSeparator, ".").replace(decimalSeparator, ",");
			} catch(Exception e) {}
		}
		return value;		
	}

	protected SelectItem buildComboBoxItem(Record dett) {
		SelectItem item = new SelectItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setClearable(true);
		GWTRestDataSource loadComboDS = new GWTRestDataSource("LoadComboAttributoDinamicoDataSource", "key", FieldType.TEXT);
		loadComboDS.addParam("nomeCombo", dett.getAttribute("nome"));
		item.setOptionDataSource(loadComboDS);
		item.setDisplayField("value");
		item.setValueField("key");
		item.setColSpan(1);
		return item;
	}

	protected RadioGroupItem buildRadioItem(Record dett) {
		RadioGroupItem item = new RadioGroupItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setValueMap(new StringSplitterClient(dett.getAttribute("valueMap"), "|*|").getTokens());
		item.setVertical(false);
		item.setColSpan(1);
		item.setStartRow(true);
		item.setEndRow(true);
		return item;
	}

	protected DocumentItem buildDocumentItem(Record dett) {
		DocumentItem item = new DocumentItem();
		item.setName(dett.getAttribute("nome"));
		item.setTitle(dett.getAttribute("label"));
		item.setColSpan(1);
		return item;
	}
	
	protected CKEditorItem buildCKEditorItem(final Record dett, int numCols) {
		int length = dett.getAttribute("length") != null && !"".equalsIgnoreCase(dett.getAttribute("length")) ? Integer.parseInt(dett.getAttribute("length")) : -1;
		int altezza = dett.getAttribute("altezza") != null && !"".equalsIgnoreCase(dett.getAttribute("altezza")) ? Integer.parseInt(dett.getAttribute("altezza")) : 5;
//		int larghezza = dett.getAttribute("larghezza") != null && !"".equalsIgnoreCase(dett.getAttribute("larghezza")) ? Integer.parseInt(dett.getAttribute("larghezza")) : 800;
		String defaultValue = dett.getAttribute("defaultValue") != null && !"".equalsIgnoreCase(dett.getAttribute("defaultValue")) ? dett.getAttribute("defaultValue") : "";;
		String tipoEditor = dett.getAttribute("sottotipo") != null && !"".equalsIgnoreCase(dett.getAttribute("sottotipo")) ? dett.getAttribute("sottotipo") : "STANDARD";
		CKEditorItem item = new CKEditorItem(dett.getAttribute("nome"), length, tipoEditor, altezza, -1, defaultValue) {
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				if(!isGestioneContenutiTabellaTrasp()) {
					if(dett.getAttribute("label") != null && !"".equals(dett.getAttribute("label"))) {
						lVLayout.setWidth100();
						lVLayout.setPadding(11);
						lVLayout.setMargin(4);
						lVLayout.setIsGroup(true);
						lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
						if (dett.getAttribute("obbligatorio") != null && "1".equals(dett.getAttribute("obbligatorio"))) {
							lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(dett.getAttribute("label")) + "</span>");
						} else {
							lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + dett.getAttribute("label") + "</span>");
						}	
					}
				}
				return lVLayout;
			}
		};
		item.setColSpan(numCols);
		if(isGestioneContenutiTabellaTrasp()) {
			item.setTitle(dett.getAttribute("label"));
			if (item.getTitle() != null && !"".equals(item.getTitle())) {
				item.setShowTitle(true);		
			}	
		}
		return item;
	}
	
	protected DocumentListItem buildDocumentListItem(final Record dett) {
		DocumentListItem item = new DocumentListItem() {
			
			@Override
			public boolean showAltreOpInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showAltreOpInDocumentListItem"));
			}
			
			@Override
			public boolean showUploadItemInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showUploadItemInDocumentListItem"));
			}
			@Override
			public boolean showPreviewButtonInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showPreviewButtonInDocumentListItem"));
			}
			
			@Override
			public boolean showPreviewEditButtonInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showPreviewEditButtonInDocumentListItem"));
			}
			
			@Override
			public boolean canBeEditedByAppletInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("canBeEditedByAppletInDocumentListItem"));
			}
			
			@Override
			public boolean showEditButtonInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showEditButtonInDocumentListItem"));
			}			
			
			@Override
			public boolean showGeneraDaModelloButtonInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showGeneraDaModelloButtonInDocumentListItem"));
			}
			
			@Override
			public boolean showVisualizzaVersioniMenuItemInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showVisualizzaVersioniMenuItemInDocumentListItem"));
			}
			
			@Override
			public boolean showDownloadMenuItemInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showDownloadMenuItemInDocumentListItem"));
			}
			
			@Override
			public boolean showDownloadButtomOutsideAltreOpMenuInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showDownloadButtomOutsideAltreOpMenuInDocumentListItem"));
			}
			
			@Override
			public boolean showAcquisisciDaScannerMenuItemInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showAcquisisciDaScannerMenuItemInDocumentListItem"));
			}
			
			@Override
			public boolean showFirmaMenuItemInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showFirmaMenuItemInDocumentListItem"));
			}
			
			@Override
			public boolean showTimbraBarcodeMenuItemsInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showTimbraBarcodeMenuItemsInDocumentListItem"));
			}			
			
			@Override
			public boolean hideTimbraMenuItemsInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("hideTimbraMenuItemsInDocumentListItem"));
			}
			
			@Override
			public boolean showEliminaMenuItemInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showEliminaMenuItemInDocumentListItem"));
			}
			
			@Override
			public boolean showFileFirmatoDigButtonInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showFileFirmatoDigButtonInDocumentListItem"));
			}
			
			@Override
			public boolean isAttivaTimbraturaFilePostRegInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("isAttivaTimbraturaFilePostRegInDocumentListItem"));
			}
			
			@Override
			public boolean showFlgSostituisciVerPrecItemInDocumentListItem() {
				return "1".equalsIgnoreCase(dett.getAttribute("showFlgSostituisciVerPrecItemInDocumentListItem"));
			}
			
			@Override
			public boolean hideNewButtonDocumentList() {
				return "1".equalsIgnoreCase(dett.getAttribute("hideNewButtonInDocumentList"));
			}
			
			@Override
			public boolean showImportaFileArchivioButtonDocumentList() {
				return "1".equalsIgnoreCase(dett.getAttribute("showImportaFileArchivioButtonInDocumentList"));
			}
			
			@Override
			public boolean showUploadFileButtonInDocumentList() {
				return "1".equalsIgnoreCase(dett.getAttribute("showUploadFileButtonInDocumentList"));
			}
			
		};
		item.setName(dett.getAttribute("nome"));
		item.setTitle(dett.getAttribute("label"));
		return item;
	}

	public FormItem buildCustomItem(Record dett, CallbackGenericFunction callback) {
		String nome = dett.getAttribute("nome");
		if (callback != null) {
			FormItem lFormItem = callback.manageClick(nome);
			return lFormItem;
		}
		return new HiddenItem(nome);
	}
	
	@Override
	public Record getFormValuesAsRecord() {
		Record record = super.getFormValuesAsRecord();
		for (DynamicForm form : getForm()) {
			for (FormItem item : form.getFields()) {
				if (item instanceof CKEditorItem) {
					record.setAttribute(item.getName(), item.getValue());
				}
			}
		}
		return record;
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		for (DynamicForm form : getForm()) {
			for (FormItem item : form.getFields()) {
				if (item != null && (item instanceof CKEditorItem)) {
					item.setValue(record.getAttribute(item.getName()));
				}
			}
		}
	}
	
	public void setCanEdit(Boolean canEdit) {
		setEditing(canEdit);
		for (DynamicForm form : getForm()) {
			for (FormItem item : form.getFields()) {
				Boolean modificabile = (item.getAttributeAsBoolean("modificabile") != null && item.getAttributeAsBoolean("modificabile")) ? canEdit : false;
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					item.setCanEdit(modificabile);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(modificabile);
					item.redraw();
				}
			}
			form.markForRedraw();
		}
	}

	public String getWidthItem(FormItem formItem, Record dett) {
		
		String widthOut = "";
		
		if(formItem instanceof CKEditorItem) {
			widthOut = "100%";
		} else if (dett.getAttribute("larghezza") != null && !"".equals(dett.getAttribute("larghezza"))) {
			widthOut = dett.getAttribute("larghezza");
		} else {
			widthOut = "*";
		}
		
		if (isGestioneContenutiTabellaTrasp()){
			String realTitle = formItem.getTitle();
			if (realTitle != null) {
				if(formItem instanceof CKEditorItem) {
					widthOut = "100%";
				}				
				else if ( (formItem instanceof ExtendedNumericItem)  || 
						  (formItem instanceof ExtendedDateItem)     ||
						  (formItem instanceof ExtendedDateTimeItem) ||
						  (formItem instanceof DateItem)             ||
						  (formItem instanceof DateTimeItem)
						) { 
					widthOut = "200";
				}
				else {
					widthOut = String.valueOf(AttributiDinamiciDetail._MAX_ITEM_WIDTH_TO_ALIGN);
				}
			}			
		}
		return widthOut;
	}

	public boolean isGestioneContenutiTabellaTrasp() {
		return ((AttributoListaItem) getItem()).isGestioneContenutiTabellaTrasp();
	}

	
	protected SelectItem buildComboBoxValoriSceltaItem(Record dett, String valoriSceltaIn) {
		SelectItem item = new SelectItem(dett.getAttribute("nome"), dett.getAttribute("label"));
		item.setPickListWidth(Integer.parseInt(getWidthItem(item, dett)));
		item.setClearable(true);
		GWTRestDataSource loadComboDS = new GWTRestDataSource("LoadComboValoriSceltaDataSource", "key", FieldType.TEXT);
		loadComboDS.addParam("valoriScelta", valoriSceltaIn);
		item.setOptionDataSource(loadComboDS);
		item.setDisplayField("value");
		item.setValueField("key");
		item.setColSpan(1);
		return item;
	}
	
	public boolean existValoriScelta(Record dett) {
		String valoriScelta = getValoriScelta(dett);
		boolean ret =  (valoriScelta!=null && !valoriScelta.equalsIgnoreCase("")) ? true : false;
		return ret;
	}
	
	public String getValoriScelta(Record dett) {
		String valoriScelta = dett.getAttribute("valoriAmmessi");
		return valoriScelta;
	}
}