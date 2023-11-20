/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.TextAreaItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class RichiesteDocViaPecDetail extends CustomDetail {

	private DynamicForm form;
	private HiddenItem idItem;
	private HiddenItem xmlRichiestaItem;
	private TextItem idRichiestaItem;
	private TextItem codApplRichItem;
	private TextAreaItem descApplRichItem;
	private TextItem tipoDocInvItem;
	private TextItem decodificaTipoDocInvItem;
	private TextItem statoRichiestaItem;
	private TextItem errorMessageItem;
	private DateItem dtSottRichItem;
	private DateItem dtInvioEmailItem;

	public RichiesteDocViaPecDetail(String nomeEntita) {

		super(nomeEntita);

		initValues();

		// LAYOUT MAIN
		VLayout lVLayout = new VLayout();
		lVLayout.setWidth100();
		lVLayout.setHeight(50);
		
		VLayout lVLayoutSpacer = new VLayout();
		lVLayoutSpacer.setWidth100();
		lVLayoutSpacer.setHeight(50);

		lVLayout.addMember(form);

		addMember(lVLayout);
		addMember(lVLayoutSpacer);

	}

	private void initValues() {
		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWrapItemTitles(false);
		form.setNumCols(15);
		form.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		idItem = new HiddenItem("id");

		xmlRichiestaItem = new HiddenItem("xmlRichiesta");

		idRichiestaItem = new TextItem("idRichiesta", I18NUtil.getMessages().invio_documentazione_via_pec_list_idrichiesta());
		idRichiestaItem.setWrapTitle(false);
		idRichiestaItem.setColSpan(1);

		codApplRichItem = new TextItem("codApplRich", I18NUtil.getMessages().invio_documentazione_via_pec_list_applrich());
		codApplRichItem.setWrapTitle(false);
		codApplRichItem.setColSpan(1);

		descApplRichItem = new TextAreaItem("descApplRich", I18NUtil.getMessages().invio_documentazione_via_pec_list_descrizioneappl());
		descApplRichItem.setStartRow(true);
		descApplRichItem.setLength(4000);
		descApplRichItem.setHeight(40);
		descApplRichItem.setWidth(600);
		descApplRichItem.setColSpan(10);

		tipoDocInvItem = new TextItem("tipoDocInv", I18NUtil.getMessages().invio_documentazione_via_pec_list_tipodocinv());
		tipoDocInvItem.setStartRow(true);
		tipoDocInvItem.setWrapTitle(false);
		tipoDocInvItem.setColSpan(1);

		decodificaTipoDocInvItem = new TextItem("decodificaTipoDocInv", I18NUtil.getMessages().invio_documentazione_via_pec_list_decodificatipodoc());
		decodificaTipoDocInvItem.setWrapTitle(false);
		decodificaTipoDocInvItem.setColSpan(1);

		statoRichiestaItem = new TextItem("statoRichiesta", I18NUtil.getMessages().invio_documentazione_via_pec_list_statorich());
		statoRichiestaItem.setWrapTitle(false);
		statoRichiestaItem.setStartRow(true);
		statoRichiestaItem.setColSpan(1);

		errorMessageItem = new TextItem("errorMessage", I18NUtil.getMessages().invio_documentazione_via_pec_list_errormessage());
		errorMessageItem.setWrapTitle(false);
		errorMessageItem.setColSpan(1);

		dtSottRichItem = new DateItem("dtSottRich", I18NUtil.getMessages().invio_documentazione_via_pec_list_richdel());
		dtSottRichItem.setColSpan(1);
		dtSottRichItem.setStartRow(true);

		dtInvioEmailItem = new DateItem("dtInvioEmail", I18NUtil.getMessages().invio_documentazione_via_pec_list_emailinvil());
		dtInvioEmailItem.setColSpan(1);

		form.setFields(idItem, xmlRichiestaItem, idRichiestaItem, codApplRichItem, descApplRichItem, tipoDocInvItem, decodificaTipoDocInvItem,
				statoRichiestaItem, errorMessageItem, dtSottRichItem, dtInvioEmailItem);
	}
}
