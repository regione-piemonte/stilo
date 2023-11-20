/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

/**
 * Maschera di REGISTRO FATTURE
 * 
 * @author Mattia Zanin
 *
 */

public class RegistroFattureDetail extends ProtocollazioneDetailEntrata {

	protected RegistroFattureDetail instance;
	
	private TextItem repertorioItem;

	public RegistroFattureDetail(String nomeEntita) {

		super(nomeEntita);

		instance = this;
	}
	
	@Override
	public boolean isModalitaWizard() {
		return false;
	}

	@Override
	public String getTitleUoProtocollanteSelectItem() {
		return "<b>U.O. di registrazione</b>";
	}
	
	@Override
	public String getTitleProtocolloGeneraleItem() {
		return "registra anche a Protocollo Generale";
	}

	@Override
	public boolean isAltraNumerazione() {
		return true;
	}

	@Override
	public String getCategoriaAltraNumerazione() {
		return "I";
	}

	@Override
	public String getSiglaAltraNumerazione() {
		return "FATTURE";
	}

	@Override
	public String getIconAltraNumerazione() {
		return "protocollazione/invoice.png";
	}

	@Override
	public String getTitleAltraNumerazione() {
		return "Registro fattura";
	}

	@Override
	public boolean showDetailSectionNuovaRegistrazione() {
		return true;
	}

	@Override
	public String getTitleDetailSectionNuovaRegistrazione() {
		return I18NUtil.getMessages().protocollazione_detail_registrofatture_title();
	}
	
	@Override
	protected void createNuovaRegistrazioneForm() {

		super.createNuovaRegistrazioneForm();

		repertorioItem = new TextItem("repertorio", I18NUtil.getMessages().protocollazione_detail_repertorioItem_title());
		repertorioItem.setValueField("key");
		repertorioItem.setDisplayField("value");
		repertorioItem.setWidth(200);
		repertorioItem.setDefaultValue("FATTURE");
		repertorioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return (!mode.equals("edit"));
			}
		});

		nuovaRegistrazioneForm.setFields(repertorioItem, protocolloGeneraleItem);
	}
	
	@Override
	public void setCanEdit(boolean canEdit) {

		super.setCanEdit(canEdit);
		repertorioItem.setCanEdit(false);
	}

}
