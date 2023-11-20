/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum XTrasporto {

	POSTA_CERTIFICATA("posta-certificata"),
	ERRORE("errore");
	
	private String value;
	
	private XTrasporto(String value) {
		this.value = value;
	}
	
	public static XTrasporto valueOfValue(String name){
		for(XTrasporto trasporto:XTrasporto.values()){
			if(trasporto.value.equals(name)){
				return trasporto;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
}