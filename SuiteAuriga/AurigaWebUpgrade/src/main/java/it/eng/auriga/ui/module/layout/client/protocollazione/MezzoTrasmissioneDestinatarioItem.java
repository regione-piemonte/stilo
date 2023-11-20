/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.core.RefDataClass;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CanvasItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemInitHandler;
import com.smartgwt.client.widgets.form.fields.events.ShowValueEvent;
import com.smartgwt.client.widgets.form.fields.events.ShowValueHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;
import com.smartgwt.client.widgets.grid.ListGridField;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.client.datasource.SelectGWTRestDataSource;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItemWithDisplay;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public abstract class MezzoTrasmissioneDestinatarioItem extends CanvasItem {

	private DynamicForm lDynamicForm;

	private HiddenItem civicoItem;
	private HiddenItem internoItem;
	private HiddenItem scalaItem;
	private HiddenItem pianoItem;
	private HiddenItem capItem;
	private HiddenItem frazioneItem;
	private HiddenItem codIstatComuneItem;
	private HiddenItem comuneItem;
	private HiddenItem codIstatStatoItem;
	private HiddenItem statoItem;
	private HiddenItem provinciaItem;
	private HiddenItem zonaItem;
	private HiddenItem complementoIndirizzoItem;
	private HiddenItem tipoToponimoItem;
	private HiddenItem ciToponimoItem;
	private HiddenItem appendiciItem;
	private HiddenItem idIndirizzoItem;
	private HiddenItem indirizzoItem;

	private HiddenItem descrizioneMezzoTrasmissioneDestinatarioItem;

	private SelectItem mezzoTrasmissioneDestinatarioItem;
	private TextItem indirizzoPECDestinatarioItem;
	private TextItem indirizzoPEODestinatarioItem;
	private TextItem indirizzoMailDestinatarioItem;
	
	private HiddenItem descrizioneIndirizzoItem;
	private SelectItemWithDisplay indirizzoDestinatarioItem;

	// DateItem
	private DateItem dataRaccomandataDestinatarioItem;
	private DateItem dataNotificaDestinatarioItem;

	// TextItem
	private TextItem nroRaccomandataDestinatarioItem;
	private TextItem nroNotificaDestinatarioItem;
	
	private boolean editing;

	/**
	 * In questo metodo devono essere inserite le condizioni tali per cui il componente MezzoTrasmissioneDestinatarioItem deve essere visualizzato nel form in
	 * cui è inserito. Tale metodo è necessario perchè la condizione di visibilità deve essere propagata a tutti gli item annidati nel
	 * MezzoTrasmissioneDestinatarioItem, altrimenti quando questo componente viene nascosto tramite lo la showIfCondition si verificano dei problemi di
	 * allineamento (alcuni item al suo interno occupano comunque lo spazione nella GUI, anche se non visualizzati).
	 * 
	 * @return true se il MezzoTrasmissioneDestinatarioItem deve essere visulizzato nel form in cui è inserito
	 */
	public abstract boolean showMezzoTrasmissioneItem();
	
	public boolean isRequiredMezzoTrasmissioneItem() {
		return false;
	}
	
	public boolean isDestinatarioGruppo() {
		return false;
	}
	
	public boolean getSoloMezzoTrasmissionePEC() {
		return false;
	}
	
	public boolean showSelectIndirizzoItem() {
		return true;
	}	
	
	public void manageOnChangedMezzoTrasmissione() {
		
	}
	
	/**
	 * Crea un AttachmentItem. In fase di init, lo disegna e ne setta lo showHandler per gestire il setValue
	 * 
	 */
	public MezzoTrasmissioneDestinatarioItem() {
		setAutoDestroy(true); // per eliminare automaticamente il canvas quando elimino il canvasItem
		setInitHandler(new FormItemInitHandler() {

			public void onInit(FormItem item) {
				// Inizializza il componente
				// buildObject(item.getJsObj()).disegna((Record) item.getValue());
				// Per qualche motivo l'istruzione precedente genera un record errato, facendo così invece funziona
				Record initRecord = item.getValue() != null ? new Record(((Record) item.getValue()).toMap()) : new Record();
				MezzoTrasmissioneDestinatarioItem jsObj = buildObject(item.getJsObj());
				jsObj.disegna(initRecord);
				
				// Setto lo showValue (Gestiste il setValue
				addShowValueHandler(new ShowValueHandler() {

					@Override
					public void onShowValue(ShowValueEvent event) {
						setValue(event.getDataValueAsRecord());
					}
				});
			}
		});
		setShouldSaveValue(true);

		// Setto la showIfCondition con l'implementazione del metodo astratto showMezzoTrasmissioneItem
		setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem arg0, Object arg1, DynamicForm arg2) {
				return showMezzoTrasmissioneItem();
			}
		});
	}

	protected void disegna(Record lRecord) {

		lDynamicForm = new DynamicForm();
		lDynamicForm.setWidth("100%");
		lDynamicForm.setNumCols(10);
		lDynamicForm.setColWidths("1", "1", "1", "1", "1", "1", "1", "1", "*", "*");

		lDynamicForm.setOverflow(Overflow.VISIBLE);
		// lDynamicForm.setCellBorder(1);

		idIndirizzoItem = new HiddenItem("idIndirizzo");
		indirizzoItem = new HiddenItem("indirizzo");
		civicoItem = new HiddenItem("civico");
		internoItem = new HiddenItem("interno");
		scalaItem = new HiddenItem("scala");
		pianoItem = new HiddenItem("piano");
		capItem = new HiddenItem("cap");
		frazioneItem = new HiddenItem("frazione");
		codIstatComuneItem = new HiddenItem("codIstatComune");
		comuneItem = new HiddenItem("comune");
		codIstatStatoItem = new HiddenItem("codIstatStato");
		statoItem = new HiddenItem("stato");
		provinciaItem = new HiddenItem("provincia");
		zonaItem = new HiddenItem("zona");
		complementoIndirizzoItem = new HiddenItem("complementoIndirizzo");
		tipoToponimoItem = new HiddenItem("tipoToponimo");
		ciToponimoItem = new HiddenItem("ciToponimo");
		appendiciItem = new HiddenItem("appendici");

		descrizioneMezzoTrasmissioneDestinatarioItem = new HiddenItem("descrizioneMezzoTrasmissioneDestinatario");

		// Mezzi di trasmissione
		GWTRestDataSource mezziTrasmissioneDS = new GWTRestDataSource("LoadComboMezziTrasmissioneDataSource", "key", FieldType.TEXT);
		mezziTrasmissioneDS.extraparam.put("idRegistrazione", null);
		mezzoTrasmissioneDestinatarioItem = new SelectItem("mezzoTrasmissioneDestinatario", I18NUtil.getMessages().protocollazione_detail_mezzoTrasmissioneItem_title()) {
			
			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				descrizioneMezzoTrasmissioneDestinatarioItem.setValue(record.getAttribute("value"));
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					descrizioneMezzoTrasmissioneDestinatarioItem.setValue("");
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				descrizioneMezzoTrasmissioneDestinatarioItem.setValue("");
			}
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				if(getSoloMezzoTrasmissionePEC()) {
					super.setCanEdit(false);
				} else {
					super.setCanEdit(canEdit);
				}
			}
		};
		if(getSoloMezzoTrasmissionePEC()) {
			LinkedHashMap<String, String> mezzoTrasmissioneDestinatarioValueMap = new LinkedHashMap<String, String>();
			mezzoTrasmissioneDestinatarioValueMap.put("PEC", "PEC (Posta Elettronica Certificata)");
			mezzoTrasmissioneDestinatarioItem.setValueMap(mezzoTrasmissioneDestinatarioValueMap);
			mezzoTrasmissioneDestinatarioItem.setDefaultValue("PEC");
		}
		mezzoTrasmissioneDestinatarioItem.setOptionDataSource(mezziTrasmissioneDS);
		mezzoTrasmissioneDestinatarioItem.setAutoFetchData(false);
		mezzoTrasmissioneDestinatarioItem.setFetchMissingValues(false);
		mezzoTrasmissioneDestinatarioItem.setDisplayField("value");
		mezzoTrasmissioneDestinatarioItem.setValueField("key");
		mezzoTrasmissioneDestinatarioItem.setWrapTitle(false);
		mezzoTrasmissioneDestinatarioItem.setStartRow(true);
		mezzoTrasmissioneDestinatarioItem.setAllowEmptyValue(true);
		mezzoTrasmissioneDestinatarioItem.setClearable(false);
		mezzoTrasmissioneDestinatarioItem.setRedrawOnChange(true);
		mezzoTrasmissioneDestinatarioItem.setWidth(200);
		mezzoTrasmissioneDestinatarioItem.setColSpan(1);
		mezzoTrasmissioneDestinatarioItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				dataRaccomandataDestinatarioItem.setValue("");
				nroRaccomandataDestinatarioItem.setValue("");
				dataNotificaDestinatarioItem.setValue("");
				nroNotificaDestinatarioItem.setValue("");
				manageOnChangedMezzoTrasmissione();
			}
		});
		mezzoTrasmissioneDestinatarioItem.setAttribute("obbligatorio", isRequiredMezzoTrasmissioneItem());
		mezzoTrasmissioneDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showMezzoTrasmissioneItem() && isRequiredMezzoTrasmissioneItem();
			}
		}));
		mezzoTrasmissioneDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return showMezzoTrasmissioneItem();
			}
		});

		// Data raccomandata
		dataRaccomandataDestinatarioItem = new DateItem("dataRaccomandataDestinatario",
				I18NUtil.getMessages().protocollazione_detail_dataRaccomandataDestinatarioItem_title());
		dataRaccomandataDestinatarioItem.setWrapTitle(false);
		dataRaccomandataDestinatarioItem.setColSpan(1);
		dataRaccomandataDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isRaccomandata() && showMezzoTrasmissioneItem();
			}
		});

		// Numero raccomandata
		nroRaccomandataDestinatarioItem = new TextItem("nroRaccomandataDestinatario",
				I18NUtil.getMessages().protocollazione_detail_nroRaccomandataDestinatarioItem_title());
		nroRaccomandataDestinatarioItem.setWrapTitle(false);
		nroRaccomandataDestinatarioItem.setWidth(100);
		nroRaccomandataDestinatarioItem.setColSpan(1);
		nroRaccomandataDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isRaccomandata() && showMezzoTrasmissioneItem();
			}
		});

		// Data notifica al destinatario
		dataNotificaDestinatarioItem = new DateItem("dataNotificaDestinatario",
				I18NUtil.getMessages().protocollazione_detail_dataNotificaDestinatarioItem_title());
		dataNotificaDestinatarioItem.setWrapTitle(false);
		dataNotificaDestinatarioItem.setColSpan(1);
		dataNotificaDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isNotifica() && showMezzoTrasmissioneItem();
			}
		});

		// Numero notifica
		nroNotificaDestinatarioItem = new TextItem("nroNotificaDestinatario",
				I18NUtil.getMessages().protocollazione_detail_nroNotificaDestinatarioItem_title());
		nroNotificaDestinatarioItem.setWrapTitle(false);
		nroNotificaDestinatarioItem.setWidth(100);
		nroNotificaDestinatarioItem.setColSpan(1);
		nroNotificaDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return isNotifica() && showMezzoTrasmissioneItem();
			}
		});
		
		indirizzoPECDestinatarioItem = new TextItem("indirizzoPECDestinatario", "E-mail");
		indirizzoPECDestinatarioItem.setWrapTitle(false);
		indirizzoPECDestinatarioItem.setWidth(240);
		indirizzoPECDestinatarioItem.setColSpan(1);
		indirizzoPECDestinatarioItem.setAttribute("obbligatorio", isRequiredMezzoTrasmissioneItem());
		indirizzoPECDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isPEC() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo() && isRequiredMezzoTrasmissioneItem();
			}
		}), new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(isPEC() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo()) {
					if(value == null || "".equals((String) value)) return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					String indirizzoEmailPEC = (String) value;
					return regExp.test(indirizzoEmailPEC.trim());		
				}
				return true;
			}
		});
		indirizzoPECDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isPEC() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo();
			}
		});
		
		indirizzoPEODestinatarioItem = new TextItem("indirizzoPEODestinatario", "E-mail");
		indirizzoPEODestinatarioItem.setWrapTitle(false);
		indirizzoPEODestinatarioItem.setWidth(240);
		indirizzoPEODestinatarioItem.setColSpan(1);
		indirizzoPEODestinatarioItem.setAttribute("obbligatorio", isRequiredMezzoTrasmissioneItem());
		indirizzoPEODestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isPEO() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo() && isRequiredMezzoTrasmissioneItem();
			}
		}), new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(isPEO() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo()) {
					if(value == null || "".equals((String) value)) return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					String indirizzoEmailPEO = (String) value;
					return regExp.test(indirizzoEmailPEO.trim());		
				}
				return true;
			}
		});
		indirizzoPEODestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isPEO() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo();
			}
		});
		
		indirizzoMailDestinatarioItem = new TextItem("indirizzoMailDestinatario", "E-mail");
		indirizzoMailDestinatarioItem.setWrapTitle(false);
		indirizzoMailDestinatarioItem.setWidth(240);
		indirizzoMailDestinatarioItem.setColSpan(1);
		indirizzoMailDestinatarioItem.setAttribute("obbligatorio", isRequiredMezzoTrasmissioneItem());
		indirizzoMailDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return isEmail() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo() && isRequiredMezzoTrasmissioneItem();
			}
		}), new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if(isEmail() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo()) {
					if(value == null || "".equals((String) value)) return true;
					RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
					String indirizzoEmail = (String) value;
					return regExp.test(indirizzoEmail.trim());		
				}
				return true;
			}
		});
		indirizzoMailDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return isEmail() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo();
			}
		});
		
		descrizioneIndirizzoItem = new HiddenItem("descrizioneIndirizzo");
		
		// Indirizzo
		SelectGWTRestDataSource lGwtRestDataSource = new SelectGWTRestDataSource("LoadComboIndirizziDataSource", "idIndirizzo", FieldType.TEXT, new String[] { "indirizzoDisplay" }, true);
		indirizzoDestinatarioItem = new SelectItemWithDisplay("indirizzoDestinatario", I18NUtil.getMessages().protocollazione_detail_indirizzoDestinatarioItem_title(), lGwtRestDataSource) {

			@Override
			public void onOptionClick(Record record) {
				super.onOptionClick(record);
				descrizioneIndirizzoItem.setValue(record.getAttribute("indirizzoDisplay"));
			}

			@Override
			public void setValue(String value) {
				super.setValue(value);
				if (value == null || "".equals(value)) {
					descrizioneIndirizzoItem.setValue("");
				}
			}

			@Override
			protected void clearSelect() {
				super.clearSelect();
				descrizioneIndirizzoItem.setValue("");
			}
		};

		int i = 0;
		String[] campiVisibili = new String[] { "indirizzoDisplay" };
		String[] campiHidden = new String[] { "idIndirizzo" };
		String[] descrizioni = new String[] { "Indirizzo" };
		Object[] width = new Object[] { "*" };
		List<ListGridField> lList = new ArrayList<ListGridField>();
		for (String lString : campiVisibili) {
			ListGridField field = new ListGridField(lString, descrizioni[i]);
			if (width[i] instanceof String) {
				field.setWidth((String) width[i]);
			} else
				field.setWidth((Integer) width[i]);

			i++;
			lList.add(field);
		}
		for (String lString : campiHidden) {
			ListGridField field = new ListGridField(lString, lString);
			field.setHidden(true);
			lList.add(field);
		}

		indirizzoDestinatarioItem.setPickListFields(lList.toArray(new ListGridField[] {}));
		indirizzoDestinatarioItem.setFilterLocally(true);
		indirizzoDestinatarioItem.setValueField("idIndirizzo");
		indirizzoDestinatarioItem.setOptionDataSource(lGwtRestDataSource);
		indirizzoDestinatarioItem.setWidth(600);
		indirizzoDestinatarioItem.setRequired(false);
		indirizzoDestinatarioItem.setClearable(true);
		indirizzoDestinatarioItem.setShowIcons(true);
		indirizzoDestinatarioItem.setCachePickListResults(false);
		indirizzoDestinatarioItem.setDisplayField("indirizzoDisplay");
		indirizzoDestinatarioItem.setAllowEmptyValue(false);
		indirizzoDestinatarioItem.setWrapTitle(false);
		indirizzoDestinatarioItem.setFetchMissingValues(true);
		indirizzoDestinatarioItem.setShowTitle(true);
		indirizzoDestinatarioItem.setStartRow(false);
		indirizzoDestinatarioItem.setColSpan(7);
		indirizzoDestinatarioItem.setName("indirizzoDestinatario");
		indirizzoDestinatarioItem.setAutoFetchData(false);
		indirizzoDestinatarioItem.setAttribute("obbligatorio", isRequiredMezzoTrasmissioneItem());
		indirizzoDestinatarioItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				return showSelectIndirizzoItem() && isMezzoTrasmissioneValorizzato() && !isEmailPECPEO() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo() && isRequiredMezzoTrasmissioneItem();
			}
		}));
		indirizzoDestinatarioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				return showSelectIndirizzoItem() && isMezzoTrasmissioneValorizzato() && !isEmailPECPEO() && showMezzoTrasmissioneItem() && !isDestinatarioGruppo();				
			}
		});		
		indirizzoDestinatarioItem.addDataArrivedHandler(new DataArrivedHandler() {			
			@Override
			public void onDataArrived(DataArrivedEvent event) {
				
				RecordList data = event.getData();
				if(data != null && data.getLength() == 1){
					Record record = data.get(0);
					lDynamicForm.setValue("indirizzoDestinatario", record.getAttribute("idIndirizzo"));
					manageOnOptionClick("indirizzoDestinatario", record);
				}				
			};
		});

		setFormItems(
				mezzoTrasmissioneDestinatarioItem, 
				dataRaccomandataDestinatarioItem, nroRaccomandataDestinatarioItem, 
				dataNotificaDestinatarioItem, nroNotificaDestinatarioItem, 
				indirizzoPECDestinatarioItem, indirizzoPEODestinatarioItem, indirizzoMailDestinatarioItem, 
				indirizzoDestinatarioItem,
				// Hidden
				idIndirizzoItem, civicoItem, internoItem, scalaItem, pianoItem, capItem, frazioneItem, codIstatComuneItem, comuneItem, codIstatStatoItem,
				statoItem, provinciaItem, zonaItem, complementoIndirizzoItem, tipoToponimoItem, ciToponimoItem, appendiciItem, indirizzoItem,
				descrizioneMezzoTrasmissioneDestinatarioItem, descrizioneIndirizzoItem);

		setCanvas(lDynamicForm);
		setValue(lRecord);
	}

	private void manageOnOptionClick(String name, Record record) {
		if (name.equals("indirizzoDestinatario")) {
			String idIndirizzo = record.getAttributeAsString("idIndirizzo");
			String indirizzo = record.getAttributeAsString("indirizzo");
			String civico = record.getAttributeAsString("civico");
			String interno = record.getAttributeAsString("interno");
			String scala = record.getAttributeAsString("scala");
			String piano = record.getAttributeAsString("piano");
			String cap = record.getAttributeAsString("cap");
			String frazione = record.getAttributeAsString("frazione");
			String codIstatComune = record.getAttributeAsString("codIstatComune");
			String comune = record.getAttributeAsString("comune");
			String codIstatStato = record.getAttributeAsString("codIstatStato");
			String stato = record.getAttributeAsString("stato");
			String provincia = record.getAttributeAsString("provincia");
			String zona = record.getAttributeAsString("zona");
			String complementoIndirizzo = record.getAttributeAsString("complementoIndirizzo");
			String tipoToponimo = record.getAttributeAsString("tipoToponimo");
			String ciToponimo = null;
			String appendici = record.getAttributeAsString("appendici");

			lDynamicForm.setValue("idIndirizzo", idIndirizzo);
			lDynamicForm.setValue("indirizzo", indirizzo);
			lDynamicForm.setValue("civico", civico);
			lDynamicForm.setValue("interno", interno);
			lDynamicForm.setValue("scala", scala);
			lDynamicForm.setValue("piano", piano);
			lDynamicForm.setValue("cap", cap);
			lDynamicForm.setValue("frazione", frazione);
			lDynamicForm.setValue("codIstatComune", codIstatComune);
			lDynamicForm.setValue("comune", comune);
			lDynamicForm.setValue("codIstatStato", codIstatStato);
			lDynamicForm.setValue("stato", stato);
			lDynamicForm.setValue("provincia", provincia);
			lDynamicForm.setValue("zona", zona);
			lDynamicForm.setValue("complementoIndirizzo", complementoIndirizzo);
			lDynamicForm.setValue("tipoToponimo", tipoToponimo);
			lDynamicForm.setValue("ciToponimo", ciToponimo);
			lDynamicForm.setValue("appendici", appendici);
		}
	}

	public void refreshFilteredSelectIndirizzoDestinatario(String idSoggetto) {
		if (indirizzoDestinatarioItem != null) {
			indirizzoDestinatarioItem.clearValue();
			((SelectGWTRestDataSource) indirizzoDestinatarioItem.getOptionDataSource()).addParam("idSoggetto", (String) idSoggetto);
			indirizzoDestinatarioItem.fetchData();
		}
	}
	
	public void setIdSoggetto(String idSoggetto) {
		if (indirizzoDestinatarioItem != null) {
			((SelectGWTRestDataSource) indirizzoDestinatarioItem.getOptionDataSource()).addParam("idSoggetto", (String) idSoggetto);
		}
	}
	
	public void setIndirizzoPECDestinatario(String indirizzoPECDestinatario) {
		if (indirizzoPECDestinatarioItem != null) {
			lDynamicForm.setValue("indirizzoPECDestinatario" , getSingleValidIndirizzoMail(indirizzoPECDestinatario));
			storeValue(getValue());
		}
	}
	
	public void setIndirizzoPEODestinatario(String indirizzoPEODestinatario) {
		if (indirizzoPEODestinatarioItem != null) {
			lDynamicForm.setValue("indirizzoPEODestinatario" , getSingleValidIndirizzoMail(indirizzoPEODestinatario));
			storeValue(getValue());
		}
	}
	
	public void setIndirizzoMailDestinatario(String indirizzoMailDestinatario) {
		if (indirizzoMailDestinatarioItem != null) {
			lDynamicForm.setValue("indirizzoMailDestinatario" , getSingleValidIndirizzoMail(indirizzoMailDestinatario));
			storeValue(getValue());
		}
	}
	
	public String getSingleValidIndirizzoMail(String email) {
		if(email != null && !"".equals((String) email)) {
			RegExp regExp = RegExp.compile(RegExpUtility.indirizzoEmailRegExp());
			return regExp.test(email) ? email : null;
		}
		return null;
	}
	
	/**
	 * Serve per istanziare la classe tramite GWT
	 * 
	 * @param jsObj
	 */
	public MezzoTrasmissioneDestinatarioItem(JavaScriptObject jsObj) {
		super(jsObj);
	}

	/**
	 * Esegue il build quando l'oggetto non esiste. return AttachmentItem;
	 * 
	 * @param jsObj
	 * @return
	 */
	public MezzoTrasmissioneDestinatarioItem buildObject(JavaScriptObject jsObj) {		
		MezzoTrasmissioneDestinatarioItem lItem = getOrCreateRef(jsObj);
		return lItem;
	}

	/**
	 * Crea l'istanza o ne restituisce una a partire dall'oggetto Javascript
	 * 
	 * @param jsObj
	 * @return
	 */
	public static MezzoTrasmissioneDestinatarioItem getOrCreateRef(JavaScriptObject jsObj) {
		if (jsObj == null)
			return null;
		RefDataClass obj = RefDataClass.getRef(jsObj);
		if (obj != null) {
			obj.setJsObj(jsObj);
			return (MezzoTrasmissioneDestinatarioItem) obj;
		} else {
			return null;
		}
	}

	@Override
	public void setValue(Object value) {		
		Record lRecord = (Record) value;	
		// Inizializzo le combo
		if (mezzoTrasmissioneDestinatarioItem != null) {
			if (lRecord != null && ((lRecord.getAttribute("mezzoTrasmissioneDestinatario") != null  && !"".equals(lRecord.getAttributeAsString("mezzoTrasmissioneDestinatario"))) &&
				(lRecord.getAttribute("descrizioneMezzoTrasmissioneDestinatario") != null && !"".equals(lRecord.getAttributeAsString("descrizioneMezzoTrasmissioneDestinatario"))))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(lRecord.getAttribute("mezzoTrasmissioneDestinatario"), lRecord.getAttribute("descrizioneMezzoTrasmissioneDestinatario"));
				mezzoTrasmissioneDestinatarioItem.setValueMap(valueMap);
			}	
		}		
		if (indirizzoDestinatarioItem != null) {
			if (lRecord != null && ((lRecord.getAttribute("indirizzoDestinatario") != null && !"".equals(lRecord.getAttributeAsString("indirizzoDestinatario"))) &&
				(lRecord.getAttribute("descrizioneIndirizzo") != null && !"".equals(lRecord.getAttributeAsString("descrizioneIndirizzo"))))) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
				valueMap.put(lRecord.getAttribute("indirizzoDestinatario"), lRecord.getAttribute("descrizioneIndirizzo"));
				indirizzoDestinatarioItem.setValueMap(valueMap);
				manageOnOptionClick("indirizzoDestinatario", lRecord);
			}	
		}
		lDynamicForm.editRecord(lRecord != null ? lRecord : new Record());
		// Memorizzo il record
		storeValue(lRecord);	
		manageOnChangedMezzoTrasmissione();
	}

	@Override
	public Object getValue() {		
		return lDynamicForm != null ? lDynamicForm.getValuesAsRecord() : new Record();
	}

	@Override
	public void setWidth(int width) {		
		super.setWidth(width);
	}

	@Override
	public void setWidth(String width) {		
		super.setWidth(width);
	}

	public void setFormItems(FormItem... items) {
		for (FormItem item : items) {
			item.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					
					Record lRecord = new Record(lDynamicForm.getValues());
					lRecord.setAttribute(event.getItem().getName(), event.getValue());
					storeValue(lRecord);
				}
			});
		}
		lDynamicForm.setItems(items);
	}

	public DynamicForm getForm() {
		return lDynamicForm;
	}

	@Override
	public void setCanEdit(Boolean canEdit) {	
		editing = canEdit != null && canEdit;
		for (FormItem item : lDynamicForm.getFields()) {
			item.setCanEdit(canEdit);
		}
		// fileButtons.setCanEdit(canEdit);
	}
	
	public boolean isMezzoTrasmissioneValorizzato() {
		String mezzoTrasmissione = mezzoTrasmissioneDestinatarioItem != null ? mezzoTrasmissioneDestinatarioItem.getValueAsString() : null;		
		return mezzoTrasmissione != null && !"".equals(mezzoTrasmissione);
	}

	public boolean isRaccomandata() {
		String mezzoTrasmissione = mezzoTrasmissioneDestinatarioItem != null ? mezzoTrasmissioneDestinatarioItem.getValueAsString() : null;		
		if (mezzoTrasmissione != null) {			
			//if ("R".equals(mezzoTrasmissione))
				//return true;			
			if (isAbilRaccomandata(mezzoTrasmissione))
				return true;
		}
		return false;
	}

	public boolean isNotifica() {
		String mezzoTrasmissione = mezzoTrasmissioneDestinatarioItem != null ? mezzoTrasmissioneDestinatarioItem.getValueAsString() : null;		
		if (mezzoTrasmissione != null) {
			if ("NM".equals(mezzoTrasmissione))
				return true;
		}
		return false;
	}
		
	public boolean isEmail() {
		String mezzoTrasmissione = mezzoTrasmissioneDestinatarioItem != null ? mezzoTrasmissioneDestinatarioItem.getValueAsString() : null;
		if (mezzoTrasmissione != null) {
			if ("EMAIL".equals(mezzoTrasmissione))
				return true;
		}
		return false;
	}
	
	public boolean isPEC() {
		String mezzoTrasmissione = mezzoTrasmissioneDestinatarioItem != null ? mezzoTrasmissioneDestinatarioItem.getValueAsString() : null;
		if (mezzoTrasmissione != null) {
			if ("PEC".equals(mezzoTrasmissione))
				return true;
		}
		return false;
	}
	
	public boolean isPEO() {
		String mezzoTrasmissione = mezzoTrasmissioneDestinatarioItem != null ? mezzoTrasmissioneDestinatarioItem.getValueAsString() : null;
		if (mezzoTrasmissione != null) {
			if ("PEO".equals(mezzoTrasmissione))
				return true;
		}
		return false;
	}
	
	public boolean isEmailPECPEO() {
		String mezzoTrasmissione = mezzoTrasmissioneDestinatarioItem != null ? mezzoTrasmissioneDestinatarioItem.getValueAsString() : null;
		if (mezzoTrasmissione != null) {
			if ("EMAIL".equals(mezzoTrasmissione) || "PEC".equals(mezzoTrasmissione) || "PEO".equals(mezzoTrasmissione))
				return true;
		}
		return false;
	}

	public void setCanEditMezzoTrasmissioneMode() {
		if (this.isCreated()) {
			this.setCanEdit(true);
		}
	}
	
	@Override
	public Boolean validate() {
		return lDynamicForm.validate();
	}
	
	public void manageOnChangedUoProtocollante() {	
		if(getSoloMezzoTrasmissionePEC()) {
			LinkedHashMap<String, String> mezzoTrasmissioneDestinatarioValueMap = new LinkedHashMap<String, String>();
			mezzoTrasmissioneDestinatarioValueMap.put("PEC", "PEC (Posta Elettronica Certificata)");
			mezzoTrasmissioneDestinatarioItem.setValueMap(mezzoTrasmissioneDestinatarioValueMap);			
			mezzoTrasmissioneDestinatarioItem.setDefaultValue("PEC");
			lDynamicForm.setValue("mezzoTrasmissioneDestinatario", "PEC");
			mezzoTrasmissioneDestinatarioItem.setCanEdit(false);
		} else {
			mezzoTrasmissioneDestinatarioItem.setValueMap();
			mezzoTrasmissioneDestinatarioItem.setDefaultValue((String) null);
			lDynamicForm.setValue("mezzoTrasmissioneDestinatario", (String) null);
			mezzoTrasmissioneDestinatarioItem.setCanEdit(editing);
		}		
	}
	
	public void markForRedraw() {	
		lDynamicForm.markForRedraw();		
		if(getSoloMezzoTrasmissionePEC()) {
			LinkedHashMap<String, String> mezzoTrasmissioneDestinatarioValueMap = new LinkedHashMap<String, String>();
			mezzoTrasmissioneDestinatarioValueMap.put("PEC", "PEC (Posta Elettronica Certificata)");
			mezzoTrasmissioneDestinatarioItem.setValueMap(mezzoTrasmissioneDestinatarioValueMap);			
			mezzoTrasmissioneDestinatarioItem.setDefaultValue("PEC");
			lDynamicForm.setValue("mezzoTrasmissioneDestinatario", "PEC");		
			mezzoTrasmissioneDestinatarioItem.setCanEdit(false);
		}		
	}

	private boolean isBlank(Object value) {
		return (value == null || ((value instanceof String) && "".equals(value.toString().trim())));
	}
	
	private boolean isAbilRaccomandata(Object codiceMezzoTrasmissioneIn){
		
		if(isBlank(codiceMezzoTrasmissioneIn))
			return false;
		
		String codici = AurigaLayout.getParametroDB("COD_MEZZI_RACCOMANDATA");
		
		if(isBlank(codici))
			codici = "R;";
		
		// Se il codice del mezzo di trasmissione e' nel parametro, allora mostro i campi della raccomandata
		String[] tokens = new StringSplitterClient(codici, ";").getTokens();
		if (tokens.length > 0) {			
			for (int i = 0; i < tokens.length; i++) {				
				if (tokens[i].equals(codiceMezzoTrasmissioneIn)) {
                   return true;					
				}
			}
		}
		return false;
	}
}