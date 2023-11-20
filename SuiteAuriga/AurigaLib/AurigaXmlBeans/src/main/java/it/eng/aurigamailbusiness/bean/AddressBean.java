/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.ArrayUtils;

import it.eng.core.business.beans.AbstractBean;

/**
 * Indirizzi di invio della mail
 * 
 * @author jravagnan
 * 
 */
@XmlRootElement
public class AddressBean extends AbstractBean implements Serializable {

	private static final long serialVersionUID = 5640712831469061712L;

	private List<String> addressto = new ArrayList<String>();

	private List<String> addresscc = new ArrayList<String>();

	private List<String> addressbcc = new ArrayList<String>();

	private List<String> alladdress = new ArrayList<String>();

	public List<String> getAddressto() {
		return addressto;
	}

	public void setAddressto(List<String> addressto) {
		this.addressto = addressto;
	}

	public List<String> getAddresscc() {
		return addresscc;
	}

	public void setAddresscc(List<String> addresscc) {
		this.addresscc = addresscc;
	}

	public List<String> getAddressbcc() {
		return addressbcc;
	}

	public void setAddressbcc(List<String> addressbcc) {
		this.addressbcc = addressbcc;
	}

	/**
	 * Recupero il numero totale di righe
	 * 
	 * @return
	 */
	public Integer getAddressSize() {
		return addressto.size() + addresscc.size() + addressbcc.size();
	}

	/**
	 * Recupero gli indirizzi del gruppo
	 */
	public List<String> getAddressForGroup(int group, int maxsize) {
		if (alladdress.isEmpty()) {
			alladdress.addAll(addressto);
			alladdress.addAll(addresscc);
			alladdress.addAll(addressbcc);
		}
		return Arrays.asList(ArrayUtils.subarray(alladdress.toArray(new String[0]), maxsize * group, (maxsize * (group + 1))));
	}

	/**
	 * Restituisce il numero totale di gruppi
	 * 
	 * @return
	 */
	public Integer getgroup(Integer maxsize) {
		int group = getAddressSize().intValue() / maxsize.intValue();
		if (getAddressSize().intValue() % maxsize.intValue() != 0) {
			group++;
		}
		return group;
	}

	public void setAlladdress(List<String> alladdress) {
		this.alladdress = alladdress;
	}
}