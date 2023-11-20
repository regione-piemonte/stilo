/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.i18n.client.NumberFormat;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.util.DateUtil;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
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
import com.smartgwt.client.widgets.tab.TabSet;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.editor.CKEditorItem;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.DetailSection;
import it.eng.utility.ui.module.layout.client.common.file.PrettyFileUploadItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
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

public class AttributiDinamiciDetail extends CustomDetail {

	public static final int _MAX_TITLE_WIDTH_TO_ALIGN = 250;
	public static final int _MAX_ITEM_WIDTH_TO_ALIGN = 800;
	

	protected Map<String, String> params = new HashMap<String, String>();

	protected DynamicForm hiddenForm;
	protected List<DetailSection> attributiDinamiciDetailSections;
	protected List<DynamicForm> attributiDinamiciForms;

	protected RecordList attributiAdd;
	protected Map mappaDettAttrLista;
	protected Map mappaValoriAttrLista;
	protected Map mappaVariazioniAttrLista;
	protected Map mappaDocumenti;

	protected List<FormItem> allItems;

	protected TabSet tabSet;
	protected String tabID;

	public AttributiDinamiciDetail(String nomeEntita, RecordList attributiAdd, Map mappaDettAttrLista, Map mappaValoriAttrLista, Map mappaVariazioniAttrLista,
			Map mappaDocumenti, String rowid, TabSet tabSet, String tabID) {
		this(nomeEntita, null, attributiAdd, mappaDettAttrLista, mappaValoriAttrLista, mappaVariazioniAttrLista, mappaDocumenti, rowid, tabSet, tabID);
	}

