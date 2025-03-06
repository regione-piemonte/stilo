package it.eng.suiteutility.module.mimedb.enums;
import java.math.BigDecimal;

/**
 * Indica se e' un formato: 2 = sempre indicizzabile; 
 * 1 = indicizzabile ma non sempre (puo' essere sia testuale che binario); 
 * 0 = mai indicizzabile in quanto sempre binario
 * 
 * 
 */
public enum FlagIndicizzabile {

	SEMPRE_INDICIZZABILE(new BigDecimal(2)),
	INDICIZZABILE(new BigDecimal(1)),
	MAI_INDICIZZABILE(new BigDecimal(0));

	private final BigDecimal indicizzabile;

	FlagIndicizzabile(BigDecimal indicizzabile){
		this.indicizzabile=indicizzabile;
	}

	public BigDecimal type(){
		return this.indicizzabile;
	}
	
}
