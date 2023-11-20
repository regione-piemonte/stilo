/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ListCreaModDocumentoInBean implements Serializable {

	public enum TipoOperazione { CREA, MODIFICA}
	private static final long serialVersionUID = -1834496248072315122L;
	private List<CreaModDocumentoInBean> documenti;
	private List<TipoOperazione> operazioni;
	private List<BigDecimal> idUds;
	private List<BigDecimal> idDocPrimari;

	public List<CreaModDocumentoInBean> getDocumenti() {
		return documenti;
	}

	public void setDocumenti(List<CreaModDocumentoInBean> documenti) {
		this.documenti = documenti;
	}

	public List<TipoOperazione> getOperazioni() {
		return operazioni;
	}

	public void setOperazioni(List<TipoOperazione> operazioni) {
		this.operazioni = operazioni;
	}

	public List<BigDecimal> getIdUds() {
		return idUds;
	}

	public void setIdUds(List<BigDecimal> idUds) {
		this.idUds = idUds;
	}

	public List<BigDecimal> getIdDocPrimari() {
		return idDocPrimari;
	}

	public void setIdDocPrimari(List<BigDecimal> idDocPrimari) {
		this.idDocPrimari = idDocPrimari;
	}
}
