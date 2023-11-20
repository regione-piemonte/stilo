/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.google.gwt.regexp.shared.RegExp;
import com.smartgwt.client.types.CharacterCasing;
import com.smartgwt.client.widgets.form.fields.events.BlurEvent;
import com.smartgwt.client.widgets.form.fields.events.BlurHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

import it.eng.auriga.ui.module.layout.client.RegExpUtility;
import it.eng.auriga.ui.module.layout.client.i18n.I18NUtil;
import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class ContraenteCanvas extends ReplicableCanvas{
	
	private TextItem denominazioneItem;
	private TextItem codiceFiscaleItem;
	private TextItem partitaIvaItem;
	
	private ReplicableCanvasForm replicableCanvasForm;

	@Override
	public void disegna() {
		
		replicableCanvasForm = new ReplicableCanvasForm();
		replicableCanvasForm.setWrapItemTitles(false);
		
		CustomValidator validatorValoriContraente = new CustomValidator() {
			@Override
			protected boolean condition(Object value) {

				String name = getFormItem().getName();				
				if (isBlank(value)) {
					boolean valid = true;
					if("denominazione".equals(name)) {
						valid = valid && isBlank(codiceFiscaleItem.getValue()) && isBlank(partitaIvaItem.getValue());
					} else if("codiceFiscale".equals(name)) {
						valid = valid && (isBlank(denominazioneItem.getValue()) || !isBlank(partitaIvaItem.getValue()));
					} else if("partitaIva".equals(name)) {
						valid = valid && (isBlank(denominazioneItem.getValue()) || !isBlank(codiceFiscaleItem.getValue()));
					}					
					return valid;
				} else return true;
			}	
		};
		
		CustomValidator checkCodiceFiscale = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if(!isBlank(codiceFiscaleItem.getValue())){
					RegExp regExp = RegExp.compile(RegExpUtility.codiceFiscaleRegExp());
					return regExp.test((String) value);
				}
				else 
					return true;
			}
		};
		
		CustomValidator checkPartitaIva = new CustomValidator() {
			
			@Override
			protected boolean condition(Object value) {
				if (!isBlank(partitaIvaItem.getValue())){
					RegExp regExp = RegExp.compile(RegExpUtility.partitaIvaRegExp());
					return regExp.test((String) value);
				}
				else 
					return true;
			}
		};
		
		denominazioneItem = new TextItem("denominazione",I18NUtil.getMessages().archivio_contratto_denominazione_cognome_nome());
		denominazioneItem.setColSpan(1);
		denominazioneItem.setWidth(180);
		denominazioneItem.setRequired(true);
		denominazioneItem.setValidators(validatorValoriContraente);
		denominazioneItem.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				
				codiceFiscaleItem.validate();
				partitaIvaItem.validate();
				denominazioneItem.validate();	
				
			}
		});
		
		codiceFiscaleItem = new TextItem("codiceFiscale",I18NUtil.getMessages().archivio_contratto_codice_fiscale());
		codiceFiscaleItem.setColSpan(1);
		codiceFiscaleItem.setWidth(150);
		codiceFiscaleItem.setLength(16);
		codiceFiscaleItem.setWrapTitle(false);	
		codiceFiscaleItem.setCharacterCasing(CharacterCasing.UPPER);
		codiceFiscaleItem.setValidators(validatorValoriContraente, checkCodiceFiscale);
		
		codiceFiscaleItem.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				codiceFiscaleItem.validate();
				partitaIvaItem.validate();
				denominazioneItem.validate();			
			}
		});
		
		partitaIvaItem = new TextItem("partitaIva",I18NUtil.getMessages().archivio_contratto_partita_iva());
		partitaIvaItem.setWidth(150);		
		partitaIvaItem.setLength(11);
		partitaIvaItem.setValidators(validatorValoriContraente, checkPartitaIva);
		
		partitaIvaItem.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				codiceFiscaleItem.validate();
				partitaIvaItem.validate();
				denominazioneItem.validate();			
			}
		});
		
		replicableCanvasForm.setFields(denominazioneItem, codiceFiscaleItem, partitaIvaItem);	
		
		replicableCanvasForm.setNumCols(10);
		
		addChild(replicableCanvasForm);
		
	}

	@Override
	public ReplicableCanvasForm[] getForm() {
		
		return new ReplicableCanvasForm[]{replicableCanvasForm};
		
	}
	
	private boolean isBlank(Object value) {
		return (value == null || ((value instanceof String) && "".equals(value.toString().trim())));		
	}	

}