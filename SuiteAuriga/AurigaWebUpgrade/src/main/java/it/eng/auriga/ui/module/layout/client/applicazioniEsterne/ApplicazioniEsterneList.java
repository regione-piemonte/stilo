/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * 
 * @author ottavio passalcqua
 *
 */
public class ApplicazioniEsterneList extends CustomList {

	private ListGridField codApplicazioneField;
	private ListGridField codIstanzaField;
	private ListGridField nomeField;
	private ListGridField dtCensimentoField;
	private ListGridField utenteCensimentoField;
	private ListGridField dtUltimoAggiornamentoField;
	private ListGridField utenteUltimoAggiornamentoField;
	private ListGridField flgUsaCredenzialiDiverseField;
	private ListGridField validoField;
	private ListGridField idApplEsternaField;
	
	public ApplicazioniEsterneList(String nomeEntita) {
		super(nomeEntita);
	
		codApplicazioneField           = new ListGridField("codApplicazione",           I18NUtil.getMessages().applicazioni_esterne_codApplicazione_list());
		codIstanzaField                = new ListGridField("codIstanza",                I18NUtil.getMessages().applicazioni_esterne_codIstanza_list());
		nomeField                      = new ListGridField("nome",                      I18NUtil.getMessages().applicazioni_esterne_nome_list());
		flgUsaCredenzialiDiverseField  = buildFlagIconField("flgUsaCredenzialiDiverse", I18NUtil.getMessages().applicazioni_esterne_flgUsaCredenzialiDiverse_list(), I18NUtil.getMessages().applicazioni_esterne_flgUsaCredenzialiDiverse_list(), "true", "ok.png");
		validoField                    = buildFlagIconField("valido",                   I18NUtil.getMessages().applicazioni_esterne_valido_list(),                   I18NUtil.getMessages().applicazioni_esterne_valido_list(),                   "true", "ok.png");
		dtCensimentoField              = new ListGridField("dtCensimento",              I18NUtil.getMessages().applicazioni_esterne_dtCensimento_list()); dtCensimentoField.setType(ListGridFieldType.DATE); dtCensimentoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		utenteCensimentoField          = new ListGridField("utenteCensimento",          I18NUtil.getMessages().applicazioni_esterne_utenteCensimento_list());
		dtUltimoAggiornamentoField     = new ListGridField("dtUltimoAggiornamento",     I18NUtil.getMessages().applicazioni_esterne_dtUltimoAggiornamento_list()); dtUltimoAggiornamentoField.setType(ListGridFieldType.DATE); dtUltimoAggiornamentoField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		utenteUltimoAggiornamentoField = new ListGridField("utenteUltimoAggiornamento", I18NUtil.getMessages().applicazioni_esterne_utenteUltimoAggiornamento_list());
		idApplEsternaField             = new ListGridField("idApplEsterna"); idApplEsternaField.setHidden(true); idApplEsternaField.setCanHide(false);
		
		setFields( codApplicazioneField, 
				   codIstanzaField, 
				   nomeField,
				   flgUsaCredenzialiDiverseField,
				   validoField,
				   dtCensimentoField,
				   utenteCensimentoField,
				   dtUltimoAggiornamentoField,
				   utenteUltimoAggiornamentoField,
				   idApplEsternaField
				);
	}

	@Override
	public void manageLoadDetail(final Record record, final int recordNum, final DSCallback callback) {
		getDataSource().performCustomOperation("get", record, new DSCallback() {

			@Override
			public void execute(DSResponse response, Object rawData, DSRequest request) {
				if (response.getStatus() == DSResponse.STATUS_SUCCESS) {
					Record record = response.getData()[0];
					layout.getDetail().editRecord(record, recordNum);
					layout.getDetail().getValuesManager().clearErrors(true);
					callback.execute(response, null, new DSRequest());
				}
			}
		}, new DSRequest());
	}

	/******************************** NUOVA GESTIONE CONTROLLI BOTTONI ********************************/
	@Override
	public boolean isDisableRecordComponent() {
		
		return true;
	};

	@Override
	protected boolean showDetailButtonField() {
		
		return true;
	}

	@Override
	protected boolean showModifyButtonField() {
		
		return ApplicazioniEsterneLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		
		return ApplicazioniEsterneLayout.isAbilToDel();
	}

	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		return ApplicazioniEsterneLayout.isRecordAbilToMod(flgDiSistema);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		final boolean flgValido = record.getAttribute("valido") != null && record.getAttribute("valido").equals("true") ? true : false;
		return ApplicazioniEsterneLayout.isRecordAbilToDel(flgValido, flgDiSistema);
	}

	/******************************** FINE NUOVA GESTIONE CONTROLLI BOTTONI ********************************/

	public ListGridField buildFlagIconField(final String name, final String title, final String hover, final String trueValue, final String icon) {
		
		ListGridField flagIconField = new ListGridField(name, title);
		flagIconField.setAlign(Alignment.CENTER);
		flagIconField.setAttribute("custom", true);
		flagIconField.setShowHover(true);
		flagIconField.setType(ListGridFieldType.ICON);
		flagIconField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (value != null && value.toString().equals(trueValue)) {
					return buildIconHtml(icon);
				}
				return null;
			}
		});
		flagIconField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				
				String realValue = record.getAttribute(name);
				if (realValue != null && realValue.equals(trueValue)) {
					return hover;
				}
				return null;
			}
		});
		return flagIconField;
	}	
}