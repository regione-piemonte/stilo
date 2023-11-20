/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum XVerificaSicurezza {

	ERRORE("errore");
	
	private String value;
	
	private XVerificaSicurezza(String value) {
		this.value = value;
	}
	
	public static XVerificaSicurezza valueOfValue(String name){
		for(XVerificaSicurezza verificasicurezza:XVerificaSicurezza.values()){
			if(verificasicurezza.value.equals(name)){
				return verificasicurezza;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}
}