	public AttributiDinamiciDetail(String nomeEntita, ValuesManager valuesManager, RecordList attributiAdd, Map mappaDettAttrLista, Map mappaValoriAttrLista,
			Map mappaVariazioniAttrLista, Map mappaDocumenti, String rowid, TabSet tabSet, String tabID) {

		super(nomeEntita, valuesManager);

		this.attributiAdd = attributiAdd;
		this.mappaDettAttrLista = mappaDettAttrLista;
		this.mappaValoriAttrLista = mappaValoriAttrLista;
		this.mappaVariazioniAttrLista = mappaVariazioniAttrLista;
		this.mappaDocumenti = mappaDocumenti;

		this.allItems = new LinkedList<FormItem>();

		this.tabSet = tabSet;
		this.tabID = tabID;

		setWidth100();
		setHeight100();
		setAutoDraw(false);
		setCanHover(false);
		setPadding(5);

		setOverflow(Overflow.AUTO);

		attributiDinamiciDetailSections = new ArrayList<DetailSection>();
		attributiDinamiciForms = new ArrayList<DynamicForm>();

		if (attributiAdd != null && attributiAdd.getLength() > 0) {

			Map<Integer, String> mappaLabelRiquadri = new HashMap<Integer, String>();
			Set<Integer> riquadriObbligatori = new HashSet<Integer>();
			Map<Integer, RecordList> mappaAttributiRiquadri = new HashMap<Integer, RecordList>();

			for (int i = 0; i < attributiAdd.getLength(); i++) {

				Record attr = attributiAdd.get(i);

				if (mappaLabelRiquadri.get(new Integer(attr.getAttribute("numero"))) == null) {
					if (attr.getAttribute("labelRiquadro") != null && !"".equals(attr.getAttribute("labelRiquadro"))) {
						mappaLabelRiquadri.put(new Integer(attr.getAttribute("numero")), attr.getAttribute("labelRiquadro"));
					} else {
						mappaLabelRiquadri.put(new Integer(attr.getAttribute("numero")), attr.getAttribute("label"));
						attr.setAttribute("label", (String) null);
					}
				}
				
				if (attr.getAttribute("obbligatorio") != null && "1".equals(attr.getAttribute("obbligatorio"))) {
					riquadriObbligatori.add(new Integer(attr.getAttribute("numero")));
				}

				RecordList recordList = mappaAttributiRiquadri.get(new Integer(attr.getAttribute("numero")));
				if (recordList == null) {
					recordList = new RecordList();
				}
				recordList.add(attr);
				mappaAttributiRiquadri.put(new Integer(attr.getAttribute("numero")), recordList);

			}

			List<Integer> riquadri = new ArrayList<Integer>(mappaAttributiRiquadri.keySet());
			Collections.sort(riquadri);

			Record values = getOtherValues() != null ? getOtherValues() : new Record();

			for (Integer nroRiquadro : riquadri) {

				String labelRiquadro = mappaLabelRiquadri.get(nroRiquadro);

				Map<Integer, RecordList> mappaAttributiRiga = new HashMap<Integer, RecordList>();

				for (int i = 0; i < mappaAttributiRiquadri.get(nroRiquadro).getLength(); i++) {

					Record attr = mappaAttributiRiquadri.get(nroRiquadro).get(i);

					int riga = 0;
					if (attr.getAttribute("riga") != null && !"".equals(attr.getAttribute("riga"))) {
						riga = Integer.parseInt(attr.getAttribute("riga"));
					}

					RecordList recordList = mappaAttributiRiga.get(new Integer(riga));
					if (recordList == null) {
						recordList = new RecordList();
					}
					recordList.add(attr);
					mappaAttributiRiga.put(new Integer(riga), recordList);

				}

				List<Integer> righeRiquadro = new ArrayList<Integer>(mappaAttributiRiga.keySet());
				Collections.sort(righeRiquadro);

				List<DynamicForm> forms = new ArrayList<DynamicForm>();

				for (Integer rigaRiquadro : righeRiquadro) {

					RecordList attributiRiga = mappaAttributiRiga.get(rigaRiquadro);

					DynamicForm form = new DynamicForm();
					form.setValuesManager(vm);
					form.setWidth100();
					form.setWrapItemTitles(false);
					// form.setPadding(5);
					// form.setItemLayout(FormLayoutType.ABSOLUTE);
					// form.setCellBorder(1);

					int numCols = (attributiRiga.getLength() * 2) + 2;
					Object[] colWidths = new Object[numCols];
					for (int i = 0; i < numCols; i++) {
						if (i < (numCols - 2)) {
							colWidths[i] = 10;
						} else {
							colWidths[i] = "*";
						}
					}

					form.setNumCols(numCols);
					form.setColWidths(colWidths);

					List<FormItem> items = new ArrayList<FormItem>();

					for (int i = 0; i < attributiRiga.getLength(); i++) {

						Record attr = attributiRiga.get(i);

						FormItem item = null;

						if ("DATE".equals(attr.getAttribute("tipo"))) {
							item = buildDateItem(attr);
						} else if ("DATETIME".equals(attr.getAttribute("tipo"))) {
							item = buildDateTimeItem(attr);
						} else if ("TEXT".equals(attr.getAttribute("tipo"))) {
							item = buildTextItem(attr);
						} else if ("TEXT-AREA".equals(attr.getAttribute("tipo"))) {
							item = buildTextAreaItem(attr/* , form.getNumCols() */);
						} else if ("CHECK".equals(attr.getAttribute("tipo"))) {
							item = buildCheckItem(attr);
						} else if ("INTEGER".equals(attr.getAttribute("tipo"))) {
							item = buildIntegerItem(attr);
						} else if ("EURO".equals(attr.getAttribute("tipo"))) {
							item = buildEuroItem(attr);
						} else if ("DECIMAL".equals(attr.getAttribute("tipo"))) {
							item = buildDecimalItem(attr);
						} else if ("LISTA".equals(attr.getAttribute("tipo"))) {
							if (attr.getAttribute("sottotipo") != null && "GRID".equals(attr.getAttribute("sottotipo"))) {
								item = buildListaGridItem(attr, mappaDettAttrLista, mappaAttributiRiquadri.get(nroRiquadro), form.getNumCols());
							} else {
								item = buildListaReplicableItem(attr, mappaDettAttrLista, mappaAttributiRiquadri.get(nroRiquadro), form.getNumCols());
							}  
						} else if ("COMBO-BOX".equals(attr.getAttribute("tipo"))) {
							item = buildComboBoxItem(attr);
						} else if ("RADIO".equals(attr.getAttribute("tipo"))) {
							item = buildRadioItem(attr);
						} else if ("DOCUMENT".equals(attr.getAttribute("tipo"))) {
							item = buildDocumentItem(attr);
						} else if ("CKEDITOR".equals(attr.getAttribute("tipo"))) {
							item = buildCKEditorItem(attr, numCols);
						} else if ("CUSTOM".equals(attr.getAttribute("tipo"))) {
							item = buildCustomItem(attr);
							continue;
						}

						if (item != null) {
							
							item.setAttribute("labelOrig", attr.getAttribute("label"));

							if (attr.getAttribute("obbligatorio") != null && "1".equals(attr.getAttribute("obbligatorio"))) {
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
											manageOnChangedRequiredItem(event.getItem());
										}
									});
										
								} else if(item instanceof ExtendedNumericItem) {
									((ExtendedNumericItem)item).addChangedBlurHandler(new ChangedHandler() {
										
										@Override
										public void onChanged(ChangedEvent event) {
											manageOnChangedRequiredItem(event.getItem());
										}
									});										
								} else if(item instanceof ExtendedTextAreaItem) {
									((ExtendedTextAreaItem)item).addChangedBlurHandler(new ChangedHandler() {
										
										@Override
										public void onChanged(ChangedEvent event) {
											manageOnChangedRequiredItem(event.getItem());
										}
									});
								} else if(item instanceof ExtendedDateItem) {
									((ExtendedDateItem)item).addChangedHandler(new ChangedHandler() {
										
										@Override
										public void onChanged(ChangedEvent event) {
											manageOnChangedRequiredItem(event.getItem());
										}
									});										
								} else if(item instanceof ExtendedDateTimeItem) {
									((ExtendedDateTimeItem)item).addChangedHandler(new ChangedHandler() {
										
										@Override
										public void onChanged(ChangedEvent event) {
											manageOnChangedRequiredItem(event.getItem());
										}
									});										
								} else if(!(item instanceof AttributoListaItem) && !(item instanceof DocumentItem) && !(item instanceof ListaAttributoDinamicoListaItem)) {
									item.addChangedHandler(new ChangedHandler() {
										
										@Override
										public void onChanged(ChangedEvent event) {
											manageOnChangedRequiredItem(event.getItem());
										}
									});													
								}
							}

							if(item instanceof CKEditorItem) {
								item.setWidth("100%");
							} else if (attr.getAttribute("larghezza") != null && !"".equals(attr.getAttribute("larghezza"))) {
								item.setWidth(new Integer(attr.getAttribute("larghezza")));
							} else {
								item.setWidth("*");
							}

							if (attr.getAttribute("modificabile") != null) {
								if ("1".equals(attr.getAttribute("modificabile"))) {
									item.setAttribute("modificabile", true);
								} else if ("0".equals(attr.getAttribute("modificabile"))) {
									item.setAttribute("modificabile", false);
								}
							}

							if (attr.getAttribute("regularExpr") != null && !"".equals(attr.getAttribute("regularExpr"))) {
								// ATTENZIONE: questo sovrascrive tutti gli eventuali altri validatori dell'item
								RegExpValidator regExpValidator = buildRegExpValidator(attr.getAttribute("regularExpr"));
								item.setValidators(regExpValidator);
							}

							if ("LISTA".equals(attr.getAttribute("tipo"))) {
								if (mappaValoriAttrLista.get(attr.getAttribute("nome")) != null) {
									RecordList valoriLista = new RecordList();
									for (Map mapValori : (ArrayList<Map>) mappaValoriAttrLista.get(attr.getAttribute("nome"))) {
										Record valori = new Record(mapValori);
										for (Map dett : (ArrayList<Map>) mappaDettAttrLista.get(attr.getAttribute("nome"))) {
											if ("CHECK".equals(dett.get("tipo"))) {
												if (valori.getAttribute((String) dett.get("nome")) != null
														&& !"".equals(valori.getAttribute((String) dett.get("nome")))) {
													String valueStr = valori.getAttribute((String) dett.get("nome")) != null ? valori.getAttribute(
															(String) dett.get("nome")).trim() : "";
													Boolean value = Boolean.FALSE;
													if ("1".equals(valueStr) || "TRUE".equalsIgnoreCase(valueStr)) {
														value = Boolean.TRUE;
													}
													valori.setAttribute((String) dett.get("nome"), value);
												}
											} else if ("DOCUMENT".equals(dett.get("tipo"))) {
												String value = valori.getAttribute((String) dett.get("nome"));
												valori.setAttribute((String) dett.get("nome"), value != null && !"".equals(value) ? mappaDocumenti.get(value)
														: null);
											}
										}
										valoriLista.add(valori);
									}
									values.setAttribute(attr.getAttribute("nome"), valoriLista);
								}
							} else if ("DOCUMENT".equals(attr.getAttribute("tipo"))) {
								String value = attr.getAttribute("valore");
								values.setAttribute(attr.getAttribute("nome"), value != null && !"".equals(value) ? mappaDocumenti.get(value) : null);
							} else {
								if (attr.getAttribute("valore") != null && !"".equals(attr.getAttribute("valore"))) {
									if ("CHECK".equals(attr.getAttribute("tipo"))) {
										String valueStr = attr.getAttribute("valore") != null ? attr.getAttribute("valore").trim() : "";
										Boolean value = Boolean.FALSE;
										if ("1".equals(valueStr) || "TRUE".equalsIgnoreCase(valueStr)) {
											value = Boolean.TRUE;
										}
										values.setAttribute(attr.getAttribute("nome"), value);
									} else
										values.setAttribute(attr.getAttribute("nome"), attr.getAttribute("valore"));
								}

							}

							boolean showTitle = true;
							if (item.getTitle() == null || "".equals(item.getTitle())) {
								showTitle = false;
							}
							item.setShowTitle(showTitle);

							if (attr.getAttribute("flgVariazione") != null && "1".equals(attr.getAttribute("flgVariazione"))) {
								if (item instanceof AttributoListaItem) {
									AttributoListaItem lAttributoListaItem = (AttributoListaItem) item;
									lAttributoListaItem.mostraVariazione();
								} else if (item instanceof ListaAttributoDinamicoListaItem) {
									ListaAttributoDinamicoListaItem lListaAttributoDinamicoListaItem = (ListaAttributoDinamicoListaItem) item;
									lListaAttributoDinamicoListaItem.mostraVariazione();
								} else if (item instanceof DocumentItem) {
									DocumentItem lDocumentItem = (DocumentItem) item;
									lDocumentItem.mostraVariazione();
								} else {
									item.setCellStyle(it.eng.utility.Styles.formCellVariazione);
									item.redraw();
								}
							}

							// se è il primo item della riga
							if (items.size() == 0 && !(item instanceof AttributoListaItem) && !(item instanceof ListaAttributoDinamicoListaItem)) {
								// se non ha il titolo aggiungo uno spazio
								if ((item instanceof CheckboxItem) || !showTitle) {
									// TitleItem titleItem = new TitleItem("");
									// titleItem.setAttribute("titleSuffix", "");
									// titleItem.setAttribute("alignTitle", true);
									// titleItem.setTitleOrientation(TitleOrientation.TOP);
									// titleItem.setTitleAlign(Alignment.RIGHT);

									// StaticTextItem titleItem = new StaticTextItem() {
									//
									// @Override
									// public void setCanEdit(Boolean canEdit) {
									// super.setCanEdit(canEdit);
									// setTextBoxStyle(it.eng.utility.Styles.formTitle);
									// }
									// };
									// titleItem.setAttribute("alignTitle", true);
									// titleItem.setColSpan(1);
									// titleItem.setShowTitle(false);
									// titleItem.setAlign(Alignment.RIGHT);
									// titleItem.setTextBoxStyle(it.eng.utility.Styles.formTitle);
									// titleItem.setWrapTitle(false);
									// items.add(titleItem);
									// allItems.add(titleItem);
								}
								// altrimenti lo allineo
								else {
									item.setAttribute("alignTitle", true);
								}
							}
							
							customizeItem(item, this);

							items.add(item);
							allItems.add(item);
						}

					}

