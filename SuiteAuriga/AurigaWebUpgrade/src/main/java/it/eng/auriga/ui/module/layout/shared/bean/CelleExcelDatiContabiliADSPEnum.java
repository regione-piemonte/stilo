/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public enum CelleExcelDatiContabiliADSPEnum{
	
	
	MOVIMENTO_E_U ("flgEntrataUscita"),
	ANNO_ESERCIZIO("annoEsercizio"),
	CIG ("codiceCIG"),
	CUP("codiceCUP"),
	CAPITOLO("capitolo"),
	CONTO("conto"),
	OPERA("opera"),
	IMPORTO("importo"),
	NOTE("note"),
	IMPONIBILE("imponibile"),
	;	
	
	private CelleExcelDatiContabiliADSPEnum(String nomeCella) {
		this.nomeCella = nomeCella;
	}

	private String nomeCella;

	public String getNomeCella() {
		return nomeCella;
	}

	public static final CelleExcelDatiContabiliADSPEnum getCellFromName (String nomeCella) {
		for (CelleExcelDatiContabiliADSPEnum lCelleExcelBeneficiariEnum : values()) {
			if (lCelleExcelBeneficiariEnum.getNomeCella().equalsIgnoreCase(nomeCella))
				return lCelleExcelBeneficiariEnum;
		}
		return null;
	}


	/*indice cella è l'indice s*/
	public static CelleExcelDatiContabiliADSPEnum getCellFromIndex (int indiceCella) {

	       if (indiceCella == 0) {
	          return MOVIMENTO_E_U;
	       }
	       else if(indiceCella == 1) {
	    	   return ANNO_ESERCIZIO;
	       }
	       else if(indiceCella == 2) {
	    	   return CIG;
	       }
	       else if(indiceCella == 3) {
	    	   return CUP;
	       }
	       else if(indiceCella == 4) {
	    	   return CAPITOLO;
	       }
	       else if(indiceCella == 5) {
	    	   return CONTO;
	       }
	       else if(indiceCella == 6) {
	    	   return OPERA;
	       }
	       else if(indiceCella == 7) {
	    	   return IMPORTO;
	       }
	       else if(indiceCella == 8) {
	    	   return NOTE;
	       }
	       else if(indiceCella == 9) {
	    	   return IMPONIBILE;
	       }

	       return values()[indiceCella];
	   }
	
	/*indice cella è l'indice s*/
	public static Integer getIndexFromCell (CelleExcelDatiContabiliADSPEnum cell) {

	       if (cell == MOVIMENTO_E_U) {
	          return 0;
	       }
	       else if(cell == ANNO_ESERCIZIO) {
	    	   return 1;
	       }
	       else if(cell == CIG) {
	    	   return 2;
	       }
	       else if(cell == CUP) {
	    	   return 3;
	       }
	       else if(cell == CAPITOLO) {
	    	   return 4;
	       }
	       else if(cell == CONTO) {
	    	   return 5;
	       }
	       else if(cell == OPERA) {
	    	   return 6;
	       }
	       else if(cell == IMPORTO) {
	    	   return 7;
	       }
	       else if(cell == NOTE) {
	    	   return 8;
	       }
	       else if(cell == IMPONIBILE) {
	    	   return 9;
	       }
	       
	       return null;
	   }

}
