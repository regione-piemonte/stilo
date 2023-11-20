/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.NumberFormatUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.filter.item.AnnoItem;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class DettaglioDatiContabiliSIBDetail extends CustomDetail {
	
	protected ListaDatiContabiliSIBItem gridItem;
	
	protected DynamicForm mDynamicForm;
	
	protected HiddenItem flgEntrataUscita;
	protected HiddenItem tipoDettaglio;
	protected HiddenItem flgValidato;
	protected HiddenItem flgSoggDaPubblicare;
	protected TextItem annoCompetenza;
	protected TextItem numeroDettaglio;
	protected TextItem subNumero;
	protected CheckboxItem isValidato;	
	protected TextItem annoCrono;
	protected TextItem numeroCrono;
	protected TextItem subCrono;	
	protected TextItem importo;
	protected TextAreaItem oggetto;
	protected TextItem codiceCIG;
	protected TextItem codiceCUP;
	protected TextItem codiceGAMIPBM;
	protected TextItem codUnitaOrgCdR;
	protected TextItem desUnitaOrgCdR;
	protected TextItem capitolo;
	protected TextItem articolo;
	protected TextItem numero;
	protected TextItem descrizioneCapitolo;
	protected TextItem liv5PdC;
	protected TextItem descrizionePdC;
	protected TextItem annoEsigibilitaDebito;
	protected DateItem dataEsigibilitaDa;
	protected DateItem dataEsigibilitaA;
	protected DateItem dataScadenzaEntrata;
	protected SelectItem dichiarazioneDL78;
	protected TextItem tipoFinanziamento;
	protected HiddenItem codTipoFinanziamento;
	protected TextItem denominazioneSogg;
	protected TextItem codFiscaleSogg;
	protected TextItem codPIVASogg;
	protected TextItem indirizzoSogg;
	protected TextItem cap;
	protected TextItem localita;
	protected TextItem provincia;
	protected TextItem settore;
	protected TextItem descrizioneSettore;
	
	public DettaglioDatiContabiliSIBDetail(String nomeEntita, ListaDatiContabiliSIBItem gridItem) {
		
		super(nomeEntita);
		
		this.gridItem = gridItem;
		
		mDynamicForm = new DynamicForm();
		mDynamicForm.setWidth100();
		mDynamicForm.setNumCols(18);
		mDynamicForm.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");
		mDynamicForm.setValuesManager(vm);
		mDynamicForm.setWrapItemTitles(false);
//		mDynamicForm.setCellBorder(1);
		
		flgEntrataUscita = new HiddenItem("flgEntrataUscita");
		tipoDettaglio = new HiddenItem("tipoDettaglio");
		flgValidato = new HiddenItem("flgValidato");
		flgSoggDaPubblicare = new HiddenItem("flgSoggDaPubblicare");		
		
		annoCompetenza = new TextItem("annoCompetenza", "");
		annoCompetenza.setWidth(150);
		annoCompetenza.setColSpan(1);
		annoCompetenza.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(tipoDettaglio.getValue() != null && !"".equals((String) tipoDettaglio.getValue())) {
					annoCompetenza.setTitle(buildTipoDettaglioValueMap().get((String)tipoDettaglio.getValue()));
				}				
				if(tipoDettaglio.getValue() != null && "SCP".equals((String) tipoDettaglio.getValue())) {
					return false;
				}
				return true;
			}
		});
		
		numeroDettaglio = new TextItem("numeroDettaglio", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numeroDettaglio_title());
		numeroDettaglio.setWidth(150);
		numeroDettaglio.setColSpan(1);
		numeroDettaglio.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(tipoDettaglio.getValue() != null && "SCP".equals((String) tipoDettaglio.getValue())) {
					return false;
				}
				return true;
			}
		});
		
		subNumero = new TextItem("subNumero", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_subNumero_title());
		subNumero.setWidth(150);
		subNumero.setColSpan(1);
		subNumero.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(tipoDettaglio.getValue() != null && "SCP".equals((String) tipoDettaglio.getValue())) {
					return false;
				}
				return true;
			}
		});
		
		isValidato = new CheckboxItem("isValidato", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgValidato_title());
		isValidato.setColSpan(1);
		isValidato.setWidth("*");
		isValidato.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				isValidato.setValue(flgValidato.getValue() != null && "1".equals((String) flgValidato.getValue())); 
				isValidato.setCanEdit(false);
				return true;
			}
		});
		
		annoCrono = new TextItem("annoCrono", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoCrono_title());
		annoCrono.setWidth(150);
		annoCrono.setColSpan(1);
		annoCrono.setStartRow(true);
		annoCrono.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(tipoDettaglio.getValue() != null && "SCP".equals((String) tipoDettaglio.getValue())) {
					annoCrono.setTitle(buildTipoDettaglioValueMap().get((String)tipoDettaglio.getValue()));
				} else {
					annoCrono.setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoCrono_title());
				}
				return true;
			}
		});
		
		numeroCrono = new TextItem("numeroCrono", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numeroCrono_title());
		numeroCrono.setWidth(150);
		numeroCrono.setColSpan(1);
		numeroCrono.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(tipoDettaglio.getValue() != null && "SCP".equals((String) tipoDettaglio.getValue())) {
					numeroCrono.setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numeroDettaglio_title());
				} else {
					numeroCrono.setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numeroCrono_title());
				}
				return true;
			}
		});
		
		subCrono = new TextItem("subCrono", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_subCrono_title());
		subCrono.setWidth(150);
		subCrono.setColSpan(1);
		subCrono.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(tipoDettaglio.getValue() != null && "SCP".equals((String) tipoDettaglio.getValue())) {
					subCrono.setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_subNumero_title());
				} else {
					subCrono.setTitle(I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_subCrono_title());
				}
				return true;
			}
		});
		
		importo = new TextItem("importo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_importo_title());
		importo.setWidth(150);
		importo.setColSpan(1);
		importo.setStartRow(true);
		importo.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				importo.setValue(NumberFormatUtility.getFormattedValue(importo.getValueAsString()));
				return true;
			}
		});
			
		oggetto = new TextAreaItem("oggetto", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_oggetto_title());
		oggetto.setWidth(630);
		oggetto.setHeight(40);
		oggetto.setColSpan(14);
		oggetto.setStartRow(true);
		
		codiceCIG = new TextItem("codiceCIG", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceCIG_title());
		codiceCIG.setWidth(150);
		codiceCIG.setColSpan(1);
		codiceCIG.setStartRow(true);
		
		codiceCUP = new TextItem("codiceCUP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceCUP_title());
		codiceCUP.setWidth(150);
		codiceCUP.setColSpan(1);
		codiceCUP.setStartRow(true);
		
		codiceGAMIPBM = new TextItem("codiceGAMIPBM", getTitleCodiceGAMIPBM());
		codiceGAMIPBM.setWidth(150);
		codiceGAMIPBM.setColSpan(1);
		codiceGAMIPBM.setStartRow(true);
		
		codUnitaOrgCdR = new TextItem("codUnitaOrgCdR", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_unitaOrgCdR_title());
		codUnitaOrgCdR.setWidth(150);
		codUnitaOrgCdR.setColSpan(1);
		codUnitaOrgCdR.setStartRow(true);
		
		desUnitaOrgCdR = new TextItem("desUnitaOrgCdR");
		desUnitaOrgCdR.setShowTitle(false);
		desUnitaOrgCdR.setWidth(480);
		desUnitaOrgCdR.setColSpan(13);
		
		capitolo = new TextItem("capitolo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_capitolo_title());
		capitolo.setColSpan(1);
		capitolo.setWidth(150);
		capitolo.setStartRow(true);
		
		articolo = new TextItem("articolo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_articolo_title());	
		articolo.setColSpan(1);
		articolo.setWidth(150);

		numero = new TextItem("numero", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_numero_title());
		numero.setColSpan(1);
		numero.setWidth(150);
				
		descrizioneCapitolo = new TextItem("descrizioneCapitolo", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_descrizioneCapitolo_title());
		descrizioneCapitolo.setWidth(630);
		descrizioneCapitolo.setColSpan(14);
		descrizioneCapitolo.setStartRow(true);
		
		liv5PdC = new TextItem("liv5PdC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_liv5PdC_title());
		liv5PdC.setWidth(150);
		liv5PdC.setColSpan(1);
		liv5PdC.setStartRow(true);
		
		descrizionePdC = new TextItem("descrizionePdC");
		descrizionePdC.setShowTitle(false);
		descrizionePdC.setWidth(480);
		descrizionePdC.setColSpan(13);
		
		annoEsigibilitaDebito = new AnnoItem("annoEsigibilitaDebito", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_annoEsigibilitaDebito_title());
		annoEsigibilitaDebito.setWidth(150);
		annoEsigibilitaDebito.setColSpan(1);
		annoEsigibilitaDebito.setStartRow(true);
		annoEsigibilitaDebito.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValue() != null && "U".equals((String) flgEntrataUscita.getValue());
			}
		});

		dataEsigibilitaDa = new DateItem("dataEsigibilitaDa", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataEsigibilitaDa_title());
		dataEsigibilitaDa.setWidth(150);
		dataEsigibilitaDa.setColSpan(1);
		dataEsigibilitaDa.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isListaDatiContabiliSIBContoCapitale()) {
					return false;
				}
				return flgEntrataUscita.getValue() != null && "U".equals((String) flgEntrataUscita.getValue());
			}
		});

		dataEsigibilitaA = new DateItem("dataEsigibilitaA", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataEsigibilitaA_title());
		dataEsigibilitaA.setWidth(150);
		dataEsigibilitaA.setColSpan(1);
		dataEsigibilitaA.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				if(isListaDatiContabiliSIBContoCapitale()) {
					return false;
				}
				return flgEntrataUscita.getValue() != null && "U".equals((String) flgEntrataUscita.getValue());
			}
		});

		dataScadenzaEntrata = new DateItem("dataScadenzaEntrata", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dataScadenzaEntrata_title());
		dataScadenzaEntrata.setWidth(150);
		dataScadenzaEntrata.setColSpan(1);
		dataScadenzaEntrata.setStartRow(true);
		dataScadenzaEntrata.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValue() != null && "E".equals((String) flgEntrataUscita.getValue());
			}
		});
		
		dichiarazioneDL78 = new SelectItem("dichiarazioneDL78", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_dichiarazioneDL78_title());
		dichiarazioneDL78.setValueMap("SI", "NO");
		dichiarazioneDL78.setWidth(150);
		dichiarazioneDL78.setColSpan(1);
		dichiarazioneDL78.setStartRow(true);
		dichiarazioneDL78.setShowIfCondition(new FormItemIfFunction() {
			
			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				return flgEntrataUscita.getValue() != null && "U".equals((String) flgEntrataUscita.getValue());
			}
		});
		
		tipoFinanziamento = new TextItem("tipoFinanziamento", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoFinanziamento_title());
		tipoFinanziamento.setWidth(295);
		tipoFinanziamento.setColSpan(4);
		tipoFinanziamento.setStartRow(true);
		
		codTipoFinanziamento = new HiddenItem("codTipoFinanziamento");
		
		denominazioneSogg = new TextItem("denominazioneSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_denominazioneSogg_title());
		denominazioneSogg.setWidth(295);
		denominazioneSogg.setColSpan(3);
		denominazioneSogg.setStartRow(true);
		
		codFiscaleSogg = new TextItem("codFiscaleSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codFiscaleSogg_title());
		codFiscaleSogg.setWidth(150);
		codFiscaleSogg.setColSpan(1);
		codFiscaleSogg.setStartRow(true);
		
		codPIVASogg = new TextItem("codPIVASogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codPIVASogg_title());
		codPIVASogg.setWidth(150);
		codPIVASogg.setColSpan(1);
		codPIVASogg.setStartRow(true);
		
		indirizzoSogg = new TextItem("indirizzoSogg", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_indirizzoSogg_title());
		indirizzoSogg.setWidth(630);
		indirizzoSogg.setColSpan(14);
		indirizzoSogg.setStartRow(true);
		
		cap = new TextItem("cap", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_cap_title());
		cap.setWidth(150);
		cap.setColSpan(1);
		
		localita = new TextItem("localita", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_localita_title());
		localita.setWidth(630);
		localita.setColSpan(14);
		localita.setStartRow(true);
		
		provincia = new TextItem("provincia", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_provincia_title());
		provincia.setWidth(50);
		provincia.setColSpan(1);			
		
		settore = new TextItem("settore", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_settore_title());
		settore.setWidth(150);
		settore.setColSpan(1);
		settore.setStartRow(true);
		
		descrizioneSettore = new TextItem("descrizioneSettore");
		descrizioneSettore.setShowTitle(false);
		descrizioneSettore.setWidth(480);
		descrizioneSettore.setColSpan(13);
					
		mDynamicForm.setFields(
			flgEntrataUscita, tipoDettaglio, flgValidato, flgSoggDaPubblicare,
			annoCompetenza, numeroDettaglio, subNumero, isValidato,
			annoCrono, numeroCrono, subCrono,
			importo,
			oggetto,
			codiceCIG,
			codiceCUP,
			codiceGAMIPBM,	
			codUnitaOrgCdR, desUnitaOrgCdR,
			capitolo, articolo,	numero,
			descrizioneCapitolo,
			liv5PdC, descrizionePdC,
			annoEsigibilitaDebito, dataEsigibilitaDa, dataEsigibilitaA,			  			
			dataScadenzaEntrata,
			dichiarazioneDL78,
			tipoFinanziamento, codTipoFinanziamento, 
			denominazioneSogg, 
			codFiscaleSogg, 
			codPIVASogg,
			indirizzoSogg, cap, 
			localita, provincia,
			settore, descrizioneSettore
		);
		
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();

		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight100();

		lVLayout.addMember(mDynamicForm);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);
	}
	
	public boolean isListaDatiContabiliSIBCorrente() {
		return gridItem != null && gridItem.isListaDatiContabiliSIBCorrente();
	}
	
	public boolean isListaDatiContabiliSIBContoCapitale() {
		return gridItem != null && gridItem.isListaDatiContabiliSIBContoCapitale();
	}
	
	public String getTitleCodiceGAMIPBM() {
		String label = gridItem != null ? gridItem.getTitleCodiceGAMIPBM() : null;
		return label != null && !"".equals(label) ? label : I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_codiceGAMIPBM_title();
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {
		super.setCanEdit(canEdit);
		annoCompetenza.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		annoCompetenza.setTabIndex(-1);
		numeroDettaglio.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		numeroDettaglio.setTabIndex(-1);		
		subNumero.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		subNumero.setTabIndex(-1);		
		annoCrono.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		annoCrono.setTabIndex(-1);
		numeroCrono.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		numeroCrono.setTabIndex(-1);		
		subCrono.setTextBoxStyle(it.eng.utility.Styles.textItemBold);
		subCrono.setTabIndex(-1);	
	}
	
	public static LinkedHashMap<String, String> buildTipoDettaglioValueMap() {
		LinkedHashMap<String, String> tipoDettaglioValueMap = new LinkedHashMap<String, String>();
		tipoDettaglioValueMap.put("IPG", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_IPG_value()); //impegno
		tipoDettaglioValueMap.put("ACC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_ACC_value()); //accertamento
		tipoDettaglioValueMap.put("VIP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VIP_value()); //variazione di impegno
		tipoDettaglioValueMap.put("VAC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VAC_value()); //variazione di accertamento
		tipoDettaglioValueMap.put("SIP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SIP_value()); //subimpegno
		tipoDettaglioValueMap.put("SAC", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SAC_value()); //subaccertamento
		tipoDettaglioValueMap.put("VSI", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VSI_value()); //variazione di subimpegno
		tipoDettaglioValueMap.put("VSA", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_VSA_value()); //variazione di subaccertamento
		tipoDettaglioValueMap.put("COP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_COP_value()); //cronoprogramma
		tipoDettaglioValueMap.put("SCP", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_tipoDettaglio_SCP_value()); //subcronoprogramma
		return tipoDettaglioValueMap;
	}
	
	public static LinkedHashMap<String, String> buildFlgEntrataUscitaValueMap() {
		LinkedHashMap<String, String> flgEntrataUscitaValueMap = new LinkedHashMap<String, String>();
		flgEntrataUscitaValueMap.put("E", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_E_value());
		flgEntrataUscitaValueMap.put("U", I18NUtil.getMessages().nuovaPropostaAtto2_detail_invioDatiSpesaWindow_flgEntrataUscita_U_value());		
		return flgEntrataUscitaValueMap;
	}
	
}