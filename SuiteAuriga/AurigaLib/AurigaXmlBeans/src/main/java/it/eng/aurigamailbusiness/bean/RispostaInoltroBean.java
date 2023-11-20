/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe che raggruppa informazioni per inoltro o risposta
 * @author jravagnan
 *
 */
@XmlRootElement
public class RispostaInoltroBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = 7811255048616558080L;
	
	private RispostaInoltro rispInol;
	private TEmailMgoBean mailOriginaria;
	private List<TEmailMgoBean> mailOrigInoltroMassivo;
	
	public RispostaInoltro getRispInol() {
		return rispInol;
	}

	public void setRispInol(RispostaInoltro rispInol) {
		this.rispInol = rispInol;
	}

	public TEmailMgoBean getMailOriginaria() {
		return mailOriginaria;
	}

	public void setMailOriginaria(TEmailMgoBean mailOriginaria) {
		this.mailOriginaria = mailOriginaria;
	}

	public List<TEmailMgoBean> getMailOrigInoltroMassivo() {
		return mailOrigInoltroMassivo;
	}

	public void setMailOrigInoltroMassivo(List<TEmailMgoBean> mailOrigInoltroMassivo) {
		this.mailOrigInoltroMassivo = mailOrigInoltroMassivo;
	}
	
}
