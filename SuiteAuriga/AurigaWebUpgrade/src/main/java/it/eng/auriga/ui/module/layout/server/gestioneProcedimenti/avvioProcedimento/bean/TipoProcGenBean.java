/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.document.NumeroColonna;

public class TipoProcGenBean {

	@NumeroColonna(numero="1")
	private String idTipoProc;	
	
	@NumeroColonna(numero="2")
	private String nomeTipoProc;
	
	@NumeroColonna(numero="3")
	private String flowTypeId;
	
	public String getIdTipoProc() {
		return idTipoProc;
	}
	
	public void setIdTipoProc(String idTipoProc) {
		this.idTipoProc = idTipoProc;
	}
	
	public String getNomeTipoProc() {
		return nomeTipoProc;
	}
	
	public void setNomeTipoProc(String nomeTipoProc) {
		this.nomeTipoProc = nomeTipoProc;
	}
	
	public String getFlowTypeId() {
		return flowTypeId;
	}
	
	public void setFlowTypeId(String flowTypeId) {
		this.flowTypeId = flowTypeId;
	}		

}
