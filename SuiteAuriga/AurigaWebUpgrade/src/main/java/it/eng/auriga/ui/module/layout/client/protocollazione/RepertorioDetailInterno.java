/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.auriga.ui.module.layout.client.postaElettronica.DettaglioRispostaProtWindow;
import it.eng.utility.ui.module.core.client.datasource.GWTRestDataSource;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

/**
 * Maschera di REPERTORIO
 * 
 * @author Mattia Zanin
 *
 */

public abstract class RepertorioDetailInterno extends ProtocollazioneDetailInterna {

	protected RepertorioDetailInterno _instance;

	private CheckboxItem annoPassatoItem;

	public RepertorioDetailInterno(String nomeEntita) {

		super(nomeEntita);

		_instance = this;
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
		return "R";
	}

	@Override
	public String getIconAltraNumerazione() {
		return "protocollazione/repertorio_interno.png";
	}

	@Override
	public String getTitleAltraNumerazione() {
		return "Repertorio";
	}

	@Override
	public boolean showDetailSectionNuovaRegistrazione() {
		return true;
	}

	@Override
	public String getTitleDetailSectionNuovaRegistrazione() {
		return I18NUtil.getMessages().protocollazione_detail_repertorio_title();
	}

	public boolean showAnnoPassatoItem() {
		return false;
	}

	@Override
	protected void createNuovaRegistrazioneForm() {

		super.createNuovaRegistrazioneForm();

		GWTRestDataSource gruppiRepertorioDS = new GWTRestDataSource("LoadComboGruppiRepertorioSource", "key", FieldType.TEXT);
		gruppiRepertorioDS.addParam("flgTipoProv", getFlgTipoProv());

		repertorioItem = new SelectItem("repertorio", I18NUtil.getMessages().protocollazione_detail_repertorioItem_title());
		repertorioItem.setValueField("key");
		repertorioItem.setDisplayField("value");
		repertorioItem.setOptionDataSource(gruppiRepertorioDS);
		repertorioItem.setWidth(200);
		repertorioItem.setClearable(true);
		repertorioItem.setCachePickListResults(false);
		repertorioItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return (!mode.equals("edit"));
			}
		});

		annoPassatoItem = new CheckboxItem("annoPassato", I18NUtil.getMessages().protocollazione_detail_annoPassatoItem_title());
		annoPassatoItem.setColSpan(1);
		annoPassatoItem.setWidth(10);
		annoPassatoItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {

				return (!mode.equals("edit")) && showAnnoPassatoItem();

			}
		});
		annoPassatoItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				nuovaRegistrazioneForm.markForRedraw();
			}
		});

		nuovaRegistrazioneForm.setFields(repertorioItem, annoPassatoItem, protocolloGeneraleItem);
	}

	@Override
	public Map<String, Object> getNuovaProtInitialValues() {
		Map<String, Object> initialValues = super.getNuovaProtInitialValues();
		if(initialValues == null) {
			initialValues = new HashMap<String, Object>();
		}
		String tipoProtocollo = (String) vm.getValues().get("tipoProtocollo");
		if (tipoProtocollo != null && tipoProtocollo.equalsIgnoreCase("PG")) {
			initialValues.put("repertorio", (String) vm.getValues().get("siglaNumerazioneSecondaria"));
		} else {
			initialValues.put("repertorio", (String) vm.getValues().get("siglaProtocollo"));
		}
		return initialValues.size() > 0 ? initialValues : null;
	}

	@Override
	public void setCanEdit(boolean canEdit) {

		super.setCanEdit(canEdit);
		repertorioItem.setCanEdit(false);
	}
	
	@Override
	public void manageOnClickRispondi() {
		
		final Record detailRecord = new Record(vm.getValues());
		final String idUd = detailRecord.getAttribute("idUd");
		
		final Record recordToShow = new Record();
		recordToShow.setAttribute("idUd", idUd);
		recordToShow.setAttribute("repertorio", detailRecord.getAttribute("registroProtocollo"));
		//controllo se il protocollo interno è abilitato alla risposta
		if(detailRecord.getAttributeAsBoolean("abilRispondiUscita")) {
			SC.ask("Sono presenti destinatari esterni nella risposta?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value) {
						recordToShow.setAttribute("tipoProt", "E");
					}
					else {
						recordToShow.setAttribute("tipoProt", "I");
					}
					new DettaglioRispostaProtWindow(recordToShow);
				}
			});
		}else {
			recordToShow.setAttribute("tipoProt", "I");
			new DettaglioRispostaProtWindow(recordToShow);
		}
	}
	
}
