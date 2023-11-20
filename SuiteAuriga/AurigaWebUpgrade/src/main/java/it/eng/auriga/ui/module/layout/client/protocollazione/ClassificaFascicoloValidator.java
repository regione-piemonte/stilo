/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class ClassificaFascicoloValidator extends CustomValidator{

	private Tipo tipo;
	public enum Tipo{ANNO, NUMERO, NOME, FASCICOLO};

	public ClassificaFascicoloValidator(Tipo tipo){
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
				if (isSetted(lStrNroFascicolo)) {
					return false; 
				} else return true;
			} else return true;
		}
		if (tipo == Tipo.NUMERO){
			lStrNroFascicolo = (String)value;
			if (!isSetted(lStrNroFascicolo)){
				if (isSetted(lStrAnnoFascicolo)) {
					return false; 
				} else return true;
			} else return true;
		}		
		if (tipo == Tipo.FASCICOLO){
			lStrNroSottoFascicolo = (String)value;
			return true;
		}
//		Map<String,String> lMap = new HashMap<String, String>();
//		getFormItem().getForm().clearFieldErrors("annoFascicolo", true);
//		getFormItem().getForm().clearFieldErrors("nroFascicolo", true);
//
//		//Se sono entrambi nulli non faccio niente
//		if (!isSetted(lStrAnno) && !isSetted(lStrNumero) && !isSetted(lStrSottoFascicolo) 
//				&& !isSetted(lStrNome)) return true;
//		if (isSetted(lStrSottoFascicolo)){
//			if (!isSetted(lStrAnno)) {
//				lMap.put("annoFascicolo", "Anno obbligatorio");
//
//			}
//			if (!isSetted(lStrNumero)){
//				lMap.put("nroFascicolo", "Numero obbligatorio");
//			}
//		}
//		if (isSetted(lStrNome)){
//			if (!isSetted(lStrAnno)) {
//				lMap.put("annoFascicolo", "Anno obbligatorio");
//
//			}
//			if (!isSetted(lStrNumero)){
//				lMap.put("nroFascicolo", "Numero obbligatorio");
//			}
//		}
//		if (isSetted(lStrAnno)){
//			if (!isSetted(lStrNumero)){
//				lMap.put("nroFascicolo", "Numero obbligatorio");
//			}
//		}
//		if (isSetted(lStrNumero)){
//			if (!isSetted(lStrAnno)) {
//				lMap.put("annoFascicolo", "Anno obbligatorio");
//
//			}
//		}
		return false;
	}

	private boolean isSetted(String value){
		if (value != null && value.trim().length()>0 && !value.trim().equals("")) return true;
		else return false;
	}

}
