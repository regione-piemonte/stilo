/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;
import java.io.Serializable;

public class ReportTrasparenzaAmministrativaFiltriXmlBean implements Serializable {
	
	@XmlVariabile(nome="DataDa", tipo=TipoVariabile.SEMPLICE)
	private String dataDa;
	
	@XmlVariabile(nome="DataA", tipo=TipoVariabile.SEMPLICE)
	private String dataA;
		
	@XmlVariabile(nome="DataRif", tipo=TipoVariabile.SEMPLICE)
	private String dataRif;
	
	
	@XmlVariabile(nome="IdSezione", tipo=TipoVariabile.SEMPLICE)
	private String idSezione;


	public String getDataDa() {
		return dataDa;
	}


	public void setDataDa(String dataDa) {
		this.dataDa = dataDa;
	}


	public String getDataA() {
		return dataA;
	}


	public void setDataA(String dataA) {
		this.dataA = dataA;
	}


	public String getDataRif() {
		return dataRif;
	}


	public void setDataRif(String dataRif) {
		this.dataRif = dataRif;
	}


	public String getIdSezione() {
		return idSezione;
	}


	public void setIdSezione(String idSezione) {
		this.idSezione = idSezione;
	}
	
		

}