/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.LinkedHashMap;

import it.eng.utility.ui.module.layout.client.common.items.CheckboxItem;
import it.eng.utility.ui.module.layout.client.common.items.StaticTextItem;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.ValuesManager;
import com.smartgwt.client.widgets.form.fields.BlurbItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;

public class EtichettaForm extends DynamicForm {

	private BlurbItem registrazione;
	private TextItem sigla;
	private TextItem anno;
	private TextItem numero;
	private RadioGroupItem formatoData;
	private CheckboxItem includiDestinatariEsterni;
	private SpinnerItem numeroCopie;
	
	public EtichettaForm() {
		initRegistrazione();
		initSigla();
		initAnno();
		initNumero();
		initFormatoData();
		initIncludiDestinatariEsterni();
		initNumeroCopie();
		setWidth(480);
		setNumCols(8);
		setColWidths(50, 50, 50, 80, 50, 80, 100, "*");
		setFields(registrazione, sigla, 
				anno,
				numero, formatoData,
				includiDestinatariEsterni, numeroCopie);
	}

	private void initNumeroCopie() {
		numeroCopie = new SpinnerItem("numeroCopie", "Num. Copie");
		numeroCopie.setStep(1);
		numeroCopie.setWidth(50);
		numeroCopie.setColSpan(3);
		numeroCopie.setRejectInvalidValueOnChange(true);
		numeroCopie.setKeyPressFilter("[0-9]");
	}

	private void initIncludiDestinatariEsterni() {
		includiDestinatariEsterni = new CheckboxItem("includiDestinatariEsterni", "Stampa destinatari esterni");
		includiDestinatariEsterni.setColSpan(3);
		includiDestinatariEsterni.setWidth(150);
	}

	private void initFormatoData() {
		formatoData = new RadioGroupItem("formatoData", "Formato data");
		LinkedHashMap<String, String> lLinkedHashMap = new LinkedHashMap<String, String>();
		lLinkedHashMap.put("gma", "Data di Protocollo formato gg/mm/aaaa");
		lLinkedHashMap.put("oreMin", "Data di Protocollo formato con Ore e min.");
		formatoData.setValueMap(lLinkedHashMap);
		formatoData.setColSpan(6);
		
	}

	private void initNumero() {
		numero = new TextItem("numero", "Numero");
		numero.setWidth(80);
		numero.setCanEdit(false);
	}

	private void initAnno() {
		anno = new TextItem("anno", "Anno");
		anno.setWidth(50);
		anno.setCanEdit(false);
	}

	private void initSigla() {
		sigla = new TextItem("sigla", "Sigla");
		sigla.setWidth(50);
		sigla.setCanEdit(false);
	}

	private void initRegistrazione() {
		registrazione = new BlurbItem("registrazione");
		registrazione.setEndRow(false);
		registrazione.setColSpan(1);
		registrazione.setWidth(50);
		registrazione.setValue("Registrazione: ");
		
	}
	
}
