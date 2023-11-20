/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.validator.RequiredIfFunction;
import com.smartgwt.client.widgets.form.validator.RequiredIfValidator;

import it.eng.utility.ui.module.layout.client.common.ReplicableCanvas;
import it.eng.utility.ui.module.layout.client.common.items.TextItem;

public class IncPrelievoPerRichEsternoCanvas extends ReplicableCanvas {
	
	private TextItem cognomeItem;
	private TextItem nomeItem;
	private TextItem codFiscaleItem;
	private TextItem emailItem;
	private TextItem telefonoItem;	

	private ReplicableCanvasForm mDynamicForm;
	
	@Override
	public void disegna() {		
		
		mDynamicForm = new ReplicableCanvasForm();
		mDynamicForm.setWrapItemTitles(false);
				
		cognomeItem = new TextItem("cognome", "Cognome");
		cognomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				String valueNome = null;
				String valueCodFiscale = null;
				String valueEmail = null;
				String valueTelefono = null;
				if (nomeItem.getValue() != null){
					valueNome = (String) nomeItem.getValue();
				}
				if (codFiscaleItem.getValue() != null){
					valueCodFiscale = (String) codFiscaleItem.getValue();
				}
				if (emailItem.getValue() != null){
					valueEmail = (String) emailItem.getValue();
				}
				if (telefonoItem.getValue() != null){
					valueTelefono = (String) telefonoItem.getValue();
				}
				
				if (((valueNome != null) && (!"".equalsIgnoreCase(valueNome))) || ((valueCodFiscale != null) && (!"".equalsIgnoreCase(valueCodFiscale))) || ((valueEmail != null) && (!"".equalsIgnoreCase(valueEmail))) || ((valueTelefono != null) && (!"".equalsIgnoreCase(valueTelefono)))){
					return true;
				}else{
					return false;
				}
			}
		}));
		
		nomeItem = new TextItem("nome", "Nome");
		nomeItem.setValidators(new RequiredIfValidator(new RequiredIfFunction() {

			@Override
			public boolean execute(FormItem formItem, Object value) {
				String valueCognome = null;
				String valueCodFiscale = null;
				String valueEmail = null;
				String valueTelefono = null;
				if (cognomeItem.getValue() != null){
					valueCognome = (String) cognomeItem.getValue();
				}
				if (codFiscaleItem.getValue() != null){
					valueCodFiscale = (String) codFiscaleItem.getValue();
				}
				if (emailItem.getValue() != null){
					valueEmail = (String) emailItem.getValue();
				}
				if (telefonoItem.getValue() != null){
					valueTelefono = (String) telefonoItem.getValue();
				}
				
				if (((valueCognome != null) && (!"".equalsIgnoreCase(valueCognome))) || ((valueCodFiscale != null) && (!"".equalsIgnoreCase(valueCodFiscale))) || ((valueEmail != null) && (!"".equalsIgnoreCase(valueEmail))) || ((valueTelefono != null) && (!"".equalsIgnoreCase(valueTelefono)))){
					return true;
				}else{
					return false;
				}
			}
		}));
		codFiscaleItem = new TextItem("codFiscale", "Cod fiscale");
		
		emailItem = new TextItem("email", "Email");
		
		emailItem.setStartRow(true);
		
		telefonoItem = new TextItem("tel", "Telefono");
								
		mDynamicForm.setFields(cognomeItem, nomeItem, codFiscaleItem, emailItem, telefonoItem);
		
		mDynamicForm.setNumCols(16);
		mDynamicForm.setColWidths("50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100","50", "100");
		
		addChild(mDynamicForm);

	}	
		
	public Record getFormValuesAsRecord() {
		return mDynamicForm.getValuesAsRecord();	
	}
	
	@Override
	public ReplicableCanvasForm[] getForm() {
		return new ReplicableCanvasForm[]{mDynamicForm};
	}
}
