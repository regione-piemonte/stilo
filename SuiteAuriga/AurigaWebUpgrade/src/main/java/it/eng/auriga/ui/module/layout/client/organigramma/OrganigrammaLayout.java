/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.smartgwt.client.data.AdvancedCriteria;
import com.smartgwt.client.data.Criterion;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.OperatorId;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedEvent;
import com.smartgwt.client.widgets.form.fields.events.DataArrivedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

import it.eng.auriga.ui.module.layout.client.AurigaLayout;
import it.eng.auriga.ui.module.layout.client.ErroreMassivoPopup;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.core.shared.bean.CacheLevelBean;
import it.eng.utility.ui.module.core.shared.message.MessageBean;
import it.eng.utility.ui.module.core.shared.message.MessageType;
import it.eng.utility.ui.module.layout.client.Layout;
import it.eng.utility.ui.module.layout.client.StringSplitterClient;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTree;
import it.eng.utility.ui.module.layout.client.common.CustomAdvancedTreeLayout;
import it.eng.utility.ui.module.layout.client.common.GenericCallback;
import it.eng.utility.ui.module.layout.client.common.MultiToolStripButton;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.ImgButtonItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class OrganigrammaLayout extends CustomAdvancedTreeLayout {
	
	protected GWTRestDataSource organigrammiDS;
	protected DynamicForm organigrammiForm;
	protected SelectItem organigrammiItem;
	protected CheckboxItem flgSoloAttiveItem;
	protected SelectItem tipoRelUtentiConUOSelectItem;
	protected ImgButtonItem tipoRelUtentiConUOButton;
	protected CheckboxItem flgMostraSVItem;

	protected Integer flgIncludiUtenti;
	protected String tipoRelUtentiConUo;
	protected String idOrganigramma;
	protected Boolean flgSoloAttive;
	protected Boolean flgMostraSV;
	protected String tsRif;
	protected String idUd;
	
	
	protected String nroLivelliOrganigramma;

	protected ToolStripButton moveUserButton;

	protected Record copyNode;
	protected Record pasteNode;
	
	//Taglia ed Incolla UO
	protected Record cutNode;
	
	//Taglia ed Incolla Postazione utente
	protected Record cutNodePostazione;

	protected MultiToolStripButton associaPuntiProtocolloMultiButton;
	protected MultiToolStripButton scollegaPuntiProtocolloMultiButton;

	protected GestisciPuntiProtocolloPopup gestisciPuntiProtocolloPopup;

	private final int ALT_POPUP_ERR_MASS = 400;
	private final int LARG_POPUP_ERR_MASS = 800;

	public OrganigrammaLayout() {
		this(null, null, null, null, null, null, null, !Layout.isPrivilegioAttivo("SIC/SO;I"), null);
	}

	public OrganigrammaLayout(String finalita, Boolean flgSelezioneSingola, Boolean flgSoloFolder, String idRootNode, Integer flgIncludiUtenti,
			String tipoRelUtentiConUo, String idOrganigramma, Boolean flgSoloAttiveLookup, String tsRif) {
		this(finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, flgIncludiUtenti, tipoRelUtentiConUo, idOrganigramma, flgSoloAttiveLookup, tsRif, null);
	}

	public OrganigrammaLayout(String finalita, Boolean flgSelezioneSingola, Boolean flgSoloFolder, final String idRootNode, Integer flgIncludiUtenti,
			String tipoRelUtentiConUo, String idOrganigramma, Boolean flgSoloAttiveLookup, String tsRif, AdvancedCriteria initialCriteria) {
		this(finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, flgIncludiUtenti, tipoRelUtentiConUo, idOrganigramma, flgSoloAttiveLookup, tsRif,
				initialCriteria, null);
	}

	public OrganigrammaLayout(String finalita, Boolean flgSelezioneSingola, Boolean flgSoloFolder, final String idRootNode, Integer flgIncludiUtenti,
			String tipoRelUtentiConUo, String idOrganigramma, Boolean flgSoloAttiveLookup, String tsRif, AdvancedCriteria initialCriteria, String idUd){
	
		this(finalita, flgSelezioneSingola, flgSoloFolder, idRootNode, flgIncludiUtenti, tipoRelUtentiConUo, idOrganigramma, flgSoloAttiveLookup, tsRif,
				initialCriteria, idUd, null);
	
	}
	
	public OrganigrammaLayout(String finalita, Boolean flgSelezioneSingola, Boolean flgSoloFolder, final String idRootNode, Integer flgIncludiUtenti,
			String tipoRelUtentiConUo, String idOrganigramma, Boolean flgSoloAttiveLookup, String tsRif, AdvancedCriteria initialCriteria, String idUd, String idEmail) {

		super("organigramma", getOrganigrammaTreeDatasource(finalita, idUd, idEmail), getOrganigrammaDataSource(finalita, idUd, idEmail), new OrganigrammaTree("organigramma"),
				new OrganigrammaFilter("organigramma", buildOrganigrammaFilterExtraparams(flgIncludiUtenti)), new OrganigrammaList("organigramma"), new OrganigrammaDetail("organigramma"), finalita,
				flgSelezioneSingola, flgSoloFolder, idRootNode, initialCriteria);

		this.flgIncludiUtenti = flgIncludiUtenti != null ? flgIncludiUtenti : new Integer(1);
		this.tipoRelUtentiConUo = tipoRelUtentiConUo;
		this.idOrganigramma = idOrganigramma;
		this.tsRif = tsRif;
		this.idUd = idUd;
		

		organigrammiDS = new GWTRestDataSource("ListaOrganigrammiDataSource", "idOrganigramma", FieldType.TEXT);

		if (getFinalita() != null && !"".equals(getFinalita())) {
			this.multiselectButton.hide();
		} else {
			this.multiselectButton.show();
		}

		newButton.setPrompt("Nuova U.O.");

		// se arrivo da una lookup
		if (isLookup()) {
			
			setFlgSoloAttive(flgSoloAttiveLookup);

			((GWTRestDataSource) tree.getDataSource()).addParam("flgSoloAttive", getFlgSoloAttive() != null && getFlgSoloAttive() ? "1" : null);
			((GWTRestDataSource) list.getDataSource()).addParam("flgSoloAttive", getFlgSoloAttive() != null && getFlgSoloAttive() ? "1" : null);
			
			organigrammiDS.fetchData(null, new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						RecordList data = response.getDataAsRecordList();
						if (data != null && data.getLength() > 0) {
							if (getIdOrganigramma() == null) {
								for (int i = 0; i < data.getLength(); i++) {
									Record record = data.get(i);
									if (record.getAttribute("flgInVigore") != null && "1".equals(record.getAttribute("flgInVigore"))) {
										setIdOrganigramma(record.getAttribute("idOrganigramma"));
										setNroLivelliOrganigramma(record.getAttribute("nroLivelli"));
										break;
									}
								}
							} else {
								for (int i = 0; i < data.getLength(); i++) {
									if (data.get(i).getAttribute("idOrganigramma").equals(getIdOrganigramma())) {
										setNroLivelliOrganigramma(data.get(i).getAttribute("nroLivelli"));
										break;
									}
								}
							}
							((GWTRestDataSource) tree.getDataSource()).addParam("idOrganigramma", getIdOrganigramma());
							loadTreeFromDefaultIdRootNode();
						}
					}
				}
			});
			
			List<FormItem> listaOrganigrammiFormItems = new ArrayList<FormItem>();
			
			// se arrivo da una lookup che deve vedere solo le attive non mostro il check flgSoloAttive
			if (getFlgSoloAttive() == null || !getFlgSoloAttive()) {
				flgSoloAttiveItem = new CheckboxItem("flgSoloAttive", I18NUtil.getMessages().organigramma_layout_flgSoloAttive());
				flgSoloAttiveItem.setColSpan(1);
				flgSoloAttiveItem.setWidth(25);
				flgSoloAttiveItem.setDefaultValue(getFlgSoloAttive());
				flgSoloAttiveItem.addChangedHandler(new ChangedHandler() {
	
					@Override
					public void onChanged(ChangedEvent event) {
						setFlgSoloAttive(flgSoloAttiveItem.getValueAsBoolean() == null || flgSoloAttiveItem.getValueAsBoolean());
						((GWTRestDataSource) tree.getDataSource()).addParam("flgSoloAttive", getFlgSoloAttive() != null && getFlgSoloAttive() ? "1" : null);
						((GWTRestDataSource) list.getDataSource()).addParam("flgSoloAttive", getFlgSoloAttive() != null && getFlgSoloAttive() ? "1" : null);
						((CustomAdvancedTree) getTree()).reloadSubTreeFrom(getNavigator().getCurrentNode().getIdNode());
						doSearch();
					}
				});
				listaOrganigrammiFormItems.add(flgSoloAttiveItem);
			}
			
			if (getFlgIncludiUtenti() == 1) {
				flgMostraSVItem = new CheckboxItem("flgMostraSV", I18NUtil.getMessages().organigramma_layout_flgMostraSVItem());
				flgMostraSVItem.setColSpan(1);
				flgMostraSVItem.setWidth(25);
				flgMostraSVItem.addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						setFlgMostraSV(flgMostraSVItem.getValueAsBoolean() == null || flgMostraSVItem.getValueAsBoolean());
						((GWTRestDataSource) tree.getDataSource()).addParam("flgIncludiUtenti", getFlgMostraSV() ? "1" : "0");
						((GWTRestDataSource) list.getDataSource()).addParam("flgIncludiUtenti", getFlgMostraSV() ? "1" : "0");
						((CustomAdvancedTree) getTree()).reloadSubTreeFrom(getNavigator().getCurrentNode().getIdNode());
						doSearch();
					}
				});
				listaOrganigrammiFormItems.add(flgMostraSVItem);
			}
			
			if(listaOrganigrammiFormItems != null && listaOrganigrammiFormItems.size() > 0) {
				organigrammiForm = new DynamicForm();
				organigrammiForm.setWidth100();
				organigrammiForm.setNumCols(14);
				organigrammiForm.setColWidths(1, "*", "*", "*");
				organigrammiForm.setItems(listaOrganigrammiFormItems.toArray(new FormItem[listaOrganigrammiFormItems.size()]));
				navigationLayout.setMembers(organigrammiForm, navigationToolStrip);
			}
		}
		// altrimenti mostro la combo degli organigrammi, il check flgSoloAttive (di default a true) e le relazioni degli utenti
		else {
			
			setFlgSoloAttive(true);
			
			((GWTRestDataSource) tree.getDataSource()).addParam("flgSoloAttive", getFlgSoloAttive() != null && getFlgSoloAttive() ? "1" : null);
			((GWTRestDataSource) list.getDataSource()).addParam("flgSoloAttive", getFlgSoloAttive() != null && getFlgSoloAttive() ? "1" : null);
			
			// di default le relazioni degli utenti sono A e D
			String tipoRelUtentiConUoDefaultValue = "A;D";
			if (tipoRelUtentiConUo != null && !"".equalsIgnoreCase(tipoRelUtentiConUo)) {
				tipoRelUtentiConUoDefaultValue = tipoRelUtentiConUo;
			}
			setTipoRelUtentiConUo(tipoRelUtentiConUoDefaultValue);

			organigrammiItem = new SelectItem("listaOrganigrammi", "<b>Organigramma</b>");
			organigrammiItem.setWrapTitle(false);
			organigrammiItem.setAllowEmptyValue(false);

			ListGridField idOrganigrammaField = new ListGridField("idOrganigramma", "Id.");
			idOrganigrammaField.setHidden(true);
			ListGridField dataInizioField = new ListGridField("dataInizio", "Dal");
			dataInizioField.setWidth(80);
			ListGridField dataFineField = new ListGridField("dataFine", "Al");
			dataFineField.setWidth(80);
			ListGridField noteField = new ListGridField("note", "Note");
			organigrammiItem.setPickListFields(idOrganigrammaField, dataInizioField, dataFineField, noteField);

			organigrammiItem.setValueField("idOrganigramma");
			organigrammiItem.setDisplayField("displayValue");
			organigrammiItem.setOptionDataSource(organigrammiDS);

			organigrammiItem.addDataArrivedHandler(new DataArrivedHandler() {

				@Override
				public void onDataArrived(DataArrivedEvent event) {
					RecordList data = event.getData();
					if (data != null && data.getLength() > 0) {
						if (getIdOrganigramma() == null) {
							for (int i = 0; i < data.getLength(); i++) {
								Record record = data.get(i);
								if (record.getAttribute("flgInVigore") != null && "1".equals(record.getAttribute("flgInVigore"))) {
									setIdOrganigramma(record.getAttribute("idOrganigramma"));
									setNroLivelliOrganigramma(record.getAttribute("nroLivelli"));
									break;
								}
							}
						} else {
							for (int i = 0; i < data.getLength(); i++) {
								if (data.get(i).getAttribute("idOrganigramma").equals(getIdOrganigramma())) {
									setNroLivelliOrganigramma(data.get(i).getAttribute("nroLivelli"));
									break;
								}
							}

						}
						organigrammiItem.setValue(getIdOrganigramma());
						((GWTRestDataSource) tree.getDataSource()).addParam("idOrganigramma", getIdOrganigramma());
						loadTreeFromDefaultIdRootNode();
					}
				}
			});

			ListGrid organigrammiPickListProperties = new ListGrid();
			organigrammiPickListProperties.setEmptyMessage(I18NUtil.getMessages().list_emptyMessage());
			organigrammiPickListProperties.addCellClickHandler(new CellClickHandler() {

				@Override
				public void onCellClick(CellClickEvent event) {
					setIdOrganigramma((String) event.getRecord().getAttribute("idOrganigramma"));
					((GWTRestDataSource) tree.getDataSource()).addParam("idOrganigramma", getIdOrganigramma());
					loadTreeFromDefaultIdRootNode();
				}
			});
			organigrammiItem.setPickListProperties(organigrammiPickListProperties);

			flgSoloAttiveItem = new CheckboxItem("flgSoloAttive", I18NUtil.getMessages().organigramma_layout_flgSoloAttive());
			flgSoloAttiveItem.setColSpan(1);
			flgSoloAttiveItem.setWidth(25);
			flgSoloAttiveItem.setDefaultValue(getFlgSoloAttive());
			flgSoloAttiveItem.addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					setFlgSoloAttive(flgSoloAttiveItem.getValueAsBoolean() == null || flgSoloAttiveItem.getValueAsBoolean());
					((GWTRestDataSource) tree.getDataSource()).addParam("flgSoloAttive", getFlgSoloAttive() != null && getFlgSoloAttive() ? "1" : null);
					((GWTRestDataSource) list.getDataSource()).addParam("flgSoloAttive", getFlgSoloAttive() != null && getFlgSoloAttive() ? "1" : null);
					((CustomAdvancedTree) getTree()).reloadSubTreeFrom(getNavigator().getCurrentNode().getIdNode());
					doSearch();
				}
			});
			
			// TIPO RELAZIONE UTENTI
			tipoRelUtentiConUOSelectItem = new SelectItem("tipoRelUtentiConUO", I18NUtil.getMessages().organigramma_layout_tipoRelUtentiConUOSelectItem_title());		
			LinkedHashMap<String, String> lLinkedHashMapTipoRelUtentiConUO = new LinkedHashMap<String, String>();		
			lLinkedHashMapTipoRelUtentiConUO.put("A", "appartenenza gerarchica");
			lLinkedHashMapTipoRelUtentiConUO.put("D", "funzionale");
			if (isAttivoGestionePuntiProtocollo()) {
				lLinkedHashMapTipoRelUtentiConUO.put("P", "protocollista");
			}
			lLinkedHashMapTipoRelUtentiConUO.put("L", "postazione ombra");
			tipoRelUtentiConUOSelectItem.setWrapTitle(false);
			tipoRelUtentiConUOSelectItem.setValueMap(lLinkedHashMapTipoRelUtentiConUO);
			tipoRelUtentiConUOSelectItem.setColSpan(1);
			tipoRelUtentiConUOSelectItem.setWidth(250);		
			tipoRelUtentiConUOSelectItem.setClearable(true);
			tipoRelUtentiConUOSelectItem.setAllowEmptyValue(true);
			tipoRelUtentiConUOSelectItem.setMultiple(true);
			tipoRelUtentiConUOSelectItem.setDefaultValues(tipoRelUtentiConUoDefaultValue.split(";"));
			
			tipoRelUtentiConUOButton = new ImgButtonItem("tipoRelUtentiConUOButton", "ok.png", I18NUtil.getMessages().organigramma_layout_tipoRelUtentiConUOButton_title());
			tipoRelUtentiConUOButton.setColSpan(1);
			tipoRelUtentiConUOButton.setIconWidth(16);
			tipoRelUtentiConUOButton.setIconHeight(16);
			tipoRelUtentiConUOButton.setIconVAlign(VerticalAlignment.BOTTOM);
			tipoRelUtentiConUOButton.setAlign(Alignment.LEFT);
			tipoRelUtentiConUOButton.setWidth(16);			
			tipoRelUtentiConUOButton.addIconClickHandler(new IconClickHandler() {

				@Override
				public void onIconClick(IconClickEvent event) {
					// FIXME inserire i valori selezionati
					setTipoRelUtentiConUo(tipoRelUtentiConUOSelectItem.getValueAsString() != null ? tipoRelUtentiConUOSelectItem.getValueAsString().replace(",", ";") : null);
					((GWTRestDataSource) tree.getDataSource()).addParam("tipoRelUtentiConUo", getTipoRelUtentiConUo());
					((GWTRestDataSource) list.getDataSource()).addParam("tipoRelUtentiConUo", getTipoRelUtentiConUo());
					((CustomAdvancedTree) getTree()).reloadSubTreeFrom(getNavigator().getCurrentNode().getIdNode());
					doSearch();
				}
			});
			
			organigrammiForm = new DynamicForm();
			organigrammiForm.setWidth100();
			organigrammiForm.setNumCols(14);
			organigrammiForm.setColWidths(1, "*", "*", "*");
			organigrammiForm.setItems(organigrammiItem, flgSoloAttiveItem, tipoRelUtentiConUOSelectItem, tipoRelUtentiConUOButton);

			navigationLayout.setMembers(organigrammiForm, navigationToolStrip);
		}

		if(flgMostraSVItem != null) {
			//leggere le pref e impoxstare default di flgMostraSVItem
			flgMostraSVItem.setValue(getFlgMostraSVDefaultValueLookup());
			((GWTRestDataSource) tree.getDataSource()).addParam("flgIncludiUtenti", getFlgMostraSVDefaultValueLookup() ? "1" : "0");
			((GWTRestDataSource) list.getDataSource()).addParam("flgIncludiUtenti", getFlgMostraSVDefaultValueLookup() ? "1" : "0");
		} else {
			if (getFlgIncludiUtenti() != null && getFlgIncludiUtenti().intValue() > 0) {
				((GWTRestDataSource) tree.getDataSource()).addParam("flgIncludiUtenti", "1");
			}
		}
		((GWTRestDataSource) tree.getDataSource()).addParam("tipoRelUtentiConUo", getTipoRelUtentiConUo());
		((GWTRestDataSource) list.getDataSource()).addParam("tipoRelUtentiConUo", getTipoRelUtentiConUo());
		((GWTRestDataSource) tree.getDataSource()).addParam("idOrganigramma", getIdOrganigramma());
		((GWTRestDataSource) tree.getDataSource()).addParam("tsRif", getTsRif());

	}
	
	public static Map<String, String> buildOrganigrammaFilterExtraparams(Integer flgIncludiUtenti) {
		Map<String, String> extraparam = new HashMap<String, String>();
		extraparam.put("flgIncludiUtenti", flgIncludiUtenti != null ? String.valueOf(flgIncludiUtenti.intValue()) : "1");		
		return extraparam;
	}
	
	public VLayout getLeftLayout() {
		return this.leftLayout;
	}

	public static GWTRestDataSource getOrganigrammaTreeDatasource(String finalita, String idUd, String idEmail) {
		GWTRestDataSource organigrammaTreeDS = new GWTRestDataSource("OrganigrammaTreeDatasource", true, "idNode", FieldType.TEXT);
		organigrammaTreeDS.addParam("idUd", idUd);
		organigrammaTreeDS.addParam("idEmail", idEmail);
		organigrammaTreeDS.addParam("finalita", finalita);
		return organigrammaTreeDS;
	}

	public static GWTRestDataSource getOrganigrammaDataSource(String finalita, String idUd, String idEmail) {
		GWTRestDataSource organigrammaDS = new GWTRestDataSource("OrganigrammaDatasource", "idUoSvUt", FieldType.TEXT);
		organigrammaDS.addParam("idUd", idUd);
		organigrammaDS.addParam("idEmail", idEmail);
		organigrammaDS.addParam("finalita", finalita);
		return organigrammaDS;
	}
	
	@Override
	protected GWTRestDataSource createNroRecordDatasource() {
		
		GWTRestDataSource gWTRestDataSource = (GWTRestDataSource) getList().getDataSource();
		gWTRestDataSource.setForceToShowPrompt(false);

		return gWTRestDataSource;
	}
	
	@Override
	protected Record[] extractRecords(String[] fields) {
		// Se sono in overflow i dati verranno recuperati con il metodo asincrono, altrimenti utilizzo quelli nella lista a GUI
		if (overflow){
			return new Record[0];
		}else{
			return super.extractRecords(fields);
		}
	}

	@Override
	public void onSaveButtonClick() {
		final Record record = new Record(detail.getValuesManager().getValues());
		if (detail.validate()) {
			
			DSOperationType saveOperationType = detail.getValuesManager().getSaveOperationType();
			Layout.showWaitPopup("Salvataggio in corso: potrebbe richiedere qualche secondo. Attendere...");
			DSCallback callback = new DSCallback() {

				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
						final Record savedRecord = response.getData()[0];
						if (savedRecord.getAttribute("flgIgnoreWarning") == null || savedRecord.getAttributeAsInt("flgIgnoreWarning") != 1) {
							try {
								DSCallback callback = new DSCallback() {

									@Override
									public void execute(DSResponse response, Object rawData, DSRequest request) {
										viewMode();
										Layout.hideWaitPopup();
										Layout.addMessage(new MessageBean(
												I18NUtil.getMessages().afterSave_message(getTipoEstremiRecord(response.getData()[0])), "", MessageType.INFO));
									}
								};
								if(detail instanceof OrganigrammaDetail) {
									((OrganigrammaList)list).manageLoadDetailUnitaOrganizzativa(savedRecord, detail.getRecordNum(), callback);
								} else if(detail instanceof PostazioneDetail) {
									((OrganigrammaList)list).manageLoadDetailPostazione(savedRecord, detail.getRecordNum(), callback);
								}
							} catch (Exception e) {
								Layout.hideWaitPopup();
							}
						} else {
							detail.getValuesManager().setValue("flgIgnoreWarning", "1");
							Layout.hideWaitPopup();
						}
						if (!fullScreenDetail) {
							reloadListAndSetCurrentRecord(savedRecord);
						}
					} else {
						Layout.hideWaitPopup();
					}
				}
			};
			try {
				/**
				 * Nel caso di una Nuova/Modifica POSTAZIONE viene controllata la chiave primaria della stessa,
				 * ovvero il campo ' ciRelUserUo ' e non ' idUoSvUt ' che invece potrebbe essere NULL anche in fase di creazione.
				 * Senza questo controllo, ad una nuova azione di aggancia utente ad UO, dopo il primo salvataggio,
				 * SENZA CHIUDERE LA WINDOW, tutte le altre operazioni di MODIFICA erano di tipo ADD, quindi senza ciRelUserUo
				 * valorizzato e quindi non corrette.
				 */
				if(detail != null && detail instanceof PostazioneDetail){
					if (saveOperationType != null && saveOperationType.equals(DSOperationType.ADD) || record.getAttribute("ciRelUserUo") == null
							|| record.getAttribute("ciRelUserUo").equals("")){
						detail.getDataSource().addData(record, callback);
					} else {
						detail.getDataSource().updateData(record, callback);
					}
				}else{
					if ((saveOperationType != null && saveOperationType.equals(DSOperationType.ADD)) || record.getAttribute("idUoSvUt") == null
							|| record.getAttribute("idUoSvUt").equals("")) {
						detail.getDataSource().addData(record, callback);
					} else {
						detail.getDataSource().updateData(record, callback);
					}
				}
			} catch (Exception e) {
				Layout.hideWaitPopup();
			}
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().validateError_message(), "", MessageType.ERROR));
		}
	}

	@Override
	public String getNewDetailTitle() {
		if (detail instanceof OrganigrammaDetail) {
			return "Nuova U.O.";
		} else if (detail instanceof PostazioneDetail) {
			return "Nuova postazione";
		}
		return super.getNewDetailTitle();
	}

	@Override
	public String getEditDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		if (detail instanceof OrganigrammaDetail) {
			return "Modifica U.O. " + getTipoEstremiRecord(record);
		} else if (detail instanceof PostazioneDetail) {
			if (record.getAttribute("sostituzioneSV") != null && "1".equals(record.getAttribute("sostituzioneSV"))) {
				return "Sostituzione utente " + getTipoEstremiRecord(record);
			} else {
				return "Modifica postazione " + getTipoEstremiRecord(record);
			}
		}
		return super.getEditDetailTitle();
	}

	@Override
	public String getViewDetailTitle() {
		Record record = new Record(detail.getValuesManager().getValues());
		if (detail instanceof OrganigrammaDetail) {
			return "Dettaglio U.O. " + getTipoEstremiRecord(record);
		} else if (detail instanceof PostazioneDetail) {
			return "Dettaglio postazione " + getTipoEstremiRecord(record);
		}
		return super.getViewDetailTitle();
	}

	@Override
	public String getTipoEstremiRecord(Record record) {
		if (detail instanceof OrganigrammaDetail) {
			if (record.getAttributeAsString("livelloCorrente") != null && !"".equals(record.getAttributeAsString("livelloCorrente"))) {
				return record.getAttributeAsString("livelloCorrente") + "." + record.getAttributeAsString("livello") + " - "
						+ record.getAttributeAsString("denominazioneEstesa");
			} else {
				return record.getAttributeAsString("livello") + " - " + record.getAttributeAsString("denominazioneEstesa");
			}
		} else if (detail instanceof PostazioneDetail) {
			return record.getAttributeAsString("intestazione");
		}
		return super.getTipoEstremiRecord(record);
	}

	@Override
	public void setMultiselect(Boolean multiselect) {
		super.setMultiselect(multiselect);
		BigDecimal nroLivello = new BigDecimal(getNroLivelloCorrente());
		if (!isAbilToInsInLivello(nroLivello) || (getFinalita() != null && !"".equals(getFinalita()))) {
			newButton.hide();
		} else {
			newButton.show();
		}
	}

	@Override
	public void cercaMode() {
		super.cercaMode();
		BigDecimal nroLivello = new BigDecimal(getNroLivelloCorrente());
		if (!isAbilToInsInLivello(nroLivello) || (getFinalita() != null && !"".equals(getFinalita()))) {
			newButton.hide();
		} else {
			newButton.show();
		}
	}

	@Override
	public void esploraMode(CacheLevelBean cacheLevel) {
		super.esploraMode(cacheLevel);
		BigDecimal nroLivello = new BigDecimal(getNroLivelloCorrente());
		if (!isAbilToInsInLivello(nroLivello) || (getFinalita() != null && !"".equals(getFinalita()))) {
			newButton.hide();
		} else {
			newButton.show();
		}
	}

	public static boolean isAbilToIns() {
		return Layout.isPrivilegioAttivo("SIC/SO;I");
	}

	public static boolean isAbilToMod() {
		return Layout.isPrivilegioAttivo("SIC/SO;M");
	}

	
	public static boolean isAbilTipologieDocPubblicabili() {
		return (isAbilToMod() && ( AurigaLayout.getParametroDB("SIST_ALBO")!=null && AurigaLayout.getParametroDB("SIST_ALBO").equalsIgnoreCase("AURIGA")));		
	}
	
	
	public boolean isAbilToInsInLivello(BigDecimal nroLivello) {
		return isAbilToIns() && (getNroLivelliOrganigramma() == null || nroLivello.compareTo(new BigDecimal(getNroLivelliOrganigramma())) < 0);
	}

	@Override
	public void newMode() {
		super.newMode();
		altreOpButton.hide();
	}

	@Override
	public void viewMode() {
		super.viewMode();
		if (isAbilToMod()) {
			editButton.show();
		} else {
			editButton.hide();
		}
		deleteButton.hide();
		altreOpButton.hide();
		Record record = new Record(detail.getValuesManager().getValues());
		if (isLookup() && record.getAttributeAsBoolean("flgSelXFinalita")) {
			lookupButton.show();
		} else {
			lookupButton.hide();
		}
	}

	@Override
	public void editMode() {
		super.editMode();
		altreOpButton.hide();	
	}
	
	@Override
	public AdvancedCriteria buildSearchCriteria(AdvancedCriteria criteria) {
		List<Criterion> criterionList = new ArrayList<Criterion>();
		if (criteria == null) {
			criteria = new AdvancedCriteria();
		} else {
			for (Criterion crit : criteria.getCriteria()) {
				criterionList.add(crit);
			}
		}

		if (flgIncludiUtenti != null) {
			criterionList.add(new Criterion("flgIncludiUtenti", OperatorId.EQUALS, String.valueOf(flgIncludiUtenti)));
		}
		if (tipoRelUtentiConUo != null && !"".equals(tipoRelUtentiConUo)) {
			criterionList.add(new Criterion("tipoRelUtentiConUo", OperatorId.EQUALS, tipoRelUtentiConUo));
		}
		if (idOrganigramma != null && !"".equals(idOrganigramma)) {
			criterionList.add(new Criterion("idOrganigramma", OperatorId.EQUALS, idOrganigramma));
		}
		if (flgSoloAttive != null) {
			criterionList.add(new Criterion("flgSoloAttive", OperatorId.EQUALS, flgSoloAttive));
		}
		if (tsRif != null && !"".equals(tsRif)) {
			criterionList.add(new Criterion("tsRif", OperatorId.EQUALS, tsRif));
		}

		Criterion[] criterias = new Criterion[criterionList.size()];
		for (int i = 0; i < criterionList.size(); i++) {
			criterias[i] = criterionList.get(i);
		}
		return super.buildSearchCriteria(new AdvancedCriteria(OperatorId.AND, criterias));
	}

	@Override
	public void setAltriParametriPercorsoIniziale(java.util.Map<String, String> altriParametri) {
		this.altriParametri = altriParametri;
		setIdOrganigramma(altriParametri.get("idOrganigramma"));
	}

	@Override
	public void doLookup(Record record) {
		if (record.getAttributeAsBoolean("flgSelXFinalita")) {
			super.doLookup(record);
		} else {
			Layout.addMessage(new MessageBean(I18NUtil.getMessages().recordNonSelezionabileXFinalita_message(), "", MessageType.WARNING));
		}
	}

	@Override
	protected Record createMultiLookupRecord(Record record) {
		final Record newRecord = new Record();
		if (record.getAttribute("idFolder") != null && !"".equals(record.getAttributeAsString("idFolder"))) {
			newRecord.setAttribute("id", record.getAttributeAsString("idFolder"));
			newRecord.setAttribute("nome", record.getAttributeAsString("nome"));
		} else if (record.getAttribute("idUoSvUt") != null && !"".equals(record.getAttributeAsString("idUoSvUt"))) {
			newRecord.setAttribute("id", record.getAttributeAsString("idUoSvUt"));
			String codRapidoUo = record.getAttributeAsString("codRapidoUo");
			String nome = record.getAttributeAsString("nome");
			newRecord.setAttribute("nome", codRapidoUo != null && !"".equals(codRapidoUo) ? codRapidoUo + " - " + nome : nome);
		}
		String tipo = record.getAttributeAsString("tipo");
		int pos = tipo.indexOf("_");
		if (!tipo.equals("SV_A") && !tipo.startsWith("UO") && pos != -1) {
			tipo = tipo.substring(0, pos);
		}
		newRecord.setAttribute("icona", nomeEntita + "/tipo/" + tipo + ".png");
		return newRecord;
	}

	@Override
	public void manageNewClick() {
		this.setMode("edit");
		if (!(detail instanceof OrganigrammaDetail)) {
			this.changeDetail(getOrganigrammaDataSource(finalita, idUd, null), new OrganigrammaDetail("organigramma"));
		}
		detail.markForRedraw();
		Map<String, String> mappa = new HashMap<String, String>();
		mappa.put("nroLivello", "" + (getNroLivelloCorrente() + 1));
		mappa.put("livelloCorrente", getCodRapidoUoCorrente());
		detail.editNewRecord(mappa);
		detail.getValuesManager().clearErrors(true);
		setIdNodeToOpen(getNavigator().getCurrentNode().getIdNode());
		newMode();
	}

	@Override
	public boolean showMultiselectButtonsUnderList() {
		// TODO: rendere opzionale la check
		return isAttivoGestionePuntiProtocollo();
	}

	@Override
	protected MultiToolStripButton[] getMultiselectButtons() {
		// Bottone firma massiva volumi
		if (isAttivoGestionePuntiProtocollo()) {
			if (associaPuntiProtocolloMultiButton == null) {
				associaPuntiProtocolloMultiButton = new MultiToolStripButton("organigramma/puntoProtocolloAdd.png", this, I18NUtil.getMessages()
						.organigramma_list_associaPuntiProtocolloMultiButton_title(), false) {

					@Override
					public boolean toShow() {
						return true;
					}

					@Override
					public void doSomething() {
						associaPuntiProtocolloMultiButtonClick();
					}
				};
			}

			if (scollegaPuntiProtocolloMultiButton == null) {
				scollegaPuntiProtocolloMultiButton = new MultiToolStripButton("organigramma/puntoProtocolloRemove.png", this, I18NUtil.getMessages()
						.organigramma_list_scollegaPuntiProtocolloMultiButton_title(), false) {

					@Override
					public boolean toShow() {
						return true;
					}

					@Override
					public void doSomething() {
						scollegaPuntiProtocolloMultiButtonClick();
					}
				};
			}
			return new MultiToolStripButton[] { associaPuntiProtocolloMultiButton, scollegaPuntiProtocolloMultiButton };
		} else
			return new MultiToolStripButton[] {};
	}
	
	//Incolla postazione dopo la copia
	public void copyAndPaste(final String tipo,final Record currentRecord,final Boolean isFromList) {
		if(tipo != null ){
			Record record = new Record();
			if ("SV".equals(tipo)) {
				record.setAttribute("idUo", getCopyNode().getAttribute("idUo"));
				record.setAttribute("tipo", tipo);
				record.setAttribute("idUser", getCopyNode().getAttribute("idUser"));
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
					record.setAttribute("flgUoPuntoProtocollo", currentRecord.getAttributeAsBoolean("flgPuntoProtocollo"));
					record.setAttribute("skipFlgUoPuntoProtocollo", "true");
				}
			}
			getList().manageLoadDetail(record, -1, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
			
					if("SV".equals(tipo)) {
						afterCopyAndPasteSV(currentRecord,isFromList);
					}
				}
			});
		}
	}
	
	//Incolla UO o Postazione utente
	public void paste(final String tipo,final Record currentRecord,final Boolean isFromList) {
		
		if(tipo != null ){
			Record record = new Record();
			
			if("UO".equals(tipo)){
				record.setAttribute("idUoSvUt", getCutNode().getAttribute("idUo"));
				record.setAttribute("tipo", tipo);
			} else if ("SV".equals(tipo)) {
				record.setAttribute("idUo", getCutNodePostazione().getAttribute("idUo"));
				record.setAttribute("tipo", tipo);
				record.setAttribute("idUser", getCutNodePostazione().getAttribute("idUser"));
				if (AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT")) {
					record.setAttribute("flgUoPuntoProtocollo", currentRecord.getAttributeAsBoolean("flgPuntoProtocollo"));
					record.setAttribute("skipFlgUoPuntoProtocollo", "true");
				}
			}
		
			getList().manageLoadDetail(record, -1, new DSCallback() {
	
				@Override
				public void execute(DSResponse response, Object rawData, DSRequest request) {
					
					if("UO".equals(tipo)) { 
						afterPasteUO(currentRecord,isFromList);
					} else if ("SV".equals(tipo)) {
						afterPasteSV(currentRecord,isFromList);
					}
				}
			});
		}
	}
	
	public void afterPasteUO(Record recordDest, Boolean isFromList) {
		
		getDetail().getValuesManager().setValue("storicizzaDatiVariati", true);
		try {
			getDetail().getValuesManager().setValue("nroLivello", "" + (Integer.parseInt(recordDest.getAttribute("nroLivello")) + 1));
		} catch (Exception e) {
			getDetail().getValuesManager().setValue("nroLivello", "1");
		}
		getDetail().getValuesManager().setValue("livelloCorrente", recordDest.getAttribute("codRapidoUo"));
		getDetail().getValuesManager().setValue("livelloCorrenteOrig", recordDest.getAttribute("codRapidoUo"));
		editMode();
		Layout.changeTitleOfPortlet(nomeEntita, "Spostamento UO "+ getCutNode().getAttribute("nome") + " nella UO " + recordDest.getAttribute("nome"));
		
		if(isFromList != null && isFromList){
			setIdNodeToOpenByIdFolder(recordDest.getAttribute("idUoSvUt") + "|*|" + getCutNode().getAttribute("idUoSup"), null);
		}else{
			setIdNodeToOpen(recordDest.getAttribute("idNode") + "|*|" + getCutNode().getAttribute("parentId"));	
		}
		
		setCutNode(null);
	}
	
	public void afterCopyAndPasteSV(Record recordDest, Boolean isFromList) {
				
		getDetail().getValuesManager().setValue("flgDuplicazione", true);
		getDetail().getValuesManager().setValue("idUo",  recordDest.getAttribute("idUo"));
		getDetail().getValuesManager().setValue("dataDal", new Date());
		getDetail().getValuesManager().setValue("dataAl",  "");
		getDetail().getValuesManager().setValue("tipoRelUtenteUo", getCopyNode().getAttribute("tipoRelUtenteUo"));
		editMode();
		Layout.changeTitleOfPortlet(nomeEntita, "Duplicazione utente " + copyNode.getAttribute("nome") + " nella UO " + recordDest.getAttribute("nome"));
		
		if(isFromList != null && isFromList){
			setIdNodeToOpenByIdFolder(recordDest.getAttribute("idUoSvUt") + "|*|" + getCopyNode().getAttribute("idUoSup"), null);
		}else{
			setIdNodeToOpen(recordDest.getAttribute("idNode") + "|*|" + getCopyNode().getAttribute("parentId"));
		}
		
		setCopyNode(null);
	}
	
	public void afterPasteSV(Record recordDest, Boolean isFromList) {
		
		getDetail().getValuesManager().setValue("flgSpostamento", true);
		getDetail().getValuesManager().setValue("idUo",  recordDest.getAttribute("idUo"));
		getDetail().getValuesManager().setValue("dataDal", new Date());
		getDetail().getValuesManager().setValue("dataAl",  "");
		getDetail().getValuesManager().setValue("tipoRelUtenteUo", getCutNodePostazione().getAttribute("tipoRelUtenteUo"));
		editMode();
		Layout.changeTitleOfPortlet(nomeEntita, "Spostamento utente " + getCutNodePostazione().getAttribute("nome") + " nella UO " + recordDest.getAttribute("nome"));
		
		if(isFromList != null && isFromList){
			setIdNodeToOpenByIdFolder(recordDest.getAttribute("idUoSvUt") + "|*|" + getCutNodePostazione().getAttribute("idUoSup"), null);
		}else{
			setIdNodeToOpen(recordDest.getAttribute("idNode") + "|*|" + getCutNodePostazione().getAttribute("parentId"));	
		}
		
		setCutNodePostazione(null);
	}
	
	public void delete(final Record currentRecord,final Boolean isFromList) {
		
		getDatasource().extraparam.put("isFromList", isFromList != null && isFromList ? "true" : "false");
		getDatasource().removeData(currentRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record recordResponse = response.getData()[0];
					if(recordResponse != null) {
						String warningMsgOut = recordResponse.getAttributeAsString("warningMsgOut");
						if(warningMsgOut == null || "".equals(warningMsgOut)){
							Layout.addMessage(new MessageBean("Operazione effettuata con successo", "" , MessageType.INFO));
							if(!isFromList){
								((CustomAdvancedTree) getTree()).reloadSubTreeFrom(currentRecord.getAttributeAsString("parentId"));	
							}
							if (isFetched()) {
								setIdNodeToOpenByIdFolder(currentRecord.getAttribute("idUoSup"), new GenericCallback() {
									
									@Override
									public void execute() {
										hideDetailAfterSave();
									}
								});	
							}
						} else {
							SC.ask(warningMsgOut, new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {
									
									if(value){
										getDatasource().extraparam.put("flgIgnoreWarning", "1");
										getDatasource().extraparam.put("isFromList", isFromList != null && isFromList ? "true" : "false");
										getDatasource().removeData(currentRecord, new DSCallback() {
											
											@Override
											public void execute(DSResponse response, Object rawData, DSRequest request) {
												if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
													Layout.addMessage(new MessageBean("Operazione effettuata con successo", "" , MessageType.INFO));
													if(!isFromList){
														((CustomAdvancedTree) getTree()).reloadSubTreeFrom(currentRecord.getAttributeAsString("parentId"));	
													}
													if (isFetched()) {
														setIdNodeToOpenByIdFolder(currentRecord.getAttribute("idUoSup"), new GenericCallback() {
															
															@Override
															public void execute() {
																hideDetailAfterSave();
															}
														});	
													}
												}
											}
										});
									}
								}
							});
						}
					}
				}
				Layout.hideWaitPopup();
			}
		});
	}

	public Integer getFlgIncludiUtenti() {
		return flgIncludiUtenti;
	}

	public String getTipoRelUtentiConUo() {
		return tipoRelUtentiConUo;
	}

	public void setTipoRelUtentiConUo(String tipoRelUtentiConUo) {
		this.tipoRelUtentiConUo = tipoRelUtentiConUo;
	}
	
	public String getIdOrganigramma() {
		return idOrganigramma;
	}

	public void setIdOrganigramma(String idOrganigramma) {
		this.idOrganigramma = idOrganigramma;
	}

	public Boolean getFlgSoloAttive() {
		return flgSoloAttive;
	}

	public void setFlgSoloAttive(Boolean flgSoloAttive) {
		this.flgSoloAttive = flgSoloAttive;
	}
		
	public Boolean getFlgMostraSV() {
		return flgMostraSV;
	}

	public void setFlgMostraSV(Boolean flgMostraSV) {
		this.flgMostraSV = flgMostraSV;
	}

	public String getNroLivelliOrganigramma() {
		return nroLivelliOrganigramma;
	}

	public void setNroLivelliOrganigramma(String nroLivelliOrganigramma) {
		this.nroLivelliOrganigramma = nroLivelliOrganigramma;
	}

	public String getTsRif() {
		return tsRif;
	}

	public Record getCopyNode() {
		return copyNode;
	}

	public void setCopyNode(Record copyNode) {
		this.copyNode = copyNode;
	}
	
	public Record getCutNode() {
		return cutNode;
	}

	public void setCutNode(Record cutNode){
		this.cutNode = cutNode;
	}
	
	public Record getCutNodePostazione() {
		return cutNodePostazione;
	}

	public void setCutNodePostazione(Record cutNodePostazione) {
		this.cutNodePostazione = cutNodePostazione;
	}

	public Record getPasteNode() {
		return pasteNode;
	}
	
	public void setPasteNode(Record pasteNode) {
		this.pasteNode = pasteNode;
	}

	public int getNroLivelloCorrente() {
		try {
			return Integer.parseInt(navigator.getCurrentNode().getAltriAttributi().get("nroLivello"));
		} catch (Exception e) {
			return 0;
		}
	}

	public String getCodRapidoUoCorrente() {
		try {
			return navigator.getCurrentNode().getAltriAttributi().get("codRapidoUo");
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isAttivoGestionePuntiProtocollo() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_PUNTI_PROT");
	}
	
	public static boolean isInibitaPreimpDestProtEntrata() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_PREIMP_DEST_UO_PROT_IN_PROT_E");
	}

	public static boolean isAbilitaUoProtUscitaFull() {
		return AurigaLayout.getParametroDBAsBoolean("RICH_ABIL_UO_PROT_USCITA");
	}

	
	public static boolean isAbilitaUoScansioneMassiva() {
		return AurigaLayout.getParametroDBAsBoolean("RICH_ABIL_SCANSIONE_MASSIVA");
	}
	
	public static boolean isAttivaGestioneUfficioGare() {
		return AurigaLayout.getParametroDBAsBoolean("ATTIVA_GEST_UFF_GARE");
	}
	
	public boolean getFlgMostraSVDefaultValueLookup() {
		return true;
	}
	private void associaPuntiProtocolloMultiButtonClick() {
		String title = I18NUtil.getMessages().organigramma_list_associaPuntiProtocolloPopup_title();
		gestisciPuntiProtocolloPopup = new GestisciPuntiProtocolloPopup(null, title) {

			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {
				Layout.showWaitPopup(I18NUtil.getMessages().organigramma_gestionePuntiProtocolloWaitPooup_message());
				List<String> listaPuntiProtocollo = getListaUoPuntiProtocolloSelezionati();
				if ((listaPuntiProtocollo != null) && (listaPuntiProtocollo.size() > 0)) {
					markForDestroy();
					final List<String> listaIdUo = getListaIdUoSelezionate();
					final RecordList listaRecordUo = getListaRecordUoSelezionate();
					if ((listaIdUo != null) && (listaIdUo.size() > 0)) {
						associaPuntiProtocolloMassivo(listaIdUo, listaRecordUo, listaPuntiProtocollo);
					} else {
						Layout.hideWaitPopup();
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().organigramma_list_listaUoVuota_error(), "", MessageType.WARNING));
					}
				} else {
					Layout.hideWaitPopup();
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().organigramma_list_listaPuntiProtocolloVuota_error(), "", MessageType.WARNING));
				}
			}
		};
		gestisciPuntiProtocolloPopup.show();
	}

	private void scollegaPuntiProtocolloMultiButtonClick() {
		String title = I18NUtil.getMessages().organigramma_list_scollegaPuntiProtocolloPopup_title();
		gestisciPuntiProtocolloPopup = new GestisciPuntiProtocolloPopup(null, title) {

			@Override
			public void onClickOkButton(Record formRecord, DSCallback callback) {
				Layout.showWaitPopup(I18NUtil.getMessages().organigramma_gestionePuntiProtocolloWaitPooup_message());
				List<String> listaPuntiProtocollo = getListaUoPuntiProtocolloSelezionati();
				if ((listaPuntiProtocollo != null) && (listaPuntiProtocollo.size() > 0)) {
					markForDestroy();
					final List<String> listaUo = getListaIdUoSelezionate();
					final RecordList listaRecordUo = getListaRecordUoSelezionate();
					if ((listaUo != null) && (listaUo.size() > 0)) {
						scollegaPuntiProtocolloMassivo(listaUo, listaRecordUo, listaPuntiProtocollo);
					} else {
						Layout.hideWaitPopup();
						Layout.addMessage(new MessageBean(I18NUtil.getMessages().organigramma_list_listaUoVuota_error(), "", MessageType.WARNING));
					}
				} else {
					Layout.hideWaitPopup();
					Layout.addMessage(new MessageBean(I18NUtil.getMessages().organigramma_list_listaPuntiProtocolloVuota_error(), "", MessageType.WARNING));
				}
			}
		};
		gestisciPuntiProtocolloPopup.show();
	}

	private void associaPuntiProtocolloMassivo(final List<String> listaUo, final RecordList listaRecordUo, List<String> listaPuntiProtocollo) {
		Record input = new Record();
		input.setAttribute("idUoList", listaUo.toArray(new String[0]));
		input.setAttribute("idUoPuntiProtocolloList", listaPuntiProtocollo.toArray(new String[0]));
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OrganigrammaDatasource");
		lGwtRestDataSource.executecustom("associaPuntiProtocollo", input, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				MessageBean messageBean = associaScollegaErrorMessage(response, listaRecordUo, "id", "idUo", I18NUtil.getMessages()
						.organigramma_associa_successo(), I18NUtil.getMessages().organigramma_associa_errore_parziale(), I18NUtil.getMessages()
						.organigramma_associa_errore_totale(), I18NUtil.getMessages().organigramma_associa_errore());
				int[] recordsToSelect = null;
				Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
				if (errorMessages != null && errorMessages.size() > 0) {
					recordsToSelect = new int[errorMessages.size()];
					int rec = 0;
					for (int i = 0; i < listaRecordUo.getLength(); i++) {
						Record record = listaRecordUo.get(i);
						if (errorMessages.get(record.getAttribute("idUo")) != null) {
							recordsToSelect[rec++] = list.getRecordIndex(record);
						}
					}
				}
				Layout.addMessage(messageBean);
				Layout.hideWaitPopup();
			}
		});
	}

	private void scollegaPuntiProtocolloMassivo(final List<String> listaUo, final RecordList listaRecordUo, List<String> listaPuntiProtocollo) {
		Record input = new Record();
		input.setAttribute("idUoList", listaUo.toArray(new String[0]));
		input.setAttribute("idUoPuntiProtocolloList", listaPuntiProtocollo.toArray(new String[0]));
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OrganigrammaDatasource");
		lGwtRestDataSource.executecustom("scollegaPuntiProtocollo", input, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				MessageBean messageBean = associaScollegaErrorMessage(response, listaRecordUo, "id", "idUo", I18NUtil.getMessages()
						.organigramma_scollega_successo(), I18NUtil.getMessages().organigramma_scollega_errore_parziale(), I18NUtil.getMessages()
						.organigramma_scollega_errore_totale(), I18NUtil.getMessages().organigramma_scollega_errore());
				int[] recordsToSelect = null;
				Map errorMessages = response.getData()[0].getAttributeAsMap("errorMessages");
				if (errorMessages != null && errorMessages.size() > 0) {
					recordsToSelect = new int[errorMessages.size()];
					int rec = 0;
					for (int i = 0; i < listaRecordUo.getLength(); i++) {
						Record record = listaRecordUo.get(i);
						if (errorMessages.get(record.getAttribute("idUo")) != null) {
							recordsToSelect[rec++] = list.getRecordIndex(record);
						}
					}
				}
				Layout.addMessage(messageBean);
				Layout.hideWaitPopup();
			}
		});
	}

	private List<String> getListaIdUoSelezionate() {
		final List<String> listaUo = new ArrayList<String>();
		if ((list != null) && (list.getSelectedRecord() != null) && (list.getSelectedRecords().length > 0)) {
			for (int i = 0; i < list.getSelectedRecords().length; i++) {
				listaUo.add(list.getSelectedRecords()[i].getAttributeAsString("idUo"));
			}
		}
		return listaUo;
	}

	private RecordList getListaRecordUoSelezionate() {
		final RecordList listaEmail = new RecordList();
		for (int i = 0; i < list.getSelectedRecords().length; i++) {
			listaEmail.add(list.getSelectedRecords()[i]);
		}
		return listaEmail;
	}

	private MessageBean associaScollegaErrorMessage(DSResponse response, RecordList listaRecord, String pkField, String nameField, String successMessage,
			String partialErrorMessage, String completeErrorMessage, String genericErrorMessage) {
		MessageBean messageBean = null;
		String strMessageBean = "";
		if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
			Record data = response.getData()[0];
			boolean storeInError = data.getAttributeAsBoolean("storeInError") != null ? data.getAttributeAsBoolean("storeInError") : false;
			if (storeInError) {
				String storeInErrorMessage = data.getAttribute("storeErrorMessage") != null && !"".equalsIgnoreCase(data.getAttribute("storeErrorMessage")) ? data
						.getAttribute("storeErrorMessage") : genericErrorMessage;
				return new MessageBean(storeInErrorMessage, "", MessageType.ERROR);
			} else {
				Map errorMessages = data.getAttributeAsMap("errorMessages");
				if ((errorMessages != null) && (errorMessages.size() > 0)) {
					RecordList listaErrori = new RecordList();
					Record recordErrore = null;
					if ((listaRecord.getLength() > errorMessages.size()) && (storeInError == false)) {
						strMessageBean = partialErrorMessage != null ? partialErrorMessage : "";
						messageBean = new MessageBean(strMessageBean, "", MessageType.WARNING);
					} else {
						strMessageBean = completeErrorMessage != null ? completeErrorMessage : "";
						messageBean = new MessageBean(strMessageBean, "", MessageType.ERROR);
					}
					for (int i = 0; i < listaRecord.getLength(); i++) {
						Record record = listaRecord.get(i);
						String idUo = record.getAttribute("idUo");
						if (errorMessages.get(idUo) != null) {
							recordErrore = new Record();
							String codiceUO = record.getAttribute("codRapidoUo") != null ? record.getAttribute("codRapidoUo") : "";
							String nomeUO = record.getAttribute("nome") != null ? record.getAttribute("nome") : "";
							String idError = codiceUO + " - " + nomeUO;
							recordErrore.setAttribute("idError", idError);
							recordErrore.setAttribute("descrizione", errorMessages.get(record.getAttribute("idUo")));
							listaErrori.add(recordErrore);
						}
					}
					ErroreMassivoPopup errorePopup = new ErroreMassivoPopup(nomeEntita, "U.O.", listaErrori, listaRecord.getLength(), LARG_POPUP_ERR_MASS,
							ALT_POPUP_ERR_MASS);
					errorePopup.show();
				} else {
					strMessageBean = successMessage != null ? successMessage : "";
					messageBean = new MessageBean(strMessageBean, "", MessageType.INFO);
				}

			}
		}
		return messageBean;
	}
	
	@Override
	public void hideDetailAfterSave() {
		if (getIdNodeToOpen() != null && !"".equals(getIdNodeToOpen())) {
			String[] idNodeArray = new StringSplitterClient(getIdNodeToOpen(), "|*|").getTokens();
			if (idNodeArray != null && idNodeArray.length > 0) {
				for (int i = 0; i < idNodeArray.length; i++) {
					((CustomAdvancedTree) getTree()).reloadSubTreeFrom(idNodeArray[i]);
				}
			}			
			setIdNodeToOpen(null);
		}
		super.hideDetailAfterSave();
	}
	
	public void setIdNodeToOpenByIdFolder(String idFolder, final GenericCallback callback) {
		try {
			if (idFolder != null && !"".equals(idFolder)) {			
				Record record = new Record();
				record.setAttribute("idFolder", idFolder);
				tree.getDataSource().performCustomOperation("getIdNodeByIdFolder", record, new DSCallback() {
	
					@Override
					public void execute(DSResponse response, Object rawData, DSRequest request) {
						if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
							Record data = response.getData()[0];
							setIdNodeToOpen(data.getAttributeAsString("idNode"));
							if(callback != null) callback.execute();
						}
					}
				}, new DSRequest());
			} else {
				if(getIdNodeToOpen() == null) {
					setIdNodeToOpen("/");
				}
				if(callback != null) callback.execute();
			}
		} catch(Exception e) {
			setIdNodeToOpen(null);
			if(callback != null) callback.execute();
		}
	}

	public void aggiungiTogliDestinatariPreferiti(final Record lRecord, final String flgAddDel, final String from) {
	
		final GWTRestDataSource lGwtRestDataSource = new GWTRestDataSource("OrganigrammaTreeDatasource");
		
		lRecord.setAttribute("idDestPref", lRecord.getAttribute("idUoSvUt"));
		lRecord.setAttribute("flgAddDel",  flgAddDel);
		
		Layout.showWaitPopup("Operazione in corso: Attendere...");
		
		lGwtRestDataSource.executecustom("aggiungiTogliDestinatariPreferiti", lRecord, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				Layout.hideWaitPopup();
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {		
						String estremi = lRecord.getAttributeAsString("codRapidoUo")  + " - " + lRecord.getAttributeAsString("descrUoSvUt").replaceAll("\\|", " ");
						if(flgAddDel.equalsIgnoreCase("A")){
							Layout.addMessage(new MessageBean(estremi + " aggiunto ai preferiti", "", MessageType.INFO));
						}
						else{
							Layout.addMessage(new MessageBean(estremi + " tolto dai preferiti", "", MessageType.INFO));
						}
						
						if(from!=null && from.equalsIgnoreCase("TREE")){
							((CustomAdvancedTree) getTree()).reloadSubTreeFrom(lRecord.getAttributeAsString("idNode"));	
						}
						
						if(from!=null && from.equalsIgnoreCase("LIST")){
							reloadListAndSetCurrentRecord(lRecord);
						}
				}
			}
		});
		
	}

	public int getMultiLookupGridSize(){
		int size = multiLookupGrid.getRecordList().getLength();
		return size;
	}
}