/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.TipoData;
import it.eng.document.XmlVariabile;
import java.io.Serializable;
import java.util.Date;


public class FiltriPubblicazioneAlboConsultazioneRichiesteBean implements Serializable
{
  
  @XmlVariabile(nome="DtRichiestaDa", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
  private Date dtRichiestaDa;
  
  @XmlVariabile(nome="DtRichiestaA", tipo=XmlVariabile.TipoVariabile.SEMPLICE)
  @TipoData(tipo=TipoData.Tipo.DATA_SENZA_ORA)
  private Date dtRichiestaA;

public Date getDtRichiestaDa() {
	return dtRichiestaDa;
}

public void setDtRichiestaDa(Date dtRichiestaDa) {
	this.dtRichiestaDa = dtRichiestaDa;
}

public Date getDtRichiestaA() {
	return dtRichiestaA;
}

public void setDtRichiestaA(Date dtRichiestaA) {
	this.dtRichiestaA = dtRichiestaA;
}
  
   
}