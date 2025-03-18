/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import com.hyperborea.sira.ws.CostNostId;

public class OstPopulateEngineBeanIn {
	
	private String mainLabel;

	private String idProcObjType;

	private CostNostId id;

	public String getMainLabel() {
		return mainLabel;
	}

	public void setMainLabel(String mainLabel) {
		this.mainLabel = mainLabel;
	}

	public String getIdProcObjType() {
		return idProcObjType;
	}

	public void setIdProcObjType(String idProcObjType) {
		this.idProcObjType = idProcObjType;
	}

	public CostNostId getId() {
		return id;
	}

	public void setId(CostNostId id) {
		this.id = id;
	}

}
