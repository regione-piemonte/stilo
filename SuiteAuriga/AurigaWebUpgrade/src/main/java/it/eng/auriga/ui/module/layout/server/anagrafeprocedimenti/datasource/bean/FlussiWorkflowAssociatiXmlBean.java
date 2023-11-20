/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Date;

import it.eng.document.NumeroColonna;
import it.eng.document.TipoData;
import it.eng.document.TipoData.Tipo;

public class FlussiWorkflowAssociatiXmlBean {

	@NumeroColonna(numero = "2")
	private String codTipoFlusso;

	@NumeroColonna(numero = "3")
	private String nomeTipoFlusso;

	@NumeroColonna(numero = "4")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataInizioVld;

	@NumeroColonna(numero = "5")
	@TipoData(tipo = Tipo.DATA_SENZA_ORA)
	private Date dataFineVld;


	public String getCodTipoFlusso() {
		return codTipoFlusso;
	}
	
	public void setCodTipoFlusso(String codTipoFlusso) {
		this.codTipoFlusso = codTipoFlusso;
	}
	
	public String getNomeTipoFlusso() {
		return nomeTipoFlusso;
	}

	public void setNomeTipoFlusso(String nomeTipoFlusso) {
		this.nomeTipoFlusso = nomeTipoFlusso;
	}

	public Date getDataInizioVld() {
		return dataInizioVld;
	}
	
	public void setDataInizioVld(Date dataInizioVld) {
		this.dataInizioVld = dataInizioVld;
	}

	public Date getDataFineVld() {
		return dataFineVld;
	}
	
	public void setDataFineVld(Date dataFineVld) {
		this.dataFineVld = dataFineVld;
	}
	
}