					form.setItems(items.toArray(new FormItem[items.size()]));

					form.setTabSet(tabSet);
					form.setTabID(tabID);
					
//					form.setCellBorder(1);

					forms.add(form);

					attributiDinamiciForms.add(form);

				}

				if (labelRiquadro.startsWith("*")) {
					labelRiquadro = labelRiquadro.substring(1);
				}

				DetailSection detailSection = new AttributiDinamiciDetailSection(labelRiquadro, true, true, riquadriObbligatori.contains(nroRiquadro), forms.toArray(new DynamicForm[forms.size()])) {
					
					@Override
					public boolean isOpenable() {
						return isOpenableSection(this);
					}

					@Override
					public String getOpenErrorMessage() {
						return getOpenErrorMessageSection(this);
					}
				};				

				int viewReplicableItemHeight = 0;
				for (DynamicForm form : forms) {
					for (FormItem item : form.getFields()) {
						if (item instanceof AttributoListaItem) {
							viewReplicableItemHeight += ((AttributoListaItem) item).getViewReplicableItemHeight();
						}
					}
				}
				if (viewReplicableItemHeight > 0) {
					detailSection.setViewReplicableItemHeight(viewReplicableItemHeight);
				}

				attributiDinamiciDetailSections.add(detailSection);
			}

			hiddenForm = new DynamicForm();
			hiddenForm.setWidth100();
			hiddenForm.setValuesManager(vm);
			hiddenForm.setVisibility(Visibility.HIDDEN);

			HiddenItem rowIdItem = new HiddenItem("rowId");
			HiddenItem[] otherHiddenItems = getOtherHiddenItems();
			HiddenItem[] hiddenItems = new HiddenItem[otherHiddenItems.length + 1];
			for (int i = 0; i < otherHiddenItems.length; i++) {
				hiddenItems[i] = otherHiddenItems[i];
			}
			hiddenItems[otherHiddenItems.length] = rowIdItem;

			hiddenForm.setItems(hiddenItems);

			addMember(hiddenForm);

			attributiDinamiciForms.add(hiddenForm);

			vm.editRecord(values);

			if (allItems != null) {
				for (FormItem item : allItems) {
					if (item instanceof AttributoListaItem) {
						if (mappaVariazioniAttrLista != null && mappaVariazioniAttrLista.get(item.getName()) != null) {
							((AttributoListaItem) item).mostraVariazioniColRighe((ArrayList<Map>) mappaVariazioniAttrLista.get(item.getName()));
						}
					} else if (item instanceof ListaAttributoDinamicoListaItem) {
						if (mappaVariazioniAttrLista != null && mappaVariazioniAttrLista.get(item.getName()) != null) {
							((ListaAttributoDinamicoListaItem) item).mostraVariazioniColRighe((ArrayList<Map>) mappaVariazioniAttrLista.get(item.getName()));
						}
					} else if (item instanceof CKEditorItem) {
						((CKEditorItem) item).setValue(values.getAttribute(item.getName()));							
					}
				}
			}

		} else {

			hiddenForm = new DynamicForm();
			hiddenForm.setWidth100();
			hiddenForm.setValuesManager(vm);
			hiddenForm.setVisibility(Visibility.HIDDEN);

			hiddenForm.setItems(getOtherHiddenItems());

			addMember(hiddenForm);

			Record values = getOtherValues() != null ? getOtherValues() : new Record();
			vm.editRecord(values);

		}

		addDetailMembers();

		VLayout spacer = new VLayout();
		spacer.setHeight100();
		spacer.setWidth100();
		addMember(spacer);

		setCanEdit(true);

		alignFormItemTitleWidth(false);
	}
	
	public void customizeItem(FormItem item, AttributiDinamiciDetail detail) {
		
	}

	@Override
	public Record getRecordToSave() {
		Record record = super.getRecordToSave();
		if (allItems != null) {
			for (FormItem item : allItems) {
				if (item instanceof AttributoListaItem) {
					record.setAttribute(item.getName(), ((AttributoListaItem) item).getValueAsRecordList());
				} else if (item instanceof ListaAttributoDinamicoListaItem) {
					record.setAttribute(item.getName(), ((ListaAttributoDinamicoListaItem) item).getValueAsRecordList());
				} else if (item instanceof CKEditorItem) {
					record.setAttribute(item.getName(), item.getValue());
				}
			}
		}
		return record;
	}

	public void alignFormItemTitleWidth(boolean afterChangedTitle) {
		for(DetailSection section : attributiDinamiciDetailSections) {
			int maxItemTitleWidth = 0;
			List<FormItem> fieldsToAlignTitle = new ArrayList<FormItem>();
			for (DynamicForm form : section.getForms()) {
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
										width = required ?(int)((length * 5.40)+ (length / 5)): (int)(length * 5.40);
									} else {
										width = required ?(length * 5) + (length / 5): (length * 5);
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
							width = required ?(int)((length * 5.40)+ (length / 5)): (int)(length * 5.40);
						} else {
							width = required ?(length * 5) + (length / 5): (length * 5);
						}
						if (width > maxItemTitleWidth) {
							formItem.setTitle(setTitleAlign(formItem.getTitle(), width, required, afterChangedTitle));
						} else {
							formItem.setTitle(setTitleAlign(formItem.getTitle(), maxItemTitleWidth, required, afterChangedTitle));
						}
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

	protected Record getOtherValues() {
		return null;
	}

	protected CallbackGenericFunction createCallback(RecordList dettAttrLista, RecordList recordList) {
		return null;
	}

	protected FormItem buildListaReplicableItem(Record attr, Map mappaDettAttrLista, RecordList recordList, int numCols) {
		RecordList dettAttrLista = new RecordList();
		try {
			for (Map dett : (ArrayList<Map>) mappaDettAttrLista.get(attr.getAttribute("nome"))) {
				dettAttrLista.add(new Record(dett));
			}
		} catch (Exception e) {
		}
		CallbackGenericFunction callback = createCallback(dettAttrLista, recordList);
		AttributoListaItem item = new AttributoListaItem(this, dettAttrLista, callback) {
			
			public void manageOnChangedRequiredItemInCanvas() {
				manageOnChangedRequiredItem(this);
			};
		};
		item.setName(attr.getAttribute("nome"));
		item.setTitle(attr.getAttribute("label"));
		if (item.getTitle() == null || "".equals(item.getTitle())) {
			item.setColSpan(numCols - 1);
		} else {
			item.setColSpan(numCols - 2);
		}
		if (attr.getAttribute("senzaAggiuntaRimozione") != null && "1".equals(attr.getAttribute("senzaAggiuntaRimozione"))) {
			item.setNotReplicable(true);	
		}
		if (attr.getAttribute("altezza") != null && !"".equals(attr.getAttribute("altezza"))) {
			item.setViewReplicableItemHeight(new Integer(attr.getAttribute("altezza")));
		}
		item.setStartRow(true);
		item.setEndRow(true);
		return item;
	}
	
	protected FormItem buildListaGridItem(final Record attr, Map mappaDettAttrLista, RecordList recordList, int numCols) {
		RecordList dettAttrLista = new RecordList();
		try {
			for (Map dett : (ArrayList<Map>) mappaDettAttrLista.get(attr.getAttribute("nome"))) {
				dettAttrLista.add(new Record(dett));
			}
		} catch (Exception e) {
		}
		CallbackGenericFunction callback = createCallback(dettAttrLista, recordList);
		ListaAttributoDinamicoListaItem item = new ListaAttributoDinamicoListaItem(this, attr, dettAttrLista, callback) {
			
			public void manageOnChangedRequiredItemInCanvas() {
				manageOnChangedRequiredItem(this);
			}
			
			@Override
			public void onClickImportXlsButton(final String uriFileImportExcel, final String mimetype) {				 	
			}
			
			@Override
			public boolean isShowNewButton() {
				if(attr.getAttribute("senzaAggiuntaRimozione") != null && "1".equals(attr.getAttribute("senzaAggiuntaRimozione"))) {
					return false;
				}
				return super.isShowNewButton();
			}
			
			@Override
			public boolean isShowDeleteButton() {
				if(attr.getAttribute("senzaAggiuntaRimozione") != null && "1".equals(attr.getAttribute("senzaAggiuntaRimozione"))) {
					return false;
				}
				return super.isShowDeleteButton();
			}

			@Override
			public boolean isGrigliaEditabile() {
				return true;
			};
		};
		item.setName(attr.getAttribute("nome"));
		item.setTitle(attr.getAttribute("label"));
		if (item.getTitle() == null || "".equals(item.getTitle())) {
			item.setColSpan(numCols);
		} else {
			item.setColSpan(numCols - 1);
		}		
		if (attr.getAttribute("altezza") != null && !"".equals(attr.getAttribute("altezza"))) {
			item.setHeight(new Integer(attr.getAttribute("altezza")));
		} else {
			item.setHeight(245);
		}		
		item.setStartRow(true);
		item.setEndRow(true);
		return item;
	}

	public void addDetailMembers() {
		for (DetailSection detailSection : attributiDinamiciDetailSections) {
			addMember(detailSection);
		}
	}

	public HiddenItem[] getOtherHiddenItems() {
		return new HiddenItem[] {};
	}

	private RegExpValidator buildRegExpValidator(String expression) {
		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator.setExpression(expression);
		regExpValidator.setErrorMessage("Valore non valido");
		return regExpValidator;
	}

	protected ExtendedDateItem buildDateItem(Record attr) {
		ExtendedDateItem item = new ExtendedDateItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
		return item;
	}

	protected ExtendedDateTimeItem buildDateTimeItem(Record attr) {
		ExtendedDateTimeItem item = new ExtendedDateTimeItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
		return item;
	}

	protected ExtendedTextItem buildTextItem(Record attr) {
		ExtendedTextItem item = new ExtendedTextItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
		if (attr.getAttribute("numMaxCaratteri") != null && !"".equals(attr.getAttribute("numMaxCaratteri"))) {
			item.setLength(new Integer(attr.getAttribute("numMaxCaratteri")));
		}
		if (attr.getAttribute("textCase") != null && !"".equals(attr.getAttribute("textCase"))) {
			if (attr.getAttribute("textCase").equalsIgnoreCase("UPPER")) {
				item.setCharacterCasing(CharacterCasing.UPPER);
			} else if (attr.getAttribute("textCase").equalsIgnoreCase("LOWER")) {
				item.setCharacterCasing(CharacterCasing.LOWER);
			}
		}
		return item;
	}

	protected ExtendedTextAreaItem buildTextAreaItem(Record attr/* , int numCols */) {
		ExtendedTextAreaItem item = new ExtendedTextAreaItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
		// item.setColSpan(numCols - 1);;
		// item.setStartRow(true);
		// item.setEndRow(true);
		if (attr.getAttribute("altezza") != null && !"".equals(attr.getAttribute("altezza"))) {
			item.setHeight(new Integer(attr.getAttribute("altezza")) * 20);
		}
		if (attr.getAttribute("numMaxCaratteri") != null && !"".equals(attr.getAttribute("numMaxCaratteri"))) {
			item.setLength(new Integer(attr.getAttribute("numMaxCaratteri")));
		}
		return item;
	}

	protected CheckboxItem buildCheckItem(Record attr) {
		CheckboxItem item = new CheckboxItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
		// item.setLabelAsTitle(true);
		item.setShowTitle(true);
		item.setTitleOrientation(TitleOrientation.RIGHT);
		item.setTitleVAlign(VerticalAlignment.BOTTOM);
		return item;
	}

	protected ExtendedNumericItem buildIntegerItem(Record attr) {
		ExtendedNumericItem item = new ExtendedNumericItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
		item.setKeyPressFilter("[0-9]");
		if (attr.getAttribute("numMaxCaratteri") != null && !"".equals(attr.getAttribute("numMaxCaratteri"))) {
			item.setLength(new Integer(attr.getAttribute("numMaxCaratteri")));
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
		// FloatPrecisionValidator precisionValidator = new FloatPrecisionValidator();
		// precisionValidator.setPrecision(numCifreDecimali);
		RegExpValidator precisionValidator = new RegExpValidator();
		if (numCifreDecimali.intValue() > 0) {
			// precisionValidator.setExpression("^([0-9]+(,[0-9]{1," + numCifreDecimali + "})?)$");
			precisionValidator.setExpression("^([0-9]{1,3}((\\.)?[0-9]{3})*(,[0-9]{1," + numCifreDecimali + "})?)$");
			precisionValidator.setErrorMessage("Valore non valido o superato il limite di " + numCifreDecimali + " cifre decimali");
		} else {
			// precisionValidator.setExpression("^([0-9]+)$");
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

	protected ExtendedNumericItem buildEuroItem(Record attr) {
		final ExtendedNumericItem item = new ExtendedNumericItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
		item.setKeyPressFilter("[0-9.]");
		final Integer numCifreDecimali = (attr.getAttribute("numCifreDecimali") != null && !"".equals(attr.getAttribute("numCifreDecimali"))) ? new Integer(
				attr.getAttribute("numCifreDecimali")) : 0;
		if (numCifreDecimali.intValue() > 0) {
			item.setKeyPressFilter("[0-9.,]");
			item.addChangedBlurHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					item.setValue(getFormattedEuroValue((String) event.getValue(), numCifreDecimali));
				}
			});
		}
		Validator precisionValidator = buildFloatPrecisionValidator(numCifreDecimali);
		Integer numMaxCaratteri = (attr.getAttribute("numMaxCaratteri") != null && !"".equals(attr.getAttribute("numMaxCaratteri"))) ? new Integer(
				attr.getAttribute("numMaxCaratteri")) : 0;
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

	protected ExtendedNumericItem buildDecimalItem(Record attr) {
		final ExtendedNumericItem item = new ExtendedNumericItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setColSpan(1);
		item.setKeyPressFilter("[0-9.]");
		final Integer numCifreDecimali = (attr.getAttribute("numCifreDecimali") != null && !"".equals(attr.getAttribute("numCifreDecimali"))) ? new Integer(
				attr.getAttribute("numCifreDecimali")) : 0;
		if (numCifreDecimali.intValue() > 0) {
			item.setKeyPressFilter("[0-9.,]");
			item.addChangedBlurHandler(new ChangedHandler() {
				
				@Override
				public void onChanged(ChangedEvent event) {
					item.setValue(getFormattedDecimalValue((String) event.getValue(), numCifreDecimali));
				}
			});
		}
		Validator precisionValidator = buildFloatPrecisionValidator(numCifreDecimali);
		Integer numMaxCaratteri = (attr.getAttribute("numMaxCaratteri") != null && !"".equals(attr.getAttribute("numMaxCaratteri"))) ? new Integer(
				attr.getAttribute("numMaxCaratteri")) : 0;
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

	protected SelectItem buildComboBoxItem(Record attr) {
		SelectItem item = new SelectItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setClearable(true);
		GWTRestDataSource loadComboDS = new GWTRestDataSource("LoadComboAttributoDinamicoDataSource", "key", FieldType.TEXT);
		loadComboDS.addParam("nomeCombo", attr.getAttribute("nome"));
		item.setOptionDataSource(loadComboDS);
		item.setDisplayField("value");
		item.setValueField("key");
		item.setColSpan(1);
		return item;
	}

	protected RadioGroupItem buildRadioItem(Record attr) {
		RadioGroupItem item = new RadioGroupItem(attr.getAttribute("nome"), attr.getAttribute("label"));
		item.setValueMap(new StringSplitterClient(attr.getAttribute("valueMap"), "|*|").getTokens());
		item.setVertical(false);
		item.setColSpan(1);
		item.setWrap(false);
		// item.setStartRow(true);
		// item.setEndRow(true);
		return item;
	}

	protected DocumentItem buildDocumentItem(Record attr) {
		DocumentItem item = new DocumentItem();
		item.setName(attr.getAttribute("nome"));
		item.setTitle(attr.getAttribute("label"));
		item.setColSpan(1);
		return item;
	}
	
	protected CKEditorItem buildCKEditorItem(final Record attr, int numCols) {
		int numMaxCaratteri = attr.getAttribute("numMaxCaratteri") != null && !"".equalsIgnoreCase(attr.getAttribute("numMaxCaratteri")) ? Integer.parseInt(attr.getAttribute("numMaxCaratteri")) : -1;
		int altezza = attr.getAttribute("altezza") != null && !"".equalsIgnoreCase(attr.getAttribute("altezza")) ? Integer.parseInt(attr.getAttribute("altezza")) : 5;
//		int larghezza = attr.getAttribute("larghezza") != null && !"".equalsIgnoreCase(attr.getAttribute("larghezza")) ? Integer.parseInt(attr.getAttribute("larghezza")) : 800;
		String tipoEditor = attr.getAttribute("sottotipo") != null && !"".equalsIgnoreCase(attr.getAttribute("sottotipo")) ? attr.getAttribute("sottotipo") : "STANDARD";
		String defaultValue = attr.getAttribute("defaultValue") != null && !"".equalsIgnoreCase(attr.getAttribute("defaultValue")) ? attr.getAttribute("defaultValue") : "";;
		CKEditorItem item = new CKEditorItem(attr.getAttribute("nome"), numMaxCaratteri, tipoEditor, altezza, -1, defaultValue) {
			
			@Override
			protected VLayout creaVLayout() {
				VLayout lVLayout = super.creaVLayout();
				if(attr.getAttribute("label") != null && !"".equals(attr.getAttribute("label"))) {
					lVLayout.setWidth100();
					lVLayout.setPadding(11);
					lVLayout.setMargin(4);
					lVLayout.setIsGroup(true);
					lVLayout.setStyleName(it.eng.utility.Styles.detailSection);
					if (attr.getAttribute("obbligatorio") != null && "1".equals(attr.getAttribute("obbligatorio"))) {
						lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + FrontendUtil.getRequiredFormItemTitle(attr.getAttribute("label")) + "</span>");
					} else {
						lVLayout.setGroupTitle("<span class=\"" + it.eng.utility.Styles.headerDetailSectionTitle + "\">" + attr.getAttribute("label") + "</span>");
					}	
				}
				return lVLayout;
			}
		};
		item.setColSpan(numCols);
//		item.setTitle(attr.getAttribute("label"));
//		item.setColSpan(numCols - 1);
//		if (item.getTitle() != null && !"".equals(item.getTitle())) {
//			item.setShowTitle(true);		
//		}				
//		if (attr.getAttribute("altezza") != null && !"".equals(attr.getAttribute("altezza"))) {
//			item.setHeight(new Integer(attr.getAttribute("altezza")) * 20);
//		}
		return item;
	}

	protected FormItem buildCustomItem(Record attr) {
		return new HiddenItem(attr.getAttribute("nome"));
	}

	@Override
	public void setCanEdit(boolean canEdit) {
		setCanEdit(new Boolean(canEdit));
	}

	public void setCanEdit(Boolean canEdit) {
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				Boolean modificabile = (item.getAttributeAsBoolean("modificabile") == null || item.getAttributeAsBoolean("modificabile")) ? canEdit : false;
				if (!(item instanceof HeaderItem) && !(item instanceof ImgButtonItem) && !(item instanceof TitleItem)) {
					item.setCanEdit(modificabile);
					item.redraw();
				}
				if (item instanceof ImgButtonItem || item instanceof PrettyFileUploadItem) {
					item.setCanEdit(modificabile);
					item.redraw();
				}
			}
		}
	}
	
	public Map<String, Object> getMappaValori(Record detailRecord) {
		Map<String, Object> attributiDinamici = new HashMap<String, Object>();
		if (attributiAdd != null) {
			for (int i = 0; i < attributiAdd.getLength(); i++) {
				Record attr = attributiAdd.get(i);
				if (!"CUSTOM".equals(attr.getAttribute("tipo"))) {
					if ("LISTA".equals(attr.getAttribute("tipo"))) {
						RecordList valoriLista = detailRecord.getAttributeAsRecordList(attr.getAttribute("nome"));
						List<Map> value = new ArrayList<Map>();
						for (int j = 0; j < valoriLista.getLength(); j++) {
							Record val = valoriLista.get(j);
							Map map = new HashMap();
							for (Map dett : (ArrayList<Map>) mappaDettAttrLista.get(attr.getAttribute("nome"))) {
								try {
									if (!"CUSTOM".equals(dett.get("tipo"))) {
										if ("DATE".equals(dett.get("tipo")) || "DATETIME".equals(dett.get("tipo"))) {
											map.put(dett.get("numero"), val.getAttributeAsDate((String) dett.get("nome")));
										} else if ("CHECK".equals(dett.get("tipo"))) {
											Boolean valueCheck =  val.getAttributeAsBoolean((String) dett.get("nome"));
											map.put(dett.get("numero"), valueCheck != null ? valueCheck : false);
										} else if ("DOCUMENT".equals(dett.get("tipo"))) {
											Record documentRecord = val.getAttributeAsRecord((String) dett.get("nome"));
											if (documentRecord == null)
												documentRecord = new Record();
											map.put(dett.get("numero"), documentRecord.toMap());
										} else if ("CKEDITOR".equals(dett.get("tipo"))) {
											String strval = ((CKEditorItem)vm.getItem((String) dett.get("nome"))).getValue();
											map.put(dett.get("numero"), strval != null ? strval.trim() : null);
										} else {
											String strval = val.getAttribute((String) dett.get("nome"));
											map.put(dett.get("numero"), strval != null ? strval.trim() : null);
										}
									}
								} catch (Exception e) {
									
								}
							}
							value.add(map);
						}
						attributiDinamici.put(attr.getAttribute("nome"), value);
					} else if ("DATE".equals(attr.getAttribute("tipo")) || "DATETIME".equals(attr.getAttribute("tipo"))) {
						attributiDinamici.put(attr.getAttribute("nome"), detailRecord.getAttributeAsDate(attr.getAttribute("nome")));
					} else if ("CHECK".equals(attr.getAttribute("tipo"))) {						
						Boolean valueCheck =  detailRecord.getAttributeAsBoolean(attr.getAttribute("nome"));
						attributiDinamici.put(attr.getAttribute("nome"), valueCheck != null ? valueCheck : false);
					} else if ("DOCUMENT".equals(attr.getAttribute("tipo"))) {
						Record documentRecord = detailRecord.getAttributeAsRecord(attr.getAttribute("nome"));
						if (documentRecord == null)
							documentRecord = new Record();
						attributiDinamici.put(attr.getAttribute("nome"), documentRecord.toMap());
					} else if ("CKEDITOR".equals(attr.getAttribute("tipo"))) {
						String strval = ((CKEditorItem)vm.getItem((String) attr.getAttribute("nome"))).getValue();
						attributiDinamici.put(attr.getAttribute("nome"), strval != null ? strval.trim() : null);
					} else {
						String strval = detailRecord.getAttribute(attr.getAttribute("nome"));
						attributiDinamici.put(attr.getAttribute("nome"), strval != null ? strval.trim() : null);
					}
				}
			}
		}
		return attributiDinamici;
	}

	public Map<String, String> getMappaTipiValori(Record detailRecord) {
		Map<String, String> tipiAttributiDinamici = new HashMap<String, String>();
		if (attributiAdd != null) {
			for (int i = 0; i < attributiAdd.getLength(); i++) {
				Record attr = attributiAdd.get(i);
				if (!"CUSTOM".equals(attr.getAttribute("tipo"))) {
					if ("LISTA".equals(attr.getAttribute("tipo"))) {
						for (Map dett : (ArrayList<Map>) mappaDettAttrLista.get(attr.getAttribute("nome"))) {
							if (!"CUSTOM".equals(dett.get("tipo"))) {
								if (!tipiAttributiDinamici.containsKey(attr.getAttribute("nome") + "." + dett.get("numero"))) {
									tipiAttributiDinamici.put(attr.getAttribute("nome") + "." + dett.get("numero"), (String) dett.get("tipo"));
								}
							}
						}
					}
					tipiAttributiDinamici.put(attr.getAttribute("nome"), attr.getAttribute("tipo"));
				}
			}
		}
		return tipiAttributiDinamici;

	}
	
	public Map<String, String> getMappaColonneListe(Record detailRecord) {
		Map<String, String> colonneListe = new HashMap<String, String>();
		if (attributiAdd != null) {
			for (int i = 0; i < attributiAdd.getLength(); i++) {
				Record attr = attributiAdd.get(i);
				if (!"CUSTOM".equals(attr.getAttribute("tipo"))) {
					if ("LISTA".equals(attr.getAttribute("tipo"))) {
						for (Map dett : (ArrayList<Map>) mappaDettAttrLista.get(attr.getAttribute("nome"))) {
							if (!"CUSTOM".equals(dett.get("tipo"))) {
								if (!colonneListe.containsKey(attr.getAttribute("nome") + "." + dett.get("numero"))) {
									colonneListe.put(attr.getAttribute("nome") + "." + dett.get("numero"), (String) dett.get("nome"));
								}
							}
						}
					}
				}
			}
		}
		return colonneListe;
	}
	
	@Override
	public void editNewRecord(Map initialValues) {
		super.editNewRecord(initialValues);
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item != null && (item instanceof CKEditorItem)) {
					item.setValue((String) initialValues.get(item.getName()));
				}
			}
		}
	}
	
	@Override
	public void editRecord(Record record) {
		super.editRecord(record);
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item != null && (item instanceof CKEditorItem)) {
					item.setValue(record.getAttribute(item.getName()));
				}
			}
		}
	}

	public void addParam(String key, String value) {
		if (params.containsKey(key)) {
			params.remove(key);
		}
		params.put(key, value);
	}

	public boolean isEditing() {
		return editing;
	}

	public RecordList getAttributiAdd() {
		return attributiAdd;
	}

	public void setAttributiAdd(RecordList attributiAdd) {
		this.attributiAdd = attributiAdd;
	}

	public Map getMappaDettAttrLista() {
		return mappaDettAttrLista;
	}

	public void setMappaDettAttrLista(Map mappaDettAttrLista) {
		this.mappaDettAttrLista = mappaDettAttrLista;
	}

	public Map getMappaValoriAttrLista() {
		return mappaValoriAttrLista;
	}

	public void setMappaValoriAttrLista(Map mappaValoriAttrLista) {
		this.mappaValoriAttrLista = mappaValoriAttrLista;
	}

	public Map getMappaDocumenti() {
		return mappaDocumenti;
	}

	public List<FormItem> getAllItems() {
		return allItems;
	}

	public List<DetailSection> getDetailSections() {
		return attributiDinamiciDetailSections;
	}

	public List<DynamicForm> getForms() {
		return attributiDinamiciForms;
	}

	public DetailSection getDetailSectionWithItem(String itemName) {
		DetailSection res = null;

		for (DetailSection section : attributiDinamiciDetailSections) {
			if (hasItem(section, itemName)) {
				res = section;
				break;
			}
		}

		return res;
	}

	public DynamicForm getDynamicFormWithItem(String itemName) {
		DynamicForm res = null;

		for (DetailSection section : attributiDinamiciDetailSections) {
			DynamicForm form = getFormItem(section, itemName);
			if (form != null) {
				res = form;
				break;
			}
		}

		return res;
	}

	private boolean hasItem(Canvas canvas, String itemName) {
		boolean res = false;
		if (canvas instanceof DynamicForm) {
			DynamicForm form = (DynamicForm) canvas;
			res = (form.getItem(itemName) != null);
		} else {
			for (Canvas child : canvas.getChildren()) {
				res = hasItem(child, itemName);
				if (res) {
					break;
				}
			}
		}
		return res;
	}

	private DynamicForm getFormItem(Canvas canvas, String itemName) {
		DynamicForm res = null;
		if (canvas instanceof DynamicForm) {
			DynamicForm form = (DynamicForm) canvas;
			if (form.getItem(itemName) != null) {
				res = form;
			}
		} else {
			for (Canvas child : canvas.getChildren()) {
				res = getFormItem(child, itemName);
				if (res != null) {
					break;
				}
			}
		}
		return res;
	}
	
	@Override
	public void clearTabErrors() {
		if (tabSet != null && tabID != null && !"".equals(tabID)) {
			tabSet.clearTabErrors(tabID);
		}
	}
	
	@Override
	public void showTabErrors() {
		if (tabSet != null && tabID != null && !"".equals(tabID)) {
			tabSet.showTabErrors(tabID);
		}
	}
	
	@Override
	public boolean customValidate() {		
		boolean valid = super.customValidate();
		if (allItems != null) {
			for (FormItem item : allItems) {
				if (item instanceof CKEditorItem) {
					valid = ((CKEditorItem) item).validate() && valid;							
				}
			}
		}		
		return valid;
	}
	
	public Boolean validateSenzaObbligatorieta() {
		vm.clearErrors(true);
		Boolean valid = true;
		for (DynamicForm form : vm.getMembers()) {
			for (FormItem item : form.getFields()) {
				if (item instanceof AttributoListaItem) {
					AttributoListaItem lAttributoListaItem = (AttributoListaItem) item;
					valid = lAttributoListaItem.validateSenzaObbligatorieta() && valid;
				} else if (item instanceof ListaAttributoDinamicoListaItem) {
					ListaAttributoDinamicoListaItem lListaAttributoDinamicoListaItem = (ListaAttributoDinamicoListaItem) item;
					valid = lListaAttributoDinamicoListaItem.validateSenzaObbligatorieta() && valid;
				} else if (item instanceof DocumentItem) {
					DocumentItem lDocumentItem = (DocumentItem) item;
					valid = lDocumentItem.validateSenzaObbligatorieta() && valid;
				} else {
					// solo se è valorizzato così escludo i controlli di obbligatorietà
					if (item.getValue() != null && !"".equals(String.valueOf(item.getValue()))) {
						valid = item.validate() && valid;
					}
				}
			}
		}
		if (!valid) {
			showTabErrors();
		}
		return valid;
	}
	
	public boolean isOpenableSection(DetailSection detailSection) {
		return true;
	}

	public String getOpenErrorMessageSection(DetailSection detailSection) {
		return null;
	}
	
	public void manageOnChangedRequiredItem(FormItem item) {
	
	}
	
	public class AttributiDinamiciDetailSection extends DetailSection {

		public AttributiDinamiciDetailSection(String pTitle, boolean pCanCollapse, boolean pShowOpen,
				boolean pIsRequired, DynamicForm[] forms) {
			super(pTitle, pCanCollapse, pShowOpen, pIsRequired, forms);
		}
		
	}

}
