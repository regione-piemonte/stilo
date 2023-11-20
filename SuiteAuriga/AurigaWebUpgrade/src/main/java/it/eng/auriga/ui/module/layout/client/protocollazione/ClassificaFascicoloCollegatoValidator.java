/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class ClassificaFascicoloCollegatoValidator extends CustomValidator{

	private Tipo tipo;
	public enum Tipo{ANNO, NUMERO, NOME, FASCICOLO};

	public ClassificaFascicoloCollegatoValidator(Tipo tipo){
		this.tipo = tipo;
	}
	@Override
	protected boolean condition(Object value) { 
		String lStrAnnoFascicolo = (String) getFormItem().getForm().getField("annoFascicolo").getValue();
		String lStrNroFascicolo = (String) getFormItem().getForm().getField("nroFascicolo").getValue();
		String lStrNroSottoFascicolo = (String) getFormItem().getForm().getField("nroSottofascicolo").getValue();
		String lStrNomeFascicolo = (String)getFormItem().getForm().getField("nomeFascicolo").getValue();
		if (tipo == Tipo.ANNO){
			lStrAnnoFascicolo = (String)value;
			if (!isSetted(lStrAnnoFascicolo)){
				return false; 				
			} else return true;
		}
		if (tipo == Tipo.NUMERO){
			lStrNroFascicolo = (String)value;
			if (!isSetted(lStrNroFascicolo)){
				return false;
			} else return true;
		}		
		if (tipo == Tipo.FASCICOLO){
			lStrNroSottoFascicolo = (String)value;
			return true;
		}
		return false;
	}

	private boolean isSetted(String value){
		if (value != null && value.trim().length()>0 && !value.trim().equals("")) return true;
		else return false;
	}

}
