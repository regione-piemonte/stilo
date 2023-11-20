/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum Categoria {

	PEC("PEC"),
	INTER_SEGN("INTEROP_SEGN"),
	INTER_ECC("INTEROP_ECC"),
	INTER_CONF("INTEROP_CONF"),
	INTER_AGG("INTEROP_AGG"),
	INTER_ANN("INTEROP_ANN"),
	ANOMALIA("ANOMALIA"),
	DELIVERY_STATUS("DELIVERY_STATUS_NOT"),
	PEC_RIC_ACC("PEC_RIC_ACC"),
	PEC_RIC_NO_ACC("PEC_RIC_NO_ACC"),
	PEC_RIC_PRESA_C("PEC_RIC_PRESA_C"),
	PEC_RIC_CONS("PEC_RIC_CONS"),
	PEC_RIC_PREAVV_NO_CONS("PEC_RIC_PREAVV_NO_CONS"),
	PEC_RIC_NO_CONS("PEC_RIC_NO_CONS"),
	CONFERMA_LETTURA("PEO_RIC_CONF"),
	CONFERMA_LETTURA_PEC("PEC_RIC_CONF"),
	ALTRO("ALTRO");
	
	private String value;
	
	private Categoria(String value) {
		this.value = value;
	}
	
	public static Categoria valueOfValue(String name){
		for(Categoria stato:Categoria.values()){
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
