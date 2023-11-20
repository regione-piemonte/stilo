/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="getDetailRequest")
public class GetDetailRequest extends MutualInput {

	private static final long serialVersionUID = 1L;
	
	@XmlElement(required=true)
	private String idEmail;

	
	public String getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(String idEmail) {
		this.idEmail = idEmail;
	}
	
}//GetDetailRequest
