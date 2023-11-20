/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MailAddress implements Serializable{

	private static final long serialVersionUID = -6186467524472872355L;
	
	private String addressFrom;
	private List<String> addressTo;
	private List<String> addressCc;
	private List<String> addressBcc;

	
	public MailAddress() {
	}

	
	public String getAddressFrom() {
		return addressFrom;
	}
	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}

	public List<String> getAddressTo() {
		return addressTo;
	}
	public void setAddressTo(List<String> addressTo) {
		this.addressTo = addressTo;
	}

	public List<String> getAddressCc() {
		return addressCc;
	}
	public void setAddressCc(List<String> addressCc) {
		this.addressCc = addressCc;
	}

	public List<String> getAddressBcc() {
		return addressBcc;
	}
	public void setAddressBcc(List<String> addressBcc) {
		this.addressBcc = addressBcc;
	}


	/**
	 * 
	 * @param indirizziMail, String di indirizzi mail separati da virgola
	 * @return lista di String indirizzi mail
	 */
	public static List<String> StringToList(String indirizziMail) {
		return StringToList(indirizziMail, ",");
	}
	
	/**
	 * 
	 * @param indirizziMail, String di indirizzi mail separati da {@code separatore}
	 * @param separatore
	 * @return lista di String indirizzi mail
	 */
	public static List<String> StringToList(String indirizziMail, String separatore) {
		return Arrays.asList(StringUtils.split(indirizziMail, separatore));
	}
	
	@Override
	public String toString() {
		return String.format("MailAddress [addressFrom=%s, addressTo=%s, addressCc=%s, addressBcc=%s]", addressFrom, addressTo, addressCc,
				addressBcc);
	}

}
