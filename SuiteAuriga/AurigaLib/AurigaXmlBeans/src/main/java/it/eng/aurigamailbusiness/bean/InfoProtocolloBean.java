/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.core.business.beans.AbstractBean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * classe che raggruppa degli idmail
 * @author jravagnan
 *
 */
@XmlRootElement
public class InfoProtocolloBean extends AbstractBean implements Serializable{

	private static final long serialVersionUID = 7811255048616558080L;
	
	private String idEmail;
	
	private MailLoginBean loginBean;

	public String getIdEmail() {
		return idEmail;
	}

	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}

	public MailLoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(MailLoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	

}
