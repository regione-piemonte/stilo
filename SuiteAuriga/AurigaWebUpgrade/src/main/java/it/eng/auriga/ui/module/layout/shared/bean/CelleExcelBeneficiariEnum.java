/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public enum CelleExcelBeneficiariEnum{
		
	TIPO_PERSONA("tipoPersona"),
	NOME("nome"),
	COGNOME("cognome"),
	RAGIONE_SOCIALE("ragioneSociale"),
	CF_PIVA("codFiscalePIVA"),
	IMPORTO("importo"),
	PROT_PRIVACY("flgPrivacy"),
	TIPO("tipo"),	
	;
	
	
	private CelleExcelBeneficiariEnum(String nomeCella) {
		this.nomeCella = nomeCella;
	}

	private String nomeCella;

	public String getNomeCella() {
		return nomeCella;
	}

	public static final CelleExcelBeneficiariEnum getCellFromName (String nomeCella) {
		for (CelleExcelBeneficiariEnum lCelleExcelBeneficiariEnum : values()) {
			if (lCelleExcelBeneficiariEnum.getNomeCella().equalsIgnoreCase(nomeCella))
				return lCelleExcelBeneficiariEnum;
		}
		return null;
	}

	/*indice cella è l'indice s*/
	public static CelleExcelBeneficiariEnum getCellFromIndex (int indiceCella) {

	       if (indiceCella == 0) {
	          return TIPO_PERSONA;
	       }
	       else if(indiceCella == 1) {
	    	   return NOME;
	       }
	       else if(indiceCella == 2) {
	    	   return COGNOME;
	       }
	       else if(indiceCella == 3) {
	    	   return RAGIONE_SOCIALE;
	       }
	       else if(indiceCella == 4) {
	    	   return CF_PIVA;
	       }
	       else if(indiceCella == 5) {
	    	   return IMPORTO;
	       }
	       else if(indiceCella == 6) {
	    	   return PROT_PRIVACY;
	       }
	       else if(indiceCella == 7) {
	    	   return TIPO;
	       }

	       return values()[indiceCella];
	   }
	
	/*indice cella è l'indice s*/
	public static Integer getIndexFromCell (CelleExcelBeneficiariEnum cell) {

	       if (cell == TIPO_PERSONA) {
	          return 0;
	       }
	       else if(cell == NOME) {
	    	   return 1;
	       }
	       else if(cell == COGNOME) {
	    	   return 2;
	       }
	       else if(cell == RAGIONE_SOCIALE) {
	    	   return 3;
	       }
	       else if(cell == CF_PIVA) {
	    	   return 4;
	       }
	       else if(cell == IMPORTO) {
	    	   return 5;
	       }
	       else if(cell == PROT_PRIVACY) {
	    	   return 6;
	       }
	       else if(cell == TIPO) {
	    	   return 7;
	       }
	       
	       return null;
	   }

}
