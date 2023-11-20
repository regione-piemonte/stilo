/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;
																					

public class ProfiliXmlBean
{
  
  @NumeroColonna(numero="1")
  private String idProfilo;
	
  @NumeroColonna(numero="2")
  private String nomeProfilo;
  

public String getIdProfilo() {
	return idProfilo;
}

public void setIdProfilo(String idProfilo) {
	this.idProfilo = idProfilo;
}

public String getNomeProfilo() {
	return nomeProfilo;
}

public void setNomeProfilo(String nomeProfilo) {
	this.nomeProfilo = nomeProfilo;
}

        
}
