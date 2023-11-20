/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.Map;


public class PostaElettronicaBean extends PostaElettronicaXmlBean {
	
	private String uriEmailSenzaBustaPEC;
	private boolean flgRilascia;
	
	private Map<String, String> estremiRegProtAssociati;
	private AzioneDaFareBean azioneDaFareBean;
	
	private SceltaDestinatarioBean recordFiltriDestinatari;
	private SceltaDestinatarioBean recordFiltriDestinatariCC;
	private SceltaDestinatarioBean recordFiltriDestinatariCCN;
				
	public String getUriEmailSenzaBustaPEC() {
		return uriEmailSenzaBustaPEC;
	}
	
	public void setUriEmailSenzaBustaPEC(String uriEmailSenzaBustaPEC) {
		this.uriEmailSenzaBustaPEC = uriEmailSenzaBustaPEC;
	}

	public Map<String, String> getEstremiRegProtAssociati() {
		return estremiRegProtAssociati;
	}
	
	public void setEstremiRegProtAssociati(
			Map<String, String> estremiRegProtAssociati) {
		this.estremiRegProtAssociati = estremiRegProtAssociati;
	}
	
	public boolean isFlgRilascia() {
		return flgRilascia;
	}
	public void setFlgRilascia(boolean flgRilascia) {
		this.flgRilascia = flgRilascia;
	}
	
	public AzioneDaFareBean getAzioneDaFareBean() {
		return azioneDaFareBean;
	}
	
	public void setAzioneDaFareBean(AzioneDaFareBean azioneDaFareBean) {
		this.azioneDaFareBean = azioneDaFareBean;
	}

	public SceltaDestinatarioBean getRecordFiltriDestinatari() {
		return recordFiltriDestinatari;
	}

	public void setRecordFiltriDestinatari(SceltaDestinatarioBean recordFiltriDestinatari) {
		this.recordFiltriDestinatari = recordFiltriDestinatari;
	}

	public SceltaDestinatarioBean getRecordFiltriDestinatariCC() {
		return recordFiltriDestinatariCC;
	}

	public void setRecordFiltriDestinatariCC(SceltaDestinatarioBean recordFiltriDestinatariCC) {
		this.recordFiltriDestinatariCC = recordFiltriDestinatariCC;
	}

	public SceltaDestinatarioBean getRecordFiltriDestinatariCCN() {
		return recordFiltriDestinatariCCN;
	}

	public void setRecordFiltriDestinatariCCN(SceltaDestinatarioBean recordFiltriDestinatariCCN) {
		this.recordFiltriDestinatariCCN = recordFiltriDestinatariCCN;
	}

}