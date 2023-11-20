/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;
import java.util.LinkedHashMap;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.validator.CustomValidator;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;

import it.eng.auriga.ui.module.layout.client.PieChartUtility;
import it.eng.utility.ui.module.layout.client.common.items.DateItem;
import it.eng.utility.ui.module.layout.client.common.items.SelectItem;

public class StatisticheProtocolloLayout extends VLayout {

	private DynamicForm _form;
	private StatisticheProtocolloWindow _window;

	public StatisticheProtocolloLayout(StatisticheProtocolloWindow pStatisticheProtocolloWindow) {

		_window = pStatisticheProtocolloWindow;

		setWidth100();
		setHeight100();
		setOverflow(Overflow.VISIBLE);
		
		_form = new DynamicForm();
		_form.setKeepInParentRect(true);
		_form.setWidth100();
		_form.setHeight100();
		_form.setNumCols(6);
		_form.setColWidths(150, 100, 20, 100, 50, "*");
		_form.setWrapItemTitles(false);

		SelectItem lSelectItem = new SelectItem("tipoReport", "Tipo di report");
		lSelectItem.setRequired(true);
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("registrazioni_a_protocollo_effettuate", "Registrazioni a Protocollo Effettuate");
		lLinkedHashMap.put("assegnazioni_eseguite", "Assegnazioni eseguite");
		lLinkedHashMap.put("assegnazioni_senza_presa_in_carico", "Assegnazioni senza prese in carico");
		lLinkedHashMap.put("tempi_medi_tra_assegnazione_e_presa_in_carico", "Tempi medi tra assegnazione e presa in carico");
		lSelectItem.setValueMap(lLinkedHashMap);
		lSelectItem.setWidth(270);
		lSelectItem.setColSpan(4);

		final DateItem lDateItemDa = new DateItem("da", "Periodo dal");
		lDateItemDa.setWidth(100);
		lDateItemDa.setRequired(true);
		CustomValidator lCustomValidatorDa = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null)
					return true;
				Date lDateA = (Date) _form.getValue("a");
				if (lDateA == null)
					return true;
				else {
					Date lDateDa = (Date) value;
					long timeDa = lDateDa.getTime();
					long timeA = lDateA.getTime();
					return timeA >= timeDa;
				}
			}
		};
		lCustomValidatorDa.setErrorMessage("La data di fine periodo deve essere maggiore di quella iniziale");
		lDateItemDa.setValidators(lCustomValidatorDa);
		lDateItemDa.setValidateOnChange(true);

		final DateItem lDateItemA = new DateItem("a", "al");
		lDateItemA.setWidth(100);
		lDateItemA.setRequired(true);
		CustomValidator lCustomValidatorA = new CustomValidator() {

			@Override
			protected boolean condition(Object value) {
				if (value == null)
					return true;
				Date lDateDa = (Date) _form.getValue("da");
				if (lDateDa == null)
					return true;
				else {
					Date lDateA = (Date) value;
					long timeDa = lDateDa.getTime();
					long timeA = lDateA.getTime();
					return timeA >= timeDa;
				}
			}
		};
		lCustomValidatorA.setErrorMessage("La data di fine periodo deve essere maggiore di quella iniziale");
		lDateItemA.setValidators(lCustomValidatorA);
		lDateItemA.setValidateOnChange(true);

		SelectItem lSelectItemTipoReg = new SelectItem("tipoDiRegistrazione", "Tipo di registrazione");
		lSelectItemTipoReg.setStartRow(true);
		lSelectItemTipoReg.setWidth(100);
		LinkedHashMap<String, String> lLinkedHashMapTipoReg = new LinkedHashMap<String, String>();
		lLinkedHashMapTipoReg.put("E", "In entrata");
		lLinkedHashMapTipoReg.put("U", "In uscita");
		lLinkedHashMapTipoReg.put("I", "Interne");
		lSelectItemTipoReg.setValueMap(lLinkedHashMapTipoReg);
		lSelectItemTipoReg.setMultiple(true);

		_form.setFields(lSelectItem, lDateItemDa, lDateItemA, lSelectItemTipoReg);

		Button stampaButton = new Button("Genera report");
		stampaButton.setIcon("ok.png");
		stampaButton.setIconSize(16);
		stampaButton.setAutoFit(false);
		stampaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (_form.validate()) {
					Date lDateDa = (Date) _form.getValue("da");
					Date lDateA = (Date) _form.getValue("a");
					String tipoReport = _form.getValueAsString("tipoReport");
					String tipoDiRegistrazione = _form.getValueAsString("tipoDiRegistrazione");
					PieChartUtility.pieChart(lDateDa, lDateA, tipoReport, tipoDiRegistrazione, null);
				}
			}
		});

		Button visualizzaButton = new Button("Visualizza dati");
		visualizzaButton.setIcon("buttons/view.png");
		visualizzaButton.setIconSize(16);
		visualizzaButton.setAutoFit(false);
		visualizzaButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (_form.validate()) {
					new VisualizzaDatiReportWindow(_form.getValuesAsRecord());
				}
			}
		});

		HStack _buttons = new HStack(5);
		_buttons.setName("buttons");
		_buttons.setHeight(30);
		_buttons.setAlign(Alignment.CENTER);
		_buttons.setPadding(5);
		_buttons.addMember(stampaButton);
		_buttons.addMember(visualizzaButton);
		
		setAlign(Alignment.CENTER);
		setTop(50);

		VLayout layout = new VLayout();
		layout.setHeight100();
		layout.setWidth100();
		layout.setOverflow(Overflow.AUTO);
		
		layout.addMember(_form);

		addMember(layout);
		addMember(_buttons);
	}

	public DynamicForm getForm() {
		return _form;
	}
}