package it.eng.suiteutility.module.mimedb.enums;
import java.io.Serializable;

/**
 * Indica lo stato del formato in IRIS: 
 * AMMESSO IN CONSERVAZIONE (=A), 
 * NON AMMESSO (=NA), 
 * AMMESSO SOLO IN PREARCHIVIO (=P). 
 * Il fatto che sia AMMESSO non implica che un soggetto versatore possa 
 * utilizzarlo a meno che non lo dichiari esplicitamente AMMESSO; 
 * invece il fatto che sia NON AMMESSO o AMMESSO SOLO IN PRE-ARCHIVIO 
 * fa si che per nessun soggetto versatore possa essere ammesso (se non eventualmente in PRE-ARCHIVIO)
 * @author upescato
 *
 */

public enum FlagStato implements Serializable {

	AMMESSO_IN_CONSERVAZIONE("A"),
	NON_AMMESSO("NA"),
	AMMESSO_SOLO_IN_PREARCHIVIO("P");

	private final String statoFormato;

	FlagStato(String statoFormato){
		this.statoFormato=statoFormato;
	}

	public String type(){
		return this.statoFormato;
	}
}
