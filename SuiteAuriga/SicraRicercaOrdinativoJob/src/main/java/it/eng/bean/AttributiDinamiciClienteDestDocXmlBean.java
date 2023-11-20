/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.List;

import it.eng.document.XmlVariabile;
import it.eng.document.XmlVariabile.TipoVariabile;

public class AttributiDinamiciClienteDestDocXmlBean {
	
	@XmlVariabile(nome="LINGUA", tipo=TipoVariabile.SEMPLICE)
	private String idLingua;

	@XmlVariabile(nome="MOD_REG_DEST_DOC", tipo=TipoVariabile.SEMPLICE)
	private String metodoRegistrazione;

	@XmlVariabile(nome="CLIENTE_DEST_DOC", tipo = TipoVariabile.LISTA)
	private List<ClienteDestDocXmlBean> clienteDestDoc;
	
	@XmlVariabile(nome="CANALI_TRASM_X_CLUSTER", tipo = TipoVariabile.LISTA)
	private List<CanaliTrasmissioneClusterXmlBean> canaliTrasmissioneCluster;

	public String getIdLingua() {
		return idLingua;
	}

	public void setIdLingua(String idLingua) {
		this.idLingua = idLingua;
	}

	public String getMetodoRegistrazione() {
		return metodoRegistrazione;
	}

	public void setMetodoRegistrazione(String metodoRegistrazione) {
		this.metodoRegistrazione = metodoRegistrazione;
	}

	public List<ClienteDestDocXmlBean> getClienteDestDoc() {
		return clienteDestDoc;
	}

	public void setClienteDestDoc(List<ClienteDestDocXmlBean> clienteDestDoc) {
		this.clienteDestDoc = clienteDestDoc;
	}

	public List<CanaliTrasmissioneClusterXmlBean> getCanaliTrasmissioneCluster() {
		return canaliTrasmissioneCluster;
	}

	public void setCanaliTrasmissioneCluster(List<CanaliTrasmissioneClusterXmlBean> canaliTrasmissioneCluster) {
		this.canaliTrasmissioneCluster = canaliTrasmissioneCluster;
	}

}
