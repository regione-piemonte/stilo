/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */


public enum Profilo {
	
	MITTENTE("mittente"),
	DESTINATARIO_PER_COMPETENZA("destinatario_per_competenza"),
	SMISTATORE("smistatore");
	
	private String value;
	
	private Profilo(String value) {
		this.value = value;
	}
	
	public static Profilo valueOfValue(String name){
		for(Profilo stato:Profilo.values()){
			if(stato.value.equals(name)){
				return stato;
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}

}
