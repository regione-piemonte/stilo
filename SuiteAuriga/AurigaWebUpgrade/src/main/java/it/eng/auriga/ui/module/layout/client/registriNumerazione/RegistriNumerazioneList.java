/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

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
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomList;

public class RegistriNumerazioneList extends CustomList {
	
	// Nascoste
	private ListGridField idTipoRegNumField;
	private ListGridField flgSistemaField;
	
	// Visibili
	private ListGridField desCategoriaField;
	private ListGridField siglaTipoRegNumField;
	private ListGridField gruppiRegistriAppartenenzaField;
	private ListGridField flgInternaField;
	private ListGridField descStatoRegistroField;
	private ListGridField nrAnniRinnovaNumerazioneField;
	private ListGridField nrUltimaRegField;
	private ListGridField dtUltimaRegField;
	private ListGridField stringListaDocRegistrabiliField;
	private ListGridField flgValidoField;
	private ListGridField dtCreazioneField;
	private ListGridField desUteCreazioneField;
	private ListGridField dtUltimaModificaField;
	private ListGridField desUteUltimaModField;
	
	private boolean registriAbilitati;
	
	public RegistriNumerazioneList(String nomeEntita, String registriAbilitati) {
		
		super(nomeEntita);
		
		this.registriAbilitati = registriAbilitati != null && "1".equals(registriAbilitati) ? true : false;
		
		// Nascoste
		idTipoRegNumField  = new ListGridField("idTipoRegNum"); idTipoRegNumField.setHidden(true);  idTipoRegNumField.setCanHide(false); idTipoRegNumField.setCanSort(true); idTipoRegNumField.setCanEdit(false); idTipoRegNumField.setCanGroupBy(false);
		flgSistemaField    = new ListGridField("flgSistema");   flgSistemaField.setHidden(true);    flgSistemaField.setCanHide(false);   flgSistemaField.setCanSort(true);   flgSistemaField.setCanEdit(false);   flgSistemaField.setCanGroupBy(false);
		
		// Visibili
		desCategoriaField                = new ListGridField("desCategoria",               I18NUtil.getMessages().registri_numerazione_list_desCategoria());
		siglaTipoRegNumField             = new ListGridField("siglaTipoRegNum",            I18NUtil.getMessages().registri_numerazione_list_siglaTipoRegNum());
		gruppiRegistriAppartenenzaField  = new ListGridField("gruppiRegistriAppartenenza", I18NUtil.getMessages().registri_numerazione_list_gruppiRegistriAppartenenza());
		descStatoRegistroField           = new ListGridField("descStatoRegistro",          I18NUtil.getMessages().registri_numerazione_list_descStatoRegistro());
		nrAnniRinnovaNumerazioneField    = new ListGridField("nrAnniRinnovaNumerazione",   I18NUtil.getMessages().registri_numerazione_list_nrAnniRinnovaNumerazione());
		nrUltimaRegField                 = new ListGridField("nrUltimaReg",                I18NUtil.getMessages().registri_numerazione_list_nrUltimaReg());  nrUltimaRegField.setCanSort(true); nrUltimaRegField.setCanGroupBy(false);
		dtUltimaRegField                 = new ListGridField("dtUltimaReg",                I18NUtil.getMessages().registri_numerazione_list_dtUltimaReg()); dtUltimaRegField.setType(ListGridFieldType.DATE); dtUltimaRegField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		stringListaDocRegistrabiliField  = new ListGridField("stringListaDocRegistrabili", I18NUtil.getMessages().registri_numerazione_list_stringListaDocRegistrabili());
		dtCreazioneField                 = new ListGridField("dtCreazione",                I18NUtil.getMessages().registri_numerazione_list_dtCreazione()); dtCreazioneField.setType(ListGridFieldType.DATE); dtCreazioneField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATETIME);
		desUteCreazioneField             = new ListGridField("desUteCreazione",            I18NUtil.getMessages().registri_numerazione_list_desUteCreazione());
		dtUltimaModificaField            = new ListGridField("dtUltimaModifica",           I18NUtil.getMessages().registri_numerazione_list_dtUltimaModifica()); dtUltimaModificaField.setType(ListGridFieldType.DATE); dtUltimaModificaField.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);
		desUteUltimaModField             = new ListGridField("desUteUltimaMod",            I18NUtil.getMessages().registri_numerazione_list_desUteUltimaMod());

		flgInternaField                  = setIconFlgField("flgInterna", I18NUtil.getMessages().registri_numerazione_list_flgInterna(), "Interna", "Esterna",    "true", "false",  "protocollazione/flagSoggettiGruppo/I.png" , "protocollazione/flagSoggettiGruppo/E.png");
		flgValidoField                   = setIconFlgField("flgValido",  I18NUtil.getMessages().registri_numerazione_list_flgValido(),  "Valido",  "Non Valido", "true", "false",  "ok.png", "postaElettronica/statoConsolidamento/ko.png");
		

		setFields(// Nascoste
				  idTipoRegNumField,
				  flgSistemaField,
				 
				  // Visibili
				  desCategoriaField, 
				  siglaTipoRegNumField, 
				  gruppiRegistriAppartenenzaField, 
				  flgInternaField,
				  descStatoRegistroField, 
				  nrAnniRinnovaNumerazioneField,				  
				  nrUltimaRegField, 
				  dtUltimaRegField, 
				  stringListaDocRegistrabiliField, 
				  flgValidoField,
				  dtCreazioneField, 
				  desUteCreazioneField, 
				  dtUltimaModificaField, 
				  desUteUltimaModField);
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
	
	@Override  
	protected int getButtonsFieldWidth() {
		return 100;
	}
	
	/********************************NUOVA GESTIONE CONTROLLI BOTTONI********************************/
	@Override
	public boolean isDisableRecordComponent() {
		return true;
	};
	
	@Override
	protected boolean showDetailButtonField() {
		return false;
	}

	@Override
	protected boolean showModifyButtonField() {
		return RegistriNumerazioneLayout.isAbilToMod();
	}

	@Override
	protected boolean showDeleteButtonField() {
		return RegistriNumerazioneLayout.isAbilToDel();
	}
	
	@Override
	protected boolean isRecordAbilToMod(ListGridRecord record) {
		final boolean flgDiSistema = record.getAttribute("flgSistema") != null && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		return RegistriNumerazioneLayout.isRecordAbilToMod(flgDiSistema);
	}

	@Override
	protected boolean isRecordAbilToDel(ListGridRecord record) {
		final boolean flgSistema = record.getAttribute("flgSistema") != null   && record.getAttributeAsString("flgSistema").equals("1") ? true : false;
		final boolean flgValido  = record.getAttribute("flgValido")  != null   && record.getAttributeAsString("flgValido").equals("1")  ? true : false;
		return RegistriNumerazioneLayout.isRecordAbilToDel(flgValido, flgSistema);
	}	
	/********************************FINE NUOVA GESTIONE CONTROLLI BOTTONI********************************/	
	
	public ListGridField setIconFlgField(final String name, final String title, final String hoverTrue, final String hoverFalse, final String trueValue, final String falseValue, final String iconTrue,final String iconFalse) {
		ListGridField flagIconField = new ListGridField(name, title);
		flagIconField.setAlign(Alignment.CENTER);
		flagIconField.setAttribute("custom", true);
		flagIconField.setShowHover(true);
		flagIconField.setType(ListGridFieldType.ICON);
		flagIconField.setCellFormatter(new CellFormatter() {

			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				String iconName = "blank.png";
				if (value != null) {
					if (value.toString().equals(trueValue)) {
						iconName = iconTrue;	
					}
					if (value.toString().equals(falseValue)) {
						iconName = iconFalse;	
					}
				}
				return buildIconHtml(iconName);
				
			}
		});
		flagIconField.setHoverCustomizer(new HoverCustomizer() {

			@Override
			public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
				String realValue = record.getAttribute(name);
				if (realValue != null && realValue.equals(trueValue)) {
					return hoverTrue;
				}
				else if(!realValue.equals(trueValue)) {
					return hoverFalse;
				}
				return null;
			}
		});
		return flagIconField;
	}

	public boolean isListaRegistriAbilitati() {
		return registriAbilitati;
	}
}