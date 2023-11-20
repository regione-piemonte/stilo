/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean per l'invio della mail
 * 
 * @author jravagnan
 */
@XmlRootElement
public class AnonymousSenderBean extends SenderBean implements Serializable {

	private static final long serialVersionUID = 7025594314059680937L;
	
	private String smtpEndpointAccountMitt;

	private String smtpPortAccountMitt;

	public String getSmtpEndpointAccountMitt() {
		return smtpEndpointAccountMitt;
	}

	public void setSmtpEndpointAccountMitt(String smtpEndpointAccountMitt) {
		this.smtpEndpointAccountMitt = smtpEndpointAccountMitt;
	}

	public String getSmtpPortAccountMitt() {
		return smtpPortAccountMitt;
	}

	public void setSmtpPortAccountMitt(String smtpPortAccountMitt) {
		this.smtpPortAccountMitt = smtpPortAccountMitt;
	}
	
	
}