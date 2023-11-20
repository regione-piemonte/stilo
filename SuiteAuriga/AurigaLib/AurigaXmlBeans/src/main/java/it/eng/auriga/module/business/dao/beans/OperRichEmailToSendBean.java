/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Federico Cacco
 */

@XmlRootElement
public class OperRichEmailToSendBean extends AbstractBean implements java.io.Serializable {

	private static final long serialVersionUID = -6184855262228046057L;

	private String idOperRichEmailToSend;

	// Appiattisco attributo TRichEmailToSend
	private String idRichiesta;

	private String opertype;
	private String stato;
	private String errMsg;
	private String result;
	private BigDecimal tryNum;
	private Date tsIns;
	private Date tsUltimoAgg;

	public String getIdOperRichEmailToSend() {
		return idOperRichEmailToSend;
	}

	public void setIdOperRichEmailToSend(String idOperRichEmailToSend) {
		this.idOperRichEmailToSend = idOperRichEmailToSend;
		this.getUpdatedProperties().add("idOperRichEmailToSend");
	}

	public String getIdRichiesta() {
		return idRichiesta;
	}

	public void setIdRichiesta(String idRichiesta) {
		this.idRichiesta = idRichiesta;
		this.getUpdatedProperties().add("idRichiesta");
	}

	public String getOpertype() {
		return opertype;
	}

	public void setOpertype(String opertype) {
		this.opertype = opertype;
		this.getUpdatedProperties().add("opertype");
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
		this.getUpdatedProperties().add("stato");
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		this.getUpdatedProperties().add("errMsg");
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
		this.getUpdatedProperties().add("result");
	}

	public BigDecimal getTryNum() {
		return tryNum;
	}

	public void setTryNum(BigDecimal tryNum) {
		this.tryNum = tryNum;
		this.getUpdatedProperties().add("tryNum");
	}

	public Date getTsIns() {
		return tsIns;
	}

	public void setTsIns(Date tsIns) {
		this.tsIns = tsIns;
		this.getUpdatedProperties().add("tsIns");
	}

	public Date getTsUltimoAgg() {
		return tsUltimoAgg;
	}

	public void setTsUltimoAgg(Date tsUltimoAgg) {
		this.tsUltimoAgg = tsUltimoAgg;
		this.getUpdatedProperties().add("tsUltimoAgg");
	}

}
