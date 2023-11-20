/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.CustomDetail;
import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.NumericItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import java.util.LinkedHashMap;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.FormItemValueFormatter;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;
import com.smartgwt.client.widgets.form.fields.SpacerItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

public class TitolarioDetail extends CustomDetail {

	private HiddenItem livelloCorrenteItem;
	private TextItem livelloItem;

	private HiddenItem idPianoClassifItem;
	private HiddenItem idClassificazioneItem;
	private HiddenItem idClassificaSupItem;
	private HiddenItem flgStoricizzaDatiItem;
	private TextItem descrizioneItem;
	private TextItem paroleChiaveItem;
	private DateItem tsValidaDalItem;
	private DateItem tsValidaFinoAlItem;
	private CheckboxItem flgNoRichAbilItem;
	private CheckboxItem flgNumContinuaItem;
	

	private CheckboxItem flgConservPermInItem;
	private NumericItem periodoConservInItem;

	private DynamicForm formLivello;
	private DynamicForm form;

	public TitolarioDetail(String nomeEntita) {

		super(nomeEntita);

		formLivello = new DynamicForm();
		formLivello.setWidth(1);
		formLivello.setValuesManager(vm);
		formLivello.setWrapItemTitles(false);
		formLivello.setOverflow(Overflow.VISIBLE);
		formLivello.setNumCols(4);

		livelloCorrenteItem = new HiddenItem("livelloCorrente");

		final StaticTextItem livelloCorrenteLabel = new StaticTextItem("livelloCorrenteLabel", setTitleAligned(I18NUtil.getMessages()
				.titolario_detail_livello()));
		livelloCorrenteLabel.setStartRow(true);
		livelloCorrenteLabel.setAttribute("obbligatorio", true);
		livelloCorrenteLabel.setAlign(Alignment.RIGHT);
		livelloCorrenteLabel.setWidth(1);
		livelloCorrenteLabel.setWrap(false);
		livelloCorrenteLabel.setValueFormatter(new FormItemValueFormatter() {

			@Override
			public String formatValue(Object value, Record record, DynamicForm form, FormItem item) {
				String livelloCorrente = vm.getValueAsString("livelloCorrente");
				if (livelloCorrente != null && !"".equals(livelloCorrente)) {
					return livelloCorrente.replace(".", "&nbsp;.&nbsp;") + " .";
				} else {
					return "";
				}
			}
		});
		livelloCorrenteLabel.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				String livelloCorrente = vm.getValueAsString("livelloCorrente");
				if (livelloCorrente != null && !"".equals(livelloCorrente)) {
					livelloItem.setShowTitle(false);
					livelloItem.setColSpan(1);
					return true;
				} else {
					livelloItem.setShowTitle(true);
					livelloItem.setColSpan(2);
					return false;
				}
			}
		});

		// LIVELLO CORRENTE DA CONCATENARE AI PRECEDENTI
		livelloItem = new TextItem("livello", setTitleAligned(I18NUtil.getMessages().titolario_detail_livello()));
		livelloItem.setRequired(true);
		livelloItem.setWidth(100);

		formLivello.setFields(livelloCorrenteItem, livelloCorrenteLabel, livelloItem);

		addSubForm(formLivello);

		form = new DynamicForm();
		form.setValuesManager(vm);
		form.setWrapItemTitles(false);
		form.setNumCols(12);
		form.setColWidths(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, "*", "*");

		idPianoClassifItem = new HiddenItem("idPianoClassif");
		idClassificazioneItem = new HiddenItem("idClassificazione");
		idClassificaSupItem = new HiddenItem("idClassificaSup");
		flgStoricizzaDatiItem = new HiddenItem("flgStoricizzaDati");

		descrizioneItem = new TextItem("descrizione", setTitleAligned(I18NUtil.getMessages().titolario_detail_descrizione()));
		descrizioneItem.setRequired(true);
		descrizioneItem.setStartRow(true);
		descrizioneItem.setWidth(650);
		descrizioneItem.setColSpan(11);

		paroleChiaveItem = new TextItem("paroleChiave", setTitleAligned(I18NUtil.getMessages().titolario_detail_paroleChiave()));
		paroleChiaveItem.setStartRow(true);
		paroleChiaveItem.setWidth(650);
		paroleChiaveItem.setColSpan(11);

		tsValidaDalItem = new DateItem("tsValidaDal", setTitleAligned(I18NUtil.getMessages().titolario_detail_tsValidaDal()));
		tsValidaDalItem.setStartRow(true);
		tsValidaDalItem.setRequired(true);

		tsValidaFinoAlItem = new DateItem("tsValidaFinoAl", I18NUtil.getMessages().titolario_detail_tsValidaFinoAl());
		tsValidaFinoAlItem.setStartRow(false);

		SpacerItem spacerItem = new SpacerItem();
		spacerItem.setStartRow(true);
		spacerItem.setColSpan(1);

		LinkedHashMap<String, Boolean> flgValueMap = new LinkedHashMap<String, Boolean>();
		flgValueMap.put("1", true);
		flgValueMap.put("0", false);
		flgValueMap.put("", false);
		flgValueMap.put(null, false);

		flgConservPermInItem = new CheckboxItem("flgConservPermIn", I18NUtil.getMessages().titolario_detail_flgConservPermIn());
		flgConservPermInItem.setValueMap(flgValueMap);
		flgConservPermInItem.setColSpan(1);
		// flgConservPermInItem.setStartRow(true);
		flgConservPermInItem.setWidth(10);
		flgConservPermInItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				form.redraw();
			}
		});

		periodoConservInItem = new NumericItem("periodoConservAnni", I18NUtil.getMessages().titolario_detail_periodoConservIn());
		periodoConservInItem.setColSpan(1);
		periodoConservInItem.setShowIfCondition(new FormItemIfFunction() {

			@Override
			public boolean execute(FormItem item, Object value, DynamicForm form) {
				
				Boolean flgConservIllimitata = form.getValue("flgConservPermIn") != null && (Boolean) form.getValue("flgConservPermIn");
				return flgConservIllimitata == null || !flgConservIllimitata;
			}
		});

		flgNoRichAbilItem = new CheckboxItem("flgNoRichAbil", I18NUtil.getMessages().titolario_detail_flgNoRichAbil());
		flgNoRichAbilItem.setValue(false);
		flgNoRichAbilItem.setColSpan(1);
		flgNoRichAbilItem.setWidth(10);

		
		flgNumContinuaItem = new CheckboxItem("flgNumContinua", I18NUtil.getMessages().titolario_detail_flgNumContinua());
		flgNumContinuaItem.setValue(false);
		flgNumContinuaItem.setColSpan(10);
		flgNumContinuaItem.setWidth(10);
		
		form.setFields(
				       // hidden
				       idPianoClassifItem, 
				       idClassificazioneItem, 
				       idClassificaSupItem, 
				       flgStoricizzaDatiItem,
				       
				       // visibili
				       descrizioneItem, 
				       paroleChiaveItem,
				       tsValidaDalItem, 
				       tsValidaFinoAlItem, 
				       spacerItem, flgConservPermInItem, 
				       periodoConservInItem, 
				       flgNoRichAbilItem,
				       spacerItem, flgNumContinuaItem
				       );

		addSubForm(form);

	}

	private String setTitleAligned(String title) {
		return "<span style=\"width: 150px; display: inline-block;\">" + title + "</span>";
	}

}
