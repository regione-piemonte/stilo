/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.events.FetchDataEvent;
import com.smartgwt.client.widgets.events.FetchDataHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.layout.HLayout;

import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatiItem;
import it.eng.auriga.ui.module.layout.client.protocollazione.AllegatoCanvas;
import it.eng.auriga.ui.module.layout.client.protocollazione.OggettoPopup;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ExtendedNumericItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DocPraticaPregressaCanvas extends AllegatoCanvas {
	
	protected ReplicableCanvasForm mDynamicFormPraticaPregressa;
	
	protected TextItem numeroDocumentoItem;
	protected HiddenItem flgFromLoadDettItem;
	protected HiddenItem flgAllegatoItem;
	protected CheckboxItem flgCapofilaItem;
	protected SelectItem tipoProtItem;
	protected SelectItem siglaProtSettoreItem;
	protected ExtendedNumericItem nroProtItem;
	protected AnnoItem annoProtItem;
	protected NumericItem nroDepositoItem;
	protected TextItem annoDepositoItem;
	protected TextItem esibentiItem;
	protected HiddenItem oggettoItem;		
	protected HiddenItem flgUdDaDataEntryScansioniItem;
	protected HiddenItem flgNextDocAllegatoItem;
	
	public DocPraticaPregressaCanvas(DocPraticaPregressaItem item, HashMap<String, String> parameters) {
		super(item, parameters);
	}

	public DocPraticaPregressaCanvas(DocPraticaPregressaItem item) {
		super(item);
	}
	
	@Override
	public void buildDynamicForm() {
		
		super.buildDynamicForm();
		
		List<FormItem> items = new ArrayList<FormItem>();
		items.add(idUdAppartenenzaItem);
		items.add(isUdSenzaFileImportataItem);
		items.add(collegaDocumentoImportatoItem);	
		items.add(idDocAllegatoHiddenItem);
		items.add(fileImportatoItem);
		items.add(uriFileAllegatoItem);
		items.add(infoFileItem);
		items.add(remoteUriItem);
		items.add(isChangedItem);
		items.add(nomeFileAllegatoOrigItem);
		items.add(nomeFileAllegatoTifItem);
		items.add(uriFileAllegatoTifItem);
		items.add(nomeFileVerPreFirmaItem);
		items.add(uriFileVerPreFirmaItem);
		items.add(improntaVerPreFirmaItem);
		items.add(infoFileVerPreFirmaItem);
		items.add(nroUltimaVersioneItem);
		items.add(esitoInvioACTASerieAttiIntegraliItem);
		items.add(esitoInvioACTASeriePubblItem);
		items.add(numFileCaricatiInUploadMultiploItem);
		items.add(numeroAllegatoItem);		
		items.add(nomeFileAllegatoItem);
		items.add(buildTransparentItem());
		items.add(uploadAllegatoItem);
		items.add(allegatoButtons);	
		items.add(improntaItem);
		items.add(flgSostituisciVerPrecItem);
		items.add(flgOriginaleCartaceoItem);		
		items.add(flgCopiaSostitutivaItem);	
		items.add(opzioniTimbroItem);	
		items.add(flgTimbratoFilePostRegItem);
		items.add(flgTimbraFilePostRegItem);
		items.add(flgDatiSensibiliItem);
		
		mDynamicForm.setFields(items.toArray(new FormItem[items.size()]));
	}
	
	@Override
	public Boolean checkAllegatoValorizzato(Map<String, Object> lMap){
		boolean isCapofila = (lMap.get("flgCapofila") != null && (Boolean) lMap.get("flgCapofila"));
		boolean hasProtocolloGenerale = (lMap.get("tipoProt") != null && "PG".equalsIgnoreCase((String) lMap.get("tipoProt"))) &&
										(lMap.get("nroProt") != null && !"".equals((String) lMap.get("nroProt"))) &&
										(lMap.get("annoProt") != null && !"".equals((String) lMap.get("annoProt")));
		boolean hasProtocolloSettore = (lMap.get("tipoProt") != null && "PP".equalsIgnoreCase((String) lMap.get("tipoProt"))) &&
									   (lMap.get("siglaProtSettore") != null && !"".equals((String) lMap.get("siglaProtSettore"))) &&
									   (lMap.get("nroProt") != null && !"".equals((String) lMap.get("nroProt"))) &&
									   (lMap.get("annoProt") != null && !"".equals((String) lMap.get("annoProt")));
		boolean hasDeposito = (lMap.get("nroDeposito") != null && !"".equals((String) lMap.get("nroDeposito"))) &&
							  (lMap.get("annoDeposito") != null && !"".equals((String) lMap.get("annoDeposito")));
		boolean hasFile = (lMap.get("nomeFileAllegato") != null && !"".equals(lMap.get("nomeFileAllegato")));
		if (isCapofila || hasProtocolloGenerale || hasProtocolloSettore ||  hasDeposito || hasFile) { 
			return true;
		}		
		return false;	
	}
	
	@Override
	public boolean validate() {
		boolean valid = super.validate();
		if(!isCapofila() && !hasProtocolloGenerale() && !hasProtocolloSettore() && !hasDeposito()) {
			mDynamicFormPraticaPregressa.setFieldErrors("tipoProt", "Almeno uno tra Protocollo Generale, di Settore e Deposito deve essere valorizzato.");
			valid = false;					
		}			
		return valid;
	}
	
	public void buildDynamicFormPraticaPregressa() {
		
		mDynamicFormPraticaPregressa = new ReplicableCanvasForm() {
			
			@Override
			public boolean hasValue(Record defaultRecord) {

				Map<String,Object> values = getValuesManager() != null ? getValuesManager().getValues() : getValues();
				if (values != null && values.size() > 0) {
					if (checkAllegatoValorizzato(values)) {
						return true;
					}
				}
				return false;
			}
		};
			
		mDynamicFormPraticaPregressa.setNumCols(20);
		mDynamicFormPraticaPregressa.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		
		numeroDocumentoItem = new TextItem("numeroDocumento", "N°") {
			
			@Override
			public void setCanEdit(Boolean canEdit) {
				super.setCanEdit(false);
				setTextBoxStyle(it.eng.utility.Styles.staticTextItemBold);
			}
		};
		numeroDocumentoItem.setWidth(30);
		numeroDocumentoItem.setShowTitle(true);
		numeroDocumentoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				mDynamicFormPraticaPregressa.setValue("numeroDocumento", "" + getCounter());
				return getItem().getTotalMembers() > 1;
			}
		});
		
		flgFromLoadDettItem = new HiddenItem("flgFromLoadDett");
		flgAllegatoItem = new HiddenItem("flgAllegato");

		flgCapofilaItem = new CheckboxItem("flgCapofila", "capofila&nbsp;&nbsp;&nbsp;");
		flgCapofilaItem.setColSpan(1);
		flgCapofilaItem.setWidth("*");
		// flgCapofilaItem.setLabelAsTitle(true);
		flgCapofilaItem.setShowTitle(true);
		flgCapofilaItem.setWrapTitle(false);
		if(((AllegatiItem)getItem()).isFromAllegatoDetailInGridItem()) {
			flgCapofilaItem.setStartRow(true);			
		}	
		flgCapofilaItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				flgCapofilaItem.setCanEdit(isFromLoadDett() ? false : getEditing());
				return true;
			}
		});
		flgCapofilaItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicFormPraticaPregressa.markForRedraw();
			}
		});		
		
		ImgButtonItem flgProtPresenteIcon = new ImgButtonItem("flgProtPresenteIcon", "ok.png", "Protocollo presente in archivio");
		flgProtPresenteIcon.setAlwaysEnabled(true);
		flgProtPresenteIcon.setColSpan(1);
		flgProtPresenteIcon.setIconWidth(16);
		flgProtPresenteIcon.setIconHeight(16);
		flgProtPresenteIcon.setIconVAlign(VerticalAlignment.BOTTOM);
		flgProtPresenteIcon.setAlign(Alignment.LEFT);
		flgProtPresenteIcon.setWidth(16);
		flgProtPresenteIcon.setRedrawOnChange(true);
		flgProtPresenteIcon.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {				
				if(isFromLoadDett()) {
					return false;
				}
				if(isCapofila()) {
					return false;
				}
				if(hasProtocolloGenerale() || hasProtocolloSettore()) {
					return isProtocolloPresente();					
				}
				return false;
			}
		});
		
		ImgButtonItem flgProtNonPresenteIcon = new ImgButtonItem("flgProtNonPresenteIcon", "warning.png", "Protocollo non presente in archivio");
		flgProtNonPresenteIcon.setAlwaysEnabled(true);
		flgProtNonPresenteIcon.setColSpan(1);
		flgProtNonPresenteIcon.setIconWidth(16);
		flgProtNonPresenteIcon.setIconHeight(16);
		flgProtNonPresenteIcon.setIconVAlign(VerticalAlignment.BOTTOM);
		flgProtNonPresenteIcon.setAlign(Alignment.LEFT);
		flgProtNonPresenteIcon.setWidth(16);
		flgProtNonPresenteIcon.setRedrawOnChange(true);
		flgProtNonPresenteIcon.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isFromLoadDett()) {
					return false;
				}
				if(isCapofila()) {
					return false;
				}
				if(hasProtocolloGenerale() || hasProtocolloSettore()) {
					return !isProtocolloPresente();					
				}
				return false;
			}
		});
		
		tipoProtItem = new SelectItem("tipoProt", "Tipo prot.");	
		LinkedHashMap<String, String> tipoProtValueMap = new LinkedHashMap<String, String>();
		tipoProtValueMap.put("PG", "Generale");
		tipoProtValueMap.put("PP", "Settore");
		tipoProtItem.setValueMap(tipoProtValueMap);		
		tipoProtItem.setDefaultValue("PG");
		tipoProtItem.setColSpan(1);
		tipoProtItem.setWidth(100);
		tipoProtItem.setAllowEmptyValue(false);		
		tipoProtItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				tipoProtItem.setCanEdit(isFromLoadDett() ? false : getEditing());
				if(isCapofila()) {
					return false;
				}
				return true;
			}
		});
		tipoProtItem.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				mDynamicFormPraticaPregressa.markForRedraw();
			}
		});
		
		GWTRestDataSource registroProtSettoreDS = new GWTRestDataSource("LoadComboRegistroProtSettoreDataSource", "key", FieldType.TEXT);
		
		siglaProtSettoreItem = new SelectItem("siglaProtSettore", "Registro");
		siglaProtSettoreItem.setColSpan(1);
		siglaProtSettoreItem.setWidth(200);
		siglaProtSettoreItem.setValueField("key");
		siglaProtSettoreItem.setDisplayField("value");
		siglaProtSettoreItem.setOptionDataSource(registroProtSettoreDS);
		siglaProtSettoreItem.setCachePickListResults(false);
		siglaProtSettoreItem.setAllowEmptyValue(false);
		siglaProtSettoreItem.setAutoFetchData(false);
		ListGrid siglaProtSettorePickListProperties = new ListGrid();
		siglaProtSettorePickListProperties.addFetchDataHandler(new FetchDataHandler() {

			@Override
			public void onFilterData(FetchDataEvent event) {				
				GWTRestDataSource registroProtSettoreDS = (GWTRestDataSource) siglaProtSettoreItem.getOptionDataSource();
				registroProtSettoreDS.addParam("idFolderType", ((DocPraticaPregressaItem)getItem()).getIdFolderType());					
				siglaProtSettoreItem.setOptionDataSource(registroProtSettoreDS);
				siglaProtSettoreItem.invalidateDisplayValueCache();
			}
		});
		siglaProtSettoreItem.setPickListProperties(siglaProtSettorePickListProperties);
		siglaProtSettoreItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				siglaProtSettoreItem.setCanEdit(isFromLoadDett() ? false : getEditing());
				if(isCapofila()) {
					return false;
				}
				return isProtocolloSettore();
			}
		});
		siglaProtSettoreItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloPregresso();
			}
		});
	
		nroProtItem = new ExtendedNumericItem("nroProt", "N° prot.", false);
		nroProtItem.setColSpan(1);
		nroProtItem.setWidth(100);
		nroProtItem.setLength(50);
		nroProtItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				nroProtItem.setCanEdit(isFromLoadDett() ? false : getEditing());
				if(isCapofila()) {
					return false;
				}
				return true;
			}
		});
		nroProtItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloPregresso();
			}
		});

		annoProtItem = new AnnoItem("annoProt", "Anno prot.");
		annoProtItem.setColSpan(1);
		annoProtItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				annoProtItem.setCanEdit(isFromLoadDett() ? false : getEditing());
				if(isCapofila()) {
					return false;
				}
				return true;
			}
		});
		annoProtItem.addChangedBlurHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				recuperaDatiProtocolloPregresso();
			}
		});
		
		nroDepositoItem = new NumericItem("nroDeposito", "N° deposito", false);
		nroDepositoItem.setColSpan(1);
		nroDepositoItem.setWidth(100);
		nroDepositoItem.setLength(50);
		nroDepositoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				nroDepositoItem.setCanEdit(isFromLoadDett() ? false : getEditing());
				if(isCapofila()) {
					return false;
				}
				return true;
			}
		});

		annoDepositoItem = new AnnoItem("annoDeposito", "Anno deposito");
		annoDepositoItem.setColSpan(1);
		annoDepositoItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				annoDepositoItem.setCanEdit(isFromLoadDett() ? false : getEditing());
				if(isCapofila()) {
					return false;
				}
				return true;
			}
		});
		
		esibentiItem = new TextItem("esibenti", "Esibente/i");
		esibentiItem.setColSpan(1);
		esibentiItem.setWidth(200);
		esibentiItem.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				boolean canEdit = getEditing();
				if(hasProtocolloGenerale() || hasProtocolloSettore()) {
					if(isProtocolloPresente()) {
						canEdit = false;						
					}
				}
				esibentiItem.setCanEdit(isFromLoadDett() ? false : canEdit);
				if(isCapofila()) {
					return false;
				}
				return true;
			}
		});
		
		oggettoItem = new HiddenItem("oggetto");
		
		ImgButtonItem apriOggettoButton = new ImgButtonItem("apriOggettoButton", "buttons/altriDati.png", "Apri oggetto");
		apriOggettoButton.setShowTitle(false);
		apriOggettoButton.setAlwaysEnabled(true);
		apriOggettoButton.setStartRow(false);
		apriOggettoButton.addIconClickHandler(new IconClickHandler() {

			@Override
			public void onIconClick(IconClickEvent event) {
				boolean canEdit = getEditing();
				if(hasProtocolloGenerale() || hasProtocolloSettore()) {
					if(isProtocolloPresente()) {
						canEdit = false;						
					}
				}								
				Record record = new Record();
				record.setAttribute("oggetto", mDynamicFormPraticaPregressa.getValueAsString("oggetto"));
				OggettoPopup oggettoPopup = new OggettoPopup("Oggetto", record, isFromLoadDett() ? false : canEdit) {
				
					@Override
					public void onClickOkButton(Record savedRecord) {
						mDynamicFormPraticaPregressa.setValue("oggetto", savedRecord.getAttributeAsString("oggetto"));
					}
				};				
				oggettoPopup.show();
			}
		});		
		apriOggettoButton.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isCapofila()) {
					return false;
				}
				return true;
			}
		});
		
		flgUdDaDataEntryScansioniItem = new HiddenItem("flgUdDaDataEntryScansioni");
		flgNextDocAllegatoItem = new HiddenItem("flgNextDocAllegato");
		
		List<FormItem> items = new ArrayList<FormItem>();		
		items.add(numeroDocumentoItem);		
		items.add(flgFromLoadDettItem);
		items.add(flgAllegatoItem);
		items.add(flgCapofilaItem);
		items.add(flgProtPresenteIcon);
		items.add(flgProtNonPresenteIcon);
		items.add(tipoProtItem);
		items.add(siglaProtSettoreItem);
		items.add(nroProtItem);
		items.add(annoProtItem);
		items.add(nroDepositoItem);
		items.add(annoDepositoItem);
		items.add(esibentiItem);
		items.add(oggettoItem);
		items.add(apriOggettoButton);
		items.add(flgUdDaDataEntryScansioniItem);
		items.add(flgNextDocAllegatoItem);
		
		mDynamicFormPraticaPregressa.setFields(items.toArray(new FormItem[items.size()]));
	}
	
	@Override
	public void buildVLayoutCanvas() {
		
		super.buildVLayoutCanvas();
		
		buildDynamicFormPraticaPregressa();
		
		HLayout lHLayoutFile = new HLayout();
		lHLayoutFile.setHeight(5);
		lHLayoutFile.setOverflow(Overflow.VISIBLE);
		lHLayoutFile.setMembers(mDynamicForm, mDynamicFormOmissis);

		lVLayoutCanvas.setMembers(mDynamicFormPraticaPregressa, lHLayoutFile);
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[] { mDynamicFormPraticaPregressa, mDynamicForm, mDynamicFormOmissis };
	}
		
	public boolean isFromLoadDett() {
		return mDynamicFormPraticaPregressa.getValueAsString("flgFromLoadDett") != null ? Boolean.parseBoolean(mDynamicFormPraticaPregressa.getValueAsString("flgFromLoadDett")) : false;		
	}
	
	public boolean isAllegato() {
		return mDynamicFormPraticaPregressa.getValueAsString("flgAllegato") != null ? Boolean.parseBoolean(mDynamicFormPraticaPregressa.getValueAsString("flgAllegato")) : false;		
	}
	
	public boolean isProtocolloPresente() {
		if(isUdDaDataEntryScansioni()) {
			return false;	
		}
		return mDynamicForm.getValueAsString("idDocAllegato") != null && !"".equals(mDynamicForm.getValueAsString("idDocAllegato"));		
	}
	
	public boolean isCapofila() {
		return mDynamicFormPraticaPregressa.getValueAsString("flgCapofila") != null ? Boolean.parseBoolean(mDynamicFormPraticaPregressa.getValueAsString("flgCapofila")) : false;		
	}
	
	public boolean isUdDaDataEntryScansioni() {
		return mDynamicFormPraticaPregressa.getValueAsString("flgUdDaDataEntryScansioni") != null ? Boolean.parseBoolean(mDynamicFormPraticaPregressa.getValueAsString("flgUdDaDataEntryScansioni")) : false;
	}
	
	public boolean isProtocolloGenerale() {
		return mDynamicFormPraticaPregressa.getValueAsString("tipoProt") != null && mDynamicFormPraticaPregressa.getValueAsString("tipoProt").equalsIgnoreCase("PG");
	}
	
	public boolean isProtocolloSettore() {
		return mDynamicFormPraticaPregressa.getValueAsString("tipoProt") != null && mDynamicFormPraticaPregressa.getValueAsString("tipoProt").equalsIgnoreCase("PP");
	}
	
	public boolean hasProtocolloGenerale() {
		if(isProtocolloGenerale()) {
			boolean hasNumProtocolloGenerale = mDynamicFormPraticaPregressa.getValueAsString("nroProt") != null && !"".equals(mDynamicFormPraticaPregressa.getValueAsString("nroProt"));
			boolean hasAnnoProtocolloGenerale = mDynamicFormPraticaPregressa.getValueAsString("annoProt") != null && !"".equals(mDynamicFormPraticaPregressa.getValueAsString("annoProt"));
			return hasNumProtocolloGenerale && hasAnnoProtocolloGenerale;
		}
		return false;
	}
	
	public boolean hasProtocolloSettore() {
		if(isProtocolloSettore()) {			
			boolean hasSiglaProtocolloSettore = mDynamicFormPraticaPregressa.getValueAsString("siglaProtSettore") != null && !"".equals(mDynamicFormPraticaPregressa.getValueAsString("siglaProtSettore"));
			boolean hasNumProtocolloSettore = mDynamicFormPraticaPregressa.getValueAsString("nroProt") != null && !"".equals(mDynamicFormPraticaPregressa.getValueAsString("nroProt"));
			boolean hasAnnoProtocolloSettore = mDynamicFormPraticaPregressa.getValueAsString("annoProt") != null && !"".equals(mDynamicFormPraticaPregressa.getValueAsString("annoProt"));
			return hasSiglaProtocolloSettore && hasNumProtocolloSettore && hasAnnoProtocolloSettore;
		}
		return false;
	}
	
	public boolean hasDeposito() {		
		boolean hasNumeroDeposito =  mDynamicFormPraticaPregressa.getValueAsString("nroDeposito") != null && !"".equals(mDynamicFormPraticaPregressa.getValueAsString("nroDeposito"));
		boolean hasAnnoDeposito = mDynamicFormPraticaPregressa.getValueAsString("annoDeposito") != null && !"".equals(mDynamicFormPraticaPregressa.getValueAsString("annoDeposito"));
		return hasNumeroDeposito && hasAnnoDeposito;		
	}
	
	public void recuperaDatiProtocolloPregresso() {
		if(hasProtocolloGenerale() || hasProtocolloSettore()) {		
			Record lRecord = new Record();	
			lRecord.setAttribute("tipoProtocolloPregresso", mDynamicFormPraticaPregressa.getValueAsString("tipoProt"));		
			lRecord.setAttribute("siglaProtocolloPregresso", mDynamicFormPraticaPregressa.getValueAsString("siglaProtSettore"));		
			lRecord.setAttribute("numProtocolloPregresso", mDynamicFormPraticaPregressa.getValueAsString("nroProt"));
			lRecord.setAttribute("annoProtocolloPregresso", mDynamicFormPraticaPregressa.getValueAsString("annoProt"));
			new GWTRestDataSource("ArchivioDatasource").executecustom("recuperaDatiProtocolloPregresso", lRecord, new DSCallback() {	
				
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {					
					Record recordUdProtocollo = null;
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {						
						Record record = response.getData()[0];
						if(record.getAttribute("idUdCapofila") != null && !"".equals(record.getAttribute("idUdCapofila"))) {
							recordUdProtocollo = record;
						}
					}
					if(recordUdProtocollo != null) {
						mDynamicForm.setValue("idUdAppartenenza", recordUdProtocollo.getAttribute("idUdCapofila"));
						mDynamicForm.setValue("idDocAllegato", recordUdProtocollo.getAttribute("idDocPrimario"));
						String esibenti = null;
						RecordList listaEsibentiPraticaPregressa = recordUdProtocollo.getAttributeAsRecordList("listaEsibentiPraticaPregressa");
						if(listaEsibentiPraticaPregressa != null) {
							for(int i = 0; i < listaEsibentiPraticaPregressa.getLength(); i++) {
								if(esibenti == null) {
									esibenti = listaEsibentiPraticaPregressa.get(i).getAttribute("value");
								} else {
									esibenti += "; " + listaEsibentiPraticaPregressa.get(i).getAttribute("value");
								}								
							}
						}
						mDynamicFormPraticaPregressa.setValue("esibenti", esibenti);
						mDynamicFormPraticaPregressa.setValue("oggetto", recordUdProtocollo.getAttribute("oggetto"));
						mDynamicFormPraticaPregressa.setValue("flgUdDaDataEntryScansioni", recordUdProtocollo.getAttribute("flgUdDaDataEntryScansioni"));
						mDynamicFormPraticaPregressa.setValue("flgNextDocAllegato", recordUdProtocollo.getAttribute("flgNextDocAllegato"));
					} else {
						boolean flgUdDaDataEntryScansioni = mDynamicFormPraticaPregressa.getValue("flgUdDaDataEntryScansioni") != null ? Boolean.valueOf(mDynamicFormPraticaPregressa.getValueAsString("flgUdDaDataEntryScansioni")) : false;
						if(!flgUdDaDataEntryScansioni) {
							mDynamicForm.setValue("idUdAppartenenza", (String) null);
							mDynamicForm.setValue("idDocAllegato", (String) null);
							mDynamicFormPraticaPregressa.setValue("esibenti", (String) null);
							mDynamicFormPraticaPregressa.setValue("oggetto", (String) null);	
							
						}
						mDynamicFormPraticaPregressa.setValue("flgNextDocAllegato", (String) null);							
					}
				}
			});		
		}
	}
	
}