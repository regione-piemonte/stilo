/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import it.eng.core.business.beans.AbstractBean;

/**
 * bean di notifica interoperabile
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class NotificaInteroperabileBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 8593467427244223687L;

	private SenderBean senderBean;

	private TEmailMgoBean mailPartenza;

	private String xmlNotifica;

	private TipoInteroperabilita tipoNotifica;

	public SenderBean getSenderBean() {
		return senderBean;
	}

	public void setSenderBean(SenderBean senderBean) {
		this.senderBean = senderBean;
	}

	public TEmailMgoBean getMailPartenza() {
		return mailPartenza;
	}

	public void setMailPartenza(TEmailMgoBean mailPartenza) {
		this.mailPartenza = mailPartenza;
	}

	public String getXmlNotifica() {
		return xmlNotifica;
	}

	public void setXmlNotifica(String xmlNotifica) {
		this.xmlNotifica = xmlNotifica;
	}

	public TipoInteroperabilita getTipoNotifica() {
		return tipoNotifica;
	}

	public void setTipoNotifica(TipoInteroperabilita tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

}
