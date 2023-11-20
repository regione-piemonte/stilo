/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlRootElement(name = "Destinatari")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "Destinatari dell'email")
public class RecipientsAddresses {

	// @XmlElementWrapper(name = "xxxxx")
	@XmlElement(name = "a"/* , defaultValue = "nomeutente@dominio.it" */)
	@ApiModelProperty(value = "Indirizzi di posta elettronica dei destinatari principali")
	private List<String> addressesTo = new ArrayList<String>(0);

	// @XmlElementWrapper(name = "xxxxx")
	@XmlElement(name = "cc"/* , defaultValue = "nomeutente@dominio.com" */)
	@ApiModelProperty(value = "Indirizzi di posta elettronica dei destinatari in copia conoscenza")
	private List<String> addressesCc = new ArrayList<String>(0);

	// @XmlElementWrapper(name = "xxxxx")
	@XmlElement(name = "ccn"/* , defaultValue = "nomeutente@dominio.net" */)
	@ApiModelProperty(value = "Indirizzi di posta elettronica dei destinatari in copia conoscenza nascosta")
	private List<String> addressesBcc = new ArrayList<String>(0);

	public List<String> getAddressesTo() {
		if (addressesTo == null) {
			return new ArrayList<String>();
		}
		return addressesTo;
	}

	public void setAddressesTo(List<String> addressesTo) {
		this.addressesTo = addressesTo;
	}

	public List<String> getAddressesCc() {
		if (addressesCc == null) {
			return new ArrayList<String>();
		}
		return addressesCc;
	}

	public void setAddressesCc(List<String> addressesCc) {
		this.addressesCc = addressesCc;
	}

	public List<String> getAddressesBcc() {
		if (addressesBcc == null) {
			return new ArrayList<String>();
		}
		return addressesBcc;
	}

	public void setAddressesBcc(List<String> addressesBcc) {
		this.addressesBcc = addressesBcc;
	}

}